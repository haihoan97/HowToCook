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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.howtocook.R;
import com.example.howtocook.adapter.MainPersonalPostAdapter;
import com.example.howtocook.model.basemodel.Post;
import com.example.howtocook.model.basemodel.Users;
import com.example.howtocook.uis.acticities.AddPostActivity;
import com.example.howtocook.uis.acticities.PersonalActivity;
import com.example.howtocook.uis.acticities.PersonalPostActivity;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
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
public class MainPersonalPostFragment extends Fragment implements View.OnClickListener {

    RecyclerView main_personal_post_recycle;
    Button main_add_personal_post;
    CircleImageView main_personal_post_user_ava;

    ArrayList<Post> listPost;
    MainPersonalPostAdapter adapter;

    FirebaseDatabase firebase_database;
    DatabaseReference database_reference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private String facebookId = "ooo";

    public MainPersonalPostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        main_personal_post_recycle = view.findViewById(R.id.main_personal_post_recycle);
        main_add_personal_post = view.findViewById(R.id.main_add_personal_post);
        main_personal_post_user_ava = view.findViewById(R.id.main_personal_post_user_ava);

        firebase_database = FirebaseDatabase.getInstance();

        setUserInfo();



        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        main_personal_post_recycle.setLayoutManager(manager);
        listPost = new ArrayList<>();

        adapter = new MainPersonalPostAdapter(new MainPersonalPostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent postIntent = new Intent(getActivity(), PersonalPostActivity.class);
                startActivity(postIntent);
            }

            @Override
            public void onImageClick(ImageView image, int position) {
                switch (image.getId()){
                    case R.id.item_personal_post_img:
                        Toast.makeText(getContext(), "Img Click"+position, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onAvaClick(CircleImageView imageView, int pos) {

            }

            @Override
            public void onButtonClick(Button button, int pos) {

            }

            @Override
            public void onTextViewClick(TextView textView, int position) {
                switch (textView.getId()){
                    case R.id.item_personal_post_name:
                        Toast.makeText(getContext(), textView.getText().toString(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_personal_post_user_name:
                        Intent personal_intent = new Intent(getActivity(), PersonalActivity.class);
                        startActivity(personal_intent);
                        break;

                }
            }
        } , listPost);
        main_personal_post_recycle.setAdapter(adapter);
        main_personal_post_recycle.clearFocus();

        initListPost();


        main_add_personal_post.setOnClickListener(this);
    }
    private void setUserInfo(){
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            database_reference = firebase_database.getReference("Users").child(user.getUid());
            database_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Users users = dataSnapshot.getValue(Users.class);
                    if (getActivity() != null){
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
    public void initListPost(){


        adapter.notifyDataSetChanged();
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
        switch (v.getId()){
            case R.id.main_add_personal_post:
                Intent intent = new Intent(getActivity(), AddPostActivity.class);
                startActivity(intent);
        }
    }
}
