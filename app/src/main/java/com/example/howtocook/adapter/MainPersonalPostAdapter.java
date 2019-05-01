package com.example.howtocook.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.howtocook.R;
import com.example.howtocook.utils.HeartAnimation;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainPersonalPostAdapter extends RecyclerView.Adapter<MainPersonalPostAdapter.ViewHolder>{

    ArrayList<String> listtest = new ArrayList<>();

    public MainPersonalPostAdapter(ArrayList<String> listtest) {
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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        viewHolder.item_personal_post_name.setText(listtest.get(i));
        viewHolder.item_personal_post_like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    HeartAnimation a=new HeartAnimation();
                    a.animationBigger(buttonView);
                }
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
        Button item_personal_post_follow;
        ImageView item_personal_post_img;
        TextView item_personal_post_name;
        TextView item_personal_post_des;
        TextView item_personal_post_view_and_like;
        ToggleButton item_personal_post_like;
        Button item_personal_post_save;
        Button item_personal_post_view_comment;

        public ViewHolder(@NonNull View itemView) {
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


        }
    }
}
