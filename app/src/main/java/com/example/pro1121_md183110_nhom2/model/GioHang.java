package com.example.pro1121_md183110_nhom2.model;

import java.util.HashMap;

public class GioHang {
    private String MaGH;
    private int ThanhTien;
    private int SoLuong;
    private String Size;
    private String MaKH;
    private String TenSP;
    private int GiaSP;


    public HashMap<String, Object> convertHashMap() {
        HashMap<String, Object> GioHang = new HashMap<>();
        GioHang.put("MaKH", MaKH);
        GioHang.put("MaGH", MaGH);
        GioHang.put("ThanhTien", ThanhTien);
        GioHang.put("SoLuong", SoLuong);
        GioHang.put("Size", Size);
        GioHang.put("TenSP", TenSP);
        GioHang.put("GiaSP", GiaSP);
        return GioHang;
    }

    public GioHang() {
    }

    public GioHang(String maGH, int soLuong, String size, String maKH, String tenSP, int giaSP) {
        MaGH = maGH;
        SoLuong = soLuong;
        Size = size;
        MaKH = maKH;
        TenSP = tenSP;
        GiaSP = giaSP;
    }

    public GioHang(String maGH, int thanhTien, int soLuong, String size, String maKH, String tenSP, int giaSP) {
        MaGH = maGH;
        ThanhTien = thanhTien;
        SoLuong = soLuong;
        Size = size;
        MaKH = maKH;
        TenSP = tenSP;
        GiaSP = giaSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public int getGiaSP() {
        return GiaSP;
    }

    public void setGiaSP(int giaSP) {
        GiaSP = giaSP;
    }

    public String getMaGH() {
        return MaGH;
    }

    public void setMaGH(String maGH) {
        MaGH = maGH;
    }

    public int getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(int thanhTien) {
        ThanhTien = thanhTien;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String maKH) {
        MaKH = maKH;
    }


}




