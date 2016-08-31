package com.topgun.fragment;


import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.topgun.activity.EditorIssueActivity;
import com.topgun.activity.ReviewIssueActivity;
import com.topgun.adapter.StrAdapter;
import com.topgun.customLib.ScListview;
import com.topgun.enoviaapp.R;
import com.topgun.entity.User;
import com.topgun.model.FileInfo;
import com.topgun.model.SoapMessage;
import com.topgun.util.Config;
import com.topgun.util.DataUtil;
import com.topgun.util.DateUtil;
import com.topgun.util.FileUtil;
import com.topgun.util.IOUtil;
import com.topgun.util.UIUtil;
import com.topgun.util.ui.BaseThreadFragment;
import com.topgun.util.ui.Interface.FragmentViewNI;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * 问题详细Fragment
 * 
 * @author liushunxin
 *
 */
public class IssueDetailFragment extends BaseThreadFragment implements OnClickListener,FragmentViewNI, OnItemClickListener{

	private TextView editorTv;
	private TextView handleTv;
	private ScListview fileListScListview;
	private TextView nullDataTv;

	private User user;
	
	private String issueId;
	
	private List<String> documentList;
	
	private List<String> fileNameList;
	
	private int indexDocument;
	
	private StrAdapter strAdapter;

	private TextView tv2 [];

	public static final int MSG_JSON_INIT = 1;
	public static final int MSG_UP_DOC = 2;
	public static final int MSG_UP_FILE = 3;
	
	private static IssueDetailFragment fragment;

	public static IssueDetailFragment getInstance() {
		if (fragment == null)
			fragment = new IssueDetailFragment();
		return fragment;
	}

	@Override
	protected Integer getLayoutResource() {
		return R.layout.fragment_issue_detail;
	}

	@Override
	protected void listenView(View rootView) {
		super.listenView(rootView);

		tv2 = new TextView[Config.FM_ITEM_ISSUE.length];
		for (int i = 0; i < Config.FM_ITEM_ISSUE.length; i++) {
			View v = rootView.findViewById(Config.FM_ITEM_ISSUE[i]);
			TextView tv1 = (TextView) v.findViewById(Config.INCLUDE[0]);
			tv2[i] = (TextView) v.findViewById(Config.INCLUDE[1]);
			tv1.setText(Config.FM_SINT_ISSUE[i]);
			editorTv = (TextView) rootView.findViewById(R.id.tvb_issue_detail_editor);
			editorTv.setOnClickListener(this);
			handleTv = (TextView) rootView.findViewById(R.id.tvb_issue_detail_handle);
			handleTv.setOnClickListener(this);
		}

		JSONObject obj = DataUtil.getIssue(getActivity());
		
		issueId = obj.getString("id");
		
		for (int i = 0; i < tv2.length; i++) {
			tv2[i].setText(obj.getString(Config.FM_STR_ISSUE[i]));
			//时间处理
			String s = obj.getString(Config.FM_STR_ISSUE[i]);
			for (int j = 0; j < Config.IF_ITEM_ISSUE_DATE.length; j++) {
				if (i == Config.IF_ITEM_ISSUE_DATE[j]){
					tv2[i].setText(DateUtil.ToString(s,DateUtil.sdf,DateUtil.sdfa));
				}
			}
		}

		//--->文件列表
		nullDataTv = (TextView) rootView.findViewById(R.id.fm_include_no_data);
		fileListScListview = (ScListview) rootView.findViewById(R.id.fm_include_listview);
		fileListScListview.setOnItemClickListener(this);
	}

	@Override
	protected void init() {
		user = DataUtil.getUserLoginInfo(getActivity());
		if (user == null) {
			user = DataUtil.getUserLoginInfo(getActivity());
		}
		
		setLoadingDialog(true);
		startVoid(MSG_UP_DOC, Config.getRelatedDocumentMethodName, new SoapMessage(user, issueId));
		
		UIUtil.showLogI(TAG, "dsad", null);
	}
	
	@Override
	public void toHandleMessage(Message msg) {
		
		super.toHandleMessage(msg);
		if(!isToHandleMessage(msg))return;
		
		String jsonStr = msg.obj.toString();
		
		switch (msg.what) {
		case MSG_UP_DOC:
			
			UIUtil.showLogI(TAG, "MSG_UP_DOC", MSG_UP_DOC);
			
			indexDocument = 0;
			fileNameList = null;
			documentList = getStringList(jsonStr);
			
			UIUtil.showLogI(TAG, "documentList.size()", documentList.size());
			
			if (documentList.size() > 0) {
				String gs = documentList.get(indexDocument);
				if (gs.trim().length() > 0) {
					startVoid(MSG_UP_FILE, Config.getObjectImageMethodName, new SoapMessage(user, documentList.get(indexDocument)));
				}
			}else {
				dataNullView();
			}
			break;
		case MSG_UP_FILE:
			indexDocument++;
			if (msg.obj == null) return;
			List<String> list = getStringList(jsonStr);
			if (fileNameList == null) {
				fileNameList = list;
			}else {
				for (int i = 0; i < list.size(); i++) {
					String st = list.get(i);
					if (st.trim().length() > 0) {						
						fileNameList.add(st);
					}
				}
			}
			if (indexDocument < documentList.size()) {
				String gs = documentList.get(indexDocument);
				if (gs.trim().length() > 0) {
					startVoid(MSG_UP_FILE, Config.getObjectImageMethodName, new SoapMessage(user, documentList.get(indexDocument)));
				}
			}else {
				List<FileInfo> data = new ArrayList<FileInfo>();
				for (String fileName : fileNameList) {
					data.add(new FileInfo(fileName, IOUtil.getDefaultFileStr()+"/"+fileName));
				}
				strAdapter = new StrAdapter(data, getActivity(),this);
				fileListScListview.setAdapter(strAdapter);
			}
			break;
		}
	}
	
	public static List<String> getStringList(String s){
		List<String> list = new ArrayList<String>();
		if (s != null){
			s = s.replace("[","");
			s = s.replace("]","");
			s = s.replace(" ","");
			String[] ss = s.split(",");
			for (String string : ss) {
				if (string.trim().length() > 0) {					
					list.add(string);
				}
			}
		}
		return list;
	}
	
	@Override
	public void dataNullView() {
		nullDataTv.setVisibility(View.VISIBLE);
	}

	@Override
	public void dataNotNullView() {
		nullDataTv.setVisibility(View.GONE);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvb_issue_detail_editor:
			startActivity(new Intent(getActivity(), EditorIssueActivity.class));
			getActivity().finish();
			break;
		case R.id.tvb_issue_detail_handle:
			startActivity(new Intent(getActivity(), ReviewIssueActivity.class));
			getActivity().finish();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i,long l) {
		FileUtil.openFile(strAdapter.getItem(i).getPath(), getActivity());		
	}

}
