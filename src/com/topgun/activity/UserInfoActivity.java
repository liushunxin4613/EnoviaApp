package com.topgun.activity;

import android.os.Bundle;

import com.topgun.enoviaapp.MainActivity;
import com.topgun.enoviaapp.R;
import com.topgun.fragment.UserInfoFragment;
import com.topgun.util.Config;
import com.topgun.util.ui.ImmersionActivity;

public class UserInfoActivity extends ImmersionActivity{
	
	private UserInfoFragment fragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			fragment = UserInfoFragment.getInstance();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.fm_user_info, fragment).commit();
		}
	}
	
	@Override
	protected Integer getLayoutResource() {
		return R.layout.activity_uer_info;
	}
	
	@Override
	protected void listenView() {
		super.listenView();
		getActionBar().setTitle(Config.idApptitle[2]);
	}
	
	@Override
	public void onBackPressed() {
		startActivityFinish(MainActivity.class);
		super.onBackPressed();
	}
}
