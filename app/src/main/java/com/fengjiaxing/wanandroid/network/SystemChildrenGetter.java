package com.fengjiaxing.wanandroid.network;

import com.fengjiaxing.wanandroid.application.contacts.SystemChildrenListContacts;
import com.fengjiaxing.wanandroid.application.contacts.SystemContacts;
import com.fengjiaxing.wanandroid.entity.Article;
import com.fengjiaxing.wanandroid.entity.ArticleList;
import com.fengjiaxing.wanandroid.entity.System;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @description 获取体系数据
 */
public class SystemChildrenGetter implements SystemContacts.ISystemModel, SystemChildrenListContacts.ISystemChildrenListModel {

    @Override
    public void getSystem() {
        try {
            String data = JSONDataGetter.methodGet("https://www.wanandroid.com/tree/json");
            if (data == null || "".equals(data)) return;
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            ArrayList<String> systemTitle = new ArrayList<>();
            ArrayList<String> systemChildrenTitle = new ArrayList<>();
            ArrayList<ArrayList<String>> childrenIdList = new ArrayList<>();
            ArrayList<ArrayList<String>> childrenNameList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                StringBuilder stringBuilderChildrenName = new StringBuilder();
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                systemTitle.add(jsonObject1.getString("name"));
                JSONArray jsonArray1 = jsonObject1.getJSONArray("children");
                ArrayList<String> childrenName = new ArrayList<>();
                ArrayList<String> childrenId = new ArrayList<>();
                for (int j = 0; j < jsonArray1.length(); j++) {
                    JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                    stringBuilderChildrenName.append(jsonObject2.getString("name")).append(" ");
                    childrenName.add(jsonObject2.getString("name"));
                    childrenId.add(jsonObject2.getString("id"));
                }
                childrenNameList.add(childrenName);
                childrenIdList.add(childrenId);
                systemChildrenTitle.add(stringBuilderChildrenName.toString());
            }
            System.setTitle(systemTitle);
            System.setChildrenName(systemChildrenTitle);
            System.setChildrenNameList(childrenNameList);
            System.setChildrenIdList(childrenIdList);
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getSystemChildren(String childrenId, int page) {
        try {
            String data = JSONDataGetter.methodGet("https://www.wanandroid.com/article/list/" + page + "/json?cid=" + childrenId);
            if (data == null || "".equals(data)) return;
            JSONObject jsonObject = new JSONObject(data);
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            JSONArray jsonArray = jsonObject1.getJSONArray("datas");
            ArrayList<Article> systemChildrenList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = (JSONObject) jsonArray.get(i);
                Article information =
                        new Article(jsonObject2.getString("title"),
                                jsonObject2.getString("author"),
                                jsonObject2.getString("chapterName"),
                                jsonObject2.getString("superChapterName"),
                                jsonObject2.getString("niceDate"),
                                jsonObject2.getInt("id") + "",
                                jsonObject2.getString("link"));
                systemChildrenList.add(information);
            }
            ArrayList<Article> systemChildrenArrayList = ArticleList.systemArticle.get(childrenId);
            if (systemChildrenArrayList == null) systemChildrenArrayList = new ArrayList<>();
            systemChildrenArrayList.addAll(systemChildrenList);
            ArticleList.systemArticle.put(childrenId, systemChildrenArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postCollectArticle(String id) {
        ArticleCollection.postCollectArticle(id);
    }

}
