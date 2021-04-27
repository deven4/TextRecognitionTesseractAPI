package com.example.atgproject.CustomViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.atgproject.CircleActivity;
import com.example.atgproject.CirclePoints;

public class CircleView extends View {

    private static final String TAG = "mTAG";
    Paint paint;
    float radius;
    Context context;
    float centerX, centerY;


    public CircleView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Log.d(TAG, "init: ");
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (CirclePoints point : CircleActivity.circleList) {
            canvas.drawCircle(point.getCenterX(), point.getCenterY(), point.getRadius(), paint);
        }
    }

}
