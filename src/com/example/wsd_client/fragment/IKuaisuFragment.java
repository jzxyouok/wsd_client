package com.example.wsd_client.fragment;

import java.util.List;

import com.example.wsd_client.entity.ClientInfo;
import com.example.wsd_client.entity.Product;

/**
 * �����µ�view��ӿڣ����ڻ�ȡmodel�㴫�ݵ��û���Ϣ���ݣ�����Ʒ����
 */
public interface IKuaisuFragment {

	/**
	 * view��������Ʒ���ݷ���
	 * @param list ��Ʒ���ݵļ���
	 */
	public void setDataProduct(List<Product> list);
	
	/**
	 * view�������û���Ϣ���ݷ���
	 */
	public void setDataUserInfo();
	
	/**
	 * view������û���Ϣ����Ʒ������Ϣ�ķ���
	 */
	public void showData();
}
