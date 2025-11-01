package com.example.kitabovski.models.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.kitabovski.models.database.AppDatabase;
import com.example.kitabovski.models.database.BookDao;
import com.example.kitabovski.models.entities.Book;
import com.example.kitabovski.models.firebase.FirebaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookRepository {

    private final BookDao bookDao;
    private final DatabaseReference firebaseBookRef;
    private final ExecutorService executorService;

    public BookRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.bookDao = db.bookDao();
        this.firebaseBookRef = FirebaseHelper.getBooksReference();
        this.executorService = Executors.newSingleThreadExecutor();
        syncBooksWithFirebase();
    }

    // Fetches from Room (which is our Single Source of Truth)
    public LiveData<List<Book>> getAllBooks() {
        return bookDao.getAllBooks();
    }

    public LiveData<Book> getBookById(String bookId) {
        return bookDao.getBookById(bookId);
    }

    public void addBook(Book book) {
        // Add to Firebase, let the sync mechanism handle adding to Room
        firebaseBookRef.child(book.getId()).setValue(book);
    }

    private void syncBooksWithFirebase() {
        firebaseBookRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Book> books = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Book book = snapshot.getValue(Book.class);
                    if (book != null) {
                        books.add(book);
                    }
                }
                // Update local database in a background thread
                executorService.execute(() -> bookDao.insertAll(books));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}