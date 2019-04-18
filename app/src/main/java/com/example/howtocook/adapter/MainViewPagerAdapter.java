package com.example.howtocook.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.howtocook.model.UserNotification;
import com.example.howtocook.uis.fragments.MainHomeFragment;
import com.example.howtocook.uis.fragments.MainNotificationFragment;
import com.example.howtocook.uis.fragments.MainSearchFragment;
import com.example.howtocook.uis.fragments.MainSettingFragment;

import java.util.ArrayList;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new MainHomeFragment();

            case 1:
                return new MainSearchFragment();

            case 2:
                return new MainNotificationFragment();

            case 3:
                return new MainSettingFragment();

            default:
                return new MainHomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
