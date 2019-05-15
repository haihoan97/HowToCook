package com.example.howtocook.adapter.personalpage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.howtocook.R;
import com.example.howtocook.model.TopUserFollow;
import com.example.howtocook.model.basemodel.Follow;
import com.example.howtocook.model.basemodel.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.ViewHolder>{

    private ArrayList<Follow> list;
    private Context context;

    public FollowAdapter(ArrayList<Follow> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate( R.layout.item_recyclerview_theodoi, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        Follow follow = list.get(i);
        viewHolder.textViewQuanTam.setText("");
        viewHolder.textViewQuanTam.setText("");
        final String userId = follow.getUserIsFollowed();

        DatabaseReference database_reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        database_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users users = dataSnapshot.getValue(Users.class);

                if (context!= null){
                    Glide.with(context).load(users.getUserImg()).into(viewHolder.circleImageView);
                }
                viewHolder.textViewUsername.setText(users.getFullName());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Loi", "Khong  the lay ve thong tin user");
            }
        });

        DatabaseReference database_reference2 = FirebaseDatabase.getInstance().getReference("Post");
        database_reference2.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                viewHolder.textViewCongThuc.setText(dataSnapshot.getChildrenCount() + " công thức");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Loi", "Khong  the lay ve thong tin user");
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView textViewUsername, textViewCongThuc, textViewQuanTam;
        ToggleButton button_follow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.circleImageView);
            textViewUsername = itemView.findViewById(R.id.textview_username);
            textViewCongThuc = itemView.findViewById(R.id.textview_cong_thuc);
            textViewQuanTam = itemView.findViewById(R.id.textview_quan_tam);
            button_follow = itemView.findViewById(R.id.button_follow);
        }
    }
}
