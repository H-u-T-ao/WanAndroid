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
import androidx.fragment.app.Fragment;

import com.fengjiaxing.wanandroid.R;
import com.fengjiaxing.wanandroid.application.contacts.ProjectListViewContacts;
import com.fengjiaxing.wanandroid.application.presenter.ProjectListViewPresenter;

/**
 * @description 项目板块的ViewPager的Fragment
 */
public class ProjectListViewFragment extends Fragment implements ProjectListViewContacts.IProjectListViewFragment {

    private ListView list;
    private ProgressBar request;
    private RelativeLayout loading;

    private final String projectArticleId;
    private final String projectArticleName;

    private boolean listEnd = false;

    private ProjectListViewAdapter adapter;

    private static final int REQUEST_FALSE = -1;
    private static final int REQUEST_SUCCESS = 0;
    private static final int INIT_LIST = 1;
    private static final int LIST_END = 2;

    private ProjectListViewContacts.IProjectListViewPresenter mPresenter;

    public ProjectListViewFragment(String projectArticleId, String projectArticleName) {
        this.projectArticleId = projectArticleId;
        this.projectArticleName = projectArticleName;
    }

    private final Handler handler = new Handler(Looper.myLooper()) {

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
                        Toast.makeText(getActivity(), "到底了~", Toast.LENGTH_LONG).show();
                        listEnd = true;
                    }
                default:
                    break;
            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_project_list_view, container, false);

        mPresenter = new ProjectListViewPresenter(this, projectArticleId);

        list = view.findViewById(R.id.lv_project);
        request = view.findViewById(R.id.pb_project_request);
        loading = view.findViewById(R.id.rl_project_loading);

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

    @Override
    public void init() {
        Message message = new Message();
        message.what = INIT_LIST;
        handler.sendMessage(message);
    }

    private void initList() {
        adapter = new ProjectListViewAdapter(getActivity(), this.projectArticleId);
        list.setAdapter(adapter);
        list.setOnItemClickListener((parent, view, position, id) -> {
            Intent webViewIntent = new Intent(getActivity(), WebViewActivity.class);
            webViewIntent.putExtra("link", mPresenter.getProjectArticleInformation().get(position).getLink());
            startActivity(webViewIntent);
        });
        list.setVisibility(View.VISIBLE);
        request.setVisibility(View.GONE);
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
        super.onDestroy();
    }

    public String getChildrenName() {
        return this.projectArticleName;
    }

}
