package com.example.administrator.dssproject.SDCard;

import android.os.Environment;

public class CheckForSDCard {


    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
    /*public boolean isSDCardPresent() {
        return Environment.getExternalStorageState().equals(

                Environment.MEDIA_MOUNTED);
    }*/


}
