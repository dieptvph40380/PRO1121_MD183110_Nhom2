package com.example.pro1121_md183110_nhom2.model;

public class GioHang {
private String MaGH;
private Long ThanhTien;
private int SoLuong;
private String Size;
private String MaKH;
private String MaSP;

    public GioHang() {
    }

    public GioHang(String maGH, Long thanhTien, int soLuong, String size, String maKH, String maSP) {
        MaGH = maGH;
        ThanhTien = thanhTien;
        SoLuong = soLuong;
        Size = size;
        MaKH = maKH;
        MaSP = maSP;
    }

    public String getMaGH() {
        return MaGH;
    }

    public void setMaGH(String maGH) {
        MaGH = maGH;
    }

    public Long getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(Long thanhTien) {
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

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String maSP) {
        MaSP = maSP;
    }
}
