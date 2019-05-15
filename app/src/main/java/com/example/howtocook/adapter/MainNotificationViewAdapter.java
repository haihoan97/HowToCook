package com.example.howtocook.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.howtocook.R;
import com.example.howtocook.model.basemodel.UserNotification;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainNotificationViewAdapter extends RecyclerView.Adapter<MainNotificationViewAdapter.ViewHolder>{

    private ArrayList<UserNotification> userNotifications;

    public MainNotificationViewAdapter(ArrayList<UserNotification> userNotifications) {
        this.userNotifications = userNotifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_notification, viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        UserNotification userNotification = userNotifications.get(i);
        viewHolder.item_noti_user_name.setText(userNotification.getUser()+" ");
        viewHolder.item_noti_time.setText(String.valueOf(userNotification.getNotiDate().getDate()+" thang "+(userNotification.getNotiDate().getMonth()+1)+" luc "+userNotification.getNotiDate().getHours()+":"+userNotification.getNotiDate().getMinutes()));
        viewHolder.item_noti_status.setText(userNotification.getNotiType());
        viewHolder.item_noti_post_name.setText(Html.fromHtml(userNotification.getContent()));
        if (!userNotification.getIschecked()){
            viewHolder.item_noti_card.setCardBackgroundColor(Color.parseColor("#dee1ed"));

        }else{
            viewHolder.item_noti_card.setCardBackgroundColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public int getItemCount() {
        return userNotifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView item_noti_card;
        CircleImageView item_noti_ava;
        TextView item_noti_user_name;
        TextView item_noti_status;
        TextView item_noti_post_name;
        TextView item_noti_time;
        ImageView item_noti_img_post;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_noti_card = itemView.findViewById(R.id.item_noti_card);
            item_noti_ava = itemView.findViewById(R.id.item_noti_ava);
            item_noti_img_post = itemView.findViewById(R.id.item_noti_img_post);
            item_noti_post_name = itemView.findViewById(R.id.item_noti_post_name);
            item_noti_user_name = itemView.findViewById(R.id.item_noti_user_name);
            item_noti_status = itemView.findViewById(R.id.item_noti_status);
            item_noti_time = itemView.findViewById(R.id.item_noti_time);
        }
    }
}
