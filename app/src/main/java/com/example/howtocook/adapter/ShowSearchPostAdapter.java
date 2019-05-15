package com.example.howtocook.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.howtocook.R;
import com.example.howtocook.model.basemodel.Likes;
import com.example.howtocook.model.basemodel.Post;
import com.example.howtocook.model.basemodel.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowSearchPostAdapter extends RecyclerView.Adapter<ShowSearchPostAdapter.ViewHolder>{


    ArrayList<Post> list;

    Context context;
    private OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView);
        void onLikeButtonClick(View itemView, ToggleButton button, boolean isCheck);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ShowSearchPostAdapter(ArrayList<Post> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    public ShowSearchPostAdapter(ArrayList<Post> list, Context context, OnItemClickListener listener) {
        this.list = list;
        this.context = context;
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

        final Post post = list.get(i);
        viewHolder.item_search_post_name.setText(post.getPostName());
        if(context != null){
            Glide.with(context).load(post.getPostImage()).into(viewHolder.item_search_post_ava);
        }
        viewHolder.item_search_post_count_like.setText(post.getView() + " lượt xem và " + post.getCountLike() + " lượt thích");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference  = FirebaseDatabase.getInstance().getReference("Likes");
        reference.orderByChild("userId").equalTo(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Likes likes = snapshot.getValue(Likes.class);
                    if (likes.getPostId().equals(post.getPostId())){
                        viewHolder.item_search_post_btn_like.setChecked(true);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference reference2  = FirebaseDatabase.getInstance().getReference("Users").child(post.getUserId());
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users users = dataSnapshot.getValue(Users.class);
                viewHolder.item_search_post_user_name.setText(users.getFullName());
                if (context!= null){
                    Glide.with(context).load(users.getUserImg()).into(viewHolder.item_search_post_user_ava);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            item_search_post_ava = itemView.findViewById(R.id.item_search_post_ava);
            item_search_post_user_name = itemView.findViewById(R.id.item_search_post_user_name);
            item_search_post_name = itemView.findViewById(R.id.item_search_post_name);
            item_search_post_btn_like = itemView.findViewById(R.id.item_search_post_btn_like);
            item_search_post_user_ava = itemView.findViewById(R.id.item_search_post_user_ava);
            item_search_post_follow = itemView.findViewById(R.id.item_search_post_follow);
            item_search_post_count_like = itemView.findViewById(R.id.item_search_post_count_like);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView);
                }
            });

            item_search_post_btn_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onLikeButtonClick(itemView, item_search_post_btn_like, item_search_post_btn_like.isChecked());
                }
            });

        }
    }
}
