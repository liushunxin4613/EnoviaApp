package com.topgun.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.ksoap2.serialization.SoapPrimitive;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.topgun.activity.IssueActivity;
import com.topgun.activity.IssueDetailActivity;
import com.topgun.adapter.StrAdapter;
import com.topgun.customLib.ScListview;
import com.topgun.dialog.ChooseListDialog;
import com.topgun.dialog.DatePickerDialog;
import com.topgun.enoviaapp.R;
import com.topgun.entity.User;
import com.topgun.model.FileInfo;
import com.topgun.model.SoapMessage;
import com.topgun.util.Config;
import com.topgun.util.DataUtil;
import com.topgun.util.DateUtil;
import com.topgun.util.FileUtil;
import com.topgun.util.GetPathFromUri4kitkat;
import com.topgun.util.IOUtil;
import com.topgun.util.MEditTextWatcher;
import com.topgun.util.PhotoFile;
import com.topgun.util.UIUtil;
import com.topgun.util.ViewUtil;
import com.topgun.util.ui.BaseThreadFragment;
import com.topgun.util.ui.Interface.FragmentViewNI;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class EditorIssueFragment extends BaseThreadFragment implements OnClickListener,android.content.DialogInterface.OnClickListener
,OnItemClickListener,OnItemLongClickListener,FragmentViewNI{

	public static final int INTENT_COM_PHOTO = 1;
	public static final int INTENT_CUT_PHOTO = 2;
	
	public static final int MSG_JSON_INIT = 3;

	private EditText et[];
	private Spinner sp[];
	private TextView nextTv[];
	private TextView tv[];
	private Button submitBt;
	private ScListview scListview;
	private View isView;
	private StrAdapter strAdapter;
	private ChooseListDialog chooseListDialog;

	private JSONObject obj;

	private int dateKey;
	private Calendar calendar;
	private DatePickerDialog datePickerDialog;
	private Uri tempUri;

	private User user;

	public static final int MSG_UPDATE = 1;
	public static final int MSG_UPFILE = 2;
	
	public static final int DIALOG_DELETE_ITEM = 1;

	private String dataId;

	private int SUBMIT_ERROR[] = {
			R.string.issue_edit_submit_error0
			,R.string.issue_edit_submit_error1
			,R.string.issue_edit_submit_error2
	};

	private SoapPrimitive sb = null;
	
	private Uri uri;

	private static List<FileInfo> fileInfoList;

	private PhotoFile photoFile;

	private static EditorIssueFragment fragment;
	private int fileIndex = 0;

	private void cleanUriData(){
		fileInfoList.clear();
	}

	public static EditorIssueFragment getInstance() {
		if (fragment == null)
			fragment = new EditorIssueFragment();
		return fragment;
	}

	@Override
	protected Integer getLayoutResource() {
		return R.layout.fragment_editor_issue;
	}

	@Override
	protected void listenView(View rootView) {
		et = ViewUtil.listenViewIncludeEt(rootView,Config.FM_ID_ET_EDITOR_ISSUE
				,Config.FM_STR_ET_EDITOR_ISSUE);
		
		for (int i = 0; i < et.length; i++) {
			et[i].addTextChangedListener(new MEditTextWatcher(et[i]));
		}

		sp = ViewUtil.listenViewIncludeSp(rootView, Config.FM_ID_SP_EDITOR_ISSUE
				,Config.FM_STR_SP_EDITOR_ISSUE);

		nextTv = ViewUtil.listenViewIncludeNext(rootView,this,Config.FM_ID_NEXT_EDITOR_ISSUE
				,Config.FM_STR_NEXT_EDITOR_ISSUE);

		tv = ViewUtil.listenViewIncludeTv(rootView, Config.FM_ID_TV_EDITOR_ISSUE
				,Config.FM_STR_TV_EDITOR_ISSUE);

		submitBt = (Button) rootView.findViewById(R.id.bt_fm_edit_issue);
		submitBt.setOnClickListener(this);

		//-->
		View v = rootView.findViewById(R.id.fg_ic_next_lv);
		ViewUtil.listenViewIncludeNext(v, this, new int[]{R.id.include_next_listview_next},new int[]{R.string.file_select});
		scListview = (ScListview) v.findViewById(R.id.include_next_listview_listview);
		scListview.setOnItemClickListener(this);
		scListview.setOnItemLongClickListener(this);
		
		isView = v.findViewById(R.id.include_next_listview_view);

		//隐藏分割线
		dataNullView();
		
		fileInfoList = new ArrayList<FileInfo>();		
		strAdapter = new StrAdapter(fileInfoList,getActivity(),this);
		scListview.setAdapter(strAdapter);
	}

	public void dataNullView(){
		isView.setVisibility(View.GONE);
	}
	
	public void dataNotNullView(){
		isView.setVisibility(View.VISIBLE);
	}
	
	@Override
	protected void init() {
		user = DataUtil.getUserLoginInfo(getActivity());
		if (null == user) return;
		
		cleanUriData();
		
		obj = DataUtil.getIssue(getActivity());

		dataId = obj.getString("id");

		String sgk[][] = {getResources().getStringArray(R.array.issue_key_hs_priority)};
		String sg[][] = {getResources().getStringArray(R.array.issue_value_hs_priority)};
		for (int i = 0; i < sp.length; i++) {
			sp[i].setAdapter(initArrayAdapter(sgk[i]));
			for (int j = 0; j < sg[i].length; j++) {
				if (obj.getString(Config.FM_KEY_SP_EDITOR_ISSUE[i]).equals(sg[i][j])) {
					sp[i].setSelection(j);
				}
			}
		}

		for (int i = 0; i < nextTv.length; i++) {
			String dateStr = obj.getString(Config.FM_KEY_NEXT_EDITOR_ISSUE[i]);
			if (dateStr != null) {
				dateStr = DateUtil.ToString(dateStr, DateUtil.sdf, DateUtil.sdfa);
			}
			nextTv[i].setText(dateStr);
		}

		for (int i = 0; i < tv.length; i++) {
			String St = obj.getString(Config.FM_KEY_TV_EDITOR_ISSUE[i]);
			if (i > 2 && i < 8) {
				if ((St != null) && (i != 4)) {
					St = DateUtil.ToString(St, DateUtil.sdf, DateUtil.sdfa);
				}
			}
			tv[i].setText(St);
		}

		for (int i = 0; i < et.length; i++) {
			et[i].setText(obj.getString(Config.FM_KEY_ET_EDITOR_ISSUE[i]));
		}

	}

	@Override
	public void onClick(View v) {

		dateKey = getViewItemId(v);
		if (dateKey < nextTv.length && dateKey > -1) {			
			setDateListener(dateKey);
		}

		switch (v.getId()) {
		case R.id.bt_fm_edit_issue:
			submitData();
			break;
		case R.id.include_next_listview_next:
			setFileData();
			break;
		}
	}

	private void setFileData() {
		if (chooseListDialog == null) {
			chooseListDialog = new ChooseListDialog(getActivity());
			chooseListDialog.addButton(getActivity().getResources().getString(R.string.file_select_take_photo));
			chooseListDialog.addButton(getActivity().getResources().getString(R.string.file_select_other));
			chooseListDialog.setOnclickListener((android.content.DialogInterface.OnClickListener) this);
		}
		FragmentTransaction ft = getChildFragmentManager()
				.beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		chooseListDialog.show(ft, TAG);
	}

	//显示对话框
	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (dialog.equals(chooseListDialog) && IOUtil.isSdCardExist()) {
			switch (which) {
			case 0:
				photoFile = new PhotoFile(getActivity());
				tempUri = photoFile.getUri();
				startActivityForResult(photoFile.photoTake(tempUri), INTENT_CUT_PHOTO);
				break;
			case 1:
				if (IOUtil.OpenSystemFile(getActivity(),getString(R.string.file_select_dialog)) != null) {
					UIUtil.showToast(getActivity(), R.string.file_select_dialog_error);
				}
				break;
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case IOUtil.FILE_SELECT_CODE:
				if (data == null) {
					return;
				}
				Uri uri = data.getData();
				addUri(uri);
				break;
			case INTENT_COM_PHOTO:
				startActivityForResult(photoFile.cropImage(tempUri), INTENT_CUT_PHOTO);
				break;
			case INTENT_CUT_PHOTO:
//				if (photoFile.getCropUri() == null) {
//					Log.i(TAG, "photoFile.getCropUri() == null");
//					return;
//				}
				if (tempUri != null) {
					addUri(tempUri);
					photoFile.clean();
				}
				break;
			}
		}
	}
	
	protected void addUri(Uri uris){
		this.uri = uris;
		if (uri == null) {
			return;
		}
		setLoadingDialog(true);
		String path = GetPathFromUri4kitkat.getPath(getActivity(), uri);
//		String s = Uri.decode(uri.toString());
//		String path = FileUtil.getUriToPath(s, getActivity());
		String name = FileUtil.getFileNameNo(path);
		String suffix = FileUtil.getSuffix(name);
		long length = FileUtil.getFilelength(path);
		String fileStr = IOUtil.fileToString(path);
		if ("errorMax".equals(fileStr)) {
			UIUtil.showToast(getActivity(), R.string.file_select_length_max);
		}else {				
			sb = FileUtil.getSoapPrimitive(fileStr);
			FileInfo info = new FileInfo(name, path, uri, suffix, sb, length);
			strAdapter.insertItem(info);
		}
		dismiss();
	}
	
	@Override
	public void toHandleMessage(Message msg) {

		super.toHandleMessage(msg);
		
		if(isToHandleMessage(msg)){
			switch (msg.what) {
			case MSG_UPDATE:
				String str = msg.obj.toString();
				user.setIssueId(str);
				fileIndex = 0;
				upFile(null);
				break;
			case MSG_UPFILE:
				String str1 = msg.obj.toString();
				upFile(str1);
				break;
			}
		}
		
	}

	private void upFile(String docId) {
		
		UIUtil.showLogI(TAG, "fileIndex", fileIndex);
		
		if (fileIndex >= strAdapter.getCount()) {
			getThreadUtil().stop();
			
			UIUtil.showLogI(TAG, "isSuccess", null);
			
			isSuccess();
		}else {
			FileInfo info = strAdapter.getItem(fileIndex);
			
			UIUtil.showLogI(TAG, "info.getLength()", info.getLength());
			
			startVoid(MSG_UPFILE, Config.connectObjAndFileMethodName
					,new SoapMessage(user, user.getIssueId(),docId,info.getSuffix(),info.getSb()));
			fileIndex++;
		}
	}
	
	protected void isSuccess(){
		UIUtil.showToast(getActivity(), R.string.issue_edit_submit_success);
		
		startActivity(new Intent(getActivity(), IssueActivity.class));
		if (IssueDetailActivity.activity != null) {
			IssueDetailActivity.activity.finish();
		}
		getActivity().finish();
		
	}
	
	private void submitData() {
		if (!checkData()) return;

		JSONObject ebj = new JSONObject();
		ViewUtil.setViewETJsonObjectData(obj,ebj,et);
		String ss[][] = {getResources().getStringArray(R.array.issue_key_hs_priority)
				,getResources().getStringArray(R.array.issue_value_hs_priority)};
		ViewUtil.setViewSpJsonObjectData(obj,ebj,sp,ss[0],ss[1]);
		ViewUtil.setViewNextJsonObjectData(obj,ebj,nextTv);

		UIUtil.showLogI(TAG, "strAdapter.getCount()", strAdapter.getCount());
		
		if ((ebj.size() == 0) && (strAdapter.getCount() == 0)) {
			UIUtil.showToast(getActivity(), "数据未改变,请重新编辑!");
			return;
		}

		String jsonStr = JSON.toJSONString(ebj);

		setLoadingDialog(true);
		startVoid(MSG_UPDATE, Config.updateIssueMethodName, new SoapMessage(user, dataId, jsonStr));

	}

	private boolean checkData() {
		for (int i = 0; i < 3; i++) {
			if (et[i].getText() == null) {
				UIUtil.showToast(getActivity(), SUBMIT_ERROR[i]);
				return false;
			}else {
				if (et[i].getText().toString().trim().length() == 0) {
					UIUtil.showToast(getActivity(), SUBMIT_ERROR[i]);
					return false;
				}
			}
		}
		return true;
	}

	public void setDateListener(int i){
		calendar = Calendar.getInstance();
		datePickerDialog = new DatePickerDialog(onDateSetListener,
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		
		switch (i) {
		case 0:
			datePickerDialog.setTitle(getString(R.string.publish_issue_date_title));
			break;
		case 1:
			datePickerDialog.setTitle(getString(R.string.publish_issue_requirements_date_title));
			break;
		}
		
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		datePickerDialog.show(ft, TAG);
	}

	private OnDateSetListener onDateSetListener = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker datepicker, int year, int month,
				int day) {
			nextTv[dateKey].setText(dateToString(year,month,day));
		}
	};

	private String dateToString(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(year,month, day, 0, 0, 0);
		String time = DateUtil.sdfa.format(c.getTime());
		calendar = c;
		return time;
	}

	private int getViewItemId(View v){
		for (int i = 0; i < nextTv.length; i++) {
			if (v.getId() == Config.FM_ID_NEXT_EDITOR_ISSUE[i]) {
				return i;
			}
		}
		return -1;
	}

	private ArrayAdapter<String> initArrayAdapter(String ss[]){
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,ss);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return adapter;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i,long l) {
		showDialog(getConfirmDialog(1),DIALOG_DELETE_ITEM,i);
		return true;
	}
	
	@Override
	protected void toDialogPositive(int index,Object obj) {
		switch (index) {
		case DIALOG_DELETE_ITEM:
			strAdapter.reomveItem((Integer) obj);
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i,long l) {
		FileUtil.openFile(strAdapter.getItem(i).getPath(), getActivity());
	}


}
