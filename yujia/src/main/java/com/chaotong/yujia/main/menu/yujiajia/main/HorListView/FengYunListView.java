package com.chaotong.yujia.main.menu.yujiajia.main.HorListView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
 * Created by Administrator on 16-5-20.
 */
public class FengYunListView {

    RelativeLayout itmel;
    GridView gridView;
    List<Trainers> trainerses;

    public FengYunListView(List<Trainers> trainerses){
        this.trainerses = trainerses;
    }


    public void run(Activity activity,Context context){
        gridView = (GridView) activity.findViewById(R.id.fragment_yujiajia_grid);
       // gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        setGridView(activity,context);
    }
    int itemWidth;
    public void setGridView(final Activity activity, final Context context) {

        int size = trainerses.size();

        int length = 100;

        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 4) * density);
        itemWidth= (int) (length * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        gridView.setLayoutParams(params); // 重点
        gridView.setColumnWidth(itemWidth); // 重点
        gridView.setHorizontalSpacing(5); // 间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size); // 重点



        GridViewAdapter adapter = new GridViewAdapter(context,
                trainerses);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Intent intent = new Intent(context,JlxqActivity.class);
                String id = trainerses.get(i).getTs_id();
                intent.putExtra("id",id);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
            }
        });
    }

    public class GridViewAdapter extends BaseAdapter {
        Context context;
        List<Trainers> list;

        public GridViewAdapter(Context _context, List<Trainers> _list) {
            this.list = _list;
            this.context = _context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return trainerses.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if(convertView == null){
                holder = new ViewHolder();
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                convertView = layoutInflater.inflate(R.layout.f_yjj_coach_list_item, parent,false);
                holder.tvList  = (TextView) convertView.findViewById(R.id.tvList);
                holder.tvCode  = (TextView) convertView.findViewById(R.id.tvCode);
                holder.imageView  = (SimpleDraweeView) convertView.findViewById(R.id.ItemImage);
                holder.ratingBar = (RatingBar) convertView.findViewById(R.id.rating_bar);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }

            Trainers item = trainerses.get(position);
            holder.tvList.setText(item.getTs_type());

            String ss= StringEscapeUtils.unescapeJava(item.getTs_name());
            holder.tvCode.setText(ss);
            holder.ratingBar.setIsIndicator(true);
            holder.ratingBar.setRating(Float.valueOf(item.getSp_lv()));
           if (item.getTs_pic()!=null&&!item.getTs_pic().equals("")){
               holder.imageView.setImageURI(Uri.parse(item.getTs_pic()));
           }

            return convertView;
        }
    }
    public class ViewHolder{

        TextView tvList,tvCode;
        SimpleDraweeView imageView;
        RatingBar ratingBar;

    }
}
