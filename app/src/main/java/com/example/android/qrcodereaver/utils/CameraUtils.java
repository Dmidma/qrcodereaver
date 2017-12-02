package com.example.android.qrcodereaver.utils;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class CameraUtils {

    private static Context mContext;
    private static Intent takePictureIntent;

    private static final String FILE_PROVIDER_AUTHORITY = "";


    private File mTmpFile;


    protected CameraUtils() {
    }

    // singleton pattern
    private static CameraUtils oneInstance = null;
    public static CameraUtils getOneInstance(Context context) {
        if (oneInstance == null) {
            mContext = context;
            takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(context.getPackageManager()) == null) {
                Toast.makeText(context, "No App for Camera", Toast.LENGTH_LONG).show();
                return null;
            }
            oneInstance = new CameraUtils();
        }
        return oneInstance;
    }





    public static final int REQUEST_CAMERA_THUMBNAIL = 1;
    /*
        Usage in Activity:
        Intent takePictureIntent = CameraUtils.startCameraForThumbnail
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(takePictureIntent, CameraUtils.REQUEST_CAMERA_THUMBNAIL);
     */
    public Intent startCameraForThumbnail() {
        return takePictureIntent;
    }
    public Bitmap finishCameraForThumbnail(Intent data) {
        Bundle extras = data.getExtras();
        // get thumbnail
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        return imageBitmap;
    }

    public static final int REQUEST_TAKE_PHOTO = 2;
    public Intent startCameraForPhoto() {
        mTmpFile = null;
        try {
            mTmpFile = BitmapUtils.createTempImageFile(mContext);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (mTmpFile != null) {
            Uri photoUri = FileProvider.getUriForFile(mContext, FILE_PROVIDER_AUTHORITY, mTmpFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            return takePictureIntent;
        }
        return null;
    }


}
