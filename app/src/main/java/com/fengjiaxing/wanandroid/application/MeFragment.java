package com.fengjiaxing.wanandroid.application;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fengjiaxing.wanandroid.R;
import com.fengjiaxing.wanandroid.application.contacts.MeContacts;
import com.fengjiaxing.wanandroid.application.presenter.MePresenter;

/**
 * @description 我板块的Fragment
 */
public class MeFragment extends Fragment implements MeContacts.IMeFragment {

    private String userNameText;
    private TextView userName;
    private RelativeLayout login;

    private static final int REQUEST_FALSE = -1;
    private static final int LOGIN_OUT_SUCCESS = 0;
    private static final int PLEASE_LOGIN = 1;
    private static final int LOGIN_CHANGE = 2;

    private MeContacts.IMePresenter mPresenter;

    private final Handler handler = new Handler(Looper.myLooper()) {

        @Override
        public void handleMessage(@NonNull Message message) {
            switch (message.what) {
                case REQUEST_FALSE:
                    Toast.makeText(getContext(), R.string.time_out_text, Toast.LENGTH_SHORT).show();
                    break;
                case LOGIN_OUT_SUCCESS:
                    userName.setText(userNameText);
                    login.setEnabled(true);
                    Toast.makeText(getContext(), "退出登录成功", Toast.LENGTH_SHORT).show();
                    break;
                case PLEASE_LOGIN:
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    break;
                case LOGIN_CHANGE:
                    changeUserName();
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_me, container, false);

        mPresenter = new MePresenter(this);

        login = view.findViewById(R.id.rl_me_login);
        userName = view.findViewById(R.id.tv_me_user_name);
        RelativeLayout collection = view.findViewById(R.id.rl_me_collection);
        RelativeLayout about = view.findViewById(R.id.rl_me_about);
        RelativeLayout loginOut = view.findViewById(R.id.rl_me_login_out);

        if (mPresenter.checkLogin() == null) {
            login.setEnabled(true);
            userNameText = "点击此处以登录";
            Message message = new Message();
            message.what = LOGIN_CHANGE;
            handler.sendMessage(message);
        } else {
            login.setEnabled(false);
            userName.setText(mPresenter.checkLogin());
        }

        login.setOnClickListener(v -> {
            if (mPresenter.checkLogin() != null) return;
            Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
            loginIntent.getStringExtra("userName");
            startActivityForResult(loginIntent, 1);
        });

        collection.setOnClickListener(v -> {
            if (mPresenter.checkLogin() == null) {
                Message message = new Message();
                message.what = PLEASE_LOGIN;
                handler.sendMessage(message);
                return;
            }
            Intent myCollectionIntent = new Intent(getActivity(), MyCollectionActivity.class);
            startActivity(myCollectionIntent);
        });

        about.setOnClickListener(v ->
        {
            Intent aboutIntent = new Intent(getActivity(), AboutActivity.class);
            startActivity(aboutIntent);
        });

        loginOut.setOnClickListener(v -> {
            if (mPresenter.checkLogin() == null) {
                Message message = new Message();
                message.what = PLEASE_LOGIN;
                handler.sendMessage(message);
            } else {
                mPresenter.loginOut();
            }

        });

        return view;
    }

    private void changeUserName() {
        userName.setText(userNameText);
        Message message = new Message();
        message.what = LOGIN_CHANGE;
        handler.sendMessage(message);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            userNameText = data.getStringExtra("userName");
            Message message = new Message();
            message.what = LOGIN_CHANGE;
            handler.sendMessage(message);
        }
    }

    @Override
    public void loginOutFinish() {
        userNameText = "点击此处以登录";
        Message message = new Message();
        message.what = LOGIN_OUT_SUCCESS;
        handler.sendMessage(message);
    }

    @Override
    public void requestFailure() {
        Message message = new Message();
        message.what = REQUEST_FALSE;
        handler.sendMessage(message);
    }

    @Override
    public void onDestroy() {
        mPresenter = null;
        super.onDestroy();
    }

}
