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

public class DangNhap_NhanVien extends AppCompatActivity {
    FirebaseFirestore database;
    EditText edtDN_NV,edtMK_NV;
    Button btnDN,btnDK,btnHuy;

    CheckBox chkLuuMK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap_nhan_vien);

        database =FirebaseFirestore.getInstance();
        edtDN_NV=findViewById(R.id.edtDN_NV);
        edtMK_NV=findViewById(R.id.edtMK_NV);
        btnDN=findViewById(R.id.btnDangNhap_NV);
        chkLuuMK=findViewById(R.id.chkLuuMK_NV);

        SharedPreferences pref=getSharedPreferences("USER_FILE_NV",MODE_PRIVATE);
        String user_nv = pref.getString("USERNAME_NV","");
        String pass_nv= pref.getString("PASSWORD_NV","");
        Boolean rem_nv = pref.getBoolean("REMEMBER_NV",false);


        edtDN_NV.setText(user_nv);
        edtMK_NV.setText(pass_nv);
        chkLuuMK.setChecked(rem_nv);

        btnDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }
    public void checkLogin(){
        if(validate()==1) {
            String TenDN_NV = edtDN_NV.getText().toString().trim();
            String MatKhau_NV = edtMK_NV.getText().toString().trim();
            database.collection("NhanVien")
                    .whereEqualTo("User", TenDN_NV)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().isEmpty()) {
                                    Toast.makeText(DangNhap_NhanVien.this, "Sai tên đăng nhập", Toast.LENGTH_SHORT).show();
                                } else {
                                    String passwordFromDatabase = task.getResult().getDocuments().get(0).getString("Pass");
                                    if (passwordFromDatabase != null && passwordFromDatabase.equals(MatKhau_NV)) {
                                        // Mật khẩu đúng
                                        Toast.makeText(DangNhap_NhanVien.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                        rememberUser1(TenDN_NV, MatKhau_NV, chkLuuMK.isChecked());
                                        Intent i = new Intent(DangNhap_NhanVien.this, Menu_NhanVien.class);
                                        startActivity(i);
                                    } else {
                                        // Mật khẩu không đúng
                                        Toast.makeText(DangNhap_NhanVien.this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                Toast.makeText(DangNhap_NhanVien.this, "Lỗi khi kiểm tra", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    public void rememberUser1(String unv , String pnv ,boolean statusnv){
        SharedPreferences pef =getSharedPreferences("USER_FILE_NV",MODE_PRIVATE);
        SharedPreferences.Editor edit= pef.edit();
        if(!statusnv){
            // xóa tình trạng lưu trữ trước đó
            edit.clear();
        }else {
            //Lưu trữ dữ liệu
            edit.putString("USERNAME_NV",unv);
            edit.putString("PASSWORD_NV",pnv);
            edit.putBoolean("REMEMBER_NV",statusnv);
        }
        //Lưu toàn bộ
        edit.commit();
    }
    public int validate(){
        int validate;
        if(edtDN_NV.getText().length()==0||edtMK_NV.getText().length()==0){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin  ", Toast.LENGTH_SHORT).show();
            validate=0;
        }else {
            validate =1;

        }
        return validate;
    }
}