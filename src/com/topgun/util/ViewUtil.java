package com.topgun.util;

import com.alibaba.fastjson.JSONObject;

import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ViewUtil {

	public static final String TAG = "ViewUtil";

	public static EditText[] listenViewIncludeEt(View rootView,int id[],int str[]){
		EditText et[] = new EditText[id.length];
		int include[] = Config.INCLUDE_PUBLISH_EDIT;
		for (int i = 0; i < id.length; i++) {
			View v = rootView.findViewById(id[i]);
			TextView tv = (TextView) v.findViewById(include[0]);
			tv.setText(str[i]);
			et[i] = (EditText) v.findViewById(include[1]);
		}
		return et;
	}

	public static void setViewETJsonObjectData(JSONObject obj,JSONObject ebj,EditText et[]){
		for (int i = 0; i < et.length; i++) {
			String d = Config.FM_KEY_ET_EDITOR_ISSUE[i];
			String s = obj.getString(d);
			Editable e = et[i].getText();
			if ((s != null ) && (e != null)) {
				if (!s.equals(e.toString())) {				
					ebj.put(d,e.toString());
				}
			}else {
				ebj.put(d,e.toString());
			}
		}
	}

	public static Spinner[] listenViewIncludeSp(View rootView,int id[],int str[]){
		Spinner sp[] = new Spinner[id.length];
		int include[] = Config.INCLUDE_SP_PUBLISH_TV;
		for (int i = 0; i < id.length; i++) {
			View v = rootView.findViewById(id[i]);
			TextView tv = (TextView) v.findViewById(include[0]);
			tv.setText(str[i]);
			sp[i] = (Spinner) v.findViewById(include[1]);
		}
		return sp;
	}

	public static void setViewSpJsonObjectData(JSONObject obj,JSONObject ebj,Spinner sp[],String []sKey,String []sValue){
		for (int i = 0; i < sp.length; i++) {
			String d = Config.FM_KEY_SP_EDITOR_ISSUE[i];
			String s = obj.getString(d);
			Object o = sp[i].getSelectedItem();

			if (o != null) {
				String ds = null;
				for (int j = 0; j < sKey.length; j++) {
					if (sKey[j].equals(o.toString())) {
						ds = sValue[j];
					}
				}
				if (s != null) {
					if (!s.equals(ds)) {
						ebj.put(d, ds);
					}
				} else {
					ebj.put(d, ds);
				}
			}

		}
	}

	public static void setViewSpJsonObjectData(JSONObject obj,JSONObject ebj,Spinner sp[]){
		for (int i = 0; i < sp.length; i++) {
			String d = Config.FM_KEY_SP_EDITOR_ISSUE[i];
			String s = obj.getString(d);
			Object e = sp[i].getSelectedItem();
			if ((s != null ) && (e != null)) {
				if (!s.equals(e.toString())) {				
					ebj.put(d,e.toString());
				}
			}else {
				ebj.put(d,e.toString());
			}
		}
	}

	public static void setViewSpInitData(Context context,Spinner sp[],String ss[][]){
		for (int i = 0; i < sp.length; i++) {
			sp[i].setAdapter(StyleUtil.initSpArrayAdapter(context,ss[i]));
			sp[i].setSelection(0);
		}
	}

	public static TextView[] listenViewIncludeNext(View rootView,OnClickListener listener,int id[],int str[]){
		TextView tvt[] = new TextView[id.length];
		int include[] = Config.INCLUDE_ID_PUBLISH_TEXTVIEW;
		for (int i = 0; i < id.length; i++) {
			View v = rootView.findViewById(id[i]);
			TextView tv = (TextView) v.findViewById(include[0]);
			tv.setText(str[i]);
			tvt[i] = (TextView) v.findViewById(include[1]);
			v.setOnClickListener(listener);
		}
		return tvt;
	}

	public static void setViewNextJsonObjectData(JSONObject obj,JSONObject ebj,TextView tv[]){
		for (int i = 0; i < tv.length; i++) {
			String d = Config.FM_KEY_NEXT_EDITOR_ISSUE[i];
			String s = obj.getString(d);
			CharSequence e = tv[i].getText();

			if (e != null) {
				if (e.toString().trim().length() != 0) {
					if (s != null) {
						String dateStr = DateUtil.ToString(e.toString(),DateUtil.sdfa,DateUtil.sdf);
						if (s.trim().length() != 0) {
							s = DateUtil.ToString(s,DateUtil.sdf,DateUtil.sdf);
							if (!s.equals(dateStr)) {				
								ebj.put(d,dateStr);
							}
						}else {
							ebj.put(d,dateStr);
						}
					} else {
						String dateStr = DateUtil.ToString(e.toString(),DateUtil.sdfa,DateUtil.sdf);
						ebj.put(d,dateStr);
					}
				}
			}
		}
	}


	public static void setViewTvJsonObjectData(JSONObject obj,JSONObject ebj,TextView tv[]){
		for (int i = 0; i < tv.length; i++) {
			String d = Config.FM_KEY_TV_EDITOR_ISSUE[i];
			String s = obj.getString(d);
			CharSequence e = tv[i].getText();
			if ((s != null ) && (e != null)) {
				if (!s.equals(e.toString())) {				
					ebj.put(d,e.toString());
				}
			}else {
				ebj.put(d,e.toString());
			}
		}
	}

	public static TextView[] listenViewIncludeTv(View rootView,int id[],int str[]){
		TextView tvt[] = new TextView[id.length];
		int include[] = Config.INCLUDE;
		for (int i = 0; i < id.length; i++) {
			View v = rootView.findViewById(id[i]);
			TextView tv = (TextView) v.findViewById(include[0]);
			tv.setText(str[i]);
			tvt[i] = (TextView) v.findViewById(include[1]);
		}
		return tvt;
	}

	public static void setViewTvData(TextView tv[],JSONObject obj,int id[],String key[]){
		for (int i = 0; i < id.length; i++) {
			tv[i].setText(obj.getString(key[i]));
		}
	}

}
