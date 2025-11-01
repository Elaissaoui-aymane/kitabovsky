package com.example.kitabovski.models.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "reviews")
public class Review {

    @PrimaryKey
    @NonNull
    private String reviewId;
    private String bookId;
    private String userId;
    private String userName; // Denormalized for easy display
    private String userProfileImageUrl; // Denormalized for easy display
    private float rating; // e.g., 1.0 to 5.0
    private String reviewText;
    private long timestamp;

    public Review() {
        // Default constructor
    }

    // Getters and Setters...
    @NonNull
    public String getReviewId() { return reviewId; }
    public void setReviewId(@NonNull String reviewId) { this.reviewId = reviewId; }
    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getUserProfileImageUrl() { return userProfileImageUrl; }
    public void setUserProfileImageUrl(String userProfileImageUrl) { this.userProfileImageUrl = userProfileImageUrl; }
    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }
    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}