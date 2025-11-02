package com.example.kitabovski.models.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseHelper {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final FirebaseStorage storage = FirebaseStorage.getInstance();
    private static final FirebaseAuth auth = FirebaseAuth.getInstance();

    public static FirebaseAuth getAuthInstance() {
        return auth;
    }

    // --- User References ---
    public static DatabaseReference getUsersReference() {
        return database.getReference("users");
    }

    public static DatabaseReference getUserReference(String userId) {
        return database.getReference("users").child(userId);
    }

    // --- Book and Review References ---
    public static DatabaseReference getBooksReference() {
        return database.getReference("books");
    }

    public static DatabaseReference getBookReference(String bookId) {
        return database.getReference("books").child(bookId);
    }

    public static DatabaseReference getReviewsReference(String bookId) {
        return getBookReference(bookId).child("reviews");
    }

    // --- Post and Reaction References ---
    public static DatabaseReference getPostsReference() {
        return database.getReference("posts");
    }

    public static DatabaseReference getPostReference(String postId) {
        return database.getReference("posts").child(postId);
    }

    public static DatabaseReference getPostLikesReference(String postId) {
        return database.getReference("post_reactions").child(postId).child("likes");
    }

    public static DatabaseReference getPostDislikesReference(String postId) {
        return database.getReference("post_reactions").child(postId).child("dislikes");
    }

    // --- Social (Follow) References ---
    // Path to the list of users that a specific user is following
    public static DatabaseReference getFollowingReference(String userId) {
        return database.getReference("user_relationships").child(userId).child("following");
    }

    // Path to the list of followers for a specific user
    public static DatabaseReference getFollowersReference(String userId) {
        return database.getReference("user_relationships").child(userId).child("followers");
    }

    // --- Storage References ---
    public static StorageReference getBookCoversStorageReference() {
        return storage.getReference("book_covers");
    }
}