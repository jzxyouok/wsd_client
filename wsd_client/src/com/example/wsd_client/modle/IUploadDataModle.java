package com.example.wsd_client.modle;

import java.util.Map;

/**
 * ��Ʒ�ϴ�modle��ӿڣ�������Ʒ�����ϴ�ҵ��
 * @author wsd_leiguoqiang
 */
public interface IUploadDataModle {
	/**
	 * modle�㶨��ķ����������ϴ���Ʒ����
	 * ����http���������е�post���󷽷�
	 * @param urlPath ���������ʽӿڵ�ַ
	 * @param hashMap map���ϣ����ڷ�װpost����������Я���Ĳ���
	 * @return boolean�������ݣ������ж������ϴ� �ɹ�����ʧ��
	 */
	public boolean uploadDataByPost(String urlPath,Map<String,String> hashMap);
}
 