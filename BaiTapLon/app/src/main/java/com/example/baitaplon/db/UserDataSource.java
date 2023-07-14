package com.example.baitaplon.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UserDataSource {
    private SQLiteDatabase db;
    private SQLiteHelper helper;

    private String[] col = {SQLiteHelper.COLUMN_IDUSER, SQLiteHelper.COLUMN_HOTEN, SQLiteHelper.COLUMN_USER, SQLiteHelper.COLUMN_PASSWORD, SQLiteHelper.COLUMN_EMAIL, SQLiteHelper.COLUMN_SDT, SQLiteHelper.COLUMN_AVATAR, SQLiteHelper.COLUMN_ROLE, SQLiteHelper.COLUMN_ACTIVEU};

    public UserDataSource (Context context){
        helper = new SQLiteHelper(context);
    }

    public void open() throws SQLException{
        db = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public boolean insertUser (String hoTen,String userName, String passWord, String email, String sdt, String avatar, int user_role, boolean active){
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_HOTEN, hoTen);
        values.put(SQLiteHelper.COLUMN_USER, userName);
        values.put(SQLiteHelper.COLUMN_PASSWORD, passWord);
        values.put(SQLiteHelper.COLUMN_EMAIL, email);
        values.put(SQLiteHelper.COLUMN_SDT, sdt);
        values.put(SQLiteHelper.COLUMN_AVATAR, avatar);
        values.put(SQLiteHelper.COLUMN_ROLE, user_role);
        values.put(SQLiteHelper.COLUMN_ACTIVEU, active);
        long result = db.insert(SQLiteHelper.TABLE_USER, null, values);
        if (result == - 1)
            return false;
        else return true;
    }

    public boolean checkUser (String userName){
        open();
        Cursor cursor = db.rawQuery("select * from User where userName = ?", new String[]{userName});
        if (cursor.getCount() > 0)
            return  true;
        else
            return false;
    }


    public boolean checkLogin (String userName, String passWord){
        open();
        Cursor cursor = db.rawQuery("select * from User where userName = ? and passWord = ?", new String[]{userName, passWord});
        if (cursor.getCount() > 0)
            return  true;
        else
            return false;
    }


}
