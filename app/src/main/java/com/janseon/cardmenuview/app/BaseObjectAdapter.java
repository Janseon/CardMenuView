package com.janseon.cardmenuview.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * 
 * @Description Adapter
 * @author 健兴
 * @version 1.0
 * @date 2014-4-29
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc.
 *             All rights reserved.
 * @param <T>
 *            Object的子类
 */
public abstract class BaseObjectAdapter<T> extends BaseAdapter {
	protected Context mContext;
	protected LayoutInflater mInflater;
	protected ArrayList<T> modelList = new ArrayList<T>();

	public ArrayList<T> getList() {
		return modelList;
	}

	public void clear() {
		modelList.clear();
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return modelList.size();
	}

	@Override
	public T getItem(int position) {
		return modelList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public BaseObjectAdapter(Context context, ArrayList<T> list) {
		super();
		mContext = context;
		mInflater = LayoutInflater.from(context);
		modelList = list;
	}

	/**
	 * 描述：
	 * 
	 * @version 1.0
	 * @createTime 2014-7-23 上午11:15:52
	 * @createAuthor 健兴
	 * 
	 * @updateTime 2014-7-23 上午11:15:52
	 * @updateAuthor 健兴
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param value
	 */
	public void add(T value) {
		modelList.add(value);
		notifyDataSetChanged();
	}

	/**
	 * 描述：
	 * 
	 * @version 1.0
	 * @createTime 2014-7-23 上午11:15:55
	 * @createAuthor 健兴
	 * 
	 * @updateTime 2014-7-23 上午11:15:55
	 * @updateAuthor 健兴
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param index
	 * @param value
	 */
	public void add(int index, T value) {
		modelList.add(index, value);
		notifyDataSetChanged();
	}

	/**
	 * 描述：添加最新的数据
	 * 
	 * @version 1.0
	 * @createTime 2014-4-29 上午11:16:37
	 * @createAuthor 健兴
	 * 
	 * @updateTime 2014-4-29 上午11:16:37
	 * @updateAuthor 健兴
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param list
	 */
	public void addNew(ArrayList<T> list) {
		modelList.addAll(0, list);
		notifyDataSetChanged();
	}

	/**
	 * 描述：添加更多数据
	 * 
	 * @version 1.0
	 * @createTime 2014-4-29 上午11:17:05
	 * @createAuthor 健兴
	 * 
	 * @updateTime 2014-4-29 上午11:17:05
	 * @updateAuthor 健兴
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param list
	 */
	public void addMore(ArrayList<T> list) {
		modelList.addAll(list);
		notifyDataSetChanged();
	}

	/**
	 * 描述：删除
	 * 
	 * @version 1.0
	 * @createTime 2014-7-23 上午9:55:12
	 * @createAuthor 健兴
	 * 
	 * @updateTime 2014-7-23 上午9:55:12
	 * @updateAuthor 健兴
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param value
	 */
	public void remove(T value) {
		modelList.remove(value);
		notifyDataSetChanged();
	}

	public T find(T another) {
		if (another == null) {
			return null;
		}
		for (T obj : modelList) {
			if (another.equals(obj)) {
				return obj;
			}
		}
		return null;
	}

	public void findRemove(T value) {
		T finadValue = find(value);
		remove(finadValue);
		notifyDataSetChanged();
	}

	public void findUpdate(T value) {
		int index = indexOf(value);
		update(index, value);
	}

	public void update(int index, T value) {
		modelList.set(index, value);
		notifyDataSetChanged();
	}

	public int indexOf(T another) {
		if (another == null) {
			return -1;
		}
		int size = modelList.size();
		for (int i = 0; i < size; i++) {
			if (another.equals(modelList.get(i))) {
				return i;
			}
		}
		return -1;
	}

	protected static void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		LinearLayout layout = null;
		if (p == null) {
			layout = new LinearLayout(child.getContext());
			p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			layout.addView(child, p);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
		if (layout != null) {
			layout.removeView(child);
		}
	}

	protected static ArrayList<String> toStringList(int num) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("");
		for (int i = 0; i < num; i++) {
			list.addAll(list);
		}
		return list;
	}
}