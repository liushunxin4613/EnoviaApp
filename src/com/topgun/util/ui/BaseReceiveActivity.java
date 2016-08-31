package com.topgun.util.ui;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class BaseReceiveActivity extends BaseActivity{
	
	private final BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent data) {
			String action = data.getAction();
			toReceive(context, data,action);
		}
	};
	
	private IntentFilter intentFilter;
	
	public IntentFilter getIntentFilter(){
		return intentFilter;
	}
	
	public void setIntentFilter(IntentFilter intentFilter) {
		this.intentFilter = intentFilter;
	}

	public BroadcastReceiver getReceiver() {
		return receiver;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setIntentFilter(getNewIntentFilter());
		
		registerReceiver(receiver, intentFilter);
		
	}
	
	private IntentFilter getNewIntentFilter() {
		IntentFilter filter = new IntentFilter();
		if (toIntentActionS() != null) {
			filter.addAction(toIntentActionS());
		}
		if (toIntentAction() != null) {
			for (String action : toIntentAction()) {
				filter.addAction(action);
			}
		}
		return filter;
	}
	
	protected void toReceive(Context context, Intent data,String action){
		
	}

	protected List<String> toIntentAction() {
		return null;
	}
	
	protected String toIntentActionS(){
		return null;
	}
	
	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		super.onDestroy();
	}
	
}
