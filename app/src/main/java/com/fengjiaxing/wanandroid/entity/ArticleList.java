package com.fengjiaxing.wanandroid.entity;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @description 所有无图Article的分类列表的实体类
 * */
public class ArticleList {

    public static ArrayList<Article> searchArticle = new ArrayList<>();
    public static ArrayList<Article> homeArticle = new ArrayList<>();
    public static ArrayList<Article> myCollectionArticle = new ArrayList<>();
    public static LinkedHashMap<String, ArrayList<Article>> systemArticle = new LinkedHashMap<>();

}
