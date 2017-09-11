package com.chaotong.yujia.main.menu.benggong.wo_de_yu_yue.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.menu.yujiajia.cgxq.view.CgxqAdapter;
import com.chaotong.yujia.main.menu.yujiajia.jlxq.view.CgListView;
import com.chaotong.yujia.main.menu.yujiajia.kcxq.KcDetailActivity;
import com.chaotong.yujia.main.menu.yujiajia.qddd.QdddActivity;
import com.chaotong.yujia.main.menu.yujiajia.qddd.SyddActivity;
import com.chaotong.yujia.main.property.yujiajia.Classdetails;
import com.chaotong.yujia.main.property.yujiajia.Detail;
import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.chaotong.yujia.main.utils.viewUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VpSimpleFragment2 extends Fragment implements CgxqAdapter2.Callback {
    public static final String BUNDLE_TITLE = "title";
    private String mTitle = "DefaultValue";
    private ListView listView;
    private static Context con;
    private List<Classdetails> classdetails;
    private List<Detail> detail;
    private int pos;


    private TextView cgxq_text;

    String ts_id;
    String reqid;
    String hy_id;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LayoutInflater inflaterm = (LayoutInflater) getContext().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        View layout = inflaterm.inflate(R.layout.cgxq_list_2, null);
        listView = (ListView) layout.findViewById(R.id.cgxq_list_view_2);
        cgxq_text = (TextView) layout.findViewById(R.id.cgxq_text);
       detail=new ArrayList<>();
        classdetails=new ArrayList<>();
        Bundle arguments = getArguments();
        if (arguments != null) {
            mTitle = arguments.getString(BUNDLE_TITLE);

            classdetails = (List<Classdetails>) arguments.getSerializable("detail");
            /*position :第几天*/
            pos = arguments.getInt("position");
            ts_id = arguments.getString("tsid");
            if (detail!=null&&detail.size()>0){
                detail.clear();
            }
            if (classdetails.get(pos) != null && classdetails.get(pos).getDetail() != null && classdetails.get(pos).getDetail().size() > 0) {
                detail.addAll(classdetails.get(pos).getDetail());
            }
            if (detail != null && detail.size() > 0) {
                cgxq_text.setVisibility(View.GONE);
                CgxqAdapter2 cgxqAdapter = new CgxqAdapter2(detail, getActivity(), this);
                listView.setAdapter(cgxqAdapter);
            }else {
                cgxq_text.setVisibility(View.VISIBLE);
            }
            hy_id = arguments.getString("hy_id");
            reqid = arguments.getString("reqid");
        }


      //  viewUtils.setListViewHeightBasedOnChildren1(listView);


        return layout;
    }
    UrlPath urlPath = UrlPath.getUrlPath();
    private String yyUrl = urlPath.getUrl() + "GzYy_yyServlet?";
    Dialog dialog;
    @Override
    public void click(final View v) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        if (v.getTag(R.id.sy_no) != null) {
            final int position = (int) v.getTag(R.id.sy_no);
            View dialogView = inflater.inflate(R.layout.gzyy_dialog, null);
             dialog = new AlertDialog.Builder(getActivity())
                    .setView(dialogView)
                    .show();
            TextView kc_time = (TextView) dialogView.findViewById(R.id.kc_time);
            kc_time.setText(detail.get(position).getTime());

            TextView kc_type = (TextView) dialogView.findViewById(R.id.kc_type);
            kc_type.setText(detail.get(position).getClassname());

            Button cancle = (Button) dialogView.findViewById(R.id.cancle);
            Button sure = (Button) dialogView.findViewById(R.id.sure);
            cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    String classid = detail.get(position).getClassid();
                    String url = yyUrl + "reqid=" + reqid + "&classid=" + classid + "&hy_id=" + hy_id+"&app_type=android";
                    new ThreadUtils_no(getActivity(), url, new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            String json = msg.obj.toString();
                            Log.i("yuyue--------",json);
                            if (!json.equals("")&&json!=null) {
                                JSONObject object = null;
                                try {
                                    object = new JSONObject(json);
                                    dialog.dismiss();
                                    String code = object.getString("code");
                                    String message = object.getString("msg");
                                    String yy_code = object.getString("yy_code");
                                    String yy_message = object.getString("yy_msg");
                                    if (("1").equals(code)) {
                                        Toast.makeText(getActivity(), yy_message, Toast.LENGTH_SHORT).show();
                                        Button bt = (Button) v;
                                        if (yy_code.equals("1")) {
                                            bt.setEnabled(false);
                                            bt.setBackgroundColor(Color.GRAY);
                                            bt.setText("已预约");
                                        } else {
                                            bt.setEnabled(true);
                                            //bt.setText(yy_message);
                                            bt.setBackgroundColor(Color.GRAY);
                                        }
                                    } else {
                                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    //e.printStackTrace();
                                }
                            }
                            super.handleMessage(msg);
                        }
                    }).start();
                }
            });
        }
        //馆主或馆员不能帮会员预约私教课程

//        if (v.getId() == R.id.siyue_yuyue){
//            Toast.makeText(getActivity(),"点击私约了",Toast.LENGTH_SHORT).show();
//        }

    }
    public static int GetNetype(Context context)
    {
        int netType = -1;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo==null)
        {
            return netType;
        }
        int nType = networkInfo.getType();
        if(nType==ConnectivityManager.TYPE_MOBILE)
        {
            if(networkInfo.getExtraInfo().toLowerCase().equals("cmnet"))
            {
                netType = 3;
            }
            else
            {
                netType = 2;
            }
        }
        else if(nType==ConnectivityManager.TYPE_WIFI)
        {
            netType = 1;
        }
        return netType;
    }
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    public static VpSimpleFragment2 newInstance(Context context, String title,
                                                List<Classdetails> classdetails,
                                                int position, String reqid, String hy_id) {
        Bundle bundle = new Bundle();
        con = context;
        bundle.putSerializable("detail", (Serializable) classdetails);
        bundle.putInt("position", position);
        bundle.putString("reqid", reqid);
        bundle.putString("hy_id", hy_id);
        bundle.putString(BUNDLE_TITLE, title);

        VpSimpleFragment2 fragment = new VpSimpleFragment2();
        fragment.setArguments(bundle);
        return fragment;
    }


}
