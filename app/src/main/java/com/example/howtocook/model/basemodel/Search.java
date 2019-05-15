package com.example.howtocook.model.basemodel;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Search implements Serializable {

    private String searchId;
    private String searchContent;
    private String userId;

    public Search(String searchId, String searchContent, String userId) {
        this.searchId = searchId;
        this.searchContent = searchContent;
        this.userId = userId;
    }

    public Search() {
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
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
        result.put("searchId",searchId);
        result.put("searchContent",searchContent);
        result.put("userId",userId);

        return result;
    }
}
