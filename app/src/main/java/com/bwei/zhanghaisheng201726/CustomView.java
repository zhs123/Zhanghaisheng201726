package com.bwei.zhanghaisheng201726;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
*1.类的用途
*2.zhanghaisheng
*3.2017/2/6
**/
public class CustomView extends View {
    private Paint paint;
    private String text;
    private int bigcolor;
    private int smallcolor;
    private int bigradius;
    private int smallradius;
    private int zhengcolor;
    private int textsize;

    private int vwith;
    private int vheight;

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context) {
        this(context, null);
    }
    //自定义属性
    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CustomView, defStyleAttr, 0);

        bigradius = (int) a.getDimension(R.styleable.CustomView_bigradius, 100);
        bigcolor = a.getColor(R.styleable.CustomView_smallcolor, Color.YELLOW);
        smallcolor = a.getColor(R.styleable.CustomView_smallcolor, Color.WHITE);
        smallradius = (int) a.getDimension(R.styleable.CustomView_smallradius, 40);
        text = a.getString(R.styleable.CustomView_text);
        zhengcolor = a.getColor(R.styleable.CustomView_zhengcolor, Color.GREEN);
        textsize = a.getInt(R.styleable.CustomView_Textsize, 20);
        paint = new Paint();
        a.recycle();

    }

    @Override//测量
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //得到View的宽和高
        vwith = getWidth() / 2;
        vheight = getHeight() / 2;

        int whithmode = MeasureSpec.getMode(widthMeasureSpec);
        int whithcurr = MeasureSpec.getSize(widthMeasureSpec);
        int heighthmode = MeasureSpec.getMode(heightMeasureSpec);
        int heightcurr = MeasureSpec.getSize(heightMeasureSpec);

        int with;
        int height;
        if (whithmode == MeasureSpec.EXACTLY) {
            with = whithcurr;
        } else {
            with = (int) (2 * bigradius) + getPaddingLeft() + getPaddingRight();
        }

        if (heighthmode == MeasureSpec.EXACTLY) {
            height = heightcurr;
        } else {
            height = (int) (2 * bigradius) + getPaddingBottom() + getPaddingTop();
        }

        setMeasuredDimension(with, height);


    }
    //画图
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
//

        //画正方形
        paint.setColor(zhengcolor);
        canvas.drawRect(vwith - bigradius, vheight - bigradius, vwith + bigradius, vheight + bigradius, paint);

        //画大圆
        paint.setColor(bigcolor);
        canvas.drawCircle(vwith, vheight, bigradius, paint);
        //画小圆
        paint.setColor(smallcolor);
        canvas.drawCircle(vwith, vheight, smallradius, paint);
        //测量画圆的尺寸
        Rect mrect = new Rect();        String str = "圆环";

        paint.getTextBounds(str, 0, str.length(), mrect);
        //写字
        paint.setTextSize(textsize);
        paint.setColor(Color.BLACK);
        canvas.drawText(str, vwith - mrect.width() / 2, vheight + mrect.height() / 2, paint);


    }
    //触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //获取当前按下的位置点
                float xdown = event.getX();
                float ydown = event.getY();

                double sqrt = Math.sqrt((xdown - vwith) * (xdown - vwith) + (ydown - vheight) * (ydown - vheight));
                if (sqrt <= smallradius) {

                    Toast.makeText(getContext(), "在小圆内", Toast.LENGTH_SHORT).show();
                }
                if (sqrt <= bigradius && sqrt > smallradius) {

                    Toast.makeText(getContext(), "在圆环内", Toast.LENGTH_SHORT).show();
                }
                if (sqrt > bigradius) {

                    Toast.makeText(getContext(), "在圆环外", Toast.LENGTH_SHORT).show();
                }


                break;

        }

        return true;

    }

   
}
