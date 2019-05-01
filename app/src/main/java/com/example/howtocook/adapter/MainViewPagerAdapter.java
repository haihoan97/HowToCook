package com.example.howtocook.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.howtocook.uis.fragments.MainHomeFragment;
import com.example.howtocook.uis.fragments.MainNotificationFragment;
import com.example.howtocook.uis.fragments.MainPersonalPostFragment;
import com.example.howtocook.uis.fragments.MainSettingFragment;

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
                return new MainPersonalPostFragment();

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
