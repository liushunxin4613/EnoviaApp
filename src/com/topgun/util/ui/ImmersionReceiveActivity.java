package com.topgun.util.ui;

import android.graphics.drawable.Drawable;
import android.view.MenuItem;

import com.topgun.enoviaapp.R;

public class ImmersionReceiveActivity extends BaseReceiveActivity{
	/**
	 * 标题栏的字体颜色，是否为白色，默认为白色
	 */
	private boolean isLight = true;
	/**
	 * 状态栏背景色
	 */
	private int statusColor;

	private SystemBarTintManager systemBarManager;

	/**
	 * @return 返回通知栏的颜色，是否为白色
	 */
	public boolean isLight() {
		return isLight;
	}

	/**
	 * @param isLight
	 *            设置通知栏的字体颜色是否为白色（仅对miui有效）,true 白色; false 黑色
	 */
	public void setLight(boolean isLight) {
		this.isLight = isLight;
		KitKatUtils.setStatusBar(this, isLight);
	}

	/**
	 * 设置通知栏背景色
	 * 
	 * @param resid
	 */
	public void setStatusColor(int color) {
		this.statusColor = color;
		systemBarManager = new SystemBarTintManager(this);
		systemBarManager.setStatusBarTintEnabled(true);
		systemBarManager.setStatusBarTintColor(statusColor);
	}

	/**
	 * 设置通知栏背景色
	 * 
	 * @param drawable
	 */
	public void setBackgroundDrawable(Drawable drawable) {
		this.getWindow().setBackgroundDrawable(drawable);
	}

	@Override
	protected void listenView() {
		KitKatUtils.setStatusBar(this, isLight);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);
		setStatusColor(getResources().getColor(R.color.immersionColor));
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
