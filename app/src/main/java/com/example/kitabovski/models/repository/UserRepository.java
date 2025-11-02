package com.example.kitabovski.models.repository;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.example.kitabovski.models.database.AppDatabase;
import com.example.kitabovski.models.database.UserDao;
import com.example.kitabovski.models.entities.User;
import com.example.kitabovski.models.firebase.FirebaseHelper;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {

    private final UserDao userDao;
    private final ExecutorService executorService;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.userDao = db.userDao();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<User> getUser(String userId) {
        refreshUser(userId);
        return userDao.getUserById(userId);
    }

    public void createUser(User user) {
        FirebaseHelper.getUserReference(user.getUid()).setValue(user);
    }

    public Task<Void> updateUserProfile(String userId, String name, String bio) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", name);
        updates.put("bio", bio);
        return FirebaseHelper.getUserReference(userId).updateChildren(updates);
    }

    public void followUser(String currentUserId, String targetUserId) {
        // Add target to current user's "following" list
        FirebaseHelper.getFollowingReference(currentUserId).child(targetUserId).setValue(true);
        // Add current user to target's "followers" list
        FirebaseHelper.getFollowersReference(targetUserId).child(currentUserId).setValue(true);
    }

    public void unfollowUser(String currentUserId, String targetUserId) {
        // Remove target from current user's "following" list
        FirebaseHelper.getFollowingReference(currentUserId).child(targetUserId).removeValue();
        // Remove current user from target's "followers" list
        FirebaseHelper.getFollowersReference(targetUserId).child(currentUserId).removeValue();
    }

    // You would also add LiveData methods to observe followers/following counts and lists

    private void refreshUser(final String userId) {
        DatabaseReference userRef = FirebaseHelper.getUserReference(userId);
        userRef.addValueEventListener(new ValueEventListener() { // Use addValueEventListener for real-time updates
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    executorService.execute(() -> userDao.insertUser(user));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Log error
            }
        });
    }
}