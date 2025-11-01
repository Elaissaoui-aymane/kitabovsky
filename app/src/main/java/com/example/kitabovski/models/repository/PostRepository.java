package com.example.kitabovski.models.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.kitabovski.models.database.AppDatabase;
import com.example.kitabovski.models.database.PostDao;
import com.example.kitabovski.models.entities.Post;
import com.example.kitabovski.models.firebase.FirebaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PostRepository {

    private final PostDao postDao;
    private final ExecutorService executorService;

    public PostRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.postDao = db.postDao();
        this.executorService = Executors.newSingleThreadExecutor();
        syncAllPosts();
    }

    public LiveData<List<Post>> getAllPosts() {
        return postDao.getAllPosts();
    }

    public void createPost(Post post) {
        FirebaseHelper.getPostReference(post.getPostId()).setValue(post);
    }

    // Methods for like/dislike would be added here, often using Firebase Transactions

    private void syncAllPosts() {
        FirebaseHelper.getPostsReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Post> posts = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    posts.add(snapshot.getValue(Post.class));
                }
                executorService.execute(() -> postDao.insertPosts(posts));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}