package com.prodev.firechat.data;

import android.net.Uri;

public class User {

    private String uid;
    private String userMail;
    private String userPassword;
    private String userImagePath;

    public User() {
    }

    public User(String uid, String userMail, String userPassword, String userImagePath) {
        this.uid = uid;
        this.userMail = userMail;
        this.userPassword = userPassword;
        this.userImagePath = userImagePath;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserImagePath(String userImagePath) {
        this.userImagePath = userImagePath;
    }

    public String getUid() {
        return uid;
    }

    public String getUserMail() {
        return userMail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserImagePath() {
        return userImagePath;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", userMail='" + userMail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                "\n, userImagePath='" + userImagePath + '\'' +
                '}';
    }
}
