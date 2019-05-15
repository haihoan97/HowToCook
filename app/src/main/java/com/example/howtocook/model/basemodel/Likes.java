package com.example.howtocook.model.basemodel;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Likes {

    private String likeId;
    private String postId;
    private String userId;


    public Likes() {
    }

    public String getLikeId() {
        return likeId;
    }

    public void setLikeId(String likeId) {
        this.likeId = likeId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("likeId",likeId);
        result.put("postId",postId);
        result.put("userId",userId);


        return result;
    }
}
