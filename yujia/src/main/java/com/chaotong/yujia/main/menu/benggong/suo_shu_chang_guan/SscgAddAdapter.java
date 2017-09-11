package com.chaotong.yujia.main.menu.benggong.suo_shu_chang_guan;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.entity.HuiYuanBean.SsAddBean;
import com.chaotong.yujia.main.entity.HuiYuanBean.SscgBean;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.thread.ThreadUtils;
import com.chaotong.yujia.main.thread.ThreadUtils_no;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/2/13 0013.
 */
public class SscgAddAdapter extends BaseAdapter {
    Context context;
    UrlPath urlPath = UrlPath.getUrlPath();
    List<SsAddBean.ListBean> mList;
    LayoutInflater inflater;
    String reqid;
    String Sdurl= urlPath.getUrl()+"AddCardServlet";
    int pos;

    public SscgAddAdapter(Context context, List<SsAddBean.ListBean> mList,String reqid) {
        this.context = context;
        this.mList = mList;
        this.reqid=reqid;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        viewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.hngguan_listitem, viewGroup, false);
            holder = new viewHolder();
            holder.textView = (TextView) view.findViewById(R.id.jiaolian_name);
            holder.textView1= (TextView) view.findViewById(R.id.add_cg);
            view.setTag(holder);

        } else {
            holder = (viewHolder) view.getTag();
        }
        holder.textView.setText(mList.get(i).getSp_name());
        holder.textView1.setVisibility(View.GONE);
       // holder.textView1.setText("加入");
        pos=i;

        holder.textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater=LayoutInflater.from(context);
                View editView=inflater.inflate(R.layout.dialog_edit,null);
                final EditText edit= (EditText) editView.findViewById(R.id.dialog_edit);
                edit.setHint("请输入实体卡号:");

                new AlertDialog.Builder(context).setTitle("卡号：")
                        .setView(editView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int a) {


                              /*  String content="";
                                final JSONObject object=new JSONObject();
                                try {
                                    object.put("reqid",reqid);
                                    object.put("card_id",edit.getText().toString());
                                    object.put("sp_id",mList.get(i).getSp_id());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                content=String.valueOf(object);

                                new ThreadUtils(context,Sdurl,content,new Handler(){
                                    @Override
                                    public void handleMessage(Message msg) {
                                        String json=msg.obj.toString();
                                        try {
                                            JSONObject object1=new JSONObject(json);
                                            String code=object1.getString("code");
                                            String message=object1.getString("msg");
                                            if (code!=null&&code.equals("1")){
                                                ToastUtils.showToast(context,message);
                                                Activity activity= (Activity) context;
                                                activity.finish();

                                            }else {
                                                ToastUtils.showToast(context,message);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }).start();*/
                            }
                        })
                        .setNegativeButton("取消", null).show();
            }
        });

        return view;
    }

    class viewHolder {
        TextView textView,textView1;
    }
}
