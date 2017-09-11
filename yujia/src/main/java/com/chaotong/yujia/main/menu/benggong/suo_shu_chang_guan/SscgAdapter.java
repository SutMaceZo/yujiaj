package com.chaotong.yujia.main.menu.benggong.suo_shu_chang_guan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.entity.HuiYuanBean.SscgBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Administrator on 2017/2/13 0013.
 */
public class SscgAdapter extends BaseAdapter {
    Context context;
    List<SscgBean.CardsBean> mList;
    LayoutInflater inflater;

    public SscgAdapter(Context context, List<SscgBean.CardsBean> mList) {
        this.context = context;
        this.mList = mList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        viewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.sscg_list, viewGroup, false);
            holder = new viewHolder();
            holder.textView1= (TextView) view.findViewById(R.id.sscg_tv1);
            holder.textView2= (TextView) view.findViewById(R.id.sscg_tv2);
            holder.textView3= (TextView) view.findViewById(R.id.sscg_tv3);
            holder.textView4= (TextView) view.findViewById(R.id.sscg_tv4);
            holder.textView5= (TextView) view.findViewById(R.id.sscg_tv5);
            holder.sscg_bt= (Button) view.findViewById(R.id.sscg_bt);
            view.setTag(holder);
        } else {
            holder = (viewHolder) view.getTag();
        }

        holder.textView1.setText(mList.get(i).getSp_name());
        holder.textView2.setText("会员卡号："+mList.get(i).getCard_id());
        holder.textView4.setText("本卡有效至"+mList.get(i).getValid_date());


        if (mList.get(i).getFlag()!=null){
            if (mList.get(i).getFlag().equals("0")){
                holder.sscg_bt.setVisibility(View.VISIBLE);
                holder.textView3.setText(mList.get(i).getFlag_msg());
            }else  if (mList.get(i).getFlag().equals("1")){
                holder.sscg_bt.setVisibility(View.VISIBLE);
                holder.textView3.setText("("+mList.get(i).getFlag_msg()+
                        ",共"+mList.get(i).getTotal_num()+"次，剩余"+
                        mList.get(i).getSurplus_num()+"次");

            }else if (mList.get(i).getFlag().equals("2")||mList.get(i).getFlag().equals("3")){
                holder.textView3.setText(mList.get(i).getFlag_msg());
                holder.sscg_bt.setVisibility(View.GONE);
            }
        }

        String falg = mList.get(i).getUse_flag();

       /* 821030;*/

        if (falg != null) {
            if (("1").equals(falg) || ("2").equals(falg)) {
                holder.sscg_bt.setVisibility(View.VISIBLE);
                holder.sscg_bt.setText(mList.get(i).getUse_msg());
                holder.sscg_bt.setEnabled(true);
                holder.sscg_bt.setBackgroundResource(R.drawable.text_bg2);
                holder.sscg_bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, TKActivity.class);
                        intent.putExtra("card_id", mList.get(i).getCard_id());
                        intent.putExtra("sp_id",mList.get(i).getSp_id());
                        intent.putExtra("sp_name",mList.get(i).getSp_name());
                        intent.putExtra("valid_date",mList.get(i).getValid_date());
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        Activity activity = (Activity) context;
                        activity.startActivity(intent);
                    }
                });

            }else {
                holder.sscg_bt.setEnabled(false);
                holder.sscg_bt.setText(mList.get(i).getUse_msg());
                holder.sscg_bt.setBackgroundResource(R.drawable.text_bg4);
            }
        }

        return view;
    }

    class viewHolder {
        TextView textView1, textView2,textView3,textView4,textView5;
        Button sscg_bt;
    }
}
