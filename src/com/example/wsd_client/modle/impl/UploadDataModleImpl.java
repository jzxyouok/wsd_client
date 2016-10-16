package com.example.wsd_client.modle.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;

import com.example.wsd_client.entity.Product;
import com.example.wsd_client.modle.IUploadDataModle;

/**
 * ��Ʒ�ϴ�modle��ӿ�ʵ���࣬������Ʒ�ϴ�ҵ��
 * 
 * @author wsd_leiguoqiang
 */
public class UploadDataModleImpl implements IUploadDataModle {

	@Override
	public void uploadDataByPost(final String urlPath, final Map<String, String> hashMap) {
		// ��û�����������ܵ�����£���http����Э���post���󷽷�

		//ͨ���첽�̶߳��������ݽ��м���
		new AsyncTask<String, Void, Boolean>(){
			
			@Override
			protected Boolean doInBackground(String... params) {
				// ���������ַ�����������ƴ���ַ���
				StringBuilder builder = new StringBuilder();
				if ((hashMap != null) && (!hashMap.isEmpty())) {

					// ����map���ϣ�ȡ�������е����ݣ���Ϊpost����Ĳ���
					for (Map.Entry<String, String> param : hashMap.entrySet()) {
						try {
							builder.append(param.getKey()).append("=").append(URLEncoder.encode(param.getValue(),"utf-8")).append("&");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
					builder.deleteCharAt(builder.length()-1);
				}
				byte[] byteArray = builder.toString().getBytes();
				try {
					// ����url����
					URL url = new URL(urlPath);
					// ����httpurlclient���Ӷ���
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					// ���������
					connection.setDoOutput(true);
					// ����ȡ������
					connection.setUseCaches(false);
					//�����������ͺͳ���
					connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//��������   
					connection.setRequestProperty("Content-Length",String.valueOf(byteArray.length));//����   
					// �õ��������������������
					OutputStream out = connection.getOutputStream();
					//�������д������
					out.write(byteArray);
					// ��ˢ���ر������
					out.flush();
					out.close();
					//�ж����ӵ���Ӧ��
					if((connection.getResponseCode())==200){
						return true;
					}else{
						return false;
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return false;
			}
			//���߳̽������ݸ���
			protected void onPostExecute(Boolean result) {
				if(result.booleanValue()){
					//��Boolean�����ֵΪtrueʱ����ҳ�������ɹ���ʾ
				}else{
					//��ҳ������ʧ����ʾ
				}
			};
		}.execute(urlPath);
	}

}
