package com.topgun.enoviaapp;

import java.util.Timer;
import java.util.TimerTask;

import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.topgun.activity.PublishIssueActivity;
import com.topgun.fragment.CollectionFragment;
import com.topgun.fragment.FindFragemnt;
import com.topgun.fragment.HomeFragment;
import com.topgun.fragment.MeFragment;
import com.topgun.service.InitUserService;
import com.topgun.util.Config;
import com.topgun.util.DataUtil;
import com.topgun.util.UIUtil;
import com.topgun.util.ui.ImmersionReceiveActivity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

/**
 * 
 * ��Activity
 * 
 * @author liusx
 *
 */
public class MainActivity extends ImmersionReceiveActivity{

	public static final String TAG = "MainActivity";

	/**
	 * �ϲ�fragment
	 */
	private FrameLayout container;

	/**
	 * �ײ��ĸ�Radio��ť
	 */
	private RadioButton[] bottomBtn;

	private RadioGroup radioGroup;
	/**
	 * ��ǰfragment��λ��
	 */
	private int location = 0;

	/**
	 * ͬ��Fragment��RadioGroup�ĵ���¼�
	 */
	private FragmentPagerAdapter pageAdapter = new FragmentPagerAdapter(
			getSupportFragmentManager()) {

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public Fragment getItem(int pos) {
			switch (pos) {
			case R.id.radio_home:
				return new HomeFragment();
			case R.id.radio_collection:
				return new CollectionFragment();
			case R.id.radio_find:
				return new FindFragemnt();
			case R.id.radio_me:
				return new MeFragment();
			}
			return null;
		}

		public void destroyItem(ViewGroup container, int position, Object object) {
			Fragment fragment = (Fragment) object;
			container.removeView(fragment.getView());
		};

	};

	/**
	 * ȷ���˳�
	 */
	private boolean confirm = false;

	@Override
	protected Integer getLayoutResource() {
		return R.layout.activity_main;
	}
	
	@Override
	protected void listenView() {
		super.listenView();
		
		container = (FrameLayout) findViewById(R.id.container);
		radioGroup = (RadioGroup) findViewById(R.id.BottomGroup);
		bottomBtn = new RadioButton[4];
		bottomBtn[0] = (RadioButton) findViewById(R.id.radio_home);
		bottomBtn[1] = (RadioButton) findViewById(R.id.radio_collection);
		bottomBtn[2] = (RadioButton) findViewById(R.id.radio_find);
		bottomBtn[3] = (RadioButton) findViewById(R.id.radio_me);
		
		listenRadioGroup();
		
		getActionBar().setDisplayShowHomeEnabled(false);
		
		perpareUI();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		setTitle();
	}
	
	private void setTitle(){
		UIUtil.showLogI(TAG, "1", null);
		ActionBar actionBar = getActionBar();
		if (DataUtil.getUserLoginInfo(getApplicationContext()) == null) return;
		UIUtil.showLogI(TAG, "2", null);
		String name = DataUtil.getUserLoginInfo(getApplicationContext()).getName();
		if(name == null) return;
		UIUtil.showLogI(TAG, "3", name);
		actionBar.setTitle(getString(R.string.welcome_ago) + name);
		UIUtil.showLogI(TAG, "3", getString(R.string.welcome_ago) + name);
	}
	
	@Override
	protected String toIntentActionS() {
		return InitUserService.ACTION_USER_INFO_SUCCESS;
	}
	
	@Override
	protected void toReceive(Context context, Intent data, String action) {
		super.toReceive(context, data, action);
		if(action.equals(InitUserService.ACTION_USER_INFO_SUCCESS)){
			setTitle();
		}
	}
	
	/**
	 * RadioGroup���ͬ��fragment
	 */
	private void listenRadioGroup(){
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				Fragment fragment = (Fragment) pageAdapter.instantiateItem(
						container, checkedId);
				pageAdapter.setPrimaryItem(container, 0, fragment);
				pageAdapter.finishUpdate(container);
				bottomBtn[location].setTextColor(getResources().getColor(
						R.color.text_gray));
				switch (checkedId) {
				case R.id.radio_home:
					location = 0;
					break;
				case R.id.radio_collection:
					location = 1;
					break;
				case R.id.radio_find:
					location = 2;
					break;
				case R.id.radio_me:
					location = 3;
					break;
				}
				bottomBtn[location].setTextColor(getResources().getColor(
						R.color.base_blue));
			}

		});
	}



	/**
	 * ��ʼ��ui
	 */
	public void perpareUI() {
		setStatusColor(getResources().getColor(R.color.immersionColor));

		SharedPreferences sharedPre = getSharedPreferences(
				Config.SHAREPREFERENCE_SETTING, MODE_PRIVATE);
		location = sharedPre.getInt(Config.SETTING_HOME_INDEX, 0);

		bottomBtn[location].setChecked(true);
		bottomBtn[location].setTextColor(getResources().getColor(
				R.color.base_blue));

		Fragment fragment = null;
		switch (location) {
		case 0:
			fragment = (Fragment) pageAdapter.instantiateItem(container,
					R.id.radio_home);
			break;
		case 1:
			fragment = (Fragment) pageAdapter.instantiateItem(container,
					R.id.radio_collection);
			break;
		case 2:
			fragment = (Fragment) pageAdapter.instantiateItem(container,
					R.id.radio_find);
			break;
		case 3:
			fragment = (Fragment) pageAdapter.instantiateItem(container,
					R.id.radio_me);
			break;
		}
		pageAdapter.setPrimaryItem(container, 0, fragment);
		pageAdapter.finishUpdate(container);
	}

	/**
	 * ��������ʾ�˵�
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * �Ե���˵��¼�������Ӧ
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		//�ύ����
		case R.id.action_publish_question:
			startActivity(new Intent(this,PublishIssueActivity.class));
			break;
		}
		return super.onOptionsItemSelected(item);
	}


	/**
	 * ����ʱ����
	 */
	@Override
	protected void onDestroy() {
		//����ʱ���������Ϣ
		SharedPreferences.Editor editor = getSharedPreferences(
				Config.SHAREPREFERENCE_SETTING, MODE_PRIVATE).edit();
		editor.putInt(Config.SETTING_HOME_INDEX, location);
		editor.commit();

		super.onDestroy();
	}

	/**
	 * ���˼�
	 */
	@Override
	public void onBackPressed() {
		if (!confirm) {
			confirm = true;
			Toast.makeText(this, R.string.main_exit_remind, Toast.LENGTH_SHORT)
			.show();
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					confirm = false;
				}
			}, 2000);
		} else {
			super.onBackPressed();
		}
	}


}

