package com.example.howtocook.uis.fragments.post;


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
import com.example.howtocook.model.basemodel.PostStep;
import com.example.howtocook.model.basemodel.Prepare;

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
    public PostStepFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        post_step_recycle = view.findViewById(R.id.post_step_recycle);
        post_step_recycle_resources = view.findViewById(R.id.post_step_recycle_resources);


        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        post_step_recycle.setLayoutManager(manager);
        listStep = new ArrayList<>();
        listResource = new ArrayList<>();

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


        postStepAdapter.notifyDataSetChanged();
    }

    public void initListResource(){

        resourceAdapter.notifyDataSetChanged();
    }

}
