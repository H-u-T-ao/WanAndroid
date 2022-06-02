package com.fengjiaxing.wanandroid.storage.shared;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.fengjiaxing.wanandroid.entity.LoginInformation;
import com.fengjiaxing.wanandroid.network.LoginRequester;

/**
 * @description 保存JSESSIONID的服务类
 */
public class LoginInformationShared extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (LoginRequester.jSessionId != null && LoginInformation.getUserName() != null) {
            String jSessionId = LoginRequester.jSessionId;
            String userName = LoginInformation.getUserName();
            SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
            editor.putString("jSessionId", jSessionId);
            editor.putString("userName", userName);
            editor.apply();
        }
        onDestroy();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}