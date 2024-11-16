package com.example.myapplication.Model;

public class User {
    int ID_User;
    String FullName;
    String username;
    String password;
    int maChucVu;
    String tenChucVu;
    String SDT;
    int NamSinh;

    public User(String fullName, String username, String password, int maChucVu, String SDT, int namSinh) {
        FullName = fullName;
        this.username = username;
        this.password = password;
        this.maChucVu = maChucVu;
        this.SDT = SDT;
        NamSinh = namSinh;
    }

    public User(int ID_User, String fullName, String username, String password, int maChucVu, String SDT, int namSinh) {
        this.ID_User = ID_User;
        FullName = fullName;
        this.username = username;
        this.password = password;
        this.maChucVu = maChucVu;
        this.SDT = SDT;
        NamSinh = namSinh;
    }

    public User(int ID_User, String fullName, String username, String password, int maChucVu, String tenChucVu, String SDT, int namSinh) {
        this.ID_User = ID_User;
        FullName = fullName;
        this.username = username;
        this.password = password;
        this.maChucVu = maChucVu;
        this.tenChucVu = tenChucVu;
        this.SDT = SDT;
        NamSinh = namSinh;
    }

    public int getID_User() {
        return ID_User;
    }

    public void setID_User(int ID_User) {
        this.ID_User = ID_User;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaChucVu() {
        return maChucVu;
    }

    public void setMaChucVu(int maChucVu) {
        this.maChucVu = maChucVu;
    }

    public String getTenChucVu() {
        return tenChucVu;
    }

    public void setTenChucVu(String tenChucVu) {
        this.tenChucVu = tenChucVu;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public int getNamSinh() {
        return NamSinh;
    }

    public void setNamSinh(int namSinh) {
        NamSinh = namSinh;
    }
}
