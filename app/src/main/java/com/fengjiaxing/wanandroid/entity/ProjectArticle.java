package com.fengjiaxing.wanandroid.entity;

import android.graphics.Bitmap;

/**
 * @description 项目板块内数据的实体类
 */
public class ProjectArticle {

    private final String Title;
    private final String author;
    private final String niceDate;
    private final String imgString;
    private Bitmap img;
    private final String desc;
    private final String link;
    private final String projectLink;

    public ProjectArticle(String title, String author, String niceDate, String imgString, String desc, String link, String projectLink) {
        Title = title;
        this.author = author;
        this.niceDate = niceDate;
        this.imgString = imgString;
        this.desc = desc;
        this.link = link;
        this.projectLink = projectLink;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public String getTitle() {
        return Title;
    }

    public String getAuthor() {
        return author;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public Bitmap getImg() {
        return img;
    }

    public String getDesc() {
        return desc;
    }

    public String getLink() {
        return link;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public String getImgString() {
        return imgString;
    }
}
