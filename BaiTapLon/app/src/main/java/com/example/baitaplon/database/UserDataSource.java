package com.example.baitaplon.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class UserDataSource {
    private SQLiteDatabase database;
    private SQLiteHelper helper;

    public UserDataSource(Context context){
        helper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public boolean insertUser(String hoten, String username, String password, String email, String sdt, String avatar, boolean active, String user_role){
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_HOTEN, hoten);
        values.put(SQLiteHelper.COLUMN_USERNAME, username);
        values.put(SQLiteHelper.COLUMN_PASSWORD, password);
        values.put(SQLiteHelper.COLUMN_EMAIL, email);
        values.put(SQLiteHelper.COLUMN_SDT, sdt);
        values.put(SQLiteHelper.COLUMN_AVATAR, avatar);
        values.put(SQLiteHelper.COLUMN_ACTIVEU, active);
        values.put(SQLiteHelper.COLUMN_ROLE, user_role);
        long result = database.insert(SQLiteHelper.TABLE_USER, null, values);
        if (result == - 1)
            return false;
        else
            return true;
    }

    public int deleteRow(User objUser){

        int res = database.delete(SQLiteHelper.TABLE_USER, "id = ?" , new String[] { objUser.getId() +"" });

        return  res;
    }

    public int updateRow(User objUser){
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_HOTEN, objUser.getHoten());
        values.put(SQLiteHelper.COLUMN_USERNAME, objUser.getUsername());
        values.put(SQLiteHelper.COLUMN_PASSWORD, objUser.getPassword());
        values.put(SQLiteHelper.COLUMN_EMAIL, objUser.getEmail());
        values.put(SQLiteHelper.COLUMN_SDT, objUser.getSdt());
        values.put(SQLiteHelper.COLUMN_AVATAR, objUser.getAvatar());
        values.put(SQLiteHelper.COLUMN_ACTIVEU, objUser.isActive());
        values.put(SQLiteHelper.COLUMN_ROLE, objUser.getUser_role());

        int res = database.update(SQLiteHelper.TABLE_USER, values,"id = ?", new String[] { objUser.getId() +"" } );
        return  res;
    }

    public User selectOne(int id){
        String[] args = new String[] { id + "" };


        String[] columns = new String[]{"*"};

        Cursor c = database.query(SQLiteHelper.TABLE_USER,columns, null, null,null,null,null);
        if(c != null) {
            c.moveToFirst();
        }
        int activeInt = c.getInt(7);
        boolean isActive = (activeInt == 1);
        User objUser = new User(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),
                    c.getString(4),c.getString(5),c.getString(6),isActive, c.getString(8));
        return objUser; // nếu không lấy được dữ liệu thì cũng trả về object rỗng
    }

    public ArrayList<User> selectAll(){
        //1. Tạo biến chứa danh sach
        ArrayList<User> listUser = new ArrayList<User>();
        String[] ds_cot = new String[] { "*" };
        // tạo đối tượng con trỏ đọc dữ liệu
        Cursor cursor = database.query(SQLiteHelper.TABLE_USER, ds_cot,null, null,null,null, null);
        if(cursor.moveToFirst()){
            // có dữ liệu
            while (!cursor.isAfterLast()){
                // lấy dữ liệu
                User objUser = new User();
                objUser.setId( cursor.getInt(0)   );

                objUser.setUsername( cursor.getString(1   ));
                objUser.setPassword( cursor.getString(2   ));
                objUser.setEmail( cursor.getString(3   ));
                objUser.setHoten( cursor.getString(4   ));

                // bỏ đối tượng vào danh sách
                listUser.add(objUser);

                // chuyển con trỏ sang dòng tiếp theo
                cursor.moveToNext();// nếu không có dòng này sẽ bị treo ứng dụng
            }
        }
        return  listUser;
    }
    public boolean checkUserName (String username){
        Cursor cursor = database.rawQuery("select * from User where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }


    public String checkLogin(String username, String password) {
        String userRole = ""; // Biến để lưu trữ user_role của người dùng, mặc định là rỗng

        Cursor cursor = database.rawQuery("SELECT * FROM User WHERE username = ? AND password = ?", new String[]{username, password});
        if (cursor.getCount() > 0) {
            // Người dùng tồn tại, kiểm tra user_role
            cursor.moveToFirst();
            int userRoleIndex = cursor.getColumnIndex("user_role"); // Giả sử tên cột lưu trữ user_role là "user_role"

            if (userRoleIndex != -1) {
                userRole = cursor.getString(userRoleIndex);
            }

            cursor.close();
        }

        return userRole;
    }

}
