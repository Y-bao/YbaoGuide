package com.ybao.guide;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ybao on 2017/11/11.
 */

public class GuideGroup extends FrameLayout {
    private List<Indicator> indicators;
    private HashMap<Indicator, List<AttachedView>> arrachedViewMap;
    private OverrideView overrideView;
    private OnTouchGuideListener onTouchGuideListener;
    public static final int TOUCH_PENETRATION_NONE = 0;
    public static final int TOUCH_PENETRATION_INDICATOR = 1;
    public static final int TOUCH_PENETRATION_ALL = 2;
    private int touchPenetrationMode = TOUCH_PENETRATION_NONE;


    GuideGroup(@NonNull Context context, List<Indicator> indicators) {
        super(context);
        this.indicators = indicators;
        initOverrideView();
        initArrachedView();
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                overrideView.invalidate();
                locationAttachedView();
            }
        });
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    private void initOverrideView() {
        overrideView = new OverrideView(getContext(), indicators);
        addView(overrideView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void initArrachedView() {
        if (indicators == null || indicators.isEmpty()) {
            return;
        }
        if (arrachedViewMap == null) {
            arrachedViewMap = new HashMap<>();
        }
        for (Indicator indicator : indicators) {
            List<AttachedView> attachedViews = indicator.getAttachedViews();
            List<Indicator.AttachedViewFrctory> attachedViewFrctories = indicator.getAttachedViewFrctories();
            if ((attachedViews == null || attachedViews.isEmpty()) && (attachedViewFrctories == null || attachedViewFrctories.isEmpty())) {
                continue;
            }
            List<AttachedView> arrachedViews = arrachedViewMap.get(indicator);
            if (arrachedViews == null) {
                arrachedViews = new ArrayList<>();
                arrachedViewMap.put(indicator, arrachedViews);
            }
            if (attachedViews != null && !attachedViews.isEmpty()) {
                for (AttachedView attachedView : attachedViews) {
                    arrachedViews.add(attachedView);
                    View view = attachedView.getView();
                    if (view.getLayoutParams() == null) {
                        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    }
                    addView(view);
                }
            }
            if (attachedViewFrctories != null && !attachedViewFrctories.isEmpty()) {
                for (Indicator.AttachedViewFrctory attachedViewFrctory : attachedViewFrctories) {
                    AttachedView attachedView = attachedViewFrctory.create(getContext());
                    View view = attachedView.getView();
                    if (view.getLayoutParams() == null) {
                        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    }
                    arrachedViews.add(attachedView);
                    addView(view);
                }
            }
        }
    }

    public void setDrawBackground(Drawable background) {
        overrideView.setDrawBackground(background);
    }

    public void setTouchPenetrationMode(int touchPenetrationMode) {
        this.touchPenetrationMode = touchPenetrationMode;
    }

    public void locationAttachedView() {
        if (indicators == null || indicators.isEmpty()) {
            return;
        }
        for (Indicator indicator : indicators) {
            locationAttachedViewByIndicator(indicator);
        }
    }

    public void locationAttachedViewByIndicator(Indicator indicator) {
        if (arrachedViewMap == null) {
            return;
        }
        List<AttachedView> attachedViews = arrachedViewMap.get(indicator);
        if (attachedViews == null || attachedViews.isEmpty()) {
            return;
        }
        int ph = getHeight();
        int pw = getWidth();
        RectF rectF = indicator.getRectF();
        for (AttachedView attachedView : attachedViews) {
            View view = attachedView.getView();
            if (!indicator.canUse()) {
                view.setVisibility(View.GONE);
                continue;
            }
            view.setVisibility(View.VISIBLE);
            float tx = 0;
            if (attachedView.getGravityWayX() == AttachedView.GRAVITY_TO_PARENT) {
                int gravity = attachedView.getGravityX();
                if ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.LEFT) {
                    tx = attachedView.getXValue();
                } else if ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.RIGHT) {
                    tx = pw - view.getWidth() - attachedView.getXValue();
                } else if ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.CENTER_HORIZONTAL) {
                    tx = (pw - view.getWidth()) / 2 + attachedView.getXValue();
                }
            } else {
                int gravity = attachedView.getGravityX();
                if ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.LEFT) {
                    tx = rectF.left - view.getWidth() - attachedView.getXValue();
                } else if ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.RIGHT) {
                    tx = rectF.right + attachedView.getXValue();
                } else if ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.CENTER_HORIZONTAL) {
                    tx = rectF.left + (rectF.width() - view.getWidth()) / 2 + attachedView.getXValue();
                }
            }
            float ty = 0;
            if (attachedView.getGravityWayY() == AttachedView.GRAVITY_TO_PARENT) {
                int gravity = attachedView.getGravityY();
                if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.TOP) {
                    ty = attachedView.getYValue();
                } else if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.BOTTOM) {
                    ty = ph - view.getHeight() - attachedView.getYValue();
                } else if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.CENTER_VERTICAL) {
                    ty = (ph - view.getHeight()) / 2 + attachedView.getYValue();
                }
            } else {
                int gravity = attachedView.getGravityY();
                if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.TOP) {
                    ty = rectF.top - view.getHeight() - attachedView.getYValue();
                } else if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.BOTTOM) {
                    ty = rectF.bottom + attachedView.getYValue();
                } else if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.CENTER_VERTICAL) {
                    ty = rectF.top + (rectF.height() - view.getHeight()) / 2 + attachedView.getYValue();
                }
            }
            view.setTranslationX(tx);
            view.setTranslationY(ty);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (touchPenetrationMode == TOUCH_PENETRATION_ALL) {
            return false;
        }
        if (indicators == null || indicators.isEmpty()) {
            if (onTouchGuideListener != null) {
                onTouchGuideListener.onTouchOverride();
            }
            return true;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                boolean hasTouchIndicator = false;
                for (Indicator indicator : indicators) {
                    RectF rectF = indicator.getRectF();
                    if (rectF.top <= ev.getRawY() && rectF.bottom >= ev.getRawY() && rectF.left <= ev.getRawX() && rectF.right >= ev.getRawX()) {
                        indicator.setTouchDown(true);
                        hasTouchIndicator = true;
                    } else {
                        indicator.setTouchDown(false);
                    }
                }
                if (!hasTouchIndicator && onTouchGuideListener != null) {
                    onTouchGuideListener.onTouchOverride();
                } else if (touchPenetrationMode == TOUCH_PENETRATION_INDICATOR) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                for (Indicator indicator : indicators) {
                    RectF rectF = indicator.getRectF();
                    if (indicator.isTouchDown()) {
                        if (rectF.top <= ev.getRawY() && rectF.bottom >= ev.getRawY() && rectF.left <= ev.getRawX() && rectF.right >= ev.getRawX()) {
                            if (onTouchGuideListener != null && onTouchGuideListener.onClickIndicator(indicator)) {
                                indicator.setTouchDown(false);
                            } else {
                                indicator.onClick();
                            }
                        } else {
                            indicator.onTouchCencl();
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                for (Indicator indicator : indicators) {
                    if (indicator.isTouchDown()) {
                        indicator.onTouchCencl();
                    }
                }
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//不起作用
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (onTouchGuideListener != null && onTouchGuideListener.onKeyBack()) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (indicators == null || indicators.isEmpty()) {
            return;
        }
        for (Indicator indicator : indicators) {
            indicator.onAttachedToWindow(onIndicatorListener);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (indicators == null || indicators.isEmpty()) {
            return;
        }
        for (Indicator indicator : indicators) {
            indicator.onDetachedFromWindow();
        }
        super.onDetachedFromWindow();
    }

    public void setOnTouchGuideListener(OnTouchGuideListener onTouchGuideListener) {
        this.onTouchGuideListener = onTouchGuideListener;
    }

    Indicator.OnIndicatorListener onIndicatorListener = new Indicator.OnIndicatorListener() {
        @Override
        public void onChange(Indicator indicator, boolean isMove) {
            overrideView.invalidate();
            if (isMove) {
                locationAttachedViewByIndicator(indicator);
            }
        }
    };

    public interface OnTouchGuideListener {
        boolean onClickIndicator(Indicator indicator);

        boolean onTouchOverride();

        boolean onKeyBack();
    }
}