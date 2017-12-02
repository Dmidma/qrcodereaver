package com.example.android.qrcodereaver;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    // for debugging
    private final static String TAG = HomeActivity.class.getSimpleName();


    private FloatingActionButton mFABCameraButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the floating button and set its action
        mFABCameraButton = (FloatingActionButton) findViewById(R.id.fab_open_camera);
        mFABCameraButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.fab_open_camera:
                Toast.makeText(HomeActivity.this, TAG + ": FAB Camera clicked.", Toast.LENGTH_LONG).show();
                return;

        }

    }
}
