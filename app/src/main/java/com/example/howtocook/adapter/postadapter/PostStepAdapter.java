package com.example.howtocook.adapter.postadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.howtocook.R;
import com.example.howtocook.model.basemodel.Images;
import com.example.howtocook.model.basemodel.PostStep;

import java.util.ArrayList;

public class PostStepAdapter extends RecyclerView.Adapter<PostStepAdapter.ViewHolder>{

    ArrayList<PostStep> list;
    private Context context;

    public PostStepAdapter(ArrayList<PostStep> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_post_step, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        PostStep content = list.get(i);
        ArrayList<Images> listImg = new ArrayList<>() ;
        //listImg = content.getListImage();
        listImg.add(new Images("","","https://food.fnr.sndimg.com/content/dam/images/food/fullset/2018/10/4/1/BX1501_Roast-Filet-of-Beef_s4x3.jpg.rend.hgtvcom.826.620.suffix/1538677423309.jpeg",""));
        listImg.add(new Images("","","https://ecs7.tokopedia.net/img/cache/700/product-1/2018/1/9/250052322/250052322_876c060d-4241-4dbe-9e74-c4b57d53cec3_943_494.jpg",""));
        listImg.add(new Images("","","https://firebasestorage.googleapis.com/v0/b/howtocook-61c13.appspot.com/o?name=postava%2F1557431966851.jpeg&uploadType=resumable&upload_id=AEnB2Upw8PHV20NYa_fKfDxbonKmoleXbbIW11O71RZacM8l4FH4LhmRmCwNYnZWNN1pcWIrZ0w8A8kTfQkePhXw6Cff_qUHFQ&upload_protocol=resumable",""));
        ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(listImg, context, ImageView.ScaleType.CENTER_CROP );
        viewHolder.post_step_view_img.setAdapter(imagePagerAdapter);
        viewHolder.post_step_step.setText("Buoc "+content.getStep());
        viewHolder.post_step_content.setText(content.getStepContent());
        viewHolder.post_step_count_img.setText((viewHolder.post_step_view_img.getCurrentItem() + 1)+"/"+listImg.size());

        final int count = listImg.size();
        viewHolder.post_step_view_img.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                viewHolder.post_step_count_img.setText((i+ 1)+"/"+count);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ViewPager post_step_view_img;
        TextView post_step_step;
        TextView post_step_content;
        TextView post_step_count_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            post_step_view_img = itemView.findViewById(R.id.post_step_view_img);
            post_step_step = itemView.findViewById(R.id.post_step_step);
            post_step_content = itemView.findViewById(R.id.post_step_content);
            post_step_count_img = itemView.findViewById(R.id.post_step_count_img);
        }
    }
}
