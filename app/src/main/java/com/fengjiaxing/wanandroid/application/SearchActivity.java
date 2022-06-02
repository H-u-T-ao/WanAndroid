package com.fengjiaxing.wanandroid.application;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fengjiaxing.wanandroid.R;
import com.fengjiaxing.wanandroid.application.contacts.SearchContacts;
import com.fengjiaxing.wanandroid.application.presenter.SearchPresenter;
import com.fengjiaxing.wanandroid.entity.ArticleList;
import com.fengjiaxing.wanandroid.util.SoftInputManager;
import com.fengjiaxing.wanandroid.util.TaskBarSetting;

import java.util.ArrayList;

/**
 * @description 搜索活动
 */
public class SearchActivity extends AppCompatActivity implements SearchContacts.ISearchActivity {

    private EditText searchBox; // 搜索内容输入
    private ListView list; // 列表View
    private ProgressBar request; // 中间的加载图标
    private RelativeLayout loading; // 下方的加载图标
    private TextView searchBtn; // 右上角搜索字样
    private LinearLayout line; // 热门搜索列表
    private TextView hotKeyText; // 热门搜索字样
    private int page; // 当前的页码

    private SearchContacts.ISearchPresenter mPresenter;

    private String search; // 要搜索的词汇
    private boolean endList = false;

    private ArticleListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mPresenter = new SearchPresenter(this);

        ImageView back = findViewById(R.id.iv_search_back);
        searchBtn = findViewById(R.id.tv_search_search);
        list = findViewById(R.id.lv_search);
        searchBox = findViewById(R.id.et_search_text);
        request = findViewById(R.id.pb_search_request);
        loading = findViewById(R.id.rl_search_loading);
        line = findViewById(R.id.ll_search_hot_key_line);
        hotKeyText = findViewById(R.id.tv_search_hot_key);

        mPresenter.requestHotKey();

        back.setOnClickListener(v -> {
            if (this.goBackToMain) {
                finish();
            } else {
                list.setVisibility(View.GONE);
                hotKeyText.setVisibility(View.VISIBLE);
                line.setVisibility(View.VISIBLE);
                this.goBackToMain = true;
            }
        });

        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    // 判断滚动到底部
                    if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                        mPresenter.requestSearchResult(search, page);
                        loading.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
            }
        });

        list.setOnItemLongClickListener((parent, view, position, id) -> {
            mPresenter.collect(position);
            return true;
        });

        searchBtn.setOnClickListener(v ->
        {
            request.setVisibility(View.VISIBLE);
            page = 0;
            search = searchBox.getText().toString();
            mPresenter.requestSearchResult(search, page);
            // 隐藏软键盘
            SoftInputManager.hide(this);
            list.setSelectionAfterHeaderView();
        });

    }

    private boolean goBackToMain = true;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (this.goBackToMain) {
                finish();
            } else {
                list.setVisibility(View.GONE);
                hotKeyText.setVisibility(View.VISIBLE);
                line.setVisibility(View.VISIBLE);
                this.goBackToMain = true;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void initHotKey() {
        ArrayList<Button> buttonList = mPresenter.buttonBuild();
        for (int i = 0; i < buttonList.size(); i++) {
            Button button = buttonList.get(i);
            button.setOnClickListener(v -> {
                request.setVisibility(View.VISIBLE);
                searchBox.setText(button.getText().toString());
                search = button.getText().toString();
                page = 0;
                mPresenter.requestSearchResult(search, page);
                list.setSelectionAfterHeaderView();
            });
            runOnUiThread(() -> line.addView(button));
        }
    }

    @Override
    public void initList() {
        runOnUiThread(() -> {
            adapter = new ArticleListViewAdapter(this, ArticleList.searchArticle);
            list.setAdapter(adapter);
            list.setOnItemClickListener((parent, view, position, id) -> {
                Intent webViewIntent = new Intent(SearchActivity.this, WebViewActivity.class);
                webViewIntent.putExtra("link", ArticleList.searchArticle.get(position).getLink());
                startActivity(webViewIntent);
            });
        });
    }

    @Override
    public void beginToSearch() {
        runOnUiThread(() -> {
            hotKeyText.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
            searchBtn.setEnabled(false);
            searchBtn.setTextColor(Color.rgb(175, 149, 221));
        });
    }

    @Override
    public void requestSuccess() {
        runOnUiThread(() -> {
            adapter.notifyDataSetChanged();
            list.setVisibility(View.VISIBLE);
            page++;
            goBackToMain = false;
            request.setVisibility(View.GONE);
            loading.setVisibility(View.GONE);
            searchBtn.setEnabled(true);
            searchBtn.setTextColor(Color.rgb(255, 255, 255));
        });
    }

    @Override
    public void requestFailure() {
        runOnUiThread(() -> Toast.makeText(this, R.string.time_out_text, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void requestEmpty() {
        runOnUiThread(() -> Toast.makeText(this, "你搜索的内容很特别，什么也没找到...", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void collectSuccess() {
        runOnUiThread(() -> Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void listEnd() {
        runOnUiThread(() -> {
            loading.setVisibility(View.GONE);
            if (!endList) {
                endList = true;
                Toast.makeText(this, "到底了~", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        TextView bar = findViewById(R.id.tv_search_task_bar);
        TaskBarSetting.hideAll(this, bar);
        super.onStart();
    }

    @Override
    public void onDestroy() {
        mPresenter = null;
        super.onDestroy();
    }
}
