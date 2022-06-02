package com.fengjiaxing.wanandroid.entity;

import java.util.ArrayList;

/**
 * @description 体系大标题的实体类
 * */
public class System {

    private static ArrayList<String> title;
    private static ArrayList<String> childrenName;
    private static ArrayList<ArrayList<String>> childrenNameList;
    private static ArrayList<ArrayList<String>> childrenIdList;

    public static ArrayList<String> getTitle() {
        return title;
    }

    public static void setTitle(ArrayList<String> title) {
        System.title = title;
    }

    public static ArrayList<String> getChildrenName() {
        return childrenName;
    }

    public static void setChildrenName(ArrayList<String> childrenName) {
        System.childrenName = childrenName;
    }

    public static ArrayList<ArrayList<String>> getChildrenIdList() {
        return childrenIdList;
    }

    public static void setChildrenIdList(ArrayList<ArrayList<String>> childrenIdList) {
        System.childrenIdList = childrenIdList;
    }

    public static ArrayList<ArrayList<String>> getChildrenNameList() {
        return childrenNameList;
    }

    public static void setChildrenNameList(ArrayList<ArrayList<String>> childrenNameList) {
        System.childrenNameList = childrenNameList;
    }
}
