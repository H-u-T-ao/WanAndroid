package com.fengjiaxing.wanandroid.application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @description 主页上方banner的ViewPager的适配器
 */
public class HomeBannerViewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<BannerFragment> fragments;

    public HomeBannerViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<BannerFragment> fragments) {
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
}
