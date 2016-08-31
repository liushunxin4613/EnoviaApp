package com.topgun.fragment;

import com.topgun.enoviaapp.R;
import com.topgun.util.ui.BaseFragment;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * 
 * �ղ�ҳ��
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
	 * ���Ƶ�ǰfragment��ʾ�����أ�����Activity�е�Fragment�л�
	 */
	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if (this.getView() != null)
			this.getView()
					.setVisibility(menuVisible ? View.VISIBLE : View.GONE);
	}
	
}
