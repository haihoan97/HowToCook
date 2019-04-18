package com.example.howtocook.uis.acticities;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.howtocook.R;
import com.example.howtocook.adapter.MainViewPagerAdapter;
import com.example.howtocook.model.UserNotification;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private View notificationViewBadge;
    private TextView notificationBadge;

    private ArrayList<UserNotification> listNoti;
    private MainViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.viewpager);

        viewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        //add listener for viewPager
        viewPager.setOnPageChangeListener(onPageChangeListener);

        //add listener for bottomNavigationView change fragment when select menu item
        bottomNavigationView.setOnNavigationItemSelectedListener(itemSelectedListener);
        addNotificationBadge(2);

    }

    public void addNotificationBadge(int noti){
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(2);

        notificationViewBadge = (View) LayoutInflater.from(this).inflate(R.layout.view_notification_badge, menuView,false);
        notificationBadge = notificationViewBadge.findViewById(R.id.notification_badge);
        notificationBadge.setText(String.valueOf(noti));
        itemView.addView(notificationViewBadge);
    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            bottomNavigationView.getMenu().getItem(i).setChecked(true);
            if (i==2){
                notificationViewBadge.setVisibility(View.GONE);
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
                    return true;
                case R.id.main_search:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.main_notification:
                    viewPager.setCurrentItem(2);
                    notificationViewBadge.setVisibility(View.GONE);
                    return true;
                case R.id.main_setting:
                    viewPager.setCurrentItem(3);
                    return true;


            }
            return false;
        }
    };
}
