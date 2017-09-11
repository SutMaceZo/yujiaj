package com.chaotong.yujia.main.menu.benggong.wo_de_yu_yue.view;

import android.content.Context;
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
import com.chaotong.yujia.main.property.yujiajia.Detail;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2016/8/4.
 */
public class CgxqAdapter2 extends BaseAdapter implements View.OnClickListener{

    private Context context;

    private List<Detail> detail;




    int type_1 = 0;//非私月课
    int type_2 = 1;//私约课

    Callback callback;
    public CgxqAdapter2(List<Detail> detail, Context context, Callback callback) {
        this.context = context;
        this.detail = detail;
        this.callback=callback;
    }

    @Override
    public void onClick(View view) {
        callback.click(view);
    }

    public interface Callback{
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
        return  detail.size();
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
                    convertView = LayoutInflater.from(context).inflate(R.layout.cgxq_viewpage_item, null);
                    viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name1);
                    viewHolder.startTime = (TextView) convertView.findViewById(R.id.tv_starttime);
                    viewHolder.position = (TextView) convertView.findViewById(R.id.tv_position);
                    viewHolder.yuyue = (Button) convertView.findViewById(R.id.bt_yuyue);
                    viewHolder.pic = (SimpleDraweeView) convertView.findViewById(R.id.pic);
                    convertView.setTag(viewHolder);
                    break;
                case 1:
                    viewHolder_1 = new ViewHolder_1();
                    convertView = LayoutInflater.from(context).inflate(R.layout.siyue_item, null);
                    viewHolder_1.siyue = (Button) convertView.findViewById(R.id.siyue_yuyue);
                    viewHolder_1.siyue_money = (TextView) convertView.findViewById(R.id.siyue_money);
                    viewHolder_1.siyue_time = (TextView) convertView.findViewById(R.id.siyue_time);
                    viewHolder_1.siyue_position = (TextView) convertView.findViewById(R.id.siyue_position);
                    viewHolder_1.siyue_max = (TextView) convertView.findViewById(R.id.siyue_max);
                    viewHolder_1.siyue_ck = (TextView) convertView.findViewById(R.id.siyue_ck);
                    viewHolder_1.siyue_min = (TextView) convertView.findViewById(R.id.siyue_min);
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
                    viewHolder.name.setText(detail.get(position).getClassname());
                    viewHolder.position.setText(detail.get(position).getStadiumname());
                    viewHolder.startTime.setText(detail.get(position).getTime());
                    viewHolder.yuyue.setText(detail.get(position).getClassstatu());
                    String code = "";
                    code = detail.get(position).getClassstatu_code();
                    if ( code.equals("0")) {
                        viewHolder.yuyue.setEnabled(true);
                        viewHolder.yuyue.setBackgroundColor(0xff7CFC00);
                        viewHolder.yuyue.setOnClickListener(this);
                        viewHolder.yuyue.setTag(R.id.sy_no,position);
                    }else {
                        viewHolder.yuyue.setEnabled(false);
                        viewHolder.yuyue.setBackgroundColor(Color.GRAY);
                    }

                    if (detail.get(position).getClasspic()!=null&&!detail.get(position).getClasspic().equals("")){
                        viewHolder.pic.setImageURI(Uri.parse(detail.get(position).getClasspic()));
                    }
                }
                break;
            case 1:
                if (detail != null) {
                    viewHolder_1.siyue_time.setText(detail.get(position).getTime());
                    if (detail.get(position).getStadiumname() == null) {
                        viewHolder_1.siyue_position.setText(detail.get(position).getClassname());
                    } else {
                        viewHolder_1.siyue_position.setText(detail.get(position).getStadiumname());
                    }

                    viewHolder_1.siyue_max.setText("最多人数：" + detail.get(position).getMaxpeople());
                    viewHolder_1.siyue_ck.setText("预约人数：" + detail.get(position).getHavepeople());
                    viewHolder_1.siyue_min.setText("最少人数：" + detail.get(position).getMinpeople());
                    viewHolder_1.siyue_money.setText(detail.get(position).getClassmoney());
                    viewHolder_1.siyue.setText(detail.get(position).getClassstatu());
                    String stu = detail.get(position).getClassstatu_code();
                    if (stu.equals("0")) {
                        viewHolder_1.siyue.setOnClickListener(this);
                        viewHolder_1.siyue.setTag(R.id.sy,position);
                    } else {
                        viewHolder_1.siyue.setEnabled(false);
                        viewHolder_1.siyue.setBackgroundColor(Color.GRAY);

                    }

                }
                break;
        }


        return convertView;
    }

    class ViewHolder {
        private TextView name, startTime, position;
        private Button yuyue;
        private SimpleDraweeView pic;
    }

    class ViewHolder_1 {
        private TextView siyue_money, siyue_time, siyue_position, siyue_max, siyue_ck, siyue_min;

        Button siyue;
    }
}
