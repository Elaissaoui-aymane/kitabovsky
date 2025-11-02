package com.example.kitabovski.models.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "users")
public class User {

    public enum Role { READER, WRITER, ADMIN }
    public enum Status { ACTIVE, BANNED }

    @PrimaryKey
    @NonNull
    private String uid;
    private String name;
    private String email;
    private String profileImageUrl;
    private String bio;
    private Role role = Role.READER; // Default role
    private Status status = Status.ACTIVE; // Default status

    public User() {
        // Default constructor for Firebase
    }

    // Getters and Setters for all fields...
    @NonNull
    public String getUid() { return uid; }
    public void setUid(@NonNull String uid) { this.uid = uid; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getProfileImageUrl() { return profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}