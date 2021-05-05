package com.example.atgproject.Activities;

import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.atgproject.ModelClass.Photo;
import com.example.atgproject.R;

import java.util.Objects;

public class ImageViewerActivity extends AppCompatActivity {

    ImageView imageView;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        toolbar = findViewById(R.id.toolbar2);
        imageView = findViewById(R.id.imageView4);

        // here we are initializing
        // fade animation.
        Fade fade = new Fade();
        View decor = getWindow().getDecorView();

        // here also we are excluding status bar,
        // action bar and navigation bar from animation.
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        // below methods are used for adding
        // enter and exit transition.
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Photo photo = getIntent().getParcelableExtra("PHOTO");

        if (photo != null) {
            Glide.with(this).load(photo.getUrl_s()).into(imageView);
            Objects.requireNonNull(getSupportActionBar()).setTitle(photo.getTitle());
        }
    }
}
