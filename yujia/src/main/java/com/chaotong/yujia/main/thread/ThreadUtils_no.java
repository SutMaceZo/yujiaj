package com.chaotong.yujia.main.thread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
public class ThreadUtils_no extends Thread {
    Context context;
    String url;
    Handler handler;
    public ThreadUtils_no(Context context, String url, Handler handler) {
        this.context=context;
        this.url=url;
        this.handler=handler;
    }
    private void doGet() {
        // get和post提交方式的不同：url的书写规则
        URL httpUrl;
        try {
            httpUrl = new URL(url);
            Log.i("url",url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl
                    .openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");// http请求方式GET，拥有自己的请求规则，如：url格式
            // BufferReader从字符输入流中读取文本,缓冲各个字符,从而提供字符、数组和行的高效读取
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String str;
            StringBuffer buffer = new StringBuffer();
            while ((str = reader.readLine()) != null) {
                buffer.append(str);
            }
         Log.i("result",buffer.toString());
            Message message=new Message();
            message.obj=buffer.toString();
            handler.sendMessage(message);
        } catch (Exception e) {
            // TODO Auto-generated catch block
           // e.printStackTrace();
        }
    }
    @Override
    public void run() {
        doGet();
    }
}
