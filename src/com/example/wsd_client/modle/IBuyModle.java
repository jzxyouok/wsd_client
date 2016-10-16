package com.example.wsd_client.modle;

import java.util.List;
import java.util.Map;

import com.example.wsd_client.entity.CartItem;

/**
 * ����modle��ӿ�
 * @author wsd_leiguoqiang
 */
public interface IBuyModle {
	/**
	 * �˷������ڴӹ��ﳵҳ����в�Ʒ�����������
	 * ���򷽷�������֮��Ҫ�����ﳵ�е���Ʒ�Ƴ�����������ת����������ҳ��
	 * �������Ʒ����ҳ������Ҫ
	 * @param listBuyParam:�������,���ǵ�һ��ͬ�¹�������Ʒ
	 * @param callBack:�ص��ӿ�
	 * @param list_YWM_ID:��Ʒid��
	 */
	public void buyFromCart(List<Map<String, String>> listBuyParam,BuyModleCallBack callBack,List<String> list_YWM_ID);
	/**
	 * �˷������ڴ���Ʒ����ҳ����в�Ʒ�������
	 * @param buyParam:�������
	 * @param callBack:�ص��ӿ�
	 * @param productId:��Ʒid���
	 */
	public void buyFromProductDetials(Map<String, String> buyParam,BuyModleCallBack callBack);

	/**
	 * ����ص��ӿ�
	 * @author wsd_leiguoqiang
	 */
	public interface BuyModleCallBack{
		/**
		 * 1)����Ǵӹ��ﳵҳ����й�����������¹��ﳵ���ݣ���ת����������ҳ��
		 * 2)����Ǵ���Ʒ����ҳ����й����������ת����������ҳ��
		 * @param productId:��Ʒid��ţ�����Ʒ����ҳ����й������Ĭ��ֵΪ0����
		 * @param index_flag:��Ǳ���,����Ʒ����ҳ����й������Ĭ��ֵΪ1����
		 */
		public void updateAndToBuyCenter(String productId,int index_flag);
	}
}
