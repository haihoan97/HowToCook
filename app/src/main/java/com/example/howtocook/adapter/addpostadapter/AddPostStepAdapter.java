package com.example.howtocook.adapter.addpostadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.howtocook.R;
import com.example.howtocook.adapter.MainPersonalPostAdapter;
import com.example.howtocook.model.basemodel.Images;
import com.example.howtocook.model.basemodel.PostStep;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddPostStepAdapter extends RecyclerView.Adapter<AddPostStepAdapter.ViewHolder>{

    ArrayList<PostStep> list;

    private OnItemClickListener listener;
    private Context context;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView);
        void onImageClick(View itemView, ImageView image);
        void onEditorClick(View itemView, EditText editText);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public AddPostStepAdapter(ArrayList<PostStep> list, Context context, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_add_post_step, viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {



        viewHolder.add_post_step_step.setText("Bước "+(i+1));
        viewHolder.add_post_step_des.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE){

                }
                return false;
            }
        });


        ArrayList<Images> listImg = list.get(i).getListImg();
        Uri uri;
        switch (listImg.size()){
            case 0:
                break;
            case 1:
                uri = Uri.parse(list.get(i).getListImg().get(0).getImgLink());
                viewHolder.add_post_step_img1.setImageURI(uri);
                break;
            case 2:
                uri  = Uri.parse(list.get(i).getListImg().get(0).getImgLink());
                viewHolder.add_post_step_img1.setImageURI(uri);
                uri  = Uri.parse(list.get(i).getListImg().get(1).getImgLink());
                viewHolder.add_post_step_img2.setImageURI(uri);

                break;
            case 3:
                uri  = Uri.parse(list.get(i).getListImg().get(0).getImgLink());
                viewHolder.add_post_step_img1.setImageURI(uri);
                uri  = Uri.parse(list.get(i).getListImg().get(1).getImgLink());
                viewHolder.add_post_step_img2.setImageURI(uri);
                uri  = Uri.parse(list.get(i).getListImg().get(2).getImgLink());
                viewHolder.add_post_step_img3.setImageURI(uri);
                break;
            case 4:
                uri  = Uri.parse(list.get(i).getListImg().get(0).getImgLink());
                viewHolder.add_post_step_img1.setImageURI(uri);
                uri  = Uri.parse(list.get(i).getListImg().get(1).getImgLink());
                viewHolder.add_post_step_img2.setImageURI(uri);
                uri  = Uri.parse(list.get(i).getListImg().get(2).getImgLink());
                viewHolder.add_post_step_img3.setImageURI(uri);
                uri  = Uri.parse(list.get(i).getListImg().get(3).getImgLink());
                viewHolder.add_post_step_img4.setImageURI(uri);
                break;
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView add_post_step_step;
        ImageView add_post_step_delete_step;
        EditText add_post_step_des;
        ImageView add_post_step_img;
        ImageView add_post_step_img1;
        ImageView add_post_step_img2;
        ImageView add_post_step_img3;
        ImageView add_post_step_img4;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            add_post_step_step = itemView.findViewById(R.id.add_post_step_step);
            add_post_step_delete_step = itemView.findViewById(R.id.add_post_step_delete_step);
            add_post_step_des = itemView.findViewById(R.id.add_post_step_des);
            add_post_step_img = itemView.findViewById(R.id.add_post_step_img);
            add_post_step_img1 = itemView.findViewById(R.id.add_post_step_img1);
            add_post_step_img2 = itemView.findViewById(R.id.add_post_step_img2);
            add_post_step_img3 = itemView.findViewById(R.id.add_post_step_img3);
            add_post_step_img4 = itemView.findViewById(R.id.add_post_step_img4);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView);
                }
            });

            add_post_step_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onImageClick(itemView, add_post_step_img);
                }
            });
            add_post_step_delete_step.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onImageClick(itemView, add_post_step_delete_step);
                }
            });

            add_post_step_des.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    listener.onEditorClick(itemView, add_post_step_des);
                    return false;
                }
            });


        }
    }
}
