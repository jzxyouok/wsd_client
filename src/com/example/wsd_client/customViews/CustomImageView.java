package com.example.wsd_client.customViews;

import com.example.wsd_client.application.Myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class CustomImageView extends ImageView{
	private int downX = 0;
	private int downY = 0;
	private OnClickListener onClickListener = null;

	public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomImageView(Context context) {
		super(context);
	}

	public CustomImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setClickable(boolean clickable) {
		super.setClickable(true);
	}
	/**
	 * ��д�÷��������ڻ���������ͻ����
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			getParent().requestDisallowInterceptTouchEvent(true);
			Myapplication.log("customImageView��ʼ��ʼ���", "���ɹ����");
			downX = (int) event.getX();
			downY = (int) event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			getParent().requestDisallowInterceptTouchEvent(true);
			break;
		case MotionEvent.ACTION_UP:
			getParent().requestDisallowInterceptTouchEvent(true);
			Myapplication.log("customImageView���������Ƿ�һ��", "downX��"+downX+"event.getX()"+event.getX());
			if(downX==(int)(event.getX())&&downY==(int)(event.getY())){
				//�����Ҫ��������
				if(onClickListener!=null){
					onClickListener.onClick(this);
					Myapplication.log("customImageView�������", "���ɹ����");
					return true;
				}
			}else{
				return false;
			}
			break;

		}
		Myapplication.log("customImageView�������", "���²���");
		return false;

	}

	public void setOnClickListener(OnClickListener onClickListener){
		this.onClickListener = onClickListener;
	}
	/**
	 * imageveiw�Զ�����������
	 * @author wsd_leiguoqiang
	 * v,�Զ����������
	 */
	public interface OnClickListener{
		/**
		 * 
		 * @param v:�ؼ��������
		 */
		public void onClick(View v);
	} 
}
