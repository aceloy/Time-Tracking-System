package com.citu.timetrackingsystem.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesManager {

    private static volatile SharedPreferencesManager mInstance;

    private Context mContext;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mSharedPreferencesEditor;


    public static final String KEY_ID_NUMBER = "KEY_ID_NUMBER";
    public static final String KEY_IS_LOGGED_IN = "KEY_IS_LOGGED_IN";

    public SharedPreferencesManager(Context context) {
        if (mInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class");
        }
        this.mContext = context;
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.mSharedPreferencesEditor = this.mSharedPreferences.edit();
    }

    public static SharedPreferencesManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SharedPreferencesManager.class) {
                if (mInstance == null) mInstance = new SharedPreferencesManager(context);
            }
        }
        return mInstance;
    }

    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    public SharedPreferences.Editor getSharedPreferencesEditor() {
        return mSharedPreferencesEditor;
    }
}
