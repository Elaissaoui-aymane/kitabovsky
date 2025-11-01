package com.example.kitabovski.controller;

import android.app.Application;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.kitabovski.models.entities.Book;
import com.example.kitabovski.models.repository.BookRepository;
import com.example.kitabovski.models.repository.FileRepository;
import java.util.List;

public class BookController extends AndroidViewModel {

    private final BookRepository bookRepository;
    private final FileRepository fileRepository;
    private final LiveData<List<Book>> allBooks;

    public BookController(@NonNull Application application) {
        super(application);
        bookRepository = new BookRepository(application);
        fileRepository = new FileRepository();
        allBooks = bookRepository.getAllBooks();
    }

    public LiveData<List<Book>> getAllBooks() {
        return allBooks;
    }

    public LiveData<Book> getBookById(String bookId) {
        return bookRepository.getBookById(bookId);
    }

    public void addBook(Book book) {
        bookRepository.addBook(book);
    }

    public LiveData<String> uploadBookCover(Uri imageUri) {
        return fileRepository.uploadBookCover(imageUri);
    }
}