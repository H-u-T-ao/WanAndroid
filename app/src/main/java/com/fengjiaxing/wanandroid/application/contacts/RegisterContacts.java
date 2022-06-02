package com.fengjiaxing.wanandroid.application.contacts;

/**
 * @description 注册活动的Contacts
 * */
public final class RegisterContacts {

    public interface IRegisterActivity {
        void registerSuccess();

        void registerFailure();

        void requestFailure();
    }

    public interface IRegisterPresenter {
        void register(String userName, String password, String rePassword);
    }

    public interface IRegisterModel {
        boolean register(String userName, String password, String rePassword);
    }
}
