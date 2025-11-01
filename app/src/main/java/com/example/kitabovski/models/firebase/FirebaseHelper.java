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

    public static DatabaseReference getBooksReference() {
        return database.getReference("books");
    }

    public static DatabaseReference getUsersReference() {
        return database.getReference("users");
    }

    public static DatabaseReference getOrdersReference() {
        return database.getReference("orders");
    }

    public static StorageReference getBookCoversStorageReference() {
        return storage.getReference("book_covers");
    }

    public static StorageReference getBookPdfsStorageReference() {
        return storage.getReference("book_pdfs");
    }

    public static FirebaseAuth getAuthInstance() {
        return auth;
    }
}