package com.topgun.fragment;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.ksoap2.serialization.SoapPrimitive;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topgun.adapter.StrAdapter;
import com.topgun.customLib.ScListview;
import com.topgun.dialog.ChooseListDialog;
import com.topgun.dialog.DatePickerDialog;
import com.topgun.enoviaapp.MainActivity;
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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class PublishIssueFragment extends BaseThreadFragment implements OnClickListener,OnItemSelectedListener,android.content.DialogInterface.OnClickListener
,OnItemClickListener,OnItemLongClickListener,FragmentViewNI{

	private static PublishIssueFragment fragment;

	private EditText et[];

	private TextView tTv[];

	private Spinner ssp[];

	private Button submitBt;

	private User user;

	public static final int MSG_CREATE = 1;
	public static final int MSG_SWHERE = 2;
	public static final int MSG_UPDATE = 3;
	public static final int MSG_UPFILE = 4;

	public static final int INTENT_COM_PHOTO = 1;
	public static final int INTENT_CUT_PHOTO = 2;

	public static final int DIALOG_DELETE_ITEM = 1;
	public static final int DIALOG_AGAIN_ITEM = 2;

	private DatePickerDialog datePickerDialog;

	private String sSa[][];

	private Calendar calendar;

	private ArrayList<String> projectData;

	private ArrayAdapter<String> adapter[];

	private Integer dateKey;

	private ScListview scListview;
	private View isView;
	private StrAdapter strAdapter;

	private int fileIndex = 0;

	private SoapPrimitive sb = null;

	private Uri uri;

	private static List<FileInfo> fileInfoList;

	private PhotoFile photoFile;

	private ChooseListDialog chooseListDialog;
	private Uri tempUri;

	private void cleanUriData(){
		fileInfoList.clear();
	}

	public static final int I_DATE_KEY_ESTART = 1;
	public static final int I_DATE_KEY_EEND = 2;

	public static PublishIssueFragment getInstance() {
		if (fragment == null)
			fragment = new PublishIssueFragment();
		return fragment;
	}

	@Override
	protected Integer getLayoutResource() {
		return R.layout.fragment_publish_issue;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void listenView(View rootView) {
		super.listenView(rootView);

		submitBt = (Button) rootView.findViewById(R.id.Bt_fm_publish_issue);
		submitBt.setOnClickListener(this);

		et = new EditText[Config.FM_ITEM_PUBLISH_ISSUE.length];
		for (int i = 0; i < Config.FM_ITEM_PUBLISH_ISSUE.length; i++) {
			View v = rootView.findViewById(Config.FM_ITEM_PUBLISH_ISSUE[i]);
			TextView tv = (TextView) v.findViewById(Config.INCLUDE_PUBLISH_EDIT[0]);
			et[i] = (EditText) v.findViewById(Config.INCLUDE_PUBLISH_EDIT[1]);
			et[i].addTextChangedListener(new MEditTextWatcher(et[i]));
			tv.setText(Config.FM_SINT_PUBLISH_ISSUE[i]);
		}

		tTv = new TextView[Config.FM_ITEM_TV_PUBLISH_ISSUE.length];
		for (int i = 0; i < Config.FM_ITEM_TV_PUBLISH_ISSUE.length; i++) {
			View v = rootView.findViewById(Config.FM_ITEM_TV_PUBLISH_ISSUE[i]);
			TextView tv = (TextView) v.findViewById(Config.INCLUDE_ID_PUBLISH_TEXTVIEW[0]);
			tTv[i] = (TextView) v.findViewById(Config.INCLUDE_ID_PUBLISH_TEXTVIEW[1]);
			tv.setText(Config.FM_SINT_TV_PUBLISH_ISSUE[i]);
			v.setOnClickListener(this);
		}

		ssp = new Spinner[Config.FM_ITEM_SP_PUBLISH_ISSUE.length];
		for (int i = 0; i < Config.FM_ITEM_SP_PUBLISH_ISSUE.length; i++) {
			View v = rootView.findViewById(Config.FM_ITEM_SP_PUBLISH_ISSUE[i]);
			TextView tv = (TextView) v.findViewById(Config.INCLUDE_SP_PUBLISH_TV[0]);
			ssp[i] = (Spinner) v.findViewById(Config.INCLUDE_SP_PUBLISH_TV[1]);
			ssp[i].setOnItemSelectedListener(this);
			tv.setText(Config.FM_SINT_SP_PUBLISH_ISSUE[i]);
		}

		sSa= new String[][]{getResources().getStringArray(R.array.issue_key_hs_priority)};

		adapter = new ArrayAdapter [sSa.length];
		for (int i = 0; i < ssp.length; i++) {
			if (i != 0) {
				int key = i-1;
				adapter[key] = initArrayAdapter(sSa[key]);
				ssp[i].setAdapter(adapter[key]);
				ssp[i].setPrompt(sSa[key][0]);
			}
		}


		View v = rootView.findViewById(R.id.fm_include_publish_next_lv);
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

	public ArrayAdapter<String> initArrayAdapter(String ss[]){
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,ss);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return adapter;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

	@Override
	protected void init() {
		user = DataUtil.getUserLoginInfo(getActivity());
		if (null == user) return;

		cleanUriData();

		setLoadingDialog(true);
		startVoid(MSG_SWHERE, Config.getProjectListMethodName, new SoapMessage(user, Config.SWHERE_TYPE[1], 0, 100));

	}

	@Override
	public void toHandleMessage(Message msg) {

		super.toHandleMessage(msg);
		
		switch (msg.what) {
		case MSG_UPFILE:
			String str1 = msg.obj.toString();
			upFile(str1);
			break;
		}
		
		if(isToHandleMessage(msg)){
			switch (msg.what) {
			case MSG_CREATE:
				String str = msg.obj.toString();
				user.setIssueId(str);
				fileIndex = 0;
				upFile(null);
				break;
			case MSG_SWHERE:
				String jsonStr = msg.obj.toString();
				@SuppressWarnings("unchecked")
				List<JSONObject> data2 = (List<JSONObject>) JSONArray.parse(jsonStr);
				projectData = new ArrayList<String>();
				for (JSONObject jsonObject : data2) {
					String nameStr =jsonObject.getString(Config.PROJECT_NAME);
					if (nameStr == null) {
					} else {
						projectData.add(nameStr);
					}
				}
				String[] sp = new String[projectData.size()+1];
				for (int i = 0; i < sp.length; i++) {
					if (i == 0) {
						sp[i] = "";
					} else {
						sp[i] = projectData.get(i-1);
					}
				}
				ArrayAdapter<String> projectAdapter = initArrayAdapter(sp);
				ssp[0].setAdapter(projectAdapter);
				break;
			}
		}
	}

	private OnDateSetListener onDateSetListener = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker datepicker, int year, int month,
				int day) {
			if (dateKey != null) {
				switch (dateKey) {
				case I_DATE_KEY_ESTART:
					tTv[0].setText(dateToString(year,month,day));
					break;
				case I_DATE_KEY_EEND:
					tTv[1].setText(dateToString(year,month,day));
					break;
				}
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Bt_fm_publish_issue:
			submit();
			break;
		case R.id.fm_include_publish_tv:
			setDateListener(I_DATE_KEY_EEND);
			break;
		case R.id.fm_include_publish_tv_start_date:
			setDateListener(I_DATE_KEY_ESTART);
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

	private void upFile(String docId) {

		if (fileIndex >= strAdapter.getCount()) {
			getThreadUtil().stop();

			UIUtil.showToast(getActivity(), R.string.publish_issue_submit_success);

			showDialog(getConfirmDialog(DIALOG_AGAIN),DIALOG_AGAIN_ITEM, null);
		}else {
			FileInfo info = strAdapter.getItem(fileIndex);
			startVoid(MSG_UPFILE, Config.connectObjAndFileMethodName
					,new SoapMessage(user, user.getIssueId(),docId,info.getSuffix(),info.getSb()));
			fileIndex++;
		}
	}

	protected void isSuccess(){
		UIUtil.showToast(getActivity(), R.string.issue_edit_submit_success);
		startActivity(new Intent(getActivity(), MainActivity.class));
		getActivity().finish();
	}

	private void submit(){
		String jsonStr = toJsonString();
		if(!checkDate(jsonStr)) return;

		startVoid(MSG_CREATE, Config.createAndSetObjectInfoMethodName, new SoapMessage(user, jsonStr));

	}

	public void setDateListener(int dateKey){
		this.dateKey = dateKey;
		calendar = Calendar.getInstance();
		datePickerDialog = new DatePickerDialog(onDateSetListener,
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		switch (dateKey) {
		case I_DATE_KEY_ESTART:
			datePickerDialog.setTitle(getString(R.string.publish_issue_start_date_title));
			break;
		case I_DATE_KEY_EEND:
			datePickerDialog.setTitle(getString(R.string.publish_issue_requirements_date_title));
			break;
		}
		
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		datePickerDialog.show(ft, TAG);
	}

	private boolean checkDate(String jsonStr) {
		JSONObject obj = JSONObject.parseObject(jsonStr);
		for (int i = 0; i < Config.FM_CHECK_DATE_PUBLISH_ISSUE.length; i++) {
			Log.i(TAG,Config.FM_CHECK_DATE_PUBLISH_ISSUE[i] + " : " + obj.getString(Config.FM_CHECK_DATE_PUBLISH_ISSUE[i]));
			
			if (obj.getString(Config.FM_CHECK_DATE_PUBLISH_ISSUE[i]) == null) {
				UIUtil.showToast(getActivity(), Config.FM_CHECK_DATE_PUBLISH_ISSUE_ERROR[i]);
				return false;
			}else {
				if (obj.getString(Config.FM_CHECK_DATE_PUBLISH_ISSUE[i]).trim().equals("")) {
					UIUtil.showToast(getActivity(), Config.FM_CHECK_DATE_PUBLISH_ISSUE_ERROR[i]);
					return false;
				}
			}
		}
		return true;
	}

	@SuppressWarnings("unused")
	private void clearShowData(){
		for (int i = 0; i < et.length; i++) {
			et[i].setText(null);
		}
		for (int i = 0; i < tTv.length; i++) {
			tTv[i].setText(null);
		}
		for (int i = 0; i < ssp.length; i++) {
			ssp[i].setSelection(0);
		}
	}

	private String toJsonString() {

		JSONObject obj = new JSONObject();
		for (int i = 0; i < et.length; i++) {
			obj.put(Config.FM_STR_PUBLISH_ISSUE[i],et[i].getText().toString());
		}
		for (int i = 0; i < tTv.length; i++) {
			String dateStr = tTv[i].getText().toString();
			String dataSt = DateUtil.ToString(dateStr,DateUtil.sdfa, DateUtil.sdf);
			obj.put(Config.FM_STR_TV_PUBLISH_ISSUE[i],dataSt);
		}
		for (int i = 0; i < ssp.length; i++) {
			if (i == 1) {
				String sg[] = getResources().getStringArray(R.array.issue_value_hs_priority);
				for (int j = 0; j < sSa[i-1].length; j++) {
					if (ssp[i].getSelectedItem().toString().equals(sSa[i-1][j])) {
						obj.put(Config.FM_STR_SP_PUBLISH_ISSUE[i],sg[j]);
						obj.put(Config.ISSUE_PRIORITY,sSa[i-1][j]);
					}
					if (j == 0) {
						obj.put(Config.FM_STR_SP_PUBLISH_ISSUE[i],sg[2]);
						obj.put(Config.ISSUE_PRIORITY,sSa[i-1][2]);
					}
				}
			}else {				
				obj.put(Config.FM_STR_SP_PUBLISH_ISSUE[i],ssp[i].getSelectedItem().toString());
			}
		}

		//添加开始时间
		Calendar cs = Calendar.getInstance();
		obj.put(Config.ISSUE_START_DATE,DateUtil.sdf.format(cs.getTime()));

		String jsonStr = JSON.toJSONString(obj);

		return jsonStr;
	}

	private String dateToString(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(year,month, day, 0, 0, 0);
		String time = DateUtil.sdfa.format(c.getTime());
		calendar = c;
		return time;
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
		case DIALOG_AGAIN_ITEM:
			strAdapter.clear();
			break;
		}
	}

	@Override
	protected void toDialogNegative(int index, Object obj) {
		switch (index) {
		case DIALOG_AGAIN_ITEM:
			isSuccess();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i,long l) {
		FileUtil.openFile(strAdapter.getItem(i).getPath(), getActivity());
	}

	public void dataNullView(){
		isView.setVisibility(View.GONE);
	}

	public void dataNotNullView(){
		isView.setVisibility(View.VISIBLE);
	}

}