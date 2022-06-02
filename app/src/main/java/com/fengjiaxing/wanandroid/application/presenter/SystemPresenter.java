package com.fengjiaxing.wanandroid.application.presenter;

import androidx.fragment.app.Fragment;

import com.fengjiaxing.wanandroid.application.contacts.SystemContacts;
import com.fengjiaxing.wanandroid.entity.System;
import com.fengjiaxing.wanandroid.network.SystemChildrenGetter;
import com.fengjiaxing.wanandroid.util.NetworkConnectionChecker;

/**
 * @description 体系板块的Fragment的Presenter
 * */
public class SystemPresenter implements SystemContacts.ISystemPresenter {

    private final SystemContacts.ISystemFragment mFragment;
    private final SystemContacts.ISystemModel mModel;

    public SystemPresenter(SystemContacts.ISystemFragment mFragment) {
        this.mFragment = mFragment;
        this.mModel = new SystemChildrenGetter();
    }

    @Override
    public void request() {
        new Thread(() -> {
            if (NetworkConnectionChecker.isNetworkConnected(((Fragment) mFragment).getContext())) {
                mModel.getSystem();
                if (System.getChildrenIdList() == null || System.getChildrenIdList().isEmpty()) {
                    mFragment.requestFailure();
                } else {
                    mFragment.requestSuccess();
                }
            } else {
                mFragment.requestFailure();
            }
        }).start();
    }

}
