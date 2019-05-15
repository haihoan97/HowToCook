package com.example.howtocook.uis.acticities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.example.howtocook.R;
import com.example.howtocook.adapter.MainSearchTextAdapter;
import com.example.howtocook.adapter.MainViewPagerAdapter;
import com.example.howtocook.model.basemodel.Search;
import com.example.howtocook.model.basemodel.UserNotification;
import com.example.howtocook.model.basemodel.Users;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private View notificationViewBadge;
    private TextView notificationBadge;
    private Toolbar main_toolbar;
    private LinearLayout main_search_view;
    private RecyclerView main_search_recycle_text;
    private LinearLayout item_search_winter;
    private LinearLayout item_search_spring;
    private LinearLayout item_search_summer;
    private LinearLayout item_search_autumn;

    private ArrayList<UserNotification> listNoti;
    private ArrayList<Search> searchList;
    private MainViewPagerAdapter viewPagerAdapter;
    private MainSearchTextAdapter searchTextAdapter;

    private Profile profile;
    private AccessToken accessToken;
    private Users currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.viewpager);
        main_toolbar = findViewById(R.id.main_toolbar);
        main_search_recycle_text = findViewById(R.id.main_search_recycle_text);
        item_search_winter = findViewById(R.id.item_search_winter);
        item_search_spring = findViewById(R.id.item_search_spring);
        item_search_summer = findViewById(R.id.item_search_summer);
        item_search_autumn = findViewById(R.id.item_search_autumn);
        main_search_view = findViewById(R.id.main_search_view);


        //viewpager
        viewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(4);

        //toolbar
        setSupportActionBar(main_toolbar);


        //add listener for viewPager
        viewPager.setOnPageChangeListener(onPageChangeListener);

        //add listener for bottomNavigationView change fragment when select menu item
        bottomNavigationView.setOnNavigationItemSelectedListener(itemSelectedListener);
        addNotificationBadge(2);

        //Add item search view
        searchList = new ArrayList<>();
        initSearchContent();
        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 2);
        main_search_recycle_text.setLayoutManager(manager);
        searchTextAdapter = new MainSearchTextAdapter(searchList);
        main_search_recycle_text.setAdapter(searchTextAdapter);

        //search click
        item_search_autumn.setOnClickListener(this);
        item_search_summer.setOnClickListener(this);
        item_search_spring.setOnClickListener(this);
        item_search_winter.setOnClickListener(this);


    }


    public void addNotificationBadge(int noti){
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(2);

        notificationViewBadge = (View) LayoutInflater.from(this).inflate(R.layout.view_notification_badge, menuView,false);
        notificationBadge = notificationViewBadge.findViewById(R.id.notification_badge);
        notificationBadge.setText(String.valueOf(noti));
        itemView.addView(notificationViewBadge);
    }

    public void initSearchContent(){
        for (int i = 0; i < 5; i++){
            Search s = new Search("", "Ga ran "+i,  "");
            searchList.add(s);
        }

    }

    public void openSearchActivity( String season){
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        intent.putExtra("season", season);
        startActivity(intent);

    }





    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            bottomNavigationView.getMenu().getItem(i).setChecked(true);

            switch (i){
                case 0:
                    main_toolbar.setTitle("Home");
                    main_toolbar.setNavigationIcon(R.drawable.ic_home_black_24dp);
                    break;
                case 1:
                    main_toolbar.setTitle("Bai viet");
                    main_toolbar.setNavigationIcon(R.drawable.ic_post_black_24dp);
                    break;
                case 2:
                    main_toolbar.setTitle("Thong bao");
                    main_toolbar.setNavigationIcon(R.drawable.ic_notifications_black_24dp);
                    notificationViewBadge.setVisibility(View.GONE);
                    break;
                case 3:
                    main_toolbar.setTitle("Ca nhan");
                    main_toolbar.setNavigationIcon(R.drawable.ic_account_circle_black_24dp);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.main_home:
                    viewPager.setCurrentItem(0);
                    main_toolbar.setTitle("Home");
                    main_toolbar.setNavigationIcon(R.drawable.ic_home_black_24dp);
                    return true;
                case R.id.main_personal_post:
                    viewPager.setCurrentItem(1);
                    main_toolbar.setTitle("Bai viet");
                    main_toolbar.setNavigationIcon(R.drawable.ic_post_black_24dp);
                    return true;
                case R.id.main_notification:
                    viewPager.setCurrentItem(2);
                    main_toolbar.setTitle("Thong bao");
                    main_toolbar.setNavigationIcon(R.drawable.ic_notifications_black_24dp);
                    notificationViewBadge.setVisibility(View.GONE);
                    return true;
                case R.id.main_setting:
                    viewPager.setCurrentItem(3);
                    main_toolbar.setTitle("Ca nhan");
                    main_toolbar.setNavigationIcon(R.drawable.ic_account_circle_black_24dp);
                    return true;


            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menu_item = menu.findItem(R.id.menu_bt_search);


        menu_item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                SearchView search_view = (SearchView) item.getActionView();
                item.setActionView(search_view);
                main_search_view.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.GONE);
                bottomNavigationView.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "OpenSearch", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(MainActivity.this, "closeView", Toast.LENGTH_SHORT).show();
                main_search_view.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);
                bottomNavigationView.setVisibility(View.VISIBLE);
                return true;
            }
        });


        return true;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_search_spring:
                openSearchActivity("mùa xuân");
                break;
            case R.id.item_search_autumn:
                openSearchActivity("mùa thu");
                break;
            case R.id.item_search_summer:
                openSearchActivity("mùa hè");
                break;
            case R.id.item_search_winter:
                openSearchActivity("mùa đông");
                break;
        }

    }

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
        super.onBackPressed();
    }
}
