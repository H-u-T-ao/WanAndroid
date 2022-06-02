package com.fengjiaxing.wanandroid.util;

import android.text.Html;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

/**
 * @description 解析HTML文本的工具类
 * */
public class HTMLTextAnalysis {

    public static void analysisAndSetText(TextView textView, String HTMLText) {
        Spanned html = Html.fromHtml(HTMLText);
        textView.setText(html);
    }

}
