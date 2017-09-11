package com.chaotong.yujia.main.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.entity.CoachBean.Trainers;
import com.chaotong.yujia.main.entity.Order.OrderJl;
import com.facebook.drawee.view.SimpleDraweeView;

import org.apache.commons.lang.StringEscapeUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/8/10 0010.
 */
public class OrderJlAdapterAdapter extends BaseAdapter{
    Context context;
    List<OrderJl.TrainersBean> list;
    LayoutInflater inflater;
    public OrderJlAdapterAdapter(Context context, List<OrderJl.TrainersBean> list) {
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        final viewHolder holder;
        if(view==null){
            view=inflater.inflate(R.layout.yuejiaolian_item,viewGroup,false);
           holder=new viewHolder();
            holder.trainer_name= (TextView) view.findViewById(R.id.trainer_name);
            holder.trainer_sex= (ImageView) view.findViewById(R.id.sex);
            holder.trainer_grade= (TextView) view.findViewById(R.id.trainer_grade);
            holder.trainer_classtype= (TextView) view.findViewById(R.id.trainer_classtype);
            holder.trainer_image= (SimpleDraweeView) view.findViewById(R.id.trainer_image );
            holder.trainer_rat= (RatingBar) view.findViewById(R.id.rating_bar);
            view.setTag(holder);
        }else {
            holder= (viewHolder) view.getTag();
        }
        if (list.get(i).getTs_name()!=null){
            String name= StringEscapeUtils.unescapeJava(list.get(i).getTs_name());
            holder.trainer_name.setText(name);
        }
        holder.trainer_classtype.setText(list.get(i).getTs_type());
        holder.trainer_grade.setText(list.get(i).getTs_lv());
        holder.trainer_rat.setRating(Float.parseFloat(list.get(i).getTs_lv()));
        if (list.get(i).getSex()!=null){
            if (list.get(i).getSex().equals("女")){
                holder.trainer_sex.setBackgroundResource(R.mipmap.female);
            }else if(list.get(i).getSex().equals("男")){
                holder.trainer_sex.setBackgroundResource(R.mipmap.male);
            }
        }


        if (list.get(i).getTs_pic()!=null){
            holder.trainer_image.setImageURI(Uri.parse(list.get(i).getTs_pic()));
        }

        return view;
    }

    class viewHolder{
        SimpleDraweeView trainer_image;
        TextView trainer_name;
        ImageView trainer_sex;
        TextView trainer_classtype;
        TextView trainer_grade;
        RatingBar trainer_rat;
    }
}
