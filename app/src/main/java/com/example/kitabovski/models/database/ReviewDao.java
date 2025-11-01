package com.example.kitabovski.models.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.kitabovski.models.entities.Review;
import java.util.List;

@Dao
public interface ReviewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReviews(List<Review> reviews);

    @Query("SELECT * FROM reviews WHERE bookId = :bookId ORDER BY timestamp DESC")
    LiveData<List<Review>> getReviewsForBook(String bookId);
}