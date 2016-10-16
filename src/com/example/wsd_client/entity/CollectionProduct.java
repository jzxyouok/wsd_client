package com.example.wsd_client.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.constant.Constant;

/**
 * ʵ���ࣺ��װ�ղصĲ�Ʒ
 * @author wsd_leiguoqiang
 */
public class CollectionProduct implements Serializable{
	/**
	 * ���к�
	 */
	private static final long serialVersionUID = -3777180327718027332L;
	/**
	 * ��Ʒ���ݼ���
	 */
	private List<Product> list_products = new ArrayList<Product>();

	public List<Product> getList_products() {
		return list_products;
	}
	public void setList_products(List<Product> list_products) {
		this.list_products = list_products;
	}

	/**
	 * �Զ��巽������������
	 */
	public void saveCollectionProduct(){
		//���û���¼ʱ���ɽ��б��ػ��洢
		YW_ClientInfo clientInfo = ((Myapplication)(Myapplication.getContext())).getClientInfo();
		if(clientInfo!=null){
			int clientId = ((Myapplication)(Myapplication.getContext())).getClientInfo().getResult().get(0).getYWC_ClientID();
			//��ȡfile�ļ�����,�û�id�͹��ﳵ���ݽ���һһ��Ӧ
			File file = new File(Myapplication.getContext().getCacheDir(),clientId+Constant.COLLECTION_PRODUCT_CACHE_FILE_NAME);
			try {
				//��ȡ���л����������
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
				//����д�뱾��
				oos.writeObject(this);
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
	 * �Զ��巽������ȡ�ղ�����
	 * @return
	 */
	public CollectionProduct readCollectionnProduct(){
		YW_ClientInfo clientInfo = ((Myapplication)(Myapplication.getContext())).getClientInfo();
		if(clientInfo!=null){
			int clientId = ((Myapplication)(Myapplication.getContext())).getClientInfo().getResult().get(0).getYWC_ClientID();
			try {
				File file = new File(Myapplication.getContext().getCacheDir(), clientId+Constant.COLLECTION_PRODUCT_CACHE_FILE_NAME);
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				CollectionProduct collectionProduct = (CollectionProduct) ois.readObject();
				if(collectionProduct==null){
					ois.close();
					return new CollectionProduct();
				}
				ois.close();
				return collectionProduct;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new CollectionProduct();
	}
	/**
	 * �Զ��巽��������ղ���Ʒ,����޶�20��
	 */
	public void addCollectionProduct(Product product){
		//�õ���Ʒid
		int product_id = product.getYWM_ID();
		if(list_products.size()!=0){
			for(Product collectionProduct:list_products){
				if(collectionProduct.getYWM_ID()==product_id){
					Myapplication.toast("����Ʒ�Ѿ������ղؼ�");
					return;
				}
			}
			//��������ӵ�������
			list_products.add(0,product);
			//�������ݵ�����
			saveCollectionProduct();
		}else{
			//��������ӵ�������
			list_products.add(0,product);
			//�������ݵ�����
			saveCollectionProduct();
		}
		//�ж��Ƿ񳬹�20������
		if(list_products.size()>20){
			list_products.remove(list_products.size()-1);
		}
	}
	/**
	 * �Զ��巽����ɾ���ղؼ���ĳ����Ʒ
	 * @param product
	 */
	public void deleteCollectionProduct(Product product){
		//�õ���Ʒid
		int product_id = product.getYWM_ID();
			for(Product collectionProduct:list_products){
				if(collectionProduct.getYWM_ID()==product_id){
					list_products.remove(collectionProduct);
				}
			}
			//�������ݵ�����
			saveCollectionProduct();
	}
	/**
	 * �Զ��巽�����ж��Ƿ��Ѿ������ղؼ�����
	 * @param product
	 * @return
	 */
	public boolean existCollectioin(Product product){
		int product_id = product.getYWM_ID();
		for(Product collectionProduct:list_products){
			if(product_id==collectionProduct.getYWM_ID()){
				return true;
			}
		}
		return false;
	}
}
