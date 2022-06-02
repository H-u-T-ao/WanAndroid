package com.fengjiaxing.wanandroid.application;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fengjiaxing.wanandroid.R;
import com.fengjiaxing.wanandroid.entity.Article;
import com.fengjiaxing.wanandroid.util.HTMLTextAnalysis;

import java.util.ArrayList;

/**
 * @description 所有无图Article列表通用的适配器
 * */
public class ArticleListViewAdapter extends BaseAdapter {

    private final LayoutInflater layoutInflater;
    private final ArrayList<Article> articleList;

    public ArticleListViewAdapter(Activity activity, ArrayList<Article> articleList) {
        this.layoutInflater = LayoutInflater.from(activity);
        this.articleList = articleList;
    }

    @Override
    public int getCount() {
        return this.articleList.size();
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
        private TextView category;
        private TextView niceDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_list_view_article, null);
            holder = new ViewHolder();
            holder.title = convertView.findViewById(R.id.tv_item_title);
            holder.author = convertView.findViewById(R.id.tv_item_author);
            holder.category = convertView.findViewById(R.id.tv_item_category);
            holder.niceDate = convertView.findViewById(R.id.tv_item_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HTMLTextAnalysis.analysisAndSetText(holder.title, this.articleList.get(position).getTitle());
        holder.author.setText(this.articleList.get(position).getAuthor());
        holder.category.setText(this.articleList.get(position).getChapter());
        holder.niceDate.setText(this.articleList.get(position).getNiceDate());
        return convertView;
    }
}
