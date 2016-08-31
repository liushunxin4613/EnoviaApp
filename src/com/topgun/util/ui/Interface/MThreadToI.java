package com.topgun.util.ui.Interface;

import android.os.Message;

public interface MThreadToI {
	void toHandleMessage(Message msg);
	void toRun(int key);
	void toTimeLong();
}
