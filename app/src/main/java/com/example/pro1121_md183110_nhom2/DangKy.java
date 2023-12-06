package com.example.pro1121_md183110_nhom2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pro1121_md183110_nhom2.model.Admin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class DangKy extends AppCompatActivity {
    Button btnDK;
    FirebaseFirestore database;

    EditText edtDN,edtHT,edtMK,edtReMK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        database =FirebaseFirestore.getInstance();

        edtDN=findViewById(R.id.edtDN);
        edtHT=findViewById(R.id.edtHT);
        edtMK=findViewById(R.id.edtMK);
        edtReMK=findViewById(R.id.edtReMK);
        btnDK=findViewById(R.id.btnDangKy);

        btnDK.setOnClickListener(view -> {
            String tenDN=edtDN.getText().toString();
                    database.collection("Admin")
                            .whereEqualTo("MaAC", tenDN)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        // Nếu không có bản ghi nào có tên đăng nhập giống như tên đăng nhập mới
                                        if (task.getResult().isEmpty()) {
                                            if(validate()==1) {
                                                String tenDN = edtDN.getText().toString();
                                                String HoTen = edtHT.getText().toString();
                                                String MK = edtMK.getText().toString();

//                                            String ID=UUID.randomUUID().toString();
                                                Admin admin = new Admin(tenDN, HoTen, MK);
                                                HashMap<String, Object> mapAdmin = admin.convertHashMap();
                                                database.collection("Admin")
                                                        .document(tenDN)
                                                        .set(mapAdmin)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                if (checkMK() == 1) {
                                                                    Toast.makeText(DangKy.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(DangKy.this, DangNhap.class);
                                                                    startActivity(intent);
                                                                }

                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(DangKy.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }
                                        } else {
                                            // Tên đăng nhập đã tồn tại, xử lý thông báo hoặc hành động phù hợp
                                            Toast.makeText(DangKy.this, "Tên đăng nhập đã tồn tại, vui lòng chọn tên đăng nhập khác.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        // Xử lý khi truy vấn không thành công
                                        Toast.makeText(DangKy.this, "Lỗi khi kiểm tra tên đăng nhập.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
        });



    }
    public int checkMK(){
        int check;
        if(edtReMK.getText().toString().equals(edtMK.getText().toString())){
            check=1;
        } else {
            check =0;
            Toast.makeText(this, "Mật khẩu không trùng khớp  ", Toast.LENGTH_SHORT).show();
        }
        return check;
    }

    public int validate(){
        int validate;
        if(edtReMK.getText().length()==0||edtHT.getText().length()==0||edtDN.getText().length()==0){
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin  ", Toast.LENGTH_SHORT).show();
            validate=0;
        }else {
            validate =1;

        }
        return validate;
    }
}