package com.fengjiaxing.wanandroid.entity;

import java.util.ArrayList;

/**
 * @description 项目大标题的实体类
 * */
public class Project {

    private static ArrayList<String> projectArticleName;
    private static ArrayList<String> projectArticleId;

    public static ArrayList<String> getProjectArticleId() {
        return projectArticleId;
    }

    public static void setProjectArticleId(ArrayList<String> projectArticleId) {
        Project.projectArticleId = projectArticleId;
    }

    public static ArrayList<String> getProjectArticleName() {
        return projectArticleName;
    }

    public static void setProjectArticleName(ArrayList<String> projectArticleName) {
        Project.projectArticleName = projectArticleName;
    }
}
