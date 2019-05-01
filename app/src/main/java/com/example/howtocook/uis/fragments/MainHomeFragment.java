package com.example.howtocook.uis.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.howtocook.R;
import com.example.howtocook.adapter.ShowPostAdapter;
import com.example.howtocook.adapter.ShowSmallPostAdapter;
import com.example.howtocook.adapter.TheoDoiAdapter;
import com.example.howtocook.uis.acticities.MainActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainHomeFragment extends Fragment {

    private RecyclerView home_what_eat_today;
    private RecyclerView home_special_post;
    private RecyclerView home_top_user;


    private ShowSmallPostAdapter postAdapter;
    private ShowSmallPostAdapter postAdapter2;
    private TheoDoiAdapter theoDoiAdapter;

    private ArrayList<String> test;

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
        test = new ArrayList<>();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                postAdapter = new ShowSmallPostAdapter();
                RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
                home_what_eat_today.setLayoutManager(manager);
                home_what_eat_today.setAdapter(postAdapter);
            }
        });

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                postAdapter2 = new ShowSmallPostAdapter();
                RecyclerView.LayoutManager manager2 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
                home_special_post.setLayoutManager(manager2);
                home_special_post.setAdapter(postAdapter2);
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                initTopUserArray();
                theoDoiAdapter = new TheoDoiAdapter(test, getContext());
                RecyclerView.LayoutManager topUserManager = new LinearLayoutManager(getActivity());
                home_top_user.setLayoutManager(topUserManager);
                home_top_user.setAdapter(theoDoiAdapter);
            }
        });

        t.start();
        t1.start();
        t2.start();



    }

    public void initTopUserArray(){
        test.add("Hoi");
        test.add("Hiep");
        test.add("Lam");
    }

}
