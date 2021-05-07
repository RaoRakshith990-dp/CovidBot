package com.example.covidbot.Model;

public class User {
    String id;
    String username;
    String imageurl;

    public User() {
    }

    public User(String username, String imageurl,String id) {
        this.username = username;
        this.imageurl = imageurl;
        this.id = id;
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
}
