package com.topgun.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.topgun.activity.InboxTaskActivity;
import com.topgun.activity.IssueActivity;
import com.topgun.activity.TaskActivity;
import com.topgun.adapter.HomeGridViewAdapter;
import com.topgun.customLib.ExpandGridView;
import com.topgun.customLib.SlideView;
import com.topgun.enoviaapp.R;
import com.topgun.model.HomeGridItem;
import com.topgun.util.UIUtil;
import com.topgun.util.ui.AccordionTransformer;
import com.topgun.util.ui.BaseFragment;

/**
 * 首页fragement
 * 
 * @author liusx
 *
 */
public class HomeFragment extends BaseFragment implements com.topgun.customLib.SlideView.OnItemClickListener{

	private SlideView slideView;

	private ExpandGridView gridView;

	/**
	 * 推送图片切换时长
	 */
	private static final int HOME_PUSH_IMG_CHANGE_TIME = 10000;

	@Override
	protected Integer getLayoutResource() {
		return R.layout.fragment_main_home;
	}
	
	@Override
	protected void listenView(View rootView) {
		super.listenView(rootView);
		
		slideView = (SlideView) rootView.findViewById(R.id.slideViewContainer);
		gridView = (ExpandGridView) rootView.findViewById(R.id.home_grid);

		slideView.setTransformer(new AccordionTransformer());
		slideView.setDuration(300);
		slideView.setOnItemClickListener(this);

		getDefaultHomePush();
	}
	
	@Override
	protected void init() {
		ArrayList<HomeGridItem> datalist = new ArrayList<HomeGridItem>();
		datalist.add(new HomeGridItem(getResources().getString(
				R.string.fragment_home_question_title), R.drawable.icon_home_part));
		datalist.add(new HomeGridItem(getResources().getString(
				R.string.fragment_home_process_task_title),
				R.drawable.icon_home_second));
		datalist.add(new HomeGridItem(getResources().getString(
				R.string.fragment_home_wbs_task_title), R.drawable.icon_home_mall));
		datalist.add(new HomeGridItem(getResources().getString(
				R.string.fragment_home_more_title), R.drawable.icon_home_more));

		HomeGridViewAdapter adapter = new HomeGridViewAdapter(datalist,
				getActivity());
		gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					startActivity(new Intent(getActivity(),IssueActivity.class));
					break;
				case 1:
					startActivity(new Intent(getActivity(),InboxTaskActivity.class));
					break;
				case 2:
					startActivity(new Intent(getActivity(),TaskActivity.class));
					break;
				case 3:
					UIUtil.showToast(getActivity(),R.string.fragment_home_more_title);
					break;
				}
			}
		});
	}
	
	/**
	 * 获取首页推送内容失败或没有推送内容时，设置默认图片
	 */
	private void getDefaultHomePush() {
		slideView.setUp(new int[] { R.drawable.item_02, R.drawable.item_03,
				 R.drawable.item_01 },
				HOME_PUSH_IMG_CHANGE_TIME);
	}

	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if (this.getView() != null)
			this.getView()
			.setVisibility(menuVisible ? View.VISIBLE : View.GONE);
	}

	@Override
	public void onResume() {
		super.onResume();
		slideView.startPlay();
	}

	@Override
	public void onPause() {
		super.onPause();
		slideView.stopPlay();
	}

	@Override
	public void onItemClick(SlideView sView, View view, int position) {

	}

}
