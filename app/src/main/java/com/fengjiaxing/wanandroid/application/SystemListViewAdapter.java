package com.fengjiaxing.wanandroid.application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fengjiaxing.wanandroid.R;
import com.fengjiaxing.wanandroid.entity.System;

/**
 * @description 体系板块的列表的适配器
 * */
public class SystemListViewAdapter extends BaseAdapter {

    private final LayoutInflater layoutInflater;

    public SystemListViewAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return System.getTitle().size();
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
        private TextView childrenTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_list_view_system, null);
            holder = new ViewHolder();
            holder.title = convertView.findViewById(R.id.tv_system_item_title);
            holder.childrenTitle = convertView.findViewById(R.id.tv_system_item_category);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(System.getTitle().get(position));
        holder.childrenTitle.setText(System.getChildrenName().get(position));
        return convertView;
    }
}
