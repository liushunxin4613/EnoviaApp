package com.topgun.fragment;

import com.alibaba.fastjson.JSONObject;
import com.topgun.dialog.EditDialog;
import com.topgun.enoviaapp.R;
import com.topgun.entity.User;
import com.topgun.model.SoapMessage;
import com.topgun.util.Config;
import com.topgun.util.DataUtil;
import com.topgun.util.UIUtil;
import com.topgun.util.ui.BaseThreadFragment;

import android.content.DialogInterface;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 流程任务详细Fragment
 * 
 * @author liusx
 *
 */
public class InboxTaskDetailFragment extends BaseThreadFragment implements OnClickListener{

	private static InboxTaskDetailFragment fragment;

	private TextView finishTaskTv;

	public static final int MSG_FINISH_INBOX = 1;

	public static final int MSG_INIT_INBOX = 2;

	private String issueId;

	private EditDialog editDialog;

	private TextView tv2 [];

	public static InboxTaskDetailFragment getInstance() {
		if (fragment == null)
			fragment = new InboxTaskDetailFragment();
		return fragment;
	}

	@Override
	protected Integer getLayoutResource() {
		return R.layout.fragment_inbox_task_details;
	}

	@Override
	protected void listenView(View rootView) {
		super.listenView(rootView);

		finishTaskTv = (TextView) rootView.findViewById(R.id.fm_include_tv_inbox_success);
		finishTaskTv.setOnClickListener(this);

		tv2 = new TextView[Config.FM_ITEM_INBOX.length];
		for (int i = 0; i < Config.FM_ITEM_INBOX.length; i++) {
			View v = rootView.findViewById(Config.FM_ITEM_INBOX[i]);
			TextView tv1 = (TextView) v.findViewById(Config.INCLUDE[0]);
			tv2[i] = (TextView) v.findViewById(Config.INCLUDE[1]);
			tv1.setText(Config.FM_SINT_INBOX[i]);
		}

		JSONObject obj = DataUtil.getIssue(getActivity());

		issueId = obj.getString(Config.ID);
		for (int i = 0; i < tv2.length; i++) {
			tv2[i].setText(obj.getString(Config.FM_STR_INBOX[i]));
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fm_include_tv_inbox_success:
			finishTask();
			break;
		}
	}

	private void finishTask() {
		editDialog = new EditDialog(
				EditDialog.CONFIRM_STYLE_CENTER
				,getString(R.string.issue_review_et0)
				,getString(R.string.issue_review_submit_error0)
				,getString(R.string.dialog_again_exit_yes)
				,getString(R.string.dialog_cancel)
				);
		editDialog.setOnclickListener(new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					String str = editDialog.getEditStr();
					if (str != null) {
						setLoadingDialog(true);
						User user = DataUtil.getUserLoginInfo(getActivity());
						if (user == null) return;
						if (issueId == null) return;
						startVoid(MSG_FINISH_INBOX, Config.doCompleteTaskMethodName, new SoapMessage(user, issueId, str));
					}
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		});
		editDialog.show(getActivity().getSupportFragmentManager().beginTransaction(), TAG);
	}

	@Override
	public void toHandleMessage(Message msg) {
		super.toHandleMessage(msg);
		switch (msg.what) {
		case MSG_FINISH_INBOX:
			UIUtil.showToast(getActivity(), R.string.finish_tast_success);
			getActivity().onBackPressed();
			break;
		}
	}

}
