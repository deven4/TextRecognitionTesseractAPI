package com.example.atgproject.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.atgproject.ModelClass.JSONObject;
import com.example.atgproject.RetrofitAPI.MyWebService;
import com.example.atgproject.Utils.Resources;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Singleton Class
 **/
public class AppRepository {

    private static final String TAG = "mTAG";

    private static AppRepository singleInstance = null;

    public static AppRepository getInstance() {
        if (singleInstance == null)
            singleInstance = new AppRepository();
        return singleInstance;
    }

    public MutableLiveData<Resources<JSONObject>> getPhotos() {

        MutableLiveData<Resources<JSONObject>> mPhotoList = new MutableLiveData<>();
        MyWebService myWebService = MyWebService.retrofit.create(MyWebService.class);
        Call<JSONObject> flickrData = myWebService.getFlickrData();

        flickrData.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Error: " + response.code());
                    return;
                }
                // If Response code is OK, we will get the data from response
                // object.
                assert response.body() != null;
                mPhotoList.setValue(Resources.success(response.body()));
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                t.printStackTrace();
                mPhotoList.setValue(Resources.error(t.getLocalizedMessage(), null));
            }
        });
        return mPhotoList;
    }
}
