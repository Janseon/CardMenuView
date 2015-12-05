package com.janseon.cardmenuview.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;
import android.widget.TextView;

/**
 * @author 健兴
 * @version 1.0
 * @Description Drawable相关的工具包
 * @date 2014-6-13
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc.
 * All rights reserved.
 */
public class DrawableUtil {

    public static GradientDrawable getCornerRadiiDrawable(float radius, int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadii(new float[]{radius, radius, radius, radius, radius, radius, radius, radius});
        drawable.setColor(color);
        return drawable;
    }

    public static GradientDrawable getCornerRadiiDrawable(float[] radii, int color) {
        GradientDrawable drawable = new GradientDrawable() {
            @Override
            public void draw(Canvas canvas) {
                super.draw(canvas);
            }
        };
        drawable.setCornerRadii(radii);
        drawable.setColor(color);
        return drawable;
    }

    public static Drawable getTextDrawable(Context context, int resId) {
        Drawable drawable = context.getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        return drawable;
    }

    public static void setTextDrawableLeft(TextView txt, int padDip, int resId) {
        txt.setCompoundDrawables(getTextDrawable(txt.getContext(), resId), null, null, null);
        if (padDip >= 0) {
            txt.setCompoundDrawablePadding(dp2Px(txt.getContext(), padDip));
        }
    }

    public static void setTextDrawableRight(TextView txt, int padDip, int resId) {
        txt.setCompoundDrawables(null, null, getTextDrawable(txt.getContext(), resId), null);
        if (padDip >= 0) {
            txt.setCompoundDrawablePadding(dp2Px(txt.getContext(), padDip));
        }
    }

    public static void setTextDrawableTop(TextView txt, int padDip, int resId) {
        txt.setCompoundDrawables(null, getTextDrawable(txt.getContext(), resId), null, null);
        if (padDip >= 0) {
            txt.setCompoundDrawablePadding(dp2Px(txt.getContext(), padDip));
        }
    }

    public static void setTextDrawable(TextView txt, int padDip, int[] resId) {
        Drawable left = null, top = null, right = null, bottom = null;
        if (resId[0] != 0) {
            left = getTextDrawable(txt.getContext(), resId[0]);
        }
        if (resId[1] != 0) {
            top = getTextDrawable(txt.getContext(), resId[1]);
        }
        if (resId[2] != 0) {
            right = getTextDrawable(txt.getContext(), resId[2]);
        }
        if (resId[3] != 0) {
            bottom = getTextDrawable(txt.getContext(), resId[3]);
        }
        txt.setCompoundDrawables(left, top, right, bottom);
        if (padDip >= 0) {
            txt.setCompoundDrawablePadding(dp2Px(txt.getContext(), padDip));
        }
    }

    public static int dp2Px(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (dp * displayMetrics.density);
    }
}
