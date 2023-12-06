package com.example.pro1121_md183110_nhom2.model;

import java.util.HashMap;

public class SanPham {
    private String MaSP;
    private  String TenSP;
    private int Gia;
    private String KhoiLuong,LuongCalo,ThanhPhan,MaLoai,TenLoai;
    private int SoLuong;
    private String NSX;
    private String IMG;

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
        SanPham.put("NSX",NSX);
        SanPham.put("IMG",IMG);
        return SanPham;
    }

    public SanPham() {
    }



    public SanPham(String maSP, String tenSP, int gia, String khoiLuong, String luongCalo, String thanhPhan, String maLoai, String tenLoai, String NSX,String IMG) {
        MaSP = maSP;
        TenSP = tenSP;
        Gia = gia;
        KhoiLuong = khoiLuong;
        LuongCalo = luongCalo;
        ThanhPhan = thanhPhan;
        MaLoai = maLoai;
        TenLoai = tenLoai;
        this.NSX = NSX;
        this.IMG = IMG;
    }

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        this.IMG = IMG;
    }

    public String getNSX() {
        return NSX;
    }

    public void setNSX(String NSX) {
        this.NSX = NSX;
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
