package com.example.howtocook.uis.fragments.personal;


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
import com.example.howtocook.adapter.personalpage.ChiaSeAdapter;
import com.example.howtocook.adapter.postadapter.PostShareAdapter;
import com.example.howtocook.model.basemodel.Share;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChiaSeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Share> listShare;
    private PostShareAdapter postShareAdapter;

    public ChiaSeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listShare = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);

        postShareAdapter = new PostShareAdapter(listShare, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(postShareAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        Intent intent = getActivity().getIntent();
        String Uid = intent.getStringExtra("user");
        setShareData(Uid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chia, container, false);
        return view;
    }

    public void setShareData(String Uid) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Share");
        databaseReference.orderByChild("userId").equalTo(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listShare.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Share share = snapshot.getValue(Share.class);
                    listShare.add(share);
                }
                postShareAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
