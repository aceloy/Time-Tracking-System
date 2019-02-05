package com.citu.timetrackingsystem.manager;

import android.content.Context;
import android.database.Cursor;

import com.citu.timetrackingsystem.data.contracts.UserContract;
import com.citu.timetrackingsystem.model.User;

public class SessionManager {

    private static volatile SessionManager mInstance;

    private Context mContext;

    private SharedPreferencesManager mSharedPreferenceManager;

    public SessionManager(Context context) {
        if (mInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class");
        }
        this.mContext = context;
        this.mSharedPreferenceManager = SharedPreferencesManager.getInstance(mContext);
    }

    public static SessionManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SessionManager.class) {
                if (mInstance == null) mInstance = new SessionManager(context);
            }
        }
        return mInstance;
    }

    public boolean isLoggedIn() {
        return mSharedPreferenceManager
                .getSharedPreferences()
                .getBoolean(SharedPreferencesManager.KEY_IS_LOGGED_IN, false);
    }

    public void addSession(int idNumber) {
        mSharedPreferenceManager
                .getSharedPreferencesEditor()
                .putBoolean(SharedPreferencesManager.KEY_IS_LOGGED_IN, true)
                .commit();
        mSharedPreferenceManager
                .getSharedPreferencesEditor()
                .putInt(SharedPreferencesManager.KEY_ID_NUMBER, idNumber)
                .commit();
    }

    public void removeSession() {
        mSharedPreferenceManager
                .getSharedPreferencesEditor()
                .remove(SharedPreferencesManager.KEY_IS_LOGGED_IN)
                .commit();
        mSharedPreferenceManager
                .getSharedPreferencesEditor()
                .remove(SharedPreferencesManager.KEY_ID_NUMBER)
                .commit();
    }

    public void createAdminUser() {
        User adminUser = User.ADMIN_USER;
        if (User.getUserByIDNumberAndPassword(mContext, adminUser.getIdNumber(), adminUser.getPassword()) != null) {
            return;
        }
        mContext
                .getContentResolver()
                .insert(UserContract.UserEntry.CONTENT_URI, adminUser.getContentValues(true));
    }

    public User getUser() {
        int idNumber = mSharedPreferenceManager
                .getSharedPreferences()
                .getInt(SharedPreferencesManager.KEY_ID_NUMBER, -1);

        if (idNumber == -1)
            return null;

        Cursor cursor = mContext.getContentResolver().query(
                UserContract.UserEntry.CONTENT_URI,
                null,
                UserContract.UserEntry.COLUMN_ID_NUMBER + " = ?",
                new String[]{String.valueOf(idNumber)},
                null);

        while (cursor.moveToNext())
            return new User(cursor);

        return null;
    }
}

