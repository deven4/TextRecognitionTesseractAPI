package com.example.atgproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.atgproject.CustomViews.CustomLayout;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements OCRThread.textRecognitionListener {

    private static final String TAG = "mTAG";

    Button myBtn;
    Bitmap bitmap;
    CustomLayout customView;
    MaterialButton resetBtn;
    ConstraintLayout constraintLayout;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myBtn = findViewById(R.id.myBtn);
//        imageView = findViewById(R.id.img);
        resetBtn = findViewById(R.id.resetBtn);
        customView = findViewById(R.id.customView);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        constraintLayout = findViewById(R.id.constraintLay);
        progressDialog.setMessage(getString(R.string.progress_message_ocr));

        myBtn.setOnClickListener(v -> startOCRThread());

        resetBtn.setOnClickListener(v -> {
            customView.resetPath();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 102 && data != null) {
            Uri pickedImage = data.getData();
            Log.d(TAG, "onActivityResult: " + pickedImage);
            bitmap = useImage(pickedImage);
            Log.d(TAG, "onActivityResult: " + bitmap);
        }
    }

    public void startOCRThread() {

        bitmap = capture();
        if (bitmap != null) {
            progressDialog.show();
            OCRThread ocrThread = new OCRThread(this);
            ocrThread.setBitmap(bitmap);
            ocrThread.setListener(this);
            ocrThread.start();
        } else
            new ErrorBottomSheet("Bitmap is NULL. Something went wrong.").
                    show(getSupportFragmentManager(), "");
    }


    private Bitmap useImage(Uri uri) {

        try {
            return MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (IOException e) {
            ErrorBottomSheet bottomSheet = new ErrorBottomSheet("Not able to get " +
                    "convert image into bitmaps. Please try another Image.");
            bottomSheet.show(getSupportFragmentManager(), "");
        }
        return null;
    }


    private Bitmap capture() {

//        ViewGroup actContent = findViewById(android.R.id.content);
        View root  = getWindow().getDecorView();
        customView.setDrawingCacheEnabled(true);
        Bitmap result = Bitmap.createBitmap(customView.getDrawingCache());
        customView.setDrawingCacheEnabled(false);

//        customView.setDrawingCacheEnabled(true);
//        Rect src = new Rect(0, customView.getHeight(), customView.getWidth(), customView.getHeight());
//        Rect dest = new Rect(0, 0, result.getWidth(), result.getHeight());
//        canvas.drawBitmap(customView.getDrawingCache(), src, dest, null);
//        customView.setDrawingCacheEnabled(false);
//        imageView.setImageBitmap(result);

        return result;
    }

    @Override
    public void onTextRecognised(String text) {
        progressDialog.dismiss();
        PopUpDialog popUpDialog = new PopUpDialog(text);
        popUpDialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void onTaskFailed(String exception) {
        progressDialog.dismiss();
        ErrorBottomSheet bottomSheet = new ErrorBottomSheet(exception);
        bottomSheet.show(getSupportFragmentManager(), "");
    }

    public void drawCircle(View view) {
        startActivity(new Intent(MainActivity.this,CircleActivity.class));
    }
}