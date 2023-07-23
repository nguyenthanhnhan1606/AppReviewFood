package com.example.baitaplon.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoaiQuanDataSource {
    private SQLiteDatabase database;
    private SQLiteHelper helper;
    private String[] col = {SQLiteHelper.COLUMN_IDLQ, SQLiteHelper.COLUMN_TENLOAI};

    public LoaiQuanDataSource(Context context){
       helper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public LoaiQuan insertLoaiQuan(String tenloai){
        ContentValues values = new ContentValues();
        values.put("tenloai", tenloai);
        long result = database.insert(SQLiteHelper.TABLE_LOAIQUAN, null, values);
        Cursor cursor = database.query(SQLiteHelper.TABLE_LOAIQUAN,col, SQLiteHelper.COLUMN_IDLQ + " = " + result, null, null, null, null);
        cursor.moveToNext();
        LoaiQuan loaiQuan = cursorToLoaiQuan(cursor);
        return loaiQuan;
    }

    public List<LoaiQuan> getAllLoai(){
        List<LoaiQuan> loaiQuans = new ArrayList<LoaiQuan>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_LOAIQUAN, col, null, null, null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            LoaiQuan loaiQuan = cursorToLoaiQuan(cursor);
            loaiQuans.add(loaiQuan);
            cursor.moveToNext();
        }

        cursor.close();
        return loaiQuans;
    }

    public LoaiQuan cursorToLoaiQuan(Cursor cursor) {
        LoaiQuan loaiQuan = new LoaiQuan();
        loaiQuan.setId(cursor.getInt(0));
        loaiQuan.setTenloai(cursor.getString(1));
        return loaiQuan;
    }

}
