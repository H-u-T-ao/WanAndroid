package com.fengjiaxing.wanandroid.application.contacts;

import android.content.Context;

public final class HomeContacts {

    public interface IHomeFragment {
        void initList();

        void requestSuccess();

        void requestFailure();

        void collectSuccess();
    }

    public interface IHomePresenter {
        void requestHomeArticle();

        void collect(int position);
    }

    public interface IHomeModel {
        void getBanner(Context context);

        void getHomeArticle(int page);

        void collect(String id);
    }

}
