package com.fengjiaxing.wanandroid.application.presenter;

import androidx.fragment.app.Fragment;

import com.fengjiaxing.wanandroid.application.contacts.SystemChildrenListContacts;
import com.fengjiaxing.wanandroid.entity.ArticleList;
import com.fengjiaxing.wanandroid.entity.LoginInformation;
import com.fengjiaxing.wanandroid.network.SystemChildrenGetter;
import com.fengjiaxing.wanandroid.util.NetworkConnectionChecker;

/**
 * @description 体系板块的子项目的ViewPager的Fragment的Presenter
 * */
public class SystemChildrenListPresenter implements SystemChildrenListContacts.ISystemChildrenListPresenter {

    private final SystemChildrenListContacts.ISystemChildrenListFragment mFragment;
    private final SystemChildrenListContacts.ISystemChildrenListModel mModel;

    private int page = 0;
    private int totalCount = 0;
    private final String systemChildrenId;
    private boolean loading = false;

    public SystemChildrenListPresenter(SystemChildrenListContacts.ISystemChildrenListFragment mFragment, String systemChildrenId) {
        this.mFragment = mFragment;
        this.systemChildrenId = systemChildrenId;
        this.mModel = new SystemChildrenGetter();
    }

    @Override
    public void request() {
        new Thread(() -> {
            if (NetworkConnectionChecker.isNetworkConnected(((Fragment) mFragment).getContext())) {
                if (!loading) {
                    loading = true;
                    mModel.getSystemChildren(systemChildrenId, page);
                    if (ArticleList.systemArticle.get(systemChildrenId) == null ||
                            ArticleList.systemArticle.get(systemChildrenId).size() == 0) {
                        mFragment.requestFailure();
                        return;
                    }
                    if (page == 0) {
                        mFragment.init();
                        page++;
                    } else if (totalCount == ArticleList.systemArticle.get(systemChildrenId).size())
                        mFragment.listEnd();
                    else {
                        mFragment.requestSuccess();
                        page++;
                    }
                    totalCount = ArticleList.systemArticle.get(systemChildrenId).size();
                    loading = false;
                }
            } else {
                mFragment.requestFailure();
            }
        }).start();
    }

    @Override
    public void collect(int position) {
        if (!LoginInformation.isSuccess()) {
            return;
        }
        new Thread(() -> {
            if (NetworkConnectionChecker.isNetworkConnected(((Fragment) mFragment).getContext())) {
                mModel.postCollectArticle(ArticleList.systemArticle.get(systemChildrenId).get(position).getId());
                mFragment.collectSuccess();
            } else {
                mFragment.requestFailure();
            }
        }).start();
    }

}
