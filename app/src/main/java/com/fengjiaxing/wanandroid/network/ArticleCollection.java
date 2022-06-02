package com.fengjiaxing.wanandroid.network;

import com.fengjiaxing.wanandroid.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @description 发送收藏和取消收藏请求
 * */
public class ArticleCollection {

    public static void postCollectArticle(String id) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL("https://www.wanandroid.com/lg/collect/" + id + "/json");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setReadTimeout(R.integer.network_request_time_out);
            connection.setConnectTimeout(R.integer.network_request_time_out);

            connection.setRequestProperty("cookie", LoginRequester.jSessionId);

            connection.getInputStream();

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

    public static void postCancelCollectArticle(String id) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL("https://www.wanandroid.com/lg/uncollect_originId/" + id + "/json");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setReadTimeout(R.integer.network_request_time_out);
            connection.setConnectTimeout(R.integer.network_request_time_out);

            connection.setRequestProperty("cookie", LoginRequester.jSessionId);

            connection.getInputStream();

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
