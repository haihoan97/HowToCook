package com.example.howtocook.uis.fragments.personal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.howtocook.R;
import com.example.howtocook.adapter.ChiaSeAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChiaSeFragment extends Fragment {

    private ArrayList<String> arr;
    private RecyclerView recyclerView;
    private ChiaSeAdapter chiaSeAdapter;

    public ChiaSeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_chia, container, false);
       arr = new ArrayList<>();
       arr.add("Hoàn");
       arr.add("Hiệp");
       arr.add("lâm");
       recyclerView = view.findViewById(R.id.recyclerView);
       chiaSeAdapter = new ChiaSeAdapter(arr);
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
       recyclerView.setAdapter(chiaSeAdapter);
       recyclerView.setLayoutManager(linearLayoutManager);
       return view;
    }

}
