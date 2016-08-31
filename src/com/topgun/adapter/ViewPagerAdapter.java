package com.topgun.adapter;

import java.util.List;
import java.util.Map;

import com.topgun.fragment.IssueArrayFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.ArrayMap;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

	public static String TAG = "ViewPagerAdapter";
	
	private Map<String,IssueArrayFragment> map = new ArrayMap<String,IssueArrayFragment>();
	private List<String> titleData;

	public static int position = 0;
	
	public ViewPagerAdapter(FragmentManager fm,List<String> titleData) {
		super(fm);
		this.titleData = titleData;
	}
	
	@Override
	public Fragment getItem(int position) {
		
		ViewPagerAdapter.position = position ;
		
		IssueArrayFragment fragment = null;
		String key = titleData.get(position);
		if (map.get(key) == null) {
			fragment = new IssueArrayFragment();
			map.put(key, fragment);
		}else {
			fragment =  map.get(key);
		}
		
		return fragment;
	}

	@Override
	public int getCount() {
		return titleData.size();
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		if (titleData == null) {
			return super.getPageTitle(position);
		} else {
			return titleData.get(position);
		}
	}

}
