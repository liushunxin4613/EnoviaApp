package com.topgun.activity;

import android.os.Bundle;

import com.topgun.enoviaapp.MainActivity;
import com.topgun.enoviaapp.R;
import com.topgun.fragment.InboxTaskFragment;
import com.topgun.util.Config;
import com.topgun.util.ui.ImmersionActivity;

/**
 * 流程任务Activity
 * 
 * @author liusx
 *
 */
public class InboxTaskActivity extends ImmersionActivity {

	private InboxTaskFragment fragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			fragment = InboxTaskFragment.getInstance();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.fl_inbox_task, fragment).commit();
		}
	}
	
	@Override
	protected Integer getLayoutResource() {
		return R.layout.activity_inbox_task;
	}
	
	@Override
	protected void listenView() {
		super.listenView();
		getActionBar().setTitle(Config.idApptitle[3]);
		
	}
	
	@Override
	public void onBackPressed() {
		startActivityFinish(MainActivity.class);
		super.onBackPressed();
	}
	
}
