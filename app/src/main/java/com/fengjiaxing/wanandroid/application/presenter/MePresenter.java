package com.fengjiaxing.wanandroid.application.presenter;

import androidx.fragment.app.Fragment;

import com.fengjiaxing.wanandroid.application.contacts.MeContacts;
import com.fengjiaxing.wanandroid.entity.LoginInformation;
import com.fengjiaxing.wanandroid.network.LoginRequester;
import com.fengjiaxing.wanandroid.util.NetworkConnectionChecker;

/**
 * @description 我板块的Fragment的Presenter
 * */
public class MePresenter implements MeContacts.IMePresenter {

    private final MeContacts.IMeFragment mFragment;
    private final MeContacts.IMeModel mModel;

    public MePresenter(MeContacts.IMeFragment mFragment) {
        this.mFragment = mFragment;
        this.mModel = new LoginRequester();
    }

    @Override
    public void loginOut() {
        if (NetworkConnectionChecker.isNetworkConnected(((Fragment) mFragment).getContext())) {
            new Thread(() -> mModel.loginOut(((Fragment) mFragment).getContext())).start();
            mFragment.loginOutFinish();
        } else {
            mFragment.requestFailure();
        }
    }

    @Override
    public String checkLogin() {
        if (LoginInformation.isSuccess()) return LoginInformation.getUserName();
        else return null;
    }
}
