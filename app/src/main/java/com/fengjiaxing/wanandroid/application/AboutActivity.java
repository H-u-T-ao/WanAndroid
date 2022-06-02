package com.fengjiaxing.wanandroid.application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengjiaxing.wanandroid.R;
import com.fengjiaxing.wanandroid.util.TaskBarSetting;

/**
 * @description 我板块的关于活动
 * */
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ImageView backToMe = findViewById(R.id.iv_about_back_about_to_me);
        backToMe.setOnClickListener(v -> finish());
    }

    @Override
    public void onStart() {
        TextView bar = findViewById(R.id.tv_about_task_bar);
        TaskBarSetting.hideAll(this, bar);
        super.onStart();
    }

}