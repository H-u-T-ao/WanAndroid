package com.fengjiaxing.wanandroid.application.contacts;

import android.content.Context;

/**
 * @description 我板块的Fragment的Contacts
 * */
public final class MeContacts {

    public interface IMeFragment {
        void loginOutFinish();

        void requestFailure();
    }

    public interface IMePresenter {
        void loginOut();

        String checkLogin();
    }

    public interface IMeModel {
        void loginOut(Context context);
    }
}
