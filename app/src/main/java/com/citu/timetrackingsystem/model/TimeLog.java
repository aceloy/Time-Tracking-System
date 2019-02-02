package com.citu.timetrackingsystem.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.citu.timetrackingsystem.data.contracts.TimeLogContract;
import com.citu.timetrackingsystem.helper.DateHelper;

public class TimeLog implements Parcelable {

    private int id = -1;
    private int idNumber;
    private String timeIn;
    private String timeOut;
    private String status;
    private String createdDate;
    private String updatedDate;

    // Content Providers
    public static final int CONTENT_PROVIDER_TIME_LOG = 1003;
    public static final int CONTENT_PROVIDER_TIME_LOG_ID = 1004;

    // Loaders
    public static final int LOADER_TIME_LOGS = 2002;

    public TimeLog() {

    }

    public TimeLog(int id, int idNumber, String timeIn, String timeOut, String status, String createdDate, String updatedDate) {
        this.id = id;
        this.idNumber = idNumber;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.status = status;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }


    public TimeLog(Cursor cursor) {
        id = cursor.getInt(cursor.getColumnIndex(TimeLogContract.TimeLogEntry._ID));
        idNumber = cursor.getInt(cursor.getColumnIndex(TimeLogContract.TimeLogEntry.COLUMN_ID_NUMBER));
        timeIn = cursor.getString(cursor.getColumnIndex(TimeLogContract.TimeLogEntry.COLUMN_TIME_IN));
        timeOut = cursor.getString(cursor.getColumnIndex(TimeLogContract.TimeLogEntry.COLUMN_TIME_OUT));
        status = cursor.getString(cursor.getColumnIndex(TimeLogContract.TimeLogEntry.COLUMN_STATUS));
        createdDate = cursor.getString(cursor.getColumnIndex(TimeLogContract.TimeLogEntry.COLUMN_CREATED_DATE));
        updatedDate = cursor.getString(cursor.getColumnIndex(TimeLogContract.TimeLogEntry.COLUMN_UPDATED_DATE));
    }

    protected TimeLog(Parcel in) {
        id = in.readInt();
        idNumber = in.readInt();
        timeIn = in.readString();
        timeOut = in.readString();
        status = in.readString();
        createdDate = in.readString();
        updatedDate = in.readString();
    }

    public static final Creator<TimeLog> CREATOR = new Creator<TimeLog>() {
        @Override
        public TimeLog createFromParcel(Parcel in) {
            return new TimeLog(in);
        }

        @Override
        public TimeLog[] newArray(int size) {
            return new TimeLog[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(idNumber);
        parcel.writeString(timeIn);
        parcel.writeString(timeOut);
        parcel.writeString(status);
        parcel.writeString(createdDate);
        parcel.writeString(updatedDate);
    }

    public String getFormattedTimeIn() {
        String timeIn = getTimeIn();
        if (timeIn == null)
            return null;

        return DateHelper.getDateFormattedInTimeAM_PM(DateHelper.getDateFromISO8601(timeIn));
    }

    public String getFormattedTimeOut() {
        String timeOut = getTimeOut();
        if (timeOut == null)
            return null;

        return DateHelper.getDateFormattedInTimeAM_PM(DateHelper.getDateFromISO8601(timeOut));
    }

    public String getFormattedCreatedDate() {
        String createdDate = getCreatedDate();
        if (createdDate == null)
            return null;

        return DateHelper.getDateFormattedInMMddyy1(DateHelper.getDateFromISO8601(createdDate));
    }
}
