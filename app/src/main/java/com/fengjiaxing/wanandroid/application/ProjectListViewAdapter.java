package com.fengjiaxing.wanandroid.application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengjiaxing.wanandroid.R;
import com.fengjiaxing.wanandroid.entity.ProjectArticle;
import com.fengjiaxing.wanandroid.util.HTMLTextAnalysis;

import java.util.ArrayList;

import static com.fengjiaxing.wanandroid.network.ProjectArticleGetter.projectArticleInformation;

/**
 * @description 项目板块列表的适配器
 * */
public class ProjectListViewAdapter extends BaseAdapter {

    private final LayoutInflater layoutInflater;

    ArrayList<ProjectArticle> projectArticleList;

    public ProjectListViewAdapter(Context context, String projectArticleId) {
        this.layoutInflater = LayoutInflater.from(context);
        projectArticleList = projectArticleInformation().get(projectArticleId);
    }

    @Override
    public int getCount() {
        if (projectArticleList == null) return 0;
        return projectArticleList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class ViewHolder {
        private TextView title;
        private TextView author;
        private TextView desc;
        private TextView niceDate;
        private ImageView img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_list_view_project, null);
            holder = new ViewHolder();
            holder.title = convertView.findViewById(R.id.tv_project_item_title);
            holder.author = convertView.findViewById(R.id.tv_project_item_author);
            holder.desc = convertView.findViewById(R.id.tv_project_item_desc);
            holder.niceDate = convertView.findViewById(R.id.tv_project_item_date);
            holder.img = convertView.findViewById(R.id.iv_project_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (projectArticleList == null) return convertView;
        HTMLTextAnalysis.analysisAndSetText(holder.title, projectArticleList.get(position).getTitle());
        holder.author.setText(projectArticleList.get(position).getAuthor());
        holder.desc.setText(projectArticleList.get(position).getDesc());
        holder.niceDate.setText(projectArticleList.get(position).getNiceDate());
        holder.img.setImageBitmap(projectArticleList.get(position).getImg());
        return convertView;
    }
}
