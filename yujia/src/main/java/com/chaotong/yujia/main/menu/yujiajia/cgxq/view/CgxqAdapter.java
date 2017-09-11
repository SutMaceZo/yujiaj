package com.chaotong.yujia.main.menu.yujiajia.cgxq.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.menu.yujiajia.qddd.QdddActivity;
import com.chaotong.yujia.main.menu.yujiajia.qddd.SyddActivity;
import com.chaotong.yujia.main.property.yujiajia.Cgxq;
import com.chaotong.yujia.main.property.yujiajia.Cgxq_Details;
import com.chaotong.yujia.main.property.yujiajia.Classdetails;
import com.chaotong.yujia.main.property.yujiajia.Detail;
import com.facebook.drawee.view.SimpleDraweeView;

import org.apache.commons.lang.StringEscapeUtils;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/4.
 */
public class CgxqAdapter extends BaseAdapter implements View.OnClickListener {

    private Context context;

    private List<Detail> detail;

    int type_1 = 0;//非私月课
    int type_2 = 1;//私约课
    String id;
    String codeid;
    Callback callback;

    public CgxqAdapter(List<Detail> detail, Context context, Callback callback) {
        this.context = context;
        this.detail = detail;
        this.callback = callback;
    }

    @Override
    public void onClick(View view) {
        callback.click(view);
    }

    public interface Callback {
        public void click(View v);
    }


    @Override
    public int getItemViewType(int position) {
        int i = position % detail.size();
        if (("0").equals(detail.get(i).getSy_code())) {
            return type_1;
        } else {
            return type_2;
        }

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return detail.size();
    }

    @Override
    public Object getItem(int position) {
        return detail.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        ViewHolder_1 viewHolder_1 = null;
        int x = getItemViewType(position);
        if (convertView == null) {
            switch (x) {
                case 0:
                    viewHolder = new ViewHolder();
                    convertView = LayoutInflater.from(context).inflate(R.layout.sy_item, parent, false);

                    viewHolder.sy_text1 = (TextView) convertView.findViewById(R.id.sy_text1);
                    viewHolder.sy_text2 = (TextView) convertView.findViewById(R.id.sy_text2);
                    viewHolder.sy_text3 = (TextView) convertView.findViewById(R.id.sy_text3);
                    viewHolder.sy_text4 = (TextView) convertView.findViewById(R.id.sy_text4);
                    viewHolder.sy_text8= (TextView) convertView.findViewById(R.id.sy_text8);
                    viewHolder.sy_text9= (TextView) convertView.findViewById(R.id.sy_text9);
                    viewHolder.sy_lin1= (LinearLayout) convertView.findViewById(R.id.sy_lin1);
                    viewHolder.sy_image1 = (SimpleDraweeView) convertView.findViewById(R.id.sy_image1);
                    viewHolder.sy_bt1= (Button) convertView.findViewById(R.id.sy_bt1);
                    convertView.setTag(viewHolder);
                    break;
                case 1:
                    viewHolder_1 = new ViewHolder_1();
                    convertView = LayoutInflater.from(context).inflate(R.layout.sy_item, parent, false);

                    viewHolder_1.sy_text1 = (TextView) convertView.findViewById(R.id.sy_text1);
                    viewHolder_1.sy_text2 = (TextView) convertView.findViewById(R.id.sy_text2);
                    viewHolder_1.sy_text3 = (TextView) convertView.findViewById(R.id.sy_text4);
                    viewHolder_1.sy_text4 = (TextView) convertView.findViewById(R.id.sy_text5);
                    viewHolder_1.sy_text5 = (TextView) convertView.findViewById(R.id.sy_text6);
                    viewHolder_1.sy_text6 = (TextView) convertView.findViewById(R.id.sy_text7);
                    viewHolder_1.sy_text7 = (TextView) convertView.findViewById(R.id.sy_text8);
                    viewHolder_1.sy_image1 = (SimpleDraweeView) convertView.findViewById(R.id.sy_image1);
                    viewHolder_1.sy_bt1= (Button) convertView.findViewById(R.id.sy_bt1);
                    convertView.setTag(viewHolder_1);
                    break;
            }
        } else {
            switch (x) {
                case 0:
                    viewHolder = (ViewHolder) convertView.getTag();
                    break;
                case 1:
                    viewHolder_1 = (ViewHolder_1) convertView.getTag();
                    break;
            }

        }
        switch (x) {
            case 0:
                if (detail != null) {
                    viewHolder.sy_lin1.setVisibility(View.INVISIBLE);
                    viewHolder.sy_text3.setVisibility(View.GONE);
                    viewHolder.sy_text4.setVisibility(View.GONE);
                    viewHolder.sy_text1.setText(detail.get(position).getClassname());
                    viewHolder.sy_text9.setText("课程");
                    if (detail.get(position).getTrainername() != null) {
                        String tname = StringEscapeUtils.unescapeJava(detail.get(position).getTrainername());
                        viewHolder.sy_text8.setText(tname);
                    } else if (detail.get(position).getStadiumname() != null) {
                        viewHolder.sy_text8.setText(detail.get(position).getStadiumname());
                    }
                    viewHolder.sy_text2.setText(detail.get(position).getTime());
                    viewHolder.sy_bt1.setText(detail.get(position).getClassstatu());
                    String code = "";
                    code = detail.get(position).getClassstatu_code();
                    Log.i("info", "code:" + code);
                    if (code.equals("0")) {
                        viewHolder.sy_bt1.setEnabled(true);
                        viewHolder.sy_bt1.setOnClickListener(this);
                        viewHolder.sy_bt1.setTag(R.id.sy_no, position);
                        viewHolder.sy_bt1.setBackgroundResource(R.drawable.text_bg2);
                    } else {
                        viewHolder.sy_bt1.setEnabled(false);
                        viewHolder.sy_bt1.setBackgroundResource(R.drawable.text_bg4);
                    }
                    //  org.xutils.x.image().bind(viewHolder.pic, detail.get(position).getClasspic());
                    if (detail.get(position).getClasspic() != null && !(detail.get(position).getClasspic()).equals(""))
                        viewHolder.sy_image1.setImageURI(Uri.parse(detail.get(position).getClasspic()));
                }
                break;
            case 1:
                if (detail != null) {
                    viewHolder_1.sy_text2.setText(detail.get(position).getTime());
                    viewHolder_1.sy_text1.setText(detail.get(position).getClassname());
                    viewHolder_1.sy_text3.setText(detail.get(position).getClassmoney());

                    if (detail.get(position).getTrainername() != null) {
                        String tname = StringEscapeUtils.unescapeJava(detail.get(position).getTrainername());
                        viewHolder_1.sy_text7.setText(tname);
                    } else if (detail.get(position).getStadiumname() != null) {
                        viewHolder_1.sy_text7.setText(detail.get(position).getStadiumname());
                    }


                    viewHolder_1.sy_text4.setText("已约人数："+detail.get(position).getHavepeople());
                    viewHolder_1.sy_text5.setText("最大人数："+detail.get(position).getMaxpeople());
                    viewHolder_1.sy_text6.setText("最少人数："+detail.get(position).getMinpeople());

                    viewHolder_1.sy_bt1.setText(detail.get(position).getClassstatu());
                    String stu = detail.get(position).getClassstatu_code();
                    if (stu.equals("0")) {
                        viewHolder_1.sy_bt1.setEnabled(true);
                        viewHolder_1.sy_bt1.setOnClickListener(this);
                        viewHolder_1.sy_bt1.setTag(R.id.sy, position);
                        viewHolder_1.sy_bt1.setBackgroundResource(R.drawable.text_bg2);
                    } else {
                        viewHolder_1.sy_bt1.setEnabled(false);
                        viewHolder_1.sy_bt1.setBackgroundResource(R.drawable.text_bg4);
                    }
                    if (detail.get(position).getClasspic()!=null&&!detail.get(position).getClasspic().equals("")){
                        viewHolder_1.sy_image1.setImageURI(Uri.parse(detail.get(position).getClasspic()));
                    }
                }
                break;
        }


        return convertView;
    }

    class ViewHolder {
        private TextView sy_text1, sy_text2, sy_text3, sy_text4, sy_text8,sy_text9;
        Button sy_bt1;
        SimpleDraweeView sy_image1;
        LinearLayout sy_lin1;
    }

    class ViewHolder_1 {


        private TextView sy_text1, sy_text2, sy_text3, sy_text4, sy_text5, sy_text6, sy_text7;
        Button sy_bt1;
        SimpleDraweeView sy_image1;

    }
}
