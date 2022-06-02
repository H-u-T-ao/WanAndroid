package com.fengjiaxing.wanandroid.application.contacts;

/**
 * @description 体系板块的Contacts
 * */
public final class SystemContacts {

    public interface ISystemFragment {
        void requestSuccess();

        void requestFailure();
    }

    public interface ISystemPresenter {
        void request();
    }

    public interface ISystemModel {
        void getSystem();
    }

}
