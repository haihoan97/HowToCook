package com.example.howtocook.model.basemodel;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Users {
    private String userId;
    private String username;
    private String password;
    private String fullName;
    private String userImg;
    private boolean gender;

    public Users() {
    }

    public Users(String username, String password, String fullName, String userImg, boolean gender) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.userImg = userImg;
        this.gender = gender;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId",userId);
        result.put("username",username);
        result.put("password",password);
        result.put("fullName",fullName);
        result.put("gender",gender);
        result.put("userImg",userImg);

        return result;
    }
}
