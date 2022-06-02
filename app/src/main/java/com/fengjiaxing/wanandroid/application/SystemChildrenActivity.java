package com.fengjiaxing.wanandroid.application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengjiaxing.wanandroid.R;
import com.fengjiaxing.wanandroid.entity.System;
import com.fengjiaxing.wanandroid.util.TaskBarSetting;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * @description 体系板块的子项目的活动
 */
public class SystemChildrenActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_children);

        Intent intent = getIntent();
        position = Integer.parseInt(intent.getStringExtra("position"));

        viewPager = findViewById(R.id.vp_system_children);
        tabLayout = findViewById(R.id.tab_system_children);
        ImageView back = findViewById(R.id.iv_system_children_back);

        back.setOnClickListener(v -> finish());

        initFragment();
    }

    private void initFragment() {
        ArrayList<SystemChildrenListFragment> systemChildrenListFragmentList = new ArrayList<>();
        for (int i = 0; i < System.getChildrenIdList().get(position).size(); i++) {
            SystemChildrenListFragment systemChildrenListFragment =
                    new SystemChildrenListFragment(System.getChildrenIdList().get(position).get(i), System.getChildrenNameList().get(position).get(i));
            systemChildrenListFragmentList.add(systemChildrenListFragment);
        }

        SystemChildrenViewPagerAdapter adapter =
                new SystemChildrenViewPagerAdapter(getSupportFragmentManager(), systemChildrenListFragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(System.getChildrenIdList().get(position).size() - 1);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void onStart() {
        TextView bar = findViewById(R.id.tv_system_children_task_bar);
        TaskBarSetting.hideAll(this, bar);
        super.onStart();
    }

}