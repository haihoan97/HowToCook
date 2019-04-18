package com.example.howtocook.model;

import java.util.Date;

public class UserNotification {
    private int notiId;
    private String user;
    private String content;
    private String notiType;
    private String friend;
    private Date notiDate;
    private Boolean ischecked;

    public UserNotification(int notiId, String user, String content, String notiType, String friend, Date notiDate, Boolean ischecked) {
        this.notiId = notiId;
        this.user = user;
        this.content = content;
        this.notiType = notiType;
        this.friend = friend;
        this.notiDate = notiDate;
        this.ischecked = ischecked;
    }

    public int getNotiId() {
        return notiId;
    }

    public void setNotiId(int notiId) {
        this.notiId = notiId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNotiType() {
        return notiType;
    }

    public void setNotiType(String notiType) {
        this.notiType = notiType;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public Date getNotiDate() {
        return notiDate;
    }

    public void setNotiDate(Date notiDate) {
        this.notiDate = notiDate;
    }

    public Boolean getIschecked() {
        return ischecked;
    }

    public void setIschecked(Boolean ischecked) {
        this.ischecked = ischecked;
    }
}
