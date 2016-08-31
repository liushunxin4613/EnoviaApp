package com.topgun.adapter;

import java.util.ArrayList;

import com.topgun.enoviaapp.R;
import com.topgun.model.HomeGridItem;
import com.topgun.util.UIUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeGridViewAdapter extends EnBaseAdapter<HomeGridItem> {

	private static final long serialVersionUID = -5166927338059061709L;
	
	public int ROW_NUMBER = 1;

	public HomeGridViewAdapter(ArrayList<HomeGridItem> datalist,
			Context context) {
		super(datalist, context);
		this.ROW_NUMBER = (datalist.size() + 1) / 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_home_layout, parent, false);
			
			AbsListView.LayoutParams param = new AbsListView.LayoutParams(
					LayoutParams.MATCH_PARENT,
					UIUtil.dip2px(context, 140));
			
			view.setLayoutParams(param);
			
			holder = new ViewHolder();
			holder.image = (ImageView) view.findViewById(R.id.homepage_image);
			holder.text = (TextView) view.findViewById(R.id.homepage_text);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		HomeGridItem item = data.get(position);
		holder.image.setImageResource(item.getImage_resouce());
		holder.text.setText(item.getText());
		
		return view;
	}

	class ViewHolder {
		ImageView image;
		TextView text;
	}

	@Override
	public boolean isContains(HomeGridItem obj) {
		return false;
	}

}
