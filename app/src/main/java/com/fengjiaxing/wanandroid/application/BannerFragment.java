package com.fengjiaxing.wanandroid.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fengjiaxing.wanandroid.R;
import com.fengjiaxing.wanandroid.application.WebViewActivity;
import com.fengjiaxing.wanandroid.entity.Banner;

/**
 * @description 主页上方banner的ViewPager的Fragment
 * */
public class BannerFragment extends Fragment {

    private final int position;

    public BannerFragment(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_list_view_banner, container, false);

        ImageView imageView = view.findViewById(R.id.iv_banner);
        TextView textView = view.findViewById(R.id.tv_banner);

        imageView.setImageBitmap(Banner.getImg().get(position));
        textView.setText(Banner.getTitle().get(position));

        imageView.setOnClickListener(v -> {
            Intent webViewIntent = new Intent(getActivity(), WebViewActivity.class);
            webViewIntent.putExtra("link", Banner.getLink().get(position));
            startActivity(webViewIntent);
        });

        return view;
    }
}
