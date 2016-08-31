package com.topgun.fragment;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topgun.activity.InboxTaskDetailActivity;
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
 * 流程任务fragment
 * 
 * @author liusx
 *
 */
public class InboxTaskFragment extends BaseThreadFragment implements RefreshListener,LoadMoreListener, OnItemClickListener, FragmentViewNI{

	public static int type = 1;

	public static final String KEY_DATA = "keyData";
	public static final String APPLICATION_MAP_INBOX_DATA = "inboxData";

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

	private SoapMessage soapMessage;

	private static InboxTaskFragment fragment;

	public static InboxTaskFragment getInstance() {
		if (fragment == null)
			fragment = new InboxTaskFragment();
		return fragment;
	}

	@Override
	protected Integer getLayoutResource() {
		return R.layout.fragment_inbox_task;
	}

	@Override
	protected void listenView(View rootView) {
		super.listenView(rootView);
		
		tv = (TextView) rootView.findViewById(R.id.fm_inbox_tv);
		
		listView = (DynamicListView) rootView.findViewById(R.id.inbox_listView);
		listView.setOnMoreListener(this);
		listView.setOnRefreshListener(this);
		listView.setOnItemClickListener(this);
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
	
	@Override
	public void init() {

		user = DataUtil.getUserLoginInfo(getActivity());
		if (null == user) return;

		soapMessage = new SoapMessage(user, Config.getMyWorkflowTaskTypeArray[type], INDEX_MSG, Config.PAGE_SIZE);
		setLoadingDialog(true);
		startVoid(MSG_JSON_INIT, Config.getMyWorkflowTaskMethodName, soapMessage);
	}

	/**
	 * 点击item
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		DataUtil.saveIssue(getActivity(), adapter.getItem(--position));
		startActivity(new Intent(getActivity(), InboxTaskDetailActivity.class));
		getActivity().finish();
	}

	@Override
	public boolean onLoadMore(DynamicListView dynamicListView) {
		//加载数据
		if ((adapter.getSize() % Config.PAGE_SIZE) == 0) {
			INDEX_MSG += Config.PAGE_SIZE;
			soapMessage.setIni3(INDEX_MSG);
			startVoid(MSG_JSON_MORE, soapMessage);
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
		if (soapMessage != null) {
			soapMessage.setIni3(0);
			startVoid(MSG_JSON_REFRESH,soapMessage);
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
		//初始化数据JsonHttpUtil.getArrayList(getActivity(),jsonStr)
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
		adapter = new HomeListAdapter(data, getActivity(), this);
		adapter.setStr(R.layout.item_task_list,Config.ITEM_TASK,Config.SINT_INBOX,Config.STR_INBOX);
		listView.setAdapter(adapter);
	}

}
