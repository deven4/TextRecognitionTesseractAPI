package com.example.atgproject.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atgproject.Adapter.FlickrImageAdapter;
import com.example.atgproject.ModelClass.Photo;
import com.example.atgproject.R;
import com.example.atgproject.Utils.ErrorBottomSheet;
import com.example.atgproject.ViewModel.FlickrImagesViewModel;

import java.util.ArrayList;
import java.util.List;

public class FlickrImagesActivity extends AppCompatActivity {

    private static final String TAG = "mTAG";
    ProgressBar progressBar;
    RecyclerView recyclerView;
    FlickrImageAdapter imageAdapter;
    FlickrImagesViewModel viewModelClass;
    List<Photo> photoList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flickr_images);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

        setupRecyclerView();
        attachViewModelClass();
    }

    /**
     * Attaching ViewModel Class to this activity, so that activity can communicate with the viewModel
     * class.
     **/
    private void attachViewModelClass() {
        viewModelClass = ViewModelProviders.of(this).get(FlickrImagesViewModel.class);
        viewModelClass.getJSONData().observe(this, listLiveData -> {

            switch (listLiveData.status) {
                case SUCCESS:
                    if (listLiveData.data != null) {
                        photoList.clear();
                        photoList.addAll(listLiveData.data.getPhotos().getPhoto());
                    } else
                        showErrorDialog("Something went wrong. Please try again later.");
                    break;
                case ERROR:
                    showErrorDialog(listLiveData.message);
                    break;
            }

            progressBar.setVisibility(View.GONE);
            imageAdapter.notifyDataSetChanged();
            Log.d(TAG, "onResponse: " + photoList);
        });
    }

    private void showErrorDialog(String msg) {
        ErrorBottomSheet errorBottomSheet = new ErrorBottomSheet(msg);
        errorBottomSheet.show(getSupportFragmentManager(), "");
    }

    /**
     * Setting up the recycler view adapter, by populating with an empty photoList.
     **/
    private void setupRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2,
                LinearLayoutManager.VERTICAL, false);
        imageAdapter = new FlickrImageAdapter(this, photoList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(imageAdapter);
    }
}
