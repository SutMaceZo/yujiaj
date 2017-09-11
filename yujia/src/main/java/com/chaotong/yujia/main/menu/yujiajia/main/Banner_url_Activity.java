package com.chaotong.yujia.main.menu.yujiajia.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Banner_url_Activity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.rl_bt_back_yhj)
    RelativeLayout rl_bt_back_yhj;
    @Bind(R.id.main_webview)
    WebView main_webview;
    private Intent intent;
    private String detail_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_banner_url_);
        ButterKnife.bind(this);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        initUrl();
    }

    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && main_webview.canGoBack()) {
//            main_webview.goBack(); //goBack()表示返回WebView的上一页面
//            return true;
//        }
//        return false;
//    }
    private void initUrl(){
        if (!detail_id.equals("")&&detail_id.length()>0) {
            //设置WebView属性，能够执行Javascript脚本
            main_webview.getSettings().setJavaScriptEnabled(true);
            //加载需要显示的网页
            main_webview.loadUrl(detail_id);
            //设置Web视图
            main_webview.setWebViewClient(new HelloWebViewClient ());
        }
    }
    private void initView(){
        intent = getIntent();
        detail_id = intent.getStringExtra("id");
        rl_bt_back_yhj.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_bt_back_yhj:
                finish();
                break;
        }
    }
    //Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
