package com.chaotong.yujia.main.menu.benggong.suo_shu_chang_guan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.thread.ThreadUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/13 0013.
 */
public class SsAdd1Activity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.rl_yuyue_back)
    RelativeLayout rl_yuyue_back;
    @Bind(R.id.yuyue_back)
    ImageView back;
    @Bind(R.id.xz_cg)
    TextView XzCg;
    @Bind(R.id.card_id)
    EditText card_id;
    @Bind(R.id.add)
    Button add;

    Intent intent;
    String reqid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ssadd1);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        rl_yuyue_back.setOnClickListener(this);
        XzCg.setOnClickListener(this);
        add.setOnClickListener(this);
        intent = getIntent();
        reqid = intent.getStringExtra("reqid");
    }
    UrlPath urlPath = UrlPath.getUrlPath();
    String Sdurl=urlPath.getUrl()+"AddCardServlet";
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_yuyue_back:
                finish();
                break;
            case R.id.xz_cg:
                Intent intent1=new Intent(SsAdd1Activity.this,SsAddActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivityForResult(intent1,1);
                break;
            case R.id.add:

                if (sp_id!=null&&!sp_id.equals("")){
                    if (!("").equals(card_id.getText().toString())){


                    String content="";
                    final JSONObject object=new JSONObject();
                    try {
                        object.put("reqid",reqid);
                        object.put("card_id",card_id.getText().toString());
                        object.put("sp_id",sp_id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    content=String.valueOf(object);

                    new ThreadUtils(SsAdd1Activity.this,Sdurl,content,new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            String json=msg.obj.toString();
                            try {
                                JSONObject object1=new JSONObject(json);
                                String code=object1.getString("code");
                                String message=object1.getString("msg");
                                if (code!=null&&code.equals("1")){
                                    finish();
                                    ToastUtils.showToast(SsAdd1Activity.this,message);
                                }else {
                                    ToastUtils.showToast(SsAdd1Activity.this,message);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();
                    }else {
                        ToastUtils.showToast(SsAdd1Activity.this,"请选择卡号");
                    }
                }else {
                    ToastUtils.showToast(SsAdd1Activity.this,"请选择场馆");
                }

                break;
        }
    }
    String sp_id;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1){
                if (data!=null){
                    String sp_name=data.getStringExtra("sp_name");
                    sp_id=data.getStringExtra("sp_id");
                    XzCg.setText(sp_name);
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
