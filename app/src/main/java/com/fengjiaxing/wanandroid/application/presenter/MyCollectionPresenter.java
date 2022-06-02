package com.fengjiaxing.wanandroid.application.presenter;

import android.app.Activity;

import com.fengjiaxing.wanandroid.application.MyCollectionModel;
import com.fengjiaxing.wanandroid.application.contacts.MyCollectionContacts;
import com.fengjiaxing.wanandroid.entity.ArticleList;
import com.fengjiaxing.wanandroid.entity.LoginInformation;
import com.fengjiaxing.wanandroid.util.NetworkConnectionChecker;

/**
 * @description 我的收藏活动的Presenter
 */
public class MyCollectionPresenter implements MyCollectionContacts.IMyCollectionPresenter {

    private final MyCollectionContacts.IMyCollectionActivity mActivity;
    private final MyCollectionContacts.IMyCollectionModel mModel;

    private int page = 0;
    private int totalCount = 0;

    private boolean loading = false;

    public MyCollectionPresenter(MyCollectionContacts.IMyCollectionActivity mActivity) {
        this.mActivity = mActivity;
        this.mModel = new MyCollectionModel();
    }

    @Override
    public void request() {
        new Thread(() -> {
            if (!LoginInformation.isSuccess()) return;
            if (page == 0) mActivity.initList();
            if (NetworkConnectionChecker.isNetworkConnected((Activity) mActivity)) {
                if (!loading) {
                    loading = true;
                    mModel.getCollection(page);
                    if (totalCount != ArticleList.myCollectionArticle.size()) {
                        mActivity.requestSuccess();
                        page++;
                    } else mActivity.listEnd();
                    totalCount = ArticleList.myCollectionArticle.size();
                    loading = false;
                }
            } else {
                mActivity.requestFailure();
            }
        }).start();
    }

    @Override
    public void cancel(int position) {
        new Thread(() -> {
            if (NetworkConnectionChecker.isNetworkConnected((Activity) mActivity)) {
                mModel.cancelCollect(ArticleList.myCollectionArticle.get(position).getId());
                ArticleList.myCollectionArticle.remove(position);
                mActivity.cancelSuccess();
            } else {
                mActivity.requestFailure();
            }
        }).start();
    }

}
