package com.example.howtocook.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.howtocook.R;
import com.example.howtocook.model.PersonalPost;
import com.example.howtocook.model.basemodel.Follow;
import com.example.howtocook.model.basemodel.Likes;
import com.example.howtocook.model.basemodel.Post;
import com.example.howtocook.model.basemodel.UserNotification;
import com.example.howtocook.model.basemodel.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowSmallPostAdapter extends RecyclerView.Adapter<ShowSmallPostAdapter.ViewHolder> {

    ArrayList<PersonalPost> list;
    Context context;
    private OnItemClickListener listener;
    String Uid;


    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView);

        void onLikeButtonClick(View itemView, ToggleButton button, boolean isCheck);
        void onFollowButtonClick(View itemView, ToggleButton button, boolean isCheck);
    }

    public void setListener(ShowSmallPostAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public ShowSmallPostAdapter(String Uid, ArrayList<PersonalPost> list, Context context, OnItemClickListener listener) {
        this.Uid = Uid;
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_small_post, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        PersonalPost personalPost = list.get(i);
        final Post post = personalPost.getPost();
        Users users = personalPost.getUsers();

        if (users != null) {
            viewHolder.item_small_post_user_name.setText(users.getFullName());
            if (context != null) {
                Glide.with(context).load(users.getUserImg()).into(viewHolder.item_small_post_user_ava);
            }
        }


        viewHolder.item_small_post_name.setText(post.getPostName());
        viewHolder.item_small_post_count_like.setText(post.getView() + " lượt xem và " + post.getCountLike() + " lượt thích");
        if (context != null) {
            Glide.with(context).load(post.getPostImage()).into(viewHolder.item_small_post_img);
        }

        //Init LikeButton
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference database_reference = firebaseDatabase.getReference("Likes");
        database_reference.orderByChild("userId").equalTo(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Likes likes = snapshot.getValue(Likes.class);
                    if (likes.getPostId().equals(post.getPostId())){
                        viewHolder.item_small_post_like_btn.setChecked(true);
                        return;
                    }
                }
                viewHolder.item_small_post_like_btn.setChecked(false);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Loi", "Khong  the lay ve thong tin user");
            }
        });

        //Init Follow button

        final DatabaseReference follow_reference = firebaseDatabase.getReference("Follow");
        follow_reference.orderByChild("userFollow").equalTo(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Follow follow = snapshot.getValue(Follow.class);
                    if (follow.getUserIsFollowed().equals(post.getUserId())){
                        viewHolder.item_small_post_follow.setChecked(true);
                        return;
                    }
                }

                viewHolder.item_small_post_follow.setChecked(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Loi", "");
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView item_small_post_img;
        ToggleButton item_small_post_like_btn;
        TextView item_small_post_name;
        TextView item_small_post_count_like;
        CircleImageView item_small_post_user_ava;
        TextView item_small_post_user_name;
        ToggleButton item_small_post_follow;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            item_small_post_count_like = itemView.findViewById(R.id.item_small_post_count_like);
            item_small_post_img = itemView.findViewById(R.id.item_small_post_img);
            item_small_post_like_btn = itemView.findViewById(R.id.item_small_post_like_btn);
            item_small_post_name = itemView.findViewById(R.id.item_small_post_name);
            item_small_post_user_ava = itemView.findViewById(R.id.item_small_post_user_ava);
            item_small_post_user_name = itemView.findViewById(R.id.item_small_post_user_name);
            item_small_post_follow = itemView.findViewById(R.id.item_small_post_follow);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView);
                }
            });

            item_small_post_like_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onLikeButtonClick(itemView, item_small_post_like_btn, item_small_post_like_btn.isChecked());
                }
            });

            item_small_post_follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onFollowButtonClick(itemView, item_small_post_follow, item_small_post_follow.isChecked());
                }
            });
        }
    }
}
