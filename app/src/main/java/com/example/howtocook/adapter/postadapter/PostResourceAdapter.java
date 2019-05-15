package com.example.howtocook.adapter.postadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.howtocook.R;
import com.example.howtocook.model.basemodel.Prepare;

import java.util.ArrayList;

public class PostResourceAdapter extends RecyclerView.Adapter<PostResourceAdapter.ViewHolder>{

    ArrayList<Prepare> listResource;

    public PostResourceAdapter(ArrayList<Prepare> listResource) {
        this.listResource = listResource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v= inflater.inflate(R.layout.item_post_resources, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Prepare prepare = listResource.get(i);
        viewHolder.item_post_resource_name.setText(prepare.getSourceName());
        viewHolder.item_post_quantity.setText(prepare.getSourceAmount());
    }

    @Override
    public int getItemCount() {
        return listResource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ToggleButton item_post_add_resource;
        TextView item_post_resource_name;
        TextView item_post_quantity;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_post_add_resource = itemView.findViewById(R.id.item_post_add_resource);
            item_post_resource_name = itemView.findViewById(R.id.item_post_resource_name);
            item_post_quantity = itemView.findViewById(R.id.item_post_quantity);
        }
    }
}
