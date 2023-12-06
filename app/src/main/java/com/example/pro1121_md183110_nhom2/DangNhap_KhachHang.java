package com.example.pro1121_md183110_nhom2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DangNhap_KhachHang extends AppCompatActivity {
    Button btnDangNhap_KH,btnDangKy_KH, btnHuy_KH;
    FirebaseFirestore database;

    EditText edtDN_KH,edtMK_KH;
    CheckBox chkRememberPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap_khach_hang);
        database = FirebaseFirestore.getInstance();

        edtDN_KH = findViewById(R.id.edtDN_KH);
        edtMK_KH = findViewById(R.id.edtMK_KH);
        btnDangNhap_KH = findViewById(R.id.btnDangNhap_KH);
        btnDangKy_KH = findViewById(R.id.btnDangKy_KH);
        chkRememberPass=findViewById(R.id.chkLuuMK_KH);
        SharedPreferences pref=getSharedPreferences("USER_FILE_KH",MODE_PRIVATE);
        String user_kh = pref.getString("USERNAME_KH","");
        String pass_kh = pref.getString("PASSWORD_KH","");
        Boolean rem_kh = pref.getBoolean("REMEMBER_KH",false);


        edtDN_KH.setText(user_kh);
        edtMK_KH .setText(pass_kh);
        chkRememberPass.setChecked(rem_kh);
        btnDangNhap_KH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { checkLoginf();

            }
        });

    }
    public void checkLoginf(){
        String TenDN= edtDN_KH.getText().toString().trim();
        String MK =edtMK_KH.getText().toString().trim();
        database.collection("KhachHang")
                .whereEqualTo("TenDN", TenDN)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                Toast.makeText(DangNhap_KhachHang.this, "Sai tên đăng nhập", Toast.LENGTH_SHORT).show();
                            }else {
                                String passwordFromDatabase = task.getResult().getDocuments().get(0).getString("MatKhau");
                                if (passwordFromDatabase != null && passwordFromDatabase.equals(MK)) {
                                    // Mật khẩu đúng
                                    Toast.makeText(DangNhap_KhachHang.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    SharedPreferences pef =getSharedPreferences("TTKH",MODE_PRIVATE);
                                    SharedPreferences.Editor edit= pef.edit();
                                    edit.putString("USERNAME_KH",TenDN);
                                    edit.commit();
                                    rememberUser(TenDN, MK, chkRememberPass.isChecked());
                                    Intent i = new Intent(DangNhap_KhachHang.this, Menu_KhanhHang.class);
                                    startActivity(i);
                                } else {
                                    // Mật khẩu không đúng
                                    Toast.makeText(DangNhap_KhachHang.this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }else {
                            Toast.makeText(DangNhap_KhachHang.this, "Lỗi khi kiểm tra", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void rememberUser(String ukh , String pkh ,boolean statuskh){
        SharedPreferences pef =getSharedPreferences("USER_FILE_KH",MODE_PRIVATE);
        SharedPreferences.Editor edit= pef.edit();
        if(!statuskh){
            // xóa tình trạng lưu trữ trước đó
            edit.clear();
        }else {
            //Lưu trữ dữ liệu
            edit.putString("USERNAME_KH",ukh);
            edit.putString("PASSWORD_KH",pkh);
            edit.putBoolean("REMEMBER_KH",statuskh);
        }
        //Lưu toàn bộ
        edit.commit();
    }

}