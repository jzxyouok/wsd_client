package com.example.wsd_client.presenter.impl;

import java.util.List;
import java.util.Map;

import com.example.wsd_client.activity.CollectionActivity;
import com.example.wsd_client.activity.ProductDetailsPagerActivityImpl;
import com.example.wsd_client.fragment.ILiebiaoFragment;
import com.example.wsd_client.fragment.ProductDetailsPagerProductFragment;
import com.example.wsd_client.fragment.impl.LiebiaoFragment;
import com.example.wsd_client.fragment.impl.UploadCartDataViewImpl;
import com.example.wsd_client.modle.IBuyModle;
import com.example.wsd_client.modle.IBuyModle.BuyModleCallBack;
import com.example.wsd_client.modle.impl.BuyModleImpl;
import com.example.wsd_client.presenter.IBuyPresenter;
/**
 * �������presenter�ӿ�ʵ����
 * @author wsd_leiguoqiang
 */
public class BuyPresenterImpl implements IBuyPresenter{
	/**
	 * ���ﳵҳ��ʵ����
	 */
	private UploadCartDataViewImpl cartView;
	/**
	 * ���ﳵҳ�湺���������
	 */
	private List<Map<String, String>> list_buyParams;
	/**
	 * ��Ʒid��ż���
	 */
	private List<String> list_productIds;


	/**
	 * ��Ʒ����ҳ��ʵ����
	 */
	private ProductDetailsPagerActivityImpl producView;
	/**
	 * ��Ʒ����ҳ�湺�����
	 */
	private Map<String, String> buyParam;

	/**
	 * ��Ʒ�б�ҳ��
	 */
	private ILiebiaoFragment productItemView;
	/**
	 * �ղؼ�ҳ��
	 */
	private CollectionActivity collectionActivity;

	/**
	 * �������modle����
	 */
	private IBuyModle buyModle;

	/**
	 * �ӹ��ﳵҳ����й������ʱʹ��
	 * @param cartView
	 * @param list_buyParams
	 * @param list_productIds
	 * @param buyModle
	 */
	public BuyPresenterImpl(UploadCartDataViewImpl cartView,
			List<Map<String, String>> list_buyParams,
			List<String> list_productIds) {
		super();
		this.cartView = cartView;
		this.list_buyParams = list_buyParams;
		this.list_productIds = list_productIds;
		this.buyModle = new BuyModleImpl();
	}

	/**
	 * ����Ʒ����ҳ����й��������ʹ��
	 * @param producView
	 * @param buyParam
	 * @param buyModle
	 */
	public BuyPresenterImpl(ProductDetailsPagerActivityImpl producView,
			Map<String, String> buyParam) {
		super();
		this.producView = producView;
		this.buyParam = buyParam;
		this.buyModle = new BuyModleImpl();
	}

	/**
	 * ����Ʒ�б� ҳ����й������
	 * @param buyParam�������������
	 * @param productItemView����Ʒ�б�view
	 */
	public BuyPresenterImpl(Map<String, String> buyParam,
			ILiebiaoFragment productItemView) {
		super();
		this.buyParam = buyParam;
		this.productItemView = productItemView;
		this.buyModle = new BuyModleImpl();
	}

	/**
	 * ���ղؼ�ҳ����й������
	 * @param buyParam�������������
	 * @param productItemView����Ʒ�б�view
	 */
	public BuyPresenterImpl(Map<String, String> buyParam,
			CollectionActivity collectionActivity) {
		super();
		this.buyParam = buyParam;
		this.collectionActivity = collectionActivity;
		this.buyModle = new BuyModleImpl();
	}

	@Override
	public void buyFromCart() {
		buyModle.buyFromCart(list_buyParams, new BuyModleCallBack() {

			@Override
			public void updateAndToBuyCenter(String productId,int index_flag) {
				cartView.buyCompleted(productId,index_flag);
			}
		}, list_productIds);

	}

	@Override
	public void buyFromProductDetails() {
		buyModle.buyFromProductDetials(buyParam, new BuyModleCallBack() {

			@Override
			public void updateAndToBuyCenter(String productId,int index_flag) {
				//��Ʒ����ҳ����лص�ʱ������Ҫ����
				producView.buyCompleted();
			}
		});
	}

	@Override
	public void buyFromProductItem() {
		buyModle.buyFromProductDetials(buyParam, new BuyModleCallBack() {

			@Override
			public void updateAndToBuyCenter(String productId, int index_flag) {
				//����Ʒ�б�ҳ�湺��ʱ����Ҫ����
				productItemView.buyCompleted();
			}
		});
	}

	@Override
	public void buyFromCollection() {
		buyModle.buyFromProductDetials(buyParam, new BuyModleCallBack() {

			@Override
			public void updateAndToBuyCenter(String productId, int index_flag) {
				//���ղؼ�ҳ�湺��ʱ����Ҫ����
				collectionActivity.buyCompleted();
			}
		});
	}
}
