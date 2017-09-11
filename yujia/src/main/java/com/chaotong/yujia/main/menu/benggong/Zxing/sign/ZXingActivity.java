package com.chaotong.yujia.main.menu.benggong.Zxing.sign;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.menu.benggong.Zxing.an.CaptureActivity;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/9/2 0002.
 */

public class ZXingActivity extends BaseActivity {

    @Bind(R.id.scan_code)
     RelativeLayout scan_code;
    @Bind(R.id.result)
     TextView result;

    private static final int REQUEST_CODE_SCAN = 0x0000;

    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qiandao);
        ButterKnife.bind(this);

        scan_code.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(ZXingActivity.this,
                        CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);

                result.setText("解码结果： \n" + content);
             //   qrCodeImage.setImageBitmap(bitmap);
            }
        }
    }
}
