package com.fengjiaxing.wanandroid.application;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fengjiaxing.wanandroid.R;
import com.fengjiaxing.wanandroid.application.contacts.SystemChildrenListContacts;
import com.fengjiaxing.wanandroid.application.presenter.SystemChildrenListPresenter;
import com.fengjiaxing.wanandroid.entity.ArticleList;

/**
 * @description 体系板块子项目活动的ViewPager的Fragment
 */
public class SystemChildrenListFragment extends Fragment implements SystemChildrenListContacts.ISystemChildrenListFragment {

    private ListView list;
    private ProgressBar request;
    private RelativeLayout loading;

    private final String systemChildrenId;
    private final String systemChildrenName;

    private boolean listEnd = false;

    private ArticleListViewAdapter adapter;

    private static final int REQUEST_FALSE = -1;
    private static final int REQUEST_SUCCESS = 0;
    private static final int INIT_LIST = 1;
    private static final int LIST_END = 2;
    private static final int COLLECTION_SUCCESS = 3;

    private SystemChildrenListContacts.ISystemChildrenListPresenter mPresenter;
    private SystemChildrenListFragment.MyHandler handler;

    public SystemChildrenListFragment(String systemChildrenId, String systemChildrenName) {
        this.systemChildrenId = systemChildrenId;
        this.systemChildrenName = systemChildrenName;
    }

    private static class MyHandler extends Handler {

        private final SystemChildrenListFragment mFragment;

        public MyHandler(SystemChildrenListFragment mFragment) {
            super(Looper.myLooper());
            this.mFragment = mFragment;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case REQUEST_FALSE:
                    Toast.makeText(mFragment.getActivity(), R.string.time_out_text, Toast.LENGTH_LONG).show();
                    break;
                case REQUEST_SUCCESS:
                    mFragment.nextPage();
                    break;
                case INIT_LIST:
                    mFragment.initList();
                    break;
                case LIST_END:
                    mFragment.loading.setVisibility(View.GONE);
                    if (!mFragment.listEnd) {
                        Toast.makeText(mFragment.getActivity(), "到底了~", Toast.LENGTH_SHORT).show();
                        mFragment.listEnd = true;
                    }
                case COLLECTION_SUCCESS:
                    Toast.makeText(mFragment.getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
                default:
                    break;
            }
        }
    }

    /*private final Handler handler = new Handler(Looper.myLooper()) {

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case REQUEST_FALSE:
                    Toast.makeText(getActivity(), R.string.time_out_text, Toast.LENGTH_LONG).show();
                    break;
                case REQUEST_SUCCESS:
                    nextPage();
                    break;
                case INIT_LIST:
                    initList();
                    break;
                case LIST_END:
                    loading.setVisibility(View.GONE);
                    if (!listEnd) {
                        Toast.makeText(getActivity(), "到底了~", Toast.LENGTH_SHORT).show();
                        listEnd = true;
                    }
                case COLLECTION_SUCCESS:
                    Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
                default:
                    break;
            }
        }
    };*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_system_children, container, false);

        mPresenter = new SystemChildrenListPresenter(this, systemChildrenId);
        handler = new SystemChildrenListFragment.MyHandler(this);

        list = view.findViewById(R.id.lv_system_children);
        request = view.findViewById(R.id.pb_system_children_request);
        loading = view.findViewById(R.id.rl_system_children_loading);

        mPresenter.request();

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

        return view;
    }

    private void initList() {
        adapter = new ArticleListViewAdapter(getActivity(), ArticleList.systemArticle.get(systemChildrenId));
        list.setAdapter(adapter);
        list.setOnItemClickListener((parent, view, position, id) -> {
            String link = ArticleList.systemArticle.get(systemChildrenId).get(position).getLink();
            if (link != null) {
                Intent webViewIntent = new Intent(getActivity(), WebViewActivity.class);
                webViewIntent.putExtra("link", link);
                startActivity(webViewIntent);
            }
        });
        list.setOnItemLongClickListener((parent, view, position, id) -> {
            mPresenter.collect(position);
            return true;
        });
        list.setVisibility(View.VISIBLE);
        request.setVisibility(View.GONE);
    }

    @Override
    public void init() {
        Message message = new Message();
        message.what = INIT_LIST;
        handler.sendMessage(message);
    }

    @Override
    public void collectSuccess() {
        Message message = new Message();
        message.what = COLLECTION_SUCCESS;
        handler.sendMessage(message);
    }

    @Override
    public void requestSuccess() {
        Message message = new Message();
        message.what = REQUEST_SUCCESS;
        handler.sendMessage(message);
    }

    private void nextPage() {
        adapter.notifyDataSetChanged();
        loading.setVisibility(View.GONE);
    }

    @Override
    public void requestFailure() {
        Message message = new Message();
        message.what = REQUEST_FALSE;
        handler.sendMessage(message);
    }

    @Override
    public void listEnd() {
        Message message = new Message();
        message.what = LIST_END;
        handler.sendMessage(message);
    }

    @Override
    public void onDestroy() {
        mPresenter = null;
        handler = null;
        super.onDestroy();
    }

    public String getChildrenName() {
        return systemChildrenName;
    }

}
