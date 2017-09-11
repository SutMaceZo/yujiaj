package com.chaotong.yujia.main;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;

import android.util.Log;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.chaotong.yujia.base.BaseActivity;

import com.chaotong.yujia.main.entity.Verson;
import com.chaotong.yujia.main.menu.xiulian.main.XiuLianFragment;
import com.chaotong.yujia.main.menu.benggong.main.guanyuan_BengGongFragment;
import com.chaotong.yujia.main.menu.benggong.main.guanzhu_BengGongFragment;
import com.chaotong.yujia.main.menu.benggong.main.huiyuan_BengGongFragment;
import com.chaotong.yujia.main.menu.benggong.main.jiaolian_BengGongFragment;
import com.chaotong.yujia.main.menu.login.loginActivity;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.menu.yujiajia.main.YuJiaJiaFragment;
import com.chaotong.yujia.main.menu.yujiaolian.main.YueJiaoLianFragment;
import com.chaotong.yujia.main.thread.ThreadUtils;
import com.chaotong.yujia.main.utils.AppManager;
import com.chaotong.yujia.main.utils.VersonUtils;
import com.chaotong.yujia.main.utils.testUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Coder-pig on 2015/8/28 0028.
 */


public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.radio)
    RadioGroup radioGroup;
    @Bind(R.id.rb_yjj)
    RadioButton rb_yjj;
    @Bind(R.id.rb_xl)
    RadioButton rb_xl;
    @Bind(R.id.rb_yjl)
    RadioButton rb_yjl;

    @Bind(R.id.rb_bg)
    RadioButton rb_bg;


    SharedPreferences sp;
    boolean isLogin;
    String reqid = "";
    String kind = "";
    String yh_type = "";


    public static final String fragment1Tag = "fragment1";
    public static final String fragment2Tag = "fragment2";
    public static final String fragment3Tag = "fragment3";
    public static final String fragment4Tag = "fragment4";//hy
    public static final String fragment5Tag = "fragment5";//jl
    public static final String fragment6Tag = "fragment6";//gy
    public static final String fragment7Tag = "fragment7";//gz

    Fragment fragment1, fragment2, fragment3, fragment4, fragment5, fragment6, fragment7;
    FragmentManager fm;
    FragmentTransaction ft;

    String[] item = {"会员", "教练", "馆员", "馆主"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // String s=getSharedPreferences("Yuga", Context.MODE_PRIVATE).getString("yh_type","");
       // Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
        ButterKnife.bind(this);
        initView(savedInstanceState);
    }


    private void initView(Bundle savedInstanceState) {
        sp = getSharedPreferences("Yuga", MODE_PRIVATE);
        isLogin = sp.getBoolean("isLogin", true);
        reqid = sp.getString("reqid", "");
        //根据kind来判断是哪一种类型
        kind = sp.getString("type", "");
        yh_type = sp.getString("yh_type","");
        urlPath.getData(yh_type);
        Log.i("aaaaaa---------------",urlPath.getData(yh_type));
        fm = getSupportFragmentManager();
        fragment1 = fm.findFragmentByTag(fragment1Tag);
        fragment2 = fm.findFragmentByTag(fragment2Tag);
        fragment3 = fm.findFragmentByTag(fragment3Tag);
        fragment4 = fm.findFragmentByTag(fragment4Tag);
        fragment5 = fm.findFragmentByTag(fragment5Tag);
        fragment6 = fm.findFragmentByTag(fragment6Tag);
        fragment7 = fm.findFragmentByTag(fragment7Tag);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = new YuJiaJiaFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.main_frame, fragment, fragment1Tag).commit();
            radioGroup.check(rb_yjj.getId());

        }
        radioGroup.setOnCheckedChangeListener(this);

        initIntent();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initVerson();
        if (!testUtils.isConn(getApplicationContext())) {
            setNetworkMethod();
        }
    }

    UrlPath urlPath = UrlPath.getUrlPath();
    String verson_code = "";
    String type = "android";
    Verson verson;
    String verUrl = urlPath.getUrl() + "UpdateApp";

    private void initVerson() {
        verson_code = VersonUtils.getVersonCode(MainActivity.this);
        String content = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", type);
            jsonObject.put("version_code", verson_code);
        } catch (JSONException e) {
            //e.printStackTrace();
        }
        content = String.valueOf(jsonObject);
        Log.i("---------verson",content);
        new ThreadUtils(this, verUrl, content, vhandler).start();
    }

    Handler vhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = msg.obj.toString();
            Log.i("verson-------",json);
            if (!json.equals("")&&json!=null) {
                Gson gson = new Gson();
                verson = gson.fromJson(json, Verson.class);
                if (verson != null) {
                    String code = verson.getCode();
                    String message = verson.getMsg();
                    if (code != null && ("1").equals(code)) {
                        initApp(verson);
                    } else {
                        ToastUtils.showToast(MainActivity.this, message);
                    }
                } else {
                    ToastUtils.showToast(MainActivity.this, "请检查网络连接");
                }
            }
        }
    };

    private void initApp(Verson verson) {
        String qzgx = verson.getQzgx();
        if (qzgx != null) {
            if (("1").equals(qzgx)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(false);
                builder.setTitle(getResources().getString(R.string.app_name) + "")
                        .setMessage(getResources().getString(R.string.message) + "")
                        .setPositiveButton("立即安装", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Uri uri = Uri.parse(MyApplication.APP_NAME);
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .show();

            } else if (("2").equals(qzgx)) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(false);
                builder.setTitle(getResources().getString(R.string.app_name) + "")
                        .setMessage(getResources().getString(R.string.message) + "")
                        .setPositiveButton("立即安装", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Uri uri = Uri.parse(MyApplication.APP_NAME);
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).show();
            } else {
            }
        }

    }


    public void setNetworkMethod() {
        //提示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("网络设置提示").setMessage("网络连接不可用,是否进行设置?").setPositiveButton("设置", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Intent intent = null;
                //判断手机系统的版本  即API大于10 就是3.0或以上版本
                if (android.os.Build.VERSION.SDK_INT > 10) {
                    intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                } else {
                    intent = new Intent();
                    ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
                startActivity(intent);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        }).show();
    }

    private void initIntent() {

        int x = getIntent().getIntExtra("x", 0);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (x == 3) {
            kind = sp.getString("type", "");
            if (kind != null && !kind.equals("")) {
                if (kind.equals(item[0])) {
                    if (fragment4 == null) {
                        fragment4 = new huiyuan_BengGongFragment();
                        ft.add(R.id.main_frame, fragment4, fragment4Tag);
                    } else {
                        ft.show(fragment4);
                    }

                } else if (kind.equals(item[1])) {

                    if (fragment5 == null) {
                        fragment5 = new jiaolian_BengGongFragment();
                        ft.add(R.id.main_frame, fragment5, fragment5Tag);
                    } else {
                        ft.show(fragment5);
                    }


                } else if (kind.equals(item[2])) {

                    if (fragment6 == null) {
                        fragment6 = new guanyuan_BengGongFragment();
                        ft.add(R.id.main_frame, fragment6, fragment6Tag);
                    } else {
                        ft.show(fragment6);
                    }

                } else if (kind.equals(item[3])) {

                    if (fragment7 == null) {
                        fragment7 = new guanzhu_BengGongFragment();
                        ft.add(R.id.main_frame, fragment7, fragment7Tag);
                    } else {
                        ft.show(fragment7);
                    }

                }
            }
        } else if (x == 1) {
            if (fragment1 == null) {
                fragment1 = new YuJiaJiaFragment();
                ft.add(R.id.main_frame, fragment1, fragment1Tag);
            } else {
                ft.show(fragment1);
            }
        }

        ft.commit();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivity.this);
            builder.setMessage("你确定退出吗?")
                    .setCancelable(false)
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    //android.os.Process.killProcess(android.os.Process.myPid());
                                    MyApplication.IsFirst=true;
                                    MainActivity.this.finish();

                                    System.exit(0);
                                }
                            })
                    .setNegativeButton("返回",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        ft = fm.beginTransaction();

        if (fragment1 != null) {
            ft.hide(fragment1);
        }
        if (fragment2 != null) {
            ft.hide(fragment2);
        }
        if (fragment3 != null) {
            ft.hide(fragment3);
        }
        if (fragment4 != null) {
            ft.hide(fragment4);
        }
        if (fragment5 != null) {
            ft.hide(fragment5);
        }
        if (fragment6 != null) {
            ft.hide(fragment6);
        }
        if (fragment7 != null) {
            ft.hide(fragment7);
        }
        switch (i) {
            case R.id.rb_yjj:
                if (fragment1 == null) {
                    fragment1 = new YuJiaJiaFragment();
                    ft.add(R.id.main_frame, fragment1, fragment1Tag);
                } else {
                    ft.show(fragment1);
                }
                break;
            case R.id.rb_xl:
                if (fragment2 == null) {
                    fragment2 = new XiuLianFragment();
                    ft.add(R.id.main_frame, fragment2, fragment2Tag);
                } else {
                    ft.show(fragment2);
                }
                break;
            case R.id.rb_yjl:
                if (fragment3 == null) {
                    fragment3 = new YueJiaoLianFragment();
                    ft.add(R.id.main_frame, fragment3, fragment3Tag);
                } else {
                    ft.show(fragment3);
                }
                break;

            case R.id.rb_bg:
                if (("").equals(reqid)) {
                    //未登录,跳转到登录界面
                    Intent intent = new Intent(MainActivity.this, loginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    //登录了,根据kind类型来区分登录类型
                    if (kind != null) {
                        if (kind.equals(item[0])) {
                            if (fragment4 == null) {
                                fragment4 = new huiyuan_BengGongFragment();
                                ft.add(R.id.main_frame, fragment4, fragment4Tag);
                            } else {
                                ft.show(fragment4);
                            }

                        } else if (kind.equals(item[1])) {

                            if (fragment5 == null) {
                                fragment5 = new jiaolian_BengGongFragment();
                                ft.add(R.id.main_frame, fragment5, fragment5Tag);
                            } else {
                                ft.show(fragment5);
                            }


                        } else if (kind.equals(item[2])) {

                            if (fragment6 == null) {
                                fragment6 = new guanyuan_BengGongFragment();
                                ft.add(R.id.main_frame, fragment6, fragment6Tag);
                            } else {
                                ft.show(fragment6);
                            }

                        } else if (kind.equals(item[3])) {

                            if (fragment7 == null) {
                                fragment7 = new guanzhu_BengGongFragment();
                                ft.add(R.id.main_frame, fragment7, fragment7Tag);
                            } else {
                                ft.show(fragment7);
                            }

                        }
                    }
                }
                break;
        }
        ft.commit();
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton mTab = (RadioButton) radioGroup.getChildAt(i);
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentByTag((String) mTab.getTag());
            FragmentTransaction ft = fm.beginTransaction();
            if (fragment != null) {
                if (!mTab.isChecked()) {
                    ft.hide(fragment);
                }
            }
            ft.commit();
        }
    }


}
