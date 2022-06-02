package com.fengjiaxing.wanandroid.application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fengjiaxing.wanandroid.R;
import com.fengjiaxing.wanandroid.entity.LoginInformation;
import com.fengjiaxing.wanandroid.network.LoginRequester;
import com.fengjiaxing.wanandroid.util.TaskBarSetting;

/**
 * @description 主活动
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final HomeFragment homeFragment = new HomeFragment();
    private final SystemFragment systemFragment = new SystemFragment();
    private final ProjectFragment projectFragment = new ProjectFragment();
    private final MeFragment meFragment = new MeFragment();

    private ImageView homeImg;
    private ImageView systemImg;
    private ImageView projectImg;
    private ImageView meImg;

    private int lastChooseId = R.id.rl_main_home;

    private Fragment lastFragment = homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.autoLogin();

        ImageView search = findViewById(R.id.iv_main_search);
        search.setOnClickListener(v -> {
            Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(searchIntent);
        });

        RelativeLayout home = findViewById(R.id.rl_main_home);
        RelativeLayout system = findViewById(R.id.rl_main_system);
        RelativeLayout project = findViewById(R.id.el_main_project);
        RelativeLayout me = findViewById(R.id.rl_main_me);
        homeImg = findViewById(R.id.img_home);
        systemImg = findViewById(R.id.img_system);
        projectImg = findViewById(R.id.img_project);
        meImg = findViewById(R.id.img_me);

        home.setOnClickListener(this);
        system.setOnClickListener(this);
        project.setOnClickListener(this);
        me.setOnClickListener(this);

        this.addFragment();
    }

    @Override
    public void onStart() {
        TextView statusBar = findViewById(R.id.tv_main_task_bar);
        TaskBarSetting.hideTaskBarOnly(this, statusBar);
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        setColor(id);
        showFragment(id);
        lastChooseId = id;
        if (this.lastChooseId == R.id.rl_main_home)
            lastFragment = homeFragment;
        else if (this.lastChooseId == R.id.rl_main_system)
            lastFragment = systemFragment;
        else if (this.lastChooseId == R.id.el_main_project)
            lastFragment = projectFragment;
        else if (this.lastChooseId == R.id.rl_main_me)
            lastFragment = meFragment;
    }

    private void autoLogin() {
        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
        String jSessionId = pref.getString("jSessionId", "");
        String userName = pref.getString("userName", "");
        if (jSessionId.isEmpty() || userName.isEmpty()) return;
        LoginInformation.setUserName(userName);
        LoginInformation.setSuccess(true);
        LoginRequester.jSessionId = jSessionId;
        Toast.makeText(MainActivity.this, "欢迎你!" + userName, Toast.LENGTH_LONG).show();
    }

    private void showFragment(int clickId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (clickId == R.id.rl_main_home) {
            transaction.hide(this.lastFragment).show(homeFragment);
        } else if (clickId == R.id.rl_main_system) {
            transaction.hide(this.lastFragment).show(systemFragment);
        } else if (clickId == R.id.el_main_project) {
            transaction.hide(this.lastFragment).show(projectFragment);
        } else if (clickId == R.id.rl_main_me) {
            transaction.hide(this.lastFragment).show(meFragment);
        }

        transaction.commit();
    }

    private void setColor(int nowChooseId) {
        if (this.lastChooseId == R.id.rl_main_home)
            homeImg.setBackgroundResource(R.drawable.ic_home_grey);
        else if (this.lastChooseId == R.id.rl_main_system)
            systemImg.setBackgroundResource(R.drawable.ic_system_grey);
        else if (this.lastChooseId == R.id.el_main_project)
            projectImg.setBackgroundResource(R.drawable.ic_project_grey);
        else if (this.lastChooseId == R.id.rl_main_me)
            meImg.setBackgroundResource(R.drawable.ic_me_grey);

        if (nowChooseId == R.id.rl_main_home)
            homeImg.setBackgroundResource(R.drawable.ic_home_purple);
        else if (nowChooseId == R.id.rl_main_system)
            systemImg.setBackgroundResource(R.drawable.ic_system_purple);
        else if (nowChooseId == R.id.el_main_project)
            projectImg.setBackgroundResource(R.drawable.ic_project_purple);
        else if (nowChooseId == R.id.rl_main_me)
            meImg.setBackgroundResource(R.drawable.ic_me_purple);
    }

    private void addFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.fragment_main, homeFragment)
                .add(R.id.fragment_main, systemFragment).hide(systemFragment)
                .add(R.id.fragment_main, projectFragment).hide(projectFragment)
                .add(R.id.fragment_main, meFragment).hide(meFragment);

        transaction.commit();
    }

}