package com.example.wsd_client.entity;

/**
 * ��װ��Ϣ���ݵ�ʵ����
 */
public class NewsResult {

	/**
	 * �µ�����
	 */
	private String InputDate;
	/**
	 * ��Ϣ���
	 */
	private int Status;
	
	private int InputOwner;
	private String SendPhone;
	/**
	 * �ͻ�id
	 */
	private int ClientID;
	private int DeptID;
	/**
	 * ��Ϣ���
	 */
	private int MessageID;
	/**
	 * ��Ϣ����
	 */
	private String MessageInfo;
	
	
	public String getInputDate() {
		return InputDate;
	}
	public void setInputDate(String inputDate) {
		InputDate = inputDate;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public int getInputOwner() {
		return InputOwner;
	}
	public void setInputOwner(int inputOwner) {
		InputOwner = inputOwner;
	}
	public String getSendPhone() {
		return SendPhone;
	}
	public void setSendPhone(String sendPhone) {
		SendPhone = sendPhone;
	}
	public int getClientID() {
		return ClientID;
	}
	public void setClientID(int clientID) {
		ClientID = clientID;
	}
	public int getDeptID() {
		return DeptID;
	}
	public void setDeptID(int deptID) {
		DeptID = deptID;
	}
	public int getMessageID() {
		return MessageID;
	}
	public void setMessageID(int messageID) {
		MessageID = messageID;
	}
	public String getMessageInfo() {
		return MessageInfo;
	}
	public void setMessageInfo(String messageInfo) {
		MessageInfo = messageInfo;
	}
	@Override
	public String toString() {
		return "NewsResult [InputDate=" + InputDate + ", Status=" + Status
				+ ", InputOwner=" + InputOwner + ", SendPhone=" + SendPhone
				+ ", ClientID=" + ClientID + ", DeptID=" + DeptID
				+ ", MessageID=" + MessageID + ", MessageInfo=" + MessageInfo
				+ "]";
	}
	
}
