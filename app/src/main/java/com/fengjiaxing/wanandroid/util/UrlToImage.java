package com.fengjiaxing.wanandroid.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.fengjiaxing.wanandroid.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @description 根据Url找到Bitmap位图的工具类
 */
public class UrlToImage {

    public static Bitmap get(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(R.integer.network_request_time_out);
            connection.setReadTimeout(R.integer.network_request_time_out);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == 200) {
                InputStream inputStream = connection.getInputStream();
                return BitmapFactory.decodeStream(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
