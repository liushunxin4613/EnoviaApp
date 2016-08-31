package com.topgun.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.topgun.enoviaapp.MainActivity;
import com.topgun.enoviaapp.R;
import com.topgun.entity.User;
import com.topgun.service.InitUserService;
import com.topgun.util.DataUtil;
import com.topgun.util.MEditTextWatcher;
import com.topgun.util.UIUtil;
import com.topgun.util.ui.BaseReceiveActivity;

public class LoginActivity extends BaseReceiveActivity implements OnClickListener {

	private EditText usernameEt,pwdEt;

	private Button sublitBt;

	private CheckBox savePwd;
	
	private User user;

	/**
	 * 登录消息
	 */
	public static final int MSG_ISUSER = 1;

	/**
	 * 登录消息
	 */
	public static final int NETERROR = 1;

	private static boolean isSavePwd;
	
	@Override
	protected List<String> toIntentAction() {
		List<String> list = new ArrayList<String>();
		list.add(InitUserService.ACTION_LOGIN_SUCCESS);
		return list;
	};
	
	@Override
	protected void toReceive(Context context, Intent data, String action) {
		if (action.equals(InitUserService.ACTION_LOGIN_SUCCESS)) {
			startActivity(new Intent(this, MainActivity.class));
			finish();
		}
	}
	
	@Override
	protected Integer getLayoutResource() {
		return R.layout.activity_login;
	};
	
	@Override
	protected void listenView() {
		super.listenView();
		usernameEt = (EditText) findViewById(R.id.Et_login_username);
		usernameEt.addTextChangedListener(new MEditTextWatcher(usernameEt));
		pwdEt = (EditText) findViewById(R.id.Et_login_pwd);
		pwdEt.addTextChangedListener(new MEditTextWatcher(pwdEt));
		sublitBt = (Button) findViewById(R.id.Bt_login_submit);
		sublitBt.setOnClickListener(this);
		savePwd = (CheckBox) findViewById(R.id.Cb_login_remember);
	}
	
	@Override
	protected void init() {
		isSavePwd = DataUtil.isIsPwd(this);

		savePwd.setChecked(isSavePwd);

		savePwd.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isSavePwd = isChecked;
				DataUtil.saveIsPwd(LoginActivity.this,isChecked);
			}
		});

		user = DataUtil.getUserLoginInfo(LoginActivity.this);

		if (null == user) {
			user = new User();
		}else {
			if (!isSavePwd) {
				user.setPwd(null);
			}
			if (null != user.getUsername()) {
				usernameEt.setText(user.getUsername());
			}
			if (null != user.getPwd()) {
				pwdEt.setText(user.getPwd());
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//登录
		case R.id.Bt_login_submit:
			submit();
			break;
		}
	}

	private void submit() {
		if (!checkData()) return;
		
		Intent intent = new Intent(this,InitUserService.class);
		intent.putExtra(InitUserService.KEY_START_SERVICE_FOR,
				InitUserService.TASK_LOGIN);
		intent.putExtra(InitUserService.INTENT_USER_INFO, new String[]{user.getUsername(),user.getPwd()});
		startService(intent);
		
	}

	/**
	 * 验证输入数据是否合法
	 * 
	 * @return
	 */
	private boolean checkData() {
		if (usernameEt.getText().toString().trim().length() != 0) {
			user.setUsername(usernameEt.getText().toString().trim());
		}else {
			user.setUsername(null);
		}
		if (pwdEt.getText().toString().trim().length() != 0) {
			user.setPwd(pwdEt.getText().toString().trim());
		}else {
			user.setPwd(null);
		}
		if (null == user.getUsername()) {
			UIUtil.showToast(this,R.string.login_username_dialog);
			return false;
		}else {
			if (user.getUsername().length() == 0) {
				UIUtil.showToast(this,R.string.login_username_dialog);
				return false;
			}
		}
		if (null == user.getPwd()) {
			UIUtil.showToast(this,R.string.login_pwd_dialog);
			return false;
		}else {
			if (user.getPwd().length() == 0) {
				UIUtil.showToast(this,R.string.login_pwd_dialog);
				return false;
			}
		}

		user = new User(usernameEt.getText().toString().trim(), pwdEt.getText().toString().trim());
		return true;
	}
	
}
