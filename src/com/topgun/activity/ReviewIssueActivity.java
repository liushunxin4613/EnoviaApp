package com.topgun.activity;

import android.content.DialogInterface;
import android.os.Bundle;

import com.topgun.dialog.ConfirmDialog;
import com.topgun.enoviaapp.R;
import com.topgun.fragment.ReviewIssueFragment;
import com.topgun.util.Config;
import com.topgun.util.ui.ImmersionActivity;

public class ReviewIssueActivity extends ImmersionActivity {

	private ReviewIssueFragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState == null) {
			fragment = ReviewIssueFragment.getInstance();
			getSupportFragmentManager().beginTransaction()
			.add(R.id.fl_review_issue,fragment).commit();
		}
	}
	
	@Override
	protected Integer getLayoutResource() {
		return R.layout.activity_review_issue;
	}
	
	@Override
	protected void listenView() {
		super.listenView();
		getActionBar().setTitle(Config.idApptitle[8]);
	}
	
	@Override
	public void onBackPressed() {
		goBack();
	}

	/**
	 * »ØÍË
	 */
	private void goBack() {
		if (!popFragment()) {
			ConfirmDialog dialog = new ConfirmDialog(
					ConfirmDialog.CONFIRM_STYLE_CENTER,
					getString(R.string.dialog_exit_title),
					getString(R.string.dialog_exit_msg),
					getString(R.string.dialog_exit_yes),
					getString(R.string.dialog_cancel));
			dialog.setOnclickListener(new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						startActivityFinish(IssueDetailActivity.class);
						finish();
						break;
					case DialogInterface.BUTTON_NEGATIVE:
						break;
					}
				}
			});
			dialog.show(getSupportFragmentManager().beginTransaction(), TAG);
		}
	}

	/**
	 * ÒÆ³ýÈÝÆ÷ÖÐÕ»¶¥µÄFragment
	 * 
	 * @return true:ÒÆ³ý³É¹¦ ; false:ÒÆ³ýÊ§°Ü£¬¼´backStackÖÐÃ»ÓÐFragment
	 * 
	 */
	private boolean popFragment() {
		int count = getSupportFragmentManager().getBackStackEntryCount();
		if (count > 0) {
			getSupportFragmentManager().popBackStack();
			return true;
		}
		return false;
	}
	
}
