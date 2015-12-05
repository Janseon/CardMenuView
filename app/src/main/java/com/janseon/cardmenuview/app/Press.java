package com.janseon.cardmenuview.app;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * 
 * @Description 按下效果
 * @author 健兴
 * @version 1.0
 * @date 2014-4-29
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc.
 *             All rights reserved.
 */
public class Press {

	/** 半透明黑色 */
	private static int tran_black = 0x22000000;

	/**
	 * 描述：绘制按下的效果
	 * 
	 * @version 1.0
	 * @createTime 2014-4-29 上午11:13:36
	 * @createAuthor 健兴
	 * 
	 * @updateTime 2014-4-29 上午11:13:36
	 * @updateAuthor 健兴
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param paramView
	 * @param paramCanvas
	 */
	public static void press(View paramView, Canvas paramCanvas) {
		if (paramView.isPressed()) {
			Paint localPaint = new Paint();
			localPaint.setColor(tran_black);
			localPaint.setAntiAlias(true);
			paramCanvas.drawRect(new Rect(0, 0, paramView.getWidth(), paramView.getHeight()), localPaint);
		}
	}

	/**
	 * 描述：玄心View的Drawable状态
	 * 
	 * @version 1.0
	 * @createTime 2014-4-29 上午11:14:02
	 * @createAuthor 健兴
	 * 
	 * @updateTime 2014-4-29 上午11:14:02
	 * @updateAuthor 健兴
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param paramView
	 * @param paramBoolean
	 * @return
	 */
	public static boolean refreshDrawableState(View paramView, boolean paramBoolean) {
		if (paramView.isPressed()) {
			if (!paramBoolean) {
				paramBoolean = true;
				paramView.invalidate();
				return paramBoolean;
			}
		}
		paramView.invalidate();
		return false;
	}
}
