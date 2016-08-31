package com.topgun.activity;

import android.os.Bundle;

import com.topgun.enoviaapp.R;
import com.topgun.fragment.InboxTaskDetailFragment;
import com.topgun.util.Config;
import com.topgun.util.ui.ImmersionActivity;

/**
 * 流程任务详细Activity
 * 
 * @author liusx
 *
 */
public class InboxTaskDetailActivity extends ImmersionActivity {

	public static final String INTENT_INBOX = "intentInbox";

	private InboxTaskDetailFragment fragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {
			fragment = InboxTaskDetailFragment.getInstance();
			getSupportFragmentManager().beginTransaction()
				.add(R.id.fl_inbox_task_detail,fragment).commit();
		}
	}
	
	@Override
	protected Integer getLayoutResource() {
		return R.layout.activity_inbox_task_detail;
	}
	
	@Override
	protected void listenView() {
		super.listenView();
		getActionBar().setTitle(Config.idApptitle[4]);
	}
	
	@Override
	public void onBackPressed() {
		startActivityFinish(InboxTaskActivity.class);
		super.onBackPressed();
	}

}
