package com.example.wsd_client.util.CalendarViewUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class WeekDayView extends View {
    //�Ϻ�����ɫ
    private int mTopLineColor = Color.parseColor("#CCE4F2");
    //�º�����ɫ
    private int mBottomLineColor = Color.parseColor("#CCE4F2");
    //��һ���������ɫ
    private int mWeedayColor = Color.parseColor("#1FC2F3");
    //���������յ���ɫ
    private int mWeekendColor = Color.parseColor("#fa4451");
    //�ߵĿ��
    private int mStrokeWidth = 4;
    private int mWeekSize = 14;
    private Paint paint;
    private DisplayMetrics mDisplayMetrics;
    private String[] weekString = new String[]{"��","һ","��","��","��","��","��"};
    public WeekDayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDisplayMetrics = getResources().getDisplayMetrics();
        paint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if(heightMode == MeasureSpec.AT_MOST){
            heightSize = mDisplayMetrics.densityDpi * 30;
        }
        if(widthMode == MeasureSpec.AT_MOST){
            widthSize = mDisplayMetrics.densityDpi * 300;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        //���л�������
        paint.setStyle(Style.STROKE);
        paint.setColor(mTopLineColor);
        paint.setStrokeWidth(mStrokeWidth);
        canvas.drawLine(0, 0, width, 0, paint);

        //���º���
        paint.setColor(mBottomLineColor);
        canvas.drawLine(0, height, width, height, paint);
        paint.setStyle(Style.FILL);
        paint.setTextSize(mWeekSize * mDisplayMetrics.scaledDensity);
        int columnWidth = width / 7;
        for(int i=0;i < weekString.length;i++){
            String text = weekString[i];
            int fontWidth = (int) paint.measureText(text);
            int startX = columnWidth * i + (columnWidth - fontWidth)/2;
            int startY = (int) (height/2 - (paint.ascent() + paint.descent())/2);
            if(text.indexOf("��") > -1|| text.indexOf("��") > -1){
                paint.setColor(mWeekendColor);
            }else{
                paint.setColor(mWeedayColor);
            }
            canvas.drawText(text, startX, startY, paint);
        }
    }

    /**
     * ���ö��ߵ���ɫ
     * @param mTopLineColor
     */
    public void setmTopLineColor(int mTopLineColor) {
        this.mTopLineColor = mTopLineColor;
    }

    /**
     * ���õ��ߵ���ɫ
     * @param mBottomLineColor
     */
    public void setmBottomLineColor(int mBottomLineColor) {
        this.mBottomLineColor = mBottomLineColor;
    }

    /**
     * ������һ-�����ɫ
     * @return
     */
    public void setmWeedayColor(int mWeedayColor) {
        this.mWeedayColor = mWeedayColor;
    }

    /**
     * �������������յ���ɫ
     * @param mWeekendColor
     */
    public void setmWeekendColor(int mWeekendColor) {
        this.mWeekendColor = mWeekendColor;
    }

    /**
     * ���ñ��ߵĿ��
     * @param mStrokeWidth
     */
    public void setmStrokeWidth(int mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
    }


    /**
     * ��������Ĵ�С
     * @param mWeekSize
     */
    public void setmWeekSize(int mWeekSize) {
        this.mWeekSize = mWeekSize;
    }


    /**
     * �������ڵ���ʽ
     * @param weekString
     * Ĭ��ֵ  "��","һ","��","��","��","��","��"
     */
    public void setWeekString(String[] weekString) {
        this.weekString = weekString;
    }
}