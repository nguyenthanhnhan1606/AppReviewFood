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
    public long insertQuanAn(QuanAn objQuanAn){

        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelper.COLUMN_TENQUAN, objQuanAn.getTenquan());
        contentValues.put(SQLiteHelper.COLUMN_DIADIEM, objQuanAn.getDiadiem());
        contentValues.put(SQLiteHelper.COLUMN_DANHGIA, objQuanAn.getDanhgia());
        contentValues.put(SQLiteHelper.COLUMN_HINHANH, objQuanAn.getHinhanh());
        contentValues.put(SQLiteHelper.COLUMN_ACTIVEQ, objQuanAn.isActive());
        contentValues.put(SQLiteHelper.COLUMN_IDLOAI, objQuanAn.getId_loai());

        long res = database.insert(SQLiteHelper.TABLE_QUANAN, null, contentValues);

        return  res;

    }

    public int deleteQuanAn(QuanAn quanAn) {
        int res = database.delete(SQLiteHelper.TABLE_QUANAN, "id = ?" , new String[] { quanAn.getId() +"" });

        return  res;
    }

    public int updateRow(QuanAn objQuanAn){
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_TENQUAN, objQuanAn.getTenquan());
        values.put(SQLiteHelper.COLUMN_DIADIEM, objQuanAn.getDiadiem());
        values.put(SQLiteHelper.COLUMN_DANHGIA, objQuanAn.getDanhgia());
        values.put(SQLiteHelper.COLUMN_HINHANH, objQuanAn.getHinhanh());
        values.put(SQLiteHelper.COLUMN_ACTIVEQ, objQuanAn.isActive());
        values.put(SQLiteHelper.COLUMN_IDLOAI, objQuanAn.getId_loai());

        int res = database.update(SQLiteHelper.TABLE_QUANAN, values,"id = ?", new String[] { objQuanAn.getId() +"" } );
        return  res;
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

    public QuanAn selectOne(int id){
        QuanAn objQuanAn = new QuanAn();
        String[] columns = new String[]{"*"};
        String selection = SQLiteHelper.COLUMN_IDQA + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};

        Cursor c = database.query(
                SQLiteHelper.TABLE_QUANAN,
                columns,
                selection,
                selectionArgs,
                null, null, null
        );

        if(c != null & c.moveToFirst()) {
            int activeInt = c.getInt(5);
            boolean isActive = (activeInt == 1);
            objQuanAn.setId(c.getInt(0));
            objQuanAn.setTenquan(c.getString(1));
            objQuanAn.setDiadiem(c.getString(2));
            objQuanAn.setDanhgia(c.getDouble(3));
            objQuanAn.setHinhanh(c.getInt(4));
            objQuanAn.setActive(isActive);
            objQuanAn.setId_loai(c.getInt(6));
        }

        return objQuanAn; // nếu không lấy được dữ liệu thì cũng trả về object rỗng
    }

    public QuanAn selectOneWithLoai(int id){
        String[] args = new String[] { id + "" };

        QuanAn objQuanAn = new QuanAn();
        String str_sql = "SELECT id, tenquan, diadiem,danhgia,hinhanh,active,id_loai" +
                " FROM QuanAn INNER JOIN LoaiQuan ON QuanAn.id_loai = LoaiQuan.id WHERE id = ?";

        Cursor cursor = database.rawQuery(str_sql, args );

        if(cursor.moveToFirst()){
            int activeInt = cursor.getInt(4);
            boolean isActive = (activeInt == 1);
            // nếu có dữ liệu thì hàm moveToFirst sẽ trả về giá trị true
            objQuanAn.setId( cursor.getInt(0));
            objQuanAn.setTenquan( cursor.getString(1));
            objQuanAn.setDiadiem( cursor.getString(2));
            objQuanAn.setDanhgia( cursor.getDouble(3));
            objQuanAn.setActive(isActive);
            objQuanAn.setHinhanh( cursor.getInt(5));
            objQuanAn.setId_loai(cursor.getInt(6));
        }

        return objQuanAn; // nếu không lấy được dữ liệu thì cũng trả về object rỗng
    }

    public boolean checkNameQuan (String name){
        Cursor cursor = database.rawQuery("select * from QuanAn where tenquan = ?", new String[]{name});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
}
