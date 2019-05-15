package com.example.howtocook.adapter.postadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.howtocook.R;
import com.example.howtocook.model.basemodel.Comments;
import com.example.howtocook.model.basemodel.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostCommentAdapter extends RecyclerView.Adapter<PostCommentAdapter.ViewHolder>{

    ArrayList<Comments> listComment;
    Context context;

    public PostCommentAdapter(ArrayList<Comments> listComment) {
        this.listComment = listComment;
    }

    public PostCommentAdapter(ArrayList<Comments> listComment, Context context) {
        this.listComment = listComment;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_post_comment, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        Comments comment = listComment.get(i);
        //viewHolder.post_comment_user_name.setText(comment.getUser().getFullName());
        viewHolder.post_comment_time.setText(comment.getCommentTime());
        viewHolder.post_comment_ratingBar.setRating(comment.getRating());
        viewHolder.post_comment_user_comment.setText(comment.getContent());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(comment.getUserId());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users users = dataSnapshot.getValue(Users.class);
                viewHolder.post_comment_user_name.setText(users.getFullName());
                if (context != null){
                    Glide.with(context).load(users.getUserImg()).into(viewHolder.post_comment_user_ava);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listComment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView post_comment_user_ava;
        TextView post_comment_user_name;
        TextView post_comment_user_comment;
        RatingBar post_comment_ratingBar;
        TextView post_comment_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            post_comment_ratingBar = itemView.findViewById(R.id.post_comment_ratingBar);
            post_comment_time = itemView.findViewById(R.id.post_comment_time);
            post_comment_user_ava = itemView.findViewById(R.id.post_comment_user_ava);
            post_comment_user_comment = itemView.findViewById(R.id.post_comment_user_comment);
            post_comment_user_name = itemView.findViewById(R.id.post_comment_user_name);
        }
    }
}
