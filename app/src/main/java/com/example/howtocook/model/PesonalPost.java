package com.example.howtocook.model;

import com.example.howtocook.model.basemodel.Likes;
import com.example.howtocook.model.basemodel.Post;
import com.example.howtocook.model.basemodel.Users;

import java.io.Serializable;

public class PesonalPost implements Serializable {

    private Post post;
    private Users users;
    private Likes likes;


    public PesonalPost() {
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }
}
