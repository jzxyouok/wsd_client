package com.example.wsd_client.entity;


/**
 * ʵ���ࣺ�����������������󷵻ص�����
 * @author wsd_leiguoqiang
 */
public class BuyReponse {
	/**
	 * ���ص���������
	 */
	private String total;
	/**
	 * ���ؽ���ַ���
	 */
	private String result;
	/**
	 * ����״̬ԭ��
	 */
	private String reason;
	
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

}