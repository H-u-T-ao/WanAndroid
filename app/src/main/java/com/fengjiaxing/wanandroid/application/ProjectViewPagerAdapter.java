package com.fengjiaxing.wanandroid.application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @description 项目板块的ViewPager的适配器
 * */
public class ProjectViewPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<ProjectListViewFragment> fragments;

    public ProjectViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<ProjectListViewFragment> fragments) {
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
        return this.fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getChildrenName();
    }
}
