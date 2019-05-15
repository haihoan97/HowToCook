package com.example.howtocook.adapter.postadapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.howtocook.R;
import com.example.howtocook.model.basemodel.Images;

import java.util.ArrayList;

public class ImagePagerUriAdapter extends PagerAdapter {

    private ArrayList<Images> listImg;
    private Context context;
    private LayoutInflater inflater;
    private ImageView.ScaleType scaleType;

    public ImagePagerUriAdapter(ArrayList<Images> listImg, Context context, ImageView.ScaleType scaleType) {
        this.listImg = listImg;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.scaleType = scaleType;
    }

    @Override
    public int getCount() {
        return listImg.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == ((RelativeLayout) o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = inflater.inflate(R.layout.item_personalpost_image, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.item_personal_img);
        final ProgressBar progressBar = itemView.findViewById(R.id.item_personal_progressbar);
        imageView.setScaleType(scaleType);
        Uri uri = Uri.parse(listImg.get(position).getImgLink());
        imageView.setImageURI(uri);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public void restoreState(@Nullable Parcelable state, @Nullable ClassLoader loader) {
        super.restoreState(state, loader);
    }

    @Nullable
    @Override
    public Parcelable saveState() {
        return super.saveState();
    }
}
