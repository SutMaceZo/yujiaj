package com.chaotong.yujia.main.thread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.chaotong.yujia.main.adapter.changguanlist_adapter;
import com.chaotong.yujia.main.entity.changguanList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class Get_cgThread extends Thread {
    private Context context;
    private String url;
    private Handler handler;


    public Get_cgThread(Context context, String url, Handler handler) {
        this.context = context;
        this.url = url;
        this.handler=handler;

    }

    @Override
    public void run() {
        doPost();
    }

    public void doPost() {
        URL httpUrl;
        Log.i("url；",url);
        try {
            httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl
                    .openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            StringBuffer buffer = new StringBuffer();
            String string;
            while ((string = reader.readLine()) != null) {
                buffer.append(string);
            }
            Log.i("buffer:",buffer.toString());

            Message msg = new Message();
            msg.obj = buffer.toString();
            handler.sendMessage(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
