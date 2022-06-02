package com.fengjiaxing.wanandroid.application;

import androidx.fragment.app.Fragment;

import com.fengjiaxing.wanandroid.application.contacts.ProjectContacts;
import com.fengjiaxing.wanandroid.entity.Project;
import com.fengjiaxing.wanandroid.network.ProjectArticleGetter;
import com.fengjiaxing.wanandroid.util.NetworkConnectionChecker;

import java.util.ArrayList;

/**
 * @description 项目板块的Fragment的Presenter
 * */
public class ProjectPresenter implements ProjectContacts.IProjectPresenter {

    private final ProjectContacts.IProjectFragment mFragment;
    private final ProjectContacts.IProjectModel mModel;

    private ArrayList<String> projectArticleId = new ArrayList<>();
    private ArrayList<String> projectArticleName = new ArrayList<>();

    public ProjectPresenter(ProjectContacts.IProjectFragment mFragment) {
        this.mFragment = mFragment;
        mModel = new ProjectArticleGetter();
    }

    @Override
    public void initData() {
        new Thread(() -> {
            if (NetworkConnectionChecker.isNetworkConnected(((Fragment) mFragment).getContext())) {
                mModel.getProject();
                if (Project.getProjectArticleId() == null || Project.getProjectArticleId().isEmpty()) {
                    mFragment.requestFailure();
                } else {
                    projectArticleId = Project.getProjectArticleId();
                    projectArticleName = Project.getProjectArticleName();
                    mFragment.initFragment();
                }
            } else {
                mFragment.requestFailure();
            }
        }).start();
    }

    @Override
    public ArrayList<String> getProjectArticleId() {
        return projectArticleId;
    }

    @Override
    public ArrayList<String> getProjectArticleName() {
        return projectArticleName;
    }
}
