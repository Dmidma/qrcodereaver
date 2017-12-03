package com.example.android.qrcodereaver;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;

    private TextView mTextViewContent;
    private TextView mTextViewNumber;
    private EditText mEditTextContent;
    private Button mButtonWrite;

    private RecordFile recordFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mContext = TestActivity.this;

        mTextViewContent = (TextView) findViewById(R.id.tv_content);
        mTextViewNumber = (TextView) findViewById(R.id.tv_number);
        mEditTextContent = (EditText) findViewById(R.id.et_content);
        mButtonWrite = (Button) findViewById(R.id.bt_write);
        mButtonWrite.setOnClickListener(this);

        recordFile = new RecordFile(mContext);
    }


    @Override
    public void onClick(View view) {

        try {

            ArrayList<String> arr = recordFile.getPathsInFile();
            mTextViewNumber.setText("Size = " + arr.size());
            mTextViewContent.setText(arr.get(0));

        } catch (IOException e) {
            Toast.makeText(mContext, "Couldn't read file", Toast.LENGTH_LONG).show();
        }





    }
}
