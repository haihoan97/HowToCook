package com.example.howtocook.uis.acticities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.howtocook.R;
import com.example.howtocook.adapter.postadapter.PersonalPostViewPager;
import com.example.howtocook.model.basemodel.Follow;
import com.example.howtocook.model.basemodel.Likes;
import com.example.howtocook.model.basemodel.Post;
import com.example.howtocook.model.basemodel.Users;
import com.example.howtocook.utils.HeartAnimation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalPostActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout personal_post_tab;
    private ViewPager personal_post_view_pager;
    private ToggleButton personal_post_like;
    private Button personal_post_view_comment;
    private HeartAnimation animation;
    private CircleImageView personal_post_user_ava;
    private TextView personal_post_user_name;
    private TextView personal_post_status;
    private ToggleButton personal_post_follow;
    private TextView personal_post_name;
    private TextView personal_post_des;
    private ImageView personal_post_ava;
    private Button personal_post_save;


    private PersonalPostViewPager viewPagerAdapter;
    Post post;
    int countLike = 0;
    int countView = 0;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_post);

        personal_post_tab = findViewById(R.id.personal_post_tab);
        personal_post_view_pager = findViewById(R.id.personal_post_view_pager);
        personal_post_like = findViewById(R.id.personal_post_like);
        personal_post_view_comment = findViewById(R.id.personal_post_view_comment);
        personal_post_user_ava = findViewById(R.id.personal_post_user_ava);
        personal_post_user_name = findViewById(R.id.personal_post_user_name);
        personal_post_status = findViewById(R.id.personal_post_status);
        personal_post_follow = findViewById(R.id.personal_post_follow);
        personal_post_name = findViewById(R.id.personal_post_name);
        personal_post_des = findViewById(R.id.personal_post_des);
        personal_post_ava = findViewById(R.id.personal_post_ava);
        personal_post_save = findViewById(R.id.personal_post_save);

        animation = new HeartAnimation();
        firebaseDatabase = FirebaseDatabase.getInstance();
        Intent intent = getIntent();
        post = (Post) intent.getSerializableExtra("post");
        countLike = post.getCountLike();
        countView = post.getView();
        initPost();

        viewPagerAdapter = new PersonalPostViewPager(getSupportFragmentManager());
        personal_post_view_pager.setAdapter(viewPagerAdapter);
        personal_post_tab.setupWithViewPager(personal_post_view_pager);

        personal_post_view_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(personal_post_tab));
        personal_post_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                personal_post_view_pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        personal_post_follow.setOnClickListener(this);
        personal_post_like.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personal_post_view_comment:
                personal_post_view_pager.setCurrentItem(1);
                break;
            case R.id.personal_post_follow:
                boolean isFollow = personal_post_follow.isChecked();
                if (isFollow){
                    addFollow(post.getUserId(), currentUser.getUid());
                    personal_post_follow.setChecked(true);


                } else {
                    removeFollow(post.getUserId(), currentUser.getUid());
                    personal_post_follow.setChecked(false);

                }

                break;
            case R.id.personal_post_like:
                boolean isLike = personal_post_like.isChecked();
                if (isLike){
                    addLike(personal_post_like, post, currentUser.getUid());
                    personal_post_like.setChecked(true);


                } else {
                    removeLike(personal_post_like, post, currentUser.getUid());
                    personal_post_like.setChecked(false);

                }

                break;

        }
    }

    public void initPost(){

        if (post!= null){
            personal_post_name.setText(post.getPostName());
            personal_post_des.setText(post.getPostDes());
            personal_post_status.setText(post.getPostTime());
            Glide.with(this).load(post.getPostImage()).into(personal_post_ava);
            Log.d("user", post.getUserId());
            initUser(post.getUserId());
            checkFollow(post.getUserId());
            checkLike(currentUser.getUid());
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            reference.child("Post").child(post.getPostId()).child("view").setValue((countView + 1));
        }

    }

    public void initUser(String Uid){
        final DatabaseReference user_reference = firebaseDatabase.getReference("Users").child(Uid);
        user_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users users = dataSnapshot.getValue(Users.class);
                personal_post_user_name.setText(users.getFullName());
                Glide.with(PersonalPostActivity.this).load(users.getUserImg()).into(personal_post_user_ava);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Loi", "");
            }
        });
    }

    public void checkFollow(final String Uid){
        if (currentUser != null) {
            databaseReference = firebaseDatabase.getReference("Follow");
            databaseReference.orderByChild("userFollow").equalTo(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Follow follow = snapshot.getValue(Follow.class);
                        if (follow.getUserIsFollowed().equals(Uid)) {
                            personal_post_follow.setChecked(true);
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

    public void checkLike(final String Uid){
        if (currentUser != null) {
            databaseReference = firebaseDatabase.getReference("Likes");
            databaseReference.orderByChild("userId").equalTo(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Likes likes = snapshot.getValue(Likes.class);
                        if (post.getPostId().equals(likes.getPostId())) {
                            personal_post_like.setChecked(true);
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

    public void addFollow(String UserId2, String Uid) {

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
            Toast.makeText(this, "Chưa follow đc", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeFollow( final String UserId2, String Uid) {

        if (currentUser != null) {
            databaseReference = firebaseDatabase.getReference("Follow");
            databaseReference.orderByChild("userFollow").equalTo(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Follow follow = snapshot.getValue(Follow.class);
                        if (follow.getUserIsFollowed().equals(UserId2)) {
                            databaseReference.child(snapshot.getKey()).removeValue();
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

    public void addLike(Button button, Post post, String Uid) {

        Likes like = new Likes(post.getPostId(), Uid);
        countLike = countLike+1;
        // day du lieu len firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Post").child(post.getPostId()).child("countLike").setValue(countLike);

        String key = reference.child("Likes").push().getKey();
        like.setLikeId(key);

        Map<String, Object> like_value = like.toMap();

        Map<String, Object> child_add = new HashMap<>();
        child_add.put("/Likes/" + like.getLikeId(), like_value);

        Task<Void> task = reference.updateChildren(child_add);

        if (task.isSuccessful() == false) {
            animation.animationBigger(button);
        } else {
            Toast.makeText(this, "Chưa like đc", Toast.LENGTH_SHORT).show();
        }

    }

    public void removeLike(Button button, final Post post, String Uid) {

        if (currentUser != null) {
            countLike = countLike-1;
            DatabaseReference postReference = firebaseDatabase.getReference("Post");
            postReference.child(post.getPostId()).child("countLike").setValue(countLike);
            databaseReference = firebaseDatabase.getReference("Likes");
            databaseReference.orderByChild("userId").equalTo(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Likes likes = snapshot.getValue(Likes.class);
                        if (likes.getPostId().equals(post.getPostId())) {
                            databaseReference.child(snapshot.getKey()).removeValue();
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


}
