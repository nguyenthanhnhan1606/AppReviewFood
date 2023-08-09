package com.example.baitaplon.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

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

    public ArrayList<LoaiQuan> getAllLoai(){
        ArrayList<LoaiQuan> loaiQuans = new ArrayList<LoaiQuan>();

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

    public int deleteRow(LoaiQuan objLoaiQuan){

        int res = database.delete(SQLiteHelper.TABLE_LOAIQUAN, "id = ?" , new String[] { objLoaiQuan.getId() +"" });

        return  res;
    }

    public int updateRow(LoaiQuan objLoaiQuan){
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_IDLQ, objLoaiQuan.getId());
        values.put(SQLiteHelper.COLUMN_TENLOAI, objLoaiQuan.getTenloai());

        int res = database.update(SQLiteHelper.TABLE_LOAIQUAN, values,"id = ?", new String[] { objLoaiQuan.getId() +"" } );
        return  res;
    }

    public LoaiQuan selectOne(int id){
        LoaiQuan objLoaiQuan = new LoaiQuan();
        String[] columns = new String[]{"*"};
        String selection = SQLiteHelper.COLUMN_IDLQ + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};

        Cursor c = database.query(
                SQLiteHelper.TABLE_LOAIQUAN,
                columns,
                selection,
                selectionArgs,
                null, null, null
        );

        if(c != null & c.moveToFirst()) {
            objLoaiQuan.setId(c.getInt(0));
            objLoaiQuan.setTenloai(c.getString(1));

        }
        return objLoaiQuan; // nếu không lấy được dữ liệu thì cũng trả về object rỗng
    }

    public boolean checkTenLoaiQuan (String tenLoaiQuan){
        Cursor cursor = database.rawQuery("select * from LoaiQuan where tenloai = ?", new String[]{tenLoaiQuan});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
}
