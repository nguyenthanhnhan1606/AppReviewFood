package com.example.baitaplon.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    //QuanAn
    public static final String TABLE_QUANAN = "QuanAn";
    public static final String COLUMN_IDQUAN = "id";
    public static final String COLUMN_TENQUAN = "tenQuan";
    public static final String COLUMN_DIADIEM = "diaDiem";
    public static final String COLUMN_HINHANH = "hinhAnh";
    public static final String COLUMN_DANHGIA = "danhGia";
    public static final String COLUMN_ACTIVEQ = "active";
    public static final String COLUMN_IDLOAIQUAN = "id_loaiQuan";


    //LoaiQuan
    public static final String TABLE_LOAIQUAN = "LoaiQuan";
    public static final String COLUMN_IDLOAI = "id";
    public static final String COLUMN_TENLOAI = "tenLoai";
    public static final String COLUMN_ACTIVEL = "active";

    //MonAn
    public static final String TABLE_MONAN = "MonAn";
    public static final String COLUMN_IDMON = "id";
    public static final String COLUMN_TENMON = "tenMon";
    public static final String COLUMN_ACTIVEM = "active";
    public static final String COLUMN_IDQUANAN = "id_quan";

    //User
    public static final String TABLE_USER = "User";
    public static final String COLUMN_IDUSER = "id";
    public static final String COLUMN_HOTEN = "hoTen";
    public static final String COLUMN_USER = "userName";
    public static final String COLUMN_PASSWORD = "passWord";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_SDT = "sdt";
    public static final String COLUMN_AVATAR = "avatar";
    public static final String COLUMN_ROLE = "user_role";
    public static final String COLUMN_ACTIVEU = "active";

    //Comment
    public static final String TABLE_COMMENT = "Comments";
    public static final String COLUMN_IDCOMMENT = "id";
    public static final String COLUMN_NOIDUNG = "noiDung";
    public static final String COLUMN_THOIGIAN = "thoiGian";
    public static final String COLUMN_ACTIVEC = "active";
    public static final String COLUMN_IDU = "id_user";
    public static final String COLUMN_IDQ = "id_quan";




    private static final String CREATE_QUANAN = "create table " + TABLE_QUANAN + "(" + COLUMN_IDQUAN + " integer primary key autoincrement, " + COLUMN_TENQUAN + " varchar(20), " +
            COLUMN_DIADIEM + " varchar(50), " + COLUMN_HINHANH + " varchar(1000), " + COLUMN_DANHGIA + " double, " + COLUMN_ACTIVEQ + " bit, " + COLUMN_IDLOAIQUAN + " integer, foreign key(" + COLUMN_IDLOAIQUAN + ") references " + TABLE_LOAIQUAN + "(" + COLUMN_IDLOAI + "))";

    private static final String CREATE_LOAIQUAN = "create table " + TABLE_LOAIQUAN + "(" + COLUMN_IDLOAI + " integer primary key autoincrement, " + COLUMN_TENLOAI + " varchar(20))";

    private static final String CREATE_MONAN = "create table " + TABLE_MONAN + "(" + COLUMN_IDMON + " integer primary key autoincrement, " + COLUMN_TENMON + " varchar(20), " + COLUMN_IDQUANAN + " integer, foreign key(" + COLUMN_IDQUANAN + ") references " + TABLE_QUANAN + "(" +COLUMN_IDQUAN + "))";

    private static  final String CREATE_USER = "create table " + TABLE_USER + "(" + COLUMN_IDUSER + " integer primary key autoincrement, " + COLUMN_HOTEN + " varchar(20), " + COLUMN_USER + " varchar(20), " + COLUMN_PASSWORD + " varchar(10), " + COLUMN_EMAIL + " varchar(100), " + COLUMN_SDT + " varchar(10), " + COLUMN_AVATAR + " varchar(1000), " + COLUMN_ROLE + " integer, " + COLUMN_ACTIVEU + " bit)";

    private static  final String CREATE_COMMENT = "create table " + TABLE_COMMENT + "(" + COLUMN_IDCOMMENT + " integer primary key autoincrement, " + COLUMN_NOIDUNG + " varchar(100), " + COLUMN_THOIGIAN + " datetime, " + COLUMN_ACTIVEC + " bit, " + COLUMN_IDU + " integer, " + COLUMN_IDQ + " integer, foreign key(" +  COLUMN_IDU + ") references " + TABLE_USER + "(" + COLUMN_IDU + "), foreign key(" + COLUMN_IDQ + ") references " + TABLE_QUANAN + "(" + COLUMN_IDQ + "))";
    public SQLiteHelper(Context context) {
        super(context, "quanandb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUANAN);
        db.execSQL(CREATE_LOAIQUAN);
        db.execSQL(CREATE_MONAN);
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_COMMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_QUANAN);
        db.execSQL("drop table if exists " + TABLE_LOAIQUAN);
        db.execSQL("drop table if exists " + TABLE_MONAN);
        db.execSQL("drop table if exists " + TABLE_USER);
        db.execSQL("drop table if exists " + TABLE_COMMENT);
    }

    public void delAll(Context context) {
        context.deleteDatabase("quanandb");
    }
}
