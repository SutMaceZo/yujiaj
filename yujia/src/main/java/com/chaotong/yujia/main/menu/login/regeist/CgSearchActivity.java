package com.chaotong.yujia.main.menu.login.regeist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.adapter.changguanlist_adapter;
import com.chaotong.yujia.main.entity.HuiYuanBean.SsAddBean;
import com.chaotong.yujia.main.entity.changguanList;

import com.chaotong.yujia.main.menu.benggong.Zxing.an.Intents;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.chaotong.yujia.main.utils.PullToFlash.PullToRefreshLayout;
import com.chaotong.yujia.main.utils.PullToFlash.PullableListView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/13 0013.
 */
public class CgSearchActivity extends BaseActivity {
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
    changguanlist_adapter Adapter;

    List<changguanList> list;
    List<changguanList> list2;



    List<changguanList> ListBean;
    List<changguanList> ListBean_search;

    changguanlist_adapter Adapter2;
    UrlPath urlPath = UrlPath.getUrlPath();
    String SsAddUrl=urlPath.getUrl()+"AllStadiums?";

    String SearchUrl=urlPath.getUrl()+"AllStadiumsSortServlet?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ssadd);
        ButterKnife.bind(this);
        initView();

    }

    private void initListener() {

        String url = SsAddUrl +"more="+more;
        new ThreadUtils_no(CgSearchActivity.this, url, handler).start();
    }

    private void initView() {

        text.setVisibility(View.GONE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sp = getSharedPreferences(MyApplication.SpName, MODE_PRIVATE);

        ListBean = new ArrayList<>();
        ListBean_search=new ArrayList<>();
        list=new ArrayList<>();
        list2=new ArrayList<>();


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
                        String url= null;
                        try {
                            url = SearchUrl+"sort="+ URLEncoder.encode(String.valueOf(editable),"UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        new ThreadUtils_no(CgSearchActivity.this,url,EHandler).start();
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
            super.handleMessage(msg);

            String json = msg.obj.toString();
            if (json != null) {
                try {
                    JSONObject object = new JSONObject(json);
                    String code = object.getString("code");
                    String message = object.getString("msg");
                    if (code.equals("1")) {
                        JSONArray name = object.getJSONArray("name");

                        if (list2!=null&&list2.size()>0){
                            list2.clear();
                        }
                        for (int i = 0; i < name.length(); i++) {
                            String aa = (name.get(i)).toString();
                            list2.add(new changguanList(aa));
                        }
                        init2(list2);
                    } else {
                        Toast.makeText(CgSearchActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }};

Handler handler=new Handler() {
    @Override
    public void handleMessage(Message msg) {

        String json = msg.obj.toString();
        if (json != null) {
            if(layout!=null){
                layout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
            try {
                JSONObject object = new JSONObject(json);
                String code = object.getString("code");
                String message = object.getString("msg");
                if (code.equals("1")) {
                    JSONArray name = object.getJSONArray("name");

                    if (list!=null&&list.size()>0){
                        list.clear();
                    }
                    for (int i = 0; i < name.length(); i++) {
                        String aa = (name.get(i)).toString();
                        list.add(new changguanList(aa));
                    }
                    init(list);
                } else {
                    Toast.makeText(CgSearchActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.handleMessage(msg);
        }}};





    private void init(List<changguanList> list) {
        if (more == 0) {
            if (ListBean != null && ListBean.size() > 0 && Adapter != null) {
                ListBean.clear();
            }
            if (list != null && list.size() > 0) {
                ListBean.addAll(list);
            }
            if (ListBean != null && ListBean.size() > 0) {
                Loadmore.setVisibility(View.VISIBLE);
                Adapter = new changguanlist_adapter(CgSearchActivity.this, ListBean);
                listview.setAdapter(Adapter);
            } else {
                Loadmore.setVisibility(View.INVISIBLE);
            }
        } else {
            if (list != null && list.size() > 0) {
                ListBean.addAll(list);
                Adapter.notifyDataSetChanged();
                Loadmore.setVisibility(View.VISIBLE);
            }
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (ListBean.get(i) != null && ListBean.get(i).getName() != null) {
                    Intent intent = getIntent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("sp_name", ListBean.get(i).getName());
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    ToastUtils.showToast(CgSearchActivity.this, "error");
                }

            }
        });
    }

    private void init2(List<changguanList> list) {
        if (more == 0) {
            if (ListBean_search != null && ListBean_search.size() > 0 && Adapter2 != null) {
                ListBean_search.clear();
            }
            if (list2 != null && list2.size() > 0) {
                ListBean_search.addAll(list2);
            }
            if (ListBean_search != null && ListBean_search.size() > 0) {
                Adapter2 = new changguanlist_adapter(CgSearchActivity.this, ListBean_search);
                Slistview.setAdapter(Adapter2);
            }

        } else {
            if (list2 != null && list2.size() > 0) {
                ListBean_search.addAll(list);
                Adapter.notifyDataSetChanged();
            }
        }

        Slistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (ListBean_search.get(i) != null && ListBean_search.get(i).getName() != null) {
                    Intent intent = getIntent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("sp_name", ListBean_search.get(i).getName());
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    ToastUtils.showToast(CgSearchActivity.this, "error");
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
                    more = 0;
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

