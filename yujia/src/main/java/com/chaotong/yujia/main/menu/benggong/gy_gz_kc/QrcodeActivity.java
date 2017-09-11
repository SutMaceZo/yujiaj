package com.chaotong.yujia.main.menu.benggong.gy_gz_kc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.menu.benggong.Zxing.encode.CodeCreator;
import com.google.zxing.WriterException;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/10/19 0019.
 */

public class QrcodeActivity extends BaseActivity {

    @Bind(R.id.qr_image)
     ImageView qrcode;
    @Bind(R.id.bt_back)
    ImageView bt_back;
    String classid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void init() {
        Intent intent=getIntent();
        classid=intent.getStringExtra("classid");
        try {
            Bitmap bitmap= CodeCreator.createQRCode(classid);
            qrcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
