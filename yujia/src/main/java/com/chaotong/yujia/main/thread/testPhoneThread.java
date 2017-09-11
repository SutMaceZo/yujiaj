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
 * Created by Administrator on 2016/7/29 0029.
 */
public class testPhoneThread extends Thread {
    private String phone_number;
    Context context;
    Handler mHandler;
    String url;

    public testPhoneThread( Context context,String phone_number,Handler mHander,String url) {
        this.mHandler=mHander;
        this.phone_number=phone_number;
        this.context=context;
        this.url=url;
    }

    @Override
    public void run() {
        URL Httpurl;
        try {
            Httpurl=new URL(url);

            JSONObject object=new JSONObject();
            object.put("tel",phone_number);
            String content=String.valueOf(object);
            Log.i("info","url:"+url);
            Log.i("info","phone:"+content);
            HttpURLConnection conn=(HttpURLConnection) Httpurl.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("ser-Agent", "Fiddler");
            conn.setRequestProperty("Content-Type", "application/json");
            OutputStream os=conn.getOutputStream();
            os.write(content.getBytes());
            os.close();
            int code=conn.getResponseCode();
            Log.i("textphone","code:"+code+"");
            if(code==200)
            {
                BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String data;
                StringBuffer buffer=new StringBuffer();
                while ((data=reader.readLine())!=null){
                    buffer.append(data);
                }
                Message message=new Message();
                message.obj=buffer.toString();

                mHandler.sendMessage(message);
            } else {
                System.out.print("连接失败");
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
