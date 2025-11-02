package com.example.kitabovski.models.database;

import android.content.Context;

import androidx.databinding.adapters.Converters;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.kitabovski.models.entities.Book;
import com.example.kitabovski.models.entities.Post;
import com.example.kitabovski.models.entities.Review;
import com.example.kitabovski.models.entities.User;

@Database(entities = {User.class, Book.class, Review.class, Post.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class}) // We'll add this for the enums
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract BookDao bookDao();
    public abstract ReviewDao reviewDao();
    public abstract PostDao postDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "kitabovski_database")
                            .fallbackToDestructiveMigration() // Use this for dev, plan migrations for prod
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}