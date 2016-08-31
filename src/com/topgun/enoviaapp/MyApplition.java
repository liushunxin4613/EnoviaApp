package com.topgun.enoviaapp;

import android.app.Application;

public class MyApplition extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		CrashHandler handler = CrashHandler.getInstance();
		
		//在Appliction里面设置我们的异常处理器为UncaughtExceptionHandler处理器
		handler.init(getApplicationContext()); 
		
	}

}
