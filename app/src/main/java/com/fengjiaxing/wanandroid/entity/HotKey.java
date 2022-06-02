package com.fengjiaxing.wanandroid.entity;

import java.util.ArrayList;

/**
 * @description 热门搜索的实体类
 * */
public class HotKey {

    private static ArrayList<String> hotKey;

    public HotKey() {
    }

    public static ArrayList<String> getHotKey() {
        return hotKey;
    }

    public static void setHotKey(ArrayList<String> hotKey) {
        HotKey.hotKey = hotKey;
    }
}
