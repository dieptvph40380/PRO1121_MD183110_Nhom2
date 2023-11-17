package com.example.pro1121_md183110_nhom2.model;

import java.util.HashMap;

public class Admin {
//    private String ID;
   private String MaAC;
   private String TenAC;
   private String MatKhau;



    public HashMap<String, Object> convertHashMap(){
        HashMap<String, Object> Admin = new HashMap<>();
//        Admin.put("ID",ID);
        Admin.put("MaAC",MaAC);
        Admin.put("TenAC",TenAC);
        Admin.put("MatKhau",MatKhau);
        return Admin;
    }
    public Admin() {
    }

    public Admin( String maAC, String tenAC, String matKhau) {
//        this.ID = ID;
        MaAC = maAC;
        TenAC = tenAC;
        MatKhau = matKhau;
    }



//    public String getID() {
//        return ID;
//    }
//
//    public void setID(String ID) {
//        this.ID = ID;
//    }

    public String getMaAC() {
        return MaAC;
    }

    public void setMaAC(String maAC) {
        MaAC = maAC;
    }

    public String getTenAC() {
        return TenAC;
    }

    public void setTenAC(String tenAC) {
        TenAC = tenAC;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }
}
