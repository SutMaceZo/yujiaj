package com.chaotong.yujia.main.menu.benggong.wo_de_yu_yue;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MainActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.entity.HuiYuanBean.YuYueBean;
import com.chaotong.yujia.main.menu.benggong.Zxing.an.CaptureActivity;
import com.chaotong.yujia.main.menu.benggong.Zxing.an.DecodeDetail;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.facebook.drawee.view.SimpleDraweeView;
import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/8/20 0020.
 */

public class YuYueActivity extends BaseActivity {


    @Bind(R.id.yy_swfl)
     SwipeRefreshLayout yy_swfl;
    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
     ImageView yuyue_back;
    @Bind(R.id.progress)
     ProgressBar Bar;
    @Bind(R.id.yytext2)
    TextView yytext2;
    @Bind(R.id.class_type)
    TextView classtype;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.name)
    TextView jlname;
    @Bind(R.id.clubname)
    TextView cgname;
    @Bind(R.id.yy_number)
    TextView yy_number;

   /* @Bind(R.id.jiaolian_image)
    SimpleDraweeView jiaolian_image;*/
    @Bind(R.id.yytext1)
    TextView view_date;
    @Bind(R.id.yytext4)
    TextView view_jlname;
    @Bind(R.id.yytext3)
    TextView view_classtype;
    @Bind(R.id.yytext6)
    TextView view_cgname;
    @Bind(R.id.yytext5)
    TextView view_time;
    @Bind(R.id.yybt1)
    Button viewbt;
    UrlPath urlPath = UrlPath.getUrlPath();
    private String url = urlPath.getUrl() + "YyInfoServlet?";
    private String qxyy_url = urlPath.getUrl() + "QxYyServlet?";
    private String reqid = "";
    private String classid = "";
    private YuYueBean YuYue;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            if (json != null) {
                Bar.setVisibility(View.INVISIBLE);
                JSONObject object;
                try {
                    object = new JSONObject(json);
                    String code = object.getString("code");
                    String message = object.getString("msg");
                    if (code.equals("1")) {
                        JSONObject object1 = null;
                        object1 = object.getJSONObject("info");
                        String date = object1.getString("date");
                        String times = object1.getString("time");
                        String classid = object1.getString("classid");
                        String classname = object1.getString("classname");
                        String trainerid = object1.getString("trainerid");
                        String trainername = object1.getString("trainername");
                        String stadiumid = object1.getString("stadiumid");
                        String stadiumname = object1.getString("stadiumname");
                        String pic = object1.getString("pic");
                        String add = object1.getString("add");
                        String tell = object1.getString("tel");
                        String mobile_ = object1.getString("mobile");
                        String orderid = object1.getString("orderid");
                        kc_code = object1.getString("kc_code");
                        String kc_msg = object1.getString("kc_msg");
                        String week = object1.getString("week");

                        classtype.setText(classname);
                        time.setText("时间："+date+" "+times);
                        jlname.setText("教练："+StringEscapeUtils.unescapeJava(trainername));
                        cgname.setText("场馆："+stadiumname);

                        yy_number.setText("预约编号："+orderid);

                        view_jlname.setText(StringEscapeUtils.unescapeJava(trainername));
                        view_classtype.setText(classname);
                        view_time.setText(times);
                        view_cgname.setText(stadiumname);
                        viewbt.setText(kc_msg);
                        view_date.setText(date);
                        yytext2.setText(week);

                        /*if (pic!=null&&!pic.equals("")){
                            jiaolian_image.setImageURI(Uri.parse(pic));
                        }*/
                        init();

                    } else {
                        Toast.makeText(YuYueActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(YuYueActivity.this, getResources().getString(R.string.error), Toast.LENGTH_LONG).show();
            }
            super.handleMessage(msg);
        }
    };
    String kc_code;

    private void init() {
        if (kc_code.equals("2")) {
            viewbt.setEnabled(true);
            viewbt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bar.setVisibility(View.VISIBLE);
                    viewbt.setBackgroundResource(R.drawable.text_bg2);
                    new ThreadUtils_no(YuYueActivity.this, qxyy_url, mHandler).start();
                }
            });
        } else if (("0").equals(kc_code) || ("1").equals(kc_code)) {
            viewbt.setEnabled(true);
            viewbt.setBackgroundResource(R.drawable.text_bg2);
            viewbt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intents=new Intent(YuYueActivity.this, CaptureActivity.class);
                    startActivityForResult(intents,YYCODE);
                }
            });
        } else {
            viewbt.setEnabled(false);
            viewbt.setBackgroundResource(R.drawable.text_bg4);
        }
    }
    private int YYCODE=01;
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==YYCODE&&resultCode==RESULT_OK){
            if (data != null) {
                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);
              /*  result.setText("解码结果： \n" + content);*/
                //   qrCodeImage.setImageBitmap(bitmap);
                Intent intent12=new Intent(YuYueActivity.this, DecodeDetail.class);
                intent12.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent12.putExtra("classid",content);
                intent12.putExtra("reqid",reqid);
                startActivity(intent12);
        }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yuyue_detail);
        ButterKnife.bind(this);
        initView();

    }
Boolean reflash=false;

    @Override
    protected void onPause() {
        super.onPause();
        reflash=true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (reflash){
            initlistener();
            reflash=false;
        }
    }

    private void initView() {

        YuYue = new YuYueBean();

        reqid = getIntent().getStringExtra("reqid");
        classid = getIntent().getStringExtra("classid");
     //   Log.i("info", reqid + " " + classid);
        url = url + "reqid=" + reqid + "&classid=" + classid;
        qxyy_url = qxyy_url + "reqid=" + reqid + "&classid=" + classid;

        initlistener();
        viewbt.setTextColor(getResources().getColor(R.color.white));
        viewbt.setBackgroundResource(R.drawable.text_bg2);

        rl_bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        yy_swfl.setColorSchemeResources(R.color.lightblue,R.color.colorPrimary,R.color.
        lightyellow,R.color.red);
        yy_swfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                      initlistener();
                        yy_swfl.setRefreshing(false);
                    }
                }, 2000);

            }
        });
    }

    private void initlistener() {
        Bar.setVisibility(View.VISIBLE);
        new ThreadUtils_no(this, url, handler).start();
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            if (json!=null){
                Bar.setVisibility(View.INVISIBLE);
                viewbt.setEnabled(true);
                //viewbt.setBackgroundResource(R.drawable.text_bg);
                try {
                    JSONObject object = new JSONObject(json);
                    String code = object.getString("code");
                    String message = object.getString("msg");
                    String qxyy_code = object.getString("qxyy_code");
                    String qxyy_msg = object.getString("qxyy_msg");
                    if (code.equals("1")) {
                        if (qxyy_code.equals("11")) {
                            Toast.makeText(YuYueActivity.this, qxyy_msg, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(YuYueActivity.this, MainActivity.class);
                            intent.putExtra("x", 3);
                            YuYueActivity.this.startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(YuYueActivity.this, qxyy_msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            super.handleMessage(msg);
        }
    };

}
