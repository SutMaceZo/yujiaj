package com.chaotong.yujia.main.menu.benggong.suo_shu_chang_guan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.menu.benggong.suo_shu_chang_guan.util.PopBirthHelper;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.thread.ThreadUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/13 0013.
 */
public class TKActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.progress)
    ProgressBar probar;

    @Bind(R.id.tk_tv1)
    TextView tk_tv1;
    @Bind(R.id.tk_tv2)
    TextView tk_tv2;

    @Bind(R.id.tk_tv4)
    TextView tk_tv4;

    @Bind(R.id.tk_tv6)
    TextView tk_tv6;
    @Bind(R.id.tk_tv8)
    TextView tk_tv8;

    @Bind(R.id.tk_et1)
    EditText tk_et1;

    @Bind(R.id.commint)
    TextView commit;
    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
    ImageView back;


    Intent intent;
    String card_id = "";
    String sp_id = "";
    String reqid = "";
    String sp_name="";
    String valid_date="";

    SharedPreferences sp;
    UrlPath urlPath = UrlPath.getUrlPath();
    String TkUrl = urlPath.getUrl() + "StopCardServlet";

    PopBirthHelper popBirthHelper;
    PopBirthHelper popBirthHelper2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tk);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initEvent() {
        rl_bt_back.setOnClickListener(this);
        commit.setOnClickListener(this);
        tk_tv4.setOnClickListener(this);
        tk_tv6.setOnClickListener(this);

    }

    private void initView() {
        sp = getSharedPreferences(MyApplication.SpName, MODE_PRIVATE);
        reqid = sp.getString("reqid", "");
        intent = getIntent();
        sp_id = intent.getStringExtra("sp_id");
        card_id = intent.getStringExtra("card_id");
        valid_date=intent.getStringExtra("valid_date");
        sp_name=intent.getStringExtra("sp_name");

        tk_tv1.setText(sp_name);
        tk_tv2.setText("会员卡号："+card_id);
        tk_tv8.setText(valid_date);

        popBirthHelper = new PopBirthHelper(this);
        popBirthHelper.setOnClickOkListener(new PopBirthHelper.OnClickOkListener() {
            @Override
            public void onClickOk(String birthday) {
                //   Toast.makeText(TKActivity.this, birthday, Toast.LENGTH_LONG).show();
                tk_tv4.setText(birthday);
            }
        });
        popBirthHelper2 = new PopBirthHelper(this);
        popBirthHelper2.setOnClickOkListener(new PopBirthHelper.OnClickOkListener() {
            @Override
            public void onClickOk(String birthday) {
                //   Toast.makeText(TKActivity.this, birthday, Toast.LENGTH_LONG).show();
                tk_tv6.setText(birthday);
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tk_tv4:
                popBirthHelper.show(tk_tv4);
                break;
            case R.id.tk_tv6:
                popBirthHelper2.show(tk_tv4);
                break;
            case R.id.commint:
                String b_time = tk_tv4.getText().toString();
                String e_time = tk_tv6.getText().toString();
                String beEdit = tk_et1.getText().toString();
                if (b_time != null && e_time != null && beEdit != null) {
                    if (!("").equals(b_time)) {
                        if (!("").equals(e_time)) {
                            if (!("").equals(beEdit)) {
                                JSONObject object = new JSONObject();
                                String content = "";
                                try {
                                    object.put("reqid", reqid);
                                    object.put("card_id", card_id);
                                    object.put("sp_id", sp_id);
                                    object.put("b_date", b_time);
                                    object.put("e_date", e_time);
                                    object.put("sqyy", beEdit);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                content = String.valueOf(object);
                                probar.setVisibility(View.VISIBLE);
                                commit.setEnabled(false);
                                new ThreadUtils(TKActivity.this, TkUrl, content, handler).start();

                            } else {
                                ToastUtils.showToast(TKActivity.this, "请填写停课原因");
                            }
                        } else {
                            ToastUtils.showToast(TKActivity.this, "请选择停课结束时间");
                        }

                    } else {
                        ToastUtils.showToast(TKActivity.this, "请选择停课开始时间");
                    }
                }
                break;
            case R.id.rl_bt_back:
                finish();
                break;
        }
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = msg.obj.toString();
            if (json != null) {
                probar.setVisibility(View.INVISIBLE);
                commit.setEnabled(true);
                try {
                    JSONObject object=new JSONObject(json);
                    String code=object.getString("code");
                    String message=object.getString("msg");
                    if (code!=null&&code.equals("1")){
                        ToastUtils.showToast(TKActivity.this,message);
                        finish();
                    }else {
                        ToastUtils.showToast(TKActivity.this,message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    };
}
