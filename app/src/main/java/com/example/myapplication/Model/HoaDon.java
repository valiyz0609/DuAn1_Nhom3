package com.example.myapplication.Model;

public class HoaDon {
    int maHoaDon;
    int maUser;
    String tenUser;
    String tenKhachHang;
    String NgayLapHD;
    int maGiohang;
    int maSP;
    String tenSP;
    int soLuong;
    String size;
    double donGia;
    double thanhTien;

    public HoaDon(int maUser, String tenKhachHang, String ngayLapHD, int maGiohang) {
        this.maUser = maUser;
        this.tenKhachHang = tenKhachHang;
        NgayLapHD = ngayLapHD;
        this.maGiohang = maGiohang;
    }

    public HoaDon(int maHoaDon, int maUser, String tenUser, String tenKhachHang, String ngayLapHD, int maSP, String tenSP, int soLuong, String size, double donGia, double thanhTien) {
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

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getTenUser() {
        return tenUser;
    }

    public void setTenUser(String tenUser) {
        this.tenUser = tenUser;
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

    public int getMaGiohang() {
        return maGiohang;
    }

    public void setMaGiohang(int maGiohang) {
        this.maGiohang = maGiohang;
    }
}
