package com.example.howtocook.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.howtocook.R;
import com.example.howtocook.model.Search;

import java.util.ArrayList;

public class MainSearchTextAdapter extends RecyclerView.Adapter<MainSearchTextAdapter.ViewHolder>{

    private ArrayList<Search> searchList = new ArrayList<>();

    public MainSearchTextAdapter(ArrayList<Search> searchList) {
        this.searchList = searchList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_main_search_text, viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Search search = searchList.get(i);

    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView main_search_text;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            main_search_text = (TextView) itemView.findViewById(R.id.item_main_search_content);
        }
    }


}
