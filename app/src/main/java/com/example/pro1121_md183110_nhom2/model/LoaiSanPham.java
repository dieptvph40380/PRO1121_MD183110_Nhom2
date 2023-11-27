package com.example.pro1121_md183110_nhom2.model;

import java.util.HashMap;

public class LoaiSanPham {
    private  String MaLSP;
  private  String TenLSP;
   private String NSXLSP;

    public HashMap<String, Object> convertHashMap() {
        HashMap<String, Object> LoaiSanPham = new HashMap<>();
        LoaiSanPham.put("MaLSP", MaLSP);
        LoaiSanPham.put("TenLSP", TenLSP);
        LoaiSanPham.put("NSXLSP", NSXLSP);

        return LoaiSanPham;
    }

    public LoaiSanPham() {
    }

    public LoaiSanPham(String maLSP, String tenLSP, String NSXLSP) {
        MaLSP = maLSP;
        TenLSP = tenLSP;
        this.NSXLSP = NSXLSP;
    }

    public String getMaLSP() {
        return MaLSP;
    }

    public void setMaLSP(String maLSP) {
        MaLSP = maLSP;
    }

    public String getTenLSP() {
        return TenLSP;
    }

    public void setTenLSP(String tenLSP) {
        TenLSP = tenLSP;
    }

    public String getNSXLSP() {
        return NSXLSP;
    }

    public void setNSXLSP(String NSXLSP) {
        this.NSXLSP = NSXLSP;
    }
}
