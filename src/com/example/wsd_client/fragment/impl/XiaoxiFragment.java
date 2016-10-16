package com.example.wsd_client.fragment.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.wsd_client.R;
import com.example.wsd_client.activity.MainActivity;
import com.example.wsd_client.adapter.XiaoxiAdapter;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.ClientInfo;
import com.example.wsd_client.entity.NewsResult;
import com.example.wsd_client.entity.YW_ClientInfo;
import com.example.wsd_client.util.CircleImageView;
import com.example.wsd_client.util.RefreshListView;
import com.example.wsd_client.util.RefreshListView.OnRefreshListener;
import com.example.wsd_client.util.XiaoxiUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class XiaoxiFragment extends Fragment implements OnClickListener,OnCheckedChangeListener{
	private TextView tvTitle;
	private View view;
	private CircleImageView civ;
	private ImageButton ibBack;
	private ImageButton ibCart;
	private RadioGroup rgXiaoxiChoose;
	private RefreshListView lvNews;
	private TextView tvNoNews;
	private int pageNum=0;


	//��ȡXiaoxiUtil�����л�ȡ�ķ�������Ϣ�ļ���
	private List<NewsResult> lists;
	//��XiaoxiUtil�����л�ȡ�ķ�������Ϣ�ĵ����װ��list�����У������ݸ���������������ʾ������Ϣ�ڵ�һ��λ��
	private List<NewsResult> list=new ArrayList<NewsResult>();
	
	
	//�ͻ�id
	private int clientid;
	/**
	 * ��ȡXiaoxiUtil���������������Ϣ����
	 * ������Ϊ1��û����Ϣ
	 * ������Ϊ2������Ϣ����ȡ��Ϣ��list����
	 */
	private Handler handler=new Handler(){

		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				tvNoNews.setVisibility(View.VISIBLE);
				lvNews.setVisibility(View.INVISIBLE);
				break;

			case 2:
				tvNoNews.setVisibility(View.INVISIBLE);
				lvNews.setVisibility(View.VISIBLE);
				lists = (List<NewsResult>) msg.obj;
				Myapplication.log("��ȡ��Ϣlists�ĳ��ȣ�", lists.size()+"");

				setAdapter();

				//������ɣ����ؽŲ���
				lvNews.onRefreshComplete();

				break;

			case 3:
				Myapplication.log("��Ϣ����liseViewû����ʱ��", "------1---------");
				//��������ʱû�����ݣ�ֱ�����ؽŲ���
				lvNews.onRefreshComplete();
				Myapplication.toast("û�и�����������");
				Myapplication.log("��Ϣ����liseViewû����ʱ��", "------2---------");
			default:
				break;
			}
		};
	};


	private XiaoxiUtil xiaoxiUtil=new XiaoxiUtil(handler);
	private Map<String, Integer> map=new HashMap<String, Integer>();
	//������
	private XiaoxiAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_xiaoxi, null);

		initView();

		setListener();

		return view;
	}

	/**
	 * ��ʼ���ؼ�
	 */
	private void initView() {
		tvTitle=(TextView) view.findViewById(R.id.tv_title);
		civ=(CircleImageView) view.findViewById(R.id.iv_contact);
		ibBack=(ImageButton) view.findViewById(R.id.ib_title_back);
		ibCart=(ImageButton) view.findViewById(R.id.ib_title_cart);
		rgXiaoxiChoose=(RadioGroup) view.findViewById(R.id.rg_xiaoxi_choose);
		lvNews=(RefreshListView) view.findViewById(R.id.lv_xiaoxi);
		tvNoNews=(TextView) view.findViewById(R.id.tv_no_news);

		//��ȡ�û���Ϣ
		Myapplication app=(Myapplication) Myapplication.getContext();
		YW_ClientInfo clientInfo = app.getClientInfo();
		List<ClientInfo> result = clientInfo.getResult();
		clientid = result.get(0).getYWC_ClientID();

		tvTitle.setText("��Ϣ����");
		civ.setVisibility(View.INVISIBLE);
		ibBack.setVisibility(View.VISIBLE);
		ibCart.setVisibility(View.INVISIBLE);

		//�״μ��ؽ����ʱ����ʾϵͳ��Ϣ
		map.put("pageNum", pageNum);
		map.put("pageSize", 10);
		map.put("clientid", clientid);
		map.put("status", 1);
		xiaoxiUtil.getNews(map,1);
	}

	/**
	 * ���ü���
	 */
	private void setListener() {
		ibBack.setOnClickListener(this);
		rgXiaoxiChoose.setOnCheckedChangeListener(this);
		lvNews.setRefreshListener(new OnRefreshListener() {

			@Override
			public void onLoadMore() {
				pageNum++;
				map.put("pageNum", pageNum);
				xiaoxiUtil.getNews(map,2);
			}
		});
	}

	/**
	 * ����������
	 */
	private void setAdapter() {
		list.clear();
		Myapplication.log("��Ϣ����setAdapter��"," ----------------------");
		//�������lists���ϵ����ݽ��е����װ������ʱ��Ľ�Զ��������
		for (int i = lists.size()-1; i>=0; i--) {
			list.add(lists.get(i));
		}
		Myapplication.log("��Ϣ����setAdapter��",list.size()+" ----------------------");
		
		adapter = new XiaoxiAdapter(getActivity(), list);
		
		lvNews.setAdapter(adapter);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_title_back:
			Intent intent = new Intent (getActivity(),MainActivity.class);
			startActivity(intent);
			//���ٵ�ǰ��Ƭ���ڵ�Activity
			getActivity().finish();
			break;

		default:
			break;
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		//���֮ǰ��ȡ��list������������ݣ�����adapter�������������֮ǰlistView����ʾ�����ݣ��Ӷ���ʾ�µ�����
		lists.clear();
		adapter.notifyDataSetChanged();

		switch (checkedId) {
		case R.id.rb_xiaoxi_system:
			map.put("pageNum", 0);
			map.put("pageSize", 10);
			map.put("clientid", clientid);
			map.put("status", 1);
			xiaoxiUtil.getNews(map,1);
			break;

		case R.id.rb_xiaoxi_order:
			map.put("pageNum", 0);
			map.put("pageSize", 10);
			map.put("clientid", clientid);
			map.put("status", 2);
			xiaoxiUtil.getNews(map,1);
			break;

		case R.id.rb_xiaoxi_amount:
			map.put("pageNum", 0);
			map.put("pageSize", 10);
			map.put("clientid", clientid);
			map.put("status", 3);
			xiaoxiUtil.getNews(map,1);
			break;

		case R.id.rb_xiaoxi_acount:
			map.put("pageNum", 0);
			map.put("pageSize", 10);
			map.put("clientid", clientid);
			map.put("status", 4);
			xiaoxiUtil.getNews(map,1);
			break;
		}

	}
}
