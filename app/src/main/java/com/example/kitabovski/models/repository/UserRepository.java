package com.example.kitabovski.models.repository;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.example.kitabovski.models.database.AppDatabase;
import com.example.kitabovski.models.database.UserDao;
import com.example.kitabovski.models.entities.User;
import com.example.kitabovski.models.firebase.FirebaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {

    private final UserDao userDao;
    private final DatabaseReference firebaseUserRef;
    private final ExecutorService executorService;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.userDao = db.userDao();
        this.firebaseUserRef = FirebaseHelper.getUsersReference();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    // Fetches a user from the local database
    public LiveData<User> getUser(String userId) {
        // Refresh the local user data from Firebase
        refreshUser(userId);
        return userDao.getUserById(userId);
    }

    // Creates or updates a user in Firebase
    public void createUser(User user) {
        firebaseUserRef.child(user.getUid()).setValue(user);
    }

    // Fetches the latest user data from Firebase and updates the local Room database
    private void refreshUser(final String userId) {
        firebaseUserRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    executorService.execute(() -> userDao.insertUser(user));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Log the error
            }
        });
    }
}