package com.chaotong.yujia.main.menu.xiulian.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.entity.HuiYuanBean.DaiPingJiaBean;
import com.chaotong.yujia.main.entity.Order.OrderKc;
import com.chaotong.yujia.main.menu.benggong.dai_ping_jia.PingjiaActivity;
import com.chaotong.yujia.main.menu.yujiajia.qddd.QdddActivity;
import com.chaotong.yujia.main.utils.NoDoubleClickListener;
import com.facebook.drawee.view.SimpleDraweeView;

import org.apache.commons.lang.StringEscapeUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
public class KcAdapter extends BaseAdapter {
    Context context;
    List<OrderKc.ClasslistBean> list;
    LayoutInflater layoutInflater;

    public KcAdapter(Context context, List<OrderKc.ClasslistBean> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final viewHolder holder;
        if (view == null) {
            holder = new viewHolder();
            view = layoutInflater.inflate(R.layout.order_kc_list_item, viewGroup,false);
            holder.ct_cg= (TextView) view.findViewById(R.id.ct_changguan);
            holder.ct_name= (TextView) view.findViewById(R.id.ct_name);
            holder.ct_time= (TextView) view.findViewById(R.id.ct_time);
            holder.ct_pic= (SimpleDraweeView) view.findViewById(R.id.ct_pic);
            holder.yy= (Button) view.findViewById(R.id.yy);
            view.setTag(holder);
        } else {
            holder = (viewHolder) view.getTag();
        }
        String face=StringEscapeUtils.unescapeJava(list.get(i).getCt_name());
        holder.ct_name.setText(face);

//        if (list.get(i).getCt_pic()!=null){
//            holder.ct_pic.setImageURI(Uri.parse(list.get(i).getCt_pic()));
//        }

        if (list.get(i).getClasspic()!=null){
            holder.ct_pic.setImageURI(Uri.parse(list.get(i).getClasspic()));
        }
        holder.ct_cg.setText(list.get(i).getSp_name());
        holder.ct_time.setText(list.get(i).getCt_time());
        String code_tag=list.get(i).getClassstatu_code();
        String code = list.get(i).getSy_code();
        holder.yy.setText(list.get(i).getClassstatu());
        if (code!=null){
            if (code.equals("0")){
                if (code_tag.equals("0")){
                    holder.yy.setEnabled(true);
                    holder.yy.setBackgroundResource(R.drawable.text_bg2);
                }else{
                    holder.yy.setEnabled(false);
                    holder.yy.setBackgroundResource(R.drawable.text_bg4);
                }

                holder.yy.setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        Intent intent = new Intent(context, QdddActivity.class);
                        intent.putExtra("classid", list.get(i).getCt_id());
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }
                });

            }else {
                if (code_tag.equals("0")){
                    holder.yy.setEnabled(true);
                    holder.yy.setBackgroundResource(R.drawable.text_bg2);
                }else{
                    holder.yy.setEnabled(false);
                    holder.yy.setBackgroundResource(R.drawable.text_bg4);
                }
            }
        }

        return view;
    }

    class viewHolder {
        TextView ct_name,ct_time,ct_cg;
        Button yy;

        SimpleDraweeView ct_pic;

    }
}
