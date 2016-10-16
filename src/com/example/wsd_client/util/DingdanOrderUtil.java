package com.example.wsd_client.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.JSONObject;


import android.os.Handler;
import android.os.Message;

import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.urlUtils.UrlPath;

/**
 * ʵ�ֶ������ĵĶ�����ɾ�Ĺ���
 */
public class DingdanOrderUtil {

	/**
	 * ͨ�����췽��������handler������ȡ����Ӧ�봫������Ӧ��fragment
	 */
	private Handler handler;


	public DingdanOrderUtil(Handler handler) {
		super();
		this.handler = handler;
	}


	/**
	 * ���������Ӽ��¶�������
	 * @param urlParam  ��ַ����
	 * @param map  ��Ҫ���ݷ�������map��������
	 */
	public  void addOrder(Map<String, String> map){
		String clientidString = map.get("clientid");
		Myapplication.log("DingdanUtil�ͻ�����clientid��", clientidString);

		int clientid=Integer.parseInt(clientidString);


		String ladeid = map.get("ladeid");
		String areacode = map.get("areacode");
		String ladetype = map.get("ladetype");
		String cementcode = map.get("cementcode");
		String cementname = map.get("cementname");
		String ordernumber = map.get("ordernumber");
		String orderprice = map.get("orderprice");
		String totalprice = map.get("totalprice");
		String carname = map.get("carname");
		String carid = map.get("carid");
		String carcode = map.get("carcode");
		String carphone = map.get("carphone");
		String orderphone = map.get("orderphone");

		String statusString = map.get("status");
		int  status=Integer.parseInt(statusString);

		String urlParam=UrlPath.BUY+"?clientid="+clientid+"&ladeid="+ladeid+
				"&areacode="+areacode+"&ladetype="+ladetype+"&cementcode="+
				cementcode+"&cementname="+cementname+"&ordernumber="+
				ordernumber+"&orderprice="+orderprice+"&totalprice="+
				totalprice+"&carname="+carname+"&carid="+carid+"&carcode="+
				carcode+"&carphone="+carphone+"&orderphone="+orderphone+
				"&status="+status;

		getUrlResult(urlParam);
	}

	/**
	 * �������޸Ķ������ܣ����������޷��޸�	
	 * @param urlParam  ��ַ����
	 * @param map  ��Ҫ���ݷ�������map��������
	 */
	public void editOrder(Map<String, String> map){
		String clientidString = map.get("clientid");
		Myapplication.log("DingdanUtil�ͻ�����clientid��", clientidString);

		int clientid=Integer.parseInt(clientidString);


		String ladeid = map.get("ladeid");
		String areacode = map.get("areacode");
		String ladetype = map.get("ladetype");
		String cementcode = map.get("cementcode");
		String cementname = map.get("cementname");
		String ordernumber = map.get("ordernumber");
		String orderprice = map.get("orderprice");
		String totalprice = map.get("totalprice");
		String carname = map.get("carname");
		String carid = map.get("carid");
		String carcode = map.get("carcode");
		String carphone = map.get("carphone");
		String orderphone = map.get("orderphone");

		String statusString = map.get("status");
		int  status=Integer.parseInt(statusString);

		String urlParam=UrlPath.EDIT+"?clientid="+clientid+"&ladeid="+ladeid+
				"&areacode="+areacode+"&ladetype="+ladetype+"&cementcode="+
				cementcode+"&cementname="+cementname+"&ordernumber="+
				ordernumber+"&orderprice="+orderprice+"&totalprice="+
				totalprice+"&carname="+carname+"&carid="+carid+"&carcode="+
				carcode+"&carphone="+carphone+"&orderphone="+orderphone+
				"&status="+status;

		getUrlResult(urlParam);




	}

	/**
	 * �������ĵĶ���ɾ������
	 * @param map ���ݴ�������۵���id�Ϳͻ�id����ɾ����������
	 */
	public void delete(Map<String, String> map){
		String orderidString = map.get("orderid");
		int orderid=Integer.parseInt(orderidString);

		String clientidString = map.get("clientid");
		int clientid=Integer.parseInt(clientidString);

		String urlParam=UrlPath.DELET+"?orderid="+orderid+"&clientid"+clientid;

		getUrlResult(urlParam);
	}


	/**
	 * �������磬��ȡ��������Ӧ�룬ִ����ɾ�Ĺ���
	 * @param url   �������������ַ����
	 * @return  ����õ���Ӧ��ͨ��handler���ݸ������õ�fragment
	 * 		total�� 1.��������Ϊnull����Ϊ0ʱ����ʾʧ�ܣ�2.��Ϊ1������ֵʱ����ʾ�ɹ�
	 */
	public  void getUrlResult(final String urlParam){

		new Thread(new Runnable() {


			@Override
			public void run() {
				StringBuffer sb=new StringBuffer();

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

					JSONObject jsonObject=new JSONObject(sb.toString());
					String total = jsonObject.getString("total");

					Myapplication.log("�������磬���������ķ���ֵtotal��", total);

					Message message=Message.obtain();
					message.obj=total;
					handler.sendMessage(message);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}


}
