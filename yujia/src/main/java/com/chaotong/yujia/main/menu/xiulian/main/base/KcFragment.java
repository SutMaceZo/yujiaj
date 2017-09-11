package com.chaotong.yujia.main.menu.xiulian.main.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.entity.Order.OrderKc;
import com.chaotong.yujia.main.entity.Order.SelectBean;
import com.chaotong.yujia.main.menu.login.loginActivity;
import com.chaotong.yujia.main.menu.xiulian.main.adapter.KcAdapter;
import com.chaotong.yujia.main.menu.xiulian.main.view.SpinerPopWindow;
import com.chaotong.yujia.main.menu.xiulian.main.view.cropString;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;

import com.chaotong.yujia.main.menu.yujiajia.jlxq.JlxqActivity;
import com.chaotong.yujia.main.menu.yujiajia.kcxq.KcDetailActivity;
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

/**
 * Created by Administrator on 2016/12/13 0013.
 */
public class KcFragment extends Fragment implements View.OnClickListener {

    View view;
    @Bind(R.id.refresh_view)
    PullToRefreshLayout layout;
    @Bind(R.id.record_listView)
    PullableListView mListview;

    View loadmore;
    @Bind(R.id.probar)
    ProgressBar progressBar;
    int more = 0;
    String region = "";
    String reqid = "";
    String latitude = "";
    String longitude = "";

    UrlPath urlPath = UrlPath.getUrlPath();
    Context context;
    SharedPreferences sp;
    String selectUrl = urlPath.getUrl() + "ClassServlet?";
    String resultUrl = urlPath.getUrl() + "ClassSearchServlet?";


    @Bind(R.id.kc_head_layout)
    LinearLayout kc_Layout;
    @Bind(R.id.kc_time)
    LinearLayout kc_time;
    @Bind(R.id.kc_type)
    LinearLayout kc_type;
    @Bind(R.id.kc_addr)
    LinearLayout kc_addr;

    @Bind(R.id.kc_time_tv)
    TextView kc_time_tv;
    @Bind(R.id.kc_type_tv)
    TextView kc_type_tv;
    @Bind(R.id.kc_addr_tv)
    TextView kc_addr_tv;

    SpinerPopWindow<String> spinerPopWindow_time;
    SpinerPopWindow<String> spinerPopWindow_type;
    SpinerPopWindowTwo<SelectBean.DistrictsBean> spinerPopWindow_addr;

    OrderKc mKcbean;
    List<OrderKc.ClasslistBean> mClasslist;
    KcAdapter adapter;


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = msg.obj.toString();
            if(!json.equals("")&&json!=null) {
                Gson gson = new Gson();
                mKcbean = gson.fromJson(json, OrderKc.class);
                Log.i("mKcbean---------", json);
                if (mKcbean != null) {
                    progressBar.setVisibility(View.INVISIBLE);
                    if (layout != null) {
                        layout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                    String code = mKcbean.getCode();
                    String message = mKcbean.getMsg();
                    if (code != null && message != null) {
                        if (code.equals("1") || ("0").equals(code) || code.equals("20")) {
                            initClassList(mKcbean);

                        } else {
                            //  ToastUtils.showToast(context,message);
                            Log.i("info", "errorLog:" + message);
                        }

                    }
                } else {
                    ToastUtils.showToast(context, "数据请求失败");
                }
            }
        }
    };

    private void initClassList(OrderKc mKcbean) {
        if (more==0){
            if (mClasslist!=null&&mClasslist.size()>0&&adapter!=null){
                mClasslist.clear();
                adapter.notifyDataSetChanged();
            }
            if (mKcbean.getClasslist()!=null&&mKcbean.getClasslist().size()>=0){
                mClasslist.addAll(mKcbean.getClasslist());
                for (int j =mClasslist.size()-1;j >= 0;j--){
                    if (mClasslist.get(j).getSy_code().equals("1")){
                        mClasslist.remove(j);
                    }
                }

            }
            if (mClasslist!=null&&mClasslist.size()>0){
                for (int j =mClasslist.size()-1;j >= 0;j--){
                    if (mClasslist.get(j).getSy_code().equals("1")){
                        mClasslist.remove(j);
                    }
                }
                adapter=new KcAdapter(context,mClasslist);
                Log.i("listadapter---------",mClasslist.toString());
                mListview.setAdapter(adapter);
                loadmore.setVisibility(View.VISIBLE);
            }


        }else {
            if (adapter!=null&&mClasslist!=null){
                mClasslist.addAll(mKcbean.getClasslist());
                for (int j =mClasslist.size()-1;j >= 0;j--){
                    if (mClasslist.get(j).getSy_code().equals("1")){
                        mClasslist.remove(j);
                    }
                }
                adapter.notifyDataSetChanged();
                loadmore.setVisibility(View.VISIBLE);
            }
        }
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context,KcDetailActivity.class);
                String id = mClasslist.get(i).getCt_id();
                intent.putExtra("classid",id);
                intent.putExtra("activity_tag","");
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.kcft, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

   Boolean reflash = false;

    @Override
    public void onPause() {
        super.onPause();
        reflash = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (reflash) {
            SharedPreferences sp1 = context.getSharedPreferences(MyApplication.SpName, Context.MODE_PRIVATE);
            region = sp1.getString("findCity", "");
            reqid = sp1.getString("reqid", "");
            latitude = sp1.getString("latitude", "");
            longitude = sp1.getString("longitude", "");
            initListenerSelect();
            more = 0;
          //  initListenerResult("", "", "", more);
            reflash = false;
        }
    }

    SelectBean selectBean;

    private void initView() {
        sp = context.getApplicationContext().getSharedPreferences(MyApplication.SpName, Context.MODE_PRIVATE);
        region = sp.getString("findCity", "");
        reqid = sp.getString("reqid", "");
        latitude = sp.getString("latitude", "");
        longitude = sp.getString("longitude", "");
        Time = new ArrayList<String>();
        Type = new ArrayList<String>();
        Districts = new ArrayList<SelectBean.DistrictsBean>();
        mClasslist=new ArrayList<OrderKc.ClasslistBean>();

        initListenerSelect();
        more = 0;
        progressBar.setVisibility(View.VISIBLE);
        layout.setOnRefreshListener(new MyListener());
        loadmore = layout.getChildAt(2);
        if (loadmore != null) {
            loadmore.setVisibility(View.INVISIBLE);
        }
    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {

            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    String type = kc_type_tv.getText().toString();
                    String bus = kc_addr_tv.getText().toString();
                    String time = cropString.corpString2(kc_time_tv.getText().toString());
                    initListenerResult(bus, time, type, 0);

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
                    String type = kc_type_tv.getText().toString();
                    String bus = kc_addr_tv.getText().toString();
                    String time = cropString.corpString2(kc_time_tv.getText().toString());
                    initListenerResult(bus, time, type, more);
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 3000);
        }
    }

    private void initListenerResult(String business, String time, String type, int more) {

        String url = null;
      try {
            url = resultUrl+"reqid="+reqid+"&region="+URLEncoder.encode(region,"UTF-8")+"&business="+URLEncoder.encode(business,"UTF-8")+"&date="+time+"&classtype="+URLEncoder.encode(type,"UTF-8")+"&longitude="+longitude+"&latitude="+latitude+"&more="+more;
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
        }

       /* url = resultUrl + "reqid=" + reqid
                + "&region=" + region + "&business=" +business
                + "&date=" + time + "&classtype=" + type +
                "&longitude=" + longitude + "&latitude=" + latitude + "&more=" + more;*/

        new ThreadUtils_no(context, url, mHandler).start();
    }

    private void initListenerSelect() {
        String city1 = viewUtils.subString(region);
        String city = viewUtils.corpString(city1);
        String url = null;
        try {
            url = selectUrl + "region=" + URLEncoder.encode(city1, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
        }
        new ThreadUtils_no(context, url, handler).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = msg.obj.toString();
            Log.i("课程json----------",json);
            if (!json.equals("")&&json!=null) {
                Gson gson = new Gson();
                selectBean = gson.fromJson(json, SelectBean.class);
                if (selectBean != null) {
                    String code = selectBean.getCode();
                    String message = selectBean.getMsg();
                    if (code != null && ("1").equals(code)) {
                        initSelect(selectBean);
                    } else {
                        ToastUtils.showToast(context, message);
                    }
                } else {
                    ToastUtils.showToast(context, "请检查网络连接");
                }
            }
        }
    };
    List<String> Time;
    List<String> Type;
    List<SelectBean.DistrictsBean> Districts;

    int mTime = 0;
    int mType = 0;
    int mAddr = 0;

    private void initSelect(SelectBean bean) {
        initTime(bean);
        initType(bean);
        initAddr(bean);

        String type = kc_type_tv.getText().toString();
        String bus = kc_addr_tv.getText().toString();
        String time =cropString.corpString2(kc_time_tv.getText().toString());
        more=0;
        initListenerResult(bus, time, type, more);

    }

    private void initAddr(SelectBean bean) {
        if (Districts != null && Districts.size() > 0) {
            Districts.clear();
        }

        if (bean.getDistricts() != null && bean.getDistricts().size() >= 0) {
            Districts.addAll(bean.getDistricts());
            kc_addr.setOnClickListener(this);
            if (bean.getClasstypes().size() > 0) {
                kc_type_tv.setText(Type.get(0));
            }
        }
        spinerPopWindow_addr = new SpinerPopWindowTwo<>(context, Districts, null);
        spinerPopWindow_addr.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setTextImage(R.drawable.icon_down, kc_addr_tv);
            }
        });
    }

    private void initType(SelectBean bean) {
        if (Type != null && Type.size() > 0) {
            Type.clear();
        }
        if (bean.getClasstypes() != null && bean.getClasstypes().size() >= 0) {
            Type.addAll(bean.getClasstypes());
            kc_type.setOnClickListener(this);
            if (bean.getClasstypes().size() > 0) {
                kc_type_tv.setText(Type.get(0));
            }

        }
        spinerPopWindow_type = new SpinerPopWindow<>(context, Type, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                spinerPopWindow_type.dismiss();
                kc_type_tv.setText(Type.get(i));

                String type = Type.get(i);
                String bus = kc_addr_tv.getText().toString();
                String time = cropString.corpString2(kc_time_tv.getText().toString());
                more = 0;
                initListenerResult(bus, time, type, more);

            }
        });
        spinerPopWindow_type.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setTextImage(R.drawable.icon_down, kc_type_tv);
            }
        });
    }

    private void initTime(final SelectBean bean) {
        if (Time != null && Time.size() > 0) {
            Time.clear();
        }
        if (bean.getDates() != null && bean.getDates().size() >= 0) {
            Time.addAll(bean.getDates());
            kc_time.setOnClickListener(this);
            if (bean.getDates().size() > 0) {
                kc_time_tv.setText(Time.get(0));
            }

        }
        spinerPopWindow_time = new SpinerPopWindow<>(context, Time, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                spinerPopWindow_time.dismiss();
                kc_time_tv.setText(Time.get(i));
                String type = kc_type_tv.getText().toString();
                String bus = kc_addr_tv.getText().toString();
                String time = cropString.corpString2(Time.get(i));
                more = 0;
                initListenerResult(bus, time, type, more);

            }
        });
        spinerPopWindow_time.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setTextImage(R.drawable.icon_down, kc_time_tv);
            }
        });
    }

    @Override
    public void onClick(View view) {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;

        switch (view.getId()) {
            case R.id.kc_addr:
                spinerPopWindow_type.setWidth(screenWidth);
                spinerPopWindow_addr.setHeight(screenHeigh / 3);
                spinerPopWindow_addr.showAsDropDown(kc_Layout);
                setTextImage(R.drawable.icon_up, kc_addr_tv);
                break;
            case R.id.kc_time:
                spinerPopWindow_time.setWidth(screenWidth);
                spinerPopWindow_time.showAsDropDown(kc_Layout);
                setTextImage(R.drawable.icon_up, kc_time_tv);
                break;
            case R.id.kc_type:
                spinerPopWindow_type.setWidth(screenWidth);
                spinerPopWindow_type.showAsDropDown(kc_Layout);
                setTextImage(R.drawable.icon_up, kc_type_tv);
                break;
        }
    }

    private void setTextImage(int resId, TextView text) {
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 必须设置图片大小，否则不显示
        text.setCompoundDrawables(null, null, drawable, null);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            initListenerSelect();
        } else {

        }
    }

    class SpinerPopWindowTwo<T> extends PopupWindow {
        private LayoutInflater inflater;
        private ListView mListView;
        private ListView mListView2;
        private List<SelectBean.DistrictsBean> list;
        private List<String> mlist;
        private int clickTemp = -1;
        private int clickTemp2 = -1;
        private MyAdapter mAdapter;
        private MyAdapter2 myAdapter2;
        private int parentId = 0;

        public SpinerPopWindowTwo(Context context, List<SelectBean.DistrictsBean> list, AdapterView.OnItemClickListener clickListener) {
            super(context);
            inflater = LayoutInflater.from(context);
            this.list = list;
            init(clickListener);
        }

        private void init(AdapterView.OnItemClickListener clickListener) {
            View view = inflater.inflate(R.layout.spiner_window_layout2, null);
            mlist = new ArrayList<String>();
            setContentView(view);
            setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            setFocusable(true);
            ColorDrawable dw = new ColorDrawable(0x00);
            setBackgroundDrawable(dw);
            mListView = (ListView) view.findViewById(R.id.l1);
            mListView2 = (ListView) view.findViewById(R.id.l2);

            if (list.get(parentId).getBusiness() != null) {
                mlist.addAll(list.get(parentId).getBusiness());
                Log.i("kecheng_list--------",mlist.toString());
            }
            mAdapter = new MyAdapter();
            mListView.setAdapter(mAdapter);
            myAdapter2 = new MyAdapter2(mlist);
            mListView2.setAdapter(myAdapter2);
            setSelection2(0);

            mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    setSelection2(i);
                    kc_addr_tv.setText(mlist.get(i));
                    myAdapter2.notifyDataSetChanged();

                    String type = kc_type_tv.getText().toString();
                    String bus = mlist.get(i);
                    String time = cropString.corpString2(kc_time_tv.getText().toString());
                    more = 0;
                    initListenerResult(bus, time, type, more);
                    spinerPopWindow_addr.dismiss();

                }
            });

            setSelection(0);


            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    parentId = i;
                    setSelection(parentId);
                    mAdapter.notifyDataSetChanged();
                    if (mlist != null && mlist.size() > 0 && myAdapter2 != null) {
                        mlist.clear();
                        myAdapter2.notifyDataSetChanged();
                    }

                    if (list.get(parentId).getBusiness() != null
                            && list.get(parentId).getBusiness().size() >= 0) {
                        mlist.addAll(list.get(parentId).getBusiness());
                        myAdapter2.notifyDataSetChanged();
                    }
                }
            });
        }

        private class MyAdapter extends BaseAdapter {

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder = null;
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.spiner_item_layout, parent, false);
                    holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                if (clickTemp == position) {
                    holder.tvName.setBackgroundResource(R.color.white);
                } else {
                    holder.tvName.setBackgroundResource(R.color.gainsboro);
                }
                holder.tvName.setText(list.get(position).getDistrict());
                return convertView;
            }
        }

        public void setSelection(int i) {
            clickTemp = i;
        }

        public void setSelection2(int i) {
            clickTemp2 = i;
        }

        private class ViewHolder {
            private TextView tvName;
        }

        private class MyAdapter2 extends BaseAdapter {
            List<String> mlist;

            public MyAdapter2(List<String> slist) {
                this.mlist = slist;
            }

            @Override
            public int getCount() {
                return mlist.size();
            }

            @Override
            public Object getItem(int position) {
                return mlist.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder2 holder = null;
                if (convertView == null) {
                    holder = new ViewHolder2();
                    convertView = inflater.inflate(R.layout.spiner_item_layout, parent, false);
                    holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder2) convertView.getTag();
                }
                if (clickTemp2 == position) {
                    holder.tvName.setBackgroundResource(R.color.white);
                } else {
                    holder.tvName.setBackgroundResource(R.color.gainsboro);
                }
                holder.tvName.setText(mlist.get(position));
                return convertView;
            }
        }

        private class ViewHolder2 {
            private TextView tvName;
        }

    }
}
