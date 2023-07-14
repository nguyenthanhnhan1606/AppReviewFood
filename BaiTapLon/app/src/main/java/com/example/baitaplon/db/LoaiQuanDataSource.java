package com.example.baitaplon.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoaiQuanDataSource {
    private SQLiteDatabase db;
    private SQLiteHelper helper;
    private String[] allcol = {SQLiteHelper.COLUMN_IDLOAI, SQLiteHelper.COLUMN_TENLOAI};
    private String ten;


    public LoaiQuanDataSource(Context context){ helper = new SQLiteHelper(context); }


    public void open() throws SQLException {
        db = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public LoaiQuan insertLoaiQuan(String tenLoai){
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_TENLOAI, tenLoai);
        long result = db.insert(SQLiteHelper.TABLE_LOAIQUAN, null, values);

        Cursor cur = db.query(SQLiteHelper.TABLE_QUANAN, allcol, SQLiteHelper.COLUMN_IDLOAI + " = " + result,null,null,null,null );
        cur.moveToNext();
        LoaiQuan loaiQuan = cursorLoaiQuan(cur);
        cur.close();
        return loaiQuan;
    }

    public List<LoaiQuan> getAllLoai () {
        List<LoaiQuan> loaiQuans = new ArrayList<LoaiQuan>();
        Cursor cursors = db.query(SQLiteHelper.TABLE_LOAIQUAN, allcol, null, null, null,null,null);
        cursors.moveToFirst();
        while (!cursors.isAfterLast()){
            LoaiQuan loaiQuan = cursorLoaiQuan(cursors);
            loaiQuans.add(loaiQuan);
            cursors.moveToNext();
        }
        cursors.close();
        return loaiQuans;
    }

    public String getTenLoai() {
        Cursor cursor = db.query(SQLiteHelper.TABLE_LOAIQUAN, allcol, null, null,null,null,null);
        while (cursor.moveToNext()){
            ten = cursor.getString(1);
        }
        return ten;
    }


    public LoaiQuan cursorLoaiQuan (Cursor cursor){
        LoaiQuan loaiQuan = new LoaiQuan();
        loaiQuan.setId(cursor.getInt(0));
        loaiQuan.setTenLoai(cursor.getString(1));
        return loaiQuan;
    }

}
