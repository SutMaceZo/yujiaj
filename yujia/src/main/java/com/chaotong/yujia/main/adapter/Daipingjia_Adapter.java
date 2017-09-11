package com.chaotong.yujia.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.chaotong.yujia.main.entity.HuiYuanBean.DaiPingJiaBean;
import com.chaotong.yujia.main.menu.benggong.dai_ping_jia.PingjiaActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import org.apache.commons.lang.StringEscapeUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
public class Daipingjia_Adapter extends BaseAdapter {
    Context context;
    List<DaiPingJiaBean> list;
    LayoutInflater layoutInflater;
    String reqid;

    public Daipingjia_Adapter(Context context, List<DaiPingJiaBean> list,String reqid) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
        this.reqid=reqid;
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
            view = layoutInflater.inflate(R.layout.dpj_list_item, viewGroup,false);

            holder.pj_name = (TextView) view.findViewById(R.id.pj_name);
            holder.pj = (Button) view.findViewById(R.id.pj);
            holder.pj_changguan = (TextView) view.findViewById(R.id.pj_changguan);
            holder.pj_time = (TextView) view.findViewById(R.id.pj_time);
            holder.pj_class = (TextView) view.findViewById(R.id.pj_class);
            holder.jiaolian_image = (SimpleDraweeView) view.findViewById(R.id.jiaolian_image);
            view.setTag(holder);
        } else {
            holder = (viewHolder) view.getTag();
        }
        String face= StringEscapeUtils.unescapeJava(list.get(i).getTrainer());
        holder.pj_name.setText(face);
        holder.pj_class.setText(list.get(i).getClassname());
        holder.pj_time.setText(list.get(i).getDate());
        holder.pj_changguan.setText(list.get(i).getStadium());

        if (list.get(i).getTrainerpic()!=null&&!("").equals(list.get(i).getTrainerpic())){
            holder.jiaolian_image.setImageURI(Uri.parse(list.get(i).getTrainerpic()));
        }
        holder.pj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PingjiaActivity.class);
                intent.putExtra("classid", list.get(i).getClassid());
                intent.putExtra("trainerid", list.get(i).getTrainerid());
                intent.putExtra("reqid",reqid);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);

            }
        });
        return view;
    }

    class viewHolder {
        TextView pj_name, pj_class, pj_time, pj_changguan, year_mouth_day;

        Button pj;
        SimpleDraweeView jiaolian_image;

    }
}
