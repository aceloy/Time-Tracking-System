package com.citu.timetrackingsystem.data.contracts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.citu.timetrackingsystem.BuildConfig;

public class TTSDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = BuildConfig.APPLICATION_ID + ".db";
    private static final int DATABASE_VERSION = 1;

    /**
     * - Create
     */

    public static final String CREATE_TABLE_USER = "CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + "("
            + UserContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + UserContract.UserEntry.COLUMN_ID_NUMBER + " INTEGER, "
            + UserContract.UserEntry.COLUMN_PASSWORD + " VARCHAR(100), "
            + UserContract.UserEntry.COLUMN_NAME + " VARCHAR(100), "
            + UserContract.UserEntry.COLUMN_AGE + " TEXT, "
            + UserContract.UserEntry.COLUMN_GENDER + " VARCHAR(50), "
            + UserContract.UserEntry.COLUMN_ADDRESS + " VARCHAR(50), "
            + UserContract.UserEntry.COLUMN_ROLE + " VARCHAR(50), "
            + BaseContract.BaseEntry.COLUMN_STATUS + " VARCHAR(50), "
            + BaseContract.BaseEntry.COLUMN_CREATED_DATE + " VARCHAR(50), "
            + BaseContract.BaseEntry.COLUMN_UPDATED_DATE + " VARCHAR(50));";

    public static final String CREATE_TABLE_TIME_LOG = "CREATE TABLE " + TimeLogContract.TimeLogEntry.TABLE_NAME + "("
            + TimeLogContract.TimeLogEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TimeLogContract.TimeLogEntry.COLUMN_USER_ID + " INTEGER, "
            + TimeLogContract.TimeLogEntry.COLUMN_TIME + " VARCHAR(100), "
            + BaseContract.BaseEntry.COLUMN_STATUS + " VARCHAR(50), "
            + BaseContract.BaseEntry.COLUMN_CREATED_DATE + " VARCHAR(50), "
            + BaseContract.BaseEntry.COLUMN_UPDATED_DATE + " VARCHAR(50));";

    /**
     * - Delete
     */

    public static final String DELETE_TABLE_USER = "DROP TABLE IF EXISTS " + UserContract.UserEntry.TABLE_NAME;
    public static final String DELETE_TABLE_TIME_LOG = "DROP TABLE IF EXISTS " + TimeLogContract.TimeLogEntry.TABLE_NAME;

    public TTSDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_TIME_LOG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE_USER);
        db.execSQL(DELETE_TABLE_TIME_LOG);
        onCreate(db);
    }
}
