package com.fengjiaxing.wanandroid.application;

import com.fengjiaxing.wanandroid.application.contacts.MyCollectionContacts;
import com.fengjiaxing.wanandroid.network.ArticleCollection;
import com.fengjiaxing.wanandroid.network.MyCollectGetter;

/**
 * @description 我的收藏活动的Model
 * */
public class MyCollectionModel implements MyCollectionContacts.IMyCollectionModel {
    @Override
    public void getCollection(int page) {
        MyCollectGetter.getCollection(page);
    }

    @Override
    public void cancelCollect(String id) {
        ArticleCollection.postCancelCollectArticle(id);
    }
}
