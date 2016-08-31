package com.topgun.fragment;

import com.alibaba.fastjson.JSONObject;
import com.topgun.activity.IssueActivity;
import com.topgun.activity.IssueDetailActivity;
import com.topgun.enoviaapp.R;
import com.topgun.entity.User;
import com.topgun.model.SoapMessage;
import com.topgun.util.Config;
import com.topgun.util.DataUtil;
import com.topgun.util.MEditTextWatcher;
import com.topgun.util.UIUtil;
import com.topgun.util.ViewUtil;
import com.topgun.util.ui.BaseThreadFragment;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ReviewIssueFragment extends BaseThreadFragment implements OnClickListener {

	private TextView tv[];
	private EditText et[];
	private Spinner sp[];
	private Button submitBt;

	private int ID_TV[] = {R.id.fg_ic_review_issue_tv0,R.id.fg_ic_review_issue_tv1};
	private int STR_TV[] = {R.string.issue_review_tv0,R.string.issue_review_tv1};
	private String KEY_TV[] = {"name","description"};

	private int ID_ET[] = {R.id.fg_ic_review_issue_et0};
	private int STR_ET[] = {R.string.issue_review_et0};

	private int ID_SP[] = {R.id.fg_ic_review_issue_sp0};
	private int STR_SP[] = {R.string.issue_review_sp0};

	private int SUBMIT_ERROR[] = {R.string.issue_review_submit_error0};

	private JSONObject obj;
	private User user;

	private String dataId;

	public static final int MSG_PROMOTE = 1;

	private static ReviewIssueFragment fragment;

	public static ReviewIssueFragment getInstance(){
		if (fragment == null) {
			fragment = new ReviewIssueFragment();
		}
		return fragment;
	}

	@Override
	protected Integer getLayoutResource() {
		return R.layout.fragment_review_issue;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_fm_review_issue:
			submitData();
			break;
		}
	}

	private void submitData() {
		UIUtil.showLogI(TAG, "处理1", null);
		if(!checkData()) return;
		UIUtil.showLogI(TAG, "处理2", null);

		setLoadingDialog(true);
		startVoid(MSG_PROMOTE, Config.promoteIssueMethodName, new SoapMessage(user, dataId, et[0].getText().toString(), sp[0].getSelectedItem().toString()));
		UIUtil.showLogI(TAG, "处理3 " + et[0].getText().toString(),  sp[0].getSelectedItem().toString());

	}

	@Override
	public void toHandleMessage(Message msg) {

		super.toHandleMessage(msg);
		UIUtil.showLogI(TAG, "d", "dsa");
		switch (msg.what) {
		case MSG_PROMOTE:
			
			UIUtil.showToast(getActivity(), R.string.issue_review_submit_sucess);

			startActivity(new Intent(getActivity(),IssueActivity.class));
			if (IssueDetailActivity.activity != null) {
				IssueDetailActivity.activity.finish();
			}
			getActivity().finish();
			
			break;
		}
	}

	private boolean checkData() {
		for (int i = 0; i < et.length; i++) {
			if (et[i].getText() == null) {
				UIUtil.showToast(getActivity(), SUBMIT_ERROR[i]);
				return false;
			}else {
				if (et[i].getText().toString().trim().length() == 0) {
					UIUtil.showToast(getActivity(), SUBMIT_ERROR[i]);
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void listenView(View rootView) {

		tv = ViewUtil.listenViewIncludeTv(rootView, ID_TV, STR_TV);
		et = ViewUtil.listenViewIncludeEt(rootView, ID_ET, STR_ET);
		et[0].setLines(5);
		et[0].addTextChangedListener(new MEditTextWatcher(et[0]));
		
		sp = ViewUtil.listenViewIncludeSp(rootView, ID_SP, STR_SP);

		submitBt = (Button) rootView.findViewById(R.id.bt_fm_review_issue);
		submitBt.setOnClickListener(this);

		ViewUtil.setViewSpInitData(getActivity(),sp
				,new String[][]{getResources().getStringArray(R.array.issue_value_action)});

	}

	@Override
	protected void init() {
		user = DataUtil.getUserLoginInfo(getActivity());
		if (null == user) return;

		obj = DataUtil.getIssue(getActivity());

		dataId = obj.getString("id");

		ViewUtil.setViewTvData(tv, obj, ID_TV, KEY_TV);
	}

}
