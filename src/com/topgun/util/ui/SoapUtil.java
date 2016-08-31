package com.topgun.util.ui;

import android.os.Message;

import com.topgun.model.Json;
import com.topgun.model.SoapMessage;
import com.topgun.util.Config;
import com.topgun.util.JsonHttpUtil;
import com.topgun.util.UIUtil;
import com.topgun.util.ui.Interface.MHandlerI;

public class SoapUtil {

	public static String TAG = "SoapUtil";

	private static Integer what;
	private static SoapMessage message;
	private static String methedName;

	public static int delay = 10000;
	public static boolean isDelay;
	public static boolean isDelayShow;

	private static MHandlerI handlerI;

	public SoapUtil(MHandlerI handlerI) {
		SoapUtil.handlerI = handlerI;
	}

	public static Integer getWhat() {
		return what;
	}

	public static void setWhat(Integer what) {
		SoapUtil.what = what;
	}

	public static SoapMessage getMessage() {
		return message;
	}

	public static void setMessage(SoapMessage message) {
		SoapUtil.message = message;
	}

	public static String getMethedName() {
		return methedName;
	}

	public static void setMethedName(String methedName) {
		SoapUtil.methedName = methedName;
	}

	private static synchronized void initSoap(int what,String methedName,SoapMessage message){
		UIUtil.showLogI(TAG, "initSoap", null);
		
		Json json = JsonHttpUtil.queryJson(methedName, JsonHttpUtil.getUserHashMap(message));

		if (json == null) {
			sendMsg(what,Config.ERROR_NET_ID,null);
		}else {
			UIUtil.showLogI(TAG, json.getResponse(),json.getData());
			if (json.getResponse().equals(Config.SUCCESS)) {
				sendMsg(what,what,json.getData());
			}else if (json.getResponse().equals(Config.ERROR_SERVICE)) {
				sendMsg(what,Config.ERROR_NET_ID, json.getData());
			}else {
				sendMsg(what,Config.ERROR_OTHER_ID,json.getResponse());
			}
		}
	}

	public void init(Integer what,String methedName,SoapMessage message){
		setMessage(message);
		setMethedName(methedName);
		setWhat(what);
	}

	public void init(Integer what,SoapMessage message){
		setMessage(message);
		setWhat(what);
	}

	public void startSoap(){
		initSoap(what, methedName, message);
	}

	private static synchronized void sendMsg(Integer what,Integer index,Object obj) {
		Message msg = new Message();
		if (what != null) {
			msg.what = what;
		}
		if (index != null) {
			msg.arg1 = index;
		}
		msg.obj = obj;
		handlerI.getHandler().sendMessage(msg);
	}

	public void clean(){
		what = null;
		methedName = null;
		message =null;
	}

}
