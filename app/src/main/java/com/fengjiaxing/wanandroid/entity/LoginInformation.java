package com.fengjiaxing.wanandroid.entity;

import java.util.List;

/**
 * @description 登录信息的实体类
 * */
public class LoginInformation {

    private static List<String> cookie;
    private static String userName;
    private volatile static boolean success;

    public static void setSuccess(boolean success) {
        LoginInformation.success = success;
    }

    public static List<String> getCookie() {
        return cookie;
    }

    public static boolean isSuccess() {
        return success;
    }

    public static void setCookie(List<String> cookie) {
        LoginInformation.cookie = cookie;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        LoginInformation.userName = userName;
    }

}
