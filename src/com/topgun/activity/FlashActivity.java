package com.topgun.activity;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.topgun.enoviaapp.MainActivity;
import com.topgun.enoviaapp.R;
import com.topgun.service.InitUserService;
import com.topgun.util.ui.KitKatUtils;
import com.topgun.util.ui.BaseReceiveActivity;

import android.content.Context;
import android.content.Intent;

/**
 * ��������
 * 
 * @author liusx
 */
public class FlashActivity extends BaseReceiveActivity {

	/**
	 * ��Activity��С����ʱʱ��
	 */
	private static int DEFAULT_DELAY = 2000;

	private boolean isTime;

	protected void toReceive(Context context, Intent data, String action) {
		finishActivity(MainActivity.class);
//		if (action.equals(InitUserService.ACTION_LOGIN_SUCCESS)) {
//			finishActivity(MainActivity.class);
//		}else if (action.equals(InitUserService.ACTION_LOGIN_ERROR)) {
//			finishActivity(LoginActivity.class);
//		}
	};

	@Override
	protected List<String> toIntentAction() {
		List<String> list = new ArrayList<String>();
		list.add(InitUserService.ACTION_LOGIN_SUCCESS);
		list.add(InitUserService.ACTION_LOGIN_ERROR);
		return list;
	}

	@Override
	protected Integer getLayoutResource() {
		return R.layout.activity_flash;
	}

	@Override
	protected void listenView() {
		super.listenView();
		KitKatUtils.setStatusBar(this, true);
	}

	@Override
	protected void init() {
		//�ȴ�ʱ��
		isTime = false;
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				isTime = true;
			}
		}, DEFAULT_DELAY);
		
		//�����Զ���¼����
		Intent intent = new Intent(this,InitUserService.class);
		intent.putExtra(InitUserService.KEY_START_SERVICE_FOR,
				InitUserService.TASK_LOGIN);
		startService(intent);
	}


	/**
	 * ����Activity ��ת����ָ����Activity
	 * 
	 * @param clazzTo
	 *            ת����Ŀ��Activity
	 */
	public void finishActivity(final Class<?> clazzTo) {
		if (!isTime) {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					finishActivity(clazzTo);
				}
			}, 500);
		} else {
			startActivity(new Intent(this, clazzTo));
			super.finish();
		}
	};

}
