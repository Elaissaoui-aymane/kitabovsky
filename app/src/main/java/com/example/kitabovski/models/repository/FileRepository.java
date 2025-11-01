package com.example.kitabovski.models.repository;

import android.net.Uri;
import androidx.lifecycle.LiveData;
import com.example.kitabovski.models.firebase.FirebaseHelper;
import com.example.kitabovski.models.firebase.FirebaseStorageManager;

public class FileRepository {

    private final FirebaseStorageManager storageManager;

    public FileRepository() {
        this.storageManager = new FirebaseStorageManager();
    }

    public LiveData<String> uploadBookCover(Uri imageUri) {
        return storageManager.uploadFile(imageUri, FirebaseHelper.getBookCoversStorageReference());
    }

    public LiveData<String> uploadBookPdf(Uri pdfUri) {
        return storageManager.uploadFile(pdfUri, FirebaseHelper.getBookPdfsStorageReference());
    }
}