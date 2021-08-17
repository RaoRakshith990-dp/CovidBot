package com.example.covidbot.Model;

public class User {
    String id;
    String username;
    String imageurl;
    String status;

    public User() {
    }

    public User(String username, String imageurl,String id, String status) {
        this.username = username;
        this.imageurl = imageurl;
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
