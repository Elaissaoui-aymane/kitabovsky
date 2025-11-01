package com.example.kitabovski.models.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "posts")
public class Post {

    @PrimaryKey
    @NonNull
    private String postId;
    private String writerId;
    private String writerName; // Denormalized
    private String writerProfileImageUrl; // Denormalized
    private String postText;
    private long timestamp;
    private int likeCount = 0;
    private int dislikeCount = 0;

    public Post() {
        // Default constructor
    }

    // Getters and Setters...
    @NonNull
    public String getPostId() { return postId; }
    public void setPostId(@NonNull String postId) { this.postId = postId; }
    public String getWriterId() { return writerId; }
    public void setWriterId(String writerId) { this.writerId = writerId; }
    public String getWriterName() { return writerName; }
    public void setWriterName(String writerName) { this.writerName = writerName; }
    public String getWriterProfileImageUrl() { return writerProfileImageUrl; }
    public void setWriterProfileImageUrl(String writerProfileImageUrl) { this.writerProfileImageUrl = writerProfileImageUrl; }
    public String getPostText() { return postText; }
    public void setPostText(String postText) { this.postText = postText; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    public int getLikeCount() { return likeCount; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }
    public int getDislikeCount() { return dislikeCount; }
    public void setDislikeCount(int dislikeCount) { this.dislikeCount = dislikeCount; }
}