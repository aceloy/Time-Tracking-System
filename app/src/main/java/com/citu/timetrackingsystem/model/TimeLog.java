package com.citu.timetrackingsystem.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TimeLog implements Parcelable {

    private int id = -1;
    private String userID;
    private String time;
    private String status;
    private String createdDate;
    private String updatedDate;

    public TimeLog() {

    }

    public TimeLog(int id, String userID, String time, String status, String createdDate, String updatedDate) {
        this.id = id;
        this.userID = userID;
        this.time = time;
        this.status = status;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    protected TimeLog(Parcel in) {
        id = in.readInt();
        userID = in.readString();
        time = in.readString();
        status = in.readString();
        createdDate = in.readString();
        updatedDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(userID);
        dest.writeString(time);
        dest.writeString(status);
        dest.writeString(createdDate);
        dest.writeString(updatedDate);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
}
