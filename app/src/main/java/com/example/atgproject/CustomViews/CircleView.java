package com.example.atgproject.CustomViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.atgproject.ModelClass.CirclePoints;

import static com.example.atgproject.Activities.CircleActivity.circleList;

public class CircleView extends View {

    private static final String TAG = "mTAG";
    private static final int STROKE_WIDTH = 6;

    Paint paint;
    Context context;
    float startAngle;
    float circleAngle;
    private final RectF rectF = new RectF();

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

        startAngle = 0;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < circleList.size(); i++) {

            CirclePoints point = circleList.get(i);

            // Calculating the circle rect (Bounding Box)
            rectF.top = point.getCenterY() - point.getRadius();
            rectF.left = point.getCenterX() - point.getRadius();
            rectF.right = point.getCenterX() + point.getRadius();
            rectF.bottom = point.getCenterY() + point.getRadius();

            if (point.getTYPE() == CirclePoints.TYPES.ARC) {
                canvas.drawArc(rectF, point.getStartAngle(), point.getSweepedAngle(), false, paint);
                circleAngle = 0;
            } else if (point.getTYPE() == CirclePoints.TYPES.CIRCLE)
                canvas.drawCircle(point.getCenterX(), point.getCenterY(), point.getRadius(), paint);
            else if (i == circleList.size() - 1) {
                canvas.drawArc(rectF, startAngle, circleAngle, false, paint);
            }

            rectF.left = point.getCenterX();
            rectF.top = point.getCenterY();
            rectF.bottom = point.getCenterY();
            rectF.right = point.getCenterX();
            canvas.drawRect(rectF, paint);
        }
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    public float getCircleAngle() {
        return circleAngle;
    }

    public void setCircleAngle(float circleAngle) {
        this.circleAngle = circleAngle;
    }
}
