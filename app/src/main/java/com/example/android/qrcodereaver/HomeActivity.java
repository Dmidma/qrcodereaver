package com.example.android.qrcodereaver;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    // for debugging
    private final static String TAG = HomeActivity.class.getSimpleName();

    static final int REQUEST_IMAGE_CAPTURE = 1;


    private FloatingActionButton mFABCameraButton;

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the floating button and set its action
        mFABCameraButton = (FloatingActionButton) findViewById(R.id.fab_open_camera);
        // change the FAB color
        // mFABCameraButton.setBackgroundColor(Color.parseColor("@color/"));
        mFABCameraButton.setOnClickListener(this);


        mImageView = (ImageView) findViewById(R.id.iv_camera);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.fab_open_camera:
                Toast.makeText(HomeActivity.this, TAG + ": FAB Camera clicked.", Toast.LENGTH_LONG).show();
                dispatchTakePictureIntent();
                return;

        }

    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }
}
