package com.example.baitaplon.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    //LoaiQuan
    public static String TABLE_LOAIQUAN = "LoaiQuan";
    public static String COLUMN_IDLQ = "id";
    public static String COLUMN_TENLOAI = "tenloai";

    //Quan an
    public static String TABLE_QUANAN = "QuanAn";
    public static String COLUMN_IDQA = "id";
    public static String COLUMN_TENQUAN = "tenquan";
    public static String COLUMN_DIADIEM = "diadiem";
    public static String COLUMN_DANHGIA = "danhgia";
    public static String COLUMN_HINHANH = "hinhanh";
    public static String COLUMN_ACTIVEQ = "active";
    public static String COLUMN_IDLOAI = "id_loai";

    //User
    public static String TABLE_USER = "User";
    public static String COLUMN_IDU = "id";
    public static String COLUMN_HOTEN = "hoten";
    public static String COLUMN_USERNAME = "username";
    public static String COLUMN_PASSWORD = "password";
    public static String COLUMN_EMAIL = "email";
    public static String COLUMN_SDT = "sdt";
    public static String COLUMN_AVATAR = "avatar";
    public static String COLUMN_ACTIVEU = "active";
    public static String COLUMN_ROLE = "user_role";

    //Comment
    public static String TABLE_COMMENT = "Comment";
    public static String COLUMN_IDC = "id";
    public static String COLUMN_NOIDUNG = "noidung";
    public static String COLUMN_NGAYDANG = "ngaydang";
    public static String COLUMN_ACTIVEC = "active";
    public static String COLUMN_IDUSER = "id_user";
    public static String COLUMN_IDQUAN = "id_quan";

    //Tạo database
    private static String CREATE_LOAIQUAN = "create table " + TABLE_LOAIQUAN + "( " + COLUMN_IDLQ + " integer primary key autoincrement, " + COLUMN_TENLOAI + " varchar(20)) ";
    private static String CREATE_QUANAN = "create table " + TABLE_QUANAN + "( " + COLUMN_IDQA + " integer primary key autoincrement, " + COLUMN_TENQUAN + " varchar(20), " + COLUMN_DIADIEM + " varchar(50), " + COLUMN_DANHGIA + " double, " + COLUMN_HINHANH + " int, " + COLUMN_ACTIVEQ + " bit, " + COLUMN_IDLOAI + " integer, foreign key(" + COLUMN_IDLOAI + ") references " + TABLE_LOAIQUAN + "(" + COLUMN_IDLOAI + "))";
    private static String CREATE_USER = "create table " + TABLE_USER + "( " + COLUMN_IDU + " integer primary key autoincrement, " + COLUMN_USERNAME + " varchar(10), " + COLUMN_PASSWORD + " varchar(10), " + COLUMN_HOTEN + " varchar(20), " + COLUMN_EMAIL + " varchar(30), " + COLUMN_SDT + " varchar(10), " + COLUMN_AVATAR + " varchar(1000), " + COLUMN_ACTIVEU + " bit, " + COLUMN_ROLE + " varchar(10))";
    private static String CREATE_COMMENT = "create table " + TABLE_COMMENT + "( " + COLUMN_IDC + " integer primary key autoincrement, " + COLUMN_NOIDUNG + " varchar(100), " + COLUMN_NGAYDANG + " datetime, " + COLUMN_ACTIVEC + " bit, " + COLUMN_IDUSER + " integer, " + COLUMN_IDQUAN + " integer, foreign key(" + COLUMN_IDUSER + ") references " + TABLE_USER + "(" + COLUMN_IDUSER + "), foreign key(" + COLUMN_IDQUAN + ") references " + TABLE_QUANAN + "(" + COLUMN_IDQUAN + "))";






    public SQLiteHelper( Context context) {
        super(context, "quanandb", null, 1);
    }

    public void delAll(Context context) {

        context.deleteDatabase("quanandb");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LOAIQUAN);
        db.execSQL(CREATE_QUANAN);
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_COMMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_LOAIQUAN);
        db.execSQL("drop table if exists " + TABLE_QUANAN);
        db.execSQL("drop table if exists " + TABLE_USER);
        db.execSQL("drop table if exists " + TABLE_COMMENT);
        onCreate(db);
    }
}
