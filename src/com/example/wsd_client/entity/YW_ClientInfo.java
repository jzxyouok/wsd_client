package com.example.wsd_client.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ʵ���ࣺ��װ�û���Ϣ,���ҽ������л�
 * @author wsd_leiguoqiang
 */
public class YW_ClientInfo implements Serializable{

	/**
	 * �汾��
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * �û������ϼ�
	 */
	private int total;

	/**
	 * ��װ�û���ϸ��Ϣ�ļ���
	 */
	private List<ClientInfo> result =new ArrayList<ClientInfo>();


	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}


	public List<ClientInfo> getResult() {
		return result;
	}

	public void setResult(List<ClientInfo> result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "YW_ClientInfo [total=" + total + ", lists=" + result + "]";
	}
	
	/**
	 * �Զ�������û���Ϣ
	 */
	public void deleteClientInfo(){
		setTotal(0);
		setResult(new ArrayList<ClientInfo>());
	}
}

/***
 * YWC_ClientID int ID ��� 
 * YWC_ClientCode Varchar��20�� �ͻ����� 
 * YWC_ClientName Varchar��60�� �ͻ����� 
 * YWC_LinkMan Varchar��20�� ��ϵ�� 
 * YWC_LinkPhone Varchar��20�� �绰 
 * YWC_PassWord Varchar(50) ���� 
 * YWC_CDate datetime �ʻ���������
 * YWC_Status int �Ƿ��� Ч (0:�� Ч��1 ��Ч) 
 * YWC_InputOwner int ������Ա 
 * YWC_DeptID Int �������� 
 * YWC_CorpCert Varchar(30) ��ҵӪҵִ�� 
 * YWC_AreaCode Varchar(10) ��������
 */
