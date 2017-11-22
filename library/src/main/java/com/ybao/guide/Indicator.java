package com.ybao.guide;

import android.content.Context;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ybao on 2017/11/11.
 */

public class Indicator {
    public static int VIEW_ONESEIF = 0;
    public static int RECTANGLE = 1;
    public static int ROUND_RECTANGLE = 2;
    public static int CIRCLE = 3;
    public static int OVAL = 4;
    private Guide.Builder builder;
    private String tag;
    private View targetView;
    private int mode;
    private int padding = 0;
    private int corners;
    List<AttachedView> attachedViews;
    List<AttachedViewFrctory> attachedViewFrctories;
    OnIndicatorListener onIndicatorListener;
    OnClickIndicatorListener onClickIndicatorListener;
    boolean isOnAttached = false;

    public Indicator() {
    }

    Indicator(Guide.Builder builder, View targetView, int mode) {
        this.builder = builder;
        this.targetView = targetView;
        this.mode = mode;
    }

    public void setBuilder(Guide.Builder builder) {
        this.builder = builder;
    }

    protected Indicator setTargetView(View targetView) {
        if (isOnAttached) {
            if (targetView != null) {
                targetView.getViewTreeObserver().removeOnPreDrawListener(onPreDrawListener);
            }
            this.targetView = targetView;
            if (targetView != null) {
                targetView.getViewTreeObserver().addOnPreDrawListener(onPreDrawListener);
            }
        } else {
            this.targetView = targetView;
        }
        return this;
    }

    public Indicator setMode(int mode) {
        this.mode = mode;
        return this;
    }

    public Indicator setCorners(int corners) {
        this.corners = corners;
        return this;
    }

    public Indicator setPadding(int padding) {
        this.padding = padding;
        return this;
    }

    public Indicator setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public Indicator setOnClickIndicatorListener(OnClickIndicatorListener onClickIndicatorListener) {
        this.onClickIndicatorListener = onClickIndicatorListener;
        return this;
    }

    public Indicator addAttachedView(View view) {
        addAttachedView(new AttachedView(view));
        return this;
    }

    public Indicator addAttachedView(View view, float x, float y) {
        addAttachedView(new AttachedView(view, x, y));
        return this;
    }

    public Indicator addAttachedView(View view, int gravityX, float x, int gravityY, float y) {
        addAttachedView(new AttachedView(view, gravityX, x, gravityY, y));
        return this;
    }

    public Indicator addAttachedView(View view, int gravityX, int xType, float x, int gravityY, int yType, float y) {
        addAttachedView(new AttachedView(view, gravityX, xType, x, gravityY, yType, y));
        return this;
    }

    public Indicator addAttachedView(View view, int gravityWayX, int gravityX, int xType, float x, int gravityWayY, int gravityY, int yType, float y) {
        addAttachedView(new AttachedView(view, gravityWayX, gravityX, xType, x, gravityWayY, gravityY, yType, y));
        return this;
    }

    public Indicator addAttachedView(AttachedView attachedView) {
        if (attachedViews == null) {
            attachedViews = new ArrayList<>();
        }
        attachedViews.add(attachedView);
        return this;
    }

    public Indicator addAttachedView(AttachedViewFrctory frctory) {
        if (attachedViewFrctories == null) {
            attachedViewFrctories = new ArrayList<>();
        }
        attachedViewFrctories.add(frctory);
        return this;
    }

    public Guide.Builder complete() {
        return builder;
    }

    int getCorners() {
        return corners;
    }

    View getTargetView() {
        return targetView;
    }

    boolean canUse() {
        return targetView != null;
    }

    public int getMode() {
        return mode;
    }

    public String getTag() {
        return tag;
    }

    RectF getRectF() {
        RectF rectF = new RectF();
        int[] location = new int[2];
        if (targetView != null) {
            targetView.getLocationOnScreen(location);
            rectF.left = location[0] - padding;
            rectF.top = location[1] - padding;
            rectF.right = location[0] + targetView.getWidth() + padding;
            rectF.bottom = location[1] + targetView.getHeight() + padding;
        }
        return rectF;
    }

    public List<AttachedView> getAttachedViews() {
        return attachedViews;
    }

    public List<AttachedViewFrctory> getAttachedViewFrctories() {
        return attachedViewFrctories;
    }


    final void onAttachedToWindow(OnIndicatorListener onIndicatorListener) {
        onDetachedFromWindow();
        if (targetView != null) {
            targetView.getViewTreeObserver().addOnPreDrawListener(onPreDrawListener);
        }
        this.onIndicatorListener = onIndicatorListener;
        isOnAttached = true;
        onAttached();
    }

    final void onDetachedFromWindow() {
        isOnAttached = false;
        onDetached();
        onIndicatorListener = null;
        if (targetView != null) {
            targetView.getViewTreeObserver().removeOnPreDrawListener(onPreDrawListener);
        }
    }

    protected void onAttached() {
    }

    protected void onDetached() {
    }


    boolean touchDown = false;

    public void setTouchDown(boolean touchDown) {
        this.touchDown = touchDown;
    }

    public boolean isTouchDown() {
        return touchDown;
    }

    void onClick() {
        if (onClickIndicatorListener != null) {
            onClickIndicatorListener.onClickIndicator();
        }
    }

    void onTouchCencl() {
        setTouchDown(false);
    }

    public interface OnClickIndicatorListener {
        boolean onClickIndicator();
    }

    ViewTreeObserver.OnPreDrawListener onPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {
        @Override
        public boolean onPreDraw() {
            if (onIndicatorListener != null) {
                onIndicatorListener.onChange(Indicator.this);
            }
            return true;
        }
    };

    public interface AttachedViewFrctory {
        AttachedView create(Context context);
    }

    public interface OnIndicatorListener {
        void onChange(Indicator indicator);
    }
}