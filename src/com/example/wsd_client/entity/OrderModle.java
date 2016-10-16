package com.example.wsd_client.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.constant.Constant;

/**
 * ʵ���ࣺ ����ģʽʵ����
 * @author wsd_leiguoqiang
 */
public class OrderModle implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6140386255451557481L;
	/**
	 * ����ģʽ���ݼ���
	 */
	private List<OrderModleInfo> list_orderModleInfo = new ArrayList<OrderModleInfo>();

	public List<OrderModleInfo> getList_orderModleInfo() {
		return list_orderModleInfo;
	}

	public void setList_orderModleInfo(List<OrderModleInfo> list_orderModleInfo) {
		this.list_orderModleInfo = list_orderModleInfo;
	}

	/**
	 * ����һ������ģʽ����
	 */
	public void addOrderModleInfo(OrderModleInfo orderinfo){
		String orderName = orderinfo.getOrderModleName();
		for(OrderModleInfo order:list_orderModleInfo){
			if(orderName.equals(order.getOrderModleName())){
				Myapplication.toast("�������Ѵ��ڣ�������");
				return;
			}
		}
		//��������
		list_orderModleInfo.add(orderinfo);
		saveOrderModle();
		Myapplication.toast("����ģʽ��ӳɹ�");
	}

	/**
	 * ���涩��ģʽ����
	 * �����û�id�������ݰ�
	 */
	public void saveOrderModle(){
		ClientInfo clientInfo = ((Myapplication) Myapplication.getContext()).getClientInfo().getResult().get(0);
		if(clientInfo!=null){
			try {
				int clientID = clientInfo.getYWC_ClientID();
				//��ȡfile����
				File file = new File(((Myapplication) Myapplication.getContext()).getCacheDir(), clientID+Constant.ORDER_MODLE_CACHE_FILE_NAME);
				//��ȡ���������
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
				//���ļ�д��orderModle����
				oos.writeObject(this);
				//��ˢ����
				oos.flush();
				oos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ��ȡ����ģʽ����
	 * @return OrderModle����
	 */
	public OrderModle readOrderModle(){
		ClientInfo clientInfo = ((Myapplication) Myapplication.getContext()).getClientInfo().getResult().get(0);
		if(clientInfo!=null){
			int clientID = clientInfo.getYWC_ClientID();
			File file = new File(Myapplication.getContext().getCacheDir(), clientID+Constant.ORDER_MODLE_CACHE_FILE_NAME);
			ObjectInputStream ois = null;
			try {
				ois = new ObjectInputStream(new FileInputStream(file));
			} catch (StreamCorruptedException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			OrderModle orderModle = null;
			if(ois!=null){
				try {
					orderModle = (OrderModle) ois.readObject();
				} catch (OptionalDataException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if(orderModle==null){
					try {
						ois.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return new OrderModle();
				}
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Myapplication.log("ordermodleʵ��ҳ��------��ȡ�ɹ�", orderModle.toString());
				return orderModle;
			}
		}
		return new OrderModle();
	}

	@Override
	public String toString() {
		return "OrderModle [list_orderModleInfo=" + list_orderModleInfo.toString() + "]";
	}
}
