package com.topgun.fragment;


import com.topgun.enoviaapp.R;
import com.topgun.util.ui.BaseFragment;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * 
 * ∑¢œ÷“≥√Ê
 * 
 * @author liusx
 *
 */
public class FindFragemnt extends BaseFragment {

	private static FindFragemnt fragment;

	public static Fragment getInstance() {
		if (fragment == null)
			fragment = new FindFragemnt();
		return fragment;
	}
	
	@Override
	protected Integer getLayoutResource() {
		return R.layout.fragment_main_find;
	}
	
	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if (this.getView() != null)
			this.getView()
					.setVisibility(menuVisible ? View.VISIBLE : View.GONE);
	}
	
}
