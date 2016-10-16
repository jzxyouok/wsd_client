package com.example.wsd_client.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.urlUtils.UrlPath;

/**
 * ��ȡ�������˻����Ĺ�����
 */
public class ZhangdanAmountUtil {

	/**
	 * ͨ�����췽��������handler������ȡ����Ӧ�봫������Ӧ��fragment
	 */
	private Handler handler;


	public ZhangdanAmountUtil(Handler handler) {
		super();
		this.handler = handler;
	}


	/**
	 * ��ȡ���������û����˻����
	 * @param clientid  ���ݴ���Ŀͻ�id��ȡ
	 */
	public  void getAmount(int id){
		int clientid=id;
		String urlParam=UrlPath.TSYS_ACCOUNT+"?clientid="+clientid;

		getUrlResult(urlParam);
	}

	/**
	 * ���ݴ������ַ����ȡ�û����˻���Ȼ��ͨ��handler���ݳ�ȥ
	 * @param urlParam ��ѯ�˻�������ַ
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
					JSONArray result = jsonObject.getJSONArray("result");
					for (int i = 0; i < result.length(); i++) {
						JSONObject object = result.getJSONObject(i);
						String TotalMoney = object.getString("TotalMoney");

						Myapplication.log("��ȡ�����˻����TotalMoney��", TotalMoney);

						Message message=Message.obtain();
						message.obj=TotalMoney;
						handler.sendMessage(message);
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
