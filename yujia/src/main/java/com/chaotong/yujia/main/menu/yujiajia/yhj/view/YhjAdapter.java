package com.chaotong.yujia.main.menu.yujiajia.yhj.view;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.property.yujiajia.Yhj;
import com.chaotong.yujia.main.property.yujiajia.YhjPro;
import com.chaotong.yujia.main.utils.viewUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 */
public class YhjAdapter extends BaseAdapter implements View.OnClickListener {

    private calledBack callBack;
    private Context context;
    private List<YhjPro> url;
  //  private int tag;

    public YhjAdapter(Context context, List<YhjPro> url, calledBack callBack) {
        this.context = context;
        this.url = url;
        this.callBack = callBack;

    }

    public interface calledBack {
        public void clickOk(View v);
    }

    @Override
    public void onClick(View v) {
        callBack.clickOk(v);
    }


    @Override
    public int getCount() {
        return url.size();
    }

    @Override
    public Object getItem(int position) {
        return url.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        private SimpleDraweeView pic;
        private TextView yhj_sy,yhj_time,yhj__sm,yhj_name;
        Button yhj_lq;
        RelativeLayout yhj_item_lin;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.yhj_item, parent,false);
            viewHolder = new ViewHolder();
            viewHolder.pic = (SimpleDraweeView) convertView.findViewById(R.id.yhj_pic);//优惠卷图片
            viewHolder.yhj_item_lin= (RelativeLayout) convertView.findViewById(R.id.yhj_item_lin);
            viewHolder.yhj_lq = (Button) convertView.findViewById(R.id.yhj_lq);//是否领取了优惠卷
            viewHolder.yhj_sy= (TextView) convertView.findViewById(R.id.yhj_sy);

            viewHolder.yhj_name= (TextView) convertView.findViewById(R.id.yhj_name);
            viewHolder.yhj_time= (TextView) convertView.findViewById(R.id.yhj_time);
            viewHolder.yhj__sm= (TextView) convertView.findViewById(R.id.yhj_sm);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        YhjPro yhjPro = url.get(position);

        viewHolder.yhj_name.setText(yhjPro.getYhj_name());
        viewHolder.yhj__sm.setText(yhjPro.getYhj_sm());


        viewHolder.yhj_time.setText("有效期至"+ viewUtils.CropString(yhjPro.getYhj_enddate()));
        viewHolder.yhj_sy.setText("还剩"+yhjPro.getYhj_num()+"张");

        viewHolder.yhj_lq.setText(yhjPro.getYhjlist_msg());
        if (!("1").equals(yhjPro.getYhjlist_code())) {
            viewHolder.yhj_lq.setEnabled(false);
            viewHolder.yhj_lq.setBackgroundResource(R.drawable.text_bg4);
        }else {
            viewHolder.yhj_lq.setEnabled(true);
            viewHolder.yhj_lq.setBackgroundResource(R.drawable.text_bg2);
            viewHolder.yhj_lq.setOnClickListener(this);
            viewHolder.yhj_lq.setTag(position);
        }

        if (yhjPro.getYhj_pic()!=null&&!yhjPro.equals("")){
            viewHolder.pic.setImageURI(Uri.parse(yhjPro.getYhj_pic()));
        }

        return convertView;
    }


}
