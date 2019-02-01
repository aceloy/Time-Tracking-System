package com.citu.timetrackingsystem.data.contracts;

import android.provider.BaseColumns;

public class BaseContract {

    public static class BaseEntry implements BaseColumns {

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_STATUS  = "status";
        public static final String COLUMN_CREATED_DATE  = "created_date";
        public static final String COLUMN_UPDATED_DATE  = "updated_date";
    }
}

