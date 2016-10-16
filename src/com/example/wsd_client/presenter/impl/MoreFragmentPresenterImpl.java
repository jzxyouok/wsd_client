package com.example.wsd_client.presenter.impl;

import java.util.Map;

import com.example.wsd_client.entity.ClientInfo;
import com.example.wsd_client.fragment.IMoreFragment;
import com.example.wsd_client.modle.IMoreFragmentModle;
import com.example.wsd_client.modle.IMoreFragmentModle.MoreFragmentCallBack;
import com.example.wsd_client.modle.impl.MoreFragmentModleImpl;
import com.example.wsd_client.presenter.IMoreFragmentPresenter;
/**
 * moreFragmentҳ��presenter��ʵ����
 * @author wsd_leiguoqiang
 */
public class MoreFragmentPresenterImpl implements IMoreFragmentPresenter{
	private IMoreFragment view;
	private IMoreFragmentModle modle;
	private Map<String, String> userInfo;

	public MoreFragmentPresenterImpl(IMoreFragment view,
			Map<String, String> userInfo) {
		super();
		this.view = view;
		this.modle = new MoreFragmentModleImpl();
		this.userInfo = userInfo;
	}

	@Override
	public void updateUserInfo() {
		modle.updateUserInfo(userInfo, new MoreFragmentCallBack() {
			@Override
			public void updateMoreFragment(ClientInfo clientInfo) {
				//����view�����ݸ���
				view.updateView(clientInfo);
			}
		});
	}

}
