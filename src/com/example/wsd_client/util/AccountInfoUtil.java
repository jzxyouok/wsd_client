package com.example.wsd_client.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.AccountInfo;
import com.example.wsd_client.urlUtils.UrlPath;

/**
 * ��ȡ�˵����ĵ��������ݹ���
 */
public class AccountInfoUtil {

	/**
	 * ͨ�����췽��������handler������ȡ����Ӧ�봫������Ӧ��fragment
	 */
	private Handler handler;

	/**
	 * ��װ�˵�������Ϣ�ļ���
	 */
	private List<AccountInfo> list = new ArrayList<AccountInfo>();

	/**
	 * ��ȡ��ǰѡ���������״̬�е�һ��
	 */
	private Integer xsstatus;

	public AccountInfoUtil(Handler handler) {
		super();
		this.handler = handler;
	}


	/**
	 * ��ȡ��������Ϣ���ĵ���Ϣ����
	 * @param map  ��װ����Ϣ�����������ݲ���
	 * @param i    ��������1ʱ�����֮ǰlist��ȡ�Ĳ������ݣ�
	 *     	                  ��������2ʱ����ʾ��ִ���������ػ�������ˢ�£������list�������
	 */
	public  void getAccountInfo(Map<String, String> map,int i){
		Myapplication.log("AccountFragment�����������󷽷���", "333333333333333");

		String pageNumString = map.get("pageNum");
		Integer pageNum=Integer.parseInt(pageNumString);

		String pageSizeString = map.get("pageSize");
		Integer pageSize=Integer.parseInt(pageSizeString);

		String starttime = map.get("starttime");
		String endtime = map.get("endtime");

		String clientidString = map.get("clientid");
		Integer clientid=Integer.parseInt(clientidString);

		String xsstatusString = map.get("xsstatus");
		xsstatus = Integer.parseInt(xsstatusString);

		String urlParam=UrlPath.BILL+"?pageNum="+pageNum+"&pageSize="
				+pageSize+"&starttime="+starttime+"&endtime="+endtime+
				"&clientid="+clientid+"&xsstatus="+xsstatus;

		getAccountInfoResult(urlParam,i);
	}



	/**
	 * ��ȡ����
	 * @param urlParam����·��ַ   tag������ɸѡ��ʽ�е�һ��
	 */
	public void getAccountInfoResult(final String urlParam, final int num){
		//��������1ʱ�����֮ǰlists��ȡ�Ĳ������ݣ���������2ʱ����ʾ��ִ���������ػ�������ˢ�£������lists�������
		if(num==1){
			list.clear();
		}else if (num==2){

		}

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

					int total = jsonObject.getInt("total");
					if(total==0){
						//��numΪ1ʱ����ʾ����Ʒ��ɸѡ��ȡ���ݣ���û������ʱ������ʾtextView
						//��num��Ϊ1����Ϊ2ʱ����ʾlistView���������ز�������������ʱ��Ҳ��ʾlistView
						if(num==1){
							Message message=Message.obtain();
							message.what=1;
							handler.sendMessage(message);
						}else if(num==2){
							Message message=Message.obtain();
							message.what=3;
							handler.sendMessage(message);
						}
					}else{
						JSONArray result = jsonObject.getJSONArray("result");
						for (int i = 0; i < result.length(); i++) {
							JSONObject object = result.getJSONObject(i);
							AccountInfo info = new AccountInfo();
							info.setXSO_CementName(object.getString("XSO_CementName"));
							info.setXSO_SetDate(object.getString("XSO_SetDate"));
							info.setXSO_TotalPrice(object.getString("XSO_TotalPrice"));
							info.setXSO_Number(object.getInt("XSO_Number"));
							info.setXSO_CarCode(object.getString("XSO_CarCode"));

							list.add(info);

						}

						Message message=Message.obtain();
						message.what=2;
						message.obj=list;
						message.arg1=xsstatus;
						handler.sendMessage(message);
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();


	}

}
