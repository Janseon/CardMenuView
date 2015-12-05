package com.janseon.cardmenuview.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 * 
 * @Description
 * @author 健兴
 * @version 1.0
 * @date 2014-9-17
 * @Copyright: Copyright (c) 2014 Shenzhen Inser Technology Co., Ltd. Inc. All
 *             rights reserved.
 */
public class MenusLayout extends LinearLayout {
	private final int numCol = 3;

	public MenusLayout(Context context) {
		this(context, null);
	}

	public MenusLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(VERTICAL);
	}

	/** 适配器 */
	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener l) {
		mOnItemClickListener = l;
	}

	/** 适配器 */
	private BaseAdapter mAdapter;

	/**
	 * 描述：获取适配器
	 * 
	 * @version 1.0
	 * @createTime 2014-4-29 上午11:21:57
	 * @createAuthor 健兴
	 * 
	 * @updateTime 2014-4-29 上午11:21:57
	 * @updateAuthor 健兴
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @return
	 */
	public BaseAdapter getAdapter() {
		return mAdapter;
	}

	/**
	 * 描述：设置适配器
	 * 
	 * @version 1.0
	 * @createTime 2014-4-29 上午11:22:16
	 * @createAuthor 健兴
	 * 
	 * @updateTime 2014-4-29 上午11:22:16
	 * @updateAuthor 健兴
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param adapter
	 */
	public void setAdapter(BaseAdapter adapter) {
		removeAllViews();
		if (adapter == null) {
			return;
		}
		delay = 500;
		addVer2Driver();
		int count = adapter.getCount();
		int numRow = count / numCol;
		int numRest = count % numCol;
		for (int i = 0; i < numRow; i++) {
			addRowLayout(adapter, i, numCol);
		}
		if (numRest > 0) {
			addRowLayout(adapter, numRow, numRest);
		}
	}

	private void addRowLayout(BaseAdapter adapter, int i, int count) {
		LinearLayout rowLayout = new LinearLayout(getContext());
		rowLayout.setOrientation(HORIZONTAL);
		for (int j = 0; j < numCol; j++) {
			final int index = i * numCol + j;
			final ScaleFrameLayout childLayout = new ScaleFrameLayout(getContext());
			LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
			params.weight = 1;
			rowLayout.addView(childLayout, params);
			if (j < count) {
				adapter.getView(index, null, childLayout);
				if (j < count - 1) {
					addHorDriver(rowLayout);
				}
				childLayout.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (mOnItemClickListener != null) {
							mOnItemClickListener.onItemClick(null, v, index, 0);
						}
					}
				});
			} else {
				childLayout.setVisibility(View.INVISIBLE);
			}
			postAnimation(childLayout);

		}
		addItemView(rowLayout);
	}

	public static final int duration = 360;
	public static int delay_offset = 100;
	private int delay;

	private void postAnimation(final View childLayout) {
		delay += delay_offset;
		postAnimation(delay, childLayout);
	}

	public static void postAnimation(int delay, final View childLayout) {
		int visibility = childLayout.getVisibility();
		if (visibility != View.VISIBLE) {
			return;
		}
		childLayout.setVisibility(View.INVISIBLE);
		childLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				childLayout.setVisibility(View.VISIBLE);
				AnimationSet animationSet = new AnimationSet(true);
				animationSet.setDuration(duration);
				animationSet.setInterpolator(new OvershootInterpolator());
				int pivotXType = Animation.RELATIVE_TO_SELF;
				// animationSet.addAnimation(new TranslateAnimation(pivotXType,
				// -1f, pivotXType, 0, pivotXType, 0, pivotXType, 0));
				animationSet.addAnimation(new ScaleAnimation(0.3f, 1, 0.3f, 1, pivotXType, 0.5f, pivotXType, 0.5f));
				animationSet.addAnimation(new AlphaAnimation(0, 1));
				childLayout.startAnimation(animationSet);
			}
		}, delay);
	}

	private void addItemView(View view) {
		addView(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		addVerDriver();
	}

	private void addHorDriver(LinearLayout rowLayout) {
		View divider = new View(getContext());
		divider.setBackgroundResource(R.color.divider_black_menu);
		rowLayout.addView(divider, 1, LayoutParams.MATCH_PARENT);
		divider = new View(getContext());
		divider.setBackgroundResource(R.color.divider_gray_menu);
		rowLayout.addView(divider, 1, LayoutParams.MATCH_PARENT);
	}

	private void addVerDriver() {
		View divider = new View(getContext());
		divider.setBackgroundResource(R.color.divider_black_menu);
		addView(divider, LayoutParams.MATCH_PARENT, 1);
		divider = new View(getContext());
		divider.setBackgroundResource(R.color.divider_gray_menu);
		addView(divider, LayoutParams.MATCH_PARENT, 1);
	}

	private void addVer2Driver() {
		View divider = new View(getContext());
		divider.setBackgroundResource(R.color.divider_gray_menu);
		addView(divider, LayoutParams.MATCH_PARENT, 1);
	}
}
