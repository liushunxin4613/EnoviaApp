package com.topgun.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.topgun.enoviaapp.R;
import com.topgun.entity.User;
import com.topgun.model.SoapMessage;
import com.topgun.util.Config;
import com.topgun.util.DataUtil;
import com.topgun.util.UIUtil;
import com.topgun.util.ui.MThreadUtil;
import com.topgun.util.ui.SoapUtil;
import com.topgun.util.ui.Interface.MHandlerI;
import com.topgun.util.ui.Interface.MThreadI;
import com.topgun.util.ui.Interface.MThreadToI;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;

/**
 * 用于后台登录
 * 
 * @author liusx
 *
 */
public class InitUserService extends Service implements MThreadToI,MHandlerI{

	/**
	 * 启动Service的功能请求类型的Key
	 */
	public static final String KEY_START_SERVICE_FOR = "StartServiceFor";

	/**
	 * 任务编号 启动Service以登陆
	 */
	public static final int TASK_LOGIN = 0x0001;
	public static final int TASK_USER_INFO = 0x0002;

	public static final String TAG = "InitUserService";

	public static final int MSG_IS_USER = 1;

	public static final int MSG_USER_INFO = 2;
	
	public static final int KEY_RUN_SOAP = 1;

	private User user;

	public static final String ACTION_INIT_USER_SERVICE_ = "com.topgun.initUserService.";

	public static final String ACTION_LOGIN_SUCCESS = ACTION_INIT_USER_SERVICE_ + "loginSuccess";

	public static final String ACTION_LOGIN_ERROR = ACTION_INIT_USER_SERVICE_ + "loginError";

	public static final String ACTION_USER_INFO_SUCCESS = ACTION_INIT_USER_SERVICE_ + "userInfoSuccess";
	
	public static final String INTENT_USER_INFO = "intentUserInfo";

	private final MThreadUtil threadUtil = new MThreadUtil(new MThreadI() {
		@Override
		public void run() {
			toRun(getKey());
			getThreadUtil().pause();
		}

		@Override
		public void timeLong() {
//			mHandler.sendEmptyMessage(Config.ERROR_NET_LONG_ID);
//			toTimeLong();
		}
	});

	private SoapUtil soapUtil;
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){
		
		@Override
		public void handleMessage(Message msg) {
			switch (msg.arg1) {
			//网络错误
			case Config.ERROR_NET_ID:
				if (msg.what == MSG_IS_USER) {
					sendBroadcast(new Intent(ACTION_LOGIN_ERROR));
				}
				UIUtil.showToast(InitUserService.this, getString(R.string.app_net_error));
				break;
			//服务器错误
			case Config.ERROR_SERVICE_ID:
				if (msg.what == MSG_IS_USER) {
					sendBroadcast(new Intent(ACTION_LOGIN_ERROR));
				}
				UIUtil.showToast(InitUserService.this, getString(R.string.app_service_error));
				break;
			//其他错误
			case Config.ERROR_OTHER_ID:
				String jsonStr = msg.obj.toString();
				if (msg.what == MSG_IS_USER) {
					//密码错误的时候，密码清除
					user.setPwd(null);
					DataUtil.saveUserLoginInfo(InitUserService.this,user,true);
					sendBroadcast(new Intent(ACTION_LOGIN_ERROR));
				}
				UIUtil.showToast(InitUserService.this, jsonStr);
				break;
			//网络超时
			case Config.ERROR_NET_LONG_ID:
				//停止线程
				getThreadUtil().stop();
				if (msg.what == MSG_IS_USER) {
					sendBroadcast(new Intent(ACTION_LOGIN_ERROR));
				}
				break;
			default:
				toHandleMessage(msg);
				break;
			}
		}
	};
	
	private int key;

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public MThreadUtil getThreadUtil() {
		return threadUtil;
	}

	public SoapUtil getSoapUtil() {
		return soapUtil;
	}


	public void setSoapUtil(SoapUtil soapUtil) {
		this.soapUtil = soapUtil;
	}

	@Override
	public Handler getHandler() {
		return mHandler;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	protected void setSoapKey(){
		setKey(KEY_RUN_SOAP);
	}

	@Override
	public void toRun(int key) {
		switch (key) {
		case KEY_RUN_SOAP:
			getSoapUtil().startSoap();
			break;
		}
	}
	
	@Override
	public void toHandleMessage(Message msg) {
		switch (msg.what) {
		case MSG_IS_USER:
			String jsonStr = msg.obj.toString();
			user.setId(jsonStr);
			DataUtil.saveUserLoginInfo(InitUserService.this, user, true);
			sendBroadcast(new Intent(ACTION_LOGIN_SUCCESS));
			UIUtil.showToast(InitUserService.this, getString(R.string.login_success));
			requestUserInfo();
			break;
		case MSG_USER_INFO:
			String name = null;
			String jsonStr1 = msg.obj.toString();
			JSONObject jsonObject = JSON.parseObject(jsonStr1);
			String lastNameStr =jsonObject.getString(Config.USER_LAST_NAME);
			String firstNameStr =jsonObject.getString(Config.USER_FIRST_NAME);
			String email = jsonObject.getString(Config.USER_EMAIL);
			if ((lastNameStr != null) && (firstNameStr != null)) {
				name = lastNameStr+firstNameStr;
			}
			
			if (name != null) {
				user.setName(name);
			}
			
			if (email != null) {
				user.setEmail(email);
			}
			
			UIUtil.showLogI(TAG, email, user);
			
			DataUtil.saveUserLoginInfo(InitUserService.this, user, true);

			//向fragment发送广播信息
			LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_USER_INFO_SUCCESS));
			sendBroadcast(new Intent(ACTION_USER_INFO_SUCCESS));
			
			break;
		}
	}

	@SuppressLint("HandlerLeak")
	@Override
	public void onCreate() {
		super.onCreate();

		soapUtil = new SoapUtil(this);

	};

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent == null) {
			stopSelf();
			return super.onStartCommand(intent, flags, startId);
		}
		int requestCode = intent.getIntExtra(KEY_START_SERVICE_FOR, 0);
		if (requestCode == 0) {
			stopSelf();
			return super.onStartCommand(intent, flags, startId);
		}
		if ((requestCode & TASK_LOGIN) == TASK_LOGIN) {
			requestUser(intent);
		}
		if ((requestCode & TASK_USER_INFO) == TASK_USER_INFO) {
			requestUserInfo();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	private void requestUser(Intent intent){
		
		soapUtil = new SoapUtil(this);
		
		String [] arrayUser = intent.getStringArrayExtra(INTENT_USER_INFO);
		if (arrayUser == null) {
			user = DataUtil.getUserLoginInfo(InitUserService.this);
		}else {
			user = new User(arrayUser[0], arrayUser[1]);
		}
		
		if (checkUser(user)) {
			startVoid(MSG_IS_USER, Config.isUsermethodName, new SoapMessage(user));
		} else {
			sendBroadcast(new Intent(ACTION_LOGIN_ERROR));
		}
	}

	private void requestUserInfo(){
		
		soapUtil = new SoapUtil(this);
		
		if ((user.getId() != null) && (user.getName() == null)) {
			startVoid(MSG_USER_INFO, Config.getOjIfthodName, new SoapMessage(user,user.getId()));
		}
	}

	protected void startVoid(Integer what, String methedName, SoapMessage message){
		setSoapKey();
		getSoapUtil().init(what,methedName,message);
		getThreadUtil().startVoid();
	}
	
	private boolean checkUser(User user) {
		if (null == user) {
			return false;
		}else {
			if (null == user.getUsername()) {
				return false;
			}else {
				if (user.getUsername().trim().length() == 0) {
					return false;
				}
			}
			if (null == user.getPwd()) {
				return false;
			}else {
				if (user.getPwd().trim().length() == 0) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void onDestroy() {
		getThreadUtil().stop();
		super.onDestroy();
	}

	@Override
	public void toTimeLong() {
		
	}

}
