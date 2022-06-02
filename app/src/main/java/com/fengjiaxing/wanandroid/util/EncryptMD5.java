package com.fengjiaxing.wanandroid.util;

import java.security.MessageDigest;

/**
 * @description 加密图片Url的工具类
 * */
public final class EncryptMD5 {

    public static String encrypt(String str) {
        MessageDigest messageDigest;
        String encryptStr = "";
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            byte[] digest = messageDigest.digest(str.getBytes());
            encryptStr = byteToString(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptStr;
    }

    private static String byteToString(byte[] digest) {
        StringBuilder str = new StringBuilder();
        String tempStr;
        for (byte b : digest) {
            tempStr = (Integer.toHexString(b & 0xff));
            if (tempStr.length() == 1) {
                str.append("0").append(tempStr);
            } else {
                str.append(tempStr);
            }
        }
        return str.toString().toLowerCase();
    }
}
