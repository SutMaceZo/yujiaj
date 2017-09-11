package com.chaotong.yujia.main.menu.login.regeist;

import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;

import android.location.Address;
import android.location.Geocoder;

import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;

import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.bumptech.glide.Glide;
import com.chaotong.yujia.base.BaseActivity;
import com.chaotong.yujia.main.R;
import com.chaotong.yujia.main.menu.login.regeist.wheels.CityModel;
import com.chaotong.yujia.main.menu.login.regeist.wheels.DistrictModel;
import com.chaotong.yujia.main.menu.login.regeist.wheels.OnWheelChangedListener;
import com.chaotong.yujia.main.menu.login.regeist.wheels.ProvinceModel;
import com.chaotong.yujia.main.menu.login.regeist.wheels.WheelView;
import com.chaotong.yujia.main.menu.login.regeist.wheels.XmlParserHandler;
import com.chaotong.yujia.main.menu.login.regeist.wheels.adapter.ArrayWheelAdapter;

import com.chaotong.yujia.main.thread.ThreadUtils_no;
import com.chaotong.yujia.main.utils.testUtils;
import com.foamtrace.photopicker.ImageCaptureManager;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.PhotoPreviewActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.foamtrace.photopicker.intent.PhotoPreviewIntent;


import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/6 0006.
 */

public class changguan_info extends BaseActivity implements View.OnClickListener, OnWheelChangedListener {

    @Bind(R.id.changuan_name_edit)
     EditText changguan_name;

    @Bind(R.id.changguan_tel)
     EditText changguan_tel;

    @Bind(R.id.bt_back)
     ImageView bt_back;

    private TextView changguan_adress01, changguan_adress02, changguan_adress03;


    @Bind(R.id.changguan_adress)
    RelativeLayout adress;
    @Bind(R.id.back11)
     Button back11;

    @Bind(R.id.add_img)
    LinearLayout mAddImage;

    @Bind(R.id.gridView)
    GridView mGridView;


    private LayoutInflater inflater;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private Button mBtnConfirm;

    @Bind(R.id.changguan_street)
     EditText changguan_street;

    @Bind(R.id.progress)
    ProgressBar progress;

    protected String[] mProvinceDatas;
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();
    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode = "";
    private SharedPreferences sp;


    private static final int REQUEST_CAMERA_CODE = 11;
    private static final int REQUEST_PREVIEW_CODE = 22;
    private ArrayList<String> imagePaths = null;
    private ImageCaptureManager captureManager; // 相机拍照处理类
    private int columnWidth;
    private GridAdapter gridAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guanzhu_info);
        ButterKnife.bind(this);
        inflater = LayoutInflater.from(this);
        changguan_adress01 = (TextView) findViewById(R.id.changguan_adress_privence);
        changguan_adress02 = (TextView) findViewById(R.id.changguan_adress_city);
        changguan_adress03 = (TextView) findViewById(R.id.changguan_adress_xian);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imagePaths=new ArrayList<>();
        back11.setEnabled(true);
        initView();
        initEvent();

    }


    private void initView() {
        sp = getSharedPreferences("Cg", MODE_PRIVATE);
        String sp_name = sp.getString("sp_name", "");
        String sp_tel = sp.getString("sp_tel", "");
        String sp_privence = sp.getString("sp_privence", "");
        String sp_city = sp.getString("sp_city", "");
        String sp_dicr = sp.getString("sp_dicr", "");
        String sp_street = sp.getString("sp_street", "");

        int size = sp.getInt("image_size", 0);
        for(int i=0;i<size;i++) {
            imagePaths.add(sp.getString("image_" + i, null));
        }
        if (imagePaths!=null&&imagePaths.size()>0){
            gridAdapter=new GridAdapter(imagePaths);
            mGridView.setAdapter(gridAdapter);
        }

        changguan_name.setText(sp_name);
        changguan_tel.setText(sp_tel);
        changguan_adress01.setText(sp_privence);
        changguan_adress02.setText(sp_city);
        changguan_adress03.setText(sp_dicr);
        changguan_street.setText(sp_street);

        initGrid();

    }

    private void initGrid() {
        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 3 ? 3 : cols;
        mGridView.setNumColumns(cols);
        // Item Width
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int columnSpace = getResources().getDimensionPixelOffset(R.dimen.space_size);
        columnWidth = (screenWidth - columnSpace * (cols-1)) / cols;
    }


    private void initEvent() {
        adress.setOnClickListener(this);
        back11.setOnClickListener(this);
        mAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] item = {"获取图库照片"};
                new AlertDialog.Builder(changguan_info.this).
                        setTitle("请选择：").
                        setItems(item, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i) {
                                    case 0:
                                        PhotoPickerIntent intent = new PhotoPickerIntent(changguan_info.this);
                                        intent.setSelectModel(SelectModel.MULTI);
                                        intent.setShowCarema(true); // 是否显示拍照
                                        intent.setMaxTotal(3); // 最多选择照片数量，默认为9
                                        intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                                        startActivityForResult(intent, REQUEST_CAMERA_CODE);
                                        break;
                                   /* case 1:
                                        try {
                                            if(captureManager == null){
                                                captureManager = new ImageCaptureManager(changguan_info.this);
                                            }
                                            Intent intent2 = captureManager.dispatchTakePictureIntent();
                                            startActivityForResult(intent2, ImageCaptureManager.REQUEST_TAKE_PHOTO);
                                        } catch (IOException e) {
                                            Toast.makeText(changguan_info.this, com.foamtrace.photopicker.R.string.msg_no_camera, Toast.LENGTH_SHORT).show();
                                            e.printStackTrace();
                                        }
                                        break;*/
                                }
                            }
                        }).show();
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhotoPreviewIntent intent = new PhotoPreviewIntent(changguan_info.this);
                intent.setCurrentItem(position);
                intent.setPhotoPaths(imagePaths);
                startActivityForResult(intent, REQUEST_PREVIEW_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    loadAdpater(data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT));
                    break;
               // 预览
                case REQUEST_PREVIEW_CODE:
                    loadAdpater(data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT));
                    break;
                // 调用相机拍照
                case ImageCaptureManager.REQUEST_TAKE_PHOTO:
                    if(captureManager.getCurrentPhotoPath() != null) {
                        captureManager.galleryAddPic();
                        ArrayList<String> paths = new ArrayList<>();
                        paths.add(captureManager.getCurrentPhotoPath());
                        loadAdpater(paths);
                    }
                    break;

            }
        }
    }

    private void loadAdpater(ArrayList<String> paths){
        if(imagePaths == null){
            imagePaths = new ArrayList<>();
        }
        imagePaths.clear();
        imagePaths.addAll(paths);

        try{
            JSONArray obj = new JSONArray(imagePaths);
            Log.e("--", obj.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

        if(gridAdapter == null){
            gridAdapter = new GridAdapter(imagePaths);
            mGridView.setAdapter(gridAdapter);
        }else {
            gridAdapter.notifyDataSetChanged();
        }
        if (imagePaths.size()>=3){
            mAddImage.setVisibility(View.GONE);
        }else {
            mAddImage.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onPause() {

        super.onPause();
        progress.setVisibility(View.INVISIBLE);
    }

    private static int CG_INFO_RESULT_OK=007;

    String LocationUrl="http://restapi.amap.com/v3/geocode/geo?";/*key=您的key&address=方恒国际中心A座&city=北京*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back11:

                String street = changguan_street.getText().toString();
                String provice = changguan_adress01.getText().toString();
                String city = changguan_adress02.getText().toString();
                String district = changguan_adress03.getText().toString();
                progress.setVisibility(View.VISIBLE);
                initLatLong(provice,city,district,street);

                break;
            case R.id.changguan_adress:
                changguan_adress();
                break;

        }
    }

    private void initLatLong(final String provice, final String city, final String district, final String street) {

       final GeocodeSearch geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
              double latitude= geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint().getLatitude();
               double longitude=geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint().getLongitude();
               Log.i("info","lat+long:"+latitude+"   "+longitude);
                String names = changguan_name.getText().toString();
                String tel = changguan_tel.getText().toString();
                Intent intent = getIntent();
                if (!("").equals(provice) &&!("").equals(city)&& !("").equals(district)
                        &&! ("").equals(names) &&! ("").equals(tel)&&!("").equals(street)) {
                    Boolean F = testUtils.isTel(tel);
                    if (F) {
                        intent.putExtra("provice", provice);
                        intent.putExtra("city", city);
                        intent.putExtra("district", district);
                        intent.putExtra("changguan_name", names);
                        intent.putExtra("street", street);
                        intent.putExtra("mobile", tel);
                        intent.putExtra("lat",latitude);
                        intent.putExtra("long",longitude);
                        intent.putStringArrayListExtra("imagelist",imagePaths);

                        SharedPreferences sp = getSharedPreferences("Cg", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("sp_name", names);
                        editor.putString("sp_tel", tel);
                        editor.putString("sp_privence", provice);
                        editor.putString("sp_city", city);
                        editor.putString("sp_dicr", district);
                        editor.putString("sp_street", street);
                        editor.putInt("image_size",imagePaths.size());
                        for (int j=0;j<imagePaths.size();j++){
                            editor.remove("image_"+j);
                            editor.putString("image_"+j,imagePaths.get(j));
                        }
                        editor.commit();
                        back11.setEnabled(false);
                        progress.setVisibility(View.VISIBLE);

                        setResult(CG_INFO_RESULT_OK, intent);

                        finish();
                    } else {
                        back11.setEnabled(true);
                        progress.setVisibility(View.INVISIBLE);
                        Toast.makeText(changguan_info.this, "电话格式不正确", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(changguan_info.this,
                            "请确认信息全部填充",
                            Toast.LENGTH_LONG).show();
                    progress.setVisibility(View.INVISIBLE);
                    back11.setEnabled(true);
                }

            }
        });
//通过GeocodeQuery设置查询参数,调用getFromLocationNameAsyn(GeocodeQuery geocodeQuery) 方法发起请求。
//address表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode都ok
        GeocodeQuery query = new GeocodeQuery(provice+city+district+street, city);
        geocodeSearch.getFromLocationNameAsyn(query);

    }


    private void changguan_adress() {
        View layout = inflater.inflate(R.layout.gg, null);
        final Dialog dialog = new AlertDialog.Builder(changguan_info.this)
                .setView(layout)
                .setTitle("请选择场馆地址")
                .show();
        mViewProvince = (WheelView) layout.findViewById(R.id.id_province);
        mViewCity = (WheelView) layout.findViewById(R.id.id_city);
        mViewDistrict = (WheelView) layout.findViewById(R.id.id_district);
        mBtnConfirm = (Button) layout.findViewById(R.id.btn_confirm);
        mViewProvince.addChangingListener(changguan_info.this);
        // 添加change事件
        mViewCity.addChangingListener(changguan_info.this);
        // 添加change事件
        mViewDistrict.addChangingListener(changguan_info.this);

        // 添加onclick事件
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changguan_adress01.setText(mCurrentProviceName);
                changguan_adress02.setText(mCurrentCityName);
                changguan_adress03.setText(mCurrentDistrictName);
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
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(changguan_info.this, mProvinceDatas));
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
        int size=areas.length;
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

    private class GridAdapter extends BaseAdapter {
        private ArrayList<String> listUrls;

        public GridAdapter(ArrayList<String> listUrls) {
            this.listUrls = listUrls;
        }

        @Override
        public int getCount() {
            return listUrls.size();
        }

        @Override
        public String getItem(int position) {
            return listUrls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.item_image, null);
                imageView = (ImageView) convertView.findViewById(R.id.imageView);
                convertView.setTag(imageView);
                // 重置ImageView宽高
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(columnWidth, columnWidth);
                imageView.setLayoutParams(params);
            }else {
                imageView = (ImageView) convertView.getTag();
            }
            Glide.with(changguan_info.this)
                    .load(new File(getItem(position)))
                    .placeholder(R.mipmap.default_error)
                    .error(R.mipmap.default_error)
                    .centerCrop()
                    .crossFade()
                    .into(imageView);
            Log.i("info","urii:"+(new File(getItem(position))));
            return convertView;
        }
    }

}

