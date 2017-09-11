package com.chaotong.yujia.main.thread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class huiyuan_registThread extends Thread{
    private String name,age,sex,password,tel,cardnum,pic,type,format,stadiums;
    Context context;
    private Handler mHandler;
    String url;
    public huiyuan_registThread(Context context, String name, String password, String sex, String age, String tel, String cardnum, String pic,
                                String format, String type,String stadiums, Handler hander,String url){
        this.cardnum=cardnum;
        this.context=context;
        this.name=name;
        this.password=password;
        this.sex=sex;
        this.age=age;
        this.tel=tel;
        this.pic=pic;
        this.format=format;
        this.type=type;
        this.stadiums=stadiums;
        this.url=url;
        this.mHandler=hander;
    }
    @Override
    public void run() {
        URL httpUrl;
        try {
            httpUrl=new URL(url);
            JSONObject JSONObject = new JSONObject();

            JSONObject.put("name",name);
            JSONObject.put("password",password);
            JSONObject.put("sex",sex);
            JSONObject.put("age",age);
            JSONObject.put("tel",tel);
            JSONObject.put("cardnum",cardnum);
            JSONObject.put("pic",pic);
            JSONObject.put("format",format);
            JSONObject.put("type",type);
            JSONObject.put("stadiums",stadiums);

            String content = String.valueOf(JSONObject);
            Log.i("huiyuan",content);

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
            Log.i("huiyuan","code:"+code+"");
            if (code == 200) {
                BufferedReader reader =new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String retData;
                StringBuffer responseData= new StringBuffer();
                while((retData = reader.readLine()) != null)
                {
                    responseData.append(retData);
                }
              Log.i("huiyuan","data:"+responseData);

                Message message=new Message();
                message.obj=responseData.toString();
                mHandler.sendMessage(message);
            } else {
                Toast.makeText(context,"连接失败",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.run();
    }



}
