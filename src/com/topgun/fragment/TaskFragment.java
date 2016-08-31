package com.topgun.fragment;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topgun.activity.TaskDetailActivity;
import com.topgun.adapter.HomeListAdapter;
import com.topgun.customLib.DynamicListView;
import com.topgun.customLib.DynamicListView.LoadMoreListener;
import com.topgun.customLib.DynamicListView.RefreshListener;
import com.topgun.enoviaapp.R;
import com.topgun.entity.User;
import com.topgun.model.SoapMessage;
import com.topgun.util.Config;
import com.topgun.util.DataUtil;
import com.topgun.util.ui.BaseThreadFragment;
import com.topgun.util.ui.Interface.FragmentViewNI;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


/**
 * WBS任务Fragment
 * 
 * @author liushunxin
 *
 */
public class TaskFragment extends BaseThreadFragment implements RefreshListener,LoadMoreListener, OnItemClickListener, FragmentViewNI {

	public static int type = 0;
	
	public static final String KEY_DATA = "keyData";
	public static final String APPLICATION_MAP_TASK_DATA = "taskData";

	/**
	 * json消息传递，初始化
	 */
	public static final int MSG_JSON_INIT = 1;

	/**
	 * json消息传递，刷新
	 */
	public static final int MSG_JSON_REFRESH = 2;

	/**
	 * json消息传递，加载
	 */
	public static final int MSG_JSON_MORE = 3;

	/**
	 * item位置
	 */
	public static int INDEX_MSG = 0;

	private DynamicListView listView;
	
	private TextView tv;

	private HomeListAdapter adapter;

	private User user;

	private SoapMessage message;

	private static TaskFragment fragment;

	public static TaskFragment getInstance() {
		if (fragment == null)
			fragment = new TaskFragment();
		return fragment;
	}

	@Override
	protected Integer getLayoutResource() {
		return R.layout.fragment_task;
	}

	@Override
	protected void listenView(View rootView) {
		super.listenView(rootView);

		tv = (TextView) rootView.findViewById(R.id.fm_task_tv);
		
		listView = (DynamicListView) rootView.findViewById(R.id.task_listView);
		listView.setOnMoreListener(this);
		listView.setOnRefreshListener(this);
		listView.setOnItemClickListener(this);

	}

	@Override
	protected void init() {
		user = DataUtil.getUserLoginInfo(getActivity());
		if (null == user) return;

		message = new SoapMessage(user, Config.getMyWBSTaskTypeArray[type], INDEX_MSG, Config.PAGE_SIZE);
		setLoadingDialog(true);
		startVoid(MSG_JSON_INIT, Config.getMyWBSTaskMethodName, message);
	}

	/**
	 * 点击item
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		DataUtil.saveIssue(getActivity(), adapter.getItem(--position));
		startActivity(new Intent(getActivity(), TaskDetailActivity.class));
		getActivity().finish();
	}

	@Override
	public boolean onLoadMore(DynamicListView dynamicListView) {
		//加载数据
		if ((adapter.getSize() % Config.PAGE_SIZE) == 0) {
			INDEX_MSG += Config.PAGE_SIZE;
			message.setIni3(INDEX_MSG);
			startVoid(MSG_JSON_MORE, message);
		}else {
			listView.setOnMoreListener(null);
		}
		return false;
		
	}

	@Override
	public void onCancelLoadMore(DynamicListView dynamicListView) {
	}

	@Override
	public boolean onRefresh(DynamicListView dynamicListView) {
		//刷新数据
		if (message != null) {
			message.setIni3(0);
			startVoid(MSG_JSON_REFRESH, message);
		}
		return false;
	}

	@Override
	public void onCancelRefresh(DynamicListView dynamicListView) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void toHandleMessage(Message msg) {
		listView.doneRefresh();
		listView.doneMore();
		
		super.toHandleMessage(msg);
		if(!isToHandleMessage(msg))return;
		
		String jsonStr = msg.obj.toString();
		switch (msg.what) {
		//初始化数据
		case MSG_JSON_INIT:
			setLoadingDialog(false);
			List<JSONObject> data = null;
			if(isJsonParse(jsonStr)){
				data = (List<JSONObject>) JSONArray.parse(jsonStr);
			}
			
			initListviewData(data);
			break;
			//刷新数据
		case MSG_JSON_REFRESH:
			
			List<JSONObject> dataRefresh = null;
			if(isJsonParse(jsonStr)){
				dataRefresh = (List<JSONObject>) JSONArray.parse(jsonStr);
			}
			
			if (adapter == null) {
				initListviewData(dataRefresh);
			}else {
				adapter.addItemsToHead(dataRefresh);
			}
			break;
			//加载数据
		case MSG_JSON_MORE:
			
			List<JSONObject> dataMore = null;
			if(isJsonParse(jsonStr)){
				dataMore = (List<JSONObject>) JSONArray.parse(jsonStr);
			}
			
			if (adapter == null) {
				initListviewData(dataMore);
			}else {
				adapter.addItems(dataMore);
			}
			
			if ((adapter.getSize() % Config.PAGE_SIZE) != 0) {	
				listView.setOnMoreListener(null);
			}
			
			break;
		}
	}
	
	private void initListviewData(List<JSONObject> data){
		if (data == null) {
			data = new ArrayList<JSONObject>();
		}
		adapter = new HomeListAdapter(data, getActivity(),this);
		adapter.setStr(R.layout.item_task_list,Config.ITEM_TASK,Config.SINT_TASK,Config.STR_TASK);
		listView.setAdapter(adapter);
	}

	@Override
	public void dataNullView() {
		tv.setVisibility(View.VISIBLE);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);
	}

	@Override
	public void dataNotNullView() {
		tv.setVisibility(View.GONE);
		listView.setHeaderDividersEnabled(true);
		listView.setFooterDividersEnabled(true);
	}

}
