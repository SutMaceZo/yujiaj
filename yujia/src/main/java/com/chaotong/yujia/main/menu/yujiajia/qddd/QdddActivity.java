package com.chaotong.yujia.main.menu.yujiajia.qddd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.property.yueke.YhjS;
import com.chaotong.yujia.main.property.yueke.Yyzf;
import com.chaotong.yujia.main.property.yujiajia.FindMoreTrainers;
import com.chaotong.yujia.main.property.yujiajia.YhjPro;
import com.chaotong.yujia.main.property.yujiaolian.Order;
import com.chaotong.yujia.main.property.yujiaolian.OrderInfo;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.google.gson.Gson;

import org.apache.commons.lang.StringEscapeUtils;


import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 确定订单
 */

public class QdddActivity extends BaseActivity implements View.OnClickListener {

    private String id;
    SharedPreferences sp;

    private String yyType;//预约方式
    private String cardId = "";//卡或者优惠卷id
    private FindMoreTrainers findMoreTrainers;
    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
    ImageView bt_back;
    @Bind(R.id.textView35)
     TextView textView35;//课程名
    @Bind(R.id.textView36)
     TextView textView36;//课程时间
    @Bind(R.id.textView37)
     TextView textView37;//教练名字
    @Bind(R.id.textView39)
     TextView textView39;//场馆名称
    @Bind(R.id.textView40)
     TextView textView40;//预约编号
    @Bind(R.id.button10)
     Button button10;//
    @Bind(R.id.radooGroup)
     RadioGroup radioGroup;
    @Bind(R.id.nianhui)
     RadioButton card;//会员卡
    @Bind(R.id.youhuijuan)
     RadioButton youhuijuan;//优惠卷
    UrlPath urlPath1 = UrlPath.getUrlPath();
    private OrderInfo orderInfo;//订单详情
    private YhjS order;//优惠券列表
    private String reqid;
    private String classId;
    private PopupWindow popupWindow;
    private Yyzf yyzf;//预约支付

    @Bind(R.id.progress)
     ProgressBar progress;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json=msg.obj.toString();
            if (!json.equals("")&&json!=null){ //修改判断
                progress.setVisibility(View.INVISIBLE);
                Gson gson = new Gson();
                yyzf = new Yyzf();
                yyzf = gson.fromJson(json, Yyzf.class);
                if (order != null) {
                    String yy_msg=yyzf.getYy_msg();
                    String yy_code=yyzf.getYy_code();
                    if(yy_code != null&&("1").equals(yy_code)&&yy_msg!=null){
                        button10.setText(yyzf.getYy_msg());
                        button10.setEnabled(true);
                        finish();
                    }else {
                        Toast.makeText(QdddActivity.this, yyzf.getYy_msg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }else{
                progress.setVisibility(View.INVISIBLE);
                Toast.makeText(QdddActivity.this,"下单失败，请重试",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qddd);
        ButterKnife.bind(this);
        rl_bt_back.setOnClickListener(this);
        button10.setOnClickListener(this);
        initView();
        initListener();
    }


    public void initView() {

        SharedPreferences sp = getSharedPreferences("Yuga", MODE_PRIVATE);
        reqid = sp.getString("reqid", "");
        Intent intent = getIntent();
        if (intent.getStringExtra("classid") != null) {
            id = intent.getStringExtra("classid");
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.rl_bt_back:
                finish();
                break;
            case R.id.button10:
                if(orderInfo!=null &&order!=null){
                    classId = orderInfo.getClassid();
                    if(order.getYytype_code()!=null&&order.getYytype_code().equals("12")){
                        progress.setVisibility(View.VISIBLE);
                                getorder();
                    }else if(("1").equals(order.getYytype_code())){
                        progress.setVisibility(View.VISIBLE);
                                getorder();
                    }else {
                        Toast.makeText(QdddActivity.this,"请选择卡或优惠券",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }


    private void popupWindow() {
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.listview, null);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;

        popupWindow = new PopupWindow(contentView,
               screenWidth/3*2, LinearLayout.LayoutParams.WRAP_CONTENT, true);
      //  popupWindow.setBackgroundDrawable(getResources().getDrawable(R.mipmap.icon));
        popupWindow.setFocusable(true);
        popupWindow.update();

        popupWindow.setContentView(contentView);
        // 一个自定义的布局，作为显示的内容
        ListView yhj_lv = (ListView) contentView.findViewById(R.id.yhj_list);
        List<YhjPro> yhjPros = order.getYhj();
        YhjItemAdapter yhjAdapter = new YhjItemAdapter(this, yhjPros);
        yhj_lv.setAdapter(yhjAdapter);
        yhj_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cardId = order.getYhj().get(position).getYhj_id();
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
    }

    private void initListener() {

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.nianhui:
                        yyType = "卡";

                        initType(yyType);

                        break;

                    case R.id.youhuijuan:
                        yyType = "券";

                        initType(yyType);
                        break;
                }
            }
        });

        UrlPath urlPath1 = UrlPath.getUrlPath();
        String urlPath =urlPath1.getUrl()+"OrderInfoServlet?";
        String url = urlPath+"classid="+id+"&reqid="+reqid;
        new ThreadUtils_no(QdddActivity.this,url,new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String json=msg.obj.toString();
                if (json!=null){
                    Gson gson = new Gson();
                    Order order = new Order();
                    order = gson.fromJson(json, Order.class);
                    if (order != null && order.getOrderInfo() != null) {
                        orderInfo = order.getOrderInfo();
                        textView35.setText(orderInfo.getClassname());
                        textView36.setText(orderInfo.getTime());
                        String s= StringEscapeUtils.unescapeJava(orderInfo.getTrainername());
                        textView37.setText(s);
                        textView39.setText(orderInfo.getStadiumname());
                        textView40.setText(orderInfo.getOrderid());
                    }
                }
            }
        }).start();
    }


    private void initType(String yyType) {
        String urlPath = urlPath1.getUrl()+"YyTypeServlet?";

        String staid = orderInfo.getStadiumid();
        String ct_id=orderInfo.getClassid();
        String url=urlPath+"yytype="+yyType+"&reqid="+reqid+"&stadiumid="+staid+"&classid="+ct_id;
        new ThreadUtils_no(QdddActivity.this,url,new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String json=msg.obj.toString();
                if (json!=null){
                    Gson gson = new Gson();
                    order = new YhjS();
                    order = gson.fromJson(json, YhjS.class);

                    if (order.getYhj() != null && order.getYhj().size() > 0&&("12").equals(order.getYytype_code())) {
                        popupWindow();
                        radioGroup.check(youhuijuan.getId());
                    } else if (("1").equals(order.getYytype_code())){
                        radioGroup.check(card.getId());
                    } else  if(!("1").equals(order.getYytype_code())||!("12").equals(order.getYytype_code())){
                        radioGroup.clearCheck();
                        Toast.makeText(QdddActivity.this, order.getYytype_msg(),Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }).start();

    }

    private void getorder() {//预约支付
        String urlPath = urlPath1.getUrl()+ urlPath1.YY_DDXQ;
        SharedPreferences sp = getSharedPreferences("Yuga", MODE_PRIVATE);
        reqid = sp.getString("reqid", "");
        if(yyType.equals("券")||yyType.equals("卡")) {
            if (!("").equals(cardId) || ("卡").equals(yyType)) {
                String url = urlPath.replace("classid=x", "classid=" + classId).replace("reqid=x", "reqid=" + reqid).replace("orderid=x", "orderid=" + orderInfo.getOrderid()).replace("yytype=x", "yytype=" + yyType).replace("yhj_id=x", "yhj_id=" + cardId)+"&app_type=android";
                Log.i("-----aaaaurl------",url);
                new ThreadUtils_no(QdddActivity.this,url,handler).start();
            }
        }
        }


}
