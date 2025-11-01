package com.example.kitabovski.models.firebase;

import android.net.Uri;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FirebaseStorageManager {

    public LiveData<String> uploadFile(Uri fileUri, StorageReference storageReference) {
        MutableLiveData<String> downloadUrlLiveData = new MutableLiveData<>();
        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "_" + fileUri.getLastPathSegment());
        UploadTask uploadTask = fileRef.putFile(fileUri);

        uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }
            return fileRef.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                downloadUrlLiveData.postValue(downloadUri.toString());
            } else {
                downloadUrlLiveData.postValue(null); // Indicates failure
            }
        });

        return downloadUrlLiveData;
    }
}