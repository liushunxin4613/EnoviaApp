package com.topgun.util.ui;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity{

	public static String TAG;

	protected static BaseActivity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TAG = getClass().getName();
		activity = this;
		if (getLayoutResource() != null) {			
			setContentView(getLayoutResource());
			listenView();
		}
		init();

	}

	protected void startActivityFinish(Class<?> activity){
		startActivity(new Intent(this,activity));
		getStaticActivity().finish();
	}

	public BaseActivity getStaticActivity(){
		activity = this;
		return activity;
	}

	protected Integer getLayoutResource(){
		return null;
	}

	protected List<Fragment> getFragmentList(){
		return null;
	}

	protected void listenView() {
	}

	protected void init() {
	}

}
