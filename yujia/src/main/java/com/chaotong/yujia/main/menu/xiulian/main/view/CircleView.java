package com.chaotong.yujia.main.menu.xiulian.main.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Administrator on 2017/3/3 0003.
 */
public class CircleView extends View {


    public CircleView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();
        paint.setColor(0xffb38ed3);
       canvas.drawCircle(0,0,8,paint);
    }
}
