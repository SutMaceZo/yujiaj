package com.chaotong.yujia.main.menu.benggong.suo_shu_chang_guan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.entity.HuiYuanBean.SscgBean;
import com.chaotong.yujia.main.menu.login.loginActivity;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.chaotong.yujia.main.utils.PullToFlash.PullToRefreshLayout;
import com.chaotong.yujia.main.utils.PullToFlash.PullableListView;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/12 0012.
 * 我的场馆
 */
public class SscgActivity extends BaseActivity {
    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
    ImageView back;

    @Bind(R.id.record_listView)
    ListView listView;

    @Bind(R.id.srfl)
    SwipeRefreshLayout srfl;

    SscgBean Sscg;
    List<SscgBean.CardsBean> CardsBean;

    View Loadmore;
    String reqid;
    SharedPreferences sp;
    int more = 0;

    SscgAdapter adapter;
    UrlPath urlPath = UrlPath.getUrlPath();
    String SscgUrl = urlPath.getUrl() + "MyCardServlet?";

    @Bind(R.id.cg_add)
    TextView add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sscg);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        rl_bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sp = getSharedPreferences(MyApplication.SpName, MODE_PRIVATE);
        reqid = sp.getString("reqid", reqid);
        CardsBean = new ArrayList<>();


        srfl.setColorSchemeResources(R.color.lightblue, R.color.red, R.color.lightyellow, R.color.colorPrimaryDark);
        srfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        initListener();
                    }
                }, 2000);
            }
        });

        initListener();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent incg = new Intent(SscgActivity.this, SsAdd1Activity.class);
                incg.putExtra("reqid", reqid);
                incg.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(incg);
            }
        });

    }

    private void initListener() {
        String url = SscgUrl + "reqid=" + reqid;
        new ThreadUtils_no(SscgActivity.this, url, handler).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = msg.obj.toString();
            if (!json.equals("")&&json != null) {
                Log.i("wode--------",json);
                // layout.refreshFinish(PullToRefreshLayout.SUCCEED);
                if (srfl != null) {
                    srfl.setRefreshing(false);
                }
                Gson gson = new Gson();
                Sscg = gson.fromJson(json, SscgBean.class);
                String code = Sscg.getCode();
                String message = Sscg.getMsg();
                if (("1").equals(code)) {
                    init(Sscg);

                } else if (("0").equals(code)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SscgActivity.this);
                    builder.setCancelable(false);
                    builder.setTitle(getResources().getString(R.string.app_name) + "")
                            .setMessage(getResources().getString(R.string.message) + "")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SharedPreferences sp = getSharedPreferences(MyApplication.SpName, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putBoolean("isLogin", false);
                                    editor.putString("reqid", "");
                                    editor.putString("type", "");
                                    editor.commit();
                                    Intent intent = new Intent(SscgActivity.this, loginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            })
                            .show();
                } else {
                    ToastUtils.showToast(SscgActivity.this, message);

                }

            } else {
                ToastUtils.showToast(SscgActivity.this, getResources().getString(R.string.error));
            }
        }
    };

    private void init(SscgBean sscg) {

        if (more == 0) {
            if (CardsBean != null && CardsBean.size() > 0 && adapter != null) {
                CardsBean.clear();
            }
            if (sscg.getCards() != null && sscg.getCards().size() > 0) {
                CardsBean.addAll(sscg.getCards());
            }
            if (CardsBean != null && CardsBean.size() > 0) {
                //  Loadmore.setVisibility(View.VISIBLE);
                // Loadmore.clearAnimation();
                adapter = new SscgAdapter(SscgActivity.this, CardsBean);
                listView.setAdapter(adapter);
            } else {
                //  Loadmore.setVisibility(View.INVISIBLE);
            }

        } else {
            if (sscg.getCards() != null && sscg.getCards().size() > 0) {
                CardsBean.addAll(sscg.getCards());
                adapter.notifyDataSetChanged();
                // Loadmore.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (reflash) {
            initListener();
            reflash = false;
        }
    }

    Boolean reflash = false;

    @Override
    protected void onPause() {
        super.onPause();
        reflash = true;
    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {

            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 千万别忘了告诉控件刷新完毕了哦！
                    //more=0;
                    initListener();
                }
            }.sendEmptyMessageDelayed(0, 2000);
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {

            // 加载操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    //    more++;
                    //   initListener();


                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 3000);
        }
    }
}
