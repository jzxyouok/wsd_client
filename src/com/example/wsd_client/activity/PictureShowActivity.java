package com.example.wsd_client.activity;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

import com.example.wsd_client.R;
import com.example.wsd_client.application.Myapplication;

/**
 * ͼƬչʾActivity�������ڵ�����Ʒ����ͼƬչʾ������ͼƬչʾ
 * @author wsd_leiguoqiang
 */
public class PictureShowActivity extends BaseActivity implements ViewFactory,OnTouchListener{

	/**
	 * imageSwitcher����
	 */
	@ViewInject(R.id.is_picture_show_pager)
	private ImageSwitcher is;

	/**
	 * ���ư���ȥ����ʼxλ��
	 */
	private int downX = 0;
	private int downY = 0;
	/**
	 * ���ư���ȥ�Ľ���xλ��
	 */
	private int upX = 0;
	private int upY = 0;
	/**
	 * imageSwitcher��ʾ��ͼƬ��Դ���
	 */
	private int index_imageSwitcher = 0;

	/**
	 * ͼƬ��Դ
	 */
	private int[] imageArray = new int[]{R.drawable.home_vp1,R.drawable.home_vp2,R.drawable.home_vp3
			,R.drawable.home_vp1,R.drawable.home_vp2,R.drawable.home_vp3};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture_show);
		x.view().inject(this);
		init();
		initListeners();
	}


	private void init() {
		Intent intent = getIntent();
		index_imageSwitcher = intent.getIntExtra("position", 0);
		//ΪimageSwitcher�ؼ�����viewFactory
		is.setFactory(this);
		//����Ĭ��ͼƬ��Դ������ʾ
		is.setImageResource(imageArray[index_imageSwitcher]);
	}

	private void initListeners() {
		is.setOnTouchListener(this);
	}

	/**
	 * iamgeSwitcher�ؼ�����ʵ�ֵ��࣬Ϊ�����ֿ��ؼ���ͼƬ
	 */
	@Override
	public View makeView() {
		return new ImageView(this);
	}

	/**
	 * ontouchLietener��������ʵ�ַ���
	 * @param v
	 * @param event
	 * @return
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		//imageSwitcher�ؼ�����
		case R.id.is_picture_show_pager:
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				downX = (int)event.getX();
				downY = (int) event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				//�����ƶ�
				if(event.getX()>downX){
					TranslateAnimation animation_in = new TranslateAnimation(-((float)(is.getWidth())), ((event.getX()-downX)-((float)(is.getWidth()))), (float)(is.getTop()), (float)(is.getTop()));
					TranslateAnimation animation_out = new TranslateAnimation(0f, event.getX(), (float)(is.getTop()), (float)(is.getTop()));
					is.setInAnimation(animation_in);
					is.setOutAnimation(animation_out);
				}else{
					TranslateAnimation animation_in = new TranslateAnimation((float)(is.getWidth()), ((float)(is.getWidth())-(downX-event.getX())), (float)(is.getTop()), (float)(is.getTop()));
					TranslateAnimation animation_out = new TranslateAnimation(0f, -(downX-event.getX()), (float)(is.getTop()), (float)(is.getTop()));
					is.setInAnimation(animation_in);
					is.setOutAnimation(animation_out);
				}
				break;
			case MotionEvent.ACTION_UP:
				upX = (int) event.getX();
				upY = (int) event.getY();
				//�ж����ƶ�
				if((downX-upX)>100){
					index_imageSwitcher--;
					if(index_imageSwitcher<0){
						index_imageSwitcher = imageArray.length-1;
					}
					//������Ӷ�������
					//ǰһ����߳�ȥ���ұ�һ�Ž���
					//					TranslateAnimation animation_out = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0, Animation.RELATIVE_TO_SELF,-(is.getWidth()), 
					//							Animation.RELATIVE_TO_SELF,0, Animation.RELATIVE_TO_SELF,0);
					//					animation_out.setDuration(1000);
					//					is.setOutAnimation(animation_out);
					//					TranslateAnimation animation_in = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -(is.getWidth()), Animation.RELATIVE_TO_SELF, 0,
					//							Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
					//					animation_in.setDuration(1000);
					//					is.setInAnimation(animation_in);
					is.setInAnimation(AnimationUtils.loadAnimation(PictureShowActivity.this, R.anim.animation_left_out));
					is.setOutAnimation(AnimationUtils.loadAnimation(PictureShowActivity.this, R.anim.animation_right_in));
					is.setImageResource(imageArray[index_imageSwitcher]);
					Myapplication.log("��������", (downX-upX)+"ͼƬ��Դ"+index_imageSwitcher);
					//�ж����ƶ�
				}else if((upX-downX)>100){
					index_imageSwitcher++;
					if(index_imageSwitcher>(imageArray.length-1)){
						index_imageSwitcher = 0;
					}
					is.setInAnimation(AnimationUtils.loadAnimation(PictureShowActivity.this, R.anim.animation_left_in));
					is.setOutAnimation(AnimationUtils.loadAnimation(PictureShowActivity.this, R.anim.animation_right_out));
					is.setImageResource(imageArray[index_imageSwitcher]);
					Myapplication.log("��������", (upX-downX)+"ͼƬ��Դ"+index_imageSwitcher);
					//�����ʼ�ͽ���λ����ͬһ���ص㣬˵��ʱ�������
				}else if((downX==upX)&&(downY==upY)){
					finish();
				}
				break;
			}
			break;
		}
		return true;
	}
}
