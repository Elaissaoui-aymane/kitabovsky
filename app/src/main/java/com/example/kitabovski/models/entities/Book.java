package com.example.kitabovski.models.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "books")
public class Book {

    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private String author;
    private String description;
    private String coverImageUrl;
    private String pdfUrl;
    private double price;
    private String ownerId;
    private long timestamp;

    public Book() {
        // Default constructor required for calls to DataSnapshot.getValue(Book.class)
    }

    public Book(@NonNull String id, String title, String author, String description,
                String coverImageUrl, String pdfUrl, double price, String ownerId, long timestamp) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.coverImageUrl = coverImageUrl;
        this.pdfUrl = pdfUrl;
        this.price = price;
        this.ownerId = ownerId;
        this.timestamp = timestamp;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }



    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}