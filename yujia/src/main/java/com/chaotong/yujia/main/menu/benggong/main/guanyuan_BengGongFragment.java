package com.chaotong.yujia.main.menu.benggong.main;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.menu.benggong.dai_ping_jia.ReceiverPingJia;
import com.chaotong.yujia.main.menu.benggong.gy_gz_kc.GyGzKcActivity;
import com.chaotong.yujia.main.menu.benggong.she_zi.Edit_info;
import com.chaotong.yujia.main.menu.benggong.she_zi.SzActivity;
import com.chaotong.yujia.main.menu.benggong.wan_cheng_ke_shi.WcksActivity;
import com.chaotong.yujia.main.menu.benggong.wo_de_yu_yue.GuanzhuYyActivity;
import com.chaotong.yujia.main.menu.benggong.yi_jian_fan_kui.fankuiActivity;
import com.chaotong.yujia.main.menu.login.loginActivity;
import com.chaotong.yujia.main.menu.xiaoxi.XiaoXiActivity;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.thread.ThreadUtils;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.facebook.drawee.view.SimpleDraweeView;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class guanyuan_BengGongFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.guanyuan_name)
    TextView guanyuan_name;
    @Bind(R.id.guanyuan_type)
    TextView guanyuan_type;

    @Bind(R.id.guanyuan_pic)
    SimpleDraweeView guanyuan_pic;

    UrlPath urlPath = UrlPath.getUrlPath();
    @Bind(R.id.layout_yuyue)
    RelativeLayout layout_yuyue;
    @Bind(R.id.layout_kecheng)
    RelativeLayout layout_kecheng;
    @Bind(R.id.layout_setting)
    RelativeLayout layout_setting;
    @Bind(R.id.layout_fankui)
    RelativeLayout layout_fankui;

    @Bind(R.id.layout_keshi)
    LinearLayout layout_keshi;
    @Bind(R.id.keshi_num)
    TextView ksnum;
    @Bind(R.id.jifen_num)
    TextView jfnum;

    @Bind(R.id.gybg_srfl)
    SwipeRefreshLayout gybg_srfl;

    @Bind(R.id.bgbar)
    RelativeLayout gy_bg;

    @Bind(R.id.edit_info)
    LinearLayout edit_info;

    @Bind(R.id.progress)
    ProgressBar progress;

    String reqid = "";
    String url = urlPath.getUrl() + "SelfInfoServlet";
    String ncname;
    String sex;
    String age;
    String tel;
    String pic;
    String type;
    String wcks;

    SharedPreferences sp;
    Context context;

    @Bind(R.id.hy_xx)
    ImageView hy_xx;

    @Bind(R.id.zxing_load)
    SimpleDraweeView mLoadZxing;

    @Bind(R.id.cancel_app)
    Button mAppCancel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            try {
                JSONObject object = new JSONObject(json);
                String code = object.getString("code");
                Log.i("code", code);
                String message = object.getString("msg");
                if (code.equals("1")) {
                     progress.setVisibility(View.INVISIBLE);
                    gybg_srfl.setRefreshing(false);
                    JSONObject object1 = object.getJSONObject("info");
                    ncname = object1.getString("ncname");
                    sex = object1.getString("sex");
                    age = object1.getString("age");
                    tel = object1.getString("tel");
                    pic = object1.getString("pic");
                    type = object1.getString("type");
                    wcks = object1.getString("wcks");

                    ksnum.setText(wcks);
                    jfnum.setText(object1.getString("jf"));
                    String s = StringEscapeUtils.unescapeJava(ncname);
                    guanyuan_name.setText(s);
                    guanyuan_type.setText(type);

                    if (pic != null && !("").equals(pic)) {
                        guanyuan_pic.setImageURI(Uri.parse(pic));
                    }
                } else if (("0").equals(code)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(false);
                    builder.setTitle(getResources().getString(R.string.app_name) + "")
                            .setMessage(getResources().getString(R.string.message) + "")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SharedPreferences sp = context.getSharedPreferences(MyApplication.SpName, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putBoolean("isLogin", false);
                                    editor.putString("reqid", "");
                                    editor.putString("type", "");
                                    editor.commit();
                                    Intent intent = new Intent(context, loginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            })
                            .show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.handleMessage(msg);
        }
    };

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.gy_bg, null, false);
        ButterKnife.bind(this, view);
        initView();
        initClick();
        return view;
    }

    String cancelUrl=urlPath.getUrl()+"DeleteRegistrationIDServlet?";
    private void initClick() {
        layout_yuyue.setOnClickListener(this);
        layout_kecheng.setOnClickListener(this);
      /*  layout_pingjia.setOnClickListener(this);*/
        layout_setting.setOnClickListener(this);
        layout_fankui.setOnClickListener(this);
        edit_info.setOnClickListener(this);
        hy_xx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent9 = new Intent(context, XiaoXiActivity.class);
                intent9.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent9);
            }
        });

        layout_keshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context, WcksActivity.class);
                in.putExtra("reqid", reqid);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in);
            }
        });

        mAppCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reqid!=null&&!reqid.equals("")){
                    String url=cancelUrl+"reqid="+reqid;
                    new ThreadUtils_no(context,url,new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            String json=msg.obj.toString();
                            if (json!=null){
                                try {
                                    JSONObject object=new JSONObject(json);
                                    String code=object.getString("code");
                                    String message=object.getString("msg");
                                    String  unbundling_code=object.getString("unbundling_code");
                                    String unbundling_msg=object.getString("unbundling_msg");

                                    if (code!=null&&code.equals("1")){
                                        if (unbundling_code!=null&&unbundling_code.equals("1")){
                                            SharedPreferences.Editor editor=sp.edit();
                                            editor.putBoolean("isLogin",false);
                                            editor.putString("reqid","");
                                            editor.putString("type","");
                                            editor.putString("yh_type","0");
                                            editor.commit();
                                            // Toast.makeText(SzActivity.this,"请重新登录",Toast.LENGTH_LONG).show();
                                            Intent intent1=new Intent(context,loginActivity.class);
                                            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent1);
                                            Activity activity= (Activity) context;
                                            activity.finish();
                                            activity.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
                                        }else {
                                            ToastUtils.showToast(context,unbundling_msg);
                                        }

                                    }else {
                                        ToastUtils.showToast(context,message);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                ToastUtils.showToast(context,getResources().getString(R.string.error));
                            }
                        }
                    }).start();
                }
            }
        });

    }

    private void initView() {


        sp = context.getSharedPreferences(MyApplication.SpName, Context.MODE_PRIVATE);
        reqid = sp.getString("reqid", "");

        initListener();

        gybg_srfl.setColorSchemeResources(R.color.blue, R.color.yellow,
                R.color.white, R.color.aquamarine);
        gybg_srfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initListener();
                    }
                }, 2000);
            }
        });

        gy_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initListener() {
        progress.setVisibility(View.VISIBLE);
        Log.i("info", reqid);
        if (!("").equals(reqid)) {
            JSONObject object;
            String content = "";
            try {
                object = new JSONObject();
                object.put("reqid", reqid);
                content = String.valueOf(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new ThreadUtils(getActivity(), url, content, handler).start();
        }
    }

    boolean reflash = false;

    @Override
    public void onResume() {
        super.onResume();
        if (reflash) {
            initListener();
            reflash = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        reflash = true;
    }

    private int YY_GUANYUAN = 01;
    private int JUMP_KC = 02;

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_yuyue:
                Intent in01 = new Intent(getActivity(), GuanzhuYyActivity.class);
                in01.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                in01.putExtra("reqid", reqid);
                startActivity(in01);
                break;
            case R.id.layout_kecheng:
                Intent intent = new Intent(getActivity(), GyGzKcActivity.class);
                intent.putExtra("reqid", reqid);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
                break;
           /* case R.id.layout_pingjia:
                Intent in08 = new Intent(getActivity(), ReceiverPingJia.class);
                in08.putExtra("reqid", reqid);
                in08.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                in08.putExtra("type","gy");
                startActivity(in08);
                break;*/
            case R.id.layout_setting:
                Intent in04 = new Intent(getActivity(), SzActivity.class);
                in04.putExtra("reqid", reqid);
                in04.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in04);
                break;
            case R.id.layout_fankui:
                Intent in03 = new Intent(getActivity(), fankuiActivity.class);
                in03.putExtra("reqid", reqid);
                in03.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in03);
                break;
            case R.id.edit_info:
                Intent intent13 = new Intent(getActivity(), Edit_info.class);
                intent13.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent13);
                break;

        }
    }
}