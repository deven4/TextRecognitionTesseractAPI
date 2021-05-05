package com.example.atgproject.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.atgproject.ModelClass.JSONObject;
import com.example.atgproject.Repository.AppRepository;
import com.example.atgproject.Utils.Resources;

public class FlickrImagesViewModel extends AndroidViewModel {

    private final MutableLiveData<Resources<JSONObject>> jsonObject;

    public FlickrImagesViewModel(@NonNull Application application) {
        super(application);
        AppRepository appRepository = AppRepository.getInstance();
        jsonObject = appRepository.getPhotos();
    }

    public MutableLiveData<Resources<JSONObject>> getJSONData() {
        return jsonObject;
    }
}
