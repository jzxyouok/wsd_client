package com.example.wsd_client.modle;

import java.util.List;

import com.example.wsd_client.entity.CartItem;

/**
 * modle��ӿڣ������ڹ��ﳵҳ������ݼ���
 * @author wsd_leiguoqiang
 */
public interface IUploadCartDataModle {
	/**
	 * ���ع��ﳵҳ������
	 * @param path �������ӿ�·��
	 * @param callBack �ص�����
	 */
	public void uploadCartData(String path,CartDataCallBack callBack);
	/**
	 * ���ع��ﳵҳ������ʱ����õĻص��ӿ�
	 * @author wsd_leiguoqiang
	 */
	public interface CartDataCallBack{
		/**
		 * ���¹��ﳵ��������
		 * @param list ����������ݼ���
		 */
		public void updateCart(List<CartItem> list);
	}
}
