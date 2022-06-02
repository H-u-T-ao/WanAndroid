package com.fengjiaxing.wanandroid.application.presenter;

import android.app.Activity;
import android.graphics.Color;
import android.widget.Button;

import com.fengjiaxing.wanandroid.R;
import com.fengjiaxing.wanandroid.application.SearchModel;
import com.fengjiaxing.wanandroid.application.contacts.SearchContacts;
import com.fengjiaxing.wanandroid.entity.Article;
import com.fengjiaxing.wanandroid.entity.ArticleList;
import com.fengjiaxing.wanandroid.entity.HotKey;
import com.fengjiaxing.wanandroid.entity.LoginInformation;
import com.fengjiaxing.wanandroid.util.NetworkConnectionChecker;

import java.util.ArrayList;

/**
 * @description 搜索活动的Presenter
 * */
public class SearchPresenter implements SearchContacts.ISearchPresenter {

    private final SearchContacts.ISearchActivity mActivity;
    private final SearchContacts.ISearchModel mModel;

    private ArrayList<Article> searchArticle = new ArrayList<>();

    private int totalCount = 0;

    private boolean loading = false;

    public SearchPresenter(SearchContacts.ISearchActivity mActivity) {
        this.mActivity = mActivity;
        mModel = new SearchModel();
    }

    @Override
    public void requestHotKey() {
        new Thread(() -> {
            if (NetworkConnectionChecker.isNetworkConnected((Activity) mActivity)) {
                mModel.getHotKey();
                if (HotKey.getHotKey() == null || HotKey.getHotKey().isEmpty()) return;
                mActivity.initHotKey();
            } else {
                mActivity.requestFailure();
            }
        }).start();
    }

    @Override
    public ArrayList<Button> buttonBuild() {
        ArrayList<Button> buttonList = new ArrayList<>();
        for (int i = 0; i < HotKey.getHotKey().size(); i++) {
            Button button = new Button((Activity) mActivity);
            button.setText(HotKey.getHotKey().get(i));
            button.setTextSize(20);
            button.setTextColor(Color.rgb(255, 255, 255));
            button.setBackgroundResource(R.drawable.ic_shape_hot_key);
            buttonList.add(button);
        }
        return buttonList;
    }

    @Override
    public void requestSearchResult(String search, int page) {
        new Thread(() -> {
            mActivity.beginToSearch();
            if (page == 0) mActivity.initList();
            if (NetworkConnectionChecker.isNetworkConnected((Activity) mActivity)) {
                if (!loading) {
                    loading = true;
                    mModel.postSearchResult(search, page);
                    if (!ArticleList.searchArticle.isEmpty() &&
                            totalCount != ArticleList.searchArticle.size()) {
                        searchArticle = ArticleList.searchArticle;
                        mActivity.requestSuccess();
                    } else if (totalCount == ArticleList.searchArticle.size())
                        mActivity.listEnd();
                    else mActivity.requestEmpty();
                    totalCount = ArticleList.searchArticle.size();
                    loading = false;
                }
            } else {
                mActivity.requestFailure();
            }
        }).start();
    }

    @Override
    public void collect(int position) {
        if (!LoginInformation.isSuccess()) {
            return;
        }
        new Thread(() -> {
            if (NetworkConnectionChecker.isNetworkConnected((Activity) mActivity)) {
                mModel.postCollectArticle(searchArticle.get(position).getId());
                mActivity.collectSuccess();
            } else {
                mActivity.requestFailure();
            }
        }).start();
    }

}
