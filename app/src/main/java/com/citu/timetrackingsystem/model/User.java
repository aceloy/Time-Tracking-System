package com.citu.timetrackingsystem.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.citu.timetrackingsystem.data.contracts.UserContract;
import com.citu.timetrackingsystem.helper.DateHelper;

public class User implements Parcelable {

    private int id = -1;
    private int idNumber;
    private String password;
    private String name;
    private int age;
    private String gender;
    private String address;
    private String status;
    private String createdDate = DateHelper.getISO8601Date();
    private String updatedDate = DateHelper.getISO8601Date();

    public static final User ADMIN_USER = new User(12345, "admin", "admin");

    public static final int CONTENT_PROVIDER_USER = 1001;
    public static final int CONTENT_PROVIDER_USER_ID = 1002;
    public static final int LOADER_USERS = 2001;

    public User() {

    }

    public User(int idNumber, String password, String name) {
        this.idNumber = idNumber;
        this.password = password;
        this.name = name;
    }

    public User(int id, int idNumber, String password, String name, int age, String gender, String address, String status, String createdDate, String updatedDate) {
        this.id = id;
        this.idNumber = idNumber;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.status = status;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public User(Cursor cursor) {
        id = cursor.getInt(cursor.getColumnIndex(UserContract.UserEntry._ID));
        idNumber = cursor.getInt(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_ID_NUMBER));
        password = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_PASSWORD));
        name = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_NAME));
        age = cursor.getInt(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_AGE));
        gender = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_GENDER));
        address = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_ADDRESS));
        status = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_STATUS));
        createdDate = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_CREATED_DATE));
        updatedDate = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_UPDATED_DATE));
    }

    protected User(Parcel in) {
        id = in.readInt();
        idNumber = in.readInt();
        password = in.readString();
        name = in.readString();
        age = in.readInt();
        gender = in.readString();
        address = in.readString();
        status = in.readString();
        createdDate = in.readString();
        updatedDate = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        parcel.writeString(name);
        parcel.writeInt(age);
        parcel.writeString(gender);
        parcel.writeString(address);
        parcel.writeString(status);
        parcel.writeString(createdDate);
        parcel.writeString(updatedDate);
    }

    public ContentValues getContentValues(boolean isSetNewUpdatedDate) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.UserEntry._ID, getId());
        contentValues.put(UserContract.UserEntry.COLUMN_ID_NUMBER, getIdNumber());
        contentValues.put(UserContract.UserEntry.COLUMN_PASSWORD, getPassword());
        contentValues.put(UserContract.UserEntry.COLUMN_NAME, getName());
        contentValues.put(UserContract.UserEntry.COLUMN_AGE, getAge());
        contentValues.put(UserContract.UserEntry.COLUMN_GENDER, getGender());
        contentValues.put(UserContract.UserEntry.COLUMN_ADDRESS, getAddress());
        contentValues.put(UserContract.UserEntry.COLUMN_STATUS, getStatus());
        contentValues.put(UserContract.UserEntry.COLUMN_CREATED_DATE, getCreatedDate());
        contentValues.put(UserContract.UserEntry.COLUMN_UPDATED_DATE, isSetNewUpdatedDate ? DateHelper.getISO8601Date() : getUpdatedDate());
        return contentValues;
    }

    public static User getUserByIDNumberAndPassword(Context context, int idNumber, String password) {
        Cursor cursor = context.getContentResolver().query(
                UserContract.UserEntry.CONTENT_URI,
                null,
                UserContract.UserEntry.COLUMN_ID_NUMBER + " = ? AND " + UserContract.UserEntry.COLUMN_PASSWORD + " = ?",
                new String[]{String.valueOf(idNumber), password},
                null);
        while (cursor.moveToNext())
            return new User(cursor);

        return null;
    }
}
