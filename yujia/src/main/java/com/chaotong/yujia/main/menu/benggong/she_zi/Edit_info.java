package com.chaotong.yujia.main.menu.benggong.she_zi;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.MyApplication;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.UrlPath;
import com.chaotong.yujia.main.entity.HuiYuanBean.huiyuanBean;
import com.chaotong.yujia.main.menu.login.loginActivity;
import com.chaotong.yujia.main.menu.yujiajia.Location.utils.ToastUtils;
import com.chaotong.yujia.main.thread.ThreadUtils;
import com.chaotong.yujia.main.thread.testPhoneThread;
import com.chaotong.yujia.main.utils.testUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.foamtrace.photopicker.Image;
import com.google.gson.Gson;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/16 0016.
 */

public class Edit_info extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.touxiang)
    RelativeLayout touxiang_Layout;
    @Bind(R.id.xinbie)
    RelativeLayout xinbie_Layout;
    @Bind(R.id.nianling)
    RelativeLayout nianling_Layout;
    @Bind(R.id.edit_button)
    Button edit_button;
    @Bind(R.id.phone)
    RelativeLayout phone_Layout;
    @Bind(R.id.nicheng)
    RelativeLayout name_layout;

    TextView edit_sex, edit_age, edit_phone, edit_name;
    SimpleDraweeView edit_pic;


    static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    static final int PHOTO_REQUEST_CUT = 3;// 结果


    static final String PHOTO_FILE_NAME = "temp_phione.png";

    File tempFile;
    Bitmap bitmap;
    int index01, index02;
    LayoutInflater inflater;
    SharedPreferences sp;
    UrlPath urlPath = UrlPath.getUrlPath();
    String reqid = "";
    String url = urlPath.getUrl() + "SelfUpdateServlet";

    huiyuanBean huiyuan;

    @Bind(R.id.rl_bt_back)
    RelativeLayout rl_bt_back;
    @Bind(R.id.bt_back)
    ImageView bt_back;

    @Bind(R.id.progress)
    ProgressBar progress;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            if (json != null) {
                progress.setVisibility(View.INVISIBLE);
                try {
                    JSONObject object = new JSONObject(json);
                    String code = object.getString("code");
                    String message = object.getString("msg");
                    if (code != null && message != null) {
                        if (("1").equals(code)) {
                            finish();
                        } else if (("0").equals(code)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Edit_info.this);
                            builder.setCancelable(false);
                            builder.setTitle(getResources().getString(R.string.app_name) + "")
                                    .setMessage(getResources().getString(R.string.message) + "")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            SharedPreferences sp = getSharedPreferences(MyApplication.SpName, MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sp.edit();
                                            editor.putBoolean("isLogin", false);
                                            editor.putString("reqid", "");
                                            editor.putString("type", "");
                                            editor.commit();
                                            Intent intent = new Intent(Edit_info.this, loginActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        }
                                    }).show();
                        } else {
                            Toast.makeText(Edit_info.this, message, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Edit_info.this, getResources().getString(R.string.error), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_info);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }


    void initEvent() {
        touxiang_Layout.setOnClickListener(this);
        xinbie_Layout.setOnClickListener(this);
        nianling_Layout.setOnClickListener(this);
        phone_Layout.setOnClickListener(this);
        name_layout.setOnClickListener(this);
        edit_button.setOnClickListener(this);

    }

    void initView() {
        edit_name = (TextView) findViewById(R.id.edit_name);
        edit_age = (TextView) findViewById(R.id.edit_nianling);
        edit_sex = (TextView) findViewById(R.id.edit_xinbie);
        edit_phone = (TextView) findViewById(R.id.edit_phone);
        edit_pic = (SimpleDraweeView) findViewById(R.id.edit_touxiang_img);
        inflater = LayoutInflater.from(this);
        sp = getSharedPreferences("Yuga", MODE_PRIVATE);
        reqid = sp.getString("reqid", "");
        if (!("").equals(reqid)) {
            progress.setVisibility(View.VISIBLE);
            String content = "";
            String u = urlPath.getUrl() + "SelfInfoServlet";
            JSONObject ob = new JSONObject();
            try {
                ob.put("reqid", reqid);
                content = String.valueOf(ob);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new ThreadUtils(this, u, content, xhandler).start();
        }
        rl_bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    Handler xhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            Gson gson = new Gson();
            huiyuan = gson.fromJson(json, huiyuanBean.class);
            if (huiyuan != null) {
                progress.setVisibility(View.INVISIBLE);
                String code = huiyuan.getCode();
                String message = huiyuan.getMsg();
                if (code != null) {
                    if (code.equals("1") || code.equals("0")) {
                        init();
                    } else {
                        ToastUtils.showToast(Edit_info.this, message);
                    }
                }
            } else {
                ToastUtils.showToast(Edit_info.this, getResources().getString(R.string.error));
            }

            super.handleMessage(msg);
        }
    };

    void init() {
        edit_phone.setText(huiyuan.getInfo().getTel());
        String s = StringEscapeUtils.unescapeJava(huiyuan.getInfo().getNcname());
        Log.i("zz", s);
        edit_name.setText(s);
        edit_pic.setDrawingCacheEnabled(true);
        edit_sex.setText(huiyuan.getInfo().getSex());
        edit_age.setText(huiyuan.getInfo().getAge());

        if (huiyuan.getInfo().getPic() != null && !huiyuan.getInfo().getPic().equals("")) {
            edit_pic.setImageURI(Uri.parse(huiyuan.getInfo().getPic()));
        }

    }

    void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                crop(uri);
            }

        } else if (requestCode == PHOTO_REQUEST_CAMERA) {
            if (hasSdcard()) {
                tempFile = new File(Environment.getExternalStorageDirectory(),
                        PHOTO_FILE_NAME);
                crop(Uri.fromFile(tempFile));
            } else {
                Toast.makeText(Edit_info.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            try {
                bitmap = data.getParcelableExtra("data");
                this.edit_pic.setImageBitmap(bitmap);
                boolean delete = tempFile.delete();
                System.out.println("delete = " + delete);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_button:
                String ncname = edit_name.getText().toString();
                String sex = edit_sex.getText().toString();
                String age = edit_age.getText().toString();
                String tel = edit_phone.getText().toString();

                String pic = "";
                try {
                    ByteArrayOutputStream out01 = new ByteArrayOutputStream();
                    if (bitmap == null) {
                        bitmap = Bitmap.createBitmap(edit_pic.getDrawingCache());
                        edit_pic.setDrawingCacheEnabled(false);
                    }
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out01);
                    out01.flush();
                    out01.close();
                    byte[] buffer = out01.toByteArray();
                    byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
                    pic = new String(encode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (("").equals(reqid) && ("").equals(ncname) && ("").equals(sex) && ("")
                        .equals(age) && ("").equals(tel) && ("").equals(pic)) {
                    Toast.makeText(Edit_info.this, "请补全信息", Toast.LENGTH_LONG).show();
                } else {
                    JSONObject object = new JSONObject();
                    String content = "";
                    try {
                        object.put("ncname", ncname);
                        object.put("sex", sex);
                        object.put("age", age);
                        object.put("reqid", reqid);
                        object.put("tel", tel);
                        object.put("pic", pic);
                        content = String.valueOf(object);
                        Log.i("info", content);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    progress.setVisibility(View.VISIBLE);
                    new ThreadUtils(Edit_info.this, url, content, handler).start();
                }

                break;
            case R.id.nicheng:
                LayoutInflater inflater = LayoutInflater.from(Edit_info.this);
                View editView = inflater.inflate(R.layout.dialog_edit, null);
                final EditText edit = (EditText) editView.findViewById(R.id.dialog_edit);
                edit.setHint("请输入昵称");

                new AlertDialog.Builder(this).setTitle("昵称：")
                        .setView(editView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                edit_name.setText(edit.getText().toString());
                            }
                        })
                        .setNegativeButton("取消", null).show();
                break;
            case R.id.touxiang:
                String[] item = {"获取图库照片", "拍照获取照片"};
                new AlertDialog.Builder(this).
                        setTitle("请选择：").
                        setItems(item, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i) {
                                    case 0:
                                        Intent intent = new Intent(Intent.ACTION_PICK);
                                        intent.setType("image/*");
                                        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                                        break;
                                    case 1:
                                        Intent intent01 = new Intent("android.media.action.IMAGE_CAPTURE");
                                        // 判断存储卡是否可以用，可用进行存储
                                        if (hasSdcard()) {
                                            intent01.putExtra(MediaStore.EXTRA_OUTPUT,
                                                    Uri.fromFile(new File(Environment
                                                            .getExternalStorageDirectory(), PHOTO_FILE_NAME)));
                                        }
                                        startActivityForResult(intent01, PHOTO_REQUEST_CAMERA);
                                        break;
                                }
                            }
                        }).show();
                break;

            case R.id.xinbie:
                final String[] type01 = {"男", "女"};
                String s = edit_sex.getText().toString();
                int n = -1;
                if (s != null && ("男").equals(s)) {
                    n = 0;
                } else if (s != null && ("女").equals(s)) {
                    n = 1;
                }
                new AlertDialog.Builder(this).setTitle("请选择性别：").
                        setSingleChoiceItems(type01, n, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                index01 = which;
                            }
                        }).
                        setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                edit_sex.setText(type01[index01]);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

                break;
            case R.id.nianling:
                final String[] ages = {"90后", "80后", "70后"};
                String Sage = edit_age.getText().toString();
                int Sn = -1;
                if (Sage != null && ("90后").equals(Sage)) {
                    Sn = 0;
                } else if (Sage != null && ("80后").equals(Sage)) {
                    Sn = 1;
                } else if (Sage != null && ("70后").equals(Sage)) {
                    Sn = 2;
                }


                new AlertDialog.Builder(this).setTitle("请选择年龄：").
                        setSingleChoiceItems(ages, Sn, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                index02 = which;
                            }
                        }).
                        setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                edit_age.setText(ages[index02]);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
                break;
            case R.id.phone:
                phone_get();
                break;

        }
    }

    EditText dialog_phone;
    Button dialog_testBtn;
    String testPhone_url = urlPath.getUrl() + "Verification";
    Dialog dialog;

    void phone_get() {
        View layout = inflater.inflate(R.layout.edit_phone_test, null);
        dialog = new AlertDialog.Builder(Edit_info.this, R.style.Dialog_Fullscreen)
                .setView(layout)
                .show();
        dialog_phone = (EditText) layout.findViewById(R.id.phone_number);
        dialog_testBtn = (Button) layout.findViewById(R.id.make_sure02);
        dialog_testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog_phone.equals("")) {
                    Toast.makeText(Edit_info.this, "手机号不能为空", Toast.LENGTH_LONG).show();
                } else {
                    Boolean S = testUtils.isPhone(dialog_phone.getText().toString());
                    if (S == false) {
                        Toast.makeText(Edit_info.this, "请检查你的手机号是否输入正确", Toast.LENGTH_LONG).show();
                    } else {
                        new testPhoneThread(Edit_info.this, dialog_phone.getText().toString(), mHandler, testPhone_url).start();
                    }
                }
            }
        });
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.obj.toString();
            try {
                JSONObject object = new JSONObject(json);
                String code = object.getString("code");
                String verificationCode = object.getString("verificationCode");
                if (code.equals("1")) {
                    Toast.makeText(Edit_info.this, "该手机号可以使用", Toast.LENGTH_LONG).show();
                    edit_phone.setText(dialog_phone.getText().toString());
                    dialog.dismiss();
                } else if (code.equals("2")) {
                    Toast.makeText(Edit_info.this, "抱歉，该手机号已被注册", Toast.LENGTH_LONG).show();
                } else if (code.equals("-1")) {
                    Toast.makeText(Edit_info.this, "服务器出现异常", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}
