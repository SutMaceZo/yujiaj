package com.chaotong.yujia.main.menu.benggong.dai_ping_jia;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.entity.JiaoLianBean.receiverPingJia;
import com.chaotong.yujia.main.entity.SdPjBean;
import com.chaotong.yujia.main.menu.xiulian.main.adapter.PjAdapter;
import com.facebook.drawee.view.SimpleDraweeView;

import org.apache.commons.lang.StringEscapeUtils;


import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/26 0026.
 */

public class receiverPJ extends BaseActivity {

    SdPjBean.WdpjBean receiverPJ;

    private SimpleDraweeView pic;
    private TextView name, time, content, classname, data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pj_item);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        name = (TextView) findViewById(R.id.pj_name);
        time = (TextView) findViewById(R.id.pj_time);
        content = (TextView) findViewById(R.id.pj_content);
        classname = (TextView) findViewById(R.id.pj_class_name);
        data = (TextView) findViewById(R.id.pj_data);
        pic = (SimpleDraweeView) findViewById(R.id.pj_pic);

        Bundle bundle=getIntent().getExtras();
        receiverPJ= (SdPjBean.WdpjBean) bundle.get("object");
        String s= StringEscapeUtils.unescapeJava(receiverPJ.getName());
        name.setText(s);
        data.setText(receiverPJ.getDate());
        classname.setText(receiverPJ.getClassname());
        time.setText(receiverPJ.getTime());

        String m=StringEscapeUtils.unescapeJava(receiverPJ.getContent());
        content.setText(m);

        if (receiverPJ.getPic()!=null&&!receiverPJ.getPic().equals("")){
            pic.setImageURI(Uri.parse(receiverPJ.getPic()));
        }
    }
}
