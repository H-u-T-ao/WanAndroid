package com.fengjiaxing.wanandroid.network;

import com.fengjiaxing.wanandroid.entity.HotKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @description 获取热门搜索数据
 * */
public class HotKeyGetter {

    public static void getHotKey() {
        try {
            String data = JSONDataGetter.methodGet("https://www.wanandroid.com/hotkey/json");
            if (data == null || "".equals(data)) return;
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            ArrayList<String> hotKey = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                hotKey.add(jsonObject1.getString("name"));
            }
            HotKey.setHotKey(hotKey);
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
    }
}
