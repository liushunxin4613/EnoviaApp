package com.topgun.enoviaapp;

import android.app.Application;

public class MyApplition extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		CrashHandler handler = CrashHandler.getInstance();
		
		//��Appliction�����������ǵ��쳣������ΪUncaughtExceptionHandler������
		handler.init(getApplicationContext()); 
		
	}

}
