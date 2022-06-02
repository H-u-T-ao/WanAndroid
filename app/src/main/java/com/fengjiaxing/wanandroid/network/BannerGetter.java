package com.fengjiaxing.wanandroid.network;

import android.content.Context;
import android.graphics.Bitmap;

import com.fengjiaxing.wanandroid.entity.Banner;
import com.fengjiaxing.wanandroid.util.BitmapReadAndWrite;
import com.fengjiaxing.wanandroid.util.UrlToImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @description 获取Banner数据
 */
public class BannerGetter {

    public static void getBanner(Context context) {
        try {
            String data = JSONDataGetter.methodGet("https://www.wanandroid.com/banner/json");
            if (data == null || "".equals(data)) return;
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            ArrayList<String> bannerTitle = new ArrayList<>();
            ArrayList<String> bannerImgString = new ArrayList<>();

            ArrayList<String> link = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                bannerTitle.add(jsonObject1.getString("title"));
                bannerImgString.add(jsonObject1.getString("imagePath"));
                link.add(jsonObject1.getString("url"));
            }
            Banner.setTitle(bannerTitle);
            Banner.setImgString(bannerImgString);
            Banner.setLink(link);
            boolean imgChange = false;
            for (int i = 0; i < Banner.getImgString().size(); i++) {
                if (!BitmapReadAndWrite
                        .fileExist("banner", Banner.getImgString().get(i), context)) {
                    imgChange = true;
                }
            }
            if (imgChange) {
                saveImg(context);
            } else {
                ArrayList<Bitmap> bannerImg = new ArrayList<>();
                for (int i = 0; i < Banner.getImgString().size(); i++) {
                    bannerImg.add(BitmapReadAndWrite.readBitmap("banner", Banner.getImgString().get(i), context));
                }
                Banner.setImg(bannerImg);
            }
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
    }

    private static void saveImg(Context context) {
        if (Banner.getImgString() != null) {
            ArrayList<Bitmap> bannerImg = new ArrayList<>();
            for (int i = 0; i < Banner.getImgString().size(); i++) {
                String url = Banner.getImgString().get(i);
                Bitmap bitmap = UrlToImage.get(url);
                BitmapReadAndWrite.writeBitmap("banner", url, bitmap, context);
                bannerImg.add(bitmap);
            }
            Banner.setImg(bannerImg);
        }
    }
}
