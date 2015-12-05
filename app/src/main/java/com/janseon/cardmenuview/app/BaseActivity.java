package com.janseon.cardmenuview.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 
 * @Description 基类Activity
 * @author 健兴
 * @version 1.0
 * @date 2014-4-17
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc.
 *             All rights reserved.
 */
public class BaseActivity extends Activity {
	protected TextView title_view;
	protected ScrollView scrollView;

	/**
	 *
	 * 描述：把所有View找出来
	 *
	 * @createTime 2014-3-30 下午3:09:24
	 * @createAuthor 健兴
	 *
	 * @updateTime 2014-3-30 下午3:09:24
	 * @updateAuthor 健兴
	 * @updateInfo
	 *
	 */
	protected void findViews() {
	}

	/**
	 *
	 * 描述：设置View的内容
	 *
	 * @createTime 2014-3-30 下午3:10:22
	 * @createAuthor 健兴
	 *
	 * @updateTime 2014-3-30 下午3:10:22
	 * @updateAuthor 健兴
	 * @updateInfo
	 *
	 */
	protected void setViewsContent() {
	}

	/**
	 *
	 * 描述：设置View的监控器
	 *
	 * @createTime 2014-3-30 下午3:10:36
	 * @createAuthor 健兴
	 *
	 * @updateTime 2014-3-30 下午3:10:36
	 * @updateAuthor 健兴
	 * @updateInfo
	 *
	 */
	protected void setViewsListener() {
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


	protected void setParentOnClickListener(View view, OnClickListener l) {
		((View) view.getParent()).setOnClickListener(l);
	}

	protected final void setTitleContentView(int layoutResID) {
		setTitleContentView(layoutResID, false);
	}

	protected final void setTitleContentView(int layoutResID, boolean setBg) {
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		title_view = new TextView(this);
		layout.addView(title_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		View.inflate(this, layoutResID, layout);
		super.setContentView(layout);
		if (setBg) {
			setBackGround(R.color.bg_gray);
		} else {
			getWindow().setBackgroundDrawable(null);
		}
	}

	protected final void setTitleContentView(View view) {
		setTitleContentView(view, false);
	}

	protected final void setTitleContentView(View view, boolean setBg) {
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		title_view = new TextView(this);
		layout.addView(title_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		layout.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		super.setContentView(layout);
		if (setBg) {
			setBackGround(R.color.bg_gray);
		} else {
			getWindow().setBackgroundDrawable(null);
		}
	}

	protected final void setTitleScrollContentView(int layoutResID) {
		setTitleScrollContentView(layoutResID, false, false);
	}

	protected final void setTitleScrollContentView(int layoutResID, boolean setBg) {
		setTitleScrollContentView(layoutResID, setBg, false);
	}

	protected final void setTitleScrollContentView(int layoutResID, boolean setBg, boolean setFillViewport) {
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		title_view = new TextView(this);
		layout.addView(title_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		scrollView = new ScrollView(this);
		if (setFillViewport) {
			scrollView.setFillViewport(true);
		}
		layout.addView(scrollView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		View.inflate(this, layoutResID, scrollView);
		super.setContentView(layout);
		if (setBg) {
			setBackGround(R.color.bg_gray);
		} else {
			getWindow().setBackgroundDrawable(null);
		}
	}

	protected final void setScrollContentView(int layoutResID, boolean setBg) {
		scrollView = new ScrollView(this);
		View.inflate(this, layoutResID, scrollView);
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		super.setContentView(scrollView, lp);
		if (setBg) {
			setBackGround(R.color.bg_gray);
		} else {
			getWindow().setBackgroundDrawable(null);
		}
	}


	protected final View addLayoutContentView(int layoutResID, int index, LinearLayout.LayoutParams params) {
		View view = View.inflate(this, layoutResID, null);
		LinearLayout layout = (LinearLayout) title_view.getParent();
		layout.addView(view, index, params);
		return view;
	}

	protected final void setBackGround(int resId) {
		getWindow().setBackgroundDrawableResource(resId);
	}

}
