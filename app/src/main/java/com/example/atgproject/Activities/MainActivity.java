package com.example.atgproject.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.atgproject.R;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    MaterialButton paintBtn, circleBtn,
            flickrBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paintBtn = findViewById(R.id.paintBtn);
        circleBtn = findViewById(R.id.circleBtn);
        flickrBtn = findViewById(R.id.flickrImgBtn);

        paintBtn.setOnClickListener(v -> startActivity(new Intent(this, PaintActivity.class)));
        circleBtn.setOnClickListener(v -> startActivity(new Intent(this,CircleActivity.class)));
        flickrBtn.setOnClickListener(v -> startActivity(new Intent(this,FlickrImagesActivity.class)));
    }
}
