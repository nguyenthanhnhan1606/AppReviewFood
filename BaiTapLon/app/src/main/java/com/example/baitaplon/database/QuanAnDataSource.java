package com.example.baitaplon.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class QuanAnDataSource {
    private SQLiteDatabase database;
    private SQLiteHelper helper;
    private String[] col = {SQLiteHelper.COLUMN_IDQA, SQLiteHelper.COLUMN_TENQUAN, SQLiteHelper.COLUMN_DIADIEM, SQLiteHelper.COLUMN_DANHGIA, SQLiteHelper.COLUMN_HINHANH, SQLiteHelper.COLUMN_ACTIVEQ, SQLiteHelper.COLUMN_IDLOAI};

   public QuanAnDataSource(Context context){
        helper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public QuanAn insertQuanAn(String tenquan, String diadiem, double danhgia, int hinhanh, boolean active, int id_loai){
        database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenquan", tenquan);
        values.put("diadiem", diadiem);
        values.put("danhgia", danhgia);
        values.put("hinhanh", hinhanh);
        values.put("active", active);
        values.put("id_loai", id_loai);
        long result = database.insert(SQLiteHelper.TABLE_QUANAN, null, values);
        Cursor cursor = database.query(SQLiteHelper.TABLE_QUANAN,col, SQLiteHelper.COLUMN_IDLQ + " = " + result, null, null, null, null);
        cursor.moveToNext();
        QuanAn quanAn = cursorToQuanAn(cursor);
        return quanAn;
    }


    public void deleteQuanAn(QuanAn quanAn) {
        long id = quanAn.getId();
//        System.out.println("Comment deleted with id: " + id);
        database.delete(SQLiteHelper.TABLE_QUANAN, SQLiteHelper.COLUMN_IDQA
                + " = " + id, null);
    }

    public ArrayList<QuanAn> getAllQuan(){
        ArrayList<QuanAn> quanAns = new ArrayList<QuanAn>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_QUANAN, col, null, null, null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            QuanAn quanAn = cursorToQuanAn(cursor);
            quanAns.add(quanAn);
            cursor.moveToNext();
        }

        cursor.close();
        return quanAns;
    }

    public QuanAn cursorToQuanAn(Cursor cursor) {
        QuanAn quanAn = new QuanAn();
        quanAn.setId(cursor.getInt(0));
        quanAn.setTenquan(cursor.getString(1));
        quanAn.setDiadiem(cursor.getString(2));
        quanAn.setDanhgia(cursor.getDouble(3));
        quanAn.setHinhanh(cursor.getInt(4));
        quanAn.setId_loai(cursor.getInt(6));
        return quanAn;
    }
}
