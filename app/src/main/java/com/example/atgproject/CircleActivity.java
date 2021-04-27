package com.example.atgproject;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.atgproject.CustomViews.CircleView;
import com.example.atgproject.DragListener.DragListener;
import com.example.atgproject.MultiTouchListener.MultiTouchListener;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class CircleActivity extends AppCompatActivity {

    private static final String TAG = "CIRCLE_TAG";
    public static ArrayList<CirclePoints> circleList = new ArrayList<>();

    float centerX;
    float centerY;
    double radius;
    float movedX, movedY;
    boolean isDragged = false;

    View divider1Pivot;
    View divider2Pivot;
    TextView textView;
    CircleView circleView;
    LinearLayout linearLayout;
    ImageView divider1;
    LinearLayout divider2;
    MaterialButton resetBtn, drawCircle;
    ConstraintLayout dividerLayout, rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);

        divider1Pivot = findViewById(R.id.view1);
        divider2Pivot = findViewById(R.id.view4);
        textView = findViewById(R.id.textView2);
        divider2 = findViewById(R.id.linearLayout2);
        divider1 = findViewById(R.id.imageView1);
        circleView = findViewById(R.id.circleView);
        resetBtn = findViewById(R.id.materialBtn3);
        rootLayout = findViewById(R.id.rootLayout);
        drawCircle = findViewById(R.id.materialBtn4);
        linearLayout = findViewById(R.id.linearLayout);
        dividerLayout = findViewById(R.id.constraintLayout);


        // OnTouch Listeners
        divider2.setOnTouchListener(new DragListener(rootLayout));
        dividerLayout.setOnTouchListener(new MultiTouchListener(true, true, true));


        // OnClickListeners
        resetBtn.setOnClickListener(v -> {
            circleList.clear();
            circleView.invalidate();
        });

        drawCircle.setOnClickListener(v -> createCircle());
    }

    /**
     * Draws Circle based to the divider(Compass) position.
     **/
    public void createCircle() {

        // Pivot 1 -- for Divider 1
        int[] coordinates = getViewLocation(divider1Pivot);
        centerX = coordinates[0];
        centerY = coordinates[1];

        // Pivot 2 -- for Divider 2
        int[] pivotLoc = getViewLocation(divider2Pivot);

        // Calculating the distance between two points -- using distance formula
        radius = Math.sqrt(((pivotLoc[0] - centerX) * (pivotLoc[0] - centerX))
                + ((pivotLoc[1] - centerY) * (pivotLoc[1] - centerY)));


        circleList.add(new CirclePoints(centerX, centerY, (float) radius));
        circleView.invalidate();

        RotateAnimation rotateAnimation = new RotateAnimation(0f, 360f, centerX, centerY);
        rotateAnimation.setDuration(3000);
        dividerLayout.setAnimation(rotateAnimation);
        dividerLayout.startAnimation(rotateAnimation);
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
        Log.d(TAG, "new loc[1]: " + loc[1]);
        return loc;
    }


//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//
//        switch (event.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN:
//                isDragged = true;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                movedX = event.getX();
//                movedY = event.getY();
//
//                if (isDragged) {
//                    // now move  the view to that position
//                    dividerLayout.setX(dividerLayout.getX() + movedX);
//                    dividerLayout.setY(dividerLayout.getY() + movedY);
//                }
//                break;
//            case MotionEvent.ACTION_POINTER_UP:
//            case MotionEvent.ACTION_UP:
//                isDragged = false;
//                break;
//        }
//        return true;
//    }

    @Override
    protected void onStop() {
        super.onStop();
        circleList.clear();
    }
}