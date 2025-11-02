package com.example.kitabovski.models.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.kitabovski.models.entities.Book;
import java.util.List;

@Dao
public interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Book> books);

    @Query("SELECT * FROM books WHERE status = 'APPROVED' ORDER BY timestamp DESC")
    LiveData<List<Book>> getAllApprovedBooks();

    @Query("SELECT * FROM books WHERE id = :bookId")
    LiveData<Book> getBookById(String bookId);

    @Query("SELECT * FROM books WHERE status = 'PENDING' ORDER BY timestamp ASC")
    LiveData<List<Book>> getPendingBooks();
}