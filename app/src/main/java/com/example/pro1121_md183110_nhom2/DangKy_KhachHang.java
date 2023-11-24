package com.example.pro1121_md183110_nhom2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pro1121_md183110_nhom2.model.Admin;
import com.example.pro1121_md183110_nhom2.model.KhachHang;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.UUID;

public class DangKy_KhachHang extends AppCompatActivity {
    Button btnDangKy_KH, btnHuy_KH;
    FirebaseFirestore database;

    EditText edtDN_KH, edtSDT_KH, edtUserName_KH, edtPassword_KH, edtePassword_KH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_khach_hang);
        database = FirebaseFirestore.getInstance();

        edtDN_KH = findViewById(R.id.edtDN_KH);
        edtSDT_KH = findViewById(R.id.edtSDT_KH);
        edtUserName_KH = findViewById(R.id.edtUserName_KH);
        edtPassword_KH = findViewById(R.id.edtPassword_KH);
        edtePassword_KH = findViewById(R.id.edtePassword_KH);
        btnDangKy_KH = findViewById(R.id.btnDangKy_KH);
        btnHuy_KH = findViewById(R.id.btnHuy_KH);

        btnDangKy_KH.setOnClickListener(view -> {
            String tenDNKH = edtDN_KH.getText().toString();
            database.collection("KhachHang")
                    .whereEqualTo("TenDN", tenDNKH)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                // Nếu không có bản ghi nào có tên đăng nhập giống như tên đăng nhập mới
                                if (task.getResult().isEmpty()) {
                                    String tenDNKH = edtDN_KH.getText().toString();
                                    String SDTKH = edtSDT_KH.getText().toString();
                                    String HoTenKH = edtUserName_KH.getText().toString();
                                    String MKKH = edtPassword_KH.getText().toString();
                                           String MaKH= UUID.randomUUID().toString();
                                    KhachHang khachHang = new KhachHang(MaKH,tenDNKH,SDTKH, HoTenKH, MKKH);
                                    HashMap<String, Object> mapKhachHang = khachHang.convertHashMap();
                                    database.collection("KhachHang").document(tenDNKH)
                                            .set(mapKhachHang)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void avoid) {

                                                    if (CheckMK() == 1) {
                                                        Toast.makeText(DangKy_KhachHang.this, "Đăng ký thành công" , Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(DangKy_KhachHang.this, DangNhap_KhachHang.class);
                                                        startActivity(intent);

                                                    }
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(DangKy_KhachHang.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    // Tên đăng nhập đã tồn tại, xử lý thông báo hoặc hành động phù hợp
                                    Toast.makeText(DangKy_KhachHang.this, "Tên đăng nhập đã tồn tại, vui lòng chọn tên đăng nhập khác.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Xử lý khi truy vấn không thành công
                                Toast.makeText(DangKy_KhachHang.this, "Lỗi khi kiểm tra tên đăng nhập.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

        btnHuy_KH.setOnClickListener(view -> {
            edtDN_KH.setText("");
            edtSDT_KH.setText("");
            edtUserName_KH.setText("");
            edtePassword_KH.setText("");
            edtPassword_KH.setText("");
        });

    }

    public int CheckMK() {
        int checkk;
        if (edtePassword_KH.getText().toString().equals(edtPassword_KH.getText().toString())) {
            checkk = 1;
        } else {
            checkk = 0;
            Toast.makeText(this, "Mật khẩu không trùng khớp  ", Toast.LENGTH_SHORT).show();
        }
        return checkk;
    }
}