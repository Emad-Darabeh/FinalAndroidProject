package com.example.androidlabproject;

public class Notebook {

    String title, createdAt, id, userId;

    public Notebook() {
    }

    public Notebook(String title, String createdAt, String id, String userId) {
        this.title = title;
        this.createdAt = createdAt;
        this.id = id;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
