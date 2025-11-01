package com.example.kitabovski.models.database;

import androidx.room.TypeConverter;
import com.example.kitabovski.models.entities.Book;
import com.example.kitabovski.models.entities.User;

public class Converters {

    // User Role Converters
    @TypeConverter
    public static User.Role toUserRole(String value) {
        return value == null ? null : User.Role.valueOf(value);
    }

    @TypeConverter
    public static String fromUserRole(User.Role role) {
        return role == null ? null : role.name();
    }

    // User Status Converters
    @TypeConverter
    public static User.Status toUserStatus(String value) {
        return value == null ? null : User.Status.valueOf(value);
    }

    @TypeConverter
    public static String fromUserStatus(User.Status status) {
        return status == null ? null : status.name();
    }

    // Book Approval Status Converters
    @TypeConverter
    public static Book.ApprovalStatus toApprovalStatus(String value) {
        return value == null ? null : Book.ApprovalStatus.valueOf(value);
    }

    @TypeConverter
    public static String fromApprovalStatus(Book.ApprovalStatus status) {
        return status == null ? null : status.name();
    }
}