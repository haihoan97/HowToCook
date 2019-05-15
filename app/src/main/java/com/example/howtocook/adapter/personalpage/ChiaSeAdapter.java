package com.example.howtocook.adapter.personalpage;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.howtocook.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChiaSeAdapter  extends RecyclerView.Adapter<ChiaSeAdapter.ChiaSeHolder>{
    private ArrayList<String> arr;

    public ChiaSeAdapter(ArrayList<String> arr) {
        this.arr = arr;
    }

    @NonNull
    @Override
    public ChiaSeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_recyclerview_chia_se, viewGroup, false);
        return new ChiaSeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiaSeHolder chiaSeHolder, int i) {
        chiaSeHolder.textViewUsername.setText(arr.get(i));
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class ChiaSeHolder extends RecyclerView.ViewHolder{
        ImageView imageViewAvatar;
        TextView textViewMota, textViewTieuDe, textViewUsername;
        CircleImageView circleImageViewAvatar;
        public ChiaSeHolder(@NonNull View itemView) {
            super(itemView);
            imageViewAvatar = itemView.findViewById(R.id.imageView_avatar);
            textViewTieuDe = itemView.findViewById(R.id.textView_tieu_de);
            textViewMota = itemView.findViewById(R.id.textView_mo_ta);
            textViewUsername = itemView.findViewById(R.id.textView_username);
            circleImageViewAvatar = itemView.findViewById(R.id.circleImageView_avatar);
        }
    }
}
