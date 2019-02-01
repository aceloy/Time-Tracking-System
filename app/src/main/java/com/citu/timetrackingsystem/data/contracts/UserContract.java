package com.citu.timetrackingsystem.data.contracts;

import android.content.ContentResolver;
import android.net.Uri;

import com.citu.timetrackingsystem.BuildConfig;

public class UserContract {

    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_USER = "user";

    public static class UserEntry extends BaseContract.BaseEntry {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_ID_NUMBER = "id_number";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_ADDRESS = "address";
    }
}
