package com.topgun.fragment;

import com.topgun.enoviaapp.R;
import com.topgun.entity.User;
import com.topgun.util.Config;
import com.topgun.util.DataUtil;
import com.topgun.util.ui.BaseFragment;

import android.view.View;
import android.widget.TextView;

public class UserInfoFragment extends BaseFragment {

	private TextView valueTv[];
	
	private View viewItem[];
	
	private User user;
	
	private static UserInfoFragment fragment;
	
	public static UserInfoFragment getInstance() {
		if (fragment == null)
			fragment = new UserInfoFragment();
		return fragment;
	}
	
	@Override
	protected Integer getLayoutResource() {
		return R.layout.fragment_user_info;
	}
	
	@Override
	protected void listenView(View rootView) {
		super.listenView(rootView);
		
		valueTv = new TextView[Config.FM_ITEM_USER_INFO_TT.length];
		viewItem = new View[Config.FM_ITEM_USER_INFO_TT.length];
		for (int i = 0; i < Config.FM_ITEM_USER_INFO_TT.length; i++) {
			viewItem[i] = rootView.findViewById(Config.FM_ITEM_USER_INFO_TT[i]);
			View vg = viewItem[i].findViewById(R.id.fm_include_user_info_e);
			TextView tv = (TextView) vg.findViewById(R.id.Tv_include_key);
			tv.setText(Config.FM_SINT_USER_INFO_TT[i]);
			valueTv[i] = (TextView) vg.findViewById(Config.INCLUDE[1]);
		}
		
	}

	@Override
	protected void init() {
		user = DataUtil.getUserLoginInfo(getActivity());
		if(user == null) return;
		
		String info[] = {user.getUsername(),user.getName(),user.getEmail()};
		for (int i = 0; i < valueTv.length; i++) {
			valueTv[i].setText(info[i]);
			if (info[i] == null) {
				viewItem[i].setVisibility(View.GONE);
			}
		}
	}

}
