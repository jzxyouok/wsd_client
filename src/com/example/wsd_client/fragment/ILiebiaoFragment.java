package com.example.wsd_client.fragment;

import java.util.List;

import android.content.Intent;

import com.example.wsd_client.activity.HomeActivity;
import com.example.wsd_client.entity.Product;

/**
 * ��Ʒ�б�view��ӿڣ����ڹ淶���ݳ���ʹ��
 * @author wsd_leiguoqiang
 */
public interface ILiebiaoFragment {
	/**
	 * view���������ݷ���
	 * @param list�����ݼ���
	 */
	public void setData(List<Product> list);
	/**
	 * view��������ݷ���
	 */
	public void showData();
	/**
	 * ����������ʱ���õķ���
	 */
	public void buyCompleted();
		
}
