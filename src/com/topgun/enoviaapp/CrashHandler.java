package com.topgun.enoviaapp;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.util.Log;

public class CrashHandler implements UncaughtExceptionHandler {

	//�������ã������������ɵ����ģ���Ϊ����һ��Ӧ�ó�������ֻ��Ҫһ��UncaughtExceptionHandlerʵ��
	private static CrashHandler instance;  

	private CrashHandler(){}

	//ͬ�����������ⵥ�����̻߳����³����쳣
	public synchronized static CrashHandler getInstance(){
		if (instance == null){
			instance = new CrashHandler();
		}
		return instance;
	}

	//��ʼ�����ѵ�ǰ�������ó�UncaughtExceptionHandler������
	public void init(Context ctx){
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {  //����δ������쳣����ʱ���ͻ���������� 
		Log.e("Sandy", "uncaughtException, thread: " + thread
				+ " name: " + thread.getName() + " id: " + thread.getId() + "exception: "
				+ ex);
		Log.e("Sandy", thread.getName());
	}

}
