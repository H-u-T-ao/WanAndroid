package com.fengjiaxing.wanandroid.application.contacts;

/**
 * @description 我的收藏活动的Contacts
 * */
public final class MyCollectionContacts {

    public interface IMyCollectionActivity {
        void initList();

        void requestSuccess();

        void requestFailure();

        void cancelSuccess();

        void listEnd();
    }

    public interface IMyCollectionPresenter {
        void request();

        void cancel(int position);
    }

    public interface IMyCollectionModel {
        void getCollection(int page);

        void cancelCollect(String id);
    }

}
