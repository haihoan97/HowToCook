package com.example.howtocook.uis.fragments;


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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.howtocook.R;
import com.example.howtocook.adapter.ShowSmallPostAdapter;
import com.example.howtocook.adapter.personalpage.TheoDoiAdapter;
import com.example.howtocook.model.basemodel.Post;
import com.example.howtocook.model.basemodel.Users;
import com.example.howtocook.utils.HeartAnimation;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
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

    private ArrayList<Post> listPost;
    private ArrayList<String> listUser;

    FirebaseDatabase firebase_database;
    DatabaseReference database_reference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



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

        listPost = new ArrayList<>();
        listUser = new ArrayList<>();
        animation = new HeartAnimation();

        firebase_database = FirebaseDatabase.getInstance();

        setUserInfo();


        initTopUserArray();

        postAdapter = new ShowSmallPostAdapter(listPost, new ShowSmallPostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {

            }

            @Override
            public void onLikeButtonClick(ToggleButton button, boolean isCheck) {
                if (isCheck){
                    animation.animationBigger(button);
                }
            }
        });
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        home_what_eat_today.setLayoutManager(manager);
        home_what_eat_today.setAdapter(postAdapter);

        postAdapter2 = new ShowSmallPostAdapter(listPost, new ShowSmallPostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {

            }

            @Override
            public void onLikeButtonClick(ToggleButton button, boolean isCheck) {
                if (isCheck){
                    animation.animationBigger(button);
                }
            }
        });
        RecyclerView.LayoutManager manager2 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        home_special_post.setLayoutManager(manager2);
        home_special_post.setAdapter(postAdapter2);

        initListUser();
        theoDoiAdapter = new TheoDoiAdapter(listUser, getContext());
        RecyclerView.LayoutManager topUserManager = new LinearLayoutManager(getActivity());
        home_top_user.setLayoutManager(topUserManager);
        home_top_user.setAdapter(theoDoiAdapter);




    }

    private void setUserInfo(){
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            database_reference = firebase_database.getReference("Users").child(user.getUid());
            database_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Users users = dataSnapshot.getValue(Users.class);
                    home_user_name.setText(users.getFullName());
                    if (getActivity() != null){
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


    public void initTopUserArray(){

    }

    public void initListUser(){
        listUser.clear();
        for (int i = 0; i<5; i++){
            listUser.add("a"+i);
        }
    }

}
