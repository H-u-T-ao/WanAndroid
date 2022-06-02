package com.fengjiaxing.wanandroid.application.contacts;

import android.content.Context;

import com.fengjiaxing.wanandroid.entity.ProjectArticle;

import java.util.ArrayList;

/**
 * @description 项目板块的Fragment的Contacts
 * */
public final class ProjectListViewContacts {

    public interface IProjectListViewFragment {
        void init();

        void requestSuccess();

        void requestFailure();

        void listEnd();
    }

    public interface IProjectListViewPresenter {
        void request();

        ArrayList<ProjectArticle> getProjectArticleInformation();
    }

    public interface IProjectListViewModel {
        void getProjectArticle(String childrenId, int page, Context context);
    }

}
