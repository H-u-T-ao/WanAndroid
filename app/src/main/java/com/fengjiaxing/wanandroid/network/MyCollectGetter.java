package com.fengjiaxing.wanandroid.network;

import com.fengjiaxing.wanandroid.R;
import com.fengjiaxing.wanandroid.entity.Article;
import com.fengjiaxing.wanandroid.entity.ArticleList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * @description 获取我的收藏列表
 */
public class MyCollectGetter {

    public static void getCollection(int page) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL("https://www.wanandroid.com/lg/collect/list/" + page + "/json");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(R.integer.network_request_time_out);
            connection.setConnectTimeout(R.integer.network_request_time_out);

            connection.setRequestProperty("cookie", LoginRequester.jSessionId);

            InputStream in = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            String data = result.toString();
            if (data.equals("null")) return;
            JSONObject jsonObject = new JSONObject(data);
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            JSONArray jsonArray = jsonObject1.getJSONArray("datas");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = (JSONObject) jsonArray.get(i);
                Article collection =
                        new Article(jsonObject2.getString("title"),
                                jsonObject2.getString("author"),
                                jsonObject2.getString("chapterName"),
                                "",
                                jsonObject2.getString("niceDate"),
                                jsonObject2.getInt("originId") + "",
                                jsonObject2.getString("link"));
                ArticleList.myCollectionArticle.add(collection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}
