package com.fengjiaxing.wanandroid.application.presenter;

import androidx.fragment.app.Fragment;

import com.fengjiaxing.wanandroid.application.contacts.ProjectListViewContacts;
import com.fengjiaxing.wanandroid.entity.ProjectArticle;
import com.fengjiaxing.wanandroid.network.ProjectArticleGetter;
import com.fengjiaxing.wanandroid.util.NetworkConnectionChecker;

import java.util.ArrayList;

import static com.fengjiaxing.wanandroid.network.ProjectArticleGetter.projectArticleInformation;

/**
 * @description 项目板块的列表的Presenter
 * */
public class ProjectListViewPresenter implements ProjectListViewContacts.IProjectListViewPresenter {

    private final ProjectListViewContacts.IProjectListViewFragment mFragment;
    private final ProjectListViewContacts.IProjectListViewModel mModel;

    private int page = 1;
    private int totalCount = 0;
    private final String projectArticleId;
    private boolean loading = false;

    private ArrayList<ProjectArticle> projectArticleInformation = new ArrayList<>();

    public ProjectListViewPresenter(ProjectListViewContacts.IProjectListViewFragment mFragment, String projectArticleId) {
        this.mFragment = mFragment;
        this.projectArticleId = projectArticleId;
        mModel = new ProjectArticleGetter();
    }

    @Override
    public void request() {
        new Thread(() -> {
            if (NetworkConnectionChecker.isNetworkConnected(((Fragment) mFragment).getContext())) {
                if (!loading) {
                    loading = true;
                    mModel.getProjectArticle(projectArticleId, page, ((Fragment) mFragment).getContext());
                    if (page == 1) {
                        projectArticleInformation = projectArticleInformation().get(projectArticleId);
                        mFragment.init();
                        page++;
                    } else if (totalCount == projectArticleInformation().get(projectArticleId).size())
                        mFragment.listEnd();
                    else {
                        projectArticleInformation = projectArticleInformation().get(projectArticleId);
                        mFragment.requestSuccess();
                        page++;
                    }
                    totalCount = projectArticleInformation().get(projectArticleId).size();
                    loading = false;
                }
            } else {
                mFragment.requestFailure();
            }
        }).start();
    }

    @Override
    public ArrayList<ProjectArticle> getProjectArticleInformation() {
        return projectArticleInformation;
    }

}
