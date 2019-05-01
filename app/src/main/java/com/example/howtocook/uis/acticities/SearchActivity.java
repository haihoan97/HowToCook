package com.example.howtocook.uis.acticities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.howtocook.R;
import com.example.howtocook.adapter.ShowPostAdapter;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private Spinner search_spinner;
    private Button search_filter;
    private TextView search_tv_content;
    private RecyclerView search_recycle;
    String searchContent = "no";
    String season = "no";

    private ShowPostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //init
        search_spinner = findViewById(R.id.search_spinner);
        search_filter = findViewById(R.id.search_filter);
        search_tv_content = findViewById(R.id.search_tv_content);
        search_recycle = findViewById(R.id.search_recycle);

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

        postAdapter = new ShowPostAdapter();
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        search_recycle.setLayoutManager(manager);
        search_recycle.setAdapter(postAdapter);

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
}
