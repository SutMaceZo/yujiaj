package com.chaotong.yujia.main.menu.yujiajia.cgxq.view;

import android.content.Context;
import android.content.Intent;
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
import com.chaotong.yujia.main.menu.yujiajia.jlxq.view.CgListView;
import com.chaotong.yujia.main.menu.yujiajia.kcxq.KcDetailActivity;
import com.chaotong.yujia.main.menu.yujiajia.qddd.QdddActivity;
import com.chaotong.yujia.main.menu.yujiajia.qddd.SyddActivity;
import com.chaotong.yujia.main.property.yujiajia.CgxqClassdetails;
import com.chaotong.yujia.main.property.yujiajia.Cgxq_Details;
import com.chaotong.yujia.main.property.yujiajia.Detail;
import com.chaotong.yujia.main.utils.viewUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VpSimpleFragment extends Fragment implements CgxqAdapter.Callback {
    public static final String BUNDLE_TITLE = "title";
    private String mTitle = "DefaultValue";
    private CgListView listView;
    private TextView cgxq_text;
    private static List<CgxqClassdetails> detailses;
    private static int pos;
    private CgxqAdapter cgxqAdapter;
    private List<Detail> cgxq_detailses;
    private static Context con;
    String reqid = "";

    private String cg_id;
    private String activity_tag = "";
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final LayoutInflater inflaterm = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflaterm.inflate(R.layout.cgxq_list, null);
        listView = (CgListView) layout.findViewById(R.id.cgxq_list_view);
        cgxq_text = (TextView) layout.findViewById(R.id.cgxq_text);
        detailses = new ArrayList<CgxqClassdetails>();
        cgxq_detailses = new ArrayList<Detail>();

        listView.setFocusable(false);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mTitle = arguments.getString(BUNDLE_TITLE);
            detailses = (List<CgxqClassdetails>) arguments.getSerializable("detailses");
            pos = arguments.getInt("position");
            cg_id = arguments.getString("cgid");
            reqid = arguments.getString("reqid");
            activity_tag = arguments.getString("activity_tag");
                if (cgxq_detailses != null && cgxq_detailses.size() > 0) {
                    cgxq_detailses.clear();
                }
                if (detailses.get(pos) != null && detailses.get(pos).getDetail() != null && detailses.get(pos).getDetail().size() > 0) {
                    cgxq_detailses.addAll(detailses.get(pos).getDetail());
                }
            Log.i("info","reqid:"+reqid);
                if (cgxq_detailses != null && cgxq_detailses.size() > 0) {

                    if(reqid!=null&&!reqid.equals("")){
                        cgxq_text.setVisibility(View.GONE);
                    }else {
                        cgxq_text.setVisibility(View.VISIBLE);
                        cgxq_text.setText("登陆后才可预约");
                    }
                    if (!activity_tag.equals("")&&activity_tag.equals("buxianshi")){
                        for (int i=cgxq_detailses.size()-1;i>=0;i--){
                            if (cgxq_detailses.get(i).getSy_code().equals("1")){
                                cgxq_detailses.remove(i);
                            }
                        }
                    }

                        cgxqAdapter = new CgxqAdapter(cgxq_detailses, getActivity(), this);
                        listView.setAdapter(cgxqAdapter);
                } else {
                    cgxq_text.setVisibility(View.VISIBLE);

                    if (reqid!=null&&!reqid.equals("")){
                        cgxq_text.setText("暂无课程,换个日期看看");
                    }else {
                        cgxq_text.setText("登陆后才可预约");
                    }

                }


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getActivity(), KcDetailActivity.class);
                        intent.putExtra("classid", cgxq_detailses.get(i).getClassid());
                        if (cgxq_detailses.get(i).getSy_code().equals("1")&&activity_tag.equals("buxianshi")){
                            intent.putExtra("activity_tag","si");
                        }else{
                            intent.putExtra("activity_tag","");
                        }
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                    }
                });
            }

        return layout;
    }

    public static VpSimpleFragment newInstance(Context context, String title,
                                               List<CgxqClassdetails> cgxqClassdetailsList,
                                               int position, String id, String reqid,String activity_tag) {
        Bundle bundle = new Bundle();
        con = context;
        bundle.putString(BUNDLE_TITLE, title);
        bundle.putSerializable("detailses", (Serializable) cgxqClassdetailsList);
        bundle.putInt("position", position);
        bundle.putString("cgid", id);
        bundle.putString("reqid", reqid);
        bundle.putString("activity_tag", activity_tag);
        VpSimpleFragment fragment = new VpSimpleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void click(View v) {
        if (v.getTag(R.id.sy) != null) {
            int position = (int) v.getTag(R.id.sy);
            Intent intent = new Intent(getActivity(), SyddActivity.class);
            intent.putExtra("classid", cgxq_detailses.get(position).getClassid());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
        if (v.getTag(R.id.sy_no) != null) {
            int position = (int) v.getTag(R.id.sy_no);
            Intent intent = new Intent(getActivity(), QdddActivity.class);
            intent.putExtra("classid", cgxq_detailses.get(position).getClassid());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }


    }

}
