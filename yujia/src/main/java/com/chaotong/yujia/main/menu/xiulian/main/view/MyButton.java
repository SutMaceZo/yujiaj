package com.chaotong.yujia.main.menu.xiulian.main.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by Administrator on 2017/1/3 0003.
 */
public class MyButton extends Button {
    private boolean _pressed = false;

    Context context;

//把三个构造方法都实现了

   public MyButton(Context context){
       super(context);

    }

  public MyButton(Context context, AttributeSet attrs){
      super(context,attrs);

  }

    public MyButton(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
    }

//覆写dispatchTouchEvent方法

    @Override

    public boolean dispatchTouchEvent(MotionEvent event){

        switch(event.getAction()){

            case MotionEvent.ACTION_DOWN:

                _pressed = true;
                break;

            case MotionEvent.ACTION_UP:

                _pressed = false;

                break;

        }
        return super.dispatchTouchEvent(event);
    }

    //提供查询是否按下

    public boolean checkPress(){

        return _pressed;

    }

}
