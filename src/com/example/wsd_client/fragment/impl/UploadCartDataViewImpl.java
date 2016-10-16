package com.example.wsd_client.fragment.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.wsd_client.R;
import com.example.wsd_client.activity.HomeActivity;
import com.example.wsd_client.activity.MainActivity;
import com.example.wsd_client.activity.ProductDetailsPagerActivityImpl;
import com.example.wsd_client.adapter.CartAdapter;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.customViews.CustomEnableLinearLayout;
import com.example.wsd_client.entity.Cart;
import com.example.wsd_client.entity.CartItem;
import com.example.wsd_client.entity.ClientInfo;
import com.example.wsd_client.entity.OrderModle;
import com.example.wsd_client.entity.OrderModleInfo;
import com.example.wsd_client.fragment.IUploadCartDataView;
import com.example.wsd_client.presenter.impl.BuyPresenterImpl;
import com.example.wsd_client.presenter.impl.UploadCartDataPresenterImpl;
/**
 * view��ʵ���࣬�����ڹ���ҳ�����ݵĳ���
 * @author wsd_leiguoqiang
 */
public class UploadCartDataViewImpl extends Fragment implements IUploadCartDataView ,OnClickListener , OnItemClickListener{
	/**
	 * ���������ݼ���
	 */
	private List<CartItem> list_cartItem;
	/**
	 * ���ﳵ���㼯�ϣ������ܼ۵ļ���
	 */
	private List<CartItem> list_total_cartItem;

	/**
	 * listview,���ֹ���������
	 */
	@ViewInject(R.id.lv_fragment_cart_pager_cartitems)
	private ListView lv_cartItem;
	/**
	 * view����
	 */
	private View view;
	/** 
	 * presenter����
	 */
	private UploadCartDataPresenterImpl presenter;
	/**
	 * ���ﳵ�������ӿ�
	 */
	private String path;
	/**
	 * �༭�ı���ť
	 */
	@ViewInject(R.id.tv_fragment_cart_pager_edite_button)
	private TextView tv_edite;
	/**
	 * ȫѡ�ı���ť
	 */
	@ViewInject(R.id.tv_fragment_cart_pager_seclete_all)
	private TextView tv_select_all;
	/**
	 * ��Ǳ�����������ȫѡ�ı���ť
	 */
	private boolean flag_select_all = false;
	/**
	 * ���ﳵ������
	 */
	private CartAdapter adapter;
	/**
	 * �ܼ۸��ı�
	 */
	private TextView tv_total_price;
	/**
	 * ���㹺���������
	 */
	private TextView tv_total_cartItem_count;
	/**
	 * �������й������Ʒid��ż���
	 */
	private List<String> list_productIds = new ArrayList<String>();
	/**
	 * �����ı���ť
	 */
	@ViewInject(R.id.tv_fragment_cart_pager_total_button)
	private TextView tv_buy;
	/**
	 * �������presenter����
	 */
	private BuyPresenterImpl buyPresenter;
	/**
	 * �����������
	 */
	private List<Map<String, String>> list_buyParams = new ArrayList<Map<String,String>>();
	/**
	 * ���ﳵ����
	 */
	private Cart cart;
	/**
	 * application����
	 */
	private Myapplication app;
	/**
	 * ����������ʱ����ʾ����
	 */
	@ViewInject(R.id.ll_fragment_cart_pager_unlinked)
	private CustomEnableLinearLayout ll_unlinked;
	/**
	 * ���¼����ı���ť
	 */
	@ViewInject(R.id.tv_fragment_cart_pager_linked_argin)
	private TextView tv_linked_argin;
	/**
	 * ���ﳵ�ٴγ��ֱ�Ǳ���
	 */
	private boolean flag_data_argin = false;
	/**
	 * ˾������
	 */
	@ViewInject(R.id.et_fragment_cart_pager_driver_name)
	private EditText et_driver_name;
	/**
	 * ˾����ʻ֤����
	 */
	@ViewInject(R.id.et_fragment_cart_pager_driver_number)
	private EditText et_driver_number;
	/**
	 * ��������
	 */
	@ViewInject(R.id.et_fragment_cart_pager_dingdan_type)
	private EditText et_dingdan_type;
	/**
	 * �����ƺ�
	 */
	@ViewInject(R.id.et_fragment_cart_pager_cart_number)
	private EditText et_cart_number;
	/**
	 * ˾���绰����
	 */
	@ViewInject(R.id.et_fragment_cart_pager_dirver_phone)
	private EditText et_dirver_phone;
	/**
	 * �µ��˵ĵ绰����
	 */
	@ViewInject(R.id.et_fragment_cart_pager_buy_phone)
	private EditText et_buy_phone;

	/**
	 * ����ģʽ�������µ�ʹ��
	 */
	@ViewInject(R.id.ll_fragment_cart_pager_dingdan_my_modle)
	private LinearLayout ll_order_modle;
	/**
	 * ����ѡ���ı���ť
	 */
	@ViewInject(R.id.tv_fragment_cart_pager_selecte_dingdan)
	private TextView tv_selecte_dinddan_modle;
	/**
	 * ��������
	 */
	@ViewInject(R.id.tv_fragment_cart_pager_to_buy_count_display)
	private TextView tv_product_count;
	/**
	 * �����ܼ�
	 */
	@ViewInject(R.id.tv_fragment_cart_pager_to_buy_total_price)
	private TextView tv_one_total_price;
	/**
	 * ���������±�ֵ
	 */
	private int position ;
	/**
	 * ȫ�ֶ���ģʽ���ݶ���
	 */
	private OrderModle orderModle;
	/**
	 * �û�����ģʽ��ϸ��Ϣ����
	 */
	private List<OrderModleInfo> list_orderModleInfo;
	/**
	 * popupwindown����
	 */
	private PopupWindow popupwindown;
	/**
	 * ��һ������
	 */
	@ViewInject(R.id.ll_fragment_cart_pager_first)
	public CustomEnableLinearLayout ll_first;
	/**
	 * �ڶ�������
	 */
	@ViewInject(R.id.ll_fragment_cart_pager_seconde)
	private CustomEnableLinearLayout ll_seconde;
	/**
	 * ��Ʒ����  
	 */
	@ViewInject(R.id.tv_fragment_cart_pager_to_buy_price)
	private TextView tv_price;
	/**
	 * �ڶ�������ȷ�ϰ�ť
	 */
	@ViewInject(R.id.btn_fragment_cart_pager_seconde_confirm)
	private Button btn_confirm;
	/**
	 * �ڶ�������͸������
	 */
	@ViewInject(R.id.ll_fragment_cart_pager_seconde_head)
	private LinearLayout ll_seconde_head;
	/**
	 * �ڶ�������ɾ����ť
	 */
	@ViewInject(R.id.iv_fragment_cart_pager_to_buy_delete)
	private ImageView iv_seconde_delete;
	/**
	 * ��Ʒ���Ӱ�ť
	 */
	@ViewInject(R.id.iv_fragment_cart_pager_to_buy_product_add)
	private ImageView tv_product_add;
	/**
	 * ��Ʒ���ٰ�ť
	 */
	@ViewInject(R.id.iv_fragment_cart_pager_to_buy_product_reduce)
	private ImageView tv_product_reduce;
	/**
	 * �ڶ�������������Ʒ���ܼ�
	 */
	@ViewInject(R.id.tv_fragment_cart_pager_to_buy_total_price)
	private TextView tv_sigle_total_price;
	/**
	 * ����ģʽ��ʾ��ť
	 */
	@ViewInject(R.id.tv_fragment_cart_pager_selecte_dingdan)
	private TextView tv_order_modle;
	/**
	 * �������ҳ����Ʒ����
	 */
	@ViewInject(R.id.tv_fragment_cart_pager_to_buy_product_name)
	private TextView tv_seconde_product_name;
	/**
	 * ����ԭʼ���ݼ���
	 */
	private List<CartItem> list_old_cartItem = new ArrayList<CartItem>();
	/**
	 * dialog�������ڼ�������ʱ���û�����ʱ��ʹ��
	 */
	private AlertDialog dialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(getActivity(), R.layout.fragment_cart, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		x.view().inject(this, view);
		init();
		initListeners();
	}

	/**
	 * ��ʼ������
	 */
	private void init() {
		//��ʾ���ݼ��ض���
//		loadingData("����Ŭ��������");
		app = (Myapplication) Myapplication.getContext();
		//���ع��ﳵͼ��
		((MainActivity) getActivity()).getTv_cart_acount().setVisibility(View.INVISIBLE);
		app.setNew_cart_data(false);
		orderModle = app.getOrderModle();
		cart = app.getCart();
		tv_total_cartItem_count = (TextView) view.findViewById(R.id.tv_fragment_cart_pager_total_button);
		list_total_cartItem = new ArrayList<CartItem>();
		lv_cartItem = (ListView) view.findViewById(R.id.lv_fragment_cart_pager_cartitems);
		tv_total_price = (TextView) view.findViewById(R.id.tv_fragment_cart_pager_total);
		//����presenter����
		presenter = new UploadCartDataPresenterImpl(this, path);
		//���й��ﳵ���ݼ���
		presenter.uploadCartData(path);
	}

	/**
	 * ���ü�����
	 */
	private void initListeners() {
		tv_select_all.setOnClickListener(this);
		tv_edite.setOnClickListener(this);
		tv_total_cartItem_count.setOnClickListener(this);
		lv_cartItem.setOnItemClickListener(this);
		tv_buy.setOnClickListener(this);
		tv_linked_argin.setOnClickListener(this);
		ll_order_modle.setOnClickListener(this);
		btn_confirm.setOnClickListener(this);
		iv_seconde_delete.setOnClickListener(this);
		ll_seconde_head.setOnClickListener(this);
		tv_product_add.setOnClickListener(this);
		tv_product_reduce.setOnClickListener(this);
	}

	@Override
	public void setData(List<CartItem> list) {
		list_cartItem = list;
		adapter = new CartAdapter(list_cartItem, list_total_cartItem,this, 
				lv_cartItem,tv_total_price , tv_total_cartItem_count,tv_select_all);
	}

	@Override
	public void setAdapter() {
		lv_cartItem.setAdapter(adapter);
		//�رռ��ض���
//		loadDataCompleted();
		//�Ƿ���ʾ������״̬
		displayUnlinked();
	}

	/**
	 * ����¼�����
	 * @param v
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		//ȫѡ�ı���ť���� 
		case R.id.tv_fragment_cart_pager_seclete_all:
			//��ս��㼯���е���������
			list_total_cartItem.clear();
			//��ѡ��ͼ����ʾ��ʱ��ȫѡ��ť�ż���
			if(!adapter.isFlag()){
				Drawable drawable = null;
				if(!flag_select_all){
					drawable = getResources().getDrawable(R.drawable.icon_seclete);
					//�ı�ͼƬ
					tv_select_all.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
					tv_select_all.setTextColor(getActivity().getResources().getColor(R.color.wathet_blue));
					flag_select_all = true;
					//ȫ��ѡ��֮�󣬵ó�����ܼۣ����Ҹ����ܼ�����
					//ȫѡ��ʱ���ﳵ�������ݺͽ���������һ����
					//������ȫ����ӵ����㼯����
					list_total_cartItem.addAll(list_cartItem);
					double total = cart.totalPrice(list_total_cartItem);
					//��ʾ�ܼ�
					tv_total_price.setText("�ϼ�:��"+total);
					//��ʾ������������
					tv_total_cartItem_count.setText("����("+list_total_cartItem.size()+")");
					if(list_total_cartItem.size()!=0){
						tv_total_cartItem_count.setBackgroundColor(getActivity().getResources().getColor(R.color.red));
						tv_total_cartItem_count.setClickable(true);
					}
					//����adapter
					adapter.setFlag_select_all(true);
					adapter.notifyDataSetChanged();
				}else{
					//ȡ��ȫѡʱ�򣬳�ʼ������
					initializeData();
					//����adapter
					adapter.setFlag_select_all(false);
					adapter.notifyDataSetChanged();
				}
			}
			break;

			//�༭�ı���ť����
		case R.id.tv_fragment_cart_pager_edite_button:
			boolean flag = adapter.isFlag();
			if(!flag){
				// �ı�ȫѡ�ı���ť���״̬
				Drawable drawable = getResources().getDrawable(R.drawable.icon_unseclete);
				//�ı�ͼƬ
				tv_select_all.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
				tv_select_all.setTextColor(getActivity().getResources().getColor(R.color.black_light));
				flag_select_all = false;
				tv_total_price.setText("�ϼ�:��0.00");
				list_total_cartItem.clear();
				tv_total_cartItem_count.setText("����("+list_total_cartItem.size()+")");
				tv_total_cartItem_count.setBackgroundColor(getActivity().getResources().getColor(R.color.gray_light));
				tv_total_cartItem_count.setClickable(false);
				//�ı�ѡ���ı���ť��Ǳ���ֵ
				adapter.setFlag_select_all(false);

				//�ı�cartAdapter�������ʾ��Ǳ�����ֵ
				adapter.setFlag(true);
				//				tv_edite.setTextColor(getActivity().getResources().getColor(R.color.red));
				tv_edite.setText("���");
				//����cartAdapter,�������е�ѡ��ͼ��,��ʾ���е�ɾ��ͼ��
				adapter.notifyDataSetChanged();
			}else{
				//�ı�cartAdapter�������ʾ��Ǳ�����ֵ
				adapter.setFlag(false);
				//				tv_edite.setTextColor(getActivity().getResources().getColor(R.color.white));
				tv_edite.setText("�༭ȫ��");
				//����cartAdapter,�������е�ɾ��ͼ��,��ʾ���е�ѡ��ͼ��
				adapter.notifyDataSetChanged();
			}
			//��ʾ����Ч��
			//			adapter.deleteIconShow();
			break;
			//������㰴ť
		case R.id.tv_fragment_cart_pager_total_button:
			//�������ض���
			loadingData("����Ŭ��������");
			//�������
			list_buyParams.clear();
			list_productIds.clear();
			//���ݽ��㼯�Ͻ������ݲɼ�
			for(CartItem cartItem:list_total_cartItem){
				//��ȡ���й���������ϣ���Ʒid����
				list_buyParams.add(cartItem.getBuyParam());
				list_productIds.add(cartItem.getProduct().getYWM_ID()+"");
			}
			for(CartItem cartitem:list_cartItem){
				Myapplication.log("���ﳵ���ݼ���",cartitem.getBuyParam().toString());
			}
			//���presenter����
			buyPresenter = new BuyPresenterImpl(this, list_buyParams , list_productIds);
			buyPresenter.buyFromCart();
			break;
			//����ҳ�棬���¼��ذ�ť
		case R.id.tv_fragment_cart_pager_linked_argin:
			//������Ʒ�б�ҳ��
			Intent intent = new Intent(getActivity(), HomeActivity.class);
			intent.putExtra("TAG", 0);
			startActivity(intent);
			break;
			//�ڶ�������ȷ�ϰ�ť
		case R.id.btn_fragment_cart_pager_seconde_confirm:
			//�õ���Ҫ�޸ĵ����ݵ��±�ֵ
			//��ȡ����������ݣ����ұ��浽��Ӧ�����ݼ�����
			list_cartItem.get(position).setBuyParam(getBuyParam());
			//���湺�ﳵ����
			cart.saveCartData();
			//���¹��ﳵ���ݳ���ҳ��
			adapter.notifyDataSetChanged();
			//�˳��ڶ�������
			hideSecondePager();
			Myapplication.toast("�༭�ɹ�");
			break;
			//�ڶ�������ɾ����ť
		case R.id.iv_fragment_cart_pager_to_buy_delete:
			hideSecondePager();
			break;
			//�ڶ�������͸������
		case R.id.ll_fragment_cart_pager_seconde_head:
			hideSecondePager();
			break;
			//�ڶ���������Ʒ���ٰ�ť
		case R.id.iv_fragment_cart_pager_to_buy_product_reduce:
			int acount = Integer.parseInt(tv_product_count.getText().toString());
			acount--;
			if(acount<1){
				tv_product_count.setText("1");
				acount = 1;
			}else{
				tv_product_count.setText(acount+"");
			}
			//���µ�����Ʒ�ܼ�
			tv_sigle_total_price.setText("�ܼۣ���"+cart.temporarySingleTotalPrice(list_cartItem.get(position), acount));
			break;
			//�ڶ���������Ʒ���Ӱ�ť
		case R.id.iv_fragment_cart_pager_to_buy_product_add:
			int total = Integer.parseInt(tv_product_count.getText().toString());
			tv_product_count.setText((++total)+"");
			//���µ�����Ʒ�ܼ�
			tv_sigle_total_price.setText("�ܼۣ���"+cart.temporarySingleTotalPrice(list_cartItem.get(position), total));
			break;
			//����ģʽ���ְ�ť
		case R.id.ll_fragment_cart_pager_dingdan_my_modle:
			//���ֶ���ģʽ�����˵�
			showPopupWindown();
			break;
		}
	}
	/**
	 * listView����
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//��ת����Ʒ����ҳ��
		Intent intent = new Intent(getActivity(), ProductDetailsPagerActivityImpl.class);
		intent.putExtra("product", list_cartItem.get(position).getProduct());
		startActivity(intent);
	}
	/**
	 * ����������ʱ���õķ���
	 */
	public void buyCompleted(String productId,int index_flag){
		//���¹��ﳵ���ݼ���
		for(int j = 0;j<list_cartItem.size();j++){
			//���ݲ�Ʒid�Ž����ж��Ƿ�ɾ��
			int id = list_cartItem.get(j).getProduct().getYWM_ID();
			if(productId.equals(id+"")){
				list_cartItem.remove(j);
				break;
			}
		}
		cart.saveCartData();

		//����̱߳��ֵ��id��Ž�ϵĳ�����һ�£�˵��������������Ѿ�ȫ�����
		if(index_flag==list_productIds.size()){
			//��ʼ��ҳ������
			initializeData();
			//���¹��ﳵ�б�
			adapter.setFlag_select_all(false);
			adapter.notifyDataSetChanged();
			//��ת���û���������
			Intent intent = new Intent(getActivity(), HomeActivity.class);
			intent.putExtra("TAG", 3);
			startActivity(intent);
			//��ֹ���ض���
			loadDataCompleted();
			Myapplication.toast("��������ɹ�");
		}
	}

	/**
	 * �Զ��巽�������صڶ�������,�ָ���һ������¼�
	 */
	private void hideSecondePager(){
		TranslateAnimation animation = new TranslateAnimation(0, 0, 0, ll_seconde.getHeight());
		animation.setDuration(500);
		ll_seconde.setAnimation(animation);
		ll_seconde.setVisibility(View.INVISIBLE);
		ll_first.setChildClickable(true);
	}
	/**
	 * �Զ��巽���������������֮��ҳ��������ʾ��ʱ�򣬽��г�ʼ������
	 */
	private void initializeData(){
		//ȫѡ��ť��ʼ��
		Drawable drawable = getResources().getDrawable(R.drawable.icon_unseclete);
		//�ı�ͼƬ
		tv_select_all.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
		tv_select_all.setTextColor(getActivity().getResources().getColor(R.color.black_light));
		flag_select_all = false;
		tv_total_price.setText("�ϼ�:��0.0");
		//��ʾ������������
		tv_total_cartItem_count.setText("����("+"0"+")");
		tv_total_cartItem_count.setBackgroundColor(getActivity().getResources().getColor(R.color.gray_light));
		tv_total_cartItem_count.setClickable(false);
	}
	/**
	 * �Զ��巽������ʾ������״̬ҳ��
	 */
	private void displayUnlinked() {
		//�ж���������,���ж���ҳ��ĳ���
		if(list_cartItem.size()!=0){
			lv_cartItem.setVisibility(View.VISIBLE);
			ll_unlinked.setVisibility(View.INVISIBLE);
		}else{
			lv_cartItem.setVisibility(View.INVISIBLE);
			ll_unlinked.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * �Զ����ȡ�������
	 */
	private Map<String, String> getBuyParam() {
		//�½������������
		Map<String, String> buyParam = new HashMap<String, String>();
		//�����������ӽ���
		ClientInfo clientInfo = app.getClientInfo().getResult().get(0);
		//�ͻ�id
		buyParam.put("clientid",clientInfo.getYWC_ClientID()+"");
		//�������
		String str = clientInfo.getYWC_ClientID()+"";
		String string = System.currentTimeMillis()+"";
		StringBuilder sb = new StringBuilder();
		sb.append(str).append(string);
		Myapplication.log("�������", sb.toString());
		buyParam.put("ladeid",sb.toString());
		//��������
		buyParam.put("areacode",clientInfo.getYWC_AreaCode());
		//��������
		buyParam.put("ladetype",et_dingdan_type.getText().toString());
		//Ʒ�ִ���
		buyParam.put("cementcode",list_cartItem.get(position).getProduct().getYWM_Code());
		//Ʒ������
		buyParam.put("cementname",list_cartItem.get(position).getProduct().getYWM_Name());
		//�µ�����
		buyParam.put("ordernumber",tv_product_count.getText().toString());
		//�µ��۸�
		buyParam.put("orderprice",list_cartItem.get(position).getProduct().getYWM_Price());
		//�µ��ܼ�
		String total_price = tv_total_price.getText().toString();
		buyParam.put("totalprice",total_price.substring(total_price.indexOf("��")+1, total_price.length()));
		//����˾������
		buyParam.put("carname",et_driver_name.getText().toString());
		//˾����ʻ֤����
		buyParam.put("carid",et_driver_number.getText().toString());
		//�������պ���
		buyParam.put("carcode",et_cart_number.getText().toString());
		//˾���绰����
		buyParam.put("carphone", et_dirver_phone.getText().toString());
		//�µ��˵绰
		buyParam.put("orderphone", et_buy_phone.getText().toString());
		//�µ�״̬��Ĭ�Ͽ�ʼֵΪ0
		buyParam.put("status", 0+"");
		return buyParam;
	}
	/**
	 * �Զ��巽�������й���������Զ���ֵ
	 */
	private void setOrderData(int i){
		//�õ����ݶ���
		OrderModleInfo orderModleInfo = list_orderModleInfo.get(i);
		et_buy_phone.setText(orderModleInfo.getOrderphone());
		et_cart_number.setText(orderModleInfo.getCarcode());
		et_dingdan_type.setText(orderModleInfo.getLadetype());
		et_dirver_phone.setText(orderModleInfo.getCarphone());
		et_driver_name.setText(orderModleInfo.getCarname());
		et_driver_number.setText(orderModleInfo.getCarid());
	}
	/**
	 * �Զ��巽����������й������,������ʾ�����������
	 */
	public void displayBuyParam(int position){
		et_buy_phone.setText("");
		et_cart_number.setText("");
		et_dingdan_type.setText("");
		et_dirver_phone.setText("");
		et_driver_name.setText("");
		et_driver_number.setText("");
		tv_order_modle.setText(null);
		tv_product_count.setText(list_cartItem.get(position).getBuyParam().get("ordernumber"));
		tv_price.setText("����:��"+list_cartItem.get(position).getProduct().getYWM_Price());
		tv_sigle_total_price.setText("�ܼ�:��"+cart.singleTotalPrice(list_cartItem.get(position)));
		tv_seconde_product_name.setText("����:"+list_cartItem.get(position).getProduct().getYWM_Name());
		//���������������
		ll_seconde.setVisibility(View.VISIBLE);
		TranslateAnimation animation_up = new TranslateAnimation(0, 0, ll_seconde.getHeight(), 0);
		animation_up.setDuration(500);
		ll_seconde.startAnimation(animation_up);
	}
	/**
	 * �Զ��巽������ʾpopupwindown�����˵�
	 */
	private void showPopupWindown() {
		//�����������ڶ���
		final Window window = getActivity().getWindow();
		final WindowManager.LayoutParams layout = window.getAttributes();
		View view = View.inflate(getActivity(), R.layout.popupwindown_view_modle_listview, null);
		ListView lv_order = (ListView) view.findViewById(R.id.lv_dingdan_my_modle);
		//�������ݲɼ�
		final List<String> list_orderModleName = new ArrayList<String>();
		for(int i=0;i<orderModle.getList_orderModleInfo().size();i++){
			list_orderModleName.add(orderModle.getList_orderModleInfo().get(i).getOrderModleName());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.base_textview_for_dapter_item_modle_font_white, list_orderModleName);
		lv_order.setAdapter(adapter);
		popupwindown = new PopupWindow(view, tv_order_modle.getWidth(), ll_seconde.getHeight()/3);
		popupwindown.showAsDropDown(tv_order_modle, 0, 2);
		popupwindown.setAnimationStyle(R.anim.popupwindown_display);
		//���ø������ڱ���
		layout.alpha = 0.5f;
		window.setAttributes(layout);
		popupwindown.setFocusable(true);
		popupwindown.update();
		popupwindown.setOutsideTouchable(false);
		//�����listview���ü���
		lv_order.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//��ʾ����ģʽ�ı�����
				tv_order_modle.setText(list_orderModleName.get(position));
				//����popupwindown
				popupwindown.dismiss();
				popupwindown = null;
				System.gc();
				//�Զ����ù����������
				setOrderData(position);
			}
		});
		//����popypwindow���ټ���
		popupwindown.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				layout.alpha = 1f;
				window.setAttributes(layout);
			}
		});
		//����popupwindow�Ǿ۽��������
		popupwindown.getContentView().setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				popupwindown.setFocusable(false);
				popupwindown.dismiss();
				popupwindown = null;
				System.gc();
				return false;
			}
		});
	}
	/**
	 * ������Ҫ�޸ĵ����ݵ��±�ֵ
	 * @param position
	 */
	public void setPosition(int position) {
		this.position = position;
	}
	/**
	 * �Զ��巽������������ʱ����ʾ���ض���Ч��
	 * str:��Ҫ��ʾ��������Ϣ
	 */
	private void loadingData(String str){
		dialog = new AlertDialog.Builder(getActivity()).create();
		View view = View.inflate(getActivity(), R.layout.dialog_loading_data, null);
		TextView text = (TextView) view.findViewById(R.id.tv_dialog_load_data);
		text.setText(str);
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
	}
	/**
	 * �Զ��巽�����رռ���������ʾ����
	 */
	private void loadDataCompleted() {
		dialog.dismiss();
		dialog = null;
		System.gc();
	}

	@Override
	public void onResume() {
		super.onResume();
		//�����������ݶ���
//		loadingData("����Ŭ��������");
		//�����Ѿ��鿴���ı乺�ﳵ������״̬
		((MainActivity) getActivity()).getTv_cart_acount().setVisibility(View.INVISIBLE);
		app.setNew_cart_data(false);
		//���ﳵ�������¼���,������ٴ���ʾҳ�棬�����¼�������
		if(flag_data_argin){
			presenter.uploadCartData(path);
			//�����ﳵ�����б仯�����ҵ�ǰѡ��ͼ��Ϊ��ʾ״̬
			if((list_cartItem.size()!=list_old_cartItem.size())&&(adapter.isFlag()==false)){
				//ȫѡ��ť��ʼ��
				Drawable drawable = getResources().getDrawable(R.drawable.icon_unseclete);
				//�ı�ͼƬ
				tv_select_all.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
				tv_select_all.setTextColor(getActivity().getResources().getColor(R.color.black_light));
				flag_select_all = false;
			}
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		flag_data_argin = true;
		list_old_cartItem.addAll(list_cartItem);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if(hidden){
			onPause();
		}else{
			onResume();
		}
	}

}
