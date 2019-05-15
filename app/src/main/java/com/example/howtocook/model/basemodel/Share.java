package com.example.howtocook.model.basemodel;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Share {

    private String shareId;
    private String postId;
    private String userId;
    private String shareImg;
    private String shareTime;
    private String shareContent;


    public Share() {
    }

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
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

    public String getShareImg() {
        return shareImg;
    }

    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
    }

    public String getShareTime() {
        return shareTime;
    }

    public void setShareTime(String shareTime) {
        this.shareTime = shareTime;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("shareId",shareId);
        result.put("shareContent",shareContent);
        result.put("shareImg",shareImg);
        result.put("shareTime",shareTime);
        result.put("postId",postId);
        result.put("userId",userId);

        return result;
    }
}
