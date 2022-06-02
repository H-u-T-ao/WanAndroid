package com.fengjiaxing.wanandroid.network;

import com.fengjiaxing.wanandroid.R;
import com.fengjiaxing.wanandroid.application.contacts.RegisterContacts;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @description 发送注册请求
 * */
public class RegisterRequester implements RegisterContacts.IRegisterModel {

    public boolean register(String userName, String password, String rePassword) {

        HttpURLConnection connection = null;
        OutputStreamWriter out = null;
        List<String> cookie = null;
        try {
            URL url = new URL("https://www.wanandroid.com/user/register");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setReadTimeout(R.integer.network_request_time_out);
            connection.setConnectTimeout(R.integer.network_request_time_out);

            out = new OutputStreamWriter(connection.getOutputStream());
            out.write("username=" + userName);
            out.write("&password=" + password);
            out.write("&repassword=" + rePassword);
            out.flush();

            Map<String, List<String>> cookies = connection.getHeaderFields();
            cookie = cookies.get("Set-Cookie");

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

        return cookie != null && cookie.size() != 1;

    }

}
