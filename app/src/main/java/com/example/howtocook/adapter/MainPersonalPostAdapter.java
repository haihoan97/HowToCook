package com.example.howtocook.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.howtocook.model.basemodel.Users;
import com.example.howtocook.utils.HeartAnimation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainPersonalPostAdapter extends RecyclerView.Adapter<MainPersonalPostAdapter.ViewHolder>{

    private ArrayList<PersonalPost> listtest;
    private OnItemClickListener listener;
    private Context context;
    private String Uid;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView);

        void onLikeButtonClick(View itemView, ToggleButton button, boolean isCheck);
        void onFollowButtonClick(View itemView, ToggleButton button, boolean isCheck);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public MainPersonalPostAdapter(Context context,String Uid,  OnItemClickListener listener, ArrayList<PersonalPost> listtest) {
        this.context = context;
        this.Uid = Uid;
        this.listener = listener;
        this.listtest = listtest;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v= inflater.inflate(R.layout.item_user_post, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        PersonalPost personalPost = listtest.get(i);
        Users users = personalPost.getUsers();
        final Post post = personalPost.getPost();

        if (post != null){
            viewHolder.item_personal_post_name.setText(post.getPostName());
            Glide.with(context).load(post.getPostImage()).into(viewHolder.item_personal_post_img);
            viewHolder.item_personal_post_des.setText("");
            viewHolder.item_personal_post_view_and_like.setText(post.getView() + " lượt xem và " + post.getCountLike() + " lượt thích");
            viewHolder.item_personal_post_des.setText(post.getPostTime());
        }

        if (users != null){
            viewHolder.item_personal_post_user_name.setText(users.getFullName());
            if (context!= null){
                Glide.with(context).load(users.getUserImg()).into(viewHolder.item_personal_post_user_ava);
            }
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
                        viewHolder.item_personal_post_like.setChecked(true);
                        return;
                    }
                }
                viewHolder.item_personal_post_like.setChecked(false);

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
                        viewHolder.item_personal_post_follow.setChecked(true);
                        return;
                    }
                }

                viewHolder.item_personal_post_follow.setChecked(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Loi", "");
            }
        });

    }

    @Override
    public int getItemCount() {
        return listtest.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView item_personal_post_user_ava;
        TextView item_personal_post_user_name;
        TextView item_personal_post_status;
        ToggleButton item_personal_post_follow;
        ImageView item_personal_post_img;
        TextView item_personal_post_name;
        TextView item_personal_post_des;
        TextView item_personal_post_view_and_like;
        ToggleButton item_personal_post_like;
        Button item_personal_post_save;
        Button item_personal_post_view_comment;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            item_personal_post_user_ava = itemView.findViewById(R.id.item_personal_post_user_ava);
            item_personal_post_user_name = itemView.findViewById(R.id.item_personal_post_user_name);
            item_personal_post_status = itemView.findViewById(R.id.item_personal_post_status);
            item_personal_post_follow = itemView.findViewById(R.id.item_personal_post_follow);
            item_personal_post_img = itemView.findViewById(R.id.item_personal_post_img);
            item_personal_post_name = itemView.findViewById(R.id.item_personal_post_name);
            item_personal_post_des = itemView.findViewById(R.id.item_personal_post_des);
            item_personal_post_view_and_like = itemView.findViewById(R.id.item_personal_post_view_and_like);
            item_personal_post_like = itemView.findViewById(R.id.item_personal_post_like);
            item_personal_post_save = itemView.findViewById(R.id.item_personal_post_save);
            item_personal_post_view_comment  = itemView.findViewById(R.id.item_personal_post_view_comment);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView);
                }
            });

            item_personal_post_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onLikeButtonClick(itemView, item_personal_post_like, item_personal_post_like.isChecked());
                }
            });

            item_personal_post_follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onFollowButtonClick(itemView, item_personal_post_follow, item_personal_post_follow.isChecked());
                }
            });

        }
    }
}
