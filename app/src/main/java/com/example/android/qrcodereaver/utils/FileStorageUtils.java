package com.example.android.qrcodereaver.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class to handle both internal and external storage
 */

public class FileStorageUtils {

    public static final int INTERNAL_DIR = 1;
    public static final int INTERNAL_CACHE_DIR = 2;

    public static final int EXTERNAL_PUBLIC_DIR = 3;
    public static final int EXTERNAL_PRIVATE_DIR = 4;
    public static final int EXTERNAL_CACHE_DIR = 5;


    /**
     *
     * @param context
     * @param filename Must contain both prefix and suffix
     * @param type
     * @param dirType
     * @param temp
     * @return
     */
    public static File getFile(Context context, String filename, int type, String dirType, boolean temp) throws IOException {
        File storageDir = getCorrectDir(context, type, dirType);

        File file;
        if (temp) {
            String [] prefixSuffix = filename.split(".");
            file = File.createTempFile(prefixSuffix[0], prefixSuffix[1], storageDir);
        } else {
            file = new File(storageDir, filename);
        }
        return file;
    }


    /**
     *
     * @param context
     * @param filename
     * @param content
     */
    public static void writeFile(Context context, String filename, String content) {


        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(filename, context.MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.close();
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Simple Read File function
     * @param file
     * @return
     * @throws IOException
     */
    public static String readFile(File file) throws IOException {

        StringBuilder text = new StringBuilder();

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            text.append(line);
            text.append("\n");
        }
        br.close();

        return text.toString();
    }


    public static File getCorrectDir(Context context, int type, String dirType) {

        switch (type) {
            case INTERNAL_DIR:
                return context.getFilesDir();
            case INTERNAL_CACHE_DIR:
                return context.getCacheDir();
            case EXTERNAL_PUBLIC_DIR:
                // Suppose that the dirType is null or one of the Environment strings
                return Environment.getExternalStoragePublicDirectory(dirType);
            case EXTERNAL_PRIVATE_DIR:
                return context.getExternalFilesDir(dirType);
            case EXTERNAL_CACHE_DIR:
                return context.getExternalCacheDir();
            default:
                return null;
        }
    }



}
