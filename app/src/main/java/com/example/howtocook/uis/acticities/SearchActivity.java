package com.example.howtocook.uis.acticities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.howtocook.R;
import com.example.howtocook.adapter.ShowSearchPostAdapter;
import com.example.howtocook.model.basemodel.Post;
import com.example.howtocook.utils.HeartAnimation;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner search_spinner;
    private Button search_filter;
    private TextView search_tv_content;
    private RecyclerView search_recycle;
    String searchContent = "no";
    String season = "no";

    private ShowSearchPostAdapter postAdapter;
    ArrayList<Post> listPost;

    HeartAnimation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //init
        search_spinner = findViewById(R.id.search_spinner);
        search_filter = findViewById(R.id.search_filter);
        search_tv_content = findViewById(R.id.search_tv_content);
        search_recycle = findViewById(R.id.search_recycle);

        animation = new HeartAnimation();

        listPost = new ArrayList<>();

        //init Spinner
        initSpinner();

        //get search Content
        Intent intent = getIntent();
        searchContent = intent.getStringExtra("searchContent");
        season = intent.getStringExtra("season");

        if (!season.equals("no")){
            search_tv_content.setText("Tim kiem: "+season);
        }else{
            search_tv_content.setText("Tim kiem: "+searchContent);
        }

        //init Recycle View

        postAdapter = new ShowSearchPostAdapter(listPost, new ShowSearchPostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {

            }

            @Override
            public void onLikeButtonClick(ToggleButton button, boolean isCheck) {
                if (isCheck){
                    animation.animationBigger(button);
                }
            }
        });
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        search_recycle.setLayoutManager(manager);
        search_recycle.setAdapter(postAdapter);

        search_filter.setOnClickListener(this);
        initData();

    }

    public void initData(){
        }

    public void initSpinner(){
        ArrayList<String> list = new ArrayList<>();
        list.add("lượt xem");
        list.add("lượt tải");
        list.add("lượt thích");
        list.add("lượt chia sẻ");

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        search_spinner.setAdapter(adp1);
    }

    

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_filter:
                final AlertDialog.Builder alert = new AlertDialog.Builder(this);
//                LayoutInflater inflater = getLayoutInflater();
//                View view = inflater.inflate(R.layout.item_post_filter);
                alert.setView(R.layout.item_post_filter);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();

        }
    }
}
