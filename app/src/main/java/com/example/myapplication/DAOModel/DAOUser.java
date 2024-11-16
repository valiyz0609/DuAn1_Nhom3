package com.example.myapplication.DAOModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Database.DbHelper;
import com.example.myapplication.Model.User;

import java.util.ArrayList;

public class DAOUser {
    private SQLiteDatabase database;

    public DAOUser(Context context) {
        DbHelper dbHelper = new DbHelper(context, "DuAn1", null, 1);
        database = dbHelper.getReadableDatabase();
        database = dbHelper.getWritableDatabase();
    }

//    Add User
    public long insertUser(User user) {
        ContentValues values = new ContentValues();
        values.put("FullName", user.getFullName());
        values.put("Username", user.getUsername());
        values.put("ChucVu", user.getMaChucVu());
        values.put("Password", user.getPassword());
        values.put("SDT", user.getSDT());
        values.put("NamSinh", user.getNamSinh());
        return database.insert("User", null, values);
    }

    // update User
    public boolean updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put("FullName", user.getFullName());
        if (!user.getPassword().isEmpty()){
            values.put("Password", user.getPassword());
        }
        values.put("SDT", user.getSDT());
        values.put("NamSinh", user.getNamSinh());

        long check = database.update("User", values, "MaUser=?", new String[]{String.valueOf(user.getID_User())});
        if (check == -1){
            return false;
        }
        else {
            return true;
        }
    }

    //    Check Đăng nhập tài khoản
    public ArrayList<User> checkLogin(String username, String password) {
        String sql = "SELECT * FROM User WHERE Username=? AND Password=?";
        ArrayList<User> list = getData(sql, username, password);
        return list;
    }

//    Check tồn tại userName;
    public int checkValid(String username) {
        String sql = "SELECT * FROM User WHERE Username=?";
        ArrayList<User> list = getData(sql, username);
        return list.size();
    }

    //    Lấy thông tin User theo ID
    public User getUser(int inputId) {
        User user = null;
        Cursor cursor = database.rawQuery("SELECT User.mauser, User.fullname, User.Username, User.password, ChucVu.MaChucVu, ChucVu.tenchucvu, User.sdt,User.namsinh FROM User, ChucVu WHERE User.ChucVu = ChucVu.machucvu and User.MaUser = ?", new String[]{String.valueOf(inputId)});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                int maUser = cursor.getInt(0);
                String fullName = cursor.getString(1);
                String userName = cursor.getString(2);
                String passWord = cursor.getString(3);
                int maChucVu = cursor.getInt(4);
                String tenChucVu = cursor.getString(5);
                String soDT = cursor.getString(6);
                int namSinh = cursor.getInt(7);
                user = new User(maUser, fullName, userName, passWord, maChucVu, tenChucVu, soDT, namSinh);
            } while (cursor.moveToNext());
        }
        return user;
    }

    //    Lấy danh sách User
    public ArrayList<User> getAllUser() {
        ArrayList<User> listUser = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT User.mauser, User.fullname, User.Username, User.password, ChucVu.MaChucVu, ChucVu.tenchucvu, User.sdt,User.namsinh FROM User, ChucVu WHERE User.ChucVu = ChucVu.machucvu", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                int maUser = cursor.getInt(0);
                String fullName = cursor.getString(1);
                String userName = cursor.getString(2);
                String passWord = cursor.getString(3);
                int maChucVu = cursor.getInt(4);
                String tenChucVu = cursor.getString(5);
                String soDT = cursor.getString(6);
                int namSinh = cursor.getInt(7);
                listUser.add(new User(maUser, fullName, userName, passWord, maChucVu, tenChucVu, soDT, namSinh));
            } while (cursor.moveToNext());
        }
        return listUser;
    }

//    Xóa User
    public boolean deleteUser(int maUser){
        long check = database.delete("User", "mauser = ?", new String[]{String.valueOf(maUser)});
        if (check == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public ArrayList<User> getData(String sql, String... selectionAGrs) {
        ArrayList<User> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, selectionAGrs);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                int id_user = cursor.getInt(0);
                String fullname = cursor.getString(1);
                String username = cursor.getString(2);
                String password = cursor.getString(3);
                int id_chucVu = cursor.getInt(4);
                String sdt = cursor.getString(5);
                int namSinh = cursor.getInt(6);

                list.add(new User(id_user, fullname, username, password, id_chucVu, sdt, namSinh));
            } while (cursor.moveToNext());
        }
        return list;
    }
}
