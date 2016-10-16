package com.example.wsd_client.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * ʵ���࣬���ڷ�װ������������������ݣ������������۸�ԭ�ϵ���Ϣ
 * @author wsd_leiguoqiang
 * ��Ҫ���ݷ��������ݽ�������----------------------------------------------------
 */
public class CartItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5625527151277786651L;
	/**
	 * ��Ʒ����
	 */
	private int product_count = 1;
	/**
	 * ��Ʒ����
	 */
	private Product product; 
	/**
	 * �������,������Ʒ����
	 */
	private Map<String, String> buyParam = new HashMap<String, String>();
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getProduct_count() {
		return product_count;
	}
	public void setProduct_count(int product_count) {
		this.product_count = product_count;
	}
	public Map<String, String> getBuyParam() {
		return buyParam;
	}
	public void setBuyParam(Map<String, String> buyParam) {
		this.buyParam = buyParam;
	}
	@Override
	public String toString() {
		return "CartItem [product_count=" + product_count + ", product="
				+ product.toString() + ", buyParam=" + buyParam.size() + "]";
	}
	
	
}
