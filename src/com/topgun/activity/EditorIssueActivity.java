package com.topgun.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.topgun.dialog.ConfirmDialog;
import com.topgun.enoviaapp.R;
import com.topgun.fragment.EditorIssueFragment;
import com.topgun.util.Config;
import com.topgun.util.IOUtil;
import com.topgun.util.UIUtil;
import com.topgun.util.ui.ImmersionActivity;

public class EditorIssueActivity extends ImmersionActivity{

	private EditorIssueFragment fragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			fragment = EditorIssueFragment.getInstance();
			getSupportFragmentManager().beginTransaction()
			.add(R.id.fl_editor_issue,fragment).commitAllowingStateLoss();
		}
	}
	
	@Override
	protected void listenView() {
		super.listenView();
		getActionBar().setTitle(Config.idApptitle[7]);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case IOUtil.FILE_SELECT_CODE:
				if (getFragmentList() != null) {
					for (Fragment fragment : getFragmentList()) {
						if (fragment != null) {
							UIUtil.showLogI(TAG, "requestCode", requestCode);
							UIUtil.showLogI(TAG, "requestCode & 0x00001111", requestCode & 0x00001111);
							UIUtil.showLogI(TAG, "resultCode", resultCode);
							UIUtil.showLogI(TAG, "data", data.toString());
							fragment.onActivityResult(requestCode & 0x00001111, resultCode, data);
						}
					}
				}
				break;
			}
		}
	}


	@Override
	protected Integer getLayoutResource() {
		return R.layout.activity_editor_issue;
	}

	@Override
	public void onBackPressed() {
		goBack();
	}

	@Override
	protected List<Fragment> getFragmentList() {
		List<Fragment> list = new ArrayList<Fragment>();
		list.add(fragment);
		return list;
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
