package com.chaotong.yujia.main.menu.yujiaolian.main;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.adapter.YuJiaoLianAdapter;
import com.chaotong.yujia.main.menu.xiulian.main.view.SpinerPopWindow;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.menu.yujiaolian.main.view.SelectType;
import com.chaotong.yujia.main.menu.yujiaolian.main.view.SyBean;
import com.chaotong.yujia.main.menu.yujiaolian.main.view.SyxqActivity;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.chaotong.yujia.main.utils.PullToFlash.PullToRefreshLayout;
import com.chaotong.yujia.main.utils.PullToFlash.PullableListView;
import com.chaotong.yujia.main.utils.viewUtils;
import com.google.gson.Gson;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class YueJiaoLianFragment extends Fragment {
    View mView;
    @Bind(R.id.yuejiaolian)
    LinearLayout yuejiaolian;
    @Bind(R.id.record_listView)
    PullableListView mLv;
    @Bind(R.id.refresh_view)
    PullToRefreshLayout mLayout;
    View mLoadmore;
    @Bind(R.id.probar)
    ProgressBar mProgressBar;
    @Bind(R.id.tv_value)
    TextView mText;
    @Bind(R.id.jl_1)
    LinearLayout jl_1;

    @Bind(R.id.yjl_bt)
    RelativeLayout bt_back;

    int more = 0;
    String region = "";
    String type = "全部";
    UrlPath urlPath = UrlPath.getUrlPath();
    SharedPreferences mSp;
    Context mContext;
    String mSyUrl = urlPath.getUrl() + "YjlTrainersServlet?";
    String mSTUrl = urlPath.getUrl() + "ClassTypeServlet";
    SelectType mTypeBean;
    SyBean mSyBean;
    List<String> mType;
    List<SyBean.TrainersBean> mTrainers;
    SpinerPopWindow<String> mSpinerPopWindow;
    YuJiaoLianAdapter mAdapter;


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = msg.obj.toString();
            Gson gson = new Gson();
            mSyBean = gson.fromJson(json, SyBean.class);
            if (mSyBean != null) {
                if (mLayout!=null){
                    mLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
                String code = mSyBean.getCode();
                String message = mSyBean.getMsg();
                if (code != null&&message!=null) {
                    if (code.equals("1")||code.equals("0")){
                        initSy(mSyBean);
                    }else {
                        ToastUtils.showToast(mContext, message);
                    }
                } else {
                    ToastUtils.showToast(mContext, message);
                }
            } else {
                ToastUtils.showToast(mContext, "数据获取失败");
            }
        }
    };

    private void initSy(SyBean mSybean) {
        if (more == 0) {
            if (mTrainers!=null&&mTrainers.size()>0&&mAdapter!=null){
                mTrainers.clear();
                mAdapter.notifyDataSetChanged();
            }
            if (mSybean.getTrainers()!=null&&mSybean.getTrainers().size()>=0){
                mTrainers.addAll(mSybean.getTrainers());
            }

            if (mTrainers != null && mTrainers.size() > 0) {
                mAdapter = new YuJiaoLianAdapter(mContext,mTrainers);
                mLv.setAdapter(mAdapter);
                mLoadmore.setVisibility(View.VISIBLE);

            }
        } else {

            if (mAdapter != null && mTrainers!= null) {
                mTrainers.addAll(mSybean.getTrainers());
                mAdapter.notifyDataSetChanged();
                mLoadmore.setVisibility(View.VISIBLE);
            }
        }
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext,SyxqActivity.class);
                String id = mTrainers.get(i).getTs_id();
                intent.putExtra("id",id);
                intent.putExtra("activity_tag","");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.main_yuejiaolian, container, false);
        ButterKnife.bind(this, mView);
        initView();

        return mView;
    }

    private void initView() {
        bt_back.requestFocus();
        bt_back.setEnabled(true);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        yuejiaolian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mType = new ArrayList<String>();
        mTrainers = new ArrayList<SyBean.TrainersBean>();
        mSp = mContext.getSharedPreferences(MyApplication.SpName, Context.MODE_PRIVATE);
        region = mSp.getString("findCity", "");
        more = 0;
        type = "全部";
        initSelectType();
        initDataListener(more, type);

        mLayout.setOnRefreshListener(new MyListener());
        mLoadmore = mLayout.getChildAt(2);
        if (mLoadmore != null) {
            mLoadmore.setVisibility(View.INVISIBLE);
        }

    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {

            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    more = 0;
                    type=mText.getText().toString();
                    initDataListener(more,type);
                    // 千万别忘了告诉控件刷新完毕了哦！

                }
            }.sendEmptyMessageDelayed(0, 2000);

        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {

            // 加载操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // 千万别忘了告诉控件加载完毕了哦！
                    more++;
                    type=mText.getText().toString();
                    initDataListener(more,type);
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 3000);
        }
    }

    private void initDataListener(int more, String type) {
        if (!region.equals("")&&region != null) {
            String url = "";
            String city = viewUtils.subString(region);
            String city2 = viewUtils.corpString(city);
           try {
                url = mSyUrl + "more=" + more + "&region=" + URLEncoder.encode(city2, "UTF-8") + "&type=" + URLEncoder.encode(type, "UTF-8");
               Log.i("-----yuejiaolian---",url);
            } catch (UnsupportedEncodingException e) {
                //e.printStackTrace();
            }
            // url=mSyUrl+"more="+more+"&region="+ city2+"&type="+type;
            new ThreadUtils_no(mContext, url, mHandler).start();
        } else {
            ToastUtils.showToast(mContext, "未选择地区");
        }

    }


    private void initSelectType() {
        new ThreadUtils_no(mContext, mSTUrl, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String json = msg.obj.toString();
                Log.i("yue-----------yue",json);
                Gson gson = new Gson();
                mTypeBean = gson.fromJson(json, SelectType.class);
                if (mTypeBean != null) {
                    String code = mTypeBean.getCode();
                    String message = mTypeBean.getMsg();
                    if (code != null && message != null && code.equals("1")) {
                        initSelect(mTypeBean);
                    } else {
                        ToastUtils.showToast(mContext,
                                message);
                    }
                } else {
                    ToastUtils.showToast(mContext,
                            mContext.getResources().getString(R.string.error));
                }

            }
        }).start();
    }

    private void initSelect(SelectType mTypeBean) {
        if (mType != null && mType.size() > 0) {
            mType.clear();
        }
        if (mTypeBean.getClasstype() != null
                && mTypeBean.getClasstype().size() >= 0) {
            mType.addAll(mTypeBean.getClasstype());
        }
        if (mType != null && mType.size() > 0) {

            if (mType.get(0) != null) {
                mText.setText(mType.get(0));
            }
            mSpinerPopWindow = new SpinerPopWindow<>(mContext, mType, new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    mSpinerPopWindow.dismiss();
                    mText.setText(mType.get(i));
                    type = mType.get(i);
                    initDataListener(0,type);
                }
            });
            mSpinerPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    setTextImage(R.drawable.icon_down);
                    mSpinerPopWindow.dismiss();
                }
            });
            mText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSpinerPopWindow.setWidth(mText.getWidth());
                    mSpinerPopWindow.showAsDropDown(jl_1);
                    setTextImage(R.drawable.icon_up);
                }
            });
        }
    }
    private void setTextImage(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 必须设置图片大小，否则不显示
        mText.setCompoundDrawables(null, null, drawable, null);
    }
}