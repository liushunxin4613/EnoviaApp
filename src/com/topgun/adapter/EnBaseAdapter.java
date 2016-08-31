package com.topgun.adapter;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.widget.BaseAdapter;

/**
 * 自定义BaseAdapter的基类，重写部分共有的方法
 * 
 * @author liusx
 *
 * @param <T>
 *            item的对应模型类
 */
public abstract class EnBaseAdapter<T> extends BaseAdapter implements Serializable{
	
	private static final long serialVersionUID = -4405955265757416474L;
	
	protected List<T> data;
	protected Context context;
	protected Comparator<T> comparator;
	
	public static String TAG;

	public EnBaseAdapter(List<T> data, Context context) {
		TAG = getClass().getName();
		this.data = data;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public T getItem(int position) {
		if (data.size() != 0) {
			return data.get(position);
		}else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 向适配器尾部添加元素
	 * 
	 * @param items
	 *            元素集合
	 * @return 排除重复的元素，返回添加成功的个数
	 */
	public int addItems(List<T> items) {
		int addCount = 0;
		for (T t : items) {
			if (!isContains(t)) {
				data.add(t);
				addCount++;
			}
		}
		if (addCount > 0)
			notifyDataSetChanged();
		return addCount;
	}

	/**
	 * 向适配器头部添加元素
	 * 
	 * @param items
	 *            元素集合
	 * @return 排除重复的元素，返回添加成功的个数
	 */
	public int addItemsToHead(List<T> items) {
		int addCount = 0;
		for (T t : items) {
			if (!isContains(t)) {
				data.add(addCount, t);
				addCount++;
			}
		}
		if (addCount > 0)
			notifyDataSetChanged();
		return addCount;
	}

	/**
	 * 插入元素，若comparator不为空则重新排序
	 * 
	 * @param obj
	 */
	public void insertItem(T obj) {
		data.add(obj);
		if (comparator != null)
			Collections.sort(data, comparator);
		notifyDataSetChanged();
	}

	/**
	 * 若comparator不为空则重新排序
	 */
	public void reSortData() {
		if (comparator != null)
			Collections.sort(data, comparator);
		notifyDataSetChanged();
	}
	
	public void orderData(){
		Collections.reverse(data);
		notifyDataSetChanged();
	}
	
	/**
	 * 清除数据集中的所有数据
	 */
	public void clear(){
		data.clear();
		notifyDataSetChanged();
	}

	/**
	 * 删除指定位置的元素
	 * 
	 * @param location
	 *            要删除的元素的位置
	 */
	public void reomveItem(int location) {
		data.remove(location);
		notifyDataSetChanged();
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	/**
	 * 判断适配器集合中是否已经包含该对象
	 * 
	 * @param obj
	 * @return
	 */
	public abstract boolean isContains(T obj);
	
}
