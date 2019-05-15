package com.example.howtocook.model;

import com.example.howtocook.model.basemodel.Follow;
import com.example.howtocook.model.basemodel.Users;

import java.io.Serializable;
import java.util.ArrayList;

public class TopUserFollow implements Serializable {
    private Users users;
    private ArrayList<Follow> listIsFollowed;
    int countFollow;

    public TopUserFollow() {
        listIsFollowed = new ArrayList<>();
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public ArrayList<Follow> getListIsFollowed() {
        return listIsFollowed;
    }

    public void setListIsFollowed(ArrayList<Follow> listIsFollowed) {
        this.listIsFollowed = listIsFollowed;
    }

    public int getCountFollow() {
        return countFollow;
    }

    public void setCountFollow(int countFollow) {
        this.countFollow = countFollow;
    }
}
