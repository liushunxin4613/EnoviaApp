package com.topgun.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.topgun.util.Config;
import com.topgun.util.ui.Interface.FragmentViewNI;

/**
 * 问题列表适配器
 * 
 * @author liushunxin
 *
 */
public class HomeListAdapter extends EnBaseAdapter<JSONObject> {

	private static final long serialVersionUID = -1340776155945622128L;

	private int [] item;
	private int [] sInt;
	private String[] str;
	private int resource;
	private FragmentViewNI fragmentI;

//	private boolean isViewNull;

	public static final String TAG = "IssueAdapter";

//	public HomeListAdapter(List<JSONObject> data, Context context) {
//		super(data, context);
//	}
	
	public HomeListAdapter(List<JSONObject> data, Context context, FragmentViewNI fragmentI) {
		super(data, context);
		this.fragmentI = fragmentI;
	}

	public void setStr(int resource,int [] item,int [] sInt,String [] str){
		this.resource = resource;
		this.item = item;
		this.sInt = sInt;
		this.str = str;
	}

	public int getResource() {
		return resource;
	}

	@Override
	public int getCount() {
		/*if (data.size() == 0) {
			return 1;
		}else {			
			return super.getCount();
		}*/
		
		if (data.size() == 0) {
			fragmentI.dataNullView();
		} else {
			fragmentI.dataNotNullView();
		}
		
		return super.getCount();
		
	}

	public int getSize(){
		return data.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;

		/*if (data.size() == 0) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.include_tv_dialog, parent,
					false);
			view.setEnabled(false);
			TextView tv = (TextView) view.findViewById(R.id.include_tv_dialog_tv);
			tv.setText(context.getString(R.string.app_data_null_data));
			isViewNull = true;
		}else {
			if ((view == null) || isViewNull) {*/

		//-->

		if(view == null){

			//<--

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(getResource(), parent,
					false);
			holder = new ViewHolder();

			holder.tv = new TextView[item.length];

			for (int i = 0; i < item.length; i++) {
				View v = view.findViewById(item[i]);
				TextView tvKey = (TextView) v.findViewById(Config.INCLUDE[0]);
				TextView tvValue = (TextView) v.findViewById(Config.INCLUDE[1]);
				tvKey.setText(sInt[i]);
				holder.tv[i] = tvValue;
			}

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		JSONObject items = data.get(position);
		for (int i = 0; i < item.length; i++) {
			if (holder.tv[i] != null) {					
				holder.tv[i].setText(items.getString(str[i]));
			}
		}
//	}
	return view;
}

class ViewHolder {
	TextView tv[];
}

@Override
public boolean isContains(JSONObject obj) {
	for (JSONObject name : data) {
		if (name.equals(obj))
			return true;
	}
	return false;
}

}
