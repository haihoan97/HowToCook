package com.example.howtocook.model.basemodel;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostStep implements Serializable {
    private String postStepId;
    private String postId;
    private int step;
    private String stepContent;
    private ArrayList<Images> listImg;



    public PostStep() {
    }

    public String getPostStepId() {
        return postStepId;
    }

    public void setPostStepId(String postStepId) {
        this.postStepId = postStepId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getStepContent() {
        return stepContent;
    }

    public void setStepContent(String stepContent) {
        this.stepContent = stepContent;
    }

    public ArrayList<Images> getListImg() {
        return listImg;
    }

    public void setListImg(ArrayList<Images> listImg) {
        this.listImg = listImg;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("postStepId",postStepId);
        result.put("postId",postId);
        result.put("step",step);
        result.put("stepContent",stepContent);

        return result;
    }
}
