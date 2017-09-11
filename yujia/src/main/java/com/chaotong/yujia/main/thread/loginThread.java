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
 * Created by Administrator on 2016/7/22 0022.
 */
public class loginThread extends Thread {
    private String username,password,URL,registrationID;
    Context context;
    Handler mHandler;
    public loginThread(Context context, String loginName, String password,String registrationID, String url, Handler mHandler){
        this.username=loginName;
        this.password=password;
        this.context=context;
        this.URL=url;
        this.mHandler=mHandler;
        this.registrationID=registrationID;
    }

   public void doPost(){
       URL httpUrl;
       try {
           httpUrl = new URL(URL);
           JSONObject JSONObject = new JSONObject();
           JSONObject.put("username", username);
           JSONObject.put("password", password);
           JSONObject.put("registrationID",registrationID);
           String content = String.valueOf(JSONObject);

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
           System.out.print("code---->"+code);
           if (code == 200) {
               BufferedReader reader =new BufferedReader(new InputStreamReader(conn.getInputStream()));
               String retData;
               StringBuffer responseData= new StringBuffer();
               while((retData = reader.readLine()) != null)
               {
                   responseData.append(retData);
               }
               Log.i("login",responseData.toString());
               Message message=new Message();
               message.obj=responseData.toString();
               mHandler.sendMessage(message);
           } else {
               System.out.print("连接失败");
           }
       } catch (Exception e) {
           //e.printStackTrace();
       }
   }
    @Override
    public void run() {
        doPost();
    }
}
