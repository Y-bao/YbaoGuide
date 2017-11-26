package com.ybao.guide;

import android.view.Gravity;
import android.view.View;

/**
 * Created by ybao on 2017/11/11.
 */

public class AttachedView {
    public static final int GRAVITY_TO_TARGET = 0;
    public static final int GRAVITY_TO_PARENT = 1;
    public static final int TYPE_VALUE = 0;
    public static final int TYPE_PS = 1;
    private View view;
    private int gravityWayX;
    private int gravityWayY;
    private int gravityX;
    private int gravityY;
    private int xType;
    private int yType;
    private float x;
    private float y;


    public AttachedView(View view, int gravityWayX, int gravityX, int xType, float x, int gravityWayY, int gravityY, int yType, float y) {
        this.view = view;
        this.gravityWayX = gravityWayX;
        this.gravityWayY = gravityWayY;
        this.gravityX = gravityX;
        this.gravityY = gravityY;
        this.xType = xType;
        this.yType = yType;
        this.x = x;
        this.y = y;
    }

    public AttachedView(View view, int gravityX, int xType, float x, int gravityY, int yType, float y) {
        this(view, GRAVITY_TO_TARGET, gravityX, xType, x, GRAVITY_TO_TARGET, gravityY, yType, y);
    }

    public AttachedView(View view, int gravityX, float x, int gravityY, float y) {
        this(view, GRAVITY_TO_TARGET, gravityX, TYPE_PS, x, GRAVITY_TO_TARGET, gravityY, TYPE_PS, y);
    }


    public AttachedView(View view, float x, float y) {
        this(view, GRAVITY_TO_TARGET, Gravity.LEFT, TYPE_PS, x, GRAVITY_TO_TARGET, Gravity.BOTTOM, TYPE_PS, y);
    }

    public AttachedView(View view) {
        this(view, GRAVITY_TO_TARGET, Gravity.LEFT, TYPE_PS, 0, GRAVITY_TO_TARGET, Gravity.BOTTOM, TYPE_PS, 0);
    }

    public View getView() {
        return view;
    }

    public AttachedView setView(View view) {
        this.view = view;
        return this;
    }

    public int getGravityWayX() {
        return gravityWayX;
    }

    public int getGravityWayY() {
        return gravityWayY;
    }

    public int getGravityX() {
        if ((gravityX & Gravity.HORIZONTAL_GRAVITY_MASK) == 0) {
            gravityX |= Gravity.LEFT;
        }
        return gravityX;
    }

    public int getGravityY() {
        if ((gravityY & Gravity.VERTICAL_GRAVITY_MASK) == 0) {
            gravityY |= Gravity.TOP;
        }
        return gravityY;
    }

    public int getxType() {
        return xType;
    }

    public int getyType() {
        return yType;
    }

    public float getX() {
        return x;
    }

    public float getXValue() {
        if (xType == TYPE_VALUE) {
            return x;
        }
        return x * view.getWidth();
    }

    public AttachedView setX(float x) {
        this.x = x;
        return this;
    }

    public float getY() {
        return y;
    }

    public float getYValue() {
        if (yType == TYPE_VALUE) {
            return y;
        }
        return y * view.getHeight();
    }

    public AttachedView setY(float y) {
        this.y = y;
        return this;
    }

    public AttachedView setX(int xType, float x) {
        this.xType = xType;
        this.x = x;
        return this;
    }

    public AttachedView setY(int yType, float y) {
        this.yType = yType;
        this.y = y;
        return this;
    }

    public AttachedView setGravityX(int gravityWayX, int gravityX) {
        this.gravityWayX = gravityWayX;
        this.gravityX = gravityX;
        return this;
    }

    public AttachedView setGravityY(int gravityWayY, int gravityY) {
        this.gravityWayY = gravityWayY;
        this.gravityY = gravityY;
        return this;
    }

    public AttachedView setGravityX(int gravityX) {
        this.gravityX = gravityX;
        return this;
    }

    public AttachedView setGravityY(int gravityY) {
        this.gravityY = gravityY;
        return this;
    }
}