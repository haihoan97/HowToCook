package com.example.howtocook.adapter.personalpage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.howtocook.uis.fragments.personal.ChiaSeFragment;
import com.example.howtocook.uis.fragments.personal.CongThucFragment;
import com.example.howtocook.uis.fragments.personal.TheoDoiFragment;


public class Pager_Adapter extends FragmentStatePagerAdapter {
    public Pager_Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i){
            case 0:
                fragment = new CongThucFragment();
                break;
            case 1:
                fragment = new TheoDoiFragment();
                break;
            case 2:
                fragment = new ChiaSeFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Công thức";
                break;
            case 1:
                title = "Đang theo dõi";
                break;
            case 2:
                title = "Đã chia sẻ";
                break;
        }
        return title;
    }
}
