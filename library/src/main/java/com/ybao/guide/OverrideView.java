package com.ybao.guide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

/**
 * Created by ybao on 2017/11/11.
 */
class OverrideView extends FrameLayout {
    private Paint layerPaint;
    private Paint maskPaint;
    List<Indicator> indicators;
    private View bgView;

    public OverrideView(Context context, List<Indicator> indicators) {
        super(context);
        this.indicators = indicators;
        init();
    }

    private void init() {
        bgView = new View(getContext());
        addView(bgView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        layerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        layerPaint.setXfermode(null);

        maskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        maskPaint.setStyle(Paint.Style.FILL);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    public void setDrawBackground(Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            bgView.setBackground(background);
        } else {
            bgView.setBackgroundDrawable(background);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (indicators == null || indicators.isEmpty()) {
            super.dispatchDraw(canvas);
        } else {
            int cWidth = canvas.getWidth();
            int cHeight = canvas.getHeight();
            int sc = canvas.saveLayer(new RectF(0, 0, cWidth, cHeight), layerPaint, Canvas.ALL_SAVE_FLAG);
            super.dispatchDraw(canvas);
            for (Indicator indicator : indicators) {
                if (!indicator.canUse()) {
                    continue;
                }
                int mode = indicator.getMode();
                if (mode == Indicator.VIEW_ONESEIF) {
                    indicator.getTargetView().buildDrawingCache();
                    Bitmap bitmap = indicator.getTargetView().getDrawingCache();
                    if (bitmap != null) {
                        RectF rectF = indicator.getRectF();
                        canvas.drawBitmap(bitmap, new Rect(0, 0, cWidth, cHeight), rectF, maskPaint);
                    }
                } else if (mode == Indicator.CIRCLE) {
                    RectF rectF = indicator.getRectF();
                    float cx = rectF.centerX();
                    float cy = rectF.centerY();
                    float w = rectF.width();
                    float h = rectF.height();
                    float r = (float) (Math.sqrt(w * w + h * h) / 2);
                    canvas.drawCircle(cx, cy, r, maskPaint);
                } else if (mode == Indicator.OVAL) {
                    RectF rectF = indicator.getRectF();
                    canvas.drawOval(rectF, maskPaint);
                } else if (mode == Indicator.RECTANGLE) {
                    RectF rectF = indicator.getRectF();
                    canvas.drawRect(rectF, maskPaint);
                } else if (mode == Indicator.ROUND_RECTANGLE) {
                    RectF rectF = indicator.getRectF();
                    int rc = indicator.getCorners();
                    canvas.drawRoundRect(rectF, rc, rc, maskPaint);
                }
            }
            canvas.restoreToCount(sc);
        }
    }
}