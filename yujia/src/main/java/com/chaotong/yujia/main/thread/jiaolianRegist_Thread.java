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
 * Created by Administrator on 2016/8/4 0004.
 */
public class jiaolianRegist_Thread extends Thread {
    String Url, name, sex, age, changguan, pic, classes, phone, password,type,format,privence,city,district;
    Handler handler;

    public jiaolianRegist_Thread(String Url, String type, String name, String password, String sex,
                                 String phone, String changguan,
                                 String age, String pic, String format, String classes,String privence,String city,String district, Handler handler) {

        this.Url = Url;
        this.age = age;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.changguan = changguan;
        this.classes = classes;
        this.pic = pic;
        this.password = password;
        this.type=type;
        this.format=format;

        this.privence=privence;
        this.city=city;
        this.district=district;

        this.handler=handler;
    }

    @Override
    public void run() {
        URL httpUrl;
        try {
            httpUrl = new URL(Url);
            JSONObject JSONObject = new JSONObject();

            JSONObject.put("name", name);
            JSONObject.put("password", password);
            JSONObject.put("sex", sex);
            JSONObject.put("age", age);
            JSONObject.put("tel", phone);

            JSONObject.put("type", type);
            JSONObject.put("classtype",classes);

            JSONObject.put("province",privence);
            JSONObject.put("city",city);
            JSONObject.put("district",district);

            JSONObject.put("format",format);
            JSONObject.put("stadiums",changguan);
            JSONObject.put("pic", pic);
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

            Log.i("jiaolian",city+"");
            Log.i("jiaolian",content);
            if (code == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String retData;
                StringBuffer responseData = new StringBuffer();
                while ((retData = reader.readLine()) != null) {
                    responseData.append(retData);
                }
               Log.i("jiaolian",responseData.toString());
            Message message=new Message();
                message.obj=responseData.toString();
                handler.sendMessage(message);
            } else {
                System.out.print("连接失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
