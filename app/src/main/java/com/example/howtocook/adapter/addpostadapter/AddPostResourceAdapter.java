package com.example.howtocook.adapter.addpostadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.howtocook.R;
import com.example.howtocook.adapter.MainPersonalPostAdapter;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddPostResourceAdapter extends RecyclerView.Adapter<AddPostResourceAdapter.ViewHolder>{

    ArrayList<String> list;
    private MainPersonalPostAdapter.OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
        void onImageClick(ImageView image, int position);
        void onAvaClick(CircleImageView imageView, int pos);
        void onButtonClick(Button button, int pos);
        void onTextViewClick(TextView textView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(MainPersonalPostAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public AddPostResourceAdapter(ArrayList<String> list, MainPersonalPostAdapter.OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    public AddPostResourceAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_add_post_resource, viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        EditText item_add_post_resource_name;
        EditText item_add_post_resource_weight;
        ImageView item_add_post_delete_resource;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_add_post_resource_name = itemView.findViewById(R.id.item_add_post_resource_name);
            item_add_post_resource_weight = itemView.findViewById(R.id.item_add_post_resource_weight);
            item_add_post_delete_resource = itemView.findViewById(R.id.item_add_post_delete_resource);



        }
    }
}
