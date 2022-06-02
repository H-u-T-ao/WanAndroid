package com.fengjiaxing.wanandroid.network;

import com.fengjiaxing.wanandroid.entity.Article;
import com.fengjiaxing.wanandroid.entity.ArticleList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @description 获取搜索数据
 */
public class SearchArticle {

    public static void postSearchResult(String searchText, int page) {
        String data = JSONDataGetter.methodPost("https://www.wanandroid.com/article/query/" + page + "/json",
                new String[]{"k"}, new String[]{searchText});
        if (data == null || "".equals(data)) return;
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            JSONArray jsonArray = jsonObject1.getJSONArray("datas");
            ArrayList<Article> informations = new ArrayList<>();
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
                informations.add(information);
            }
            if (page == 0) {
                ArticleList.searchArticle.clear();
            }
            ArticleList.searchArticle.addAll(informations);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
