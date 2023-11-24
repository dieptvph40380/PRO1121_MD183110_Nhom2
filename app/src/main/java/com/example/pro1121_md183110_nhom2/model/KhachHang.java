package com.example.pro1121_md183110_nhom2.model;

import java.util.HashMap;

public class KhachHang {
    private String MaKH;
    private String SDT;
    private String HoVaTen;
    private String TenDN;
    private String MatKhau;


    public HashMap<String, Object> convertHashMap() {
        HashMap<String, Object> KhachHang = new HashMap<>();
        KhachHang.put("MaKH", MaKH);
        KhachHang.put("SDT", SDT);
        KhachHang.put("HoVaten", HoVaTen);
        KhachHang.put("TenDN", TenDN);
        KhachHang.put("MatKhau", MatKhau);
        return KhachHang;
    }

    public KhachHang() {
    }

    public KhachHang(String maKH, String SDT, String hoVaTen, String tenDN, String matKhau) {
        MaKH = maKH;
        this.SDT = SDT;
        HoVaTen = hoVaTen;
        TenDN = tenDN;
        MatKhau = matKhau;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String maKH) {
        MaKH = maKH;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getHoVaTen() {
        return HoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        HoVaTen = hoVaTen;
    }

    public String getTenDN() {
        return TenDN;
    }

    public void setTenDN(String tenDN) {
        TenDN = tenDN;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }
}

