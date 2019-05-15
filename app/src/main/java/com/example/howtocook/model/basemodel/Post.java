package com.example.howtocook.model.basemodel;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Post implements Serializable {
    private String postId;
    private String userId;
    private String amthucId;
    private String mucdichId;
    private String seasonId;
    private String postName;
    private String mucDo;
    private String khauPhan;
    private String postImage;
    private String postTime;
    private String postDes;
    private int view;
    private int countLike;

    public int getCountLike() {
        return countLike;
    }

    public void setCountLike(int countLike) {
        this.countLike = countLike;
    }

    public Post() {
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

    public String getAmthucId() {
        return amthucId;
    }

    public void setAmthucId(String amthucId) {
        this.amthucId = amthucId;
    }

    public String getMucdichId() {
        return mucdichId;
    }

    public void setMucdichId(String mucdichId) {
        this.mucdichId = mucdichId;
    }

    public String getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(String seasonId) {
        this.seasonId = seasonId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getMucDo() {
        return mucDo;
    }

    public void setMucDo(String mucDo) {
        this.mucDo = mucDo;
    }

    public String getKhauPhan() {
        return khauPhan;
    }

    public void setKhauPhan(String khauPhan) {
        this.khauPhan = khauPhan;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public String getPostDes() {
        return postDes;
    }

    public void setPostDes(String postDes) {
        this.postDes = postDes;
    }

    @Exclude
    public Map<String, Object> toMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("postId",postId);
        result.put("userId",userId);
        result.put("amthucId",amthucId);
        result.put("mucdichId", mucdichId);
        result.put("season",seasonId);
        result.put("postName", postName);
        result.put("mucDo", mucDo);
        result.put("khauPhan", khauPhan);
        result.put("postImage",postImage);
        result.put("postTime", postTime);
        result.put("view", view);
        result.put("like", countLike);
        result.put("postDes", postDes);

        return result;
    }
}
