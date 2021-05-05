package com.example.atgproject;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class OCRThread extends Thread {

    private static final String TAG = "mTAG";
    public static final String TESS_DATA = "eng.traineddata";
    public String DESTINATION_PATH = "/tesseract/tessdata/";

    private Bitmap bitmap;
    private final Context context;
    private final Handler handler;
    private final TessBaseAPI tessBaseAPI;
    private textRecognitionListener mListener;

    public OCRThread(Context context) {
        this.context = context;
        this.handler = new Handler(context.getMainLooper());
        DESTINATION_PATH = context.getCacheDir() + DESTINATION_PATH;
        tessBaseAPI = new TessBaseAPI(progressValues -> Log.d(TAG, "onProgressValues: " + progressValues));
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setListener(textRecognitionListener mListener) {
        this.mListener = mListener;
    }

    public void startTextRecognition() {

        String LOC_OF_FOLDER = context.getCacheDir() + "/tesseract";
        setUpTesseract(DESTINATION_PATH);
        tessBaseAPI.init(LOC_OF_FOLDER, "eng");
        tessBaseAPI.setImage(bitmap);
        updateOCRText();
    }

    public String getTessData() {
        String utf8Text = tessBaseAPI.getUTF8Text();
        tessBaseAPI.stop();
        return utf8Text;
    }


    public void setUpTesseract(String path) {

        File file = new File(path);
        if (!file.exists()) {
            if (file.mkdirs()) {
                Log.d(TAG, "setUpTesseract: Directory Created");
                copyFileIntoCache();
            } else
                mListener.onTaskFailed("Unable to create directory.");
        }
    }

    private void copyFileIntoCache() {

        try {
            AssetManager assetManager = context.getAssets();
            InputStream in = assetManager.open(TESS_DATA);
            String pathToDataFile = DESTINATION_PATH + TESS_DATA;
            if (!(new File(pathToDataFile)).exists()) {

                OutputStream out = new FileOutputStream(pathToDataFile);
                byte[] buf = new byte[1024];
                int length;
                while ((length = in.read(buf)) > 0) {
                    out.write(buf, 0, length);
                }
                in.close();
                out.close();
                Log.d(TAG, "File Copied " + pathToDataFile);
            }
        } catch (IOException e) {
            mListener.onTaskFailed("Unable to copy files. Reason: " + e.getMessage());
        }
    }

    private void updateOCRText() {
        if (mListener != null) {
            final String text = getTessData();
            handler.post(() -> {
                mListener.onTextRecognised(text);
                mListener = null;
            });
        }
    }

    /**
     * Perform text recognition using Tesserract API.
     */
    @Override
    public void run() {
        super.run();
        startTextRecognition();
    }


    public interface textRecognitionListener {
        void onTextRecognised(String text);

        void onTaskFailed(String exception);
    }
}