package com.example.wsd_client.modle.impl;

import java.util.ArrayList;
import java.util.List;

import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.Cart;
import com.example.wsd_client.entity.CartItem;
import com.example.wsd_client.modle.IUploadCartDataModle;

/**
 * modle��ʵ���࣬�����ڹ��ﳵ���ݳ���ҳ��
 * @author wsd_leiguoqiang
 */
public class UploadCartDataModleImpl implements IUploadCartDataModle{

	@Override
	public void uploadCartData(String path, CartDataCallBack callBack) {
		Myapplication app = (Myapplication) Myapplication.getContext();
		//���ػ���ȡ����
		Cart cart = app.getCart();
		//�������ݼ���
		List<CartItem> list = new ArrayList<CartItem>();
		list = cart.getList_cartItem();
		Myapplication.log("���ﳵmodle��", list.toString());
		//���ûص��������view�����ݸ���
		callBack.updateCart(list);
	}

}
