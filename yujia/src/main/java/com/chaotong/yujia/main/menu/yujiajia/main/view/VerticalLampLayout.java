package com.chaotong.yujia.main.menu.yujiajia.main.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.chaotong.yujia.main.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 垂直跑马灯
 */
public class VerticalLampLayout extends LinearLayout implements OnClickListener {

	private boolean isOne = true;// 两次滑动的标志位
	private boolean flag=true;

	private static final long DURATION = 3000;// 时间间隔

	private MyHandler mHandler;

	private List<String> mDataList;
	private Context mContext;

	private TextView mText_1;
	private TextView mText_2;
	private Animation mSlide_In;
	private Animation mSlide_Out;

	private int item = 0;// 标志位

	/**
	 * 构造函数
	 */
	public VerticalLampLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mHandler = new MyHandler();
		mContext = getContext();
		View layout = LayoutInflater.from(mContext).inflate(R.layout.vertical_lamp_layout, this);

		mText_1 = (TextView) layout.findViewById(R.id.lamp_text_1);
		mText_2 = (TextView) layout.findViewById(R.id.lamp_text_2);
		//mText_1.setOnClickListener(this);
		//mText_2.setOnClickListener(this);

		mSlide_In = AnimationUtils.loadAnimation(mContext, R.anim.slide_in);
		mSlide_Out = AnimationUtils.loadAnimation(mContext, R.anim.slide_out);
	}

	/**
	 * 对外,设置文本数据
	 */
	public void setData(List<String> list) {
		this.mDataList = list;
		// 非空判断
		if (mDataList == null || mDataList.size() < 2) {
			mDataList = new ArrayList<String>();
			mDataList.add("通知一");
			mDataList.add("通知二");
		}
		mText_1.setText(mDataList.get(0));
		mText_2.setText(mDataList.get(1));
	}

	/**
	 * 对外,执行启动
	 */
	public void startRun() {
		mHandler.postDelayed(mHandler, DURATION);
		Log.i("info","start");
	}
	public void stopRun(){
		Log.i("info","stop");
		mHandler.removeCallbacks(mHandler);
	}

	/**
	 * 业务执行的handler
	 */
	class MyHandler extends Handler implements Runnable {
		@Override
		public void run() {
			if (isOne) {
				startOne();
			} else {
				startTwo();
			}

			mHandler.postDelayed(this, DURATION);// 重复执行
		}
	}

	/** 第一步的动画 */
	private void startOne() {
		mText_1.startAnimation(mSlide_Out);
		mText_1.setVisibility(View.INVISIBLE);
		if (mDataList != null) {
			if ((++item) == mDataList.size()) {
				item = 0;
			}
			mText_2.setText(mDataList.get(item));
			mText_2.setVisibility(View.VISIBLE);
			mText_2.startAnimation(mSlide_In);
			isOne = false;
		}

	}

	/** 第二步的动画 */
	private void startTwo() {
		mText_2.startAnimation(mSlide_Out);
		mText_2.setVisibility(View.INVISIBLE);

		if ((++item) == mDataList.size()) {
			item = 0;
		}
		mText_1.setText(mDataList.get(item));
		mText_1.setVisibility(View.VISIBLE);
		mText_1.startAnimation(mSlide_In);

		isOne = true;
	}

	@Override
	public void onClick(View view) {
		Toast.makeText(mContext, mDataList.get(item), Toast.LENGTH_SHORT).show();
	}

}
