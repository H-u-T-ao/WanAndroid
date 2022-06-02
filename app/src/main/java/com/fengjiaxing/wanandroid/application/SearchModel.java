package com.fengjiaxing.wanandroid.application;

import com.fengjiaxing.wanandroid.application.contacts.SearchContacts;
import com.fengjiaxing.wanandroid.network.ArticleCollection;
import com.fengjiaxing.wanandroid.network.HotKeyGetter;
import com.fengjiaxing.wanandroid.network.SearchArticle;

/**
 * @description 搜索活动的Model
 * */
public class SearchModel implements SearchContacts.ISearchModel {
    @Override
    public void postSearchResult(String searchText, int page) {
        SearchArticle.postSearchResult(searchText, page);
    }

    @Override
    public void postCollectArticle(String id) {
        ArticleCollection.postCollectArticle(id);
    }

    @Override
    public void getHotKey() {
        HotKeyGetter.getHotKey();
    }
}
