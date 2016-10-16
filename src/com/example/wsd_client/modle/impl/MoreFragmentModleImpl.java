package com.example.wsd_client.modle.impl;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.ClientInfo;
import com.example.wsd_client.modle.IMoreFragmentModle;
import com.example.wsd_client.urlUtils.UrlPath;

/**
 * moreFragmentҳ��modle��ӿ�
 * @author wsd_leiguoqiang
 */
public class MoreFragmentModleImpl implements IMoreFragmentModle{

	@Override
	public void updateUserInfo(final Map<String, String> userInfo,
			final MoreFragmentCallBack callBack) {
		//volley��ܽ��з���������
		StringRequest sr = new StringRequest(Request.Method.POST, UrlPath.USER_INFO_EDITE, 
				new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				//�����������Ҫ�Է��ض�������ж�
				try {
					JSONObject jsonObject = new JSONObject(response);
					int total = jsonObject.getInt("total");
					//���ݳɹ�����
					if(total==1){
						//��map�����е�����ֵȫ����ֵ�����ػ����û���Ϣʵ������У������û����ݸ���
						ClientInfo clientInfo = ((Myapplication)(Myapplication.getContext())).getClientInfo().getResult().get(0);
						clientInfo.setYWC_AreaCode(userInfo.get("areacode"));
						clientInfo.setYWC_PassWord(userInfo.get("password"));
						clientInfo.setYWC_LinkPhone(userInfo.get("linkphone"));
						clientInfo.setYWC_LinkMan(userInfo.get("linkman"));
						clientInfo.setYWC_ClientName(userInfo.get("clientname"));
						clientInfo.setYWC_ClientCode(userInfo.get("clientcode"));
						Myapplication.toast("�û����óɹ�");
						//callback�������
						callBack.updateMoreFragment(clientInfo);
						//���ز��ɹ�
					}else{
						JSONObject object = jsonObject.getJSONObject("- status");
						String reason = object.getString("status_reason");
						Myapplication.toast(reason);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Myapplication.toast("���緱æ�����Ժ�����"+error.toString());
			}
		}){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return userInfo;
			}
		};
		Myapplication.getRequestQueue().add(sr);
	}


}
