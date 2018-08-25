package com.example.administrator.dssproject.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {
    public static final String DEFAULT_BOX_ID = "";

    private static final String SHARED_PREF_NAME = "myMachingCode";
    private static final String KEY_BOX_ID_PREF = "machingCode";
    private static final String KEY_APP_STATUS_PREF = "appStatus";

    public static void saveMatchingCode(Context context, String matchingCode) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_BOX_ID_PREF, matchingCode);
        editor.apply();
    }

    public static String getMatchingCode(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_BOX_ID_PREF, DEFAULT_BOX_ID);
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
