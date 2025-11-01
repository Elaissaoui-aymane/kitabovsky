package com.example.kitabovski.models.repository;

import com.example.kitabovski.models.entities.Book;
import com.example.kitabovski.models.entities.User;
import com.example.kitabovski.models.firebase.FirebaseHelper;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

public class AdminRepository {

    public Task<Void> banUser(String userId) {
        return FirebaseHelper.getUserReference(userId).child("status").setValue(User.Status.BANNED);
    }

    public Task<Void> unbanUser(String userId) {
        return FirebaseHelper.getUserReference(userId).child("status").setValue(User.Status.ACTIVE);
    }

    public Task<Void> approveBook(String bookId) {
        return FirebaseHelper.getBookReference(bookId).child("status").setValue(Book.ApprovalStatus.APPROVED);
    }

    public Task<Void> rejectBook(String bookId) {
        // You might want to just remove it or set a "REJECTED" status
        return FirebaseHelper.getBookReference(bookId).child("status").setValue(Book.ApprovalStatus.REJECTED);
    }

    public Task<Void> deleteBook(String bookId) {
        // Note: This does not delete the cover image from Storage. That requires extra steps.
        return FirebaseHelper.getBookReference(bookId).removeValue();
    }
}