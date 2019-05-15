package com.example.howtocook.uis.acticities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.howtocook.R;
import com.example.howtocook.adapter.postadapter.PersonalPostViewPager;
import com.example.howtocook.utils.HeartAnimation;

import java.util.ArrayList;

public class PersonalPostActivity extends AppCompatActivity implements View.OnClickListener {

    TabLayout personal_post_tab;
    ViewPager personal_post_view_pager;
    ToggleButton personal_post_like;
    Button personal_post_view_comment;
    HeartAnimation animation;


    private PersonalPostViewPager viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_post);

        personal_post_tab = findViewById(R.id.personal_post_tab);
        personal_post_view_pager = findViewById(R.id.personal_post_view_pager);
        personal_post_like = findViewById(R.id.personal_post_like);
        personal_post_view_comment = findViewById(R.id.personal_post_view_comment);

        animation = new HeartAnimation();

        viewPagerAdapter = new PersonalPostViewPager(getSupportFragmentManager());
        personal_post_view_pager.setAdapter(viewPagerAdapter);
        personal_post_tab.setupWithViewPager(personal_post_view_pager);

        personal_post_view_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(personal_post_tab));
        personal_post_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                personal_post_view_pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        personal_post_like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    animation.animationBigger(personal_post_like);
                }
            }
        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personal_post_view_comment:
                personal_post_view_pager.setCurrentItem(1);
        }
    }
}
