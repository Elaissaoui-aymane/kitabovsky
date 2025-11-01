package com.example.kitabovski.models.firebase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthManager {

    private final FirebaseAuth firebaseAuth;
    private final MutableLiveData<FirebaseUser> userLiveData;

    public FirebaseAuthManager() {
        this.firebaseAuth = FirebaseHelper.getAuthInstance();
        this.userLiveData = new MutableLiveData<>();
        if (firebaseAuth.getCurrentUser() != null) {
            userLiveData.postValue(firebaseAuth.getCurrentUser());
        }
    }

    public LiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public void register(String email, String password, AuthTaskListener listener) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        userLiveData.postValue(firebaseAuth.getCurrentUser());
                        listener.onSuccess();
                    } else {
                        listener.onFailure(task.getException().getMessage());
                    }
                });
    }

    public void login(String email, String password, AuthTaskListener listener) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        userLiveData.postValue(firebaseAuth.getCurrentUser());
                        listener.onSuccess();
                    } else {
                        listener.onFailure(task.getException().getMessage());
                    }
                });
    }

    public void logout() {
        firebaseAuth.signOut();
        userLiveData.postValue(null);
    }

    public interface AuthTaskListener {
        void onSuccess();
        void onFailure(String errorMessage);
    }
}