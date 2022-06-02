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
import androidx.viewpager.widget.ViewPager;

import com.fengjiaxing.wanandroid.R;
import com.fengjiaxing.wanandroid.application.contacts.HomeContacts;
import com.fengjiaxing.wanandroid.application.presenter.HomePresenter;
import com.fengjiaxing.wanandroid.entity.ArticleList;
import com.fengjiaxing.wanandroid.entity.Banner;

import java.util.ArrayList;

/**
 * @description 主页板块的Fragment
 */
public class HomeFragment extends Fragment implements HomeContacts.IHomeFragment {

    private ListView list;
    private ProgressBar request;
    private RelativeLayout loading;

    private HomeContacts.IHomePresenter mPresenter;

    private int bannerNumber = 0;

    private static final int REQUEST_FALSE = -1;
    private static final int INIT_LIST = 0;
    private static final int NEXT_PAGE = 1;
    private static final int COLLECT_SUCCESS = 2;

    private ArticleListViewAdapter adapter; // list的适配器

    private final Handler handler = new Handler(Looper.myLooper()) {

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case REQUEST_FALSE:
                    Toast.makeText(getActivity(), R.string.time_out_text, Toast.LENGTH_LONG).show();
                    break;
                case INIT_LIST:
                    init();
                    break;
                case NEXT_PAGE:
                    adapter.notifyDataSetChanged();
                    loading.setVisibility(View.GONE);
                    break;
                case COLLECT_SUCCESS:
                    Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, container, false);

        mPresenter = new HomePresenter(this);

        list = view.findViewById(R.id.lv_home_list_view);
        loading = view.findViewById(R.id.rl_home_loading);
        request = view.findViewById(R.id.pb_home_request);

        mPresenter.requestHomeArticle();

        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    // 判断滚动到底部
                    if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                        mPresenter.requestHomeArticle();
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

    private void init() {
        adapter = new ArticleListViewAdapter(getActivity(), ArticleList.homeArticle);
        list.setAdapter(adapter);

        // 初始化banner的界面
        View banner = getLayoutInflater().inflate(R.layout.item_home_view_pager, null);
        ViewPager bannerViewPager = banner.findViewById(R.id.vp_banner);
        ArrayList<BannerFragment> bannerFragmentList = new ArrayList<>();
        for (int i = 0; i < Banner.getTitle().size(); i++) {
            BannerFragment bannerFragment = new BannerFragment(i);
            bannerFragmentList.add(bannerFragment);
        }
        bannerViewPager.setAdapter(new HomeBannerViewPagerAdapter(getChildFragmentManager(), bannerFragmentList));

        // 新开线程用于控制banner的轮播
        new Thread(() -> {
            for (; ; bannerNumber++) {
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() ->
                            bannerViewPager.setCurrentItem(bannerNumber % Banner.getTitle().size()));
                }
            }
        }).start();

        // 加载banner到list的第一个item中
        list.addHeaderView(banner);

        list.setOnItemClickListener((parent, view, position, id) -> {
            Intent webViewIntent = new Intent(getActivity(), WebViewActivity.class);
            webViewIntent.putExtra("link", ArticleList.homeArticle.get(position - 1).getLink());
            startActivity(webViewIntent);
        });

        // 长按收藏
        list.setOnItemLongClickListener((parent, view, position, id) -> {
            mPresenter.collect(position - 1);
            return true;
        });
        list.setVisibility(View.VISIBLE);
        request.setVisibility(View.GONE);
    }

    @Override
    public void initList() {
        Message message = new Message();
        message.what = INIT_LIST;
        handler.sendMessage(message);
    }

    @Override
    public void requestSuccess() {
        Message message = new Message();
        message.what = NEXT_PAGE;
        handler.sendMessage(message);
    }

    @Override
    public void requestFailure() {
        Message message = new Message();
        message.what = REQUEST_FALSE;
        handler.sendMessage(message);
    }

    @Override
    public void collectSuccess() {
        Message message = new Message();
        message.what = COLLECT_SUCCESS;
        handler.sendMessage(message);
    }

    @Override
    public void onDestroy() {
        mPresenter = null;
        super.onDestroy();
    }

}
