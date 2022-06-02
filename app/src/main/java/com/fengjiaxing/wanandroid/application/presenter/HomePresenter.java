package com.fengjiaxing.wanandroid.application.presenter;

import androidx.fragment.app.Fragment;

import com.fengjiaxing.wanandroid.application.HomeModel;
import com.fengjiaxing.wanandroid.application.contacts.HomeContacts;
import com.fengjiaxing.wanandroid.entity.ArticleList;
import com.fengjiaxing.wanandroid.entity.LoginInformation;
import com.fengjiaxing.wanandroid.util.NetworkConnectionChecker;

public class HomePresenter implements HomeContacts.IHomePresenter {

    private final HomeContacts.IHomeFragment mFragment;
    private final HomeContacts.IHomeModel mModel;

    private int page = 0;

    private boolean loading = false;

    public HomePresenter(HomeContacts.IHomeFragment mFragment) {
        this.mFragment = mFragment;
        mModel = new HomeModel();
    }

    @Override
    public void requestHomeArticle() {
        if (page == 0) {
            new Thread(() -> {
                if (NetworkConnectionChecker.isNetworkConnected(((Fragment) mFragment).getContext())) {
                    mModel.getBanner(((Fragment) mFragment).getContext());
                    mModel.getHomeArticle(page);
                    mFragment.initList();
                    page++;
                } else {
                    mFragment.requestFailure();
                }
            }).start();
        } else {
            new Thread(() -> {
                if (NetworkConnectionChecker.isNetworkConnected(((Fragment) mFragment).getContext())) {
                    if (!loading) {
                        loading = true;
                        mModel.getHomeArticle(page);
                        mFragment.requestSuccess();
                        loading = false;
                    }
                } else {
                    mFragment.requestFailure();
                }
            }).start();
        }
    }

    @Override
    public void collect(int position) {
        if (!LoginInformation.isSuccess()) {
            return;
        }
        new Thread(() -> {
            if (NetworkConnectionChecker.isNetworkConnected(((Fragment) mFragment).getContext())) {
                mModel.collect(ArticleList.homeArticle.get(position).getId());
                mFragment.collectSuccess();
            } else {
                mFragment.requestFailure();
            }
        }).start();
    }

}
