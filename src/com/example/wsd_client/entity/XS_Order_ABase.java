package com.example.wsd_client.entity;


import java.io.Serializable;



/**
 * ��������
 * �ֻ��µ����۱�
 * 
 * ��Ӧ�������ĵ�����
 * 
 * XSO_OrderID int ID ���(������) 
 * XSO_ClientID int �ͻ�  id
 * XSO_LadeID Varchar��30�� ������� 
 * XSO_SetDate datetime �µ�����
 * XSO_AreaCode Varchar��30���������� 
 * XSO_LadeType Varchar(3) �������� 
 * XSO_CementCode Varchar(30) ˮ��Ʒ�ִ���
 * XSO_CementName Varchar(50) ˮ��Ʒ������
 * XSO_Number Decimal(18,2) �µ����� 
 * XSO_Price Decimal(18,2) �µ��۸� 
 * XSO_TotalPrice Decimal(18,2) �µ���� 
 * XSO_CarName Varchar(30) ˾������
 * XSO_CarID Varchar(30) ˾����ʻ֤��
 * XSO_CarCode Varchar(10) ���䳵�� 
 * XSO_CarPhone Varchar(20) ˾���ֻ��� 
 * XSO_OrderPhone Varchar(20) �µ���ϵ���ֻ���
 * XSO_InputOwner Int  
 * XSO_DeptID Int  
 * XSO_Status int   0:���µ����ȴ���ˣ�
 * 					1 ����ˡ������
 * 					2 �Ѹ��
 * 					3 �ѽ�����
 * 					4 ����װ����
 * 					5 װ����ɣ�
 * 					9 �ѳ��� 
JSON 
 */
public class XS_Order_ABase implements Serializable{
	int XSO_OrderID ;//ID ���(������) 
	int XSO_ClientID ;//�ͻ� id
	String XSO_LadeID ;//������� 
	String XSO_SetDate  ;//�µ����� 
	String XSO_AreaCode ;// ��������
	String XSO_LadeType ;//�������� 
	String XSO_CementCode ;//ˮ��Ʒ�ִ��� 
	String XSO_CementName ;//ˮ��Ʒ������
	String XSO_Number ;//�µ����� 
	String XSO_Price ;//�µ��۸� 
	String XSO_TotalPrice ;//�µ����
	String XSO_CarName ;// ˾������ 
	String XSO_CarID ;//˾����ʻ֤��
	String XSO_CarCode ;//���䳵�� 
	String XSO_CarPhone ;//˾���ֻ��� 
	String XSO_OrderPhone ;//�µ���ϵ���ֻ���
	int XSO_InputOwner ;//
	int XSO_DeptID  ;//
	int XSO_Status ;//�µ�״̬��Ĭ�ϴ���0
	
	
	public XS_Order_ABase(){
		
	}
	
	public XS_Order_ABase(int xSO_OrderID, int xSO_ClientID, String xSO_LadeID,
			String xSO_SetDate, String xSO_AreaCode, String xSO_LadeType,
			String xSO_CementCode, String xSO_CementName, String xSO_Number,
			String xSO_Price, String xSO_TotalPrice, String xSO_CarName,
			String xSO_CarID, String xSO_CarCode, String xSO_CarPhone,
			String xSO_OrderPhone, int xSO_InputOwner, int xSO_DeptID,
			int xSO_Status) {
		super();
		XSO_OrderID = xSO_OrderID;
		XSO_ClientID = xSO_ClientID;
		XSO_LadeID = xSO_LadeID;
		XSO_SetDate = xSO_SetDate;
		XSO_AreaCode = xSO_AreaCode;
		XSO_LadeType = xSO_LadeType;
		XSO_CementCode = xSO_CementCode;
		XSO_CementName = xSO_CementName;
		XSO_Number = xSO_Number;
		XSO_Price = xSO_Price;
		XSO_TotalPrice = xSO_TotalPrice;
		XSO_CarName = xSO_CarName;
		XSO_CarID = xSO_CarID;
		XSO_CarCode = xSO_CarCode;
		XSO_CarPhone = xSO_CarPhone;
		XSO_OrderPhone = xSO_OrderPhone;
		XSO_InputOwner = xSO_InputOwner;
		XSO_DeptID = xSO_DeptID;
		XSO_Status = xSO_Status;
	}
	
	
	
	public int getXSO_OrderID() {
		return XSO_OrderID;
	}
	public void setXSO_OrderID(int xSO_OrderID) {
		XSO_OrderID = xSO_OrderID;
	}
	public int getXSO_ClientID() {
		return XSO_ClientID;
	}
	public void setXSO_ClientID(int xSO_ClientID) {
		XSO_ClientID = xSO_ClientID;
	}
	public String getXSO_LadeID() {
		return XSO_LadeID;
	}
	public void setXSO_LadeID(String xSO_LadeID) {
		XSO_LadeID = xSO_LadeID;
	}
	public String getXSO_SetDate() {
		return XSO_SetDate;
	}
	public void setXSO_SetDate(String xSO_SetDate) {
		XSO_SetDate = xSO_SetDate;
	}
	public String getXSO_AreaCode() {
		return XSO_AreaCode;
	}
	public void setXSO_AreaCode(String xSO_AreaCode) {
		XSO_AreaCode = xSO_AreaCode;
	}
	public String getXSO_LadeType() {
		return XSO_LadeType;
	}
	public void setXSO_LadeType(String xSO_LadeType) {
		XSO_LadeType = xSO_LadeType;
	}
	public String getXSO_CementCode() {
		return XSO_CementCode;
	}
	public void setXSO_CementCode(String xSO_CementCode) {
		XSO_CementCode = xSO_CementCode;
	}
	public String getXSO_CementName() {
		return XSO_CementName;
	}
	public void setXSO_CementName(String xSO_CementName) {
		XSO_CementName = xSO_CementName;
	}
	public String getXSO_Number() {
		return XSO_Number;
	}
	public void setXSO_Number(String xSO_Number) {
		XSO_Number = xSO_Number;
	}
	public String getXSO_Price() {
		return XSO_Price;
	}
	public void setXSO_Price(String xSO_Price) {
		XSO_Price = xSO_Price;
	}
	public String getXSO_TotalPrice() {
		return XSO_TotalPrice;
	}
	public void setXSO_TotalPrice(String xSO_TotalPrice) {
		XSO_TotalPrice = xSO_TotalPrice;
	}
	public String getXSO_CarName() {
		return XSO_CarName;
	}
	public void setXSO_CarName(String xSO_CarName) {
		XSO_CarName = xSO_CarName;
	}
	public String getXSO_CarID() {
		return XSO_CarID;
	}
	public void setXSO_CarID(String xSO_CarID) {
		XSO_CarID = xSO_CarID;
	}
	public String getXSO_CarCode() {
		return XSO_CarCode;
	}
	public void setXSO_CarCode(String xSO_CarCode) {
		XSO_CarCode = xSO_CarCode;
	}
	public String getXSO_CarPhone() {
		return XSO_CarPhone;
	}
	public void setXSO_CarPhone(String xSO_CarPhone) {
		XSO_CarPhone = xSO_CarPhone;
	}
	public String getXSO_OrderPhone() {
		return XSO_OrderPhone;
	}
	public void setXSO_OrderPhone(String xSO_OrderPhone) {
		XSO_OrderPhone = xSO_OrderPhone;
	}
	public int getXSO_InputOwner() {
		return XSO_InputOwner;
	}
	public void setXSO_InputOwner(int xSO_InputOwner) {
		XSO_InputOwner = xSO_InputOwner;
	}
	public int getXSO_DeptID() {
		return XSO_DeptID;
	}
	public void setXSO_DeptID(int xSO_DeptID) {
		XSO_DeptID = xSO_DeptID;
	}
	public int getXSO_Status() {
		return XSO_Status;
	}
	public void setXSO_Status(int xSO_Status) {
		XSO_Status = xSO_Status;
	}



	@Override
	public String toString() {
		return "XS_Order_ABase [XSO_OrderID=" + XSO_OrderID + ", XSO_ClientID="
				+ XSO_ClientID + ", XSO_LadeID=" + XSO_LadeID
				+ ", XSO_SetDate=" + XSO_SetDate + ", XSO_AreaCode="
				+ XSO_AreaCode + ", XSO_LadeType=" + XSO_LadeType
				+ ", XSO_CementCode=" + XSO_CementCode + ", XSO_CementName="
				+ XSO_CementName + ", XSO_Number=" + XSO_Number
				+ ", XSO_Price=" + XSO_Price + ", XSO_TotalPrice="
				+ XSO_TotalPrice + ", XSO_CarName=" + XSO_CarName
				+ ", XSO_CarID=" + XSO_CarID + ", XSO_CarCode=" + XSO_CarCode
				+ ", XSO_CarPhone=" + XSO_CarPhone + ", XSO_OrderPhone="
				+ XSO_OrderPhone + ", XSO_InputOwner=" + XSO_InputOwner
				+ ", XSO_DeptID=" + XSO_DeptID + ", XSO_Status=" + XSO_Status
				+ "]";
	}

	
	
	
	
}
