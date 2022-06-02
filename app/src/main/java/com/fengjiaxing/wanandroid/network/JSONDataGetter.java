package com.fengjiaxing.wanandroid.network;

import com.fengjiaxing.wanandroid.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @description 封装GET和POST的类
 */
public class JSONDataGetter {

    static String methodGet(String urlString) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String data = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(R.integer.network_request_time_out);
            connection.setConnectTimeout(R.integer.network_request_time_out);
            InputStream in = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            data = result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return data;
    }

    static String methodPost(String urlString, String[] setName, String[] setInfo) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        OutputStreamWriter out = null;
        String data = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setReadTimeout(R.integer.network_request_time_out);
            connection.setConnectTimeout(R.integer.network_request_time_out);

            StringBuilder sb = new StringBuilder();
            out = new OutputStreamWriter(connection.getOutputStream());
            if (setName != null || setInfo != null) {
                for (int i = 0; i < setName.length; i++) {
                    if (i == 1) {
                        out.write(sb.append(setName[i]).append("=").append(setInfo[i]).toString());
                    } else {
                        out.write(sb.append("&").append(setName[i]).append("=").append(setInfo[i]).toString());
                    }
                }
                out.flush();
            }

            InputStream in = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            data = result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
        return data;
    }

}
