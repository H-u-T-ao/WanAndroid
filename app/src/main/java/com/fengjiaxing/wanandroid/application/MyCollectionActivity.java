package com.fengjiaxing.wanandroid.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fengjiaxing.wanandroid.R;
import com.fengjiaxing.wanandroid.application.contacts.MyCollectionContacts;
import com.fengjiaxing.wanandroid.application.presenter.MyCollectionPresenter;
import com.fengjiaxing.wanandroid.entity.ArticleList;
import com.fengjiaxing.wanandroid.util.TaskBarSetting;

/**
 * @description 我板块的收藏活动
 */
public class MyCollectionActivity extends AppCompatActivity implements MyCollectionContacts.IMyCollectionActivity {

    private ListView list;
    private ProgressBar request;
    private RelativeLayout loading;

    private boolean listEnd = false;

    private MyCollectionPresenter mPresenter;

    private ArticleListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_my_collection);

        mPresenter = new MyCollectionPresenter(this);

        list = findViewById(R.id.lv_collection);
        request = findViewById(R.id.pb_collection_request);
        loading = findViewById(R.id.rl_collection_loading);
        ImageView back = findViewById(R.id.iv_collection_back);

        mPresenter.request();

        back.setOnClickListener(v -> finish());

        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    // 判断滚动到底部
                    if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                        mPresenter.request();
                        loading.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
            }
        });

        list.setOnItemClickListener((parent, view, position, id) -> {
            Intent wenView = new Intent(MyCollectionActivity.this, WebViewActivity.class);
            wenView.putExtra("link", ArticleList.myCollectionArticle.get(position).getLink());
            startActivity(wenView);
        });

        list.setOnItemLongClickListener((parent, view, position, id) -> {
            mPresenter.cancel(position);
            return true;
        });
    }

    @Override
    public void initList() {
        runOnUiThread(() -> {
            adapter = new ArticleListViewAdapter(this, ArticleList.myCollectionArticle);
            list.setAdapter(adapter);
            request.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void requestSuccess() {
        runOnUiThread(() -> {
            adapter.notifyDataSetChanged();
            loading.setVisibility(View.GONE);
        });
    }

    @Override
    public void requestFailure() {
        runOnUiThread(() -> Toast.makeText(this, R.string.time_out_text, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void cancelSuccess() {
        runOnUiThread(() -> {
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "取消收藏成功", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void listEnd() {
        runOnUiThread(() -> loading.setVisibility(View.GONE));
        if (!listEnd) {
            runOnUiThread(() -> Toast.makeText(this, "到底了~", Toast.LENGTH_SHORT).show());
            listEnd = true;
        }
    }

    @Override
    public void onStart() {
        TextView statusBar = findViewById(R.id.tv_collection_task_bar);
        TaskBarSetting.hideAll(this, statusBar);
        super.onStart();
    }

    @Override
    public void onDestroy() {
        mPresenter = null;
        super.onDestroy();
    }

}
