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

public class DangNhap extends AppCompatActivity {
    FirebaseFirestore database;
    EditText edtDN,edtMK;
    Button btnDN,btnDK,btnHuy;

    CheckBox chkLuuMK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        database =FirebaseFirestore.getInstance();
        edtDN=findViewById(R.id.edtDN);
        edtMK=findViewById(R.id.edtMK);
        btnDK=findViewById(R.id.btnDangKy);
        btnDN=findViewById(R.id.btnDangNhap);
        btnHuy=findViewById(R.id.btnHuy);
        chkLuuMK=findViewById(R.id.chkLuuMK);

        SharedPreferences pref=getSharedPreferences("USER_FILE",MODE_PRIVATE);
        String user = pref.getString("USERNAME","");
        String pass= pref.getString("PASSWORD","");
        Boolean rem = pref.getBoolean("REMEMBER",false);


        edtDN.setText(user);
        edtMK .setText(pass);
        chkLuuMK.setChecked(rem);
        btnDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtDN.setText("");
                edtMK.setText("");
            }
        });
        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(DangNhap.this,DangKy.class);
                startActivity(intent);
            }
        });

    }
    public void checkLogin(){
        String TenDN= edtDN.getText().toString().trim();
        String MatKhau = edtMK.getText().toString().trim();
        database.collection("Admin")
                .whereEqualTo("MaAC", TenDN)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                Toast.makeText(DangNhap.this, "sai ten dang nhap", Toast.LENGTH_SHORT).show();
                            }else {
                                String passwordFromDatabase = task.getResult().getDocuments().get(0).getString("MatKhau");
                                if (passwordFromDatabase != null && passwordFromDatabase.equals(MatKhau)) {
                                    // Mật khẩu đúng
                                    Toast.makeText(DangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    rememberUser(TenDN, MatKhau, chkLuuMK.isChecked());
                                    Intent i = new Intent(DangNhap.this, DangKy.class);
                                    startActivity(i);
                                } else {
                                    // Mật khẩu không đúng
                                    Toast.makeText(DangNhap.this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }else {
                            Toast.makeText(DangNhap.this, "loi khi kiem tra", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void rememberUser(String u , String p ,boolean status){
        SharedPreferences pef =getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor edit= pef.edit();
        if(!status){
            // xóa tình trạng lưu trữ trước đó
            edit.clear();
        }else {
            //Lưu trữ dữ liệu
            edit.putString("USERNAME",u);
            edit.putString("PASSWORD",p);
            edit.putBoolean("REMEMBER",status);
        }
        //Lưu toàn bộ
        edit.commit();
    }

}