package com.chaotong.yujia.main.menu.xiaoxi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.property.xiaoxi.PushMsg;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016/9/18.
 */
public class XiaoxiAdapter extends BaseAdapter {

    private Context context;
    private List<PushMsg> pushMsgList;

    public XiaoxiAdapter(Context context,List<PushMsg> pushMsgList){
        this.context = context;
        this.pushMsgList = pushMsgList;
    }

    @Override
    public int getCount() {
        return pushMsgList.size();
    }

    @Override
    public Object getItem(int position) {
        return pushMsgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.xiaoxi_adapter,parent,false);
            viewHolder.time = (TextView) convertView.findViewById(R.id.xiaoxi_time);
            viewHolder.content = (TextView) convertView.findViewById(R.id.xiaoxi_content);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PushMsg pushMsg = pushMsgList.get(position);
        viewHolder.time.setText(pushMsg.getDate());
        viewHolder.content.setText(pushMsg.getMsg());

        return convertView;
    }

    class ViewHolder{
       private TextView time,content;
    }
}
