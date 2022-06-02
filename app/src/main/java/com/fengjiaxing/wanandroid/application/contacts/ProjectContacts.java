package com.fengjiaxing.wanandroid.application.contacts;

import java.util.ArrayList;

/**
 * @description 项目板块的Contacts
 * */
public final class ProjectContacts {

    public interface IProjectFragment {
        void initFragment();

        void requestFailure();
    }

    public interface IProjectPresenter {
        void initData();

        ArrayList<String> getProjectArticleId();

        ArrayList<String> getProjectArticleName();
    }

    public interface IProjectModel {
        void getProject();
    }

}
