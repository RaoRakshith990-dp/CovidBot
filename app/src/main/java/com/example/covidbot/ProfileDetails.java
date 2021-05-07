package com.example.covidbot;

public class ProfileDetails {
    String username;
    String useremail;
    String profileimage;
    String usernumber;

    public ProfileDetails() {
    }

    public ProfileDetails(String username, String useremail, String profileimage, String usernumber) {
        this.username = username;
        this.useremail = useremail;
        this.profileimage = profileimage;
        this.usernumber = usernumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getUsernumber() {
        return usernumber;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
    }
}