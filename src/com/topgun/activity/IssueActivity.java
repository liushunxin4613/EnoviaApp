package com.topgun.activity;


import android.view.Menu;
import android.view.MenuItem;

import com.topgun.enoviaapp.MainActivity;
import com.topgun.enoviaapp.R;
import com.topgun.fragment.IssueArrayFragment;
import com.topgun.util.Config;
import com.topgun.util.ui.ImmersionActivity;

/**
 * 问题Activity
 * 
 * @author liushunxin
 *
 */
public class IssueActivity extends ImmersionActivity {


	public static IssueActivity activity;
	private int[] array;
	private IssueArrayFragment fragment;
	//菜单标记
	public static int itemId;
	//菜单防重复标记
	public static int itemIds;

	@Override
	protected Integer getLayoutResource() {
		return R.layout.activity_issue;
	}
	
	@Override
	protected void listenView() {
		super.listenView();
		getActionBar().setTitle(Config.idApptitle[5]);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_issue, menu);
		return true;
	}

	@Override
	protected void init() {
		array = Config.AC_FM_STR;
		setItemId();
	}
	
	private void setItemId() {
		fragment = new IssueArrayFragment();
		fragment.setIndex(itemId);
		getActionBar().setTitle(array[itemId]);
		getSupportFragmentManager().beginTransaction().replace(R.id.fl_issue, fragment).commit();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_0:
			itemId = 0;
			break;
		case R.id.action_1:
			itemId = 1;
			break;
		case R.id.action_2:
			itemId = 2;
			break;
		case R.id.action_3:
			itemId = 3;
			break;
		case R.id.action_4:
			itemId = 4;
			break;
		case R.id.action_5:
			itemId = 5;
			break;
		case R.id.action_6:
			itemId = 6;
			break;
		}
		//防止重复操作
		if (itemId != itemIds) {			
			setItemId();
		}
		itemIds = itemId;
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		startActivityFinish(MainActivity.class);
		super.onBackPressed();
	}

}
