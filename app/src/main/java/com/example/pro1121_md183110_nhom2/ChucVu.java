package com.example.pro1121_md183110_nhom2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class ChucVu extends AppCompatActivity {
    Button btnAdmin,btnNhanVien,btnKhachHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuc_vu);

        btnAdmin=findViewById(R.id.btnAdmin);
        btnKhachHang=findViewById(R.id.btnKhachHang);
        btnNhanVien=findViewById(R.id.btnNhanVien);


    }
}