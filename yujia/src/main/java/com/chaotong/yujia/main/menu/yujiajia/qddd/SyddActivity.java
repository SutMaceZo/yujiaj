package com.chaotong.yujia.main.menu.yujiajia.qddd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
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
import com.chaotong.yujia.main.Constants;
import com.chaotong.yujia.main.MainActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.entity.Order.PayConfigBean;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.menu.yujiajia.cgxq.CgxqActivity;
import com.chaotong.yujia.main.menu.yujiajia.jlxq.JlxqActivity;
import com.chaotong.yujia.main.menu.yujiaolian.main.view.SyxqActivity;
import com.chaotong.yujia.main.property.yueke.YhjS;
import com.chaotong.yujia.main.property.yujiajia.YhjPro;
import com.chaotong.yujia.main.property.yujiaolian.Order;
import com.chaotong.yujia.main.property.yujiaolian.OrderInfo;
import com.chaotong.yujia.main.thread.ThreadUtils;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.chaotong.yujia.main.utils.NoDoubleClickListener;
import com.google.gson.Gson;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/7 0007.
 */

public class SyddActivity extends BaseActivity implements View.OnClickListener {
    String classid = "";
    @Bind(R.id.sy_time)
    TextView sy_time;
    @Bind(R.id.sy_jl)
    TextView sy_jl;
    @Bind(R.id.sy_cg)
    TextView sy_cg;
    @Bind(R.id.sy_num)
    TextView sy_num;
    @Bind(R.id.sy_max)
    TextView sy_max;
    @Bind(R.id.sy_have)
    TextView sy_have;
    @Bind(R.id.sy_load)
    TextView sy_load;
    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
    ImageView bt_back;

    @Bind(R.id.radooGroup)
    RadioGroup mSyRg;

    @Bind(R.id.sy_dj)
    RadioButton mSyDj;
    @Bind(R.id.sy_bc)
    RadioButton mSyBc;

    @Bind(R.id.order_bt)
    Button mOrderBt;


  /*  @Bind(R.id.swfl)
    SwipeRefreshLayout swfl;*/

    @Bind(R.id.select_yhj)
    TextView select_yhj;

    @Bind(R.id.progress)
    ProgressBar progress;

    UrlPath urlPath = UrlPath.getUrlPath();
    String url = urlPath.getUrl() + "OrderInfoServlet?";
    String WXurl = urlPath.getUrl() + "Unifiedorder?app_type=android";
    Order order;
    OrderInfo orderinfo;

    String reqid = "";
    SharedPreferences sp;

    String mDjmoney;
    String mBcmoney;
    String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sydd);
        ButterKnife.bind(this);
        init();
        registerBroadcastReceiver();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            if(!json.equals("")&&json != null) {
                Log.i("zhifu-------------",json);
                Gson gson = new Gson();
                order = gson.fromJson(json, Order.class);
                String code = order.getCode();
                String message = order.getMsg();

                if (code != null && code.equals("1")) {
                    orderinfo = order.getOrderInfo();
                    initView();
                } else {
                    ToastUtils.showToast(SyddActivity.this, message);
                }
            }
            super.handleMessage(msg);
        }
    };

    private void initView() {
        sy_time.setText(orderinfo.getDate() + " " + orderinfo.getTime());
        String s = StringEscapeUtils.unescapeJava(orderinfo.getTrainername());
        sy_jl.setText(s);
        sy_cg.setText(orderinfo.getStadiumname());
        sy_num.setText(orderinfo.getOrderid());
        sy_max.setText(orderinfo.getMaxpeople());
        sy_have.setText(orderinfo.getMinpeople());
        sy_load.setText(orderinfo.getHavepeople());

        flag = orderinfo.getFlag();

        mDjmoney = orderinfo.getOrdermoney();
        if (!flag.equals("") && flag != null) {
            if (flag.equals("0")) {
                mSyDj.setText("定金金额" + "("+mDjmoney+")");
            } else if (flag.equals("1")) {
                mSyDj.setText("固定金额" +"("+mDjmoney+")");
            }
        }
        mBcmoney = orderinfo.getBlockbookingmoney();
        mSyBc.setText("包场支付" +"("+mBcmoney+")");
        //此处先解开试试
        if (!orderinfo.getIfblockbooking().equals("")) {
            if (orderinfo.getIfblockbooking().equals("false")) {
                //mSyBc.setBackgroundResource(R.color.gainsboro);
                mSyBc.setText("不可包场");
                mSyBc.setEnabled(false);
            } else {
                //mSyBc.setBackgroundResource(R.color.lightgreen);
                mSyBc.setEnabled(true);
            }
        }
    }

    boolean reflash = false;

    @Override
    protected void onPause() {
        super.onPause();
        reflash = true;
        progress.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (reflash) {
            final String Url = url + "classid=" + classid + "&reqid=" + reqid;
            if (!("").equals(classid)) {
                new ThreadUtils_no(this, Url, handler).start();
            }
            reflash = false;
            yhj_id = "";
            select_yhj.setText("请选择优惠券");
        }
    }

    private void init() {
        sp = getSharedPreferences(MyApplication.SpName, MODE_PRIVATE);
        reqid = sp.getString("reqid", "");
        classid = getIntent().getStringExtra("classid");
        final String Url = url + "classid=" + classid + "&reqid=" + reqid;
        if (!("").equals(classid)) {
            new ThreadUtils_no(this, Url, handler).start();
        }

       /* swfl.setColorSchemeResources(R.color.aquamarine, R.color.blue, R.color.colorPrimaryDark, R.color.red);
        swfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!("").equals(classid)) {
                            new ThreadUtils_no(SyddActivity.this, Url, handler).start();
                        }
                    }
                }, 2000);
            }
        });*/

        initEvent();
    }

    private void initEvent() {
        rl_bt_back.setOnClickListener(this);
        mOrderBt.setOnClickListener(this);


        mSyRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.sy_dj:
                        mType = 0;
                        GetWx(mDjmoney, MyApplication.CT_MONEY);
                        break;
                    case R.id.sy_bc:
                        mType = 1;
                        GetWx(mBcmoney, MyApplication.CT_BlOCkBOOK);
                        break;
                }
            }
        });

        select_yhj.setOnClickListener(this);
    }

    int mType = 0;

    private void GetWx(String money, String MoneyType) {
        if (!("").equals(reqid) && reqid != null) {
            String content2 = "";
            JSONObject object2 = new JSONObject();
            try {
                object2.put("reqid", reqid);
                object2.put("orderNo", orderinfo.getOrderid());
                if ( !money.equals("") &&money != null) {
                    object2.put("money", money);
                }
                object2.put("yytype", MoneyType);
                //object2.put("app_type", MyApplication.APP_TYPE);
                object2.put("app_type","androidOnline");
                object2.put("ct_id", classid);
                if (yhj_id != null) {
                    object2.put("yhj_id", yhj_id);
                } else {
                    Log.i("info", "error:yhj_id==null");
                }
            } catch (JSONException e) {
               // e.printStackTrace();
            }
            content2 = String.valueOf(object2);
            Log.i("info", "Sydd:" + content2);
            progress.setVisibility(View.VISIBLE);
            new ThreadUtils(SyddActivity.this, WXurl, content2, mHandler).start();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            String qxUrl = urlPath.getUrl() + "QxOrder";
            JSONObject object = new JSONObject();
            String content = "";
            try {
                object.put("reqid", reqid);
                object.put("ct_id", order.getOrderInfo().getClassid());
            } catch (JSONException e) {
                //e.printStackTrace();
            }
            content = String.valueOf(object);

            new ThreadUtils(SyddActivity.this, qxUrl, content, xHandler).start();


        }
        return super.onKeyDown(keyCode, event);
    }

    YhjS yhjs;
    private PopupWindow popupWindow;

    String yhj_id = "";

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_bt_back:

                String qxUrl = urlPath.getUrl() + "QxOrder";
                JSONObject object = new JSONObject();
                String content = "";
                try {
                    object.put("reqid", reqid);
                    object.put("ct_id", order.getOrderInfo().getClassid());
                } catch (JSONException e) {
                    //e.printStackTrace();
                }
                content = String.valueOf(object);

                new ThreadUtils(SyddActivity.this, qxUrl, content, xHandler).start();

                break;


            case R.id.select_yhj:
                String url = urlPath.getUrl() + "YyTypeServlet?yytype=券" + "&reqid=" + reqid + "&stadiumid=" + orderinfo.getStadiumid() + "&classid=" + classid;
                progress.setVisibility(View.VISIBLE);
                new ThreadUtils_no(SyddActivity.this, url, new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        String json = msg.obj.toString();
                        Gson gson = new Gson();
                        yhjs = gson.fromJson(json, YhjS.class);
                        if (yhjs != null) {
                            progress.setVisibility(View.INVISIBLE);
                            String code = yhjs.getCode();
                            String message = yhjs.getMsg();
                            if (code != null && code.equals("1")) {

                                initYhj(yhjs);
                            } else {
                                ToastUtils.showToast(SyddActivity.this, message);
                            }
                        }
                    }
                }).start();

                break;
            case R.id.order_bt:
                initWx(mPayConfig);
                break;
        }
    }

    private void initYhj(YhjS yhjs) {
        if (yhjs.getYytype_code() != null) {
            if (!yhjs.getYytype_code().equals("12")) {
                select_yhj.setText(yhjs.getYytype_msg());
            } else {
                if (yhjs.getYhj() != null && yhjs.getYhj().size() > 0) {
                    popupWindow();
                }
            }

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
                screenWidth / 3 * 2, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setFocusable(true);
        popupWindow.update();

        popupWindow.setContentView(contentView);
        // 一个自定义的布局，作为显示的内容
        ListView yhj_lv = (ListView) contentView.findViewById(R.id.yhj_list);
        final List<YhjPro> yhjPros = yhjs.getYhj();
        YhjItemAdapter yhjAdapter = new YhjItemAdapter(this, yhjPros);
        yhj_lv.setAdapter(yhjAdapter);
        yhj_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                yhj_id = yhjPros.get(position).getYhj_id();

                mDjmoney = yhjPros.get(position).getCt_money();
                mBcmoney = yhjPros.get(position).getCt_blockbook();

                if (flag != null && !flag.equals("")) {
                    if (flag.equals("0")) {
                        mSyDj.setText("定金金额" + "("+mDjmoney+")");
                    } else if (flag.equals("1")) {
                        mSyDj.setText("固定金额" + "("+mDjmoney+")");
                    }
                }
                mBcmoney = orderinfo.getBlockbookingmoney();
                mSyBc.setText("包场支付" +"("+mBcmoney+")");

               /* sy_dj.setText("定金金额："+mDjmoney);
                sy_bc.setText("包场金额："+mBcmoney);*/
                ///
                select_yhj.setText(yhjPros.get(position).getYhj_sm());
                popupWindow.dismiss();
            }
        });
        //popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
    }

    PayConfigBean mPayConfig;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = msg.obj.toString();
            if(!json.equals("")&&json!=null) {
                Log.i("dingjin-----------",json);
                Gson gson = new Gson();
                mPayConfig = gson.fromJson(json, PayConfigBean.class);
                if (mPayConfig != null) {
                    progress.setVisibility(View.INVISIBLE);
                    String code = mPayConfig.getCode();
                    String message = mPayConfig.getMsg();
                    if (code != null && code.equals("1")) {
                        Constants.APP_ID = mPayConfig.getAppid();
                        if (mType == 0) {
                            mSyRg.check(mSyDj.getId());
                        } else {
                            mSyRg.check(mSyBc.getId());
                        }

                    } else {
                        mSyRg.clearCheck();
                        ToastUtils.showToast(SyddActivity.this, message);
                    }


                }
            }
        }
    };
    PayReq req;
    final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);

    private void initWx(final PayConfigBean mPayConfig) {

        AlertDialog.Builder builder = new AlertDialog.Builder(SyddActivity.this);
        // builder.setCancelable(false);
        builder.setTitle(getResources().getString(R.string.app_name) + "")
                .setMessage(getResources().getString(R.string.pay) + "")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!mPayConfig.equals("")){
                           // Log.i("pay-----------",mPayConfig.toString());
                            req = new PayReq();
                            req.appId = mPayConfig.getAppid();
                            req.partnerId = mPayConfig.getPartnerid();
                            req.prepayId = mPayConfig.getPrepayid();
                            req.packageValue = mPayConfig.getPackages();
                            req.nonceStr = mPayConfig.getNoncestr();
                            req.timeStamp = mPayConfig.getTimestamp();
                            req.sign = mPayConfig.getSign();
                            msgApi.registerApp(mPayConfig.getAppid());
                            progress.setVisibility(View.VISIBLE);
                            msgApi.sendReq(req);
                        }else {
                            Toast.makeText(SyddActivity.this,"请选择支付方式",Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .show();
    }

    Handler xHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = msg.obj.toString();
            if (json != null) {
                try {
                    JSONObject object = new JSONObject(json);
                    String code = object.getString("code");
                    String message = object.getString("msg");
                    if (code != null && message != null && code.equals("1")) {
                        // ToastUtils.showToast(SyddActivity.this, "取消成功");
                        finish();
                      /*  final String Url = url + "classid=" + classid + "&reqid=" + reqid;
                        if (!("").equals(classid)) {
                            new ThreadUtils_no(SyddActivity.this, Url, handler).start();
                        }*/
                    }
                } catch (JSONException e) {
                   // e.printStackTrace();
                }
            } else {
                ToastUtils.showToast(SyddActivity.this, getResources().getString(R.string.error));
            }
        }
    };

    public void registerBroadcastReceiver() {
        receiver = new WxPayBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WXPAY_ACTION);
        registerReceiver(receiver, filter);
    }

    public static final String WXPAY_ACTION = "com.chaotong.yujia.main.wxpay";
    WxPayBroadcastReceiver receiver;


    //问题：
    // 1.支付成功后可以继续支付
    //2.支付失败后，订单已生成，重复点击多次失败，后返回可能会只清除一个订单（可能后台会把所有的假订单清掉，之后可能也有问题）
    //3.支付取消，订单已生成，重复点击多次失败，后返回可能会只清除一个订单
    //4.第一次点定金，后取消，此时订单已生成，想要包场但由于已存在订单，固无法完成包场
    //解决办法：根据广播接受到的状态码，选择什么时候去发送请求作废订单
    class WxPayBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if (action.equals(WXPAY_ACTION)) {

                int code = intent.getIntExtra("code", -10000);
                Log.i("zfcode---------",code+"");
                if (code == 0) {// 支付成功

                    mOrderBt.setBackgroundResource(R.drawable.text_bg4);
                    mOrderBt.setEnabled(false);


                } else if (code == -1) {// 失败

                    Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                    mOrderBt.setEnabled(true);

                } else if (code == -2) {// 用户取消
                    mOrderBt.setEnabled(true);
                    Toast.makeText(context, "取消支付", Toast.LENGTH_SHORT).show();
                }

            }

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (receiver != null) {
            unregisterReceiver(receiver);
        }

    }
}
