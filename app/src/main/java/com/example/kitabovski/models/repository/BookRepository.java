package com.example.kitabovski.models.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.kitabovski.models.database.AppDatabase;
import com.example.kitabovski.models.database.BookDao;
import com.example.kitabovski.models.database.ReviewDao;
import com.example.kitabovski.models.entities.Book;
import com.example.kitabovski.models.entities.Review;
import com.example.kitabovski.models.firebase.FirebaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookRepository {

    private final BookDao bookDao;
    private final ReviewDao reviewDao;
    private final ExecutorService executorService;

    public BookRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.bookDao = db.bookDao();
        this.reviewDao = db.reviewDao();
        this.executorService = Executors.newSingleThreadExecutor();
        syncApprovedBooks();
    }

    // Fetches from Room (Single Source of Truth for approved books)
    public LiveData<List<Book>> getApprovedBooks() {
        return bookDao.getAllApprovedBooks();
    }

    public LiveData<Book> getBookById(String bookId) {
        return bookDao.getBookById(bookId);
    }

    public LiveData<List<Review>> getReviewsForBook(String bookId) {
        syncReviewsForBook(bookId);
        return reviewDao.getReviewsForBook(bookId);
    }

    public void submitBookForReview(Book book) {
        // Writers submit books, which are set to PENDING by default
        FirebaseHelper.getBookReference(book.getId()).setValue(book);
    }

    public void addReview(String bookId, Review review) {
        // Push the review to Firebase
        DatabaseReference reviewRef = FirebaseHelper.getReviewsReference(bookId);
        reviewRef.child(review.getReviewId()).setValue(review);

        // Use a transaction to update the average rating and count on the book object
        DatabaseReference bookRef = FirebaseHelper.getBookReference(bookId);
        bookRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Book book = mutableData.getValue(Book.class);
                if (book == null) {
                    return Transaction.success(mutableData);
                }

                int ratingCount = book.getRatingCount();
                double averageRating = book.getAverageRating();

                double totalStars = averageRating * ratingCount;

                ratingCount++;
                totalStars += review.getRating();
                book.setAverageRating(totalStars / ratingCount);
                book.setRatingCount(ratingCount);

                mutableData.setValue(book);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean committed, DataSnapshot dataSnapshot) {
                // Transaction completed
            }
        });
    }

    private void syncApprovedBooks() {
        FirebaseHelper.getBooksReference().orderByChild("status").equalTo("APPROVED").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Book> books = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    books.add(snapshot.getValue(Book.class));
                }
                executorService.execute(() -> bookDao.insertAll(books));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void syncReviewsForBook(String bookId) {
        FirebaseHelper.getReviewsReference(bookId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Review> reviews = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    reviews.add(snapshot.getValue(Review.class));
                }
                executorService.execute(() -> reviewDao.insertReviews(reviews));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}