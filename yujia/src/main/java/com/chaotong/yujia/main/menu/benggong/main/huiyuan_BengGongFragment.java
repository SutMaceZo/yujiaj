package com.chaotong.yujia.main.menu.benggong.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.chaotong.yujia.main.Constants;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.entity.HuiYuanBean.HuiYuan_Info;
import com.chaotong.yujia.main.entity.HuiYuanBean.huiyuanBean;
import com.chaotong.yujia.main.menu.benggong.Zxing.an.CaptureActivity;
import com.chaotong.yujia.main.menu.benggong.Zxing.an.DecodeDetail;

import com.chaotong.yujia.main.menu.benggong.huan_gou_ding_dan.HuanGouAactivity;
import com.chaotong.yujia.main.menu.benggong.she_zi.Edit_info;
import com.chaotong.yujia.main.menu.benggong.suo_shu_chang_guan.SscgActivity;
import com.chaotong.yujia.main.menu.benggong.dai_ping_jia.DpjActivity;

import com.chaotong.yujia.main.menu.benggong.she_zi.SzActivity;
import com.chaotong.yujia.main.menu.benggong.wan_cheng_ke_shi.HyWcksActivity;
import com.chaotong.yujia.main.menu.benggong.wan_cheng_ke_shi.WcksActivity;
import com.chaotong.yujia.main.menu.benggong.wo_de_shou_chang.WdscActivity;
import com.chaotong.yujia.main.menu.benggong.wo_de_you_hui_juan.WdyhjActivity;
import com.chaotong.yujia.main.menu.benggong.wo_de_yu_yue.WdyyActivity;
import com.chaotong.yujia.main.menu.benggong.yi_jian_fan_kui.fankuiActivity;

import com.chaotong.yujia.main.menu.login.loginActivity;
import com.chaotong.yujia.main.menu.xiaoxi.XiaoXiActivity;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.menu.yujiajia.points.MyPointsActivity;
import com.chaotong.yujia.main.thread.ThreadUtils;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;


import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class huiyuan_BengGongFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.huiyuan_pic)
    SimpleDraweeView huiyuan_image;
    @Bind(R.id.huiyuan_name)
    TextView huiyuan_name;
    @Bind(R.id.layout_daipingjia)
    LinearLayout layout_daipingjia;
    @Bind(R.id.huiyuan_type)
    TextView huiyuan_type;

    @Bind(R.id.edit_info)
    LinearLayout _info;

    @Bind(R.id.layout_yuyue)
    LinearLayout layout_yuyue;
    @Bind(R.id.yuyue_num)
    TextView yuyue_num;

    @Bind(R.id.layout_keshi)
    LinearLayout layout_keshi;
    @Bind(R.id.keshi_num)
    TextView keshi_num;

    //扫一扫签到
    @Bind(R.id.sign)
    ImageView sign;

    @Bind(R.id.daipingjia_num)
    TextView daipingjia_num;

    @Bind(R.id.layout_jifen)
    LinearLayout layout_jifen;
    @Bind(R.id.jifen_num)
    TextView jifen_num;

    @Bind(R.id.layout_youhuijuan)
    LinearLayout layout_youhuijuan;
    @Bind(R.id.youhuijuan_num)
    TextView youhuijuan_num;

    @Bind(R.id.layout_dingdan)
    LinearLayout layout_dingdan;
    @Bind(R.id.dingdan_num)
    TextView dingdan_num;

    /* @Bind(R.id.layout_xiulian)
      LinearLayout layout_xiulian;*/
    @Bind(R.id.layout_collet)
    RelativeLayout layout_collect;
    @Bind(R.id.layout_setting)
    RelativeLayout layout_setting;
    @Bind(R.id.layout_fankui)
    RelativeLayout layout_fankui;

    @Bind(R.id.layout_cg)
    RelativeLayout layout_cg;

    @Bind(R.id.hybg_srfl)
    SwipeRefreshLayout hybg_srfl;
    @Bind(R.id.progress)
    ProgressBar progress;
    @Bind(R.id.hy_xx)
    ImageView rpj;

    @Bind(R.id.zxing_load)
    SimpleDraweeView mLoadZxing;

    @Bind(R.id.cancel_app)
    Button mAppCancel;

    String picUrl = "";
    SharedPreferences sp;
    String reqid = "";
    huiyuanBean huiyuan;

    @Bind(R.id.hy_bg)
    RelativeLayout hy_bg;

    Context mContext;
    UrlPath urlPath = UrlPath.getUrlPath();
    String url = urlPath.getUrl() + "SelfInfoServlet";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            try {
                if (json != null) {
                    progress.setVisibility(View.INVISIBLE);
                    hybg_srfl.setRefreshing(false);
                    JSONObject object = new JSONObject(json);
                    String code = object.getString("code");
                    String message = object.getString("msg");
                    if (("1").equals(code)) {
                        Gson gson = new Gson();
                        huiyuan = gson.fromJson(json, huiyuanBean.class);

                        String face = StringEscapeUtils.unescapeJava(huiyuan.getInfo().getNcname());
                        huiyuan_name.setText(face);
                        huiyuan_type.setText(huiyuan.getInfo().getType());
                        picUrl = huiyuan.getInfo().getPic();
                        yuyue_num.setText(huiyuan.getInfo().getWdyy());
                        keshi_num.setText(huiyuan.getInfo().getWcks());
                        daipingjia_num.setText(huiyuan.getInfo().getDpj());
                        jifen_num.setText(huiyuan.getInfo().getJf());
                        youhuijuan_num.setText(huiyuan.getInfo().getYhj());
                        dingdan_num.setText(huiyuan.getInfo().getHgdd());

                        if (picUrl != null && !picUrl.equals("")) {
                            //   huiyuan_image.setImageURI(Uri.parse(picUrl));

                            GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(getResources())
                                    .setRoundingParams(RoundingParams.asCircle())
                                    .build();
                            huiyuan_image.setHierarchy(hierarchy);
                            DraweeController controller = Fresco.newDraweeControllerBuilder()
                                    .setUri(picUrl)
                                    .build();
                            huiyuan_image.setController(controller);

                        }

                    } else if (("0").equals(code)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setCancelable(false);
                        builder.setTitle(getResources().getString(R.string.app_name) + "")
                                .setMessage(getResources().getString(R.string.message) + "")
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        SharedPreferences sp = mContext.getSharedPreferences(MyApplication.SpName, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putBoolean("isLogin", false);
                                        editor.putString("reqid", "");
                                        editor.putString("type", "");
                                        editor.commit();
                                        Intent intent = new Intent(mContext, loginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    } else {
                        Toast.makeText(mContext.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
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
        view = inflater.inflate(R.layout.hy_bg, null, false);
        ButterKnife.bind(this, view);
        initView();
        initClick();
        return view;
    }

    ThreadUtils thread = null;

    public void initView() {
        huiyuan = new huiyuanBean();
        sign.setOnClickListener(this);
        hy_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        sp = mContext.getSharedPreferences(MyApplication.SpName, Context.MODE_PRIVATE);
        reqid = sp.getString("reqid", "");
        initListener();
        hybg_srfl.setColorSchemeResources(R.color.blue, R.color.yellow,
                R.color.white, R.color.aquamarine);
        hybg_srfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
    }

    public void initListener() {
        if (reqid != null && !("").equals(reqid)) {
            progress.setVisibility(View.VISIBLE);
            JSONObject object;
            String content = "";
            try {
                object = new JSONObject();
                object.put("reqid", reqid);
                content = String.valueOf(object);
            } catch (JSONException e) {
                //e.printStackTrace();
            }
            thread = new ThreadUtils(mContext, url, content, handler);
            thread.start();
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

    String cancelUrl = urlPath.getUrl() + "DeleteRegistrationIDServlet?";

    public void initClick() {
        _info.setOnClickListener(this);
        layout_yuyue.setOnClickListener(this);
        layout_keshi.setOnClickListener(this);
        layout_jifen.setOnClickListener(this);
        layout_youhuijuan.setOnClickListener(this);
        layout_dingdan.setOnClickListener(this);
        // layout_xiulian.setOnClickListener(this);
        layout_collect.setOnClickListener(this);
        layout_setting.setOnClickListener(this);
        layout_fankui.setOnClickListener(this);
        layout_daipingjia.setOnClickListener(this);
        rpj.setOnClickListener(this);
        layout_cg.setOnClickListener(this);
        mAppCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reqid != null && !reqid.equals("")) {
                    String url = cancelUrl + "reqid=" + reqid;
                    new ThreadUtils_no(mContext, url, new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            String json = msg.obj.toString();
                            if (!json.equals("") && json != null) {
                                try {
                                    JSONObject object = new JSONObject(json);
                                    String code = object.getString("code");
                                    String message = object.getString("msg");
                                    String unbundling_code = object.getString("unbundling_code");
                                    String unbundling_msg = object.getString("unbundling_msg");

                                    if (code != null && code.equals("1")) {
                                        if (unbundling_code != null && unbundling_code.equals("1")) {
                                            SharedPreferences.Editor editor = sp.edit();
                                            editor.putBoolean("isLogin", false);
                                            editor.putString("reqid", "");
                                            editor.putString("type", "");
                                            editor.putString("yh_type", "0");
                                            editor.commit();
//                                            urlPath.getData("1");
                                            // Toast.makeText(SzActivity.this,"请重新登录",Toast.LENGTH_LONG).show();
                                            Intent intent1 = new Intent(mContext, loginActivity.class);
                                            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent1);
                                            Activity activity = (Activity) mContext;
                                            activity.finish();
                                            activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                        } else {
                                            ToastUtils.showToast(mContext, unbundling_msg);
                                        }

                                    } else {
                                        ToastUtils.showToast(mContext, message);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                ToastUtils.showToast(mContext, getResources().getString(R.string.error));
                            }
                        }
                    }).start();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_collet:
                Intent in06 = new Intent(mContext, WdscActivity.class);
                in06.putExtra("reqid", reqid);
                in06.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in06);

                break;
            case R.id.layout_dingdan:
                Intent intent10 = new Intent(mContext, HuanGouAactivity.class);
                break;
            case R.id.layout_fankui:
                Intent in03 = new Intent(mContext, fankuiActivity.class);
                in03.putExtra("reqid", reqid);
                in03.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in03);
                break;
            case R.id.layout_jifen:
                Intent in01 = new Intent(mContext, MyPointsActivity.class);
                in01.putExtra("reqid", reqid);
                in01.putExtra("jifen_num", jifen_num.getText().toString());
                in01.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in01);

                break;
            case R.id.layout_keshi:
                Intent in = new Intent(mContext, HyWcksActivity.class);
                in.putExtra("reqid", reqid);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in);

                break;
            case R.id.layout_setting:
                Intent in05 = new Intent(mContext, SzActivity.class);
                in05.putExtra("reqid", reqid);
                in05.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in05);

                break;
            case R.id.layout_cg:
                Intent incg = new Intent(mContext, SscgActivity.class);
                incg.putExtra("reqid", reqid);
                incg.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(incg);
                break;

            case R.id.layout_youhuijuan:
                Intent in07 = new Intent(mContext, WdyhjActivity.class);
                in07.putExtra("reqid", reqid);
                in07.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in07);

                break;
            case R.id.layout_yuyue:
                Intent in08 = new Intent(mContext, WdyyActivity.class);
                in08.putExtra("reqid", reqid);
                in08.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in08);

                break;
            case R.id.edit_info:
                Intent intent13 = new Intent(mContext, Edit_info.class);
                intent13.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent13);

                break;
            case R.id.layout_daipingjia:
                Intent intent = new Intent(mContext, DpjActivity.class);
                intent.putExtra("reqid", reqid);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                break;
            //扫描二维码签到
            case R.id.sign:
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE},
                            1000);
                }else{
                    useCamera();
                }

                break;


            case R.id.hy_xx:
                Intent intent9 = new Intent(mContext, XiaoXiActivity.class);
                intent9.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent9);

        }
    }
    public void useCamera(){
        Intent intent11 = new Intent(mContext,
                CaptureActivity.class);
        startActivityForResult(intent11, REQUEST_CODE_SCAN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN) {
            if (data != null) {
                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);
                Intent intent12 = new Intent(mContext, DecodeDetail.class);
                intent12.putExtra("classid", content);
                intent12.putExtra("reqid", reqid);
                startActivity(intent12);
            }
        }
    }

    static final int REQUEST_CODE_SCAN = 0x0000;


    static final String DECODED_CONTENT_KEY = "codedContent";
    static final String DECODED_BITMAP_KEY = "codedBitmap";

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                useCamera();
            } else
            {
                // Permission Denied
                Toast.makeText(getActivity(), "获取权限失败", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}