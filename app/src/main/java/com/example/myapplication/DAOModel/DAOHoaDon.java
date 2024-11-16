package com.example.myapplication.DAOModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Database.DbHelper;
import com.example.myapplication.Model.HoaDon;

import java.util.ArrayList;

public class DAOHoaDon {

    private SQLiteDatabase database;
    DbHelper dbHelper;

    //    Khởi tạo Constructor
    public DAOHoaDon(Context context){
        dbHelper = new DbHelper(context, "DuAn1", null, 1);
        database = dbHelper.getWritableDatabase();
        database = dbHelper.getReadableDatabase();
    }

//    Tạo Hóa đơn
    public boolean addHoaDon(HoaDon hoaDon){
        ContentValues values = new ContentValues();
        values.put("MaUser", hoaDon.getMaUser());
        values.put("TenKhachHang", hoaDon.getTenKhachHang());
        values.put("NgayLapHD", hoaDon.getNgayLapHD());
        values.put("MaGioHang", hoaDon.getMaGiohang());
        long check = database.insert("HoaDon", null, values);
        if (check == -1){
            return false;
        }
        else {
            return true;
        }
    }

//    Lấy ra hóa đơn
    public ArrayList<HoaDon> getHoaDon(){
        ArrayList<HoaDon> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT HoaDon.MaHoaDon, " +
                "User.mauser, " +
                "User.FullName, " +
                "HoaDon.tenkhachhang, " +
                "HoaDon.ngaylaphd, " +
                "SanPham.MaSanPham, " +
                "SanPham.tensanpham, " +
                "GioHang.soluong, " +
                "GioHang.size, " +
                "GioHang.dongia, " +
                "(GioHang.soluong * GioHang.dongia) as ThanhTien FROM HoaDon, GioHang, SanPham, User WHERE HoaDon.magiohang = GioHang.MaGioHang and GioHang.masanpham = SanPham.MaSanPham and HoaDon.mauser = User.MaUser", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                int maHoaDon = cursor.getInt(0);
                int maUser = cursor.getInt(1);
                String userName = cursor.getString(2);
                String tenKH = cursor.getString(3);
                String ngayLapHD = cursor.getString(4);
                int maSP = cursor.getInt(5);
                String tenSP = cursor.getString(6);
                int soLuong = cursor.getInt(7);
                String size = cursor.getString(8);
                double donGia = cursor.getDouble(9);
                double thanhTien = cursor.getDouble(10);
                list.add(new HoaDon(maHoaDon, maUser, userName, tenKH, ngayLapHD,maSP, tenSP, soLuong, size, donGia, thanhTien));
            }   while (cursor.moveToNext());
        }
        return list;
    }

//    Xóa hóa đơn
    public  void deleteHoaDon(HoaDon hoaDon){
        database.delete("HoaDon","MaHoaDon = ?", new String[]{String.valueOf(hoaDon.getMaHoaDon())});
    }
}
