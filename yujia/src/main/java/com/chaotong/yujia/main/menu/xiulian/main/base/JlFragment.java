package com.chaotong.yujia.main.menu.xiulian.main.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.adapter.CoachAdapter;
import com.chaotong.yujia.main.adapter.OrderJlAdapterAdapter;
import com.chaotong.yujia.main.adapter.spanner_adapter;
import com.chaotong.yujia.main.entity.Order.OrderJl;
import com.chaotong.yujia.main.menu.xiulian.main.view.SpinerPopWindow;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.menu.yujiajia.jlxq.JlxqActivity;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.chaotong.yujia.main.utils.PullToFlash.PullToRefreshLayout;
import com.chaotong.yujia.main.utils.PullToFlash.PullableListView;
import com.chaotong.yujia.main.utils.viewUtils;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/13 0013.
 */
public class JlFragment extends Fragment{
   View view;
    @Bind(R.id.record_listView)
    PullableListView lv;
    @Bind(R.id.refresh_view)
    PullToRefreshLayout layout;
    View loadmore;
    @Bind(R.id.probar)
    ProgressBar progressBar;
    @Bind(R.id.tv_value)
    TextView text;
    int more=0;
    String region="";
    String type="全部";
    UrlPath urlPath = UrlPath.getUrlPath();
    String jlUrl= urlPath.getUrl()+"TrainersServlet?";
    SharedPreferences sp;
    Context context;

    @Bind(R.id.jl_1)
    LinearLayout jl_1;

    OrderJl JlBean;
    List<String> types;
    List<OrderJl.TrainersBean> Trainers;
    OrderJlAdapterAdapter adapter;

    private SpinerPopWindow<String> mSpinerPopWindow;


    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json=msg.obj.toString();
            if (!json.equals("") && json!=null) {
                Gson gson = new Gson();
                JlBean = gson.fromJson(json, OrderJl.class);
                if (JlBean != null) {
                    if (Visible == 1) {
                        layout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    } else if (Visible == 2) {
                        layout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }

                    String code = JlBean.getCode();
                    String message = JlBean.getMsg();
                    if (code != null && message != null) {
                        if (("1").equals(code) || ("0").equals(code) || ("20").equals(code)) {
                            init(JlBean);
                        } else {
                            ToastUtils.showToast(context, message);
                        }
                    } else {
                        ToastUtils.showToast(context, message);
                    }
                }
            }
        }
    };
    private void init(OrderJl jlBean) {
        if (types!=null&&types.size()>0){
            types.clear();
        }
        types=jlBean.getTs_types();
        if (types!=null&&types.size()>=0){
            text.setOnClickListener(clickListener);
            mSpinerPopWindow=new SpinerPopWindow<>(context,types,itemClickListener);
            mSpinerPopWindow.setOnDismissListener(dismissListener);
        }
            initT(jlBean);

    }

    /**
     * 监听popupwindow取消
     */
    private PopupWindow.OnDismissListener dismissListener=new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            setTextImage(R.drawable.icon_down);
        }
    };
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            mSpinerPopWindow.dismiss();
            text.setText(types.get(position));
            type=types.get(position);
            initListener(0,type);

        }
    };

    /**
     * 显示PopupWindow
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_value:
                    mSpinerPopWindow.setWidth(text.getWidth());
                    mSpinerPopWindow.showAsDropDown(jl_1);
                    setTextImage(R.drawable.icon_up);
                    break;
            }
        }
    };
    /**
     * 给TextView右边设置图片
     * @param resId
     */
    private void setTextImage(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());// 必须设置图片大小，否则不显示
        text.setCompoundDrawables(null, null, drawable, null);
    }

    private void initT(OrderJl Jl) {
        if (more == 0) {
            if (Trainers!=null&&Trainers.size()>0&&adapter!=null){
                Trainers.clear();
                adapter.notifyDataSetChanged();
            }
            if (Jl.getTrainers()!=null&&Jl.getTrainers().size()>0){
                Trainers.addAll(Jl.getTrainers());
            }

            if (Trainers != null && Trainers.size() > 0) {
                adapter = new OrderJlAdapterAdapter(context,Trainers);
                lv.setAdapter(adapter);
                loadmore.setVisibility(View.VISIBLE);
            }else {
                loadmore.setVisibility(View.INVISIBLE);
            }
        } else {
            if (Jl.getTrainers()!=null&&Jl.getTrainers().size()>0) {
                Trainers.addAll(Jl.getTrainers());
                adapter.notifyDataSetChanged();
                loadmore.setVisibility(View.VISIBLE);
            }
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context,JlxqActivity.class);
                String id = Trainers.get(i).getTs_id();
                intent.putExtra("id",id);
                intent.putExtra("activity_tag","buxianshi");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.jlft,container,false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

   Boolean reflash=false;
    @Override
    public void onPause() {
        super.onPause();
        reflash=true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (reflash){
            SharedPreferences   sp1=context.getSharedPreferences(MyApplication.SpName, Context.MODE_PRIVATE);
            region=sp1.getString("findCity","");
            initListener(0,"全部");
            reflash=false;
        }
    }
int Visible=0;
    private void initView() {
        types=new ArrayList<>();
        Trainers=new ArrayList<>();
        sp=getContext().getSharedPreferences(MyApplication.SpName, Context.MODE_PRIVATE);
        region=sp.getString("findCity","");
        more=0;
        type="全部";
        Visible=0;
        layout.setOnRefreshListener(new MyListener());
        initListener(more,type);


        loadmore = layout.getChildAt(2);
        if (loadmore != null) {
            loadmore.setVisibility(View.INVISIBLE);
        }
    }

    private void initListener(int more,String ty) {
        String city1=viewUtils.subString(region);
        String city= viewUtils.corpString(city1);
        String url= null;
       try {
            url = jlUrl+"more="+more+"&region="+URLEncoder.encode(city,"UTF-8")+"&type="+ URLEncoder.encode(ty,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
        }

        new ThreadUtils_no(getContext(),url,handler).start();
    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {

            // 下拉刷新操作
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    more = 0;
                    Visible=1;
                    initListener(more,type);
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
                    Visible=2;
                    initListener(more,type);
                   // pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 3000);
        }
    }
}



