package com.topgun.activity;

import android.os.Bundle;

import com.topgun.enoviaapp.MainActivity;
import com.topgun.enoviaapp.R;
import com.topgun.fragment.TaskFragment;
import com.topgun.util.Config;
import com.topgun.util.ui.ImmersionActivity;

/**
 * WEB»ŒŒÒActivity
 * 
 * @author liushunxin
 *
 */
public class TaskActivity extends ImmersionActivity {
	
	private TaskFragment fragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState == null) {
			fragment = TaskFragment.getInstance();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.fl_task, fragment).commit();
		}
	}
	
	@Override
	protected Integer getLayoutResource() {
		return R.layout.activity_task;
	}
	
	@Override
	protected void listenView() {
		super.listenView();
		getActionBar().setTitle(Config.idApptitle[9]);
	}
	
	@Override
	public void onBackPressed() {
		startActivityFinish(MainActivity.class);
		super.onBackPressed();
	}
}
