package com.example.atgproject.Activities;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.RotateAnimation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.atgproject.CircleAnimation;
import com.example.atgproject.CustomTouchListeners.DragListener;
import com.example.atgproject.CustomTouchListeners.MultiTouchListener;
import com.example.atgproject.CustomViews.CircleView;
import com.example.atgproject.ModelClass.CirclePoints;
import com.example.atgproject.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

@SuppressLint("ClickableViewAccessibility")
public class CircleActivity extends AppCompatActivity implements MultiTouchListener.rotateListener {

    private static final String TAG = "CIRCLE_TAG";
    public static ArrayList<CirclePoints> circleList = new ArrayList<>();

    float centerX;
    float centerY;
    double radius;
    double startAngle;
    private float prevRotation;
    private float saveAngleVar = 0;

    View pencilPivot;
    TextView textView;
    CheckBox drawCircle;
    View rtDividerPivot;
    CircleView circleView;
    ImageView leftDivider;
    MaterialButton resetBtn;
    CirclePoints circleObject;
    LinearLayout rightDivider;
    CircleAnimation circleAnimation;
    ConstraintLayout rootLayout, compass;
    LinearLayout leftDividerPencil, pencil;

    DragListener dragListener;
    MultiTouchListener multiTouchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);

        pencil = findViewById(R.id.pencil);
        pencilPivot = findViewById(R.id.view1);
        rtDividerPivot = findViewById(R.id.view4);
        textView = findViewById(R.id.textView2);
        rightDivider = findViewById(R.id.linearLayout2);
        leftDivider = findViewById(R.id.imageView1);
        circleView = findViewById(R.id.circleView);
        resetBtn = findViewById(R.id.materialBtn3);
        rootLayout = findViewById(R.id.rootLayout);
        drawCircle = findViewById(R.id.checkbox);
        compass = findViewById(R.id.constraintLayout);
        leftDividerPencil = findViewById(R.id.leftDividerLayout);

        multiTouchListener = new MultiTouchListener(this);
        dragListener = new DragListener(rootLayout);

        // OnTouch Listeners
        compass.setOnTouchListener(multiTouchListener);
        leftDividerPencil.setOnTouchListener(dragListener);

        // OnClickListeners
        resetBtn.setOnClickListener(v -> {
            circleList.clear();
            circleView.invalidate();
        });
        drawCircle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                createCircle();
            else
                moveCompass();
        });

        circleList.clear();
        moveCompass();
    }

    private void moveCompass() {
        dragListener.setTouchable(true);
        multiTouchListener.setScaleEnabled(true);
        multiTouchListener.setRotateEnabled(true);
        multiTouchListener.setTranslateEnabled(true);

        RotateAnimation rotateAnimation = new RotateAnimation(0, 0);
        rotateAnimation.setDuration(100);
        compass.startAnimation(rotateAnimation);

        textView.setText("Compass unlocked");
    }

    /**
     * Draws Circle based to the divider(Compass) position.
     **/
    public void createCircle() {

        // Pivot 1 -- for Divider 1
        int[] coordinates = getViewLocation(rtDividerPivot);
        centerX = coordinates[0];
        centerY = coordinates[1];

        // Pivot 2 -- for Divider 2
        int[] pivotLoc = getViewLocation(pencilPivot);

        // Calculating the distance between two points -- using distance formula
        radius = Math.sqrt(((pivotLoc[0] - centerX) * (pivotLoc[0] - centerX))
                + ((pivotLoc[1] - centerY) * (pivotLoc[1] - centerY)));

        // Calculating the angle from the center of the circle to the point on circle.
        startAngle = Math.toDegrees(Math.atan2(pivotLoc[1] - centerY, pivotLoc[0] - centerX));

        circleObject = new CirclePoints(centerX, centerY, (float) radius, (float) startAngle,
                0, CirclePoints.TYPES.ARC);
        circleList.add(circleObject);

        circleAnimation = new CircleAnimation(circleView, (float) startAngle);

        this.saveAngleVar = 0;
        this.prevRotation = 0;
        dragListener.setTouchable(false);
        multiTouchListener.setScaleEnabled(false);
        multiTouchListener.setRotateEnabled(true);
        multiTouchListener.setTranslateEnabled(false);

        textView.setText("Compass Locked");
    }


    /**
     * returns the coordinates of the current location of a view on the screen.
     **/
    public int[] getViewLocation(View view) {

        Rect rectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
        int[] loc = new int[2];
        view.getLocationOnScreen(loc);
        loc[1] = loc[1] - statusBarHeight;
        return loc;
    }

    @Override
    public void onRotate(View view, float currentRotation) {

        currentRotation = currentRotation + saveAngleVar;
        Log.d(TAG, "current rotation: " + currentRotation);

        circleObject.setSweepedAngle(currentRotation);

        RotateAnimation rotateAnimation = new RotateAnimation(prevRotation, currentRotation, centerX, centerY);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setFillEnabled(true);
        rotateAnimation.setDuration(100);
        view.startAnimation(rotateAnimation);

        circleAnimation.setDuration(100);
        circleAnimation.setNewAngle(currentRotation);
        circleAnimation.start();
        circleView.startAnimation(circleAnimation);

        if (currentRotation < -360 || currentRotation > 360) {
            saveAngleVar = 0;
            circleObject.setTYPE(CirclePoints.TYPES.CIRCLE);
        }

        prevRotation = currentRotation;
    }

    @Override
    public void onRotationEnd(View view) {
        saveAngleVar = prevRotation;
    }
}