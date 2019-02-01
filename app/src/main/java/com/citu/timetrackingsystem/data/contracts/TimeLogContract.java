package com.citu.timetrackingsystem.data.contracts;

import android.content.ContentResolver;
import android.net.Uri;

import com.citu.timetrackingsystem.BuildConfig;

public class TimeLogContract {

    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_TIME_LOG = "time_log";

    public static class TimeLogEntry extends BaseContract.BaseEntry {

        public static final Uri CONTENT_URI =  BASE_CONTENT_URI.buildUpon().appendPath(PATH_TIME_LOG).build();
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TIME_LOG;
        public static final String CONTENT_ITEM_TYPE =  ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TIME_LOG;
        public static final int CONTENT_PROVIDER_TIME_LOG = 1003;
        public static final int CONTENT_PROVIDER_TIME_LOG_ID = 1004;
        public static final String TABLE_NAME = "time_log";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_TIME = "time";
    }
}
