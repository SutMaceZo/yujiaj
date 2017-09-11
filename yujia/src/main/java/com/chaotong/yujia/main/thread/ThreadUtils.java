package com.chaotong.yujia.main.thread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
public class ThreadUtils extends Thread {
    Context context;
    String url;
    String content;
    Handler handler;
    public ThreadUtils(Context context, String url, String content, Handler handler) {
        this.content=content;
        this.context=context;
        this.url=url;
        this.handler=handler;
    }
public void doPost(){
    URL httpUrl;
    try {
        Log.i("url","url:"+url);
        httpUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
        conn.setConnectTimeout(5000);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("ser-Agent", "Fiddler");
        conn.setRequestProperty("Content-Type", "application/json");
        OutputStream os = conn.getOutputStream();
        os.write(content.getBytes());
        os.close();
        int code = conn.getResponseCode();

        Log.i("Thread",code+"");
        if (code == 200) {
            BufferedReader reader =new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String retData;
            StringBuffer responseData= new StringBuffer();
            while((retData = reader.readLine()) != null)
            {
                responseData.append(retData);
            }
            Log.i("result",responseData.toString());
            Message message=new Message();
            message.obj=responseData.toString();
            handler.sendMessage(message);
        } else {
            System.out.print("连接失败");
        }
    } catch (Exception e) {
       // e.printStackTrace();
    }
}
    @Override
    public void run() {
        doPost();
    }
}
