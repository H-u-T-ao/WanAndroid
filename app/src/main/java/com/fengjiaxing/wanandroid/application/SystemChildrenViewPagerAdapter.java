package com.fengjiaxing.wanandroid.application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @description 体系板块子项目活动的ViewPager的适配器
 * */
public class SystemChildrenViewPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<SystemChildrenListFragment> fragments;

    public SystemChildrenViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<SystemChildrenListFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getChildrenName();
    }
}
