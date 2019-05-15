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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.howtocook.R;
import com.example.howtocook.adapter.ShowSmallPostAdapter;
import com.example.howtocook.adapter.personalpage.TheoDoiAdapter;
import com.example.howtocook.model.PersonalPost;
import com.example.howtocook.model.TopUserFollow;
import com.example.howtocook.model.basemodel.Follow;
import com.example.howtocook.model.basemodel.Likes;
import com.example.howtocook.model.basemodel.Post;
import com.example.howtocook.model.basemodel.Users;
import com.example.howtocook.uis.acticities.PersonalPostActivity;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainHomeFragment extends Fragment {

    private RecyclerView home_what_eat_today;
    private RecyclerView home_special_post;
    private RecyclerView home_top_user;

    private TextView home_user_name;
    private CircleImageView home_user_ava;


    private ShowSmallPostAdapter postAdapter;
    private ShowSmallPostAdapter postAdapter2;
    private TheoDoiAdapter theoDoiAdapter;

    private ArrayList<PersonalPost> listViewPost;
    private ArrayList<PersonalPost> listLikePost;
    private ArrayList<TopUserFollow> listUser;

    FirebaseDatabase firebase_database;
    DatabaseReference database_reference;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


    HeartAnimation animation;

    public MainHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        home_what_eat_today = view.findViewById(R.id.home_what_eat_today);
        home_special_post = view.findViewById(R.id.home_special_post);
        home_top_user = view.findViewById(R.id.home_top_user);
        home_user_name = view.findViewById(R.id.home_user_name);
        home_user_ava = view.findViewById(R.id.home_user_ava);

        listViewPost = new ArrayList<>();
        listUser = new ArrayList<>();
        listLikePost = new ArrayList<>();
        animation = new HeartAnimation();

        firebase_database = FirebaseDatabase.getInstance();

        setUserInfo(currentUser.getUid());


        postAdapter = new ShowSmallPostAdapter(currentUser.getUid(), listViewPost, getContext(), new ShowSmallPostAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View itemView) {
                int i = home_what_eat_today.getChildAdapterPosition(itemView);
                Intent intent = new Intent(getActivity(), PersonalPostActivity.class);
                intent.putExtra("post", listViewPost.get(i).getPost());
                startActivity(intent);

            }

            @Override
            public void onLikeButtonClick(View itemView, ToggleButton button, boolean isCheck) {
                int i = home_what_eat_today.getChildAdapterPosition(itemView);
                View v = home_special_post.getChildAt(i);
                if (isCheck) {
                    addLike(button, listViewPost.get(i).getPost(), currentUser.getUid());
                    button.setChecked(true);


                } else {
                    removeLike(button, listViewPost.get(i).getPost(), currentUser.getUid());
                    button.setChecked(false);

                }
            }

            @Override
            public void onFollowButtonClick(View itemView, ToggleButton button, boolean isCheck) {
                int i = home_special_post.getChildAdapterPosition(itemView);
                if (isCheck) {
                    addFollow(button, listViewPost.get(i).getPost().getUserId(), currentUser.getUid());
                    button.setChecked(true);


                } else {
                    removeFollow(button, listViewPost.get(i).getPost().getUserId(), currentUser.getUid());
                    button.setChecked(false);

                }
            }
        });
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        home_what_eat_today.setLayoutManager(manager);
        home_what_eat_today.setAdapter(postAdapter);

        postAdapter2 = new ShowSmallPostAdapter(currentUser.getUid(), listLikePost, getContext(), new ShowSmallPostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView) {
                int i = home_special_post.getChildAdapterPosition(itemView);
                Intent intent = new Intent(getActivity(), PersonalPostActivity.class);
                intent.putExtra("post", listLikePost.get(i).getPost());
                startActivity(intent);
            }

            @Override
            public void onLikeButtonClick(View itemView, ToggleButton button, boolean isCheck) {
                int i = home_special_post.getChildAdapterPosition(itemView);
                View v = home_special_post.getChildAt(i);
                if (isCheck) {
                    addLike(button, listLikePost.get(i).getPost(), currentUser.getUid());
                    button.setChecked(true);


                } else {
                    removeLike(button, listLikePost.get(i).getPost(), currentUser.getUid());
                    button.setChecked(false);

                }
            }

            @Override
            public void onFollowButtonClick(View itemView, ToggleButton button, boolean isCheck) {
                int i = home_special_post.getChildAdapterPosition(itemView);
                View v = home_special_post.getChildAt(i);
                if (isCheck) {
                    addFollow(button, listLikePost.get(i).getUsers().getUserId(), currentUser.getUid());
                    button.setChecked(true);


                } else {
                    removeFollow(button, listLikePost.get(i).getUsers().getUserId(), currentUser.getUid());
                    button.setChecked(false);

                }
            }

        });
        RecyclerView.LayoutManager manager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        home_special_post.setLayoutManager(manager2);
        home_special_post.setAdapter(postAdapter2);

        initListUser();
        theoDoiAdapter = new TheoDoiAdapter(listUser, getContext());
        RecyclerView.LayoutManager topUserManager = new LinearLayoutManager(getActivity());
        home_top_user.setLayoutManager(topUserManager);
        home_top_user.setAdapter(theoDoiAdapter);

        initTopViewPost();
        initTopLikeList();


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

    private void setUserInfo(String userId) {

        if (currentUser != null) {
            database_reference = firebase_database.getReference("Users").child(userId);
            database_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Users users = dataSnapshot.getValue(Users.class);
                    home_user_name.setText(users.getFullName());
                    if (getActivity() != null) {
                        Glide.with(getActivity()).load(users.getUserImg()).into(home_user_ava);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Loi", "Khong  the lay ve thong tin user");
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

                    postAdapter.notifyDataSetChanged();
                    postAdapter2.notifyDataSetChanged();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Loi", "Khong  the lay ve thong tin user");
                }
            });

        }
    }


    public void initTopViewPost() {
        if (currentUser != null) {
            DatabaseReference viewReference = firebase_database.getReference("Post");
            viewReference.limitToLast(5).orderByChild("view").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listViewPost.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Post post = snapshot.getValue(Post.class);
                        PersonalPost personalPost = new PersonalPost();
                        personalPost.setPost(post);
                        setUserInfo2(personalPost, personalPost.getPost().getUserId());
                        listViewPost.add(personalPost);
                    }
                    Collections.sort(listViewPost, new Comparator<PersonalPost>() {
                        @Override
                        public int compare(PersonalPost o1, PersonalPost o2) {
                            return o2.getPost().getView() - o1.getPost().getView();
                        }
                    });
                    postAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void initTopLikeList() {
        if (currentUser != null) {
            DatabaseReference viewReference = firebase_database.getReference("Post");
            viewReference.limitToLast(5).orderByChild("countLike").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    listLikePost.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Post post = snapshot.getValue(Post.class);
                        PersonalPost personalPost = new PersonalPost();
                        personalPost.setPost(post);
                        setUserInfo2(personalPost, personalPost.getPost().getUserId());
                        listLikePost.add(personalPost);
                    }
                    Collections.sort(listLikePost, new Comparator<PersonalPost>() {
                        @Override
                        public int compare(PersonalPost o1, PersonalPost o2) {
                            return o2.getPost().getCountLike() - o1.getPost().getCountLike();
                        }
                    });
                    postAdapter2.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void initListUser() {
        if (currentUser != null) {
            DatabaseReference database_reference = firebase_database.getReference("Users");
            database_reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TopUserFollow> list = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Users users = snapshot.getValue(Users.class);
                        TopUserFollow topUserFollow = new TopUserFollow();
                        topUserFollow.setUsers(users);
                        getFollowUser(topUserFollow, users.getUserId());
                        Log.d("count2", ""+topUserFollow.getCountFollow());
                        list.add(topUserFollow);
                    }

                    Collections.sort(list, new Comparator<TopUserFollow>() {
                        @Override
                        public int compare(TopUserFollow o1, TopUserFollow o2) {

                            return o2.getCountFollow()  - o1.getCountFollow();
                        }
                    });

                    for (int i = 0; i< list.size(); i++){
                        listUser.add(list.get(i));
                        theoDoiAdapter.notifyDataSetChanged();
                        if (i>=4){
                            break;
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

    public void getFollowUser(final TopUserFollow topUserFollow, String Uid){
        DatabaseReference database_reference = firebase_database.getReference("Follow");
        database_reference.orderByChild("userIsFollowed").equalTo(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int count = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Follow follow = snapshot.getValue(Follow.class);
                    topUserFollow.getListIsFollowed().add(follow);
                    count++;

                }
                topUserFollow.setCountFollow(count);
                Log.d("count", ""+count);
                theoDoiAdapter.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Loi", "Khong  the lay ve thong tin user");
            }
        });
    }

}
