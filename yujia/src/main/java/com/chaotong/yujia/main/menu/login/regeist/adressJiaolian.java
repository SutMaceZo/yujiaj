package com.chaotong.yujia.main.menu.login.regeist;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.menu.login.regeist.wheels.CityModel;
import com.chaotong.yujia.main.menu.login.regeist.wheels.DistrictModel;
import com.chaotong.yujia.main.menu.login.regeist.wheels.OnWheelChangedListener;
import com.chaotong.yujia.main.menu.login.regeist.wheels.ProvinceModel;
import com.chaotong.yujia.main.menu.login.regeist.wheels.WheelView;
import com.chaotong.yujia.main.menu.login.regeist.wheels.XmlParserHandler;
import com.chaotong.yujia.main.menu.login.regeist.wheels.adapter.ArrayWheelAdapter;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/17 0017.
 */

public class adressJiaolian extends BaseActivity implements View.OnClickListener, OnWheelChangedListener {

    @Bind(R.id.jiaolian_adress)
     LinearLayout jiaolian_adress;

    @Bind(R.id.jiaolian_adress_privence)
     TextView adress_privence;

    @Bind(R.id.jiaolian_adress_city)
     TextView adress_city;

    @Bind(R.id.jiaolian_adress_xian)
     TextView adress_district;

    @Bind(R.id.adress_sure)
     Button adressBtn;

    @Bind(R.id.bt_back)
    ImageView bt_back;

    private static final int SELECT_ADRESS=6;


    private LayoutInflater inflater;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    protected String[] mProvinceDatas;
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();
    protected String mCurrentProviceName;
    protected String mCurrentCityName;
    protected String mCurrentDistrictName = "";
    protected String mCurrentZipCode = "";
    private Button mBtnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adress_jiaolian);
        ButterKnife.bind(this);
        
        inflater=LayoutInflater.from(this);

        jiaolian_adress.setOnClickListener(this);
        adressBtn.setOnClickListener(this);
        bt_back.setOnClickListener(this);

    }
        private String privence="";
    private String city="";
    private String district="";

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=getIntent();
            intent.putExtra("privence","");
            intent.putExtra("city","");
            intent.putExtra("district","");
            setResult(SELECT_ADRESS,intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    public void onClick(View view) {
     switch (view.getId())
     {
         case R.id.adress_sure:
             Intent intent=getIntent();
             privence=adress_privence.getText().toString();
             city=adress_city.getText().toString();
             district=adress_district.getText().toString();
             intent.putExtra("privence",privence);
             intent.putExtra("city",city);
             intent.putExtra("district",district);
             setResult(SELECT_ADRESS,intent);
             finish();
             break;
         case R.id.jiaolian_adress:
             changguan_adress();
             break;
         case R.id.bt_back:
             Intent intent1=getIntent();
             intent1.putExtra("privence","");
             intent1.putExtra("city","");
             intent1.putExtra("district","");
             setResult(SELECT_ADRESS,intent1);
             finish();
             break;

     }


    }

    private void changguan_adress() {
        View layout = inflater.inflate(R.layout.gg, null);
        final Dialog dialog = new AlertDialog.Builder(adressJiaolian.this)
                .setView(layout)
                .setTitle("请选择场馆地址")
                .show();
        mViewProvince = (WheelView) layout.findViewById(R.id.id_province);
        mViewCity = (WheelView) layout.findViewById(R.id.id_city);
        mViewDistrict = (WheelView) layout.findViewById(R.id.id_district);
        mBtnConfirm = (Button) layout.findViewById(R.id.btn_confirm);
        mViewProvince.addChangingListener(adressJiaolian.this);
        // 添加change事件
        mViewCity.addChangingListener(adressJiaolian.this);
        // 添加change事件
        mViewDistrict.addChangingListener(adressJiaolian.this);
        // 添加onclick事件
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adress_privence.setText(mCurrentProviceName);
                adress_city.setText(mCurrentCityName);
                adress_district.setText(mCurrentDistrictName);
                dialog.dismiss();
            }
        });
        setUpData();
    }

    private void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = getAssets();
        Log.i("assets", asset + "");
        try {
            InputStream input = asset.open("province_data.xml");

            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据

            provinceList = handler.getDataList();
            Log.i("xxxxxxxxx", provinceList.size() + "");
            //*/ 初始化默认选中的省、市、区
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(adressJiaolian.this, mProvinceDatas));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
    }

    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        int size = areas.length;
        mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
        mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
// TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }
}
