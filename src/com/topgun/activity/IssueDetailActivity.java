package com.topgun.activity;

import android.os.Bundle;

import com.topgun.enoviaapp.R;
import com.topgun.fragment.IssueDetailFragment;
import com.topgun.util.Config;
import com.topgun.util.ui.ImmersionActivity;


/**
 * Œ Ã‚œÍœ∏Activity
 * 
 * @author liushunxin
 *
 */
public class IssueDetailActivity extends ImmersionActivity {

	public static final String INTENT_ISSUE = "issue";
	public static final String INTENT_ISSUE_TYPE = "issueType";
	
	private IssueDetailFragment fragment;
	
	public static IssueDetailActivity activity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState == null) {
			fragment = IssueDetailFragment.getInstance();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.fl_issue_detail, fragment).commit();
		}
	}
	
	@Override
	protected Integer getLayoutResource() {
		return R.layout.activity_issue_detail;
	}
	
	@Override
	protected void listenView() {
		super.listenView();
		getActionBar().setTitle(Config.idApptitle[6]);
	}
	
	
	@Override
	public void onBackPressed() {
		startActivityFinish(IssueActivity.class);
		super.onBackPressed();
	}
}
