package com.ybao.guide;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Y-bao on 2017/11/9 0009.
 */

public class Guide {
    public static final int TOUCH_PENETRATION_NONE = GuideGroup.TOUCH_PENETRATION_NONE;
    public static final int TOUCH_PENETRATION_INDICATOR = GuideGroup.TOUCH_PENETRATION_INDICATOR;
    public static final int TOUCH_PENETRATION_ALL = GuideGroup.TOUCH_PENETRATION_ALL;
    Context context;
    private GuideGroup guideGroup;
    OnShowListener onShowListener;
    OnClickIndicatorListener onClickIndicatorListener;

    private Guide(Context context, List<Indicator> indicators) {
        this.context = context;
        this.guideGroup = new GuideGroup(context, indicators);
        this.guideGroup.setOnTouchGuideListener(inOnTouchGuideListener);
        setFocusable(true);
    }

    public Guide setBackground(Drawable drawable) {
        guideGroup.setDrawBackground(drawable);
        return this;
    }

    public Guide setBackgroundColor(int color) {
        setBackground(new ColorDrawable(color));
        return this;
    }

    public Guide setBackgroundResource(int resId) {
        setBackground(context.getResources().getDrawable(resId));
        return this;
    }

    public Guide setFocusable(boolean focusable) {
        this.guideGroup.setFocusable(focusable);
        return this;
    }

    public Guide setOnShowListener(OnShowListener onShowListener) {
        this.onShowListener = onShowListener;
        return this;
    }

    public Guide setTouchPenetrationMode(int touchPenetrationMode) {
        this.guideGroup.setTouchPenetrationMode(touchPenetrationMode);
        return this;
    }

    public Guide show() {
        if (context instanceof Activity) {
            ViewGroup viewGroup = ((ViewGroup) ((Activity) context).getWindow().getDecorView());
            show(viewGroup);
        }
        return this;
    }

    public Guide show(ViewGroup viewGroup) {
        ViewParent oldParent = guideGroup.getParent();
        if (oldParent != viewGroup) {
            if (oldParent != null) {
                ((ViewGroup) oldParent).removeView(guideGroup);
            }
            viewGroup.addView(guideGroup, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            if (oldParent == null && onShowListener != null) {
                onShowListener.onShow();
            }
        }
        return this;
    }

    public Guide dismiss() {
        if (guideGroup.getParent() != null) {
            ViewGroup parent = (ViewGroup) guideGroup.getParent();
            parent.removeView(guideGroup);
            if (onShowListener != null) {
                onShowListener.onDismiss();
            }
        }
        return this;
    }

    GuideGroup.OnTouchGuideListener inOnTouchGuideListener = new GuideGroup.OnTouchGuideListener() {
        @Override
        public boolean onClickIndicator(Indicator indicator) {
            if (onClickIndicatorListener != null) {
                return onClickIndicatorListener.onClickIndicator(indicator);
            }
            return false;
        }

        @Override
        public boolean onTouchOverride() {
            dismiss();
            return false;
        }

        @Override
        public boolean onKeyBack() {
            dismiss();
            return true;
        }
    };

    public void setOnTouchGuideListener(OnClickIndicatorListener onClickIndicatorListener) {
        this.onClickIndicatorListener = onClickIndicatorListener;
    }

    public interface OnShowListener {
        void onShow();

        void onDismiss();
    }

    public interface OnClickIndicatorListener {
        boolean onClickIndicator(Indicator indicator);
    }

    public static class Builder {
        List<Indicator> indicators;
        Drawable bgDrawable;
        Context context;

        public Builder(Context context) {
            this.context = context;
        }

        public Indicator addIndicator(View view) {
            return onAddIndicator(new Indicator(this, view, Indicator.VIEW_ONESEIF));
        }

        public Indicator addIndicator(View view, int mode) {
            return onAddIndicator(new Indicator(this, view, mode));
        }

        public Indicator addIndicator(Indicator indicator) {
            indicator.setBuilder(this);
            return onAddIndicator(indicator);
        }

        public Indicator onAddIndicator(Indicator indicator) {
            if (indicators == null) {
                indicators = new ArrayList<>();
            }
            indicators.add(indicator);
            return indicator;
        }


        public Builder setBackground(Drawable drawable) {
            this.bgDrawable = drawable;
            return this;
        }

        public Builder setBackgroundColor(int color) {
            setBackground(new ColorDrawable(color));
            return this;
        }

        public Builder setBackgroundResource(int resId) {
            setBackground(context.getResources().getDrawable(resId));
            return this;
        }

        public Guide create() {
            return new Guide(context, indicators).setBackground(bgDrawable);
        }
    }
}
