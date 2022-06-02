package com.fengjiaxing.wanandroid.application.presenter;

import android.app.Activity;

import com.fengjiaxing.wanandroid.application.contacts.RegisterContacts;
import com.fengjiaxing.wanandroid.network.RegisterRequester;
import com.fengjiaxing.wanandroid.util.NetworkConnectionChecker;

/**
 * @description 注册活动的Presenter
 * */
public class RegisterPresenter implements RegisterContacts.IRegisterPresenter {

    private final RegisterContacts.IRegisterActivity mActivity;
    private final RegisterContacts.IRegisterModel mModel;

    public RegisterPresenter(RegisterContacts.IRegisterActivity mActivity) {
        this.mActivity = mActivity;
        this.mModel = new RegisterRequester();
    }

    @Override
    public void register(String userName, String password, String repassword) {
        new Thread(() -> {
            if (NetworkConnectionChecker.isNetworkConnected((Activity) mActivity)) {
                if (mModel.register(userName, password, repassword)) mActivity.registerSuccess();
                else mActivity.registerFailure();
            } else {
                mActivity.requestFailure();
            }
        }).start();
    }
}
