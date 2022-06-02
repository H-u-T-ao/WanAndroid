package com.fengjiaxing.wanandroid.application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fengjiaxing.wanandroid.R;
import com.fengjiaxing.wanandroid.application.contacts.SystemContacts;
import com.fengjiaxing.wanandroid.application.presenter.SystemPresenter;

/**
 * @description 体系板块的Fragment
 */
public class SystemFragment extends Fragment implements SystemContacts.ISystemFragment {

    private ListView list;
    private ProgressBar request;

    private SystemContacts.ISystemPresenter mPresenter;

    private final int REQUEST_FALSE = -1;
    private final int INIT_LIST = 0;

    private final Handler handler = new Handler(Looper.myLooper()) {

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case INIT_LIST:
                    initList();
                    break;
                case REQUEST_FALSE:
                    Toast.makeText(getActivity(), R.string.time_out_text, Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_system, container, false);

        mPresenter = new SystemPresenter(this);

        list = view.findViewById(R.id.lv_system);
        request = view.findViewById(R.id.pb_system_request);

        mPresenter.request();

        return view;
    }

    private void initList() {
        SystemListViewAdapter adapter = new SystemListViewAdapter(getActivity());
        list.setAdapter(adapter);
        list.setVisibility(View.VISIBLE);
        list.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getActivity(), SystemChildrenActivity.class);
            intent.putExtra("position", "" + position);
            startActivity(intent);
        });
        request.setVisibility(View.GONE);
    }

    @Override
    public void requestSuccess() {
        Message message = new Message();
        message.what = INIT_LIST;
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