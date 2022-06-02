package com.fengjiaxing.wanandroid.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @description 读写Bitmap位图的工具类
 * */
public class BitmapReadAndWrite {

    public static void writeBitmap(String targetPath, String fileName, Bitmap bitmap, Context context) {
        String encryptMD5 = EncryptMD5.encrypt(fileName) + ".jpg";
        String path = context.getFilesDir() + "/" + targetPath + "/";
        File file = new File(path);
        if (!file.exists()) if (!file.mkdirs()) return;
        File writeFile = new File(path, encryptMD5);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(writeFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Bitmap readBitmap(String targetPath, String fileName, Context context) {
        String encryptMD5 = EncryptMD5.encrypt(fileName) + ".jpg";
        String path = context.getFilesDir() + "/" + targetPath + "/";
        File file = new File(path);
        if (!file.exists()) return null;
        File readFile = new File(path, encryptMD5);
        FileInputStream in = null;
        try {
            in = new FileInputStream(readFile);
            return BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static boolean fileExist(String targetPath, String fileName, Context context) {
        String encryptMD5 = EncryptMD5.encrypt(fileName) + ".jpg";
        String path = context.getFilesDir() + "/" + targetPath + "/" + encryptMD5;
        File file = new File(path);
        return file.exists();
    }

}
