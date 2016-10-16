package com.example.wsd_client.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wsd_client.R;
import com.example.wsd_client.activity.CollectionActivity;
import com.example.wsd_client.activity.LoginActivity;
import com.example.wsd_client.activity.OrderModleActivity;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.constant.Constant;
import com.example.wsd_client.entity.ClientInfo;
import com.example.wsd_client.entity.YW_ClientInfo;
import com.example.wsd_client.presenter.impl.MoreFragmentPresenterImpl;
import com.example.wsd_client.util.SharedPreferenceUtils;
/**
 * moreFragmentҳ��veiw��ʵ����
 */
public class MoreFragment extends Fragment implements IMoreFragment,OnClickListener{
	private View view;
	/**
	 * ��һ������
	 */
	@ViewInject(R.id.ll_fragment_more_first)
	private LinearLayout ll_first_container;
	/**
	 * �ڶ�������
	 */
	@ViewInject(R.id.ll_fragment_more_seconde)
	private LinearLayout ll_seconde_container;
	/**
	 * �û�ͷ��
	 */
	@ViewInject(R.id.iv_fragment_more_userhead)
	private com.example.wsd_client.util.CircleImageView iv_userHead;
	/**
	 * ��¼��ť
	 */
	@ViewInject(R.id.tv_fragment_more_login)
	private TextView tv_login;
	/**
	 * �û���ʾ��ť
	 */
	@ViewInject(R.id.tv_fragment_more_username)
	private TextView tv_username;
	/**
	 * ȷ���޸İ�ť
	 */
	@ViewInject(R.id.btn_fragment_more_confirm)
	private Button btn_confirm;

	private MoreFragmentPresenterImpl presenter;
	/**
	 * map���ϣ������û��޸����ݵķ�װ
	 */
	private Map<String, String> userInfo = new HashMap<String, String>();
	/**
	 * �û���¼����
	 */
	@ViewInject(R.id.tv_fragment_more_client_code)
	private TextView tv_client_code;
	/**
	 * �û�����
	 */
	@ViewInject(R.id.et_fragment_more_clientname)
	private EditText et_client_name;
	/**
	 * ��������
	 */
	@ViewInject(R.id.et_fragment_more_areacode)
	private EditText et_areacode;
	/**
	 * ��ϵ��
	 */
	@ViewInject(R.id.et_fragment_more_linkman)
	private EditText et_linkman;
	/**
	 * �绰����
	 */
	@ViewInject(R.id.et_fragment_more_linkphone)
	private EditText et_linkphone;
	/**
	 * �û�����
	 */
	@ViewInject(R.id.et_fragment_more_password)
	private EditText et_password;
	/**
	 * ���ؼ�
	 */
	@ViewInject(R.id.iv_more_fragment_seconde_back)
	private ImageView iv_back;
	/**
	 * �˳�����ť
	 */
	@ViewInject(R.id.tv_more_fragment_pager_out)
	private TextView tv_out;
	/**
	 * �ҵĶ���ģʽ
	 */
	@ViewInject(R.id.tv_more_fragment_pager_my_order_modle)
	private TextView tv_order_modle;
	private Myapplication app;
	/**
	 * �ҵ��ղ�
	 */
	@ViewInject(R.id.tv_more_fragment_pager_my_collect)
	private TextView tv_my_collection;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_more, null);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		x.view().inject(this, view);

		init();

		//��ȡ�û�����
		YW_ClientInfo clientInfo = app.getClientInfo();
		List<ClientInfo> result = clientInfo.getResult();
		String clientcode = result.get(0).getYWC_ClientCode();
		tv_client_code.setText(clientcode);

		initListeners();
	}

	private void init() {
		app = ((Myapplication)(Myapplication.getContext()));
		//��ʾ�û������¼��ť
		String userName = app.getClientInfo().getResult().get(0).getYWC_ClientName();
		if(!TextUtils.isEmpty(userName)){
			//�ж��û��Ƿ��¼����¼���û���ʾ�л�
			tv_username.setVisibility(View.VISIBLE);
			tv_login.setVisibility(View.GONE);
			tv_username.setText(userName);
			iv_userHead.setClickable(true);
		}else{
			//�ж��û��Ƿ��¼����¼���û���ʾ�л�
			tv_username.setVisibility(View.GONE);
			tv_login.setVisibility(View.VISIBLE);
			iv_userHead.setClickable(false);
		}
	}

	private void initListeners() {
		iv_userHead.setOnClickListener(this);
		tv_login.setOnClickListener(this);
		btn_confirm.setOnClickListener(this);
		iv_back.setOnClickListener(this);
		tv_out.setOnClickListener(this);
		tv_order_modle.setOnClickListener(this);
		tv_my_collection.setOnClickListener(this);
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//��¼��ť
		case R.id.tv_fragment_more_login:
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			getActivity().startActivityForResult(intent, Constant.REQUESTCODE_MORE_FRAGMENT_TO_LOGIN_ACTIVITY);
			break;
			//ͷ��ť
		case R.id.iv_fragment_more_userhead:
			//ҳ������л�
			TranslateAnimation animation_one = new TranslateAnimation(0, -ll_first_container.getWidth(), 0, 0);
			animation_one.setDuration(1000);
			ll_first_container.startAnimation(animation_one);
			ll_first_container.setVisibility(View.INVISIBLE);
			ll_seconde_container.setVisibility(View.VISIBLE);
			TranslateAnimation animation_two = new TranslateAnimation(ll_seconde_container.getWidth(), 0, 0, 0);
			animation_two.setDuration(1000);
			ll_seconde_container.startAnimation(animation_two);
			break;
		case R.id.btn_fragment_more_confirm:
			//���û��Ѿ���¼�����֮�²�ִ�д˲���
			if(!TextUtils.isEmpty(tv_username.getText().toString())){
				//�õ�������Ҫ���ĵ���Ϣ����װ��map������
				//���ͻ�id��ӵ�������,ͨ�����������ص���Ӧ����
				int clientId = ((Myapplication)(Myapplication.getContext())).getClientInfo().getResult().get(0).getYWC_ClientID();
				userInfo.put("clientid", clientId+"");
				userInfo.put("clientcode", tv_client_code.getText().toString());
				userInfo.put("clientname", et_client_name.getText().toString());
				userInfo.put("linkman", et_linkman.getText().toString());
				userInfo.put("linkphone", et_linkphone.getText().toString());
				userInfo.put("password", et_password.getText().toString());
				userInfo.put("areacode", et_areacode.getText().toString());
				//����presenter���������û������޸�
				presenter = new MoreFragmentPresenterImpl(this, userInfo);
				presenter.updateUserInfo();
			}
			break;
			//���ؼ�
		case R.id.iv_more_fragment_seconde_back:
			ll_first_container.setVisibility(View.VISIBLE);
			TranslateAnimation animation_four = new TranslateAnimation(-ll_first_container.getWidth(), 0, 0, 0);
			animation_four.setDuration(1000);
			ll_first_container.startAnimation(animation_four);

			TranslateAnimation animation_three = new TranslateAnimation(0, ll_seconde_container.getWidth(), 0, 0);
			animation_three.setDuration(1000);
			ll_seconde_container.startAnimation(animation_three);
			ll_seconde_container.setVisibility(View.INVISIBLE);

			break;
			//�˳���ť
		case R.id.tv_more_fragment_pager_out:
			//�����Ի����˳���ǰ�˺ź͹رճ���
			final AlertDialog dialog =  new AlertDialog.Builder(getActivity()).create();
			View view = View.inflate(getActivity(), R.layout.dialog_modle_out_order, null);
			LinearLayout ll_close_current_user = (LinearLayout) view.findViewById(R.id.ll_close_dialog_close_current_user);
			LinearLayout ll_close_app = (LinearLayout) view.findViewById(R.id.ll_close_dialog_close_app);
			dialog.setView(view, 0, 0, 0, 0);
			dialog.show();
			//����dialog��С
			LayoutParams params = dialog.getWindow().getAttributes();
			params.height = (int) (ll_first_container.getHeight()*0.5);
			params.width = (int) (ll_first_container.getWidth()*0.8);
			dialog.getWindow().setAttributes(params);
			dialog.setCanceledOnTouchOutside(true);

			ll_close_current_user.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					//�û���Ϣ���
					app.getClientInfo().deleteClientInfo();
					//��ת����¼����
					SharedPreferenceUtils.setCheckedBoolean("cbAutomatic", false, getActivity());
					Intent intent = new Intent(getActivity(), LoginActivity.class);
					intent.putExtra("fromMoreFragment", 1);
					startActivity(intent);

				}
			});
			ll_close_app.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					//�ر�Ӧ�ó���
					Myapplication.finishActivity(Myapplication.getList());
				}
			});
			break;
			//����ģʽ
		case R.id.tv_more_fragment_pager_my_order_modle:
			Intent in = new Intent(getActivity(), OrderModleActivity.class);
			startActivity(in);
			break;
			//�ҵ��ղ�
		case R.id.tv_more_fragment_pager_my_collect:
			Intent intent_collection = new Intent(getActivity(), CollectionActivity.class);
			startActivity(intent_collection);
			break;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//������ʾ�û�����Ϣ
		if(requestCode==Constant.REQUESTCODE_MORE_FRAGMENT_TO_LOGIN_ACTIVITY
				&&resultCode==getActivity().RESULT_OK){
			iv_userHead.setClickable(true);
			String userName = ((Myapplication)(Myapplication.getContext())).getClientInfo().getResult().get(0).getYWC_ClientName();
			tv_username.setText(userName);
			tv_username.setVisibility(View.VISIBLE);
			tv_login.setVisibility(View.GONE);
		}
	}

	//���½���ʹ��
	@Override
	public void updateView(ClientInfo clientInfo) {
		//��ʾ��һ�㣬���صڶ���
		ll_first_container.setVisibility(View.VISIBLE);
		ll_seconde_container.setVisibility(View.GONE);
		//������ʾ�û�����Ϣ���û���Ϣʵ������Ҫ��������ֵ,��modle����У��û������޸ĳɹ�֮����и�������ֵ
		tv_username.setText(clientInfo.getYWC_ClientName());
	}

	@Override
	public void onResume() {
		super.onResume();
	}

}
