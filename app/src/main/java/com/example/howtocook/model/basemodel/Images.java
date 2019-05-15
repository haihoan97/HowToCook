package com.example.howtocook.model.basemodel;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Images implements Serializable {
    private String imageId;
    private String postStepId;
    private String imgLink;
    private String des;


    public Images() {
    }

    public Images(String imageId, String postStepId, String imgLink, String des) {
        this.imageId = imageId;
        this.postStepId = postStepId;
        this.imgLink = imgLink;
        this.des = des;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getPostStepId() {
        return postStepId;
    }

    public void setPostStepId(String postStepId) {
        this.postStepId = postStepId;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("imageId",imageId);
        result.put("postStepId",postStepId);
        result.put("imgLink",imgLink);
        result.put("des",des);

        return result;
    }
}
