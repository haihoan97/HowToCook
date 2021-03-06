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

public class TheoDoiAdapter extends RecyclerView.Adapter<TheoDoiAdapter.TheoDoiAdapterHolder> {

    private ArrayList<TopUserFollow> arr;
    private Context context;

    public TheoDoiAdapter(ArrayList<TopUserFollow> arr, Context context) {
        this.arr = arr;
        this.context = context;
    }

    @NonNull
    @Override
    public TheoDoiAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate( R.layout.item_recyclerview_theodoi, viewGroup, false);
        return new TheoDoiAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TheoDoiAdapterHolder theoDoiAdapterHolder, int i) {

        Users users = arr.get(i).getUsers();
        ArrayList<Follow> list = arr.get(i).getListIsFollowed();
        theoDoiAdapterHolder.button_follow.setVisibility(View.GONE);
        if (users!= null){
            theoDoiAdapterHolder.textViewUsername.setText(users.getFullName());
            if (context != null){
                Glide.with(context).load(users.getUserImg()).into(theoDoiAdapterHolder.circleImageView);
            }else{

            }
            DatabaseReference database_reference = FirebaseDatabase.getInstance().getReference("Post");
            database_reference.orderByChild("userId").equalTo(users.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    theoDoiAdapterHolder.textViewCongThuc.setText(dataSnapshot.getChildrenCount() + " bài viết");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Loi", "Khong  the lay ve thong tin user");
                }
            });

        }
        if (list != null){
            theoDoiAdapterHolder.textViewQuanTam.setText(list.size()+" người theo dõi");
        }

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class TheoDoiAdapterHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView textViewUsername, textViewCongThuc, textViewQuanTam;
        ToggleButton button_follow;

        public TheoDoiAdapterHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.circleImageView);
            textViewUsername = itemView.findViewById(R.id.textview_username);
            textViewCongThuc = itemView.findViewById(R.id.textview_cong_thuc);
            textViewQuanTam = itemView.findViewById(R.id.textview_quan_tam);
            button_follow = itemView.findViewById(R.id.button_follow);
        }
    }
}
