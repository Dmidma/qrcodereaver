package com.example.android.qrcodereaver;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.example.android.qrcodereaver.utils.FileStorageUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by dmidma on 12/3/17.
 */

public class RecordFile {

    // This class will write to and read from a private file where the
    // full path of the images will be kept

    // name of the file
    private static final String RECORD_FILE_NAME = "qr_images_paths.txt";

    private Context mContext;
    private File mFile;
    private ArrayList<String> mPaths;

    public RecordFile(Context context) {
        mContext = context;
        mFile = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), RECORD_FILE_NAME);
        mPaths = new ArrayList<String>();
    }

    public boolean checkFile() {
        return (mFile == null)? false: true;
    }

    // only add the path to the list
    public void addPathToFile(String path) {
        mPaths.add(path);
    }


    // Loop over the lines of the file and get an array of paths
    public ArrayList<String> getPathsInFile() throws IOException {

        // reading the content of the file
        // the list will be erased
        mPaths.clear();

        FileInputStream fis = mContext.openFileInput(mFile.getName());
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            // make sure to remove white spaces
            mPaths.add(line.trim());
        }
        br.close();
        return mPaths;
    }

    // loop over the list and set the paths
    public void setPathsToFile() throws IOException {
        FileOutputStream outputStream = mContext.openFileOutput(mFile.getName(), mContext.MODE_PRIVATE);
        for (String path : mPaths) {
            outputStream.write((path + "\n").getBytes());
        }
        outputStream.close();
    }


    public String dumpAllPaths() throws IOException {
        FileInputStream fis = mContext.openFileInput(mFile.getName());
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        br.close();
        return sb.toString();

    }

    // clear the file and the list
    public void emptyFile() throws IOException {
        mPaths.clear();
        FileOutputStream outputStream = mContext.openFileOutput(mFile.getName(), mContext.MODE_PRIVATE);
        outputStream.write("".getBytes());
        outputStream.close();
    }


}
