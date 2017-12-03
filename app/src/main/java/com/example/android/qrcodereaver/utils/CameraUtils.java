package com.example.android.qrcodereaver.utils;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class CameraUtils {

    private static Context mContext;
    private static Intent takePictureIntent;

    private static final String FILE_PROVIDER_AUTHORITY = "com.example.android.qrcodereaver.fileprovider";


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
    public Bitmap finishCameraForPhoto() {

        Bitmap mimi = BitmapFactory.decodeFile(mTmpFile.getAbsolutePath());

        try {
            File fifi = FileStorageUtils.getFile(mContext, "File.txt", FileStorageUtils.EXTERNAL_PRIVATE_DIR, Environment.DIRECTORY_PICTURES);
        } catch (IOException ee) {
            ee.printStackTrace();
        }



        /*
        Bitmap resultImage = BitmapUtils.resamplePic(mContext, mTmpFile.getAbsolutePath());

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mTmpFile.getAbsolutePath());
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        mContext.sendBroadcast(mediaScanIntent);
        */
        return mimi;
    }


    private void deleteTmpFile() {
        if (mTmpFile != null) {
            boolean deleted = mTmpFile.delete();
            if (!deleted) {
                Toast.makeText(mContext, "Error Finding Image", Toast.LENGTH_LONG).show();
            }
        }
    }

}
