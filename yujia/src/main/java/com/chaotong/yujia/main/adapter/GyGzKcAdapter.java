package com.chaotong.yujia.main.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.entity.JiaoLianBean.WdkcBean;
import com.facebook.drawee.view.SimpleDraweeView;
import org.apache.commons.lang.StringEscapeUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/8/19 0019.
 */
public class GyGzKcAdapter extends BaseAdapter implements View.OnClickListener {

    Context context;
    List<WdkcBean> list;
    LayoutInflater layoutInflater;
    String reqid;
    Callback callback;



    public GyGzKcAdapter(List<WdkcBean> list, Context context, String reqid, Callback callback) {
        this.list = list;
        this.context = context;
        this.reqid = reqid;
        layoutInflater = LayoutInflater.from(context);
        this.callback = callback;
    }

    @Override
    public void onClick(View view) {
        callback.click(view);
    }

    public interface Callback {
        public void click(View view);
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
            view = layoutInflater.inflate(R.layout.gy_gz_kc_item, viewGroup,false);

            holder.name_tv = (TextView) view.findViewById(R.id.pj_name);
            holder.zxingbutton = (Button) view.findViewById(R.id.zxing);
            holder.changguan_tv = (TextView) view.findViewById(R.id.pj_changguan);
            holder.time_tv = (TextView) view.findViewById(R.id.pj_time);
            holder.class_tv = (TextView) view.findViewById(R.id.pj_class);
          //  holder.ct_max= (TextView) view.findViewById(R.id.ct_max);
            //holder.ct_min= (TextView) view.findViewById(R.id.ct_min);
            holder.ct_num= (TextView) view.findViewById(R.id.ct_finish);
            holder.jiaolian_image = (SimpleDraweeView) view.findViewById(R.id.jiaolian_image);
            view.setTag(holder);
        } else {
            holder = (viewHolder) view.getTag();
        }
        if (list.get(i)!=null){
            holder.time_tv.setText(list.get(i).getDate() + " " + list.get(i).getTime());
            String s = StringEscapeUtils.unescapeJava(list.get(i).getTrainername());
            holder.name_tv.setText(s);
            holder.changguan_tv.setText(list.get(i).getStadiumname());
            holder.class_tv.setText(list.get(i).getClassname());

           // holder.ct_max.setText("最大人数："+list.get(i).getCt_max());
            //holder.ct_min.setText("最少人数："+list.get(i).getCt_min());
            holder.ct_num.setText("已约人数："+list.get(i).getCt_num());
            if (list.get(i).getPic()!=null&&!list.get(i).getPic().equals("")){
                holder.jiaolian_image.setImageURI(Uri.parse(list.get(i).getPic()));
            }

            holder.zxingbutton.setOnClickListener(this);
            holder.zxingbutton.setTag(i);
        }

        return view;
    }

    class viewHolder {
        TextView name_tv, class_tv, time_tv, changguan_tv,ct_max,ct_min,ct_num;
        Button zxingbutton;

        SimpleDraweeView jiaolian_image;

    }
}
