package com.chaotong.yujia.main.menu.benggong.suo_shu_chang_guan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.entity.HuiYuanBean.SsAddBean;
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
 * Created by Administrator on 2017/2/13 0013.
 */
public class SsAddActivity extends BaseActivity {
    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
    ImageView back;

    @Bind(R.id.refresh_view)
    PullToRefreshLayout layout;
    @Bind(R.id.record_listView)
    PullableListView listview;

    @Bind(R.id.search_list)
    ListView Slistview;

    @Bind(R.id.iv_search)
    ImageView iv_search;
    @Bind(R.id.et_search)
    EditText et_search;


    @Bind(R.id.cg_add)
    TextView text;

    View Loadmore;
    int more = 0;
    String reqid;
    SharedPreferences sp;
    SscgAddAdapter Adapter;

    SsAddBean SsAdd;
    List<SsAddBean.ListBean> ListBean;

    SsAddBean SsAdd_search;
    List<SsAddBean.ListBean> ListBean_search;
    SscgAddAdapter Adapter2;
    UrlPath urlPath = UrlPath.getUrlPath();
    String SsAddUrl=urlPath.getUrl()+"CardStadiumsServlet?";

    String SearchUrl=urlPath.getUrl()+"AddCardSortServlet?";
   // http://192.168.0.5:8080/YuJia/AddCardSortServlet?reqid=7465160932&sort=%E6%9E%9C%E5%86%BB
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ssadd);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        text.setVisibility(View.GONE);


        rl_bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sp = getSharedPreferences(MyApplication.SpName, MODE_PRIVATE);
        reqid = sp.getString("reqid", reqid);
        ListBean = new ArrayList<>();
        ListBean_search=new ArrayList<>();


        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()==0){
                    layout.setVisibility(View.VISIBLE);
                    Slistview.setVisibility(View.GONE);
                }else {
                    layout.setVisibility(View.GONE);
                    Slistview.setVisibility(View.VISIBLE);
                    if (editable.length()>1){
                        String url=SearchUrl+"reqid="+reqid+"&sort="+editable;
                        new ThreadUtils_no(SsAddActivity.this,url,EHandler).start();
                    }
                }

            }
        });

        layout.setOnRefreshListener(new MyListener());
        Loadmore = layout.getChildAt(2);
        if (Loadmore != null) {
            Loadmore.setVisibility(View.INVISIBLE);
        }
        more=0;
        initListener();

    }
Handler EHandler=new Handler(){
    @Override
    public void handleMessage(Message msg) {

        String json = msg.obj.toString();
        if (json != null) {
            layout.refreshFinish(PullToRefreshLayout.SUCCEED);
            Gson gson = new Gson();
            SsAdd_search = gson.fromJson(json, SsAddBean.class);
            String code = SsAdd_search.getCode();
            String message = SsAdd_search.getMsg();
            if (("1").equals(code)) {
               // init(SsAdd);
                initList(SsAdd_search);

            } else if (("0").equals(code)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SsAddActivity.this);
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
                                Intent intent = new Intent(SsAddActivity.this, loginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .show();
            } else {
                ToastUtils.showToast(SsAddActivity.this, message);

            }

        } else {
            ToastUtils.showToast(SsAddActivity.this, getResources().getString(R.string.error));
        }

        super.handleMessage(msg);
    }
};

    private void initList(SsAddBean ssAdd_search) {
        if (ListBean_search!=null&&ListBean_search.size()>0){
            ListBean_search.clear();
        }
        if (ssAdd_search.getList()!=null&&ssAdd_search.getList().size()>0){
            ListBean_search.addAll(ssAdd_search.getList());
        }
        if (ListBean_search!=null&&ListBean_search.size()>0){
            Adapter2=new SscgAddAdapter(SsAddActivity.this,ListBean_search,reqid);
            Slistview.setAdapter(Adapter2);
        }

        Slistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (ListBean_search.get(i)!=null&&ListBean_search.get(i).getSp_id()!=null){
                    Intent intent=getIntent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("sp_name",ListBean_search.get(i).getSp_name());
                    intent.putExtra("sp_id",ListBean_search.get(i).getSp_id());
                    setResult(RESULT_OK,intent);
                    finish();
                }

            }
        });

    }

    private void initListener() {
        String url = SsAddUrl + "reqid=" + reqid+"&more="+more;
        new ThreadUtils_no(SsAddActivity.this, url, handler).start();
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = msg.obj.toString();
            if (json != null) {
                layout.refreshFinish(PullToRefreshLayout.SUCCEED);
                Gson gson = new Gson();
                SsAdd = gson.fromJson(json, SsAddBean.class);
                String code = SsAdd.getCode();
                String message = SsAdd.getMsg();
                if (("1").equals(code)) {
                    init(SsAdd);

                } else if (("0").equals(code)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SsAddActivity.this);
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
                                    Intent intent = new Intent(SsAddActivity.this, loginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            })
                            .show();
                } else {
                    ToastUtils.showToast(SsAddActivity.this, message);

                }

            } else {
                ToastUtils.showToast(SsAddActivity.this, getResources().getString(R.string.error));
            }
        }
    };


    private void init(SsAddBean addBean) {
        if (more == 0) {
            if (ListBean != null && ListBean.size() > 0 && Adapter != null) {
                ListBean.clear();
            }
            if (addBean.getList() != null && addBean.getList().size() > 0) {
                ListBean.addAll(addBean.getList());
            }
            if (ListBean!=null&&ListBean.size()>0){
                Loadmore.setVisibility(View.VISIBLE);
                Adapter=new SscgAddAdapter(SsAddActivity.this,ListBean,reqid);
                listview.setAdapter(Adapter);
            }else {
                Loadmore.setVisibility(View.INVISIBLE);
            }

        } else {
            if (addBean.getList()!=null&&addBean.getList().size()>0){
                ListBean.addAll(addBean.getList());
                Adapter.notifyDataSetChanged();
                Loadmore.setVisibility(View.VISIBLE);
            }
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (ListBean.get(i)!=null&&ListBean.get(i).getSp_id()!=null){
                    Intent intent=getIntent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("sp_name",ListBean.get(i).getSp_name());
                    intent.putExtra("sp_id",ListBean.get(i).getSp_id());
                    setResult(RESULT_OK,intent);
                    finish();
                }else {
                    ToastUtils.showToast(SsAddActivity.this,"error");
                }

            }
        });
    }
    public class MyListener implements PullToRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {

            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 千万别忘了告诉控件刷新完毕了哦！
                       more=0;
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
                       more++;
                      initListener();
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 3000);
        }
    }

}
