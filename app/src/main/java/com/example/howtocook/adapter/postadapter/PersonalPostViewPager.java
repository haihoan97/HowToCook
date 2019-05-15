package com.example.howtocook.adapter.postadapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.howtocook.uis.fragments.post.PostCommentFragment;
import com.example.howtocook.uis.fragments.post.PostShareFragment;
import com.example.howtocook.uis.fragments.post.PostStepFragment;

public class PersonalPostViewPager extends FragmentPagerAdapter {

    public PersonalPostViewPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new PostStepFragment();
            case 1:
                return new PostCommentFragment();
            case 2:
                return new PostShareFragment();
            default:
                return new PostStepFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Cac buoc";
            case 1:
                return "Binh luan";
            case 2:
                return "Chia se";
            default:
                return "Cac buoc";
        }
    }
}
