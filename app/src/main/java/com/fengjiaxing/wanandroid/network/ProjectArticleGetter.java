package com.fengjiaxing.wanandroid.network;

import android.content.Context;
import android.graphics.Bitmap;

import com.fengjiaxing.wanandroid.application.contacts.ProjectContacts;
import com.fengjiaxing.wanandroid.application.contacts.ProjectListViewContacts;
import com.fengjiaxing.wanandroid.entity.Project;
import com.fengjiaxing.wanandroid.entity.ProjectArticle;
import com.fengjiaxing.wanandroid.util.BitmapReadAndWrite;
import com.fengjiaxing.wanandroid.util.UrlToImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @description 获取项目板块内内容
 */
public class ProjectArticleGetter implements ProjectContacts.IProjectModel, ProjectListViewContacts.IProjectListViewModel {

    private static LinkedHashMap<String, ArrayList<ProjectArticle>> projectArticleInformations;

    public void getProject() {
        try {
            String data = JSONDataGetter.methodGet("https://www.wanandroid.com/project/tree/json");
            if (data == null || "".equals(data)) return;
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            ArrayList<String> projectName = new ArrayList<>();
            ArrayList<String> projectId = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                projectName.add(jsonObject1.getString("name"));
                projectId.add(jsonObject1.getString("id"));
            }
            Project.setProjectArticleName(projectName);
            Project.setProjectArticleId(projectId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getProjectArticle(String childrenId, int page, Context context) {
        try {
            String data = JSONDataGetter.methodGet("https://www.wanandroid.com/project/list/" + page + "/json?cid=" + childrenId);
            if (data == null || "".equals(data)) return;
            synchronized (this) {
                if (projectArticleInformations == null) {
                    projectArticleInformations = new LinkedHashMap<>();
                    for (int i = 0; i < Project.getProjectArticleId().size(); i++) {
                        ArrayList<ProjectArticle> projectArticleList = new ArrayList<>();
                        projectArticleInformations.put(Project.getProjectArticleId().get(i), projectArticleList);
                    }
                }
            }
            JSONObject jsonObject = new JSONObject(data);
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            JSONArray jsonArray = jsonObject1.getJSONArray("datas");
            ArrayList<ProjectArticle> projectArticleList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = (JSONObject) jsonArray.get(i);
                String url = jsonObject2.getString("envelopePic");
                ProjectArticle projectArticle =
                        new ProjectArticle(jsonObject2.getString("title"),
                                jsonObject2.getString("author"),
                                jsonObject2.getString("niceDate"),
                                url,
                                jsonObject2.getString("desc"),
                                jsonObject2.getString("link"),
                                jsonObject2.getString("projectLink"));

                if (BitmapReadAndWrite.fileExist("project", url, context)) {
                    projectArticle.setImg(BitmapReadAndWrite.readBitmap("project", url, context));
                } else {
                    new Thread(() -> {
                        Bitmap bitmap = UrlToImage.get(url);
                        projectArticle.setImg(bitmap);
                        BitmapReadAndWrite.writeBitmap("project", url, bitmap, context);
                    }).start();
                }
                projectArticleList.add(projectArticle);
            }

            ArrayList<ProjectArticle> projectArticleArrayList = projectArticleInformations.get(childrenId);
            if (projectArticleArrayList == null) return;
            projectArticleArrayList.addAll(projectArticleList);
            projectArticleInformations.put(childrenId, projectArticleArrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static LinkedHashMap<String, ArrayList<ProjectArticle>> projectArticleInformation() {
        if (projectArticleInformations == null) projectArticleInformations = new LinkedHashMap<>();
        return projectArticleInformations;
    }

}
