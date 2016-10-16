package com.example.wsd_client.util;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceUtils {

	/**
	 * �����û���������뵽ƫ������
	 * @param usercode  �û�����
	 * @param userpwd   �û�����
	 * @param context   �����Ķ���
	 * @return  �û���Ϣ����
	 */
	public static boolean saveInfo(String usercode,String userpwd,Context context){
		try{
			SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
			Editor edit = sp.edit();
			edit.putString("usercode", usercode);
			edit.putString("userpwd", userpwd);
			edit.commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * ��ȡƫ�������д洢���û����������
	 * @param context �����Ķ���
	 * @return map���Ϸ�װ�û���Ϣ
	 */
	public static Map<String, String> getInfo(Context context){
		try{
			SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
			String usercode = sp.getString("usercode", null);
			String userpwd = sp.getString("userpwd", null);
			Map<String, String> map=new HashMap<String, String>();
			map.put("usercode", usercode);
			map.put("userpwd", userpwd);
			return map;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ��¼֮ǰ�û��İ�ť���״̬
	 * @param checkName ������İ�ť������
	 * @param context �����Ķ���
	 * @return  �Ƿ��¼�����״̬
	 */
	public static boolean getCheckedBoolean(String checkName,Context context) {
		SharedPreferences sp = context.getSharedPreferences("checkState", Context.MODE_PRIVATE);
		boolean isCheck = sp.getBoolean(checkName, false);
		return isCheck;
	}

	/**
	 * ���밴�������״̬
	 * @param checkName  ������İ�������
	 * @param value	    �������״̬��Ϊboolean����
	 * @param context  �����Ķ���
	 */
	public static void setCheckedBoolean(String checkName,Boolean value ,Context context){
		SharedPreferences sp = context.getSharedPreferences("checkState", Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putBoolean(checkName, value);
		edit.commit();
		
	}
	
	/**
	 * ��¼�µ�ǰ��¼ʱ��ʱ�����ֵ
	 * @param i  ��ǰ��ѡ�Զ���¼ʱ������1970��1��1��0��0��0��ʱ�����ֵ
	 */
	public static void setCurrentMillies(long i,Context context){
		SharedPreferences sp=context.getSharedPreferences("time", Context.MODE_PRIVATE);
		Editor editor=sp.edit();
		editor.putLong("timeMillies", i);
		editor.commit();
		
	}
	
	/**
	 * ��ȡ�ϴε�¼ʱ��ʱ�����ֵ
	 * @param context  �����Ķ���
	 * @return  �ϴε�¼ʱ�洢��ʱ�����1970��1��1��0��0��0�ĺ���ֵ
	 */
	public static long getCurrentMillies(Context context ){
		SharedPreferences sp=context.getSharedPreferences("time", Context.MODE_PRIVATE);
		long i=sp.getLong("timeMillies", 0);
		return i;
	}
}
