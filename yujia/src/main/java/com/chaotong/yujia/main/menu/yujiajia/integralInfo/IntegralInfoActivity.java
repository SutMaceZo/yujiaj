package com.chaotong.yujia.main.menu.yujiajia.integralInfo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.entity.HuiYuanBean.Hg_list;
import com.chaotong.yujia.main.entity.HuiYuanBean.JFDetailBean;
import com.chaotong.yujia.main.entity.HuiYuanBean.SZ;
import com.chaotong.yujia.main.menu.yujiajia.integralInfo.view.IntegralInfoListView;
import com.chaotong.yujia.main.menu.yujiajia.integralInfo.view.ListViewAdapter;
import com.chaotong.yujia.main.thread.ThreadUtils;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.chaotong.yujia.main.utils.PullToFlash.MyListener;
import com.chaotong.yujia.main.utils.PullToFlash.PullToRefreshLayout;
import com.chaotong.yujia.main.utils.PullToFlash.PullableListView;
import com.google.gson.Gson;




import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 积分明细
 */
public class IntegralInfoActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
    ImageView bt_back;
    JFDetailBean JFDetail;
    List<SZ> sz_list;/*收支数据*/
    SharedPreferences sp;
    String reqid = "";
    ListViewAdapter adapter;

    @Bind(R.id.record_listView)
     PullableListView listview;
    private PullToRefreshLayout ptrl;

    View loadmore;

    UrlPath urlPath = UrlPath.getUrlPath();
    private String url = urlPath.getUrl() + "SzDetailServlet?";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            Gson gson = new Gson();
            JFDetail = gson.fromJson(json, JFDetailBean.class);
            String code = JFDetail.getCode();
            String message = JFDetail.getMsg();
            if (code.equals("1")) {
                if (sz_list!=null&&sz_list.size()>0&&adapter!=null){
                    sz_list.clear();
                    adapter.notifyDataSetChanged();
                }
                if (JFDetail.getSz_list()!=null&&JFDetail.getSz_list().size()>0){
                    sz_list.addAll(JFDetail.getSz_list());
                }

                init();
            } else {
                Toast.makeText(IntegralInfoActivity.this, message, Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);
        }
    };
    private Handler xhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            Gson gson = new Gson();
            JFDetail = gson.fromJson(json, JFDetailBean.class);
            String code = JFDetail.getCode();
            String message = JFDetail.getMsg();
            if (code.equals("1")) {
                if(JFDetail.getSz_list()!=null&&JFDetail.getSz_list().size()>0){
                    for (int i=0;i<JFDetail.getSz_list().size();i++){
                        SZ  bean=JFDetail.getSz_list().get(i);
                        sz_list.add(bean);
                    }
                    adapter.notifyDataSetChanged();
                    loadmore.setVisibility(View.VISIBLE);
                }
            } else {
                Toast.makeText(IntegralInfoActivity.this, message, Toast.LENGTH_SHORT).show();
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.integral_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        rl_bt_back.setOnClickListener(this);
        sz_list = new ArrayList<SZ>();

        sp = getSharedPreferences("Yuga", MODE_PRIVATE);
        reqid = sp.getString("reqid", "");
        if (reqid.equals("")) {
            Toast.makeText(this, "请检查是否登录", Toast.LENGTH_LONG).show();
        } else {
            url = url + "reqid=" + reqid+"&more="+0;
            new ThreadUtils_no(this, url, handler).start();
        }
        ptrl= (PullToRefreshLayout) findViewById(R.id.refresh_view);
        ptrl.setOnRefreshListener(new MyListener());

        loadmore=ptrl.getChildAt(2);
        if (loadmore!=null){
            loadmore.setVisibility(View.INVISIBLE);
        }

    }
    int more=0;
    public class MyListener implements PullToRefreshLayout.OnRefreshListener
    {
        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout)
        {// 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg)
                {
                    new ThreadUtils_no(IntegralInfoActivity.this, url, handler).start();
                    // 千万别忘了告诉控件刷新完毕了哦！
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 2000);
        }
        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout)
        {
            // 加载操作
            new Handler()
            {
                @Override
                public void handleMessage(Message msg)
                {
                    more++;
                    url=url.replace("more=0","more="+more);
                    new ThreadUtils_no(IntegralInfoActivity.this, url, xhandler).start();
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 2000);
        }
    }
    private void init() {
        if (sz_list!=null&&sz_list.size()>0){
            adapter=new ListViewAdapter(IntegralInfoActivity.this,sz_list);
            listview.setAdapter(adapter);
            loadmore.setVisibility(View.VISIBLE);
        }else {
            loadmore.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.rl_bt_back:
                finish();
                break;
        }
    }
}
