package com.example.wsd_client.activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import com.example.wsd_client.R;
import com.example.wsd_client.adapter.AccountFragmentPpWindowAdapter;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.XS_Order_ABase;
import com.example.wsd_client.urlUtils.UrlPath;
import com.example.wsd_client.util.CircleImageView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ManageActivity extends Activity implements OnClickListener{

	//��װpopupWindowҪ��ʾ������
	private List<String> list;
	private ListView lvPpWindow;
	private View viewPpWindow;
	private PopupWindow popupWindow;
	private AccountFragmentPpWindowAdapter ppWindowAdapter;
	private String itemAtPosition;
	//���ڷ�װ��list�����г���tvChoose����ʾ���������������
		private List<String> removeList;
	
	
	private CircleImageView civ;
	private ImageButton ibBack;
	private ImageButton ibCart;

	//�޸�ɾ����ť
	private Button btn_edit,btn_delete;

	private XS_Order_ABase order_ABase;
	/**
	 * �����Ӽ���ť
	 */
	private ImageView iv_add,iv_reduce;
	//tvSetDate:�µ�ʱ��   tvPrice����Ʒ����  tvCountDisplay���µ�����
	//tvTotalPrice:�ܼ�
	private TextView tvTitle,tvSetDate,tvPrice,tvCountDisplay,tvTotalPrice,tvCementName;
	//etLadeID���������  etCarName��˾������   etCarCode��˾����ʻ֤��
	//etCarPhone��˾���绰   etOrderPhone���µ��˵绰
	private EditText etLadeID,etCarName,etCarID,etCarCode,etCarPhone,etOrderPhone;

	//���� ��Ʒ���������ܼ�
	int count=0;
	double c=0.0;
	 double mul=0.0;
	//
	private Myapplication myapplication=(Myapplication) getApplication();
	//��ŵ�ַ����
	private String urlParam;

	//dialog
	private AlertDialog.Builder builder;

	int positon;

	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			//���ȷ��ɾ��
			case 0:
				String delete=(String) msg.obj;
				Myapplication.toast(delete);
				Bundle bundle=new Bundle();
				bundle.putInt("positon", positon);
				Intent intent=getIntent();
				intent.putExtra("positon", bundle);
				setResult(6,intent);
				finish();
				break;
				//���ȷ���޸�
			case 1:
				String edit=(String) msg.obj;
				if(edit!=null){
					Myapplication.toast("�����ڶ�Ӧ�ĵ��ݺţ������޸�");
				}else {
					Myapplication.toast("�޸����");
					Bundle bundle2=new Bundle();
					bundle2.putInt("positon", positon);
					Intent intent2=new Intent();
					intent2.putExtra("positon", bundle2);
					setResult(6,intent2);
					finish();
				}

				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage);

		//x.view().inject(this);
		//��ʼ���ؼ�
		initView();
		//�ؼ���ֵ
		setView();
		//���ü���
		setListener();
	}

	/**
	 * ��ʾ��ֵ
	 */
	private void setView() {
		etLadeID.setText(order_ABase.getXSO_LadeID());
		tvSetDate.setText(order_ABase.getXSO_SetDate());
		tvCementName.setText(order_ABase.getXSO_CementName());
		
		tvPrice.setText("��"+order_ABase.getXSO_Price());

		tvCountDisplay.setText(count+"");

		tvTotalPrice.setText("��"+order_ABase.getXSO_TotalPrice());
		etCarName.setText(order_ABase.getXSO_CarName());
		etCarID.setText(order_ABase.getXSO_CarID());
		etCarCode.setText(order_ABase.getXSO_CarCode());
		etCarPhone.setText(order_ABase.getXSO_CarPhone());
		etOrderPhone.setText(order_ABase.getXSO_OrderPhone());
		//˾��������λ
		etCarName.setFocusable(true);
		//���ܱ༭
		etLadeID.setEnabled(false);
		//etLadeID.setKeyListener(null);
		
		//���ö���ѡ��ʽ�����ݼ���
		list=new ArrayList<String>();
		list.add(order_ABase.getXSO_CementName());
		list.add("PC42.5R��װ");
		list.add("PC32.5��װ");
		list.add("ɢװ42.5");

		//��װ�Ƴ��������
		removeList = new ArrayList<String>();
		removeList.addAll(list);
		removeList.remove(0);
		//����Ĭ����ʾ����
		tvCementName.setText(list.get(0));
		itemAtPosition="δ����";

	}
	/**
	 * ��ʼ���ؼ�
	 */
	private void initView() {
		//��ʾ���ز��֣����ڼ���ͬһ�������ļ�
		
		tvCementName=(TextView)findViewById(R.id.tv_kuaisu_cementName);
	
		tvCementName.setVisibility(View.VISIBLE);
	
		
		//ͷ������ʾ����
		civ=(CircleImageView)findViewById(R.id.iv_contact);
		ibBack=(ImageButton)findViewById(R.id.ib_title_back);
		ibCart=(ImageButton)findViewById(R.id.ib_title_cart);

		tvTitle=(TextView) findViewById(R.id.tv_title);
		tvTitle.setText("��������");
		civ.setVisibility(View.INVISIBLE);
		ibBack.setVisibility(View.VISIBLE);
		ibCart.setVisibility(View.INVISIBLE);
		
		
		
		
		
		//������ʾ���༭���ı���ʼ��
		tvSetDate=(TextView) findViewById(R.id.tv_kuaisu_setDate);
		tvPrice=(TextView) findViewById(R.id.tv_kuaisu_price);
		tvCountDisplay=(TextView) findViewById(R.id.tv_kuaisu_count_display);



		tvTotalPrice=(TextView)findViewById(R.id.tv_kuaisu_totalPrice);
		etLadeID=(EditText) findViewById(R.id.et_kuaisu_ladeID);
		etCarName=(EditText) findViewById(R.id.et_kuaisu_carName);
		etCarID=(EditText) findViewById(R.id.et_kuaisu_carID);
		etCarCode=(EditText) findViewById(R.id.et_kuaisu_carCode);
		etCarPhone=(EditText) findViewById(R.id.et_kuaisu_carPhone);
		etOrderPhone=(EditText) findViewById(R.id.et_kuaisu_orderPhone);
		//��Ӽ��ٰ�ť
		iv_reduce=(ImageView) findViewById(R.id.iv_kuaisu_reduce);
		iv_add=(ImageView) findViewById(R.id.iv_kuaisu_add);
		//����޸�ɾ����ť
		btn_edit=(Button) findViewById(R.id.btn_kuaisu_edit);
		btn_delete=(Button) findViewById(R.id.btn_kuaisu_delete);

		Intent intent=getIntent();
		order_ABase=(XS_Order_ABase) intent.getSerializableExtra("order_ABase");
		positon=intent.getIntExtra("position",0);
		
		
		
		Myapplication.log("������Ϣ����",order_ABase+""+positon );

		//��ʼ����Ʒ����ת��Ϊdouble����

		//��ȷ������
		c=Double.parseDouble(order_ABase.getXSO_Price());
		String []a=order_ABase.getXSO_Number().split("\\.");
		String b=a[0];
		count=Integer.parseInt(b);


		Myapplication.log("������֤�㶼Ҫ��log", count+"");
	}

	/**
	 * ��ť�����¼�
	 */
	private void setListener() {
		ibBack.setOnClickListener(this);
		iv_add.setOnClickListener(this);
		iv_reduce.setOnClickListener(this);
		btn_edit.setOnClickListener(this);
		btn_delete.setOnClickListener(this);
		tvCementName.setOnClickListener(this);
	}
	/**
	 * ��ȷ����double�������ݵĳ˷�
	 */
	public static double mul(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		//���ذ�ť
		case R.id.ib_title_back:
			//����Ҫintentʵ����ת
			//ֱ�����ٵ�ǰactivity
			finish();
			break;
		//��Ӱ�ť
		case R.id.iv_kuaisu_add:
			if(count>=1){
				count++;
			}

			tvCountDisplay.setText(""+count);
			mul=mul(c,count);
			tvTotalPrice.setText("��"+mul);
			break;
			//ɾ����ť
		case R.id.iv_kuaisu_reduce:
			if(count>1){
				count--;
			}
			tvCountDisplay.setText(""+count);
			mul=mul(c,count);
			tvTotalPrice.setText("��"+mul);
			break;
			//�޸İ�ť
		case R.id.btn_kuaisu_edit:
			builder=new Builder(ManageActivity.this);
			builder.setMessage("ȷ���޸ĸö�����?");
			builder.setTitle("��ʾ");
			builder.setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					int clientid=order_ABase.getXSO_ClientID();//�ͻ�id
					String ladeid=order_ABase.getXSO_LadeID();//�������
					String areacode=order_ABase.getXSO_AreaCode();//��������
					
					String ladetype=order_ABase.getXSO_LadeType();//��������
					if(ladetype==null){
						ladetype="APP";
					}
					String cementcode=order_ABase.getXSO_CementCode();//Ʒ�ִ���
					String cementname=order_ABase.getXSO_CementName();//Ʒ������
				
					String ordernumber=tvCountDisplay.getText().toString()+".0";//�µ�����
					String orderprice=order_ABase.getXSO_Price();//�µ��۸�
					String totalprice=tvTotalPrice.getText().toString().substring(1, tvTotalPrice.getText().toString().length());//�ܽ��
					String carname=etCarName.getText().toString();//����˾������
					String carid=etCarID.getText().toString();//����˾����ʻ֤��
					String carcode=etCarCode.getText().toString();//�������ƺ�
					String carphone=etCarPhone.getText().toString();//˾���绰
					String orderphone=etOrderPhone.getText().toString();//�µ���Ա��ϵ�绰
					
					int status=0;//ĿǰĬ�ϸ���Ϊ0	
					
					Myapplication.log("ת���", cementname);
					urlParam=UrlPath.ORDER_EDIT+"?clientid="+clientid
							+"&ladeid="+ladeid
							+"&areacode="+areacode
							+"&ladetype="+"APP"
							+"&cementcode="+cementcode
							+"&cementname="+cementname
							+"&ordernumber="+ordernumber
							+"&orderprice="+orderprice
							+"&totalprice="+totalprice
							+"&carname="+carname
							+"&carid="+carid
							+"&carcode="+carcode
							+"&carphone="+carphone
							+"&orderphone="+orderphone
							+"&status="+status
							;
				
					new Thread(new Runnable() {
						StringBuffer sb=new StringBuffer();
						@Override
						public void run() {
							try {
								Myapplication.log("��ַ", urlParam);
								URL url = new URL(urlParam);
								HttpURLConnection conn=(HttpURLConnection) url.openConnection();
								conn.setConnectTimeout(5000);
								conn.setRequestMethod("GET");
								conn.connect();
								InputStream is=conn.getInputStream();
								InputStreamReader isr=new InputStreamReader(is);
								BufferedReader reader=new BufferedReader(isr);
								String line=reader.readLine();
								while(line!=null){
									sb.append(line);
									Myapplication.log("-----------", "sb:"+sb.toString());
									line=reader.readLine();
								}
								JSONObject object=new JSONObject(sb.toString());
								JSONObject object2=object.getJSONObject("status");
								String reason=object2.getString("status_reason");
								URLEncoder.encode(reason, "UTF-8");
								Myapplication.log("ת����", reason);
								Message message=new Message();
								message.what=1;
								message.obj=reason;
								Myapplication.log("��ȡ�ֶ��Ƿ���ȷ", reason);
								handler.sendMessage(message);
								
								is.close();
								isr.close();
								reader.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}).start();
					dialog.dismiss();
				}
			});
			builder.setNegativeButton("ȡ��",new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.create().show();
			break;
			//ɾ����ť
		case R.id.btn_kuaisu_delete:
			builder=new AlertDialog.Builder(ManageActivity.this);
			builder.setMessage("ȷ��ɾ���ö�����?");
			builder.setTitle("��ʾ");
			builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(final DialogInterface dialog, int which) {
					int clientid=order_ABase.getXSO_ClientID();
					int orderdid=order_ABase.getXSO_OrderID();
					urlParam=UrlPath.ORDER_DELETE+"?orderid="+orderdid+"&clientid="+clientid;
					new Thread(new Runnable() {
						StringBuffer sb=new StringBuffer();
						@Override
						public void run() {
							try {
								URL url = new URL(urlParam);
								HttpURLConnection conn=(HttpURLConnection) url.openConnection();
								conn.setConnectTimeout(5000);
								conn.setRequestMethod("GET");
								conn.connect();
								InputStream is=conn.getInputStream();
								InputStreamReader isr=new InputStreamReader(is);
								BufferedReader reader=new BufferedReader(isr);
								String line=reader.readLine();
								while(line!=null){
									sb.append(line);
									Myapplication.log("-----------", "sb:"+sb.toString());
									line=reader.readLine();
								}
								JSONObject object=new JSONObject(sb.toString());

								String result=object.getString("result");
							
								Message message=new Message();
								message.what=0;
								message.obj=result;
								Myapplication.log("��ȡ�ֶ��Ƿ���ȷ", result);
								handler.sendMessage(message);

								is.close();
								isr.close();
								reader.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}).start();
					dialog.dismiss();
				}
			});
			builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.create().show();
			break;
		//	
		case R.id.tv_kuaisu_cementName:
			showPopWindow(v);
			break;
		}
	}
	  private void showPopWindow(View v) {
		  if(popupWindow==null){
				LayoutInflater layoutInflater=(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				viewPpWindow = layoutInflater.inflate(R.layout.popup_window_listview, null);
				lvPpWindow = (ListView) viewPpWindow.findViewById(R.id.lv_popup_window);
				Myapplication.log("ppWindow���俪ʼ��", "111111111111111111111111111");
				ppWindowAdapter = new AccountFragmentPpWindowAdapter(this, removeList);
				lvPpWindow.setAdapter(ppWindowAdapter);
				Myapplication.log("ppWindow���������", "222222222222222222222222");
				//����һ��PopupWindow����
				popupWindow=new PopupWindow(viewPpWindow,tvCementName.getMeasuredWidth(),LayoutParams.WRAP_CONTENT,true);
			}
			//ʹ��۽�
			popupWindow.setFocusable(true);
			//���������������͵�����ؼ���ʧ
			popupWindow.setOutsideTouchable(true);
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
			//������ʾλ��,��tvPpWindow�·�
			popupWindow.showAsDropDown(tvCementName);
			lvPpWindow.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					itemAtPosition =  removeList.get(position);
					tvCementName.setText(itemAtPosition);
					removeList.clear();
					Myapplication.log("removeList.clear()�����ݳ��ȣ�", removeList.size()+"");
					ppWindowAdapter.notifyDataSetChanged();
					tvCementName.setText(itemAtPosition);
					removeList.addAll(list);
					removeList.remove(itemAtPosition);
					if (popupWindow != null) {
						popupWindow.dismiss();
					}
				}
			});
		
	}

	/* 
     * ��json�ַ������й���,��ֹ����ͺڿ� 
     */  
    public static String JsonFilter(String jsonstr){  
        return jsonstr.substring(jsonstr.indexOf("{")).replace("\r\n","\n");   
    }  
}
