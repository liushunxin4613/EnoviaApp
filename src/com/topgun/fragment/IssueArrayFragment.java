package com.topgun.fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topgun.activity.IssueDetailActivity;
import com.topgun.adapter.HomeListAdapter;
import com.topgun.customLib.DynamicListView;
import com.topgun.customLib.DynamicListView.LoadMoreListener;
import com.topgun.customLib.DynamicListView.RefreshListener;
import com.topgun.dialog.ConfirmDialog;
import com.topgun.enoviaapp.R;
import com.topgun.entity.User;
import com.topgun.model.SoapMessage;
import com.topgun.util.Config;
import com.topgun.util.DataUtil;
import com.topgun.util.UIUtil;
import com.topgun.util.ui.BaseThreadFragment;
import com.topgun.util.ui.Interface.FragmentViewNI;

@SuppressWarnings("serial")
@SuppressLint("ValidFragment")
public class IssueArrayFragment extends BaseThreadFragment implements Serializable,RefreshListener,LoadMoreListener,OnItemClickListener,OnItemLongClickListener, FragmentViewNI{

	private int index ;

	public static String APPLICATION_MAP_ISSUE_DATA = "issueData";

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public IssueArrayFragment() {
	}

	public static final String KEY_DATA = "keyData";

	/**
	 * json消息传递，初始化
	 */
	public final int MSG_JSON_INIT = 1;

	/**
	 * json消息传递，刷新
	 */
	public final int MSG_JSON_REFRESH = 2;

	/**
	 * json消息传递，加载
	 */
	public final int MSG_JSON_MORE = 3;

	/**
	 * json消息传递，加载
	 */
	public final int MSG_JSON_ITEM_DELETE = 4;

	/**
	 * item位置
	 */
	private int INDEX_MSG = 0;

	private DynamicListView listView;
	
	private TextView tv;

	private HomeListAdapter adapter;

	private User user;

	private SoapMessage message;

	private String deleteId;

	private int deleteItemId;

	public static final String ACTION_REFRESH = "com.android.intent.refresh";

	@Override
	protected Integer getLayoutResource() {
		return R.layout.fragment_issue;
	}

	@Override
	protected void listenView(View rootView) {
		super.listenView(rootView);
		
		tv = (TextView) rootView.findViewById(R.id.fm_issue_tv);
		
		listView = (DynamicListView) rootView.findViewById(R.id.issue_listView);
		listView.setOnMoreListener(this);
		listView.setOnRefreshListener(this);
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);

	}

	@Override
	protected void init() {
		user = DataUtil.getUserLoginInfo(getActivity());
		if (user == null) {
			user = DataUtil.getUserLoginInfo(getActivity());
		}

		message = new SoapMessage(user,Config.AC_FM_KEY[index],INDEX_MSG,Config.PAGE_SIZE);
		setLoadingDialog(true);
		startVoid(MSG_JSON_INIT, Config.getMyIssueMethodName, message);
	}

	
	
	/**
	 * 点击item
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		DataUtil.saveIssue(getActivity(), adapter.getItem(--position));
		startActivity(new Intent(getActivity(), IssueDetailActivity.class));
		getActivity().finish();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		deleteItemId = --position;
		deleteId = adapter.getItem(deleteItemId).getString(Config.ISSUE_ID);
		showDialog();
		return true;
	};

	private void deleteItem(String deleteId){
		if(deleteId != null){
			startVoid(MSG_JSON_ITEM_DELETE, Config.deleteObjectMethodName, new SoapMessage(user, deleteId));
		}
	}

	private void showDialog(){
		ConfirmDialog dialog = new ConfirmDialog(
				ConfirmDialog.CONFIRM_STYLE_CENTER,
				getString(R.string.dialog_exit_title),
				getString(R.string.dialog_delete_item_exit_msg),
				getString(R.string.dialog_delete_item_exit_yes),
				getString(R.string.dialog_cancel));
		dialog.setOnclickListener(new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					//暂不清理数据
					deleteItem(deleteId);
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		});
		dialog.show(getActivity().getSupportFragmentManager().beginTransaction(), TAG);
	}

	@Override
	public boolean onLoadMore(DynamicListView dynamicListView) {
		
		UIUtil.showLogI(TAG, "x", "加载中...");
		UIUtil.showLogI(TAG, "x", "adapter.getSize()" + adapter.getSize());
		UIUtil.showLogI(TAG, "x", "Config.PAGE_SIZE" + Config.PAGE_SIZE);
		
		//加载数据
		if ((adapter.getSize() % Config.PAGE_SIZE) == 0) {	
			INDEX_MSG+=Config.PAGE_SIZE;
			message.setIni3(INDEX_MSG);
			
			UIUtil.showLogI(TAG, "z", "加载中...");
			
			startVoid(MSG_JSON_MORE, Config.getMyIssueMethodName, message);
			
			return false;
		}else {
			
			listView.setOnMoreListener(null);
			
			return true;
		}
		
	}

	@Override
	public void onCancelLoadMore(DynamicListView dynamicListView) {
	}

	@Override
	public boolean onRefresh(DynamicListView dynamicListView) {
		//刷新数据
		if (message != null) {
			message.setIni3(0);
			startVoid(MSG_JSON_REFRESH, Config.getMyIssueMethodName, message);
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
		
		if(isToHandleMessage(msg)){
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
				} else {
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
				} else {
					adapter.addItems(dataMore);
				}
				
				if ((adapter.getSize() % Config.PAGE_SIZE) != 0) {	
					listView.setOnMoreListener(null);
				}
				
				break;
			}
		}
		
		switch (msg.what) {
		case MSG_JSON_ITEM_DELETE:
			adapter.reomveItem(deleteItemId);
			UIUtil.showToast(getActivity(), R.string.issue_delete_success);
			break;
		}
	}

	private void initListviewData(List<JSONObject> data){
		if (data == null) {
			data = new ArrayList<JSONObject>();
		}
		adapter = new HomeListAdapter(data, getActivity(),this);
		adapter.setStr(R.layout.item_home_list,Config.ITEM, Config.SINT_ISSUE,Config.STR_ISSUE);
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
