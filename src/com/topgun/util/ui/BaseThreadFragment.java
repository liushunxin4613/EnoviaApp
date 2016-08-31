package com.topgun.util.ui;


import com.alibaba.fastjson.JSONArray;
import com.topgun.dialog.LoadingDialog;
import com.topgun.dialog.LoadingDialog.OnBackPressedLisener;
import com.topgun.enoviaapp.R;
import com.topgun.model.SoapMessage;
import com.topgun.util.Config;
import com.topgun.util.UIUtil;
import com.topgun.util.ui.Interface.MHandlerI;
import com.topgun.util.ui.Interface.MThreadI;
import com.topgun.util.ui.Interface.MThreadToI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;

@SuppressLint("HandlerLeak")
public class BaseThreadFragment extends BaseFragment implements MThreadToI,MHandlerI{

	public static String TAG;

	private boolean isDismiss;

	//	public void setRun(boolean isRun) {
	//		this.isRun = isRun;
	//	}

	private final MThreadUtil threadUtil = new MThreadUtil(new MThreadI() {
		@Override
		public void run() {
			if (isLoadingDialog) {
				isDismiss = true;
				loadingDialog = new LoadingDialog(getString(R.string.dialog_loading));
				loadingDialog.setOnBackPressedListener(new OnBackPressedLisener() {
					@Override
					public void onBackPressed() {
						loadingDialog.dismiss();
						isDismiss = false;
						threadUtil.stop();
						threadUtil.interrupt();
						//						isRun = false;
						getActivity().onBackPressed();
					}
				});
				FragmentTransaction ft = getChildFragmentManager().beginTransaction();
				if ((loadingDialog != null) && (ft != null)) {						
					loadingDialog.show(ft, TAG);
				}
			}
			//			if(isRun){
			toRun(getKey());
			//			}
			threadUtil.pause();
			dismiss();
		}

		@Override
		public void timeLong() {
			//			mHandler.sendEmptyMessage(Config.ERROR_NET_LONG_ID);
			//			if (loadingDialog != null) {
			//				loadingDialog.dismiss();
			//			}
			//			toTimeLong();
		}
	});

	private SoapUtil soapUtil;

	private LoadingDialog loadingDialog;

	private boolean isLoadingDialog;

	public static final int KEY_RUN_SOAP = 1;

	private int key;

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	private final Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.arg1) {
			case Config.ERROR_NET_ID:
				UIUtil.showToast(getActivity(), getString(R.string.app_net_error));
				break;
			case Config.ERROR_SERVICE_ID:
				UIUtil.showToast(getActivity(), getString(R.string.app_net_error));
				break;
			case Config.ERROR_OTHER_ID:
				String jsonStr = msg.obj.toString();
				UIUtil.showToast(getActivity(), jsonStr);
				break;
				//ÍøÂç³¬Ê±
			case Config.ERROR_NET_LONG_ID:
				//				Log.i(TAG, "ÍøÂç³¬Ê±");
				//				UIUtil.showToast(getActivity(), getString(R.string.app_net_long_error));
				break;
			default:
				toHandleMessage(msg);
				break;
			} 
		}
	};

	protected void setLoadingDialog(boolean isLoadingDialog) {
		this.isLoadingDialog = isLoadingDialog;
	}

	public LoadingDialog getLoadingDialog() {
		return loadingDialog;
	}

	protected MThreadUtil getThreadUtil() {
		return threadUtil;
	}

	protected SoapUtil getSoapUtil() {
		return soapUtil;
	}

	protected void setSoapUtil(SoapUtil soapUtil) {
		this.soapUtil = soapUtil;
	}

	@Override
	public Handler getHandler() {
		return mHandler;
	}

	@SuppressLint("HandlerLeak")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TAG = getClass().getName();

		soapUtil = new SoapUtil(this);

	}

	protected void dismiss(){
		if (loadingDialog != null) {
			if (isDismiss) {
				loadingDialog.dismiss();
			}
		}
	}

	@Override
	public void toHandleMessage(Message msg) {
		/*switch (msg.arg1) {
		case Config.ERROR_NET_ID:
			UIUtil.showToast(getActivity(), getString(R.string.app_net_error));
			break;
		case Config.ERROR_SERVICE_ID:
			UIUtil.showToast(getActivity(), getString(R.string.app_net_error));
			break;
		case Config.ERROR_OTHER_ID:
			String jsonStr = msg.obj.toString();
			UIUtil.showToast(getActivity(), jsonStr);
			break;
			//ÍøÂç³¬Ê±
		case Config.ERROR_NET_LONG_ID:
//			Log.i(TAG, "ÍøÂç³¬Ê±");
//			UIUtil.showToast(getActivity(), getString(R.string.app_net_long_error));
			break;
		}*/
	}

	protected boolean isToHandleMessage(Message msg){
		if (msg.obj == null)return false;
		return true;
	}

	protected boolean isTo(Message msg){
		if(msg.arg1 != msg.what)return true;
		return false;
	}

	protected boolean isJsonParse(String s){
		try {
			JSONArray.parse(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	protected void start(Integer what,String methedName,SoapMessage message){
		setSoapKey();
		if (!threadUtil.isThread()) {
			threadUtil.init();
		}
		getSoapUtil().init(what, methedName, message);
		threadUtil.start();
	}

	protected void setSoapKey(){
		setKey(KEY_RUN_SOAP);
	}

	protected void start(Integer what,SoapMessage message){
		setSoapKey();
		if (!threadUtil.isThread()) {
			threadUtil.init();
		}
		getSoapUtil().init(what, message);
		threadUtil.start();
	}

	protected void startVoid(Integer what,String methedName,SoapMessage message){
		setSoapKey();
		getSoapUtil().init(what, methedName, message);
		threadUtil.startVoid();
	}

	protected void startVoid(Integer what,SoapMessage message){
		setSoapKey();
		getSoapUtil().init(what, message);
		threadUtil.startVoid();
	}

	protected void start(int key) {
		setKey(key);
		threadUtil.startVoid();
	}

	@Override
	public void onDestroy() {
		threadUtil.stop();
		super.onDestroy();
	}

	@Override
	public void toRun(int key) {
		switch (key) {
		case KEY_RUN_SOAP:
			getSoapUtil().startSoap();
			dismiss();
			break;
		}

	}

	@Override
	public void toTimeLong() {

	}

}
