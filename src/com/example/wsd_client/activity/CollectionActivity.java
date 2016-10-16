package com.example.wsd_client.activity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wsd_client.R;
import com.example.wsd_client.adapter.CollectionAdapter;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.customViews.CustomEnableLinearLayout;
import com.example.wsd_client.entity.Cart;
import com.example.wsd_client.entity.CartItem;
import com.example.wsd_client.entity.ClientInfo;
import com.example.wsd_client.entity.CollectionProduct;
import com.example.wsd_client.entity.OrderModle;
import com.example.wsd_client.entity.OrderModleInfo;
import com.example.wsd_client.entity.Product;
import com.example.wsd_client.presenter.impl.BuyPresenterImpl;
import com.example.wsd_client.util.CircleImageView;

/**
 *  �ղؼ�ҳ��
 * @author wsd_leiguoqiang
 */
public class CollectionActivity extends BaseActivity implements OnClickListener,OnItemClickListener{
	/**
	 * listview
	 */
	@ViewInject(R.id.lv_liebiao)
	private ListView lv_collection;
	/**
	 * ���ݼ���
	 */
	private List<Product> lists = new ArrayList<Product>();
	/**
	 * ������
	 */
	private CollectionAdapter collectiionAdapter;
	/**
	 * �ڶ�������
	 */
	@ViewInject(R.id.ll_product_item_pager_seconde)
	private LinearLayout ll_seconde;
	/**
	 * ��һ������
	 */
	@ViewInject(R.id.ll_product_item_pager_first)
	private LinearLayout ll_first;

	/**
	 * ���빺�ﳵ����������Я����Ǳ���
	 */
	private int click_flag;
	/**
	 * ��ӵ����ﳵ
	 */
	private static final int ADD_TO_CART = 1;
	/**
	 * ��������
	 */
	private static final int PROMPTLY_BUY = 2;
	/**
	 * �ڶ�������ͷ��͸������
	 */
	@ViewInject(R.id.ll_product_item_pager_seconde_head)
	private LinearLayout ll_seconde_head;
	/**
	 * �ڶ�������ȷ�ϰ�ť
	 */
	@ViewInject(R.id.btn_product_item_pager_seconde_confirm)
	private Button btn_confirm;
	/**
	 * �ڶ���ɾ����ť
	 */
	@ViewInject(R.id.iv_product_item_pager_to_buy_delete)
	private ImageView iv_seconde_delete;
	/**
	 * �������presenter
	 */
	private BuyPresenterImpl buyPresenter;
	/**
	 * �����������
	 */
	private Map<String, String> buyParam;
	/**
	 * ˾������
	 */
	@ViewInject(R.id.et_product_item_pager_driver_name)
	private EditText et_driver_name;
	/**
	 * ˾����ʻ֤����
	 */
	@ViewInject(R.id.et_product_item_pager_driver_number)
	private EditText et_driver_number;
	/**
	 * ��������
	 */
	@ViewInject(R.id.et_product_item_pager_dingdan_type)
	private EditText et_dingdan_type;
	/**
	 * �����ƺ�
	 */
	@ViewInject(R.id.et_product_item_pager_cart_number)
	private EditText et_cart_number;
	/**
	 * ˾���绰����
	 */
	@ViewInject(R.id.et_product_item_pager_dirver_phone)
	private EditText et_dirver_phone;
	/**
	 * �µ��˵ĵ绰����
	 */
	@ViewInject(R.id.et_product_item_pager_buy_phone)
	private EditText et_buy_phone;
	/**
	 * myapplication����
	 */
	private Myapplication app;
	/**
	 * ��������
	 */
	@ViewInject(R.id.tv_product_item_pager_to_buy_count_display)
	private TextView tv_product_count;
	/**
	 * �����ܼ�
	 */
	@ViewInject(R.id.tv_product_item_pager_to_buy_total_price)
	private TextView tv_total_price;
	/**
	 * ���������±�ֵ
	 */
	private int position ;
	/**
	 * ��Ʒ����
	 */
	@ViewInject(R.id.tv_product_item_pager_to_buy_price)
	private TextView tv_price;
	/**
	 * ���Ӱ�ť
	 */
	@ViewInject(R.id.iv_product_item_pager_to_buy_product_add)
	private ImageView iv_add;
	/**
	 * ���ٰ�ť
	 */
	@ViewInject(R.id.iv_product_item_pager_to_buy_product_reduce)
	private ImageView iv_reduce;
	/**
	 * ���ﳵ����
	 */
	private Cart cart;
	/**
	 * �ղؼж���
	 */
	private CollectionProduct collection;
	/**
	 * ����ģʽ�������µ�ʹ��
	 */
	@ViewInject(R.id.ll_product_item_pager_dingdan_my_modle)
	private LinearLayout ll_order_modle;
	/**
	 * ����ѡ���ı���ť
	 */
	@ViewInject(R.id.tv_product_item_pager_selecte_dingdan)
	private TextView tv_selecte_dinddan_modle;
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
	 * ���������Ӳ���
	 */
	@ViewInject(R.id.ll_fragment_liebiao_pager_unlinked)
	private CustomEnableLinearLayout ll_unlinked;
	/**
	 * ������ʱ�����¼����ı���ť
	 */
	@ViewInject(R.id.tv_fragment_liebiao_pager_linked_argin)
	private TextView tv_linked_argin;
	/**
	 * �ڶ���������Ʒ����
	 */
	@ViewInject(R.id.tv_product_item_pager_to_buy_product_name)
	private TextView tv_seconde_product_name;
	/**
	 * ͷ����������
	 */
	@ViewInject(R.id.rl_title_bar)
	private RelativeLayout rl_head;
	/**
	 * ��������������
	 */
	@ViewInject(R.id.rl_liebiao_fragment_pager_third)
	private RelativeLayout rl_search;
	/**
	 * ����������������edittext
	 */
	@ViewInject(R.id.et_liebiao_fragment_search)
	private EditText et_third_search;

	/**
	 * ҳ�����
	 */
	private TextView tvTitle;
	/**
	 * Բ��ͼ��
	 */
	private CircleImageView civ;
	/**
	 * ҳ�淵�ؼ�
	 */
	private ImageButton ibBack;
	/**
	 * ���ﳵͼ��
	 */
	private ImageButton ibCart;
	/**
	 * ���ﳵ����ͼ��
	 */
	private TextView tvAccount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_liebiao);
		init();
		initListeners();
	}

	private void initListeners() {
		ibBack.setOnClickListener(this);
		ibCart.setOnClickListener(this);
		lv_collection.setOnItemClickListener(this);
		btn_confirm.setOnClickListener(this);
		ll_seconde_head.setOnClickListener(this);
		iv_seconde_delete.setOnClickListener(this);
		iv_add.setOnClickListener(this);
		iv_reduce.setOnClickListener(this);
		ll_order_modle.setOnClickListener(this);
		tv_linked_argin.setOnClickListener(this);
	}

	private void init() {
		x.view().inject(this);
		app = (Myapplication) Myapplication.getContext();
		cart = app.getCart();
		collection = app.getCollectionProduct();
		orderModle = app.getOrderModle();
		tvTitle=(TextView)findViewById(R.id.tv_title);
		civ=(CircleImageView)findViewById(R.id.iv_contact);
		ibBack=(ImageButton)findViewById(R.id.ib_title_back);
		ibCart=(ImageButton)findViewById(R.id.ib_title_cart);
		tvAccount=(TextView)findViewById(R.id.tv_cart_index);
		tvTitle.setText("�ղؼ�");
		civ.setVisibility(View.INVISIBLE);
		ibBack.setVisibility(View.VISIBLE);
		ibCart.setVisibility(View.VISIBLE);
		//�������ݲɼ�
		lists = collection.getList_products();
		Myapplication.log("�ղؼ����ݳ���", lists.size()+"");
		//����������ʾ
		collectiionAdapter=new CollectionAdapter(lists, this);
		lv_collection.setAdapter(collectiionAdapter);
		//��ʼ�����ﳵͼ������
		int acount = ((Myapplication)(Myapplication.getContext())).getCart().getList_cartItem().size();
		if(acount>0){
			tvAccount.setText(acount+"");
			tvAccount.setVisibility(View.VISIBLE);
		}else{
			tvAccount.setVisibility(View.INVISIBLE);
		}
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
		tv_product_count.setText("1");
		tv_selecte_dinddan_modle.setText(null);
		tv_price.setText("����:��"+lists.get(position).getYWM_Price());
		tv_total_price.setText("�ܼ�:��"+lists.get(position).getYWM_Price());
		tv_seconde_product_name.setText("����:"+lists.get(position).getYWM_Name());
		//���������������
		ll_seconde.setVisibility(View.VISIBLE);
		TranslateAnimation animation_up = new TranslateAnimation(0, 0, ll_seconde.getHeight(), 0);
		animation_up.setDuration(1000);
		ll_seconde.startAnimation(animation_up);
	}

	public void setClick_flag(int click_flag) {
		this.click_flag = click_flag;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(this, ProductDetailsPagerActivityImpl.class);
		Product product = lists.get(position);
		intent.putExtra("product", product);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//���ؼ�
		case R.id.ib_title_back:
			finish();
			break;
			//���ﳵͼ�갴ť
		case R.id.ib_title_cart:
			Intent in =new Intent(this,MainActivity.class);
			in.putExtra("TAG",2);
			startActivity(in);
			break;
			// �ڶ��������ȷ�ϰ�ť
		case R.id.btn_product_item_pager_seconde_confirm:
			switch (click_flag) {
			//���뵽���ﳵ
			case ADD_TO_CART:
				//�жϼ������Ƿ��Ѿ����ڴ���Ʒ��ͨ����Ʒid�����жϣ�
				int productId = lists.get(position).getYWM_ID();
				//��ȡ���ﳵ�е�����
				List<CartItem> list_cartItem = cart.getList_cartItem();
				for(CartItem cartItem:list_cartItem){
					int productID = cartItem.getProduct().getYWM_ID();
					if(productId==productID){
						TranslateAnimation animation_down = new TranslateAnimation(0, 0, 0, ll_seconde.getHeight());
						animation_down.setDuration(1000);
						ll_seconde.startAnimation(animation_down);
						ll_seconde.setVisibility(View.INVISIBLE);
						Myapplication.toast("����Ʒ�Ѵ��ڹ��ﳵ");
						return;
					}
				}
				//����һ����cartitem���󣬲���������ӵ����ﳵ���ݼ�����
				CartItem cartItem = new CartItem();
				//���ò�Ʒ����
				cartItem.setProduct(lists.get(position));
				//���ù����������
				cartItem.setBuyParam(getBuyParam());
				//�������ݵ�����
				list_cartItem.add(cartItem);
				cart.saveCartData();
				//���¹��ﳵ����Сͼ��
				tvAccount.setText(list_cartItem.size()+"");
				tvAccount.setVisibility(View.VISIBLE);
				TranslateAnimation animation_down = new TranslateAnimation(0, 0, 0, ll_seconde.getHeight());
				animation_down.setDuration(1000);
				ll_seconde.startAnimation(animation_down);
				ll_seconde.setVisibility(View.INVISIBLE);
				Myapplication.toast("�ɹ����빺�ﳵ");
				break;
				//��������
			case PROMPTLY_BUY:
				buyPresenter = new BuyPresenterImpl(getBuyParam(), this);
				buyPresenter.buyFromCollection();
				TranslateAnimation animation_down_one = new TranslateAnimation(0, 0, 0, ll_seconde.getHeight());
				animation_down_one.setDuration(1000);
				ll_seconde.startAnimation(animation_down_one);
				ll_seconde.setVisibility(View.INVISIBLE);
				break;
			}
			break;
			//�ڶ���͸������
		case R.id.ll_product_item_pager_seconde_head:
			TranslateAnimation animation_down = new TranslateAnimation(0, 0, 0, ll_seconde.getHeight());
			animation_down.setDuration(1000);
			ll_seconde.startAnimation(animation_down);
			ll_seconde.setVisibility(View.INVISIBLE);
			break;
			//�ڶ���ɾ����ť
		case R.id.iv_product_item_pager_to_buy_delete:
			TranslateAnimation animation_down_delete = new TranslateAnimation(0, 0, 0, ll_seconde.getHeight());
			animation_down_delete.setDuration(1000);
			ll_seconde.startAnimation(animation_down_delete);
			ll_seconde.setVisibility(View.INVISIBLE);
			break;
			//����
		case R.id.iv_product_item_pager_to_buy_product_add:
			int acount_add = Integer.parseInt(tv_product_count.getText().toString());
			tv_product_count.setText((++acount_add)+"");
			tv_total_price.setText("�ܼ�:��"+totalPrice(lists.get(position).getYWM_Price(), acount_add)+"");
			break;
			//����
		case R.id.iv_product_item_pager_to_buy_product_reduce:
			int acount_reduce = Integer.parseInt(tv_product_count.getText().toString());
			acount_reduce--;
			if(acount_reduce<1){
				acount_reduce = 1;
			}
			tv_product_count.setText(acount_reduce+"");
			tv_total_price.setText("�ܼ�:��"+totalPrice(lists.get(position).getYWM_Price(), acount_reduce)+"");
			break;
			//����ģʽ��ť
		case R.id.ll_product_item_pager_dingdan_my_modle:
			//��ʾpopupdindow
			showPopypwindow();
			break;
			//���¼��������ı���ť
		case R.id.tv_fragment_liebiao_pager_linked_argin:
			//���½��з������������󣬷����������޴˹���
			//			presenter.loadData();
			break;
		}
	}
	/**
	 *�Զ��巽�����ó���ʱ�ܼ� 
	 * @param price������
	 * @param acount������
	 */
	private double totalPrice(String price,int acount){
		BigDecimal bd_price = new BigDecimal(price);
		BigDecimal bd_acount = new BigDecimal(acount);
		DecimalFormat df = new DecimalFormat("#.00");
		return Double.parseDouble(df.format(bd_price.multiply(bd_acount).doubleValue()));
	}
	/**
	 * �Զ��巽������ʾpopupdindow�����п����µ�
	 */
	private void showPopypwindow() {
		//����popopwindown(����view��myapplication��ȡ����ģʽ���ݣ���ʾ����)
		View view = View.inflate(this, R.layout.popupwindown_view_modle_listview, null);
		ListView lv_dingdan = (ListView) view.findViewById(R.id.lv_dingdan_my_modle);
		//�������ݲɼ�
		final List<String> list_dingdan_name = new ArrayList<String>();
		list_orderModleInfo = orderModle.getList_orderModleInfo();
		for(OrderModleInfo orderModleInfo:list_orderModleInfo){
			list_dingdan_name.add(orderModleInfo.getOrderModleName());
		}
		//�������ݳ���
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.base_textview_for_dapter_item_modle_font_white, list_dingdan_name);
		lv_dingdan.setAdapter(adapter);
		popupwindown = new PopupWindow(view, tv_selecte_dinddan_modle.getWidth(), ll_seconde.getHeight()/3);
		popupwindown.showAsDropDown(tv_selecte_dinddan_modle, 0, 2);
		//���������ĳ�͸����ɫ
		WindowManager.LayoutParams layout = getWindow().getAttributes();
		layout.alpha = 0.5f;
		getWindow().setAttributes(layout);
		//���ý���͸��´���״̬
		popupwindown.setFocusable(true);
		popupwindown.update();
		//����֮��������ȡ��popupwindow
		popupwindown.setOutsideTouchable(false);
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
		popupwindown.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams layout = getWindow().getAttributes();
				layout.alpha = 1f;
				getWindow().setAttributes(layout);
			}
		});
		lv_dingdan.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//����Ǹ��ͰѸ����ݱ�����ʾ��ѡ���ı���
				tv_selecte_dinddan_modle.setText(list_dingdan_name.get(position));
				//ȡ�����������ݣ��������ؼ����и�ֵ
				setOrderData(position);
				popupwindown.setFocusable(false);
				popupwindown.dismiss();
				popupwindown = null;
				System.gc();
			}
		});
	}
	/**
	 * �Զ����ȡ�������
	 */
	private Map<String, String> getBuyParam() {
		//�½������������
		buyParam = new HashMap<String, String>();
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
		buyParam.put("cementcode",lists.get(position).getYWM_Code());
		//Ʒ������
		buyParam.put("cementname",lists.get(position).getYWM_Name());
		//�µ�����
		buyParam.put("ordernumber",tv_product_count.getText().toString());
		//�µ��۸�
		buyParam.put("orderprice",lists.get(position).getYWM_Price());
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
	 * ����������ʱ���õķ���
	 */
	public void buyCompleted(){
		//��ת���û���������ҳ��
		Intent intent = new Intent(this, HomeActivity.class);
		intent.putExtra("TAG", 3);
		startActivity(intent);
		Myapplication.toast("����ɹ�");
	}
}
