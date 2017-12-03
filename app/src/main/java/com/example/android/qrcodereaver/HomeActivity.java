package com.example.android.qrcodereaver;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.qrcodereaver.utils.CameraUtils;
import com.example.android.qrcodereaver.utils.FileStorageUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    // for debugging
    private final static String TAG = HomeActivity.class.getSimpleName();

    private Context mContext;




    private FloatingActionButton mFABCameraButton;

    // testing
    private ImageView mImageView;
    private TextView mTextViewImagePath;
    private TextView mTextViewImageName;
    private CameraUtils mCamera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // set context
        mContext = HomeActivity.this;

        // get the floating button and set its action
        mFABCameraButton = (FloatingActionButton) findViewById(R.id.fab_open_camera);
        mFABCameraButton.setOnClickListener(this);

        ((Button) findViewById(R.id.bt_test)).setOnClickListener(this);

        // testing
        mImageView = (ImageView) findViewById(R.id.iv_camera);
        mTextViewImagePath = (TextView) findViewById(R.id.tv_image_path);
        mTextViewImageName = (TextView) findViewById(R.id.tv_image_name);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.fab_open_camera:
                mCamera = CameraUtils.getOneInstance(mContext);
                startCamera(CameraUtils.REQUEST_TAKE_PHOTO);

                return;
        }
    }

    private void startCamera(int typeCameraUsage) {
        if (mCamera != null) {
            switch (typeCameraUsage) {
                case CameraUtils.REQUEST_CAMERA_THUMBNAIL:
                    startActivityForResult(mCamera.startCameraForThumbnail(), typeCameraUsage);
                    return;
                case CameraUtils.REQUEST_TAKE_PHOTO:
                    startActivityForResult(mCamera.startCameraForPhoto(), typeCameraUsage);
                    return;
            }
        }
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {


            File imageFile = createImageFile();

            if (imageFile != null) {
                Uri photoUri = FileProvider.getUriForFile(mContext,
                        "com.example.android.qrcodereaver.homeactivity",
                        imageFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                Toast.makeText(HomeActivity.this, TAG + ": FAB Camera clicked.", Toast.LENGTH_LONG).show();
                //startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check that the result was from the CAMERA and the result is OK
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case CameraUtils.REQUEST_CAMERA_THUMBNAIL:
                    mImageView.setImageBitmap(mCamera.finishCameraForThumbnail(data));
                    return;
                case CameraUtils.REQUEST_TAKE_PHOTO:
                    Bitmap image = mCamera.finishCameraForPhoto();
                    mImageView.setImageBitmap(image);
                    return;
            }
        }
    }

    private File createImageFile() {
        // collision-resistant
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String prefix = "JPEG_" + timeStamp + "_";
        try {
            File imageFile =  FileStorageUtils.getTempFile(
                    mContext,
                    prefix,
                    ".jpg",
                    FileStorageUtils.EXTERNAL_PRIVATE_DIR,
                    Environment.DIRECTORY_PICTURES);
            mTextViewImagePath.setText(imageFile.getAbsolutePath());
            return imageFile;
        } catch (IOException e) {
            Log.e(TAG, "Unable to create the file");
        }

        return null;
    }
}
