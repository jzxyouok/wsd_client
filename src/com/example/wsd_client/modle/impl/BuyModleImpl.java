package com.example.wsd_client.modle.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.BuyReponse;
import com.example.wsd_client.modle.IBuyModle;
import com.example.wsd_client.urlUtils.UrlPath;

/**
 * ����modleʵ���࣬�����ڹ������
 * @author wsd_leiguoqiang
 */
public class BuyModleImpl implements IBuyModle{

	@SuppressWarnings("unchecked")
	@Override
	public void buyFromCart(List<Map<String, String>> listBuyParam,
			BuyModleCallBack callBack, List<String> list_YWM_ID) {
		//���ǵ�ͬʱ�����Ʒ���򣬲��ұ������ݵ�һ���ԣ����첽�߳�ȥ�����������
		//�����������Ʒid������ͬ���������ʴ���һһ��Ӧ����ȷ��ϵ
		int i = 0;
		for(Map<String, String> buyParam:listBuyParam){
			new BuyAsyncTask(buyParam, callBack,list_YWM_ID.get(i),(i+1)).execute(buyParam);
			Myapplication.log("��Ʒid����", list_YWM_ID.get(i)+"");
			Myapplication.log("�̱߳�Ǻ�", (i+1)+"");
			i++;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void buyFromProductDetials(Map<String, String> buyParam,
			BuyModleCallBack callBack) {
		new BuyAsyncTask(buyParam, callBack, "0", 0).execute(buyParam);
	}

	/**
	 * �Զ����첽�߳�
	 * @author wsd_leiguoqiang
	 * @param BuyReponse:��������Ӧʵ�����
	 */
	class BuyAsyncTask extends AsyncTask<Map<String, String>, Void, BuyReponse>{
		/**
		 * �������
		 */
		private Map<String, String> buyParam;
		/**
		 * �ص�����
		 */
		private BuyModleCallBack callBack;
		/**
		 * ��Ʒid���(������Ʒ����Ĭ��0)
		 */
		private String productId;
		/**
		 * �߳�˳����(������Ʒ����Ĭ��0)
		 */
		private int index_flag;

		public BuyAsyncTask(Map<String, String> buyParam,
				BuyModleCallBack callBack,String productId,int index_flag) {
			super();
			this.buyParam = buyParam;
			this.callBack = callBack;
			this.productId = productId;
			this.index_flag = index_flag;
		}

		@Override
		protected BuyReponse doInBackground(Map<String, String>... params) {
			//���з���������,��ȡ���ʲ���
			StringBuilder stringbuilder = new StringBuilder();
			//�������ݷ�װʵ��
			BuyReponse buyreponse = new BuyReponse();
			//ȡ������,������stringbuilder��
			for(String key:buyParam.keySet()){
				try {
					stringbuilder.append(key).append("=").append(URLEncoder.encode(buyParam.get(key),"utf-8")).append("&");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			stringbuilder.deleteCharAt(stringbuilder.length()-1);
			Myapplication.log("-----�������", stringbuilder.toString());
			//���ַ���ת����byte����
			byte[] bytearray = stringbuilder.toString().getBytes();
			try {
				URL url = new URL(UrlPath.BUY);
				//��ȡ���Ӷ���
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				//�������󷽷�
				connection.setRequestMethod("POST");
				//�������������
				connection.setDoInput(true);
				connection.setDoOutput(true);
				//���ò�����
				connection.setUseCaches(false);
				connection.setConnectTimeout(5000);
				//���ò����ı�����
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				connection.setRequestProperty("Content-Length", String.valueOf(bytearray.length));
				OutputStream os;
				os = connection.getOutputStream();
				//�����ݴ��������
				os.write(bytearray);
				os.flush();
				Myapplication.log("----��������Ӧ��", connection.getResponseCode()+"");
				//��ȡ���������
				InputStream is = connection.getInputStream();
				//������Ӧ���ж����������Ƿ�ɹ�
				if(connection.getResponseCode()==200){
					//���������л�ȡ���������ص�����
					StringBuilder sb = new StringBuilder();
					//���ֽ���ת���ɻ����ַ�������߶�ȡ�ٶ�
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					String string = br.readLine();
					while(string!=null){
						sb.append(string);
						string = br.readLine();
					}
					br.close();
					Myapplication.log("--��������Ӧ����", sb.toString());
					//����json����
					JSONObject jsonObject = new JSONObject(sb.toString());
					
					String total = jsonObject.getString("total");
					buyreponse.setTotal(total);
					
					String result = jsonObject.getString("result");
					buyreponse.setResult(result);
					
					JSONObject status = jsonObject.getJSONObject("status");
					String reason = status.getString("status_reason");
					buyreponse.setReason(reason);
				}
				/**
				 *  {
    					"status": {
        						"status_code": -1,
        						"status_reason": "������1469412631340�Ѿ����ڣ�����������"
    							},
    					"result": null,
    					"total": null
					}
				 **/
				is.close();
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return buyreponse;
		}

		@Override
		protected void onPostExecute(BuyReponse result) {
			super.onPostExecute(result);
			//���ݷ�������Ӧ���ݽ�����ز���
			if(result.getTotal().equals("1")){
				//���ûص����󣬽������߳���ز���
				//��������ݸ��£���ת����������ҳ��
				callBack.updateAndToBuyCenter(productId,index_flag);
			}else{
				Myapplication.toast(result.getReason());
			}
		}
	}
}
