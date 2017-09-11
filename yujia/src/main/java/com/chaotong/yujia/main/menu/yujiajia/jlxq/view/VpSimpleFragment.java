package com.chaotong.yujia.main.menu.yujiajia.jlxq.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.http.LoggingEventHandler;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.menu.yujiajia.cgxq.view.CgxqAdapter;
import com.chaotong.yujia.main.menu.yujiajia.kcxq.KcDetailActivity;
import com.chaotong.yujia.main.menu.yujiajia.qddd.QdddActivity;
import com.chaotong.yujia.main.menu.yujiajia.qddd.SyddActivity;
import com.chaotong.yujia.main.property.yujiajia.Classdetails;
import com.chaotong.yujia.main.property.yujiajia.Detail;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VpSimpleFragment extends Fragment implements CgxqAdapter.Callback {
    public static final String BUNDLE_TITLE = "title";
    private String mTitle = "DefaultValue";
    private CgListView listView;
    private static Context con;
    private List<Classdetails> classdetails;
    private List<Detail> detail;
    private int pos;
    private TextView cgxq_text;
    String ts_id;
    CgxqAdapter cgxqAdapter;
    private String activity_tag = "";
    String reqid="";
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LayoutInflater inflaterm = (LayoutInflater) getContext().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View layout = inflaterm.inflate(R.layout.cgxq_list,container,false);
        listView = (CgListView) layout.findViewById(R.id.cgxq_list_view);
        cgxq_text = (TextView) layout.findViewById(R.id.cgxq_text);
        classdetails=new ArrayList<>();
        detail=new ArrayList<>();

        listView.setFocusable(false);

        Bundle arguments = getArguments();
        if (arguments != null) {
            mTitle = arguments.getString(BUNDLE_TITLE);
            classdetails = (List<Classdetails>) arguments.getSerializable("detail");
            /*position :第几天*/
            pos = arguments.getInt("position");
            ts_id = arguments.getString("tsid");
            reqid=arguments.getString("reqid");
            activity_tag = arguments.getString("activity_tag");
            if (detail != null && detail.size() > 0) {
                detail.clear();
            }
            if (classdetails.get(pos) != null && classdetails.get(pos).getDetail() != null && classdetails.get(pos).getDetail().size() > 0) {
                detail.addAll(classdetails.get(pos).getDetail());
                if (!activity_tag.equals("")&&activity_tag.equals("buxianshi")){
                    for (int i=detail.size()-1;i>=0;i--){
                        if (detail.get(i).getSy_code().equals("1")){
                            detail.remove(i);
                        }
                    }
                }

            }

            Log.i("info","reqid:"+reqid);
            if (detail != null && detail.size() > 0) {

                if(reqid!=null) {
                    if (reqid.equals("") || reqid.equals("defaultname")) {
                        cgxq_text.setVisibility(View.VISIBLE);
                        cgxq_text.setText("登陆后才可预约");
                    } else {
                        cgxq_text.setVisibility(View.GONE);
                    }
                }
                if (!activity_tag.equals("")&&activity_tag.equals("buxianshi")){
                    for (int i=detail.size()-1;i>=0;i--){
                        if (detail.get(i).getSy_code().equals("1")){
                            detail.remove(i);
                        }
                    }
                }

                cgxqAdapter = new CgxqAdapter(detail, getActivity(), this);
                listView.setAdapter(cgxqAdapter);
            } else {
                cgxq_text.setVisibility(View.VISIBLE);

                if(reqid!=null) {
                    if (reqid.equals("") || reqid.equals("defaultname")) {
                        cgxq_text.setVisibility(View.VISIBLE);
                        cgxq_text.setText("登陆后才可预约");
                    } else {
                        cgxq_text.setText("暂无课程,换个日期看看");
                    }
                }

            }

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getActivity(), KcDetailActivity.class);
                        intent.putExtra("classid", detail.get(i).getClassid());
                        if (!activity_tag.equals("")&&detail.get(i).getSy_code().equals("1")){
                            intent.putExtra("activity_tag","buxianshi");
                        }else{
                            intent.putExtra("activity_tag","");
                        }

                        startActivityForResult(intent, getActivity().RESULT_OK);
                        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    }
                });
            }

        return layout;
    }

    @Override
    public void click(View v) {
        if (v.getTag(R.id.sy) != null) {
            int position = (int) v.getTag(R.id.sy);
            Intent intent = new Intent(getActivity(), SyddActivity.class);
            intent.putExtra("classid", detail.get(position).getClassid());
            startActivityForResult(intent, getActivity().RESULT_OK);
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

    public static VpSimpleFragment newInstance(Context context, String title,
                                               List<Classdetails> classdetails, int position, String tsid,String reqid,String activity_tag) {
        Bundle bundle = new Bundle();
        con = context;
        bundle.putSerializable("detail", (Serializable) classdetails);
        bundle.putInt("position", position);
        bundle.putString("tsid", tsid);
        bundle.putString(BUNDLE_TITLE, title);
        bundle.putString("reqid",reqid);
        bundle.putString("activity_tag",activity_tag);
        VpSimpleFragment fragment = new VpSimpleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


}
