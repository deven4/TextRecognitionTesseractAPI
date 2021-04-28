package com.example.atgproject;

import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.example.atgproject.CustomViews.CircleView;

public class CircleAnimation extends Animation {

    float oldAngle;
    float newAngle;
    private final CircleView circleView;

    public CircleAnimation(CircleView circleView, float startAngle, float finalAngle) {
        this.newAngle = finalAngle;
        this.circleView = circleView;
        this.circleView.setStartAngle(startAngle);
        this.oldAngle = circleView.getCircleAngle();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {

        float angle = oldAngle + ((newAngle - oldAngle)) * interpolatedTime;
        circleView.setCircleAngle(angle);
        circleView.invalidate();
    }
}
