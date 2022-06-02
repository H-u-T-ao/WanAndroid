package com.fengjiaxing.wanandroid.application.contacts;

import android.widget.Button;

import java.util.ArrayList;

/**
 * @description 搜索活动的Contacts
 * */
public final class SearchContacts {

    public interface ISearchActivity {
        void initHotKey();

        void initList();

        void beginToSearch();

        void requestSuccess();

        void requestFailure();

        void requestEmpty();

        void collectSuccess();

        void listEnd();
    }

    public interface ISearchPresenter {
        void requestHotKey();

        ArrayList<Button> buttonBuild();

        void requestSearchResult(String searchText, int page);

        void collect(int position);
    }

    public interface ISearchModel {
        void postSearchResult(String searchText, int page);

        void postCollectArticle(String id);

        void getHotKey();
    }

}
