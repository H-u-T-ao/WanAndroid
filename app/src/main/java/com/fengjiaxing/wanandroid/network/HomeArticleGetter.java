package com.fengjiaxing.wanandroid.network;

import com.fengjiaxing.wanandroid.entity.Article;
import com.fengjiaxing.wanandroid.entity.ArticleList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @description 获取主页数据
 */
public class HomeArticleGetter {

    public static void getHomeArticle(int page) {
        try {
            String data = JSONDataGetter.methodGet("https://www.wanandroid.com/article/list/" + page + "/json");
            if (data == null || "".equals(data)) return;
            JSONObject jsonObject = new JSONObject(data);
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            JSONArray jsonArray = jsonObject1.getJSONArray("datas");
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
                ArticleList.homeArticle.add(information);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
