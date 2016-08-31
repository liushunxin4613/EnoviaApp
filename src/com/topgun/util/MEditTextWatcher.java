package com.topgun.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MEditTextWatcher implements TextWatcher {

	//�����ı���ı��� 
	private EditText editText; 

	/** 
	 * ���캯�� 
	 */ 
	public MEditTextWatcher(EditText editText){ 
		this.editText = editText; 
	} 

	@Override 
	public void onTextChanged(CharSequence ss, int start, int before, int count) { 
		String editable = editText.getText().toString(); 
		String str = stringFilter(editable.toString()); 
		if(!editable.equals(str)){ 
			editText.setText(str); 
			//�����µĹ������λ�� 
			editText.setSelection(str.length()); 
		} 
	} 

	@Override 
	public void afterTextChanged(Editable s) { 
	} 
	@Override 
	public void beforeTextChanged(CharSequence s, int start, int count,int after) { 
	} 
	public static String stringFilter(String str){ 
		// ֻ������ĸ������ 
		//[^a-zA-Z0-9\u4e00-\u9fa5]
		String regEx = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(str); 
		return m.replaceAll("").trim(); 
	}

}
