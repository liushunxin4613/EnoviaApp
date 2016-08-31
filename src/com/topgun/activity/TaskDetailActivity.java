package com.topgun.activity;

import android.os.Bundle;

import com.topgun.enoviaapp.R;
import com.topgun.fragment.TaskDetailFragment;
import com.topgun.util.Config;
import com.topgun.util.ui.ImmersionActivity;

/**
 * WEBÈÎÎñÏêÏ¸Activity
 * 
 * @author liushunxin
 *
 */
public class TaskDetailActivity extends ImmersionActivity{

	public static final String INTENT_TASK = "intentTASK";
	
	private TaskDetailFragment fragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState == null) {
			fragment = TaskDetailFragment.getInstance();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.fl_task_detail, fragment).commit();
		}
	}
	
	
	@Override
	protected Integer getLayoutResource() {
		return R.layout.activity_task_detail;
	}
	
	@Override
	protected void listenView() {
		super.listenView();
		getActionBar().setTitle(Config.idApptitle[10]);
	}
	
	@Override
	public void onBackPressed() {
		startActivityFinish(TaskActivity.class);
		super.onBackPressed();
	}
	
}
