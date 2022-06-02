package com.fengjiaxing.wanandroid.application.presenter;

import android.app.Activity;

import com.fengjiaxing.wanandroid.application.contacts.LoginContacts;
import com.fengjiaxing.wanandroid.entity.LoginInformation;
import com.fengjiaxing.wanandroid.network.LoginRequester;
import com.fengjiaxing.wanandroid.util.NetworkConnectionChecker;

/**
 * @description 登录活动的Presenter
 * */
public class LoginPresenter implements LoginContacts.ILoginPresenter {

    private final LoginContacts.ILoginActivity mActivity;
    private final LoginContacts.ILoginModel mModel;

    public LoginPresenter(LoginContacts.ILoginActivity mActivity) {
        this.mActivity = mActivity;
        this.mModel = new LoginRequester();
    }

    @Override
    public void login(String userName, String password) {
        new Thread(() -> {
            if (NetworkConnectionChecker.isNetworkConnected((Activity) mActivity)) {
                mModel.newLogin(userName, password);
                if (LoginInformation.isSuccess()) mActivity.loginSuccess();
                else mActivity.loginFailure();
            } else {
                mActivity.requestFailure();
            }
        }).start();
    }
}
