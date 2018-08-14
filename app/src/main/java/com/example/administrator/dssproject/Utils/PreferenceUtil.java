package com.example.administrator.dssproject.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {
    public static final int DEFAULT_BOX_ID = 0;

    private static final String SHARED_PREF_NAME = "myBox";
    private static final String KEY_BOX_ID_PREF = "boxId";
    private static final String KEY_APP_STATUS_PREF = "appStatus";

    public static void saveBoxId(Context context, int boxId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_BOX_ID_PREF, boxId);
        editor.apply();
    }

    public static int getBoxId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_BOX_ID_PREF, DEFAULT_BOX_ID);
    }

    public static void saveAppStatus(Context context, boolean appStatus) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(KEY_APP_STATUS_PREF, appStatus);
        editor.apply();
    }

    public static boolean getAppStatus(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_APP_STATUS_PREF, false);
    }
}
