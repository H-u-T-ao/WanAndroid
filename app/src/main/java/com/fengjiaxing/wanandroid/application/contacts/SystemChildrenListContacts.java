package com.fengjiaxing.wanandroid.application.contacts;

/**
 * @description 体系板块的子项目的Contacts
 * */
public final class SystemChildrenListContacts {

    public interface ISystemChildrenListFragment {
        void init();

        void collectSuccess();

        void requestSuccess();

        void requestFailure();

        void listEnd();
    }

    public interface ISystemChildrenListPresenter {
        void request();

        void collect(int position);
    }

    public interface ISystemChildrenListModel {
        void getSystemChildren(String childrenId, int page);

        void postCollectArticle(String id);
    }

}
