package com.example.pro1121_md183110_nhom2.model;

import java.util.HashMap;

public class NhanVien {
    private String MaNV;
    private String TenNV;
    private String SDT;
    private String User;
    private String Pass;

    public HashMap<String, Object> convertHashMap(){
        HashMap<String, Object> NhanVien = new HashMap<>();
        NhanVien.put("MaNV",MaNV);
        NhanVien.put("TenNV",TenNV);
        NhanVien.put("SDT",SDT);
        NhanVien.put("User",User);
        NhanVien.put("Pass",Pass);
        return NhanVien;
    }

    public NhanVien() {
    }

    public NhanVien(String maNV, String tenNV, String SDT, String user, String pass) {
        MaNV = maNV;
        TenNV = tenNV;
        this.SDT = SDT;
        User = user;
        Pass = pass;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String tenNV) {
        TenNV = tenNV;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }
}
