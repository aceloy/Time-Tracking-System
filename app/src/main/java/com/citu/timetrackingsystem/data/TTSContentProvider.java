package com.citu.timetrackingsystem.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.citu.timetrackingsystem.data.contracts.BaseContract;
import com.citu.timetrackingsystem.data.contracts.TimeLogContract;
import com.citu.timetrackingsystem.data.contracts.UserContract;
import com.citu.timetrackingsystem.model.TimeLog;
import com.citu.timetrackingsystem.model.User;

public class TTSContentProvider extends ContentProvider {

    private static final UriMatcher mURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mURIMatcher.addURI(UserContract.CONTENT_AUTHORITY, UserContract.PATH_USER, User.CONTENT_PROVIDER_USER);
        mURIMatcher.addURI(UserContract.CONTENT_AUTHORITY, UserContract.PATH_USER + "/#", User.CONTENT_PROVIDER_USER_ID);
        mURIMatcher.addURI(UserContract.CONTENT_AUTHORITY, TimeLogContract.PATH_TIME_LOG, TimeLog.CONTENT_PROVIDER_TIME_LOG);
        mURIMatcher.addURI(UserContract.CONTENT_AUTHORITY, TimeLogContract.PATH_TIME_LOG + "/#", TimeLog.CONTENT_PROVIDER_TIME_LOG_ID);
    }

    private com.citu.timetrackingsystem.data.contracts.TTSDatabaseHelper mTTSDatabaseHelper;

    @Override
    public boolean onCreate() {
        mTTSDatabaseHelper = new com.citu.timetrackingsystem.data.contracts.TTSDatabaseHelper(getContext());
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (mURIMatcher.match(uri)) {
            case User.CONTENT_PROVIDER_USER:
                return UserContract.UserEntry.CONTENT_LIST_TYPE;
            case User.CONTENT_PROVIDER_USER_ID:
                return UserContract.UserEntry.CONTENT_ITEM_TYPE;
            case TimeLog.CONTENT_PROVIDER_TIME_LOG:
                return TimeLogContract.TimeLogEntry.CONTENT_LIST_TYPE;
            case TimeLog.CONTENT_PROVIDER_TIME_LOG_ID:
                return TimeLogContract.TimeLogEntry.CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase sqLiteDatabase = mTTSDatabaseHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (mURIMatcher.match(uri)) {
            case User.CONTENT_PROVIDER_USER:
            case User.CONTENT_PROVIDER_USER_ID:
                cursor = getCursorByUri(sqLiteDatabase, UserContract.UserEntry.TABLE_NAME, uri, projection, selection, selectionArgs, sortOrder);
                break;
            case TimeLog.CONTENT_PROVIDER_TIME_LOG:
            case TimeLog.CONTENT_PROVIDER_TIME_LOG_ID:
                cursor = getCursorByUri(sqLiteDatabase, UserContract.UserEntry.TABLE_NAME, uri, projection, selection, selectionArgs, sortOrder);
                break;
        }
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = mTTSDatabaseHelper.getWritableDatabase();
        long id = 0;
        switch (mURIMatcher.match(uri)) {
            case User.CONTENT_PROVIDER_USER:
                id = insertByUri(sqLiteDatabase, UserContract.UserEntry.TABLE_NAME, uri, contentValues);
                break;
            case TimeLog.CONTENT_PROVIDER_TIME_LOG:
                id = insertByUri(sqLiteDatabase, TimeLogContract.TimeLogEntry.TABLE_NAME, uri, contentValues);
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null, false);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = mTTSDatabaseHelper.getWritableDatabase();
        int numberOfRowsUpdated = 0;
        ContentValues newContentValues = contentValues;
        contentValues.remove(BaseContract.BaseEntry._ID);
        switch (mURIMatcher.match(uri)) {
            case User.CONTENT_PROVIDER_USER:
            case User.CONTENT_PROVIDER_USER_ID:
                newContentValues.remove(UserContract.UserEntry.COLUMN_ID_NUMBER);
                numberOfRowsUpdated = updateByUri(sqLiteDatabase, UserContract.UserEntry.TABLE_NAME, uri, newContentValues, selection, selectionArgs);
                break;
            case TimeLog.CONTENT_PROVIDER_TIME_LOG:
            case TimeLog.CONTENT_PROVIDER_TIME_LOG_ID:
                numberOfRowsUpdated = updateByUri(sqLiteDatabase, TimeLogContract.TimeLogEntry.TABLE_NAME, uri, newContentValues, selection, selectionArgs);
                break;
        }
        if (numberOfRowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null, false);
        }
        return numberOfRowsUpdated;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = mTTSDatabaseHelper.getWritableDatabase();
        int numberOfRowsDeleted = 0;
        switch (mURIMatcher.match(uri)) {
            case User.CONTENT_PROVIDER_USER:
            case User.CONTENT_PROVIDER_USER_ID:
                numberOfRowsDeleted = deleteByUri(sqLiteDatabase, UserContract.UserEntry.TABLE_NAME, uri, selection, selectionArgs);
                break;
            case TimeLog.CONTENT_PROVIDER_TIME_LOG:
            case TimeLog.CONTENT_PROVIDER_TIME_LOG_ID:
                numberOfRowsDeleted = deleteByUri(sqLiteDatabase, TimeLogContract.TimeLogEntry.TABLE_NAME, uri, selection, selectionArgs);
                break;
        }
        if (numberOfRowsDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null, false);
        }
        return numberOfRowsDeleted;
    }

    /**
     * -
     */

    long insertByUri(SQLiteDatabase sqLiteDatabase,
                     String tableName,
                     Uri uri,
                     @Nullable ContentValues contentValues) {
        String selection = BaseContract.BaseEntry._ID + " = ?";
        String[] selectionArgs = new String[]{contentValues.getAsString(BaseContract.BaseEntry._ID)};
        if (query(uri, null, selection, selectionArgs, null).getCount() == 0) {
            ContentValues newContentValues = contentValues;
            newContentValues.remove(BaseContract.BaseEntry._ID);
            return sqLiteDatabase.insert(tableName, null, contentValues);
        } else {
            return update(uri, contentValues, selection, selectionArgs);
        }
    }

    Cursor getCursorByUri(SQLiteDatabase sqLiteDatabase,
                          String tableName,
                          Uri uri,
                          @Nullable String[] projection,
                          @Nullable String selection,
                          @Nullable String[] selectionArgs,
                          @Nullable String sortOrder) {
        long id = 0;
        try {
            id = ContentUris.parseId(uri);
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {

        }
        if (id != 0) {
            selection = BaseColumns._ID + " = ?";
            selectionArgs = new String[]{String.valueOf(id)};
        }
        return sqLiteDatabase.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
    }

    int updateByUri(SQLiteDatabase sqLiteDatabase,
                    String tableName,
                    Uri uri,
                    @Nullable ContentValues contentValues,
                    @Nullable String selection,
                    @Nullable String[] selectionArgs) {
        long id = -1;
        try {
            id = ContentUris.parseId(uri);
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {

        }
        if (id != -1) {
            selection = BaseColumns._ID + " = ?";
            selectionArgs = new String[]{String.valueOf(id)};
        }
        return sqLiteDatabase.update(tableName, contentValues, selection, selectionArgs);
    }

    int deleteByUri(SQLiteDatabase sqLiteDatabase,
                    String tableName,
                    Uri uri,
                    @Nullable String selection,
                    @Nullable String[] selectionArgs) {
        long id = -1;
        try {
            id = ContentUris.parseId(uri);
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {

        }
        if (id != -1) {
            selection = BaseColumns._ID + " = ?";
            selectionArgs = new String[]{String.valueOf(id)};
        }
        return sqLiteDatabase.delete(tableName, selection, selectionArgs);
    }
}
