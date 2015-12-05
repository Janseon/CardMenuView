package com.janseon.cardmenuview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.Scroller;

public class CardFrameLayout extends FrameLayout implements OnTouchListener {
    /***/
    private final float DIVIDE_RATE = 5;
    private final int maxRotation = 15;
    private final int hideResetDuration = 800;

    private final Scroller mScroller;
    private float mDownMotionY;
    private float mDownScrollY;
    private int mOffset;
    protected int mLayoutHeight;

    private Camera mCamera = new Camera();
    private Transformation mTransformation = new Transformation();
    private PaintFlagsDrawFilter mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

    private Runnable mFinishRunnable;
    private boolean mHideRestOthersEnable = false;
    private boolean mFirstHideEnable = false;
    private boolean mFirstResetEnable = false;

    public CardFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScroller = new Scroller(context);
    }

    public void setFirstHideEnable(boolean enable) {
        mFirstHideEnable = enable;
        mFirstResetEnable = false;
    }

    public void setFirstResetEnable(boolean enable) {
        mFirstResetEnable = enable;
        mFirstHideEnable = false;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mLayoutHeight = h - mOffset;
        super.onSizeChanged(w, h, oldw, oldh);
        if (mFirstHideEnable) {
            scrollTo(0, -h);
            setVisibility(View.GONE);
        }
        if (mFirstResetEnable) {
            scrollTo(0, -mLayoutHeight);
        }
    }

    private View mTransformView;

    @Override
    public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        child.setOnTouchListener(this);
        if (mTransformView == null) {
            mTransformView = child;
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            invalidateScrollYTo(mScroller.getCurrY());
        }
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        MarginLayoutParams marginParams = (MarginLayoutParams) params;
        mOffset = marginParams.topMargin;
        marginParams.topMargin = 0;
        super.setLayoutParams(params);
    }

    public int getOffset() {
        return mOffset;
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        if (mTransformView == child && applyTransformation()) {
            canvas.save();
            canvas.concat(mTransformation.getMatrix());
            canvas.setDrawFilter(mPaintFlagsDrawFilter);
            boolean drawChild = super.drawChild(canvas, child, drawingTime);
            canvas.restore();
            return drawChild;
        }
        return super.drawChild(canvas, child, drawingTime);
    }

    private boolean applyTransformation() {
        mTransformation.clear();
        mTransformation.setTransformationType(Transformation.TYPE_MATRIX);
        final Matrix matrix = mTransformation.getMatrix();

        final int layoutHeight = getHeight();
        final int currY = getScrollY();
        final float effectsAmount = currY / (float) layoutHeight;
        if (effectsAmount == 0f) {
            return false;
        }

        float degrees = maxRotation * effectsAmount;
        final Camera camera = mCamera;
        camera.save();
        camera.rotateX(degrees);
        camera.getMatrix(matrix);
        camera.restore();

        final float centerX = getWidth() / 2;
        matrix.preTranslate(-centerX, 0);
        matrix.postTranslate(centerX, 0);
        return true;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return onChildTouchEvent(event);
    }

    private boolean onChildTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onChildTouchDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                if (!touchDowned) {
                    onChildTouchDown(event);
                    break;
                }
                final float y = event.getRawY();
                int scrollY = (int) (mDownMotionY - y + mDownScrollY);
                invalidateScrollYTo(scrollY);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                touchDowned = false;
                int startScrollY = getScrollY();
                final int endY = getEndY(startScrollY);
                int dy = endY - startScrollY;
                int duration = getDuration(dy);
                startScroll(startScrollY, endY, duration, null);
                if (mHideRestOthersEnable) {
                    hideRestOthers(endY);
                }
                break;
        }
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                touchDowned = false;
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private boolean touchDowned = false;

    private void onChildTouchDown(MotionEvent event) {
        touchDowned = true;
        abortAnimation();
        if (mHideRestOthersEnable) {
            abortAnimationOthers();
        }
        mDownMotionY = event.getRawY();
        mDownScrollY = getScrollY();
    }

    private int getEndY(int startScrollY) {
        int endY = 0;
        if (startScrollY < mDownScrollY) {
            if (startScrollY < -mLayoutHeight / DIVIDE_RATE) {
                endY = -mLayoutHeight;
            } else {
                endY = 0;
            }
        } else {
            if (startScrollY > -mLayoutHeight * (DIVIDE_RATE - 1) / DIVIDE_RATE) {
                endY = 0;
            } else {
                endY = -mLayoutHeight;
            }
        }
        return endY;
    }

    private int getDuration(int dy) {
        int duration = Math.abs(dy) * 2;
        if (duration > 300) {
            duration = 300;
        } else if (duration < 160) {
            duration = 160;
        }
        return duration;
    }

    public void startResetScroll() {
        startResetScroll(null);
    }

    public void startResetScroll(int hideResetDuration) {
        setVisibility(View.VISIBLE);
        startScroll(-mLayoutHeight, hideResetDuration, null);
    }

    public void startResetScroll(Runnable finishRunnable) {
        setVisibility(View.VISIBLE);
        startScroll(-mLayoutHeight, hideResetDuration, finishRunnable);
    }

    public void startHideScroll(Runnable finishRunnable) {
        startScroll(-getHeight(), hideResetDuration, finishRunnable);
    }

    public void startScroll(int endY, int duration, Runnable finishRunnable) {
        startScroll(getScrollY(), endY, duration, finishRunnable);
    }

    public void startScroll(int startScrollY, int endY, int duration, Runnable finishRunnable) {
        int dy = endY - startScrollY;
        if (dy == 0) {
            return;
        }
        startScroll(startScrollY, dy, duration);
        if (finishRunnable != null) {
            mFinishRunnable = finishRunnable;
            postDelayed(finishRunnable, duration);
        }
    }

    private void startScroll(int startScrollY, int dy, int duration) {
        abortAnimation();
        mScroller.startScroll(0, startScrollY, 0, dy, duration);
        invalidate();
    }

    private void abortAnimation() {
        removeCallbacks(mFinishRunnable);
        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
            Log.i("abortAnimation", "abortAnimation");
        }
    }

    protected void invalidateScrollYTo(int y) {
        scrollTo(0, y);
        invalidate();
    }

    public void reset() {
        setVisibility(View.VISIBLE);
        invalidateScrollYTo(-mLayoutHeight);
    }

    public void resetScroll(long delayMillis) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                startResetScroll();
            }
        }, delayMillis);
    }

    public void resetScroll(long delayMillis, final int startResetScroll) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                startResetScroll(startResetScroll);
            }
        }, delayMillis);
    }

    public boolean checkReset() {
        int scrollY = getScrollY();
        return getVisibility() == View.GONE || scrollY <= -mLayoutHeight;
    }

    // /////////////
    public void setHideRestOthersEnable(boolean enable) {
        mHideRestOthersEnable = enable;
    }

    private void hideRestOthers(int endY) {
        ViewGroup parent = (ViewGroup) getParent();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (child == this) {
                continue;
            }
            if (child instanceof CardFrameLayout) {
                if (endY == 0) {
                    ((CardFrameLayout) child).startHideScroll(new Runnable() {
                        @Override
                        public void run() {
                            child.setVisibility(View.GONE);
                        }
                    });
                } else if (endY == -mLayoutHeight) {
                    ((CardFrameLayout) child).startResetScroll();
                }
            }
        }
    }

    private void abortAnimationOthers() {
        ViewGroup parent = (ViewGroup) getParent();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (child == this) {
                continue;
            }
            if (child instanceof CardFrameLayout) {
                ((CardFrameLayout) child).abortAnimation();
            }
        }
    }

    // // ////////
    // private boolean canChildScrollUp(View target) {
    // if (android.os.Build.VERSION.SDK_INT < 14) {
    // if (target instanceof AbsListView) {
    // final AbsListView absListView = (AbsListView) target;
    // return absListView.getChildCount() > 0
    // && (absListView.getFirstVisiblePosition() > 0 ||
    // absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
    // } else {
    // return target.getScrollY() > 0;
    // }
    // } else {
    // return ViewCompat.canScrollVertically(target, -1);
    // }
    // }
}