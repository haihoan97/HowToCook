package com.example.howtocook.adapter.personalpage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.howtocook.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TheoDoiAdapter extends RecyclerView.Adapter<TheoDoiAdapter.TheoDoiAdapterHolder> {

    private ArrayList<String> arr;
    private Context context;

    public TheoDoiAdapter(ArrayList<String> arr, Context context) {
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
    public void onBindViewHolder(@NonNull TheoDoiAdapterHolder theoDoiAdapterHolder, int i) {
        theoDoiAdapterHolder.textViewUsername.setText(arr.get(i));
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
