package com.fengjiaxing.wanandroid.application.contacts;

/**
 * @description 登录活动的Contacts
 * */
public final class LoginContacts {

    public interface ILoginActivity {
        void loginSuccess();

        void loginFailure();

        void requestFailure();
    }

    public interface ILoginPresenter {
        void login(String userName, String password);
    }

    public interface ILoginModel {
        void newLogin(String userName, String password);
    }

}
