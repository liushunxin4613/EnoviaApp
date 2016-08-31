package com.topgun.enoviaapp;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.util.Log;

public class CrashHandler implements UncaughtExceptionHandler {

	//单例引用，这里我们做成单例的，因为我们一个应用程序里面只需要一个UncaughtExceptionHandler实例
	private static CrashHandler instance;  

	private CrashHandler(){}

	//同步方法，以免单例多线程环境下出现异常
	public synchronized static CrashHandler getInstance(){
		if (instance == null){
			instance = new CrashHandler();
		}
		return instance;
	}

	//初始化，把当前对象设置成UncaughtExceptionHandler处理器
	public void init(Context ctx){
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {  //当有未处理的异常发生时，就会来到这里。。 
		Log.e("Sandy", "uncaughtException, thread: " + thread
				+ " name: " + thread.getName() + " id: " + thread.getId() + "exception: "
				+ ex);
		Log.e("Sandy", thread.getName());
	}

}
