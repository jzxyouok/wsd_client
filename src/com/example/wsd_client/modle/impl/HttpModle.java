package com.example.wsd_client.modle.impl;

import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.wsd_client.activity.LoginActivity;
import com.example.wsd_client.application.Myapplication;
import com.google.gson.Gson;
/**
 * ����ࣺ�ṩ������ʹ��߷���
 * @author wsd_leiguoqiang
 */
public class HttpModle {
	/**
	 * @ʹ�ó����������ȡ���ݣ����������ݽ�����Ӧ�Ĵ������磺���ݳ��֣�
	 * @ʹ��λ�ã�mvcģʽ�У�activity�������
	 * @param t:��������������ݷ��������ص��ַ������Զ����ʵ�������ͱ���
	 * @param url���������ӿڵ�ַ
	 * @param params���ӿڲ�������
	 * @param callBack���ص�����
	 */
	public<T> void getData(final Class<T> t,String url, final Map<String, String> params,final HttpModleCallBack callBack){
		StringRequest sr = new StringRequest(Request.Method.POST, url, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				Gson gson = new Gson();
				T instance = gson.fromJson(response, t);
				callBack.httpModleSuccessCallBack(instance);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				callBack.httpMdoleErrorCallBack();
			}
		}){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return params;
			}
		};
		Myapplication.getRequestQueue().add(sr);
	}
	/**
	 * ������лص��ӿ�
	 * @author wsd_leiguoqiang
	 */
	public interface HttpModleCallBack{
		/**
		 * ���ʷ������ɹ�ʱ���õķ���
		 * @param object������ʵ�����
		 */
		public void httpModleSuccessCallBack(Object object); 
		/**
		 * ���ʷ�����ʧ��ʱ���õķ���
		 */
		public void httpMdoleErrorCallBack();
	}
}
