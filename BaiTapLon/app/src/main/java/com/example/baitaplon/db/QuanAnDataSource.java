package com.example.baitaplon.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class QuanAnDataSource {
    private SQLiteDatabase db;
    private SQLiteHelper helper;

    private String[] allcol = {SQLiteHelper.COLUMN_IDQUAN, SQLiteHelper.COLUMN_TENQUAN, SQLiteHelper.COLUMN_DIADIEM, SQLiteHelper.COLUMN_HINHANH, SQLiteHelper.COLUMN_DANHGIA, SQLiteHelper.COLUMN_IDLOAIQUAN};

    public QuanAnDataSource (Context context){ helper = new SQLiteHelper(context);}

    public void open() throws SQLException {
        db = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public QuanAn insertQuanAn (String tenQuan, String diaDiem, String hinhAnh, double danhGia, int id_Loai){
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_TENQUAN, tenQuan);
        values.put(SQLiteHelper.COLUMN_DIADIEM, diaDiem);
        values.put(SQLiteHelper.COLUMN_HINHANH, hinhAnh);
        values.put(SQLiteHelper.COLUMN_DANHGIA, danhGia);
        values.put(SQLiteHelper.COLUMN_IDLOAIQUAN, id_Loai);
        long result = db.insert(SQLiteHelper.TABLE_QUANAN, null, values);

        Cursor cur = db.query(SQLiteHelper.TABLE_QUANAN, allcol, SQLiteHelper.COLUMN_IDQUAN + " = " + result,null,null,null,null );
        cur.moveToNext();
        QuanAn quanAn = cursorQuanAn(cur);
        cur.close();
        return quanAn;
    }

    public List<QuanAn> getAll () {
        List<QuanAn> quanAns = new ArrayList<QuanAn>();
        Cursor cursors = db.query(SQLiteHelper.TABLE_QUANAN, allcol, null, null, null,null,null);
        cursors.moveToFirst();
        while (!cursors.isAfterLast()){
            QuanAn quanAn = cursorQuanAn(cursors);
            quanAns.add(quanAn);
            cursors.moveToNext();
        }
        cursors.close();
        return quanAns;
    }

    public QuanAn cursorQuanAn (Cursor cursor){
        QuanAn quanAn = new QuanAn();
        quanAn.setId(cursor.getInt(0));
        quanAn.setTenQuan(cursor.getString(1));
        quanAn.setDiaDiem(cursor.getString(2));
        quanAn.setHinhAnh(cursor.getString(3));
        quanAn.setDanhGia(cursor.getDouble(4));
        quanAn.setId_loaiQuan(cursor.getInt(5));
        return quanAn;
    }
}
