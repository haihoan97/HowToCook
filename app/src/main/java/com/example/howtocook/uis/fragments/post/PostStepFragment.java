package com.example.howtocook.uis.fragments.post;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.howtocook.R;
import com.example.howtocook.adapter.postadapter.PostResourceAdapter;
import com.example.howtocook.adapter.postadapter.PostStepAdapter;
import com.example.howtocook.model.basemodel.Images;
import com.example.howtocook.model.basemodel.Post;
import com.example.howtocook.model.basemodel.PostStep;
import com.example.howtocook.model.basemodel.Prepare;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostStepFragment extends Fragment {

    RecyclerView post_step_recycle;
    RecyclerView post_step_recycle_resources;
    PostStepAdapter postStepAdapter;
    PostResourceAdapter resourceAdapter;

    ArrayList<PostStep> listStep;
    ArrayList<Prepare> listResource;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    Post post;
    public PostStepFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        post_step_recycle = view.findViewById(R.id.post_step_recycle);
        post_step_recycle_resources = view.findViewById(R.id.post_step_recycle_resources);

        firebaseDatabase = FirebaseDatabase.getInstance();

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        post_step_recycle.setLayoutManager(manager);
        listStep = new ArrayList<>();
        listResource = new ArrayList<>();
        Intent intent = getActivity().getIntent();
        post = (Post) intent.getSerializableExtra("post");

        postStepAdapter = new PostStepAdapter(listStep, getContext());
        post_step_recycle.setAdapter(postStepAdapter);

        RecyclerView.LayoutManager manager2 = new LinearLayoutManager(getContext());
        post_step_recycle_resources.setLayoutManager(manager2);
        resourceAdapter = new PostResourceAdapter(listResource);
        post_step_recycle_resources.setAdapter(resourceAdapter);

        initListStep();
        initListResource();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_step, container, false);
    }

    public void initListStep(){
        databaseReference = firebaseDatabase.getReference("Post/"+post.getPostId());
        databaseReference.child("PostStep").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    PostStep postStep = snapshot.getValue(PostStep.class);
                    getImage(postStep);
                    listStep.add(postStep);
                }

                postStepAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        postStepAdapter.notifyDataSetChanged();
    }

    public void getImage(final PostStep postStep){
        databaseReference = firebaseDatabase.getReference("Post/"+post.getPostId()+"/PostStep/"+postStep.getPostStepId());
        databaseReference.child("Images").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Images> list = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Images images = snapshot.getValue(Images.class);
                    list.add(images);
                }
                postStep.setListImg(list);

                postStepAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        postStepAdapter.notifyDataSetChanged();
    }

    public void initListResource(){
        databaseReference = firebaseDatabase.getReference("Post/"+post.getPostId());
        databaseReference.child("Prepare").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                   Prepare prepare = snapshot.getValue(Prepare.class);
                   listResource.add(prepare);
                }
                resourceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
