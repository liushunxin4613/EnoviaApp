package com.topgun.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.topgun.enoviaapp.R;
import com.topgun.util.MEditTextWatcher;

@SuppressLint("ValidFragment")
public class EditDialog extends BaseDialog{

	private EditText msgView;

	private CharSequence msg;
	/**
	 * 对话框的风格
	 */
//	private int dialogStyle;

	/**
	 * 对话框风格，居中
	 */
	public static final int CONFIRM_STYLE_CENTER = 0;
	/**
	 * 对话框风格，底部
	 */
	public static final int CONFIRM_STYLE_BOTTOM = 1;

	public EditDialog() {
	}

	public EditDialog(int dialogStyle, String title,
			String msg, String posText, String nevText) {
//		this.dialogStyle = dialogStyle;
		if (dialogStyle == CONFIRM_STYLE_CENTER)
			this.theme = R.style.MyDialogTheme_Center;
		this.title = title;
		this.msg = msg;
		this.positiveText = posText;
		this.nevigativeText = nevText;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.dialog_config_edit,
				container, false);
		titleView = (TextView) rootView.findViewById(R.id.dialog_confirm_et_title);
		msgView = (EditText) rootView.findViewById(R.id.dialog_confirm_et_msg);
		msgView.addTextChangedListener(new MEditTextWatcher(msgView));
		positiveBtn = (Button) rootView
				.findViewById(R.id.dialog_positive_et_button);
		nevigativeBtn = (Button) rootView
				.findViewById(R.id.dialog_nevigative_et_button);

		if (title != null) {
			titleView.setVisibility(View.VISIBLE);
			titleView.setText(title);
		}
		if (msg != null) {
			msgView.setVisibility(View.VISIBLE);
			msgView.setHint(msg);
		}
		if (positiveText != null){
			positiveBtn.setText(positiveText);
			positiveBtn.setVisibility(View.VISIBLE);
		}
		if (nevigativeText != null){
			nevigativeBtn.setText(nevigativeText);
			nevigativeBtn.setVisibility(View.VISIBLE);
		}
		if (positiveText == null || nevigativeText == null){
			View line = rootView.findViewById(R.id.dialog_button_ling);
			line.setVisibility(View.GONE);
		}

		positiveBtn.setOnClickListener(this);
		nevigativeBtn.setOnClickListener(this);
		return rootView;
	}

	public void setContentMsg(int resId) {
		msg = context.getString(resId);
		if (msgView != null) {
			msgView.setHint(msg);
		}
	}

	public String getEditStr(){
		if (msgView == null) {
			return null;
		}else if(msgView.getText() == null) {
			return null;
		}else {			
			return msgView.getText().toString();
		}
	}

}
