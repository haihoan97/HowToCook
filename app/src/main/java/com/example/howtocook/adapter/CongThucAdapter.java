package com.example.howtocook.adapter;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.howtocook.R;

import java.util.ArrayList;

public class CongThucAdapter extends RecyclerView.Adapter<CongThucAdapter.CongThucHolder>{
    //private ArrayList<Post> arr;
    private ArrayList<String> arr;

    public CongThucAdapter(ArrayList<String> arr) {
        this.arr = arr;
    }

    @NonNull
    @Override
    public CongThucHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_recyclerview_cong_thuc, viewGroup, false);
        return new CongThucHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CongThucHolder congThucHolder, int i) {
        //congThucHolder.textViewTieuDe.setText(arr.get(i).getTitle());
        congThucHolder.textViewTieuDe.setText(arr.get(i));
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class CongThucHolder extends RecyclerView.ViewHolder{
        private ImageView imageViewAvatar;
        private TextView textViewTieuDe;
        public CongThucHolder(@NonNull View itemView) {
            super(itemView);
            imageViewAvatar = itemView.findViewById(R.id.imageView_avatar);
            textViewTieuDe = itemView.findViewById(R.id.textView_tieu_de);
        }
    }
}
