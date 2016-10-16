package com.example.wsd_client.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * ��Ʒʵ���࣬��װ��Ʒ�б����ݼ��ϣ���Ʒ����
 * @author wsd_leiguoqiang
 */
public class ProductListItem {
	/**
	 * ��װproduct�ļ���
	 */
	private List<Product> result = new ArrayList<Product>();
	/**
	 * �����������ݷ�����Ʒ����
	 */
	private int total;
	
	public List<Product> getResult() {
		return result;
	}
	public void setResult(List<Product> result) {
		this.result = result;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
}

//����������json����
/**
 * {
    "status": {
        "status_code": 0,
        "status_reason": ""
    },
    "result": [
        {
            "YWM_Name": "PC32.5��װ",
            "YWM_Price": 260,
            "YWM_PicName": 1001,
            "YWM_ID": 1001,
            "YWM_Unit": "��",
            "YWM_Code": "SC20160608001",
            "YWM_Validate": "2019-01-01 01:20:02",
            "YWM_CDate": "2016-03-03 12:01:00",
            "YWM_Kind": "2001001"
        },
        {
            "YWM_Name": "PC42.5R��װ",
            "YWM_Price": 280.06,
            "YWM_PicName": 2001,
            "YWM_ID": 2001,
            "YWM_Unit": "��",
            "YWM_Code": "FD20160601001",
            "YWM_Validate": "2019-01-01 12:12:10",
            "YWM_CDate": "2016-02-02 10:02:03",
            "YWM_Kind": "2002001"
        },
        {
            "YWM_Name": "ɢװ42.5",
            "YWM_Price": 280.61,
            "YWM_PicName": 3001,
            "YWM_ID": 3001,
            "YWM_Unit": "��",
            "YWM_Code": "SZ20160101002",
            "YWM_Validate": "2030-10-10 01:20:01",
            "YWM_CDate": "2015-01-01 15:13:02",
            "YWM_Kind": "3001001"
        }
    ],
    "total": 3
}
 * */
