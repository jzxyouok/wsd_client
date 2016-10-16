package com.example.wsd_client.activity;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


import com.example.wsd_client.R;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.Cart;
import com.example.wsd_client.entity.CollectionProduct;
import com.example.wsd_client.entity.OrderModle;
import com.example.wsd_client.entity.YW_ClientInfo;
import com.example.wsd_client.urlUtils.UrlPath;
import com.example.wsd_client.util.SharedPreferenceUtils;
import com.google.gson.Gson;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class LoginActivity extends Activity implements OnClickListener{

	private EditText etUsercode,etPassword,etCodePhoto;//��֤��
	private RelativeLayout rl3;
	private ImageView ivCode;
	private CheckBox cbRem,cbAutomatic;//�Զ���½
	private Button btnLogin;
	private TextView tvHelp,tvNew;
	private String usercode;
	private String userpwd;
	private String code;
	private String urlParma;
	//���ڼ�¼�û�������������ﵽ�����������ʱ��������֤��
	private int i=0;

	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			//�û������˺�������ȷʱ�������ֵΪ1
			case 1:
				login(usercode,userpwd);
				break;

				//�û������˺����벻��ȷʱ�������ֵΪ2
			case 2:
				Myapplication.toast("��¼ʧ�ܣ������˻��������Ƿ���ȷ");
				etUsercode.setText("");
				etPassword.setText("");
				etCodePhoto.setText("");
				//�۽��������ת���û�����ĵط�
				etUsercode.requestFocus();
				i++;
				if(i>2){
					rl3.setVisibility(View.VISIBLE);
				}
				break;

				//�������Ӳ�����ʱ�������ֵΪ3
			case 3:
				Myapplication.toast("���������Ƿ����ӣ�");
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		initView();

		setCheckState();

		setListener();
	}

	/**
	 * ��ʼ���ؼ�
	 */
	private void initView() {
		etUsercode=(EditText) findViewById(R.id.et_login_usercode);
		etPassword=(EditText) findViewById(R.id.et_login_userpassword);
		etCodePhoto=(EditText) findViewById(R.id.et_indentification_code);
		ivCode=(ImageView) findViewById(R.id.iv_code);
		cbRem=(CheckBox) findViewById(R.id.cb_login_remember);
		cbAutomatic=(CheckBox) findViewById(R.id.cb_login_automatic);
		btnLogin=(Button) findViewById(R.id.btn_login);
		tvHelp=(TextView) findViewById(R.id.tv_login_help);
		tvNew=(TextView) findViewById(R.id.tv_login_new);
		rl3=(RelativeLayout) findViewById(R.id.rl3);
	}


	/**
	 * ͨ��ƫ�����û�ȡ��¼�����Ƿ��Ǳ���ѡ״̬
	 * �Զ���¼ʱ�������ϴ��ֶ���¼��¼��ʱ�����ֵ���������7���ڿ��Զ���¼��7������ֶ���¼
	 */
	private void setCheckState() {
		boolean isCheckRem = SharedPreferenceUtils.getCheckedBoolean("cbRem", LoginActivity.this);
		boolean isCheckAuto = SharedPreferenceUtils.getCheckedBoolean("cbAutomatic", LoginActivity.this);
		Map<String, String> info = SharedPreferenceUtils.getInfo(LoginActivity.this);
		usercode=info.get("usercode");
		userpwd=info.get("userpwd");
		if(usercode!=null&&userpwd!=null){
			if(isCheckRem){
				cbRem.setChecked(true);
				etUsercode.setText(usercode);
				etPassword.setText(userpwd);
				if(isCheckAuto){
					//��ȡ��ǰ��ʱ�����ֵ
					long currentMillies=System.currentTimeMillis();
					//��ȡ�ϴδ洢��ʱ�����ֵ
					long lastMillies=SharedPreferenceUtils.getCurrentMillies(this);
					//������ϴ��Զ���¼�͵�ǰ��ʱ����Ƿ�Ϊ7�죬7��֮���Զ���¼��7��֮����Ҫ���������˺�����
					//7���ʱ�����ֵ
					long i=7*24*60*60*1000;
					//ʱ���
					long timeDifference=currentMillies-lastMillies;
					if(timeDifference<i){
						cbAutomatic.setChecked(true);
						uploadInfo(usercode,userpwd);
					}else{
						cbAutomatic.setChecked(false);
						cbRem.setChecked(false);
						etPassword.setText("");
						SharedPreferenceUtils.saveInfo(usercode, "", LoginActivity.this);
						SharedPreferenceUtils.setCheckedBoolean("cbAutomatic", false, LoginActivity.this);
						SharedPreferenceUtils.setCheckedBoolean("cbRem", false, LoginActivity.this);
					}
				}
			}
		}else{
			cbAutomatic.setChecked(false);
			cbRem.setChecked(false);
			SharedPreferenceUtils.setCheckedBoolean("cbRem", false, this);
			SharedPreferenceUtils.setCheckedBoolean("cbAutomatic", false, this);
		}
	}


	private void setListener() {
		ivCode.setOnClickListener(LoginActivity.this);
		cbRem.setOnClickListener(LoginActivity.this);
		cbAutomatic.setOnClickListener(LoginActivity.this);
		btnLogin.setOnClickListener(LoginActivity.this);
		tvHelp.setOnClickListener(LoginActivity.this);
		tvNew.setOnClickListener(LoginActivity.this);

	}

	@Override
	public void onClick(View v) {

		initData();

		switch (v.getId()) {
		case R.id.iv_code:

			break;

		case R.id.btn_login:
			//�ֶ���¼ʱ������ס������Զ���¼��ť����ѡ�����¼�µ�ǰ�ĵ�¼ʱ�����ֵ��������֮ǰ������
			if(cbRem.isChecked()&&cbAutomatic.isChecked()){
				long currentMillies=System.currentTimeMillis();
				SharedPreferenceUtils.setCurrentMillies(currentMillies, LoginActivity.this);
			}
			uploadInfo(usercode,userpwd);

			break;

		case R.id.tv_login_help:

			break;

		case R.id.tv_login_new:

			break;

		}

	}


	/**
	 * ��ʼ������
	 */
	private void initData() {
		//��ȡ�û�������û����롢���롢��֤��
		usercode = etUsercode.getText().toString().trim();
		userpwd = etPassword.getText().toString();
		code = etCodePhoto.getText().toString();
	}

	/**
	 * 
	 * ���û�������û��������봫�������������ȡ��Ӧ��
	 * @param urlParma  ����������˻�����
	 */
	private void uploadInfo( String usercode,String userpwd) {

		urlParma=UrlPath.USER_INFO+"?usercode="+usercode+"&userpwd="+userpwd;
		new Thread(new Runnable() {

			@Override
			public void run() {
				StringBuffer sb=new StringBuffer();

				try {
					URL url = new URL(urlParma);
					HttpURLConnection conn=(HttpURLConnection) url.openConnection();
					conn.setConnectTimeout(5000);
					conn.setRequestMethod("GET");
					conn.connect();

					//���ݷ���ֵ��ȡ��ǰ����״̬�Ƿ�����
					if(conn.getResponseCode()!=HttpURLConnection.HTTP_OK){
						Message message=Message.obtain();
						message.what=3;
						handler.sendMessage(message);
					}

					InputStream is=conn.getInputStream();
					InputStreamReader isr=new InputStreamReader(is);
					BufferedReader reader=new BufferedReader(isr);
					String line=reader.readLine();
					while(line!=null){
						sb.append(line);
						Myapplication.log("-----------", "sb:"+sb.toString());
						line=reader.readLine();
					}

					//��ȡtotal��ֵ
					Gson gson = new Gson();
					YW_ClientInfo json = gson.fromJson(sb.toString(), YW_ClientInfo.class);

					//����ȡ�����û���Ϣ��װ��Myapplication��
					Myapplication app=(Myapplication) Myapplication.getContext();
					app.setClientInfo(json);

					int total = json.getTotal();
					Myapplication.log("-----------", "totla:"+total);
					if(total>0){
						//����total��ֵ��������0���ȱ�ʾ�˺�������ȷ����¼�ɹ���������0�����¼ʧ��
						Message message=Message.obtain();
						message.what=1;
						handler.sendMessage(message);
					}else{
						Message message=Message.obtain();
						message.what=2;
						handler.sendMessage(message);
					}

					is.close();
					isr.close();
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	/**
	 * ��¼�ɹ�ʱ��ִ�еĲ���
	 */
	private void login(String ucode,String upwd) {
		if(cbRem.isChecked()){
			SharedPreferenceUtils.setCheckedBoolean("cbRem", true, this);
			SharedPreferenceUtils.saveInfo(ucode, upwd, this);

		}else{
			SharedPreferenceUtils.setCheckedBoolean("cbRem", false, this);
		}

		if(cbAutomatic.isChecked()){
			SharedPreferenceUtils.setCheckedBoolean("cbAutomatic", true, this);
		}else{
			SharedPreferenceUtils.setCheckedBoolean("cbAutomatic", false, this);
		}

		if(rl3.getVisibility()==View.VISIBLE){
			if(code.equals("7364")){
				Intent intent = new Intent(LoginActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}else{
				Myapplication.toast("��֤�������������������");
				etCodePhoto.setText("");
			}
		}else{
			//��ȡ���ﳵ����
			Myapplication app = (Myapplication) Myapplication.getContext();
			//���ع��ﳵ����
			Cart cart = app.getCart();
			cart = cart.readCartData();
			app.setCart(cart);
			if(cart.getList_cartItem().size()!=0){
				app.setNew_cart_data(true);
			}
			//���ض���ģʽ����
			OrderModle orderModle = app.getOrderModle();
			orderModle = orderModle.readOrderModle();
			app.setOrderModle(orderModle);
			//�����ղز�Ʒ����
			CollectionProduct collectionProduct = app.getCollectionProduct();
			collectionProduct = collectionProduct.readCollectionnProduct();
			app.setCollectionProduct(collectionProduct);

			//��ת��mainactivity
			Intent intent = new Intent(LoginActivity.this,MainActivity.class);
			intent.putExtra("TAG", 0);
			startActivity(intent);
			finish();
		}
	}


}
