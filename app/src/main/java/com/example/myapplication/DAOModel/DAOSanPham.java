package com.example.myapplication.DAOModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.myapplication.Database.DbHelper;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.Model.TheLoai;

import java.util.ArrayList;

public class DAOSanPham {
    private SQLiteDatabase database;
    DbHelper dbHelper;

    public DAOSanPham(Context context) {
        dbHelper = new DbHelper(context, "DuAn1", null, 1);
        database = dbHelper.getWritableDatabase();
        database = dbHelper.getReadableDatabase();
    }

    public void insertData(byte[] image, String TenSanPham, double Price, int MaLoai, String MoTa) {
        String sql = "INSERT INTO SanPham VALUES (NULL, ?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindBlob(1, image);
        statement.bindString(2, TenSanPham);
        statement.bindDouble(3, Price);
        statement.bindLong(4, MaLoai);
        statement.bindString(5, MoTa);
        statement.executeInsert();
    }

    public void updateSanPham(byte[] image, String TenSanPham, double Price, int MaLoai, String MoTa, int id) {
        String sql = "UPDATE SanPham SET image = ?, TenSanPham = ?, Price = ?, MaLoai = ?, MoTa = ? WHERE MaSanPham =?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindBlob(1, image);
        statement.bindString(2, TenSanPham);
        statement.bindDouble(3, Price);
        statement.bindLong(4, MaLoai);
        statement.bindString(5, MoTa);
        statement.bindDouble(6, (double) id);

        statement.execute();
        database.close();
    }

    public void deleteData(int id) {

        String sql = "DELETE FROM SanPham WHERE MaSanPham = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) id);
        statement.execute();
        database.close();
    }

    public ArrayList<SanPham> getAllProduct(int rdoCheck) {
        String sql = null;
        if (rdoCheck == 0) {
            sql = "SELECT * FROM SanPham";
        }
        if (rdoCheck == 1) {
            sql = "SELECT * FROM SanPham ORDER BY Price ASC";
        }
        if (rdoCheck == 2) {
            sql = "SELECT * FROM SanPham ORDER BY MaLoai ASC";
        }
        return getData(sql);
    }

    public SanPham getSPofMaSP(int maSp) {
        String sql = "Select * FROM SanPham WHERE SanPham.MaSanPham = ?";
        ArrayList<SanPham> list = getData(sql, String.valueOf(maSp));
        return list.get(0);
    }

    public ArrayList<SanPham> getSPofTL(int maLoai) {
        String sql = "Select * FROM SanPham WHERE SanPham.MaLoai = ?";
        ArrayList<SanPham> list = getData(sql, String.valueOf(maLoai));
        return list;
    }

    public ArrayList<SanPham> getData(String sql, String... selectionAGrs) {
        ArrayList<SanPham> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, selectionAGrs);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            byte[] image = cursor.getBlob(1);
            String name = cursor.getString(2);
            double price = cursor.getDouble(3);
            int maLoai = cursor.getInt(4);
            String moTa = cursor.getString(5);
            list.add(new SanPham(id, image, name, price, maLoai, moTa));
        }
        return list;
    }

//    DAO LOẠI SẢN PHẨM - ANHNQ

    //    Thêm Loại Sản phẩm
    public boolean addLSP(TheLoai theLoai) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenLoai", theLoai.getTenLoai());
        long check = sqLiteDatabase.insert("THELOAI", null, contentValues);
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean editLSP(TheLoai theLoai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenLoai", theLoai.getTenLoai());
        long check = sqLiteDatabase.update("THELOAI",contentValues, "maLoai = ?", new String[]{String.valueOf(theLoai.getMaLoai())});
        if (check <= 0) return false;
        return true;
    }

    public boolean deleteLSP(int maLoai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        long check = sqLiteDatabase.delete("THELOAI", "maLoai = ?" , new String[]{String.valueOf(maLoai)});
        if (check<= 0) return false;
        return true;
    }

    //    Lấy danh sách Loại Sản phẩm
    public ArrayList<TheLoai> getDSLSP() {
        ArrayList<TheLoai> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THELOAI", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                int maLoai = cursor.getInt(0);
                String tenLoai = cursor.getString(1);
                list.add(new TheLoai(maLoai, tenLoai));
            } while (cursor.moveToNext());
        }
        return list;
    }

    // Tìm kiếm sản phẩm

}
