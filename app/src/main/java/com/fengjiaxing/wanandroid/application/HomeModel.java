package com.fengjiaxing.wanandroid.application;

import android.content.Context;

import com.fengjiaxing.wanandroid.application.contacts.HomeContacts;
import com.fengjiaxing.wanandroid.network.ArticleCollection;
import com.fengjiaxing.wanandroid.network.BannerGetter;
import com.fengjiaxing.wanandroid.network.HomeArticleGetter;

public class HomeModel implements HomeContacts.IHomeModel {
    @Override
    public void getBanner(Context context) {
        BannerGetter.getBanner(context);
    }

    @Override
    public void getHomeArticle(int page) {
        HomeArticleGetter.getHomeArticle(page);
    }

    @Override
    public void collect(String id) {
        ArticleCollection.postCollectArticle(id);
    }

}
