package com.example.howtocook.uis.fragments.personal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.howtocook.R;
import com.example.howtocook.adapter.personalpage.TheoDoiAdapter;
import com.example.howtocook.model.TopUserFollow;
import com.example.howtocook.model.basemodel.Follow;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TheoDoiFragment extends Fragment {

    ArrayList<TopUserFollow> arr;
    RecyclerView recyclerView;
    TheoDoiAdapter theoDoiAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_theo_doi,container,false);
        arr = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);
        theoDoiAdapter = new TheoDoiAdapter(arr, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(theoDoiAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

}
