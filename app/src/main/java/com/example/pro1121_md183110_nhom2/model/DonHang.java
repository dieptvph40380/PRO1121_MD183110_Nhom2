package com.example.pro1121_md183110_nhom2.model;

import java.util.HashMap;

public class DonHang {
    private String MaDH;
    private String TenSP;
    private int Gia;
    private int SoLuong;
    private int ThanhTien;
    private String DNKH;
    private String DNQL;
    private int TrangThaiDH;
    private int TrangThaiGH;
    private String Size;
    private String Ngay;

    public DonHang() {
    }

    public HashMap<String, Object> convertHashMap() {
        HashMap<String, Object> DonHang = new HashMap<>();
        DonHang.put("MaDH", MaDH);
        DonHang.put("TenSP", TenSP);
        DonHang.put("Gia", Gia);
        DonHang.put("SoLuong", SoLuong);
        DonHang.put("ThanhTien",ThanhTien);
        DonHang.put("DNKH", DNKH);
        DonHang.put("DNQL", DNQL);
        DonHang.put("TrangThaiDH",TrangThaiDH);
        DonHang.put("TrangThaiDH",TrangThaiDH);
        DonHang.put("Size",Size);
        DonHang.put("Ngay",Ngay);
        return DonHang;
    }

    public DonHang(String maDH, String tenSP, int gia, int soLuong, int thanhTien, String DNKH, String DNQL, int trangThaiDH, int trangThaiGH, String size, String ngay) {
        MaDH = maDH;
        TenSP = tenSP;
        Gia = gia;
        SoLuong = soLuong;
        ThanhTien = thanhTien;
        this.DNKH = DNKH;
        this.DNQL = DNQL;
        TrangThaiDH = trangThaiDH;
        TrangThaiGH = trangThaiGH;
        Size = size;
        Ngay = ngay;
    }

    public int getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(int thanhTien) {
        ThanhTien = thanhTien;
    }

    public String getMaDH() {
        return MaDH;
    }

    public void setMaDH(String maDH) {
        MaDH = maDH;
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

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getDNKH() {
        return DNKH;
    }

    public void setDNKH(String DNKH) {
        this.DNKH = DNKH;
    }

    public String getDNQL() {
        return DNQL;
    }

    public void setDNQL(String DNQL) {
        this.DNQL = DNQL;
    }

    public int getTrangThaiDH() {
        return TrangThaiDH;
    }

    public void setTrangThaiDH(int trangThaiDH) {
        TrangThaiDH = trangThaiDH;
    }

    public int getTrangThaiGH() {
        return TrangThaiGH;
    }

    public void setTrangThaiGH(int trangThaiGH) {
        TrangThaiGH = trangThaiGH;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }
}
