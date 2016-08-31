package com.topgun.util;

import android.content.Context;
import android.widget.ArrayAdapter;

public class StyleUtil {

	
	public static ArrayAdapter<String> initSpArrayAdapter(Context context,String ss[]){
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,ss);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return adapter;
	}
	
}
