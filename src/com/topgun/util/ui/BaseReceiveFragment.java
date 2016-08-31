package com.topgun.util.ui;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

public class BaseReceiveFragment extends BaseFragment {
	
	public static String TAG;
	
	private BroadcastReceiver receiver;
	private IntentFilter intentFilter;
	private LocalBroadcastManager manager;
	
	public IntentFilter getIntentFilter(){
		return intentFilter;
	}
	
	public void setIntentFilter(IntentFilter intentFilter) {
		this.intentFilter = intentFilter;
	}

	public BroadcastReceiver getReceiver() {
		return receiver;
	}

	public void setReceiver(BroadcastReceiver receiver) {
		this.receiver = receiver;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TAG = getClass().getName();
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		setReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent data) {
				String action = data.getAction();
				toReceive(context, data,action);
			}
		});
		
		setIntentFilter(getNewIntentFilter());
		
		//Ω” ’π„≤•
		manager = LocalBroadcastManager.getInstance(getActivity());
		
		manager.registerReceiver(receiver, intentFilter);
	}
	
	private IntentFilter getNewIntentFilter() {
		IntentFilter filter = new IntentFilter();
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
	
	@Override
	public void onDestroy() {
		manager.unregisterReceiver(receiver);
		super.onDestroy();
	}
}
