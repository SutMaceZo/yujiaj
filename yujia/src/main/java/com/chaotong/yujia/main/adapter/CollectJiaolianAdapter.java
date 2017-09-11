package com.chaotong.yujia.main.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.entity.collect.CollectJiaolian;
import com.chaotong.yujia.main.menu.benggong.wo_de_shou_chang.ScBean;
import com.facebook.drawee.view.SimpleDraweeView;

import org.apache.commons.lang.StringEscapeUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class CollectJiaolianAdapter extends BaseAdapter {
    Context context;
    private List<ScBean.WdscjlBean> list;
    LayoutInflater inflater;

    public CollectJiaolianAdapter(Context context, List<ScBean.WdscjlBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
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
        if (list.get(i).getTrainername()!=null){
            String name= StringEscapeUtils.unescapeJava(list.get(i).getTrainername());
            holder.trainer_name.setText(name);
        }
        holder.trainer_classtype.setText(list.get(i).getType());
        holder.trainer_grade.setText(list.get(i).getLv());
        holder.trainer_rat.setRating(Float.parseFloat(list.get(i).getLv()));
       /* if (list.get(i).get()!=null){
            if (list.get(i).getSex().equals("女")){
                holder.trainer_sex.setBackgroundResource(R.mipmap.female);
            }else if(list.get(i).getSex().equals("男")){
                holder.trainer_sex.setBackgroundResource(R.mipmap.male);
            }
        }*/
        if (list.get(i).getPic()!=null){
            holder.trainer_image.setImageURI(Uri.parse(list.get(i).getPic()));
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
