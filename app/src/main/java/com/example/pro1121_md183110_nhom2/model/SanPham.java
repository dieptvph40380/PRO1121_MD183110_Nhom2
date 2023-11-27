package com.example.pro1121_md183110_nhom2.model;

import java.util.HashMap;

public class SanPham {
    private String MaSP;
    private  String TenSP;
    private int Gia;
    private String KhoiLuong,LuongCalo,ThanhPhan,MaLoai,TenLoai;
    private int SoLuong;

    public HashMap<String, Object> convertHashMap(){
        HashMap<String, Object> SanPham = new HashMap<>();
        SanPham.put("MaSP",MaSP);
        SanPham.put("TenSP",TenSP);
        SanPham.put("Gia",Gia);
        SanPham.put("KhoiLuong",KhoiLuong);
        SanPham.put("LuongCalo",LuongCalo);
        SanPham.put("ThanhPhan",ThanhPhan);
        SanPham.put("MaLoai",MaLoai);
        SanPham.put("TenLoai",TenLoai);
        SanPham.put("SoLuong",SoLuong);
        return SanPham;
    }

    public SanPham() {
    }

    public SanPham(String maSP, String tenSP, int gia, String khoiLuong, String luongCalo, String thanhPhan, String maLoai, String tenLoai, int soLuong) {
        MaSP = maSP;
        TenSP = tenSP;
        Gia = gia;
        KhoiLuong = khoiLuong;
        LuongCalo = luongCalo;
        ThanhPhan = thanhPhan;
        MaLoai = maLoai;
        TenLoai = tenLoai;
        SoLuong = soLuong;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String maSP) {
        MaSP = maSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public int getGia() {
        return Gia;
    }

    public void setGia(int gia) {
        Gia = gia;
    }

    public String getKhoiLuong() {
        return KhoiLuong;
    }

    public void setKhoiLuong(String khoiLuong) {
        KhoiLuong = khoiLuong;
    }

    public String getLuongCalo() {
        return LuongCalo;
    }

    public void setLuongCalo(String luongCalo) {
        LuongCalo = luongCalo;
    }

    public String getThanhPhan() {
        return ThanhPhan;
    }

    public void setThanhPhan(String thanhPhan) {
        ThanhPhan = thanhPhan;
    }

    public String getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(String maLoai) {
        MaLoai = maLoai;
    }

    public String getTenLoai() {
        return TenLoai;
    }

    public void setTenLoai(String tenLoai) {
        TenLoai = tenLoai;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }
}
