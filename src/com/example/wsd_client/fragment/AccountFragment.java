package com.example.wsd_client.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.example.wsd_client.R;
import com.example.wsd_client.adapter.AccountFragmentPpWindowAdapter;
import com.example.wsd_client.adapter.AccountListViewAdapter;
import com.example.wsd_client.application.Myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.wsd_client.entity.AccountInfo;
import com.example.wsd_client.entity.ClientInfo;
import com.example.wsd_client.entity.YW_ClientInfo;
import com.example.wsd_client.util.AccountInfoUtil;
import com.example.wsd_client.util.RefreshListView;
import com.example.wsd_client.util.RefreshListView.OnRefreshListener;
import com.example.wsd_client.util.CalendarViewUtil.DateUtils;
import com.example.wsd_client.util.CalendarViewUtil.MonthDateView.DateClick;

/**
 * �˵�����
 */
@SuppressLint("HandlerLeak")
public class AccountFragment extends Fragment implements OnClickListener{
	//	private Cart cart;
	private Myapplication app;

	private View viewPpWindow;
	private ListView lvPpWindow;
	private PopupWindow popupWindow;
	private AccountFragmentPpWindowAdapter ppWindowAdapter;
	private String itemAtPosition;

	//listView������
	private AccountListViewAdapter lvAdapter;

	private TextView tvStarttime,tvEndtime,tvChoose,tvNoAccount;
	private Button btnSearch;
	private RefreshListView lvShow;
	private View view;

	//��װpopupWindowҪ��ʾ������
	private List<String> list;
	//��ǰ�·�
	private Integer month;
	//��ǰ���
	private Integer year;
	//��ǰ����
	private Integer day;
	//�ͻ�id
	private int clientid;

	//�Զ���Dialog�Ŀؼ���ʼ��
	private ImageView ivLeft;
	private ImageView ivRight;
	private TextView tvDate;
	private TextView tvWeek;
	private TextView tvToday;
	private com.example.wsd_client.util.CalendarViewUtil.MonthDateView monthDateView;
	private Button btnEnsure;
	private Button btnCancel;

	//����j�ĸ�ֵ����j=1ʱ����ʾ�ͻ�ѡ��ʼʱ�䣻��j=2ʱ����ʾ�ͻ�ѡ�����ʱ��
	private int j;

	//��Ϊ��ȡ����ҳ���Ĳ���
	private int pageNum=0;

	//���ڷ�װ��list�����г���tvChoose����ʾ���������������
	private List<String> removeList;

	//Ҫ���е����ݼ���
	private List<AccountInfo> lists;

	private Handler handler=new Handler(){


		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			//1:��ʾ����ɸѡ����û����   2����ʾ������  3����ʾlistView��������û������
			switch (msg.what) {
			case 1:
				lvShow.setVisibility(View.INVISIBLE);
				tvNoAccount.setVisibility(View.VISIBLE);
				break;

			case 2:
				lvShow.setVisibility(View.VISIBLE);
				tvNoAccount.setVisibility(View.INVISIBLE);

				Myapplication.log("handler��ʼ���У�","---------------");

				lists = (List<AccountInfo>) msg.obj;
				int i=msg.arg1;

				Myapplication.log("List<AccountInfo>�ĳ��ȣ�", lists.size()+"");
				lvAdapter = new AccountListViewAdapter(getActivity(), lists,i);
				lvShow.setAdapter(lvAdapter);

				//������ɣ����ؽŲ���
				lvShow.onRefreshComplete();

				break;

			case 3:
				//��������ʱû�����ݣ�ֱ�����ؽŲ���
				lvShow.onRefreshComplete();
				Myapplication.toast("û�и�����������");

			}


		};
	};

	private AccountInfoUtil accountInfoUtil=new AccountInfoUtil(handler);
	//��������Ĳ�������
	private Map<String, String> map;

	private AlertDialog dialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_account, null);

		initView();

		initData();

		setListener();


		return view;
	}

	/**
	 * ��ʼ���ؼ�
	 */
	private void initView() {
		tvStarttime=(TextView) view.findViewById(R.id.tv_account_startTime);
		tvEndtime=(TextView) view.findViewById(R.id.tv_account_endTime);
		tvChoose=(TextView) view.findViewById(R.id.tv_account_choose);
		tvNoAccount=(TextView) view.findViewById(R.id.tv_no_news_account);
		btnSearch=(Button) view.findViewById(R.id.btn_account_choose);
		lvShow=(RefreshListView) view.findViewById(R.id.lv_account);
	}

	/**
	 * ��ʼ������
	 */
	@SuppressLint("SimpleDateFormat")
	private void initData() {

		//��ȡ��ǰϵͳʱ��
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		Myapplication.log("��ǰϵͳʱ��time��", time);
		//��ȡ��ǰ����ݺ�����
		String yearString=time.substring(0, 4);
		year = Integer.parseInt(yearString); 

		String monthString=time.substring(5, 7);
		month = Integer.parseInt(monthString);
		Myapplication.log("��ǰ����year����month��", year+"-------"+month);

		String dayString=time.substring(8, 10);
		day = Integer.parseInt(dayString);

		//��ȡ�����������
		int maxDay=DateUtils.getMonthDays(year, month-1);
		//�жϵ�ǰʱ���Ƿ�Ϊ��ĩ����ĩ���һ�죬��ѯʱ����ʱ�������ӳ�һ��
		int newDay=0;
		int newYear = 0 ;
		int newMonth=0;

		if(day==maxDay){
			newDay=1;
			if(month==12){
				newYear=year+1;
				newMonth=1;
			}else{
				newMonth=month+1;
				newYear=year;
			}
		}else{
			newDay=day+1;
			newYear=year;
			newMonth=month;
		}

		//��ȡ��ǰ�Ŀͻ�id
		app=(Myapplication) Myapplication.getContext();
		YW_ClientInfo clientInfo = app.getClientInfo();
		List<ClientInfo> result = clientInfo.getResult();
		clientid = result.get(0).getYWC_ClientID();

		String starttime=year+"-"+month+"-01";
		//��ʾ�Ľ���ʱ��
		String endtime=year+"-"+month+"-"+day;
		//��ѯ��ʵ�ʽ���ʱ��
		String newEndTime=newYear+"-"+newMonth+"-"+newDay;
		Myapplication.log("ʵ�ʲ�ѯ�Ľ���ʱ�䣺", newEndTime);
		
		tvStarttime.setText(starttime);
		tvEndtime.setText(endtime);

		//��������ѡ��ʽ�����ݼ���
		list=new ArrayList<String>();
		list.add("Ʒ�ֻ���");
		list.add("���ڻ���");
		list.add("���ƻ���");
		list.add("����+Ʒ��");
		list.add("����+����");

		//��װ�Ƴ��������
		removeList = new ArrayList<String>();
		removeList.addAll(list);
		removeList.remove(0);
		//����Ĭ����ʾ����
		tvChoose.setText(list.get(0));
		itemAtPosition="Ʒ�ֻ���";

		map = new HashMap<String, String>();
		map.put("pageNum", pageNum+"");
		map.put("pageSize", "10");
		map.put("starttime",starttime);
		map.put("endtime",newEndTime);
		map.put("clientid", clientid+"");
		map.put("xsstatus", "0");
		Myapplication.log("accountInfoUtil:", "��������ʼ");
		accountInfoUtil.getAccountInfo(map,1);
		Myapplication.log("accountInfoUtil:", "�����������");
	}


	/**
	 * ������ť����¼�
	 */
	private void setListener() {
		tvStarttime.setOnClickListener(this);
		tvEndtime.setOnClickListener(this);
		tvChoose.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		//�������غ�����ˢ�¼���
		lvShow.setRefreshListener(new OnRefreshListener() {

			@Override
			public void onLoadMore() {
				pageNum++;
				map.put("pageNum", pageNum+"");
				accountInfoUtil.getAccountInfo(map,2);

			}
		});
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.tv_account_startTime:
			//���ͻ�ѡ��ʼʱ��ʱ����j���ó�1
			j=1;
			getTimeDialog();
			break;

		case R.id.tv_account_endTime:
			//���ͻ�ѡ��ʼʱ��ʱ����j���ó�1
			j=2;
			getTimeDialog();
			break;

		case R.id.tv_account_choose:

			showPopWindow(v);
			break;

		case R.id.btn_account_choose:

			search();
			break;
		}

	}  


	/**
	 * ��ʾ�Զ���AlertDialog
	 * i:������Ϊ1ʱ����ʾ�ͻ�ѡ��ʼʱ��    ������Ϊ2ʱ����ʾ�ͻ�ѡ�����ʱ��
	 */
	private void getTimeDialog() {
		AlertDialog.Builder builder= new AlertDialog.Builder(getActivity(), R.style.add_dialog);

		View viewCalendar=LayoutInflater.from(getActivity()).inflate(R.layout.alertdialog_date, null);
		dialog = builder.create();
		dialog.setView(viewCalendar, 0, 0, 0, 0);

		ivLeft=(ImageView) viewCalendar.findViewById(R.id.iv_left);
		ivRight=(ImageView) viewCalendar.findViewById(R.id.iv_right);
		tvToday=(TextView) viewCalendar.findViewById(R.id.tv_today);
		tvWeek=(TextView) viewCalendar.findViewById(R.id.week_text);
		tvDate=(TextView) viewCalendar.findViewById(R.id.date_text);
		btnEnsure=(Button) viewCalendar.findViewById(R.id.btn_dialog_ensure);
		btnCancel=(Button) viewCalendar.findViewById(R.id.btn_dialog_cancel);
		monthDateView= (com.example.wsd_client.util.CalendarViewUtil.MonthDateView) viewCalendar.findViewById(R.id.monthDateView);

		monthDateView.setTextView(tvDate, tvWeek);
		dialog.show();

		//����dialog�Ĵ�С��һ��Ҫ����show()�����ĺ���
		android.view.WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
		attributes.height=view.getHeight()-view.getHeight()/8;
		attributes.width=view.getWidth()-view.getWidth()/8;
		dialog.getWindow().setAttributes(attributes);

		//����dialog����ʾλ��
		Window window=dialog.getWindow();
		window.setGravity(Gravity.CENTER);

		//���ð��հײ����ܹ�ȡ���Ի���
		dialog.setCanceledOnTouchOutside(true);

		monthDateView.setDateClick(new DateClick() {

			public void onClickOnDate() {
				//��ǰʱ��ĵ���¼�
			}
		});

		Myapplication.log("������û��ִ�е���һ��", "��ִ�е���һ����---------");

		setOnlistener();

	}

	private void setOnlistener(){
		ivLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				monthDateView.onLeftClick();
			}
		});

		ivRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				monthDateView.onRightClick();
			}
		});

		tvToday.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				monthDateView.setTodayToView();
			}
		});

		btnEnsure.setOnClickListener(new OnClickListener() {

			private int yearChoose;
			private int monthChoose;
			private int dayChoose;

			@Override
			public void onClick(View v) {

				if(j==1){
					yearChoose = monthDateView.getmSelYear();
					monthChoose = monthDateView.getmSelMonth()+1;
					dayChoose = monthDateView.getmSelDay();

					//��ȡ��ǰ��ʾ�Ľ���ʱ��
					String eTime=tvEndtime.getText().toString();

					String [] i=eTime.split("[-]");
					String eYear=i[0];
					String eMonth=i[1];
					String eDay=i[2];

					Integer endyear=Integer.parseInt(eYear);
					Integer endmonth=Integer.parseInt(eMonth);
					Integer endday=Integer.parseInt(eDay);

					Myapplication.log("������ȡ���ǲ�����ȷ�ģ�", endyear+"-"+endmonth+"-"+endday);

					//����ʼ�������ڵ�ǰ��ʾ�Ľ�������ʱ������ִ�в�ѯ����
					if(yearChoose<endyear||yearChoose==endyear&&monthChoose<endmonth||yearChoose==endyear&&monthChoose==endmonth&&dayChoose<=endday){

						tvStarttime.setText(yearChoose+"-"+monthChoose+"-"+dayChoose);
						//�˳��Ի���
						dialog.dismiss();
					}else{
						Myapplication.toast("��ʼ���ڲ������ڽ������ڣ�������ѡ��ʼ���ڣ�");
					}

				}else if(j==2){
					yearChoose= monthDateView.getmSelYear();
					monthChoose = monthDateView.getmSelMonth()+1;
					dayChoose = monthDateView.getmSelDay();

					//��ȡ��ǰ��ʾ�Ŀ�ʼʱ��
					String sTime=tvStarttime.getText().toString();

					String [] i=sTime.split("[-]");
					String sYear=i[0];
					String sMonth=i[1];
					String sDay=i[2];

					Integer startyear=Integer.parseInt(sYear);
					Integer startmonth=Integer.parseInt(sMonth);
					Integer startday=Integer.parseInt(sDay);

					Myapplication.log("�ٿ�����ȡ���ǲ�����ȷ�ģ�", startyear+"-"+startmonth+"-"+startday);

					//����ʼ�������ڵ�ǰ��ʾ�Ľ�������ʱ������ִ�в�ѯ����
					if(yearChoose>startyear||yearChoose==startyear&&monthChoose>startmonth||yearChoose==startyear&&monthChoose==startmonth&&dayChoose>=startday){

						tvEndtime.setText(yearChoose+"-"+monthChoose+"-"+dayChoose);
						//�˳��Ի���
						dialog.dismiss();
					}else{
						Myapplication.toast("�������ڲ������ڿ�ʼ���ڣ�������ѡ��������ڣ�");
					}
				}

			}
		});

		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//�˳��Ի���
				dialog.dismiss();
			}
		});
	}


	@SuppressWarnings("deprecation")
	private void showPopWindow(View v) {
		if(popupWindow==null){
			LayoutInflater layoutInflater=(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			viewPpWindow = layoutInflater.inflate(R.layout.popup_window_listview, null);
			lvPpWindow = (ListView) viewPpWindow.findViewById(R.id.lv_popup_window);

			Myapplication.log("ppWindow���俪ʼ��", "111111111111111111111111111");

			ppWindowAdapter = new AccountFragmentPpWindowAdapter(getActivity(), removeList);
			lvPpWindow.setAdapter(ppWindowAdapter);

			Myapplication.log("ppWindow���������", "222222222222222222222222");
			//����һ��PopupWindow����
			popupWindow=new PopupWindow(viewPpWindow,tvChoose.getMeasuredWidth(),LayoutParams.WRAP_CONTENT,true);
		}

		//ʹ��۽�
		popupWindow.setFocusable(true);
		//���������������͵�����ؼ���ʧ
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		//������ʾλ��,��tvPpWindow�·�
		popupWindow.showAsDropDown(tvChoose);

		lvPpWindow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				itemAtPosition =  removeList.get(position);
				tvChoose.setText(itemAtPosition);

				removeList.clear();
				Myapplication.log("removeList.clear()�����ݳ��ȣ�", removeList.size()+"");
				ppWindowAdapter.notifyDataSetChanged();
				tvChoose.setText(itemAtPosition);
				removeList.addAll(list);
				removeList.remove(itemAtPosition);

				//����������ɸѡʱ������ʱ��pageNumֵ���0
				pageNum=0;

				if (popupWindow != null) {
					popupWindow.dismiss();
				}

			}

		});
	}

	/**
	 * ִ�в�ѯ����
	 */
	private void search() {
		Myapplication.log("������ʱ���ѯ���ǵڼ�ҳ��", pageNum+"");
		pageNum=0;
		
		String start=tvStarttime.getText().toString();
		String end=tvEndtime.getText().toString();
		Myapplication.log("��ѯ�ǵ���ֹʱ��͵�ǰѡ������ݣ�", start+"----------"+end+"----"+itemAtPosition);

		map.put("starttime",start);

		//��ȡ��ǰ��ʾ�Ľ���ʱ�䣬ִ�в�ѯʱ��������ʱ�������һ�죬��ִ�в�ѯ
		String [] i=end.split("[-]");
		String searchYear=i[0];
		String searchMonth=i[1];
		String searchDay=i[2];

		Integer endYear=Integer.parseInt(searchYear);
		Integer endMonth=Integer.parseInt(searchMonth);
		Integer endDay=Integer.parseInt(searchDay);

		//��ȡ�����������
		int maxDay=DateUtils.getMonthDays(endYear, endMonth-1);
		Myapplication.log("���������", maxDay+"");
		//�жϵ�ǰʱ���Ƿ�Ϊ��ĩ����ĩ���һ�죬��ѯʱ����ʱ�������ӳ�һ��
		int newDay=0;
		int newYear = 0;
		int newMonth=0;

		if(endDay==maxDay){
			newDay=1;
			if(endMonth==12){
				newYear=endYear+1;
				newMonth=1;
			}else{
				newMonth=endMonth+1;
				newYear=endYear;
			}
		}else{
			newDay=endDay+1;
			newYear=endYear;
			newMonth=endMonth;
		}

		//ƴװ����ʱ��
		String endTime=newYear+"-"+newMonth+"-"+newDay;
		Myapplication.log("�����ѯ��ť���ʵ�ʽ�����ѯʱ�䣺", endTime);
		
		map.put("endtime",endTime);
		map.put("pageNum", pageNum+"");

		if(itemAtPosition.equals("Ʒ�ֻ���")){
			map.put("xsstatus", "0");
		}else if(itemAtPosition.equals("���ڻ���")){
			map.put("xsstatus", "1");
		}else if(itemAtPosition.equals("���ƻ���")){
			map.put("xsstatus", "2");
		}else if(itemAtPosition.equals("����+Ʒ��")){
			map.put("xsstatus", "3");
		}else if(itemAtPosition.equals("����+����")){
			map.put("xsstatus", "4");
		}

		accountInfoUtil.getAccountInfo(map,1);

	}


}
