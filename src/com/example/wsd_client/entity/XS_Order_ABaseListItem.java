package com.example.wsd_client.entity;

import java.util.ArrayList;
import java.util.List;

public class XS_Order_ABaseListItem {
	/**
	 * ��װXS_Order_ABase�ļ���
	 */
	private List<XS_Order_ABase> result = new ArrayList<XS_Order_ABase>();
	
	/**
	 * �����������ݷ�������
	 */
	private int total;
	

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public XS_Order_ABaseListItem(List<XS_Order_ABase> result) {
		super();
		this.result = result;
	}

	public List<XS_Order_ABase> getResult() {
		return result;
	}

	public void setResult(List<XS_Order_ABase> result) {
		this.result = result;
	}

	
	
	
	
}
