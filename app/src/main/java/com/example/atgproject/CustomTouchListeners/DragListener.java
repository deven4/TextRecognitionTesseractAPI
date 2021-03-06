package com.example.atgproject.CustomTouchListeners;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class DragListener implements View.OnTouchListener {

    private static final String TAG = "mTAG_DRAG";
    boolean drag = false;
    boolean isTouchable;
    double newFingerRotation;
    private float viewRotation;
    private double fingerRotation;
    private final ViewGroup rootLayout;

    public DragListener(ViewGroup rootLayout) {
        this.rootLayout = rootLayout;
    }

    private int getAngle(float xt, float yt, float x, float y) {
        float dx = x - xt;
        float dy = yt - y;

        double inRads = Math.atan2(dy, dx);
        return (int) Math.toDegrees(inRads);
    }

    public void setTouchable(boolean touchable) {
        isTouchable = touchable;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        final int xc = rootLayout.getWidth() / 2;
        final int yc = rootLayout.getHeight() / 2;

        int angle = getAngle(xc, yc, event.getX(), event.getY());

        //        double v1 = Math.atan2(x - xc, yc - y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drag = true;
                viewRotation = v.getRotation();
                fingerRotation = angle;
                break;
            case MotionEvent.ACTION_MOVE:
                if(isTouchable) {
                    newFingerRotation = angle;
                    float currentRotation = (float) (viewRotation + newFingerRotation - fingerRotation);
                    if (currentRotation > 0 && currentRotation < 60)
                        v.setRotation(currentRotation);
                }
                break;
            case MotionEvent.ACTION_UP:
                drag = false;
                fingerRotation = newFingerRotation = 0.0f;
                break;
        }
        return true;
    }
}
