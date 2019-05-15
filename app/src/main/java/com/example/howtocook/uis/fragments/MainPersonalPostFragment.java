package com.example.howtocook.uis.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.howtocook.R;
import com.example.howtocook.adapter.MainPersonalPostAdapter;
import com.example.howtocook.model.PersonalPost;
import com.example.howtocook.model.basemodel.Follow;
import com.example.howtocook.model.basemodel.Likes;
import com.example.howtocook.model.basemodel.Post;
import com.example.howtocook.model.basemodel.Users;
import com.example.howtocook.uis.acticities.AddPostActivity;
import com.example.howtocook.uis.acticities.PersonalActivity;
import com.example.howtocook.uis.acticities.PersonalPostActivity;
import com.example.howtocook.utils.HeartAnimation;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPersonalPostFragment extends Fragment implements View.OnClickListener {

    RecyclerView main_personal_post_recycle;
    Button main_add_personal_post;
    CircleImageView main_personal_post_user_ava;

    ArrayList<PersonalPost> listPost;
    MainPersonalPostAdapter adapter;

    FirebaseDatabase firebase_database;
    DatabaseReference database_reference;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    HeartAnimation animation;


    public MainPersonalPostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        main_personal_post_recycle = view.findViewById(R.id.main_personal_post_recycle);
        main_add_personal_post = view.findViewById(R.id.main_add_personal_post);
        main_personal_post_user_ava = view.findViewById(R.id.main_personal_post_user_ava);

        animation = new HeartAnimation();
        firebase_database = FirebaseDatabase.getInstance();

        setUserInfo();


        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        main_personal_post_recycle.setLayoutManager(manager);
        listPost = new ArrayList<>();

        adapter = new MainPersonalPostAdapter(getContext(), currentUser.getUid(), new MainPersonalPostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView) {
                int i = main_personal_post_recycle.getChildAdapterPosition(itemView);
                Intent intent = new Intent(getActivity(), PersonalPostActivity.class);
                intent.putExtra("post", listPost.get(i).getPost());
                startActivity(intent);
            }

            @Override
            public void onLikeButtonClick(View itemView, ToggleButton button, boolean isCheck) {
                int i = main_personal_post_recycle.getChildAdapterPosition(itemView);
                if (isCheck) {
                    addLike(button, listPost.get(i).getPost(), currentUser.getUid());
                    button.setChecked(true);


                } else {
                    removeLike(button, listPost.get(i).getPost(), currentUser.getUid());
                    button.setChecked(false);

                }
            }

            @Override
            public void onFollowButtonClick(View itemView, ToggleButton button, boolean isCheck) {
                int i = main_personal_post_recycle.getChildAdapterPosition(itemView);
                if (isCheck) {
                    addFollow(button, listPost.get(i).getUsers().getUserId(), currentUser.getUid());
                    button.setChecked(true);


                } else {
                    removeFollow(button, listPost.get(i).getUsers().getUserId(), currentUser.getUid());
                    button.setChecked(false);

                }
            }
        }, listPost);
        main_personal_post_recycle.setAdapter(adapter);
        main_personal_post_recycle.clearFocus();

        initListPost();
        main_add_personal_post.setOnClickListener(this);
    }

    private void setUserInfo() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            database_reference = firebase_database.getReference("Users").child(currentUser.getUid());
            database_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Users users = dataSnapshot.getValue(Users.class);
                    if (getActivity() != null) {
                        Glide.with(getActivity()).load(users.getUserImg()).into(main_personal_post_user_ava);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Loi", "Khong  the lay ve thong tin user");
                }
            });

        }
    }

    public void initListPost() {

        if (currentUser != null) {
            DatabaseReference viewReference = firebase_database.getReference("Post");
            viewReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listPost.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Post post = snapshot.getValue(Post.class);
                        PersonalPost personalPost = new PersonalPost();
                        personalPost.setPost(post);
                        setUserInfo2(personalPost, personalPost.getPost().getUserId());
                        listPost.add(personalPost);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void setUserInfo2(final PersonalPost personalPost, final String userId) {

        if (currentUser != null) {
            DatabaseReference database_reference = firebase_database.getReference("Users").child(userId);
            database_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Users users = dataSnapshot.getValue(Users.class);
                    if (users.getUserId().equals(userId)) {
                        personalPost.setUsers(users);
                    }

                    adapter.notifyDataSetChanged();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Loi", "Khong  the lay ve thong tin user");
                }
            });

        }
    }

    public void addLike(Button button, Post post, String Uid) {

        Likes like = new Likes(post.getPostId(), Uid);
        // day du lieu len firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Post").child(post.getPostId()).child("countLike").setValue((post.getCountLike() + 1));

        String key = reference.child("Likes").push().getKey();
        like.setLikeId(key);

        Map<String, Object> like_value = like.toMap();

        Map<String, Object> child_add = new HashMap<>();
        child_add.put("/Likes/" + like.getLikeId(), like_value);

        Task<Void> task = reference.updateChildren(child_add);

        if (task.isSuccessful() == false) {
            animation.animationBigger(button);
        } else {
            Toast.makeText(getActivity(), "Chưa like đc", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeLike(Button button, final Post post, String Uid) {

        if (currentUser != null) {
            DatabaseReference postReference = firebase_database.getReference("Post");
            postReference.child(post.getPostId()).child("countLike").setValue((post.getCountLike()-1));
            database_reference = firebase_database.getReference("Likes");
            database_reference.orderByChild("userId").equalTo(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Likes likes = snapshot.getValue(Likes.class);
                        if (likes.getPostId().equals(post.getPostId())) {
                            database_reference.child(snapshot.getKey()).removeValue();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Loi", "Khong  the lay ve thong tin user");
                }
            });

        }
    }

    public void addFollow(Button button, String UserId2, String Uid) {

        Follow follow = new Follow(Uid, UserId2);
        // day du lieu len firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        String key = reference.child("Follow").push().getKey();
        follow.setFollowId(key);

        Map<String, Object> follow_value = follow.toMap();

        Map<String, Object> child_add = new HashMap<>();
        child_add.put("/Follow/" + follow.getFollowId(), follow_value);

        Task<Void> task = reference.updateChildren(child_add);

        if (task.isSuccessful() == false) {
        } else {
            Toast.makeText(getActivity(), "Chưa follow đc", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeFollow(Button button, final String UserId2, String Uid) {

        if (currentUser != null) {
            database_reference = firebase_database.getReference("Follow");
            database_reference.orderByChild("userFollow").equalTo(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Follow follow = snapshot.getValue(Follow.class);
                        if (follow.getUserIsFollowed().equals(UserId2)) {
                            database_reference.child(snapshot.getKey()).removeValue();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Loi", "");
                }
            });

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main_personal_post, container, false);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_add_personal_post:
                Intent intent = new Intent(getActivity(), AddPostActivity.class);
                startActivity(intent);
        }
    }


}
