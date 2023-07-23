package com.example.baitaplon.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UserDataSource {
    private SQLiteDatabase database;
    private SQLiteHelper helper;

    public UserDataSource(Context context){
        helper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public boolean insertUser(String hoten, String username, String password, String email, String sdt, String avatar, boolean active, String user_role){
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_HOTEN, hoten);
        values.put(SQLiteHelper.COLUMN_USERNAME, username);
        values.put(SQLiteHelper.COLUMN_PASSWORD, password);
        values.put(SQLiteHelper.COLUMN_EMAIL, email);
        values.put(SQLiteHelper.COLUMN_SDT, sdt);
        values.put(SQLiteHelper.COLUMN_AVATAR, avatar);
        values.put(SQLiteHelper.COLUMN_ACTIVEU, active);
        values.put(SQLiteHelper.COLUMN_ROLE, user_role);
        long result = database.insert(SQLiteHelper.TABLE_USER, null, values);
        if (result == - 1)
            return false;
        else
            return true;
    }

    public boolean checkUserName (String username){
        Cursor cursor = database.rawQuery("select * from User where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }


    public boolean checkLogin (String username, String password){
        Cursor cursor = database.rawQuery("select * from User where username = ? and password = ?", new String[]{username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
}
