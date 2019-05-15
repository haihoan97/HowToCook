package com.example.howtocook.model.basemodel;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Prepare implements Serializable {
    private String prepareId;
    private String postId;
    private String sourceName;
    private String sourceAmount;

    public Prepare() {
    }

    public Prepare(String prepareId, String postId, String sourceName, String sourceAmount) {
        this.prepareId = prepareId;
        this.postId = postId;
        this.sourceName = sourceName;
        this.sourceAmount = sourceAmount;
    }

    public String getPrepareId() {
        return prepareId;
    }

    public void setPrepareId(String prepareId) {
        this.prepareId = prepareId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceAmount() {
        return sourceAmount;
    }

    public void setSourceAmount(String sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("prepareId",prepareId);
        result.put("postId",postId);
        result.put("sourceName",sourceName);
        result.put("sourceAmount",sourceAmount);

        return result;
    }
}
