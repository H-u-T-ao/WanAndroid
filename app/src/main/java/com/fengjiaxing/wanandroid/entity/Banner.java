package com.fengjiaxing.wanandroid.entity;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * @description 主页上方Banner的实体类
 * */
public class Banner {

    private static ArrayList<String> title;
    private static ArrayList<String> imgString;
    private static ArrayList<Bitmap> img;
    private static ArrayList<String> link;

    public static ArrayList<String> getImgString() {
        return imgString;
    }

    public static void setImgString(ArrayList<String> imgString) {
        Banner.imgString = imgString;
    }

    public static void setTitle(ArrayList<String> title) {
        Banner.title = title;
    }

    public static void setImg(ArrayList<Bitmap> img) {
        Banner.img = img;
    }

    public static void setLink(ArrayList<String> link) {
        Banner.link = link;
    }

    public static ArrayList<String> getTitle() {
        return title;
    }

    public static ArrayList<Bitmap> getImg() {
        return img;
    }

    public static ArrayList<String> getLink() {
        return link;
    }
}
