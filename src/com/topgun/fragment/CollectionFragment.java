package com.topgun.fragment;

import com.topgun.enoviaapp.R;
import com.topgun.util.ui.BaseFragment;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * 
 * 收藏页面
 * 
 * @author liusx
 *
 */
public class CollectionFragment extends BaseFragment {
	
	private static CollectionFragment fragment;

	public static Fragment getInstance() {
		if (fragment == null)
			fragment = new CollectionFragment();
		return fragment;
	}

	@Override
	protected Integer getLayoutResource() {
		return R.layout.fragment_main_collection;
	}
	
	/**
	 * 控制当前fragment显示或隐藏，用于Activity中的Fragment切换
	 */
	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if (this.getView() != null)
			this.getView()
					.setVisibility(menuVisible ? View.VISIBLE : View.GONE);
	}
	
}
