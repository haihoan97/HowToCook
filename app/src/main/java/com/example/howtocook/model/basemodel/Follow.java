package com.example.howtocook.model.basemodel;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Follow implements Serializable {
    private String followId;
    private String userFollow;
    private String userIsFollowed;

    public Follow() {
    }

    public Follow(String userFollow, String userIsFollowed) {
        this.userFollow = userFollow;
        this.userIsFollowed = userIsFollowed;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    public String getUserFollow() {
        return userFollow;
    }

    public void setUserFollow(String userFollow) {
        this.userFollow = userFollow;
    }

    public String getUserIsFollowed() {
        return userIsFollowed;
    }

    public void setUserIsFollowed(String userIsFollowed) {
        this.userIsFollowed = userIsFollowed;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("followId",followId);
        result.put("userFollow",userFollow);
        result.put("userIsFollowed",userIsFollowed);

        return result;
    }
}
