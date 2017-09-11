package com.chaotong.yujia.main.menu.yujiaolian.main.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.menu.yujiajia.cgxq.view.CgxqAdapter;
import com.chaotong.yujia.main.menu.yujiajia.jlxq.view.CgListView;
import com.chaotong.yujia.main.menu.yujiajia.kcxq.KcDetailActivity;
import com.chaotong.yujia.main.menu.yujiajia.qddd.QdddActivity;
import com.chaotong.yujia.main.menu.yujiajia.qddd.SyddActivity;
import com.chaotong.yujia.main.property.yujiajia.Classdetails;
import com.chaotong.yujia.main.property.yujiajia.Detail;
import com.chaotong.yujia.main.utils.viewUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SyVpFragment extends Fragment implements SyxqAdapter.Callback {
    public static final String BUNDLE_TITLE = "title";
    private String mTitle = "DefaultValue";
    private CgListView listView;
    private static Context con;
    private List<SyxqBean.ClassdetailsBean> classdetails;
    private List<SyxqBean.ClassdetailsBean.DetailBean> detail;
    private int pos=0;

    SyxqAdapter adapter;
    private TextView cgxq_text;

    String reqid="";

    String ts_id;
    Bundle arguments;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        LayoutInflater inflaterm = (LayoutInflater) getContext().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View layout = inflaterm.inflate(R.layout.cgxq_list, container,false);
        listView = (CgListView) layout.findViewById(R.id.cgxq_list_view);
        cgxq_text = (TextView) layout.findViewById(R.id.cgxq_text);
        init();

        return layout;
    }

    private static final String TAG = "SyVpFragment";
    private void init() {
        classdetails=new ArrayList<>();
        detail=new ArrayList<>();

      arguments = getArguments();
        if (arguments != null) {
            mTitle = arguments.getString(BUNDLE_TITLE);
            classdetails = (List<SyxqBean.ClassdetailsBean>) arguments.getSerializable("detail");
            /*position :第几天*/
            pos = arguments.getInt("position");
            ts_id = arguments.getString("tsid");
            reqid=arguments.getString("reqid");
            if (detail!=null&&detail.size()>0){
                detail.clear();
            }
            if (classdetails.get(pos).getDetail()!=null&&classdetails.get(pos).getDetail().size()>0){
                detail.addAll(classdetails.get(pos).getDetail());
            }

            if (detail != null && detail.size() > 0) {
                if(reqid!=null&&!reqid.equals("")){
                    cgxq_text.setVisibility(View.GONE);
                }else {
                    cgxq_text.setVisibility(View.VISIBLE);
                    cgxq_text.setText("登陆后才可预约");
                }
                adapter = new SyxqAdapter(detail, getActivity(), this);
                listView.setAdapter(adapter);
            } else {
                cgxq_text.setVisibility(View.VISIBLE);
                if (reqid!=null&&!reqid.equals("")){
                    cgxq_text.setText("暂无课程");
                }else {
                    cgxq_text.setText("登陆后才可预约");
                }
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), KcDetailActivity.class);
                intent.putExtra("classid", detail.get(i).getClassid());
                startActivityForResult(intent, getActivity().RESULT_OK);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

    }

    @Override
    public void click(View v) {
        if (v.getTag(R.id.sy) != null) {
            int position = (int) v.getTag(R.id.sy);
            Intent intent = new Intent(getActivity(), SyddActivity.class);
            intent.putExtra("classid", detail.get(position).getClassid());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
        if(v.getTag(R.id.sy_no) != null){
                int position= (int) v.getTag(R.id.sy_no);
                Intent intent = new Intent(getActivity(), QdddActivity.class);
                intent.putExtra("classid", detail.get(position).getClassid());
                startActivityForResult(intent, getActivity().RESULT_OK);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }



    }

    public static SyVpFragment newInstance(Context context, String title,
                                           List<SyxqBean.ClassdetailsBean> classdetails, int position, String tsid,String reqid) {
        Bundle bundle = new Bundle();
        con = context;
        bundle.putSerializable("detail", (Serializable) classdetails);

        bundle.putInt("position", position);
        bundle.putString("tsid", tsid);
        bundle.putString("reqid",reqid);
        bundle.putString(BUNDLE_TITLE, title);
        SyVpFragment fragment = new SyVpFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


}
