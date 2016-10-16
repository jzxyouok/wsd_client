package com.example.wsd_client.activity;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wsd_client.R;
import com.example.wsd_client.adapter.OrderModleAdapter;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.customViews.CustomEnableLinearLayout;
import com.example.wsd_client.entity.ClientInfo;
import com.example.wsd_client.entity.OrderModle;
import com.example.wsd_client.entity.OrderModleInfo;

/**
 * ����ģʽactivity����������µ�ʹ��
 * @author wsd_leiguoqiang
 */
public class OrderModleActivity extends BaseActivity implements OnClickListener{
	/**
	 * ����ģʽ���ݼ���
	 */
	private List<OrderModleInfo> list_orderModlerInfo = new ArrayList<OrderModleInfo>();
	/**
	 * ����ģʽ��������
	 */
	private List<String> list_orderModleName = new ArrayList<String>();
	/**
	 * listview����ģʽ�б�
	 */
	@ViewInject(R.id.lv_order_modle_pager_orders)
	private ListView lv;
	/**
	 * ����ı���ť
	 */
	@ViewInject(R.id.tv_order_modle_pager_add)
	private TextView tv_add;
	/**
	 * ��һ�㲼��
	 */
	@ViewInject(R.id.ll_order_modle_pager_first)
	private LinearLayout ll_first;
	/**
	 * �ڶ��㲼��
	 */
	@ViewInject(R.id.ll_order_modle_pager_seconde)
	private LinearLayout ll_seconde;
	/**
	 * �ڶ���ȷ����ť
	 */
	@ViewInject(R.id.btn_product_details_pager_seconde_confirm)
	private Button btn_confirm;
	
	private Myapplication app;
	/**
	 * �û�������
	 */
	private ClientInfo clientInfo = null;

	@ViewInject(R.id.et_order_modle_pager_dingdan_type)
	private EditText et_ladeType;

	@ViewInject(R.id.et_order_modle_pager_buy_phone)
	private EditText et_buy_phone;
	/**
	 * ˾������
	 */
	@ViewInject(R.id.et_order_modle_pager_driver_name)
	private EditText et_driverName;
	/**
	 * ˾����ʻ֤����
	 */
	@ViewInject(R.id.et_order_modle_pager_driver_number)
	private EditText et_driverNumber;
	/**
	 * ��������
	 */
	@ViewInject(R.id.et_order_modle_pager_cart_number)
	private EditText et_cartNumber;
	/**
	 * ˾���ֻ�����
	 */
	@ViewInject(R.id.et_order_modle_pager_dirver_phone)
	private EditText et_driverPhone;
	/**
	 * ����ģʽ����
	 */
	@ViewInject(R.id.et_order_modle_pager_dingdan_name)
	private EditText et_dingdanName;
	/**
	 * ����ģʽ���Զ���
	 */
	private OrderModleInfo orderModleInfo = null;
	/**
	 * ����ģʽʵ����
	 */
	private OrderModle orderModle;
	/**
	 * ������
	 */
	private OrderModleAdapter adapter;
	/**
	 * ������״̬ҳ��
	 */
	@ViewInject(R.id.ll_order_modle_pager_unlinked)
	private CustomEnableLinearLayout ll_linked;
	/**
	 * ������״̬������Ӱ�ť
	 */
	@ViewInject(R.id.tv_order_modle_pager_linked_argin)
	private TextView tv_linked_argin;
	/**
	 * �����±�ֵ
	 */
	private int position;
	/**
	 * ��Ǳ�����ȷ�ϰ�ť���������������
	 */
	private int flag_confirm;
	/**
	 * ��Ӳ���
	 */
	public static final int ADD_ORDER = 1;
	/**
	 * �༭����
	 */
	public static final int EDITE_ORDER = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_modle_pager);
		x.view().inject(this);
		init();
		initListeners();
	}

	private void init() {
		app = (Myapplication) Myapplication.getContext();
		orderModle = app.getOrderModle();
		//��ʾ����ģʽ����
		list_orderModlerInfo = orderModle.readOrderModle().getList_orderModleInfo();
		for(OrderModleInfo orderModle:list_orderModlerInfo){
			list_orderModleName.add(orderModle.getOrderModleName());
		}
		adapter = new OrderModleAdapter(this, list_orderModleName);
		lv.setAdapter(adapter);
		//������״̬
		displayUnlinked();
	}

	private void initListeners() {
		tv_add.setOnClickListener(this);
		btn_confirm.setOnClickListener(this);
		tv_linked_argin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//��Ӱ�ť
		case R.id.tv_order_modle_pager_add:
			//��ʾ��ӱ༭ҳ��
			displayEditePager();
			flag_confirm = ADD_ORDER;
			break;
			//�ڶ���ȷ����ť
		case R.id.btn_product_details_pager_seconde_confirm:
			switch (flag_confirm) {
			//��Ӳ���
			case ADD_ORDER:
				//�ռ�����
				getorderModleInfo();
				//�����¶���ģʽ����
				orderModle.addOrderModleInfo(orderModleInfo);
				//���б��ػ��洢
				orderModle.saveOrderModle();
				//���¶���ģʽ�����б�
				list_orderModleName.add(orderModleInfo.getOrderModleName());
				adapter.notifyDataSetChanged();
				//�������״̬���Ƿ���ʾ������״̬
				displayUnlinked();
				//�Զ��л��༭ҳ����ʾ״̬
				displayEditePager();
				break;
			//�༭����
			case EDITE_ORDER:
				//�ռ�����
				getorderModleInfo();
				//���Ƴ����ڼ���
				list_orderModlerInfo.remove(position);
				list_orderModlerInfo.add(position, orderModleInfo);
				//��������
				orderModle.saveOrderModle();
				//�������ݺ��б�
				list_orderModleName.remove(position);
				list_orderModleName.add(position, orderModleInfo.getOrderModleName());
				adapter.notifyDataSetChanged();
				//�Զ��л��༭ҳ����ʾ״̬
				displayEditePager();
				break;
			}
			break;
			//������״̬������Ӱ�ť
		case R.id.tv_order_modle_pager_linked_argin:
			displayEditePager();
			flag_confirm = ADD_ORDER;
			break;
		}
	}
	/**
	 * �Զ��巽������ʾ����ģʽ�༭ҳ��
	 */
	public void displayEditePager() {
		//���ж��Ƿ��û���¼�ɹ�
		clientInfo = app.getClientInfo().getResult().get(0);
		if(clientInfo!=null){
			if(ll_seconde.getVisibility()!=View.VISIBLE){
				//��ʾ�༭����
				ll_first.setVisibility(View.INVISIBLE);
				ll_seconde.setVisibility(View.VISIBLE);
				TranslateAnimation animation = new TranslateAnimation(ll_seconde.getWidth(), 0, 0, 0);
				animation.setDuration(1000);
				ll_seconde.startAnimation(animation);
			}else{
				//���ر༭����
				AlphaAnimation alpha_animation = new AlphaAnimation(1f, 0f);
				alpha_animation.setDuration(1000);
				ll_seconde.startAnimation(alpha_animation);
				ll_seconde.setVisibility(View.INVISIBLE);
				AlphaAnimation alpha_animation_first = new AlphaAnimation(0f, 1f);
				alpha_animation_first.setDuration(1000);
				ll_first.startAnimation(alpha_animation_first);
				ll_first.setVisibility(View.VISIBLE);
			}
			
		}
	}
	/**
	 * �Զ��巽������ȡordermodleinfo����
	 */
	private void getorderModleInfo(){
		orderModleInfo = new OrderModleInfo();
		orderModleInfo.setOrderModleName(et_dingdanName.getText().toString());
		orderModleInfo.setCarcode(et_cartNumber.getText().toString());
		orderModleInfo.setCarid(et_driverNumber.getText().toString());
		orderModleInfo.setCarname(et_driverName.getText().toString());
		orderModleInfo.setCarphone(et_driverPhone.getText().toString());
		orderModleInfo.setLadetype(et_ladeType.getText().toString());
		orderModleInfo.setOrderphone(et_buy_phone.getText().toString());
		Myapplication.log("ordermodleactivity",orderModleInfo.toString());
	}
	/**
	 * �Զ��巽����������״̬��ʾҳ��
	 */
	private void displayUnlinked() {
		if(list_orderModleName.size()!=0){
			ll_linked.setVisibility(View.INVISIBLE);
			lv.setVisibility(View.VISIBLE);
		}else{
			ll_linked.setVisibility(View.VISIBLE);
			lv.setVisibility(View.INVISIBLE);
		}
	}

	public void setFlag_confirm(int flag_confirm) {
		this.flag_confirm = flag_confirm;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	@Override
	public void onBackPressed() {
		if(ll_seconde.getVisibility()==View.VISIBLE){
			displayEditePager();
		}else{
			super.onBackPressed();
		}
	}
}
