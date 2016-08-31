package com.topgun.fragment;

import java.util.ArrayList;
import java.util.List;

import com.topgun.activity.LoginActivity;
import com.topgun.activity.UserInfoActivity;
import com.topgun.enoviaapp.R;
import com.topgun.entity.User;
import com.topgun.service.InitUserService;
import com.topgun.util.Config;
import com.topgun.util.DataUtil;
import com.topgun.util.ui.BaseReceiveFragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 
 * 我页面
 * 
 * @author liusx
 *
 */
public class MeFragment extends BaseReceiveFragment implements OnClickListener{

	private static MeFragment fragment;

	private TextView tv[];
	private int sInt [];

	private User user;

	public static final int MSG_USER = 1;

	public static Fragment getInstance() {
		if (fragment == null)
			fragment = new MeFragment();
		return fragment;
	}

	@Override
	protected Integer getLayoutResource() {
		return R.layout.fragment_main_me;
	}
	
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.user_imgBtn:
			onClickUserImgBtn();
			break;
		case R.id.user_hello:
			onClickUserHello();
			break;
		case R.id.fm_include_main_me_ti1:
			onClickUserInfo();
			break;
		case R.id.fm_include_main_me_ti2:
			onClickUserLoginOut();
			break;
		}
	}

	/**
	 * 点击用户头像
	 */
	private void onClickUserImgBtn() {
	}
	
	/**
	 * 点击编辑资料
	 */
	private void onClickUserHello() {
	}
	
	/**
	 * 点击用户信息
	 */
	private void onClickUserInfo() {
		startActivity(new Intent(getActivity(), UserInfoActivity.class));
	}

	/**
	 * 点击退出登录
	 */
	private void onClickUserLoginOut() {
		startActivity(new Intent(getActivity(), LoginActivity.class));
		
		//退出数据处理
		DataUtil.cleanUserInfo(getActivity());
		
		getActivity().finish();
	}
	
	@Override
	protected List<String> toIntentAction() {
		List<String> list = new ArrayList<String>();
		list.add(InitUserService.ACTION_USER_INFO_SUCCESS);
		return list;
	}
	
	@Override
	protected void toReceive(Context context, Intent data, String action) {
		if (action.equals(InitUserService.ACTION_USER_INFO_SUCCESS)) {
			user = DataUtil.getUserLoginInfo(getActivity());
			if (null == user) return;
			String name = user.getName();
			tv[0].setText(name);
		}
	}
	
	@Override
	protected void listenView(View rootView) {
		super.listenView(rootView);
		
		tv = new TextView[Config.FM_ITEM_IM_MAIN_ME.length];
		for (int i = 0; i < Config.FM_ITEM_IM_MAIN_ME.length; i++) {
			View v = rootView.findViewById(Config.FM_ITEM_IM_MAIN_ME[i]);
			ImageButton ib = (ImageButton) v.findViewById(Config.INCLUDE_ID_IMAGE_LIST[0]);
			ib.setOnClickListener(this);
			//TODO 暂不设置点击图像事件
			ib.setClickable(false);
			tv[i] = (TextView) v.findViewById(Config.INCLUDE_ID_IMAGE_LIST[1]);
			TextView tv1 = (TextView) v.findViewById(Config.INCLUDE_ID_IMAGE_LIST[2]);
			tv1.setOnClickListener(this);
			//TODO 暂不设置编辑资料事件
			tv1.setClickable(false);
		}

		sInt = Config.FM_STINT_TTI_MIAN_ME;
		for (int i = 0; i < Config.FM_ITEM_TI_MIAN_ME.length; i++) {
			View v = rootView.findViewById(Config.FM_ITEM_TI_MIAN_ME[i]);
			TextView tv = (TextView) v.findViewById(Config.INCLUDE_ID_H_TV_IM[0]);
			tv.setText(sInt[i]);
			v.setOnClickListener(this);
		}
	}
	
	@Override
	protected void init() {
		user = DataUtil.getUserLoginInfo(getActivity());
		if (null == user) return;

		if (user.getName() == null) {
			Intent intent = new Intent(getActivity(),InitUserService.class);
			intent.putExtra(InitUserService.KEY_START_SERVICE_FOR,
					InitUserService.TASK_USER_INFO);
			getActivity().startService(intent);
		} else {
			tv[0].setText(user.getName());
		}
		
	}
	
	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if (this.getView() != null)
			this.getView()
			.setVisibility(menuVisible ? View.VISIBLE : View.GONE);
	}

}

