package com.example.myapplication.Model;

public class LuuHoaDon {
    int maLuu;
    int maHoaDon;
    int maUser;
    String tenUser;
    String userName;
    int chucVu;
    String userSDT;
    int userNamSinh;
    String tenKhachHang;
    String NgayLapHD;
    int maSP;
    String tenSP;
    int soLuong;
    String size;
    double donGia;
    double thanhTien;

    public LuuHoaDon(int maLuu, int maHoaDon, int maUser, String tenUser, String tenKhachHang, String ngayLapHD, int maSP, String tenSP, int soLuong, String size, double donGia, double thanhTien) {
        this.maLuu = maLuu;
        this.maHoaDon = maHoaDon;
        this.maUser = maUser;
        this.tenUser = tenUser;
        this.tenKhachHang = tenKhachHang;
        NgayLapHD = ngayLapHD;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.size = size;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    public LuuHoaDon(int maLuu, int maHoaDon, String tenKhachHang, double thanhTien) {
        this.maLuu = maLuu;
        this.maHoaDon = maHoaDon;
        this.tenKhachHang = tenKhachHang;
        this.thanhTien = thanhTien;
    }

    public LuuHoaDon(int maHoaDon, int maUser, String tenUser, String tenKhachHang, String ngayLapHD, int maSP, String tenSP, int soLuong, String size, double donGia, double thanhTien) {
        this.maHoaDon = maHoaDon;
        this.maUser = maUser;
        this.tenUser = tenUser;
        this.tenKhachHang = tenKhachHang;
        NgayLapHD = ngayLapHD;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.size = size;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    public LuuHoaDon(int maUser, String tenUser, String userName, int chucVu, String userSDT, int userNamSinh, double thanhTien) {
        this.maUser = maUser;
        this.tenUser = tenUser;
        this.userName = userName;
        this.chucVu = chucVu;
        this.userSDT = userSDT;
        this.userNamSinh = userNamSinh;
        this.thanhTien = thanhTien;
    }

    public LuuHoaDon(int maLuu, int maHoaDon, String tenUser, String tenKhachHang, String ngayLapHD, String tenSP, int soLuong, String size, double donGia) {
        this.maLuu = maLuu;
        this.maHoaDon = maHoaDon;
        this.tenUser = tenUser;
        this.tenKhachHang = tenKhachHang;
        NgayLapHD = ngayLapHD;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.size = size;
        this.donGia = donGia;
    }

    public int getMaLuu() {
        return maLuu;
    }

    public void setMaLuu(int maLuu) {
        this.maLuu = maLuu;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaUser() {
        return maUser;
    }

    public void setMaUser(int maUser) {
        this.maUser = maUser;
    }

    public String getTenUser() {
        return tenUser;
    }

    public void setTenUser(String tenUser) {
        this.tenUser = tenUser;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getNgayLapHD() {
        return NgayLapHD;
    }

    public void setNgayLapHD(String ngayLapHD) {
        NgayLapHD = ngayLapHD;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getChucVu() {
        return chucVu;
    }

    public void setChucVu(int chucVu) {
        this.chucVu = chucVu;
    }

    public String getUserSDT() {
        return userSDT;
    }

    public void setUserSDT(String userSDT) {
        this.userSDT = userSDT;
    }

    public int getUserNamSinh() {
        return userNamSinh;
    }

    public void setUserNamSinh(int userNamSinh) {
        this.userNamSinh = userNamSinh;
    }
}
