package com.example.wsd_client.entity;

import java.io.Serializable;

/**
 * ��װ�û���Ϣ��ʵ����
 */
public class ClientInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * �û����id
	 */
	private int YWC_ClientID;
	/**
	 * �û�����
	 */
	private String YWC_ClientCode;
	/**
	 * �û�����
	 */
	private String YWC_ClientName;
	/**
	 * �û���ϵ��
	 */
	private String YWC_LinkMan;
	/**
	 * �绰����
	 */
	private String YWC_LinkPhone;
	/**
	 * ����
	 */
	private String YWC_PassWord;
	/**
	 * �˻���������
	 */
	private String YWC_CDate;
	/**
	 * �Ƿ��� Ч (0:�� Ч��1 ��Ч)
	 */
	private int YWC_Status; 
	/**
	 * �û�������Ա
	 */
	private int YWC_InputOwner;
	/**
	 * ��������
	 */
	private int YWC_DeptID;
	/**
	 * Ӫҵִ��
	 */
	private String YWC_CorpCert;
	/**
	 * ��������
	 */
	private String YWC_AreaCode;
	public int getYWC_ClientID() {
		return YWC_ClientID;
	}
	public void setYWC_ClientID(int yWC_ClientID) {
		YWC_ClientID = yWC_ClientID;
	}
	public String getYWC_ClientCode() {
		return YWC_ClientCode;
	}
	public void setYWC_ClientCode(String yWC_ClientCode) {
		YWC_ClientCode = yWC_ClientCode;
	}
	public String getYWC_ClientName() {
		return YWC_ClientName;
	}
	public void setYWC_ClientName(String yWC_ClientName) {
		YWC_ClientName = yWC_ClientName;
	}
	public String getYWC_LinkMan() {
		return YWC_LinkMan;
	}
	public void setYWC_LinkMan(String yWC_LinkMan) {
		YWC_LinkMan = yWC_LinkMan;
	}
	public String getYWC_LinkPhone() {
		return YWC_LinkPhone;
	}
	public void setYWC_LinkPhone(String yWC_LinkPhone) {
		YWC_LinkPhone = yWC_LinkPhone;
	}
	public String getYWC_PassWord() {
		return YWC_PassWord;
	}
	public void setYWC_PassWord(String yWC_PassWord) {
		YWC_PassWord = yWC_PassWord;
	}
	public String getYWC_CDate() {
		return YWC_CDate;
	}
	public void setYWC_CDate(String yWC_CDate) {
		YWC_CDate = yWC_CDate;
	}
	public int getYWC_Status() {
		return YWC_Status;
	}
	public void setYWC_Status(int yWC_Status) {
		YWC_Status = yWC_Status;
	}
	public int getYWC_InputOwner() {
		return YWC_InputOwner;
	}
	public void setYWC_InputOwner(int yWC_InputOwner) {
		YWC_InputOwner = yWC_InputOwner;
	}
	public int getYWC_DeptID() {
		return YWC_DeptID;
	}
	public void setYWC_DeptID(int yWC_DeptID) {
		YWC_DeptID = yWC_DeptID;
	}
	public String getYWC_CorpCert() {
		return YWC_CorpCert;
	}
	public void setYWC_CorpCert(String yWC_CorpCert) {
		YWC_CorpCert = yWC_CorpCert;
	}
	public String getYWC_AreaCode() {
		return YWC_AreaCode;
	}
	public void setYWC_AreaCode(String yWC_AreaCode) {
		YWC_AreaCode = yWC_AreaCode;
	}
}
