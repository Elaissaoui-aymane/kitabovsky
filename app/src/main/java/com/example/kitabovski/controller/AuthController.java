package com.example.Kitabovski.controller;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.kitabovski.models.entities.User;
import com.example.kitabovski.models.firebase.FirebaseAuthManager;
import com.example.kitabovski.models.repository.UserRepository;
import com.google.firebase.auth.FirebaseUser;

public class AuthController extends AndroidViewModel {

    private final FirebaseAuthManager authManager;
    private final UserRepository userRepository;

    public AuthController(@NonNull Application application) {
        super(application);
        this.authManager = new FirebaseAuthManager();
        this.userRepository = new UserRepository(application);
    }

    public LiveData<FirebaseUser> getFirebaseUserLiveData() {
        return authManager.getUserLiveData();
    }

    // Updated register method
    public void register(String name, String email, String password, User.Role role, FirebaseAuthManager.AuthTaskListener listener) {
        authManager.register(email, password, new FirebaseAuthManager.AuthTaskListener() {
            @Override
            public void onSuccess() {
                FirebaseUser firebaseUser = authManager.getUserLiveData().getValue();
                if (firebaseUser != null) {
                    User newUser = new User();
                    newUser.setUid(firebaseUser.getUid());
                    newUser.setName(name);
                    newUser.setEmail(email);
                    newUser.setRole(role); // Set the chosen role
                    userRepository.createUser(newUser);
                }
                listener.onSuccess();
            }

            @Override
            public void onFailure(String errorMessage) {
                listener.onFailure(errorMessage);
            }
        });
    }

    public void login(String email, String password, FirebaseAuthManager.AuthTaskListener listener) {
        authManager.login(email, password, listener);
    }

    public void logout() {
        authManager.logout();
    }
}