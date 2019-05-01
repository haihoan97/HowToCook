package com.example.howtocook.uis.fragments;


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
import com.example.howtocook.adapter.MainPersonalPostAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPersonalPostFragment extends Fragment {

    RecyclerView main_personal_post_recycle;

    ArrayList<String> test;
    MainPersonalPostAdapter adapter;

    public MainPersonalPostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        main_personal_post_recycle = view.findViewById(R.id.main_personal_post_recycle);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        main_personal_post_recycle.setLayoutManager(manager);
        test = new ArrayList<>();
        for(int i = 1; i<5;i++){
            test.add("Bai "+i);
        }
        adapter = new MainPersonalPostAdapter(test);
        main_personal_post_recycle.setAdapter(adapter);
        main_personal_post_recycle.clearFocus();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main_personal_post, container, false);
        return v;
    }

}
