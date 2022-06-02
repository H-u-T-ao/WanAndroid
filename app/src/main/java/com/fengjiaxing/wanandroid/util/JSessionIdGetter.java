package com.fengjiaxing.wanandroid.util;

import com.fengjiaxing.wanandroid.entity.LoginInformation;

/**
 * @description 从cookie中截取JSessionId字段的工具类
 * */
public class JSessionIdGetter {

    public static String getJSessionId() {
        if (LoginInformation.getCookie() == null) return null;
        return LoginInformation.getCookie().get(0).substring(0, LoginInformation.getCookie().get(0).indexOf(";"));
    }

}
