package com.example.howtocook.adapter.postadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.howtocook.R;
import com.example.howtocook.model.basemodel.Share;
import com.example.howtocook.model.basemodel.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostShareAdapter extends RecyclerView.Adapter<PostShareAdapter.ViewHolder>{

    ArrayList<Share> listShare;
    Context context;

    public PostShareAdapter(ArrayList<Share> listShare) {
        this.listShare = listShare;
    }

    public PostShareAdapter(ArrayList<Share> listShare, Context context) {
        this.listShare = listShare;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_post_share, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        Share share = listShare.get(i);
        viewHolder.item_share_post_comment.setText(share.getShareContent());
        viewHolder.item_share_post_status.setText(share.getShareTime());
        Glide.with(context).load(share.getShareImg()).into(viewHolder.item_share_post_img);
        viewHolder.item_share_post_follow.setVisibility(View.GONE);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(share.getUserId());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users users = dataSnapshot.getValue(Users.class);
                viewHolder.item_share_post_user_name.setText(users.getFullName());
                if (context != null){
                    Glide.with(context).load(users.getUserImg()).into(viewHolder.item_share_post_user_ava);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return listShare.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView item_share_post_user_ava;
        TextView item_share_post_user_name;
        TextView item_share_post_status;
        ToggleButton item_share_post_follow;
        TextView item_share_post_comment;
        ImageView item_share_post_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_share_post_follow = itemView.findViewById(R.id.item_share_post_follow);
            item_share_post_comment = itemView.findViewById(R.id.item_share_post_comment);
            item_share_post_img = itemView.findViewById(R.id.item_share_post_img);
            item_share_post_status = itemView.findViewById(R.id.item_share_post_status);
            item_share_post_user_ava = itemView.findViewById(R.id.item_share_post_user_ava);
            item_share_post_user_name = itemView.findViewById(R.id.item_share_post_user_name);
        }
    }
}
