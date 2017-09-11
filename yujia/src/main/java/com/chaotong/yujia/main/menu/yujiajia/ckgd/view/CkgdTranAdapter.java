package com.chaotong.yujia.main.menu.yujiajia.ckgd.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.menu.yujiajia.jlxq.JlxqActivity;
import com.chaotong.yujia.main.property.yujiajia.Trainers;
import com.facebook.drawee.view.SimpleDraweeView;

import org.apache.commons.lang.StringEscapeUtils;

import java.util.List;

/**
 * 查看更多教练信息
 */
public class CkgdTranAdapter extends BaseAdapter {

    private Context context;
    private List<Trainers> trainers;
    LayoutInflater inflater;

    public CkgdTranAdapter(Context context,List<Trainers> trainers){
        this.context = context;
        this.trainers = trainers;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return trainers.size();
    }

    @Override
    public Object getItem(int position) {
        return trainers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        viewHolder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.yuejiaolian_item,parent,false);
            holder=new viewHolder();
            holder.trainer_name= (TextView) convertView.findViewById(R.id.trainer_name);
            holder.trainer_sex= (ImageView) convertView.findViewById(R.id.sex);
            holder.trainer_grade= (TextView) convertView.findViewById(R.id.trainer_grade);
            holder.trainer_classtype= (TextView) convertView.findViewById(R.id.trainer_classtype);
            holder.trainer_image= (SimpleDraweeView) convertView.findViewById(R.id.trainer_image );
            holder.trainer_rat= (RatingBar) convertView.findViewById(R.id.rating_bar);
            convertView.setTag(holder);
        }else {
            holder= (viewHolder) convertView.getTag();
        }
        if (trainers.get(i).getTs_name()!=null){
            String name= StringEscapeUtils.unescapeJava(trainers.get(i).getTs_name());
            holder.trainer_name.setText(name);
        }
        holder.trainer_classtype.setText(trainers.get(i).getTs_type());
        holder.trainer_grade.setText(trainers.get(i).getTs_lv());
        holder.trainer_rat.setRating(Float.parseFloat(trainers.get(i).getTs_lv()));
        if (trainers.get(i).getSex()!=null){
            if (trainers.get(i).getSex().equals("女")){
                holder.trainer_sex.setBackgroundResource(R.mipmap.female);
            }else if(trainers.get(i).getSex().equals("男")){
                holder.trainer_sex.setBackgroundResource(R.mipmap.male);
            }
        }

        if (trainers.get(i).getTs_pic()!=null){
            holder.trainer_image.setImageURI(Uri.parse(trainers.get(i).getTs_pic()));
        }
        return convertView;
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
