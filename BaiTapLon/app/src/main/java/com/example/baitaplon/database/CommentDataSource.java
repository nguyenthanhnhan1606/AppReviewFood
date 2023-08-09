package com.example.baitaplon.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class CommentDataSource {
    private String[] col = {SQLiteHelper.COLUMN_IDC, SQLiteHelper.COLUMN_NOIDUNG, SQLiteHelper.COLUMN_DANHGIA_CM, SQLiteHelper.COLUMN_NGAYDANG, SQLiteHelper.COLUMN_ACTIVEC, SQLiteHelper.COLUMN_IDUSER, SQLiteHelper.COLUMN_IDQUAN};

    private SQLiteDatabase database;
    private SQLiteHelper helper;

    public CommentDataSource(Context context) {
        helper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public boolean insertComment(String noidung, double danhgia, String ngaydang, int id_user, int id_quanan) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_NOIDUNG, noidung);
        values.put(SQLiteHelper.COLUMN_DANHGIA_CM, danhgia);
        values.put(SQLiteHelper.COLUMN_NGAYDANG, ngaydang);
        values.put(SQLiteHelper.COLUMN_IDUSER, id_user);
        values.put(SQLiteHelper.COLUMN_ACTIVEC, 1);
        values.put(SQLiteHelper.COLUMN_IDQUAN, id_quanan);
        long result = database.insert(SQLiteHelper.TABLE_COMMENT, null, values);
        if (result == -1) return false;
        else return true;
    }

    public ArrayList<Comment> getAllComments(int id) {
        ArrayList<Comment> comments = new ArrayList<Comment>();
        String selection = SQLiteHelper.COLUMN_IDQUAN + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = database.query(SQLiteHelper.TABLE_COMMENT, col, selection, selectionArgs, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Comment comment = cursorToCommnets(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }

    public ArrayList<Comment> getAllCommentsByid(int id) {
        ArrayList<Comment> comments = new ArrayList<Comment>();
        String selection = SQLiteHelper.COLUMN_IDQUAN + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        // Kết hợp bảng Comment và User thông qua cột id_user
        String query = "SELECT Comment." + SQLiteHelper.COLUMN_IDC + ", " + SQLiteHelper.COLUMN_NOIDUNG + ", " + SQLiteHelper.COLUMN_NGAYDANG + ", " + SQLiteHelper.COLUMN_HOTEN + " FROM " + SQLiteHelper.TABLE_COMMENT + " Comment " + " JOIN " + SQLiteHelper.TABLE_USER + " User " + " ON Comment." + SQLiteHelper.COLUMN_IDUSER + " = User." + SQLiteHelper.COLUMN_IDU + " WHERE Comment." + SQLiteHelper.COLUMN_IDQUAN + " =? " + " And Comment." + SQLiteHelper.COLUMN_NOIDUNG + " != '' " + " ORDER BY Comment." + SQLiteHelper.COLUMN_IDC + " DESC";

        Cursor cursor = database.rawQuery(query, selectionArgs);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Comment comment = cursorToCommentWithUser(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }

    public ArrayList<QuanAn> getAllHisQuanAn(int id_user) {
        ArrayList<QuanAn> comments = new ArrayList<>();

        String query = "SELECT QuanAn." + SQLiteHelper.COLUMN_IDQA + ", " + SQLiteHelper.COLUMN_TENQUAN + ", " + SQLiteHelper.COLUMN_DIADIEM + ", " + SQLiteHelper.COLUMN_HINHANH + ", QuanAn." + SQLiteHelper.COLUMN_DANHGIA + " FROM " + SQLiteHelper.TABLE_QUANAN + " QuanAn " + " JOIN " + SQLiteHelper.TABLE_COMMENT + " Comment " + " ON QuanAn." + SQLiteHelper.COLUMN_IDQA + " = Comment." + SQLiteHelper.COLUMN_IDQUAN + " JOIN " + SQLiteHelper.TABLE_USER + " User " + " ON Comment." + SQLiteHelper.COLUMN_IDUSER + " = User." + SQLiteHelper.COLUMN_IDU + " WHERE Comment." + SQLiteHelper.COLUMN_IDUSER + " = ?" + " GROUP BY QuanAn." + SQLiteHelper.COLUMN_IDQA;

        String[] selectionArgs = {String.valueOf(id_user)};
        Cursor cursor = database.rawQuery(query, selectionArgs);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            QuanAn quanAn = cursorToQuanAn(cursor);
            comments.add(quanAn);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }

    public boolean checkHis(int id_user, int id_quan) {
        String selection = SQLiteHelper.COLUMN_IDUSER + " = ?" + " And " + SQLiteHelper.COLUMN_IDQUAN + " = ?";
        String[] selectionArgs = {String.valueOf(id_user), String.valueOf(id_quan)};
        Cursor cursor = database.query(SQLiteHelper.TABLE_COMMENT, col, selection, selectionArgs, null, null, null);
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    public boolean updateRating(int id_user, int id_quan, double rating) {
        String query = "UPDATE  " + SQLiteHelper.TABLE_COMMENT + " SET " + SQLiteHelper.COLUMN_DANHGIA_CM + " =?" + " WHERE " + SQLiteHelper.COLUMN_IDUSER + " =?" + " AND " + SQLiteHelper.COLUMN_IDQUAN + " =?";
        String[] selectionArgs = {String.valueOf(rating), String.valueOf(id_user), String.valueOf(id_quan)};
        try {
            Log.d("UPDATE_RATING", "Query: " + query);
            Log.d("UPDATE_RATING", "Selection Args: " + Arrays.toString(selectionArgs));
            database.execSQL(query, selectionArgs);
            return true; // Cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Có lỗi xảy ra khi cập nhật
        }
    }

    public double getRatingFromComment(int id_user, int id_quan) {
        double maxDanhGia = 0;
        String[] columns = {"MAX(" + SQLiteHelper.COLUMN_DANHGIA_CM + ")"};
        String selection = SQLiteHelper.COLUMN_IDUSER + "=? AND " + SQLiteHelper.COLUMN_IDQUAN + "=?";
        String[] selectionArgs = {String.valueOf(id_user), String.valueOf(id_quan)};

        Cursor cursor = database.query(SQLiteHelper.TABLE_COMMENT, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            maxDanhGia = cursor.getDouble(0);
            cursor.close();
        }

        return maxDanhGia;
    }

    private Comment cursorToCommnets(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getInt(0));
        comment.setNoidung(cursor.getString(1));
        comment.setDanhgia(cursor.getDouble(2));
        comment.setNgaydang(cursor.getString(3));
        int activeInt = cursor.getInt(4);
        boolean isActive = (activeInt == 1);
        comment.setActive(isActive);
        comment.setId_user(cursor.getInt(5));
        comment.setId_qan(cursor.getInt(6));
        return comment;
    }

    public boolean checkCommentExists(int id_user, int id_quan) {
        String selection = SQLiteHelper.COLUMN_IDUSER + " = ? AND " + SQLiteHelper.COLUMN_IDQUAN + " = ?";
        String[] selectionArgs = {String.valueOf(id_user), String.valueOf(id_quan)};

        Cursor cursor = database.query(SQLiteHelper.TABLE_COMMENT, col, selection, selectionArgs, null, null, null);

        boolean commentExists = cursor != null && cursor.getCount() > 0;
        cursor.close();

        return commentExists;
    }

    public double getTotalMaxDanhGia(int id_quan) {
        double totalMaxDanhGia = 0;

        String[] columns = {"SUM(max_danh_gia) AS total_max_danh_gia"};

        Cursor cursor = database. rawQuery("SELECT SUM(max_danh_gia) AS total_max_danh_gia\n" + "FROM (\n" + "    SELECT MAX(danhgia) AS max_danh_gia\n" + "    FROM Comment\n" + "    WHERE id_quan=?\n" + "    GROUP BY id_user\n" + ") AS max_danh_gia_table", new String[]{String.valueOf(id_quan)});

        if (cursor != null && cursor.moveToFirst()) {
            totalMaxDanhGia = cursor.getDouble(cursor.getColumnIndex("total_max_danh_gia"));
            cursor.close();
        }
        return totalMaxDanhGia;
    }

    public int getCountUserCommentForQuan(int id_quan) {
        String query = "SELECT COUNT(*) AS count_user_comment " +
                "FROM (" +
                "    SELECT id_user " +
                "    FROM Comment " +
                "    WHERE id_quan = ? " +
                "    GROUP BY id_user" +
                ") AS subquery";

        String[] selectionArgs = { String.valueOf(id_quan) };
        Cursor cursor = database.rawQuery(query, selectionArgs);

        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(cursor.getColumnIndex("count_user_comment"));
            cursor.close();
        }

        return count;
    }


    private Comment cursorToCommentWithUser(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getInt(0));
        comment.setNoidung(cursor.getString(1));
        comment.setNgaydang(cursor.getString(2));

        // Thêm thông tin người dùng (username) vào Comment
        User user = new User();
        user.setHoten(cursor.getString(3)); // Sử dụng getString để lấy thông tin từ Cursor
        comment.setUser(user);
        return comment;
    }


    private QuanAn cursorToQuanAn(Cursor cursor) {
        QuanAn quanAn = new QuanAn();
        quanAn.setId(cursor.getInt(0));
        quanAn.setTenquan(cursor.getString(1));
        quanAn.setDiadiem(cursor.getString(2));
        quanAn.setHinhanh(cursor.getInt(3));
        quanAn.setDanhgia(cursor.getDouble(4));
        return quanAn;
    }

    public int deleteRow(Comment objComment){

        int res = database.delete(SQLiteHelper.TABLE_COMMENT, "id = ?" , new String[] { objComment.getId() +"" });

        return  res;
    }

    public Comment selectOne(int id){
        Comment objComment = new Comment();
        String[] columns = new String[]{"*"};
        String selection = SQLiteHelper.COLUMN_IDC + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};

        Cursor c = database.query(
                SQLiteHelper.TABLE_COMMENT,
                columns,
                selection,
                selectionArgs,
                null, null, null
        );

        if(c != null & c.moveToFirst()) {
            int activeInt = c.getInt(4);
            boolean isActive = (activeInt == 1);
            objComment.setId(c.getInt(0));
            objComment.setNoidung(c.getString(1));
            objComment.setDanhgia(c.getDouble(2));
            objComment.setNgaydang(c.getString(3));
            objComment.setActive(isActive);
            objComment.setId_user(c.getInt(5));
            objComment.setId_qan(c.getInt(6));
        }

        return objComment; // nếu không lấy được dữ liệu thì cũng trả về object rỗng
    }

    public ArrayList<Comment> selectAll(){
        //1. Tạo biến chứa danh sach
        ArrayList<Comment> listComment = new ArrayList<Comment>();
        String[] ds_cot = new String[] { "*" };
        // tạo đối tượng con trỏ đọc dữ liệu
        Cursor cursor = database.query(SQLiteHelper.TABLE_COMMENT, ds_cot,null, null,null,null, null);
        if(cursor.moveToFirst()){
            // có dữ liệu
            while (!cursor.isAfterLast()){
                // lấy dữ liệu
                Comment objComment = new Comment();
                objComment.setId( cursor.getInt(0)   );

                objComment.setNoidung( cursor.getString(1   ));
                objComment.setDanhgia( cursor.getDouble(2   ));
                objComment.setNgaydang( cursor.getString(3   ));
                objComment.setId_user( cursor.getInt(5));
                objComment.setId_qan( cursor.getInt(6));
                // bỏ đối tượng vào danh sách
                listComment.add(objComment);

                // chuyển con trỏ sang dòng tiếp theo
                cursor.moveToNext();// nếu không có dòng này sẽ bị treo ứng dụng
            }
        }
        return  listComment;
    }
    public int getCommentCountForRestaurant(int restaurantId) {
        String query = "SELECT COUNT(*) FROM " + SQLiteHelper.TABLE_COMMENT +
                " WHERE " + SQLiteHelper.COLUMN_IDQUAN + " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(restaurantId)});
        if (cursor != null && cursor.moveToFirst()) {
            int commentCount = cursor.getInt(0);
            cursor.close();
            return commentCount;
        }
        return 0;
    }

    public int getRatingCountForRestaurant(int restaurantId) {
        String query = "SELECT COUNT(*) FROM " + SQLiteHelper.TABLE_COMMENT +
                " WHERE " + SQLiteHelper.COLUMN_IDQUAN + " = ? AND " +
                SQLiteHelper.COLUMN_DANHGIA_CM + " > 0";

        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(restaurantId)});
        if (cursor != null && cursor.moveToFirst()) {
            int ratingCount = cursor.getInt(0);
            cursor.close();
            return ratingCount;
        }
        return 0;
    }

}
