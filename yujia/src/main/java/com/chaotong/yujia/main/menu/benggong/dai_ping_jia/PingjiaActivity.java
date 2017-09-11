package com.chaotong.yujia.main.menu.benggong.dai_ping_jia;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MainActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.thread.ThreadUtils;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/8/13 0013.
 */

public class PingjiaActivity extends BaseActivity implements RatingBar.OnRatingBarChangeListener {
    @Bind(R.id.pingjia_edit)
     EditText pingjia_edit;

    @Bind(R.id.btn_pingjia)
     Button pingjia_btn;

    @Bind(R.id.bt_back)
    ImageView bt_back;

    @Bind(R.id.ratingBar01)
     RatingBar ratingBar01;
    @Bind(R.id.ratingBar02)
     RatingBar ratingBar02;
    @Bind(R.id.ratingBar03)
     RatingBar ratingBar03;
    UrlPath urlPath = UrlPath.getUrlPath();
    private String teach_level = "";
    private String serv_level = "";
    private String beauty_level = "";
    private String url = urlPath.getUrl() + "PjServlet";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            try {
                JSONObject object = new JSONObject(json);
                String code = object.getString("code");
                String message = object.getString("msg");
                pingjia_btn.setBackgroundResource(R.drawable.text_bg4);
                if (code.equals("1")) {
                    Toast.makeText(PingjiaActivity.this, message, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(PingjiaActivity.this, MainActivity.class);
                    intent.putExtra("x", 3);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }else {
                    ToastUtils.showToast(PingjiaActivity.this,message);
                }
                //code
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.handleMessage(msg);
        }
    };
    String pjrid;
    String classid;
    String reqid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.huiyuan_pingjia);
        ButterKnife.bind(this);
        initRatingBar();
        pjrid = getIntent().getStringExtra("trainerid");
        classid = getIntent().getStringExtra("classid");
        reqid = getIntent().getStringExtra("reqid");
        pingjia_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pjmsg = pingjia_edit.getText().toString();
                String jx = teach_level;
                String fw = serv_level;
                String yb = beauty_level;
                JSONObject object = new JSONObject();
                String content = "";
                try {
                    object.put("reqid", reqid);
                    object.put("pjrid", pjrid);
                    object.put("classid", classid);
                    object.put("pjmsg", pjmsg);
                    object.put("jx", jx);
                    object.put("fw", fw);
                    object.put("yb", yb);
                    content = String.valueOf(object);
                    Log.i("info", content);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pingjia_btn.setBackgroundResource(R.drawable.text_bg4);
                new ThreadUtils(PingjiaActivity.this, url, content, handler).start();
            }
        });

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void initRatingBar() {
        ratingBar01.setMax(100); //设置最大刻度
        ratingBar01.setProgress(0); //设置当前刻度
        ratingBar01.setOnRatingBarChangeListener(this);

        ratingBar02.setMax(100); //设置最大刻度
        ratingBar02.setProgress(0); //设置当前刻度
        ratingBar02.setOnRatingBarChangeListener(this);

        ratingBar03.setMax(100); //设置最大刻度
        ratingBar03.setProgress(0); //设置当前刻度
        ratingBar03.setOnRatingBarChangeListener(this);
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        switch (ratingBar.getId()) {
            case R.id.ratingBar01:
             /*   int progress = (int)ratingBar.getProgress(); //获得当前的刻度
                // TODO Auto-generated method stub
                Toast.makeText(PingjiaActivity.this, "progress: " + progress + "rating: " + v, Toast.LENGTH_LONG).show();*/
                //teach_level = v + "";
                teach_level =new BigDecimal(ratingBar.getRating()).setScale(1,BigDecimal.ROUND_HALF_UP).floatValue()+"";
                break;
            case R.id.ratingBar02:
              /*  int progress01 = (int)ratingBar.getProgress(); //获得当前的刻度

                // TODO Auto-generated method stub
                Toast.makeText(PingjiaActivity.this, "progress: " + progress01 + "rating: " + v, Toast.LENGTH_LONG).show();*/
                serv_level = v + "";
                serv_level =new BigDecimal(ratingBar.getRating()).setScale(1,BigDecimal.ROUND_HALF_UP).floatValue()+"";
                break;
            case R.id.ratingBar03:
                /*int progress02 = (int)ratingBar.getProgress(); //获得当前的刻度

                // TODO Auto-generated method stub
                Toast.makeText(PingjiaActivity.this, "progress: " + progress02+ "rating: " + v, Toast.LENGTH_LONG).show();*/
                //beauty_level = v + "";
                beauty_level = new BigDecimal(ratingBar.getRating()).setScale(1,BigDecimal.ROUND_HALF_UP).floatValue()+"";
                break;
        }

    }


}
