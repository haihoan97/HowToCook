package com.example.howtocook.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.howtocook.R;
import com.example.howtocook.model.basemodel.Post;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowSearchPostAdapter extends RecyclerView.Adapter<ShowSearchPostAdapter.ViewHolder>{


    ArrayList<Post> list;
    private OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
        void onLikeButtonClick(ToggleButton button, boolean isCheck);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ShowSearchPostAdapter(ArrayList<Post> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_search_post, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        Post post = list.get(i);
        viewHolder.item_search_post_name.setText(post.getPostName());

        viewHolder.item_search_post_btn_like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.onLikeButtonClick(viewHolder.item_search_post_btn_like, isChecked);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView item_search_post_user_name;
        TextView item_search_post_name;
        ImageView item_search_post_ava;
        ToggleButton item_search_post_btn_like;
        CircleImageView item_search_post_user_ava;
        ToggleButton item_search_post_follow;
        TextView item_search_post_count_like;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_search_post_ava = itemView.findViewById(R.id.item_search_post_ava);
            item_search_post_user_name = itemView.findViewById(R.id.item_search_post_user_name);
            item_search_post_name = itemView.findViewById(R.id.item_search_post_name);
            item_search_post_btn_like = itemView.findViewById(R.id.item_search_post_btn_like);
            item_search_post_user_ava = itemView.findViewById(R.id.item_search_post_user_ava);
            item_search_post_follow = itemView.findViewById(R.id.item_search_post_follow);
            item_search_post_count_like = itemView.findViewById(R.id.item_search_post_count_like);




        }
    }
}
