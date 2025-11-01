package com.example.kitabovski.models.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters; // We will add this later if needed for complex types like List<String>
import com.example.kitabovski.models.entities.Book;
import com.example.kitabovski.models.entities.User;
import com.example.kitabovski.models.entities.Order;

@Database(entities = {Book.class, User.class, Order.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BookDao bookDao();
    public abstract UserDao userDao();
    // public abstract OrderDao orderDao(); // Add this when you create OrderDao

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "kitabovski_database")
                            .fallbackToDestructiveMigration() // Not for production, for dev only
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}