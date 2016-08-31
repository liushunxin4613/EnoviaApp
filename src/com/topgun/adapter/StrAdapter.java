package com.topgun.adapter;

import java.util.List;

import com.topgun.enoviaapp.R;
import com.topgun.model.FileInfo;
import com.topgun.util.ui.Interface.FragmentViewNI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StrAdapter extends EnBaseAdapter<FileInfo> {

	private static final long serialVersionUID = 3412627998167679603L;
	
	private FragmentViewNI fragmentI;

	public StrAdapter(List<FileInfo> data, Context context) {
		super(data, context);
	}
	
	public StrAdapter(List<FileInfo> data, Context context,FragmentViewNI fragmentI) {
		super(data, context);
		this.fragmentI = fragmentI;
	}
	
	@Override
	public int getCount() {
		if (fragmentI != null) {
			if (data.size() == 0) {
				fragmentI.dataNullView();
			}else {
				fragmentI.dataNotNullView();
			}
		}
		return super.getCount();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_string, parent,
					false);
			holder = new ViewHolder();
			holder.tv = (TextView) view;
			
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		
		FileInfo info = data.get(position);
		if (info != null) {
			holder.tv.setText(info.getName());
		}
		return view;
	}

	class ViewHolder {
		TextView tv;
	}

	@Override
	public boolean isContains(FileInfo obj) {
		for (FileInfo name : data) {
			if (name.equals(obj))
				return true;
		}
		return false;
	}

}
