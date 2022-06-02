package com.fengjiaxing.wanandroid.application;

import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.fengjiaxing.wanandroid.R;
import com.fengjiaxing.wanandroid.application.contacts.ProjectContacts;
import com.fengjiaxing.wanandroid.entity.Project;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * @description 项目板块的Fragment
 * */
public class ProjectFragment extends Fragment implements ProjectContacts.IProjectFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private ProjectContacts.IProjectPresenter mPresenter;

    private static final int REQUEST_FALSE = -1;
    private static final int REQUEST_SUCCESS = 0;

    private final Handler handler = new Handler(Looper.myLooper()) {


        @Override
        public void handleMessage(@NonNull Message message) {
            switch (message.what) {
                case REQUEST_FALSE:
                    Toast.makeText(getActivity(), R.string.time_out_text, Toast.LENGTH_SHORT).show();
                    break;
                case REQUEST_SUCCESS:
                    buildFragment();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_project, container, false);

        mPresenter = new ProjectPresenter(this);

        tabLayout = view.findViewById(R.id.tab_project);
        viewPager = view.findViewById(R.id.vp_project);

        mPresenter.initData();

        return view;
    }

    @Override
    public void initFragment() {
        Message message = new Message();
        message.what = REQUEST_SUCCESS;
        handler.sendMessage(message);
    }

    private void buildFragment() {
        ArrayList<ProjectListViewFragment> projectListViewFragmentList = new ArrayList<>();
        for (int i = 0; i < mPresenter.getProjectArticleId().size(); i++) {
            ProjectListViewFragment projectListViewFragment =
                    new ProjectListViewFragment(mPresenter.getProjectArticleId().get(i), mPresenter.getProjectArticleName().get(i));
            projectListViewFragmentList.add(projectListViewFragment);
        }
        ProjectViewPagerAdapter adapter =
                new ProjectViewPagerAdapter(getChildFragmentManager(), projectListViewFragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(Project.getProjectArticleId().size() - 1);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void requestFailure() {
        Message message = new Message();
        message.what = REQUEST_FALSE;
        handler.sendMessage(message);
    }

    @Override
    public void onDestroy() {
        mPresenter = null;
        super.onDestroy();
    }

}
