package com.fengjiaxing.wanandroid.entity;

/**
 * @description 所有无图Article的通用实体类
 * */
public class Article {

    private final String title;
    private final String author;
    private final String chapter;
    private final String niceDate;
    private final String id;
    private final String link;

    public Article(String title, String author, String chapterName, String superChapterName, String niceDate, String id, String link) {
        this.title = title;
        this.author = author;
        this.chapter = chapterName + "   " + superChapterName;
        this.niceDate = niceDate;
        this.id = id;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getChapter() {
        return chapter;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public String getLink() {
        return link;
    }

    public String getId() {
        return id;
    }

}
