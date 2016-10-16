package com.example.wsd_client.modle;

import java.util.Map;

import com.example.wsd_client.entity.ClientInfo;

/**
 * ����fragmentҳ��modle��ӿڣ������û����������޸ĵ�ҵ�����
 * @author wsd_leiguoqiang
 */
public interface IMoreFragmentModle {
	/**
	 * ��������������󣬽����û������޸Ĳ���
	 * @param userInfo���û���Ϣ��Ҫ�޸ĵ����ݼ���
	 */
	public void updateUserInfo(Map<String, String> userInfo,MoreFragmentCallBack callBack);
	/**
	 * moreFragmentҳ��ص��ӿ�
	 * @author wsd_leiguoqiang
	 */
	public interface MoreFragmentCallBack{
		/**
		 * ����view�����ݸ���ʹ��
		 * @param clientInfo:���������ص��û���Ϣ����
		 */
		public void updateMoreFragment(ClientInfo clientInfo);
	}
}
