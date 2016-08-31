package com.topgun.util.ui;

import com.topgun.util.ui.Interface.MThreadI;
import com.topgun.util.ui.Interface.StateI;

public class MThreadUtil implements StateI{
	
	public static final String TAG = "MThreadUtil";
	
	private MThreadI threadI;
	
	private Thread thread;

	private boolean isThread;
	
	private boolean isStart;
	
//	private Timer timer;
	
	private int delay = 5000;
	
	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getDelay() {
		return delay;
	}

	public MThreadUtil(MThreadI threadI) {
		this.threadI = threadI;
	}
	
	public boolean isThread() {
		return isThread;
	}

	public boolean isStart() {
		return isStart;
	}
	
	public void startVoid(){
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				threadI.run();
//				if (timer != null) {
//					timer.cancel();
//				}
			}
		});
		thread.start();
		
//		timer = new Timer();
//		timer.schedule(new TimerTask() {
//			@Override
//			public void run() {
//				if (thread != null) {
//					thread.interrupt();
//				}
//				threadI.timeLong();
//			}
//		}, delay);
	}
	
	@Override
	public void init() {
		isThread = true;
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (isThread) {
					if (isStart) {
						threadI.run();
					}
				}
//				if (timer != null) {
//					timer.cancel();
//				}
			}
		});
		thread.start();
		
//		timer = new Timer();
//		timer.schedule(new TimerTask() {
//			@Override
//			public void run() {
//				if (thread != null) {
//					thread.interrupt();
//				}
//				threadI.timeLong();
//			}
//		}, delay);
	}

	@Override
	public void start() {
		isStart = true;
	}

	@Override
	public void pause() {
		isStart = false;
	}

	@Override
	public void cut() {
		isStart = !isStart;
	}

	@Override
	public void stop() {
		isThread = false;
	}

	@Override
	public void interrupt() {
		if(thread == null) return;
		thread.interrupt();
	}
	
	
}
