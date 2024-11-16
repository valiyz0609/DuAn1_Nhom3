package com.example.myapplication.DAOModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Database.DbHelper;
import com.example.myapplication.Model.GioHang;

import java.util.ArrayList;

public class DAOGioHang {

    private SQLiteDatabase database;
    DbHelper dbHelper;

//    Khởi tạo Constructor
    public DAOGioHang(Context context){
        dbHelper = new DbHelper(context, "DuAn1", null, 1);
        database = dbHelper.getWritableDatabase();
        database = dbHelper.getReadableDatabase();
    }

//    Thêm sản phẩm vào giỏ hàng
    public boolean addGiohang(GioHang gioHang){
        ContentValues values = new ContentValues();
        values.put("MaGioHang", gioHang.getMaGioHang());
        values.put("MaSanPham", gioHang.getMaSanPham());
        values.put("SoLuong", gioHang.getSoLuong());
        values.put("Size", gioHang.getSize());
        values.put("DonGia", gioHang.getDonGia());
        long check = database.insert("GioHang", null, values);
        if (check == -1){
            return false;
        }
        else {
            return true;
        }
    }

//    Lấy danh sách sản phẩm có trong giỏ hàng
    public ArrayList<GioHang> getGioHang(){
        ArrayList<GioHang> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT GioHang.MaGioHang, GioHang.masanpham, SanPham.image, SanPham.tensanpham, GioHang.SoLuong, GioHang.size, GioHang.dongia FROM GioHang, SanPham WHERE GioHang.MaSanPham = SanPham.MaSanPham", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                int maGioHang = cursor.getInt(0);
                int maSanPham = cursor.getInt(1);
                byte[] imgSP = cursor.getBlob(2);
                String tenSp = cursor.getString(3);
                int soLuong = cursor.getInt(4);
                String size = cursor.getString(5);
                double donGia = cursor.getDouble(6);
                list.add(new GioHang(maGioHang, maSanPham, imgSP, tenSp, soLuong, size, donGia));
            }   while (cursor.moveToNext());
        }
        return list;
    }

//    Sửa số lượng sản phẩm
    public boolean updateGioHang(GioHang gioHang){
        ContentValues values = new ContentValues();
        values.put("MaGioHang", gioHang.getMaGioHang());
        values.put("MaSanPham", gioHang.getMaSanPham());
        values.put("SoLuong", gioHang.getSoLuong());
        values.put("Size", gioHang.getSize());
        values.put("DonGia", gioHang.getDonGia());
        long check = database.update("GioHang", values, "MaSanPham = ? AND Size = ?", new String[]{String.valueOf(gioHang.getMaSanPham()), gioHang.getSize()});
        if (check == -1){
            return false;
        }
        else {
            return true;
        }
    }

//    Check tồn tại SP
    public ArrayList<GioHang> checkValidGioHang(GioHang gioHang){
        ArrayList<GioHang> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT GioHang.MaGioHang, GioHang.masanpham, SanPham.image, SanPham.tensanpham, GioHang.SoLuong, GioHang.size, GioHang.dongia FROM GioHang, SanPham WHERE GioHang.MaSanPham = SanPham.MaSanPham AND SanPham.MaSanPham = ? AND GioHang.size = ?", new String[]{String.valueOf(gioHang.getMaSanPham()), gioHang.getSize()});
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                int maGioHang = cursor.getInt(0);
                int maSanPham = cursor.getInt(1);
                byte[] imgSP = cursor.getBlob(2);
                String tenSp = cursor.getString(3);
                int soLuong = cursor.getInt(4);
                String size = cursor.getString(5);
                double donGia = cursor.getDouble(6);
                list.add(new GioHang(maGioHang, maSanPham, imgSP, tenSp, soLuong, size, donGia));
            }   while (cursor.moveToNext());
        }
        return list;
    }

//    Xóa SP khỏi giỏ hàng
    public boolean deleteGiohang(GioHang gioHang){
        long check = database.delete("GioHang", "MaSanPham = ? AND Size = ?", new String[]{String.valueOf(gioHang.getMaSanPham()), gioHang.getSize()});
        if (check == -1){
            return false;
        }
        else {
            return true;
        }
    }

//    Tính tổng tiền thanh toán từ giỏ hàng
    public double tongTienGiohang(){
        double tongTien = 0;
        Cursor cursor = database.rawQuery("SELECT SUM(GioHang.soluong * GioHang.dongia) as TongTien FROM GioHang", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            tongTien = cursor.getDouble(0);
        }
        return tongTien;
    }

}
