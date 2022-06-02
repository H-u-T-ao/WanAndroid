package com.fengjiaxing.wanandroid.network;

import android.content.Context;

import com.fengjiaxing.wanandroid.R;
import com.fengjiaxing.wanandroid.application.contacts.LoginContacts;
import com.fengjiaxing.wanandroid.application.contacts.MeContacts;
import com.fengjiaxing.wanandroid.entity.LoginInformation;
import com.fengjiaxing.wanandroid.util.JSessionIdGetter;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @description 发送登录和退出登录请求
 */
public class LoginRequester implements LoginContacts.ILoginModel, MeContacts.IMeModel {

    public static String jSessionId;

    public void newLogin(String userName, String password) {
        HttpURLConnection connection = null;
        OutputStreamWriter out = null;
        try {
            URL url = new URL("https://www.wanandroid.com/user/login");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setReadTimeout(R.integer.network_request_time_out);
            connection.setConnectTimeout(R.integer.network_request_time_out);

            out = new OutputStreamWriter(connection.getOutputStream());
            out.write("username=" + userName);
            out.write("&password=" + password);
            out.flush();

            Map<String, List<String>> cookies = connection.getHeaderFields();
            List<String> cookie = cookies.get("Set-Cookie");
            if (cookie != null && cookie.size() == 5) {
                LoginInformation.setCookie(cookies.get("Set-Cookie"));
                LoginInformation.setUserName(userName);
                LoginInformation.setSuccess(true);
                jSessionId = JSessionIdGetter.getJSessionId();
            } else {
                LoginInformation.setSuccess(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void loginOut(Context context) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("https://www.wanandroid.com/user/logout/json");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(R.integer.network_request_time_out);
            connection.setConnectTimeout(R.integer.network_request_time_out);

            connection.setRequestProperty("cookie", LoginRequester.jSessionId);

            LoginInformation.setSuccess(false);
            LoginInformation.setUserName("点击此处以登录");
            LoginInformation.setCookie(null);

            File file = new File("/data/data/" + context.getPackageName() + "/shared_prefs/user.xml");
            if (file.exists()) {
                file.delete();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}
