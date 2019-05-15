package com.example.howtocook.uis.fragments.personal;


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
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.howtocook.R;
import com.example.howtocook.adapter.personalpage.FollowAdapter;
import com.example.howtocook.adapter.personalpage.TheoDoiAdapter;
import com.example.howtocook.model.PersonalPost;
import com.example.howtocook.model.TopUserFollow;
import com.example.howtocook.model.basemodel.Follow;
import com.example.howtocook.model.basemodel.Users;
import com.example.howtocook.uis.acticities.PersonalActivity;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class TheoDoiFragment extends Fragment {

    ArrayList<Follow> arr;
    RecyclerView recyclerView;
    TheoDoiAdapter theoDoiAdapter;
    FollowAdapter followAdapter;

    FirebaseDatabase firebase_database;
    DatabaseReference database_reference;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_theo_doi,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebase_database = FirebaseDatabase.getInstance();
        arr = new ArrayList<>();

        final Intent intent = getActivity().getIntent();
        String Uid = intent.getStringExtra("user");
        recyclerView = view.findViewById(R.id.recyclerView);
        followAdapter = new FollowAdapter(arr, getContext(), new FollowAdapter.OnItemClick() {
            @Override
            public void onItemClick(View view) {
                int i = recyclerView.getChildAdapterPosition(view);
                Intent intent1 = new Intent(getContext(), PersonalActivity.class);
                intent1.putExtra("user", arr.get(i).getUserIsFollowed());
                startActivity(intent1);
            }

            @Override
            public void ToggleClick(View view, ToggleButton toggleButton, boolean isCheck) {

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(followAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        getFollowUser(Uid);

    }

    public void getFollowUser(String Uid){
        DatabaseReference database_reference = firebase_database.getReference("Follow");
        database_reference.orderByChild("userFollow").equalTo(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Follow follow = snapshot.getValue(Follow.class);
                    arr.add(follow);

                }
                followAdapter.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Loi", "Khong  the lay ve thong tin user");
            }
        });
    }



}
