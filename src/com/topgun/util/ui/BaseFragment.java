package com.topgun.util.ui;

import java.lang.reflect.Field;

import com.topgun.dialog.ConfirmDialog;
import com.topgun.enoviaapp.R;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment{

	public static String TAG;
	protected static final int DIALOG_DELETE = 1;
	protected static final int DIALOG_AGAIN = 2;
	protected static int dialogIndex ;
	protected static Object obj ;
	protected static BaseFragment fragment;
	private LayoutInflater inflater;
	private ViewGroup container;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TAG = getClass().getName();
	}

	protected BaseFragment getStaticFragment(){
		fragment = this;
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		this.inflater = inflater;
		this.container = container;
		
		if (getLayoutResource() == null) {
			init();
			return super.onCreateView(inflater, container, savedInstanceState);
		} else {
			View rootView = inflater.inflate(getLayoutResource(), container, false);
			listenView(rootView);
			init();
			return rootView;
		}

	}

	protected View getRootView(){
		return inflater.inflate(getLayoutResource(), container, false);
	}
	
	protected ConfirmDialog getConfirmDialog(int index){
		switch (index) {
		case DIALOG_DELETE:
			return new ConfirmDialog(
					ConfirmDialog.CONFIRM_STYLE_CENTER,
					getString(R.string.dialog_exit_title),
					getString(R.string.dialog_delete_item_exit_msg),
					getString(R.string.dialog_delete_item_exit_yes),
					getString(R.string.dialog_cancel));
		case DIALOG_AGAIN:
			return new ConfirmDialog(
					ConfirmDialog.CONFIRM_STYLE_CENTER,
					getString(R.string.dialog_exit_title),
					getString(R.string.dialog_again_exit_msg),
					getString(R.string.dialog_again_exit_yes),
					getString(R.string.dialog_cancel));
		default:
			return null;
		}
	}

	protected void showDialog(ConfirmDialog dialog,int index,Object object){
		dialogIndex = index;
		obj = object;
		if (dialog == null) {
			return;
		}
		dialog.setOnclickListener(new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					toDialogPositive(dialogIndex,obj);
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					toDialogNegative(dialogIndex,obj);
					break;
				}
			}
		});
		dialog.show(getActivity().getSupportFragmentManager().beginTransaction(), TAG);
	}

	protected void toDialogNegative(int index,Object obj) {

	}

	protected void toDialogPositive(int index,Object obj) {

	}

	protected Integer getLayoutResource(){
		return null;
	}

	protected void listenView(View rootView) {
	}

	protected void init() {
	}

	@Override
	public void onDetach() {
		super.onDetach();
		try {  
			Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");  
			childFragmentManager.setAccessible(true);  
			childFragmentManager.set(this, null);  
		} catch (NoSuchFieldException e) {  
			throw new RuntimeException(e);  
		} catch (IllegalAccessException e) {  
			throw new RuntimeException(e);  
		}  
	}
}
