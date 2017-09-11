package com.chaotong.yujia.main.menu.benggong.wo_de_yu_yue;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MainActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;

import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.adapter.YuYueAdapter;

import com.chaotong.yujia.main.entity.HuiYuanBean.Wdyy;
import com.chaotong.yujia.main.entity.HuiYuanBean.YuYueBean;

import com.chaotong.yujia.main.menu.benggong.Zxing.an.CaptureActivity;
import com.chaotong.yujia.main.menu.benggong.Zxing.an.DecodeDetail;
import com.chaotong.yujia.main.menu.login.loginActivity;
import com.chaotong.yujia.main.thread.ThreadUtils;
import com.chaotong.yujia.main.thread.ThreadUtils_no;

import com.chaotong.yujia.main.utils.PullToFlash.PullToRefreshLayout;
import com.chaotong.yujia.main.utils.PullToFlash.PullableListView;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的预约
 */

public class WdyyActivity extends BaseActivity implements AdapterView.OnItemClickListener,YuYueAdapter.callback {

    @Bind(R.id.record_listView)
    PullableListView listView;
    private String reqid = "";
    String content = "";
    UrlPath urlPath = UrlPath.getUrlPath();
    private Wdyy wdyy;
    private List<YuYueBean> mlist;
    String url = urlPath.getUrl() + "WdkcServlet";
    private YuYueAdapter adapter;

    @Bind(R.id.rl_yuyue_back)
    RelativeLayout rl_yuyue_back;
    @Bind(R.id.yuyue_back)
    ImageView yuyue_back;
    View loadmore;

    @Bind(R.id.probar)
    ProgressBar progress;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            if(!json.equals("")&&json!=null) {
                Log.i("yuyue-------", json);
                JSONObject object;
                Gson gson = new Gson();
                wdyy = gson.fromJson(json, Wdyy.class);
                String code = wdyy.getCode();
                String message = wdyy.getMsg();
                if (code != null && message != null) {
                    if (code.equals("1")) {
                        init(1);
                    } else if (("0").equals(code)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(WdyyActivity.this);
                        builder.setCancelable(false);
                        builder.setTitle(getResources().getString(R.string.app_name) + "")
                                .setMessage(getResources().getString(R.string.message) + "")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        SharedPreferences sp = getSharedPreferences(MyApplication.SpName, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putBoolean("isLogin", false);
                                        editor.putString("reqid", "");
                                        editor.putString("type", "");
                                        editor.commit();
                                        Intent intent = new Intent(WdyyActivity.this, loginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                }).show();
                    } else {
                        Toast.makeText(WdyyActivity.this, wdyy.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(WdyyActivity.this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }
            super.handleMessage(msg);
        }
    };

    private Handler xhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            JSONObject object;
            Gson gson = new Gson();
            wdyy = gson.fromJson(json, Wdyy.class);
            String code = wdyy.getCode();
            if (code.equals("1")||code.equals("0")) {
                if (wdyy.getLwdkc() != null) {
                    for (int i = 0; i < wdyy.getLwdkc().size(); i++) {
                        YuYueBean yuyue = wdyy.getLwdkc().get(i);
                        mlist.add(yuyue);
                    }
                    adapter.notifyDataSetChanged();
                    loadmore.setVisibility(View.VISIBLE);
                }
            } else {
                Toast.makeText(WdyyActivity.this, wdyy.getMsg(), Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);
        }
    };


    private void init(int n) {
        if (mlist!=null&&mlist.size()>0&&adapter!=null){
            mlist.clear();
            adapter.notifyDataSetChanged();
        }
        mlist=wdyy.getLwdkc();
        if (mlist!=null&&mlist.size()>0){
            loadmore.setVisibility(View.VISIBLE);
            adapter = new YuYueAdapter(WdyyActivity.this,mlist,reqid,this);
            listView.setAdapter(adapter);
        }else {
            loadmore.setVisibility(View.INVISIBLE);
        }



    }

    private PullToRefreshLayout ptrl;
    private boolean isFirstIn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wdyy);
        ButterKnife.bind(this);
        initView();
    }

    boolean reflash=false;

    @Override
    protected void onResume() {
        super.onResume();
        if (reflash){
            reflash=false;
            initListener();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        reflash=true;
    }

    private void initView() {
        mlist = new ArrayList<YuYueBean>();
        reqid = getIntent().getStringExtra("reqid");

        initListener();

        listView.setOnItemClickListener(this);

        rl_yuyue_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ptrl = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
        ptrl.setOnRefreshListener(new MyListener());
        loadmore=ptrl.getChildAt(2);
        if (loadmore!=null){
            loadmore.setVisibility(View.INVISIBLE);
        }
    }
    private void initListener() {

        JSONObject object;
        if (!("").equals(reqid)) {
            try {
                object = new JSONObject();
                object.put("reqid", reqid);
                object.put("more", 0);
                content = String.valueOf(object);
            } catch (JSONException e) {
                //e.printStackTrace();
            }
            new ThreadUtils(this, url, content, handler).start();
        } else {
            Toast.makeText(this, "请重新登录", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(WdyyActivity.this, YuYueActivity.class);
        intent.putExtra("reqid", reqid);
        intent.putExtra("classid", mlist.get(i).getClassid());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void clickOk(final View v) {


            if (v.getTag(R.id.hyqd)!=null) {
                final int position = (int) v.getTag(R.id.hyqd);
                if (!("").equals(reqid)) {
                    /*String classid = mlist.get(position).getClassid();
                    String kc_code = mlist.get(position).getKc_code();*/
                    Intent intent = new Intent(WdyyActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_SCAN);
                } else {
                    Toast.makeText(WdyyActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        Log.i("quxiao--------",v.getTag(R.id.qxyy)+"");
        if (v.getTag(R.id.qxyy)!=null&&(int)v.getTag(R.id.qxyy)!=-1){
                    final int pos= (int) v.getTag(R.id.qxyy);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(WdyyActivity.this);
                    builder.setTitle(getResources().getString(R.string.app_name) + "")
                            .setMessage(getResources().getString(R.string.qxyy)+"")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    String url01 = urlPath.getUrl()+"QxYyServlet?reqid="
                                            + reqid  + "&classid="
                                            + mlist.get(pos).getClassid();
                                    Log.i("ur101-------------",url01);
                                    progress.setVisibility(View.VISIBLE);
                                    v.setEnabled(false);
                                    v.setBackgroundResource(R.drawable.text_bg4);

                                    new ThreadUtils_no(WdyyActivity.this, url01, new Handler() {
                                        @Override
                                        public void handleMessage(Message msg) {
                                            super.handleMessage(msg);
                                            String json = msg.obj.toString();
                                            Log.i("urjson----------",json);
                                            if (!json.equals("")&&json!=null){
                                                progress.setVisibility(View.INVISIBLE);
                                                v.setEnabled(true);
                                                v.setBackgroundResource(R.drawable.text_bg);

                                                try {
                                                    JSONObject object = new JSONObject(json);
                                                    String co = object.getString("code");
                                                    String message = object.getString("msg");
                                                    String qxyy_code = object.getString("qxyy_code");
                                                    String qxyy_msg = object.getString("qxyy_msg");
                                                    if (co.equals("1")) {
                                                        Toast.makeText(WdyyActivity.this, qxyy_msg, Toast.LENGTH_SHORT).show();
                                                    initListener();
                                                    } else {
                                                        Toast.makeText(WdyyActivity.this, message, Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    //e.printStackTrace();
                                                }
                                            }
                                        }
                                    }).start();

                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    builder.show();
                }

    }
    private static final int REQUEST_CODE_SCAN = 0x0000;

    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_CODE_SCAN&&resultCode==RESULT_OK){
            if (data != null) {
                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);
              /*  result.setText("解码结果： \n" + content);*/
                //   qrCodeImage.setImageBitmap(bitmap);
                Intent intent12=new Intent(WdyyActivity.this, DecodeDetail.class);
                intent12.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent12.putExtra("classid",content);
                intent12.putExtra("reqid",reqid);
                startActivity(intent12);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            JSONObject object;
            if (!("").equals(reqid)) {
                try {
                    object = new JSONObject();
                    object.put("reqid", reqid);
                    object.put("more", 0);
                    content = String.valueOf(object);
                } catch (JSONException e) {
                    //e.printStackTrace();
                }
                new ThreadUtils(WdyyActivity.this, url, content, handler).start();
            } else {
                Toast.makeText(WdyyActivity.this, "请重新登录", Toast.LENGTH_SHORT).show();
            }
            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 千万别忘了告诉控件刷新完毕了哦！
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 2000);

        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {

            // 加载操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    JSONObject object;
                    int more = 0;
                    if (!("").equals(reqid)) {
                        more++;
                        try {
                            object = new JSONObject();
                            object.put("reqid", reqid);
                            object.put("more", more);
                            content = String.valueOf(object);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        new ThreadUtils(WdyyActivity.this, url, content, xhandler).start();
                    } else {
                        Toast.makeText(WdyyActivity.this, "请重新登录", Toast.LENGTH_SHORT).show();
                    }
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 3000);
        }
    }
}
