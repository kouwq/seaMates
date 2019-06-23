package com.demo.hello.seamates;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class WebService {
    private static String TAG = "WebService";


    public static String executeHttpPost(String hostUrl, HashMap<String, String> hashMap) {
        String retStr = "";
        Log.i(TAG, "executeHttpPost()");
        try {
            //存储封装好的请求体信息
            StringBuilder stringBuilder = new StringBuilder();
            String s = "";
            if (hashMap.size() > 0) {
                for (HashMap.Entry<String, String> entry : hashMap.entrySet()) {
                    s += entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "utf-8") + "&";
                    stringBuilder.append("&").append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8"));
                    Log.i(TAG, "key= " + entry.getKey() + " and value= " + entry.getValue());
                }
            }
            stringBuilder.deleteCharAt(0);
            Log.i(TAG, "executeHttpPost: stringBuilder= " + stringBuilder);
            byte[] data = s.substring(0, s.length() - 1).getBytes();
            //服务器IP地址
            URL url = new URL("http://192.168.43.106:8080/mis_group" + hostUrl);
            Log.i(TAG, "executeHttpPost: url= " + url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000); //设置连接超时时间
            httpURLConnection.setDoInput(true); //打开输入流，以便从服务器获取数据
            httpURLConnection.setDoOutput(true); //打开输出流，以便向服务器提交数据
            httpURLConnection.setRequestMethod("POST"); //设置以Post方式提交数据
            httpURLConnection.setUseCaches(false); //使用Post方式不能使用缓存
            //设置请求体的类型是文本类型
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求体的长度
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
            //获得输出流，向服务器写入数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);
            int response = httpURLConnection.getResponseCode(); //获得服务器的响应码
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpURLConnection.getInputStream();
                retStr = inputStreamToString(inputStream); //处理服务器的响应结果
                Log.i(TAG, "retStr:" + retStr);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return retStr;
    }

    private static String inputStreamToString(InputStream inputStream) {
        String resultData; //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }

}
