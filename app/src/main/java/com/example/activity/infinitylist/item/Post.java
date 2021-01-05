package com.example.activity.infinitylist.item;

import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("author")
    private String author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
