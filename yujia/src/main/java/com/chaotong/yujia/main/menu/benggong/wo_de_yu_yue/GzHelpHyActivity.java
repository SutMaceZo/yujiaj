package com.chaotong.yujia.main.menu.benggong.wo_de_yu_yue;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.entity.Guanzhu.YyInfo;
import com.chaotong.yujia.main.menu.benggong.wo_de_yu_yue.view.VpSimpleFragment2;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.menu.yujiajia.jlxq.view.ViewPagerIndicator;
import com.chaotong.yujia.main.menu.yujiajia.jlxq.view.VpSimpleFragment;
import com.chaotong.yujia.main.property.yujiajia.Classdetails;
import com.chaotong.yujia.main.property.yujiajia.Detail;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.chaotong.yujia.main.utils.CustomViewPager;
import com.foamtrace.photopicker.Image;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/11 0011.
 */

public class GzHelpHyActivity extends BaseActivity {
    @Bind(R.id.id_indicator)
     ViewPagerIndicator mIndicator;

    @Bind(R.id.swfl)
    SwipeRefreshLayout swfl;

    private List<VpSimpleFragment2> mTabContents = new ArrayList<>();

    private FragmentPagerAdapter mAdapter;

    @Bind(R.id.id_vp)
     ViewPager mViewPager;

    @Bind(R.id.rl_back)
    RelativeLayout rl_back;
    @Bind(R.id.back)
    ImageView back;
    UrlPath urlPath = UrlPath.getUrlPath();
    private String reqid = "";
    private String hy_id = "";
    private String gzyyUrl = urlPath.getUrl() + "GzYy_ClassServlet?";

    private List<String> mDatas = new ArrayList<>();
    YyInfo yyinfo;
    List<Classdetails> classdeteils = new ArrayList<>();
    Detail deteil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gz_yy);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        reqid = getIntent().getStringExtra("reqid");
        hy_id = getIntent().getStringExtra("id");
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initListener();

        swfl.setColorSchemeResources(R.color.blue, R.color.yellow,
                R.color.white, R.color.aquamarine);
        swfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            if (json!=null){
                swfl.setRefreshing(false);
                Gson gson = new Gson();
                yyinfo = gson.fromJson(json, YyInfo.class);
                String code=yyinfo.getCode();
                String message=yyinfo.getMsg();
                if (code!=null&&code.equals("1")){
                    initData();
                    mIndicator.setTabItemTitles(mDatas);
                    mViewPager.setAdapter(mAdapter);
                    mIndicator.setViewPager(mViewPager, 0);
                }else {
                    ToastUtils.showToast(GzHelpHyActivity.this,message);
                }
            }
            super.handleMessage(msg);
        }
    };

    private void initData() {
        String code = yyinfo.getCode();
        if (("1").equals(code)) {
            classdeteils = yyinfo.getClassdetails();
            if (classdeteils != null && classdeteils.size() > 0) {

                for (int i = 0; i < classdeteils.size(); i++) {
                    mDatas.add(i, classdeteils.get(i).getDate());
                }
                for (int i = 0; i < mDatas.size(); i++) {//日期
                    String data = mDatas.get(i);
                    VpSimpleFragment2 fragment = VpSimpleFragment2.newInstance(this, data, classdeteils, i, reqid, hy_id);
                    mTabContents.add(fragment);
                }
                mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
                    @Override
                    public Fragment getItem(int position) {
                        return mTabContents.get(position);
                    }

                    @Override
                    public int getCount() {
                        return mTabContents.size();
                    }

                    @Override
                    public int getItemPosition(Object object) {
                        return super.getItemPosition(object);
                    }
                };

            }
        } else {
            Toast.makeText(GzHelpHyActivity.this, yyinfo.getMsg(), Toast.LENGTH_SHORT).show();
        }

    }

    private void initListener() {
        String url = gzyyUrl + "reqid=" + reqid + "&hy_id=" + hy_id;
        new ThreadUtils_no(GzHelpHyActivity.this, url, handler).start();
    }
}
