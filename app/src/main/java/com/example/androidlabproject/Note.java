package com.example.androidlabproject;

public class Note {

    private String title, date, body, notebookId, userId;

    public Note() {
    }

    public Note(String title, String date, String body, String notebookId, String userId) {
        this.title = title;
        this.date = date;
        this.body = body;
        this.notebookId = notebookId;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNotebookId() {
        return notebookId;
    }

    public void setNotebookId(String notebookId) {
        this.notebookId = notebookId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
