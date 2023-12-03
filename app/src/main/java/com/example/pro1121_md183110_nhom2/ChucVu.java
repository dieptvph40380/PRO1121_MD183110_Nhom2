package com.example.pro1121_md183110_nhom2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pro1121_md183110_nhom2.model.NhanVien;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.UUID;

public class ChucVu extends AppCompatActivity {
    Button btnAdmin,btnNhanVien,btnKhachHang, btnMaPin;

    EditText edtMaPin;
    Dialog dialog;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuc_vu);

        btnAdmin=findViewById(R.id.btnAdmin);
        btnKhachHang=findViewById(R.id.btnKhachHang);
        btnNhanVien=findViewById(R.id.btnNhanVien);

        btnAdmin.setOnClickListener(view ->{
            openDialog(context,0);
        });
        btnNhanVien.setOnClickListener(view ->{
            Intent intent=new Intent(ChucVu.this, DangNhap_NhanVien.class);
            startActivity(intent);
        });
        btnKhachHang.setOnClickListener(view ->{
            Intent intent=new Intent(ChucVu.this, DangNhap_KhachHang.class);
            startActivity(intent);
        });

    }
    public void openDialog(final Context context, final int type){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.edt_chucvu);

        edtMaPin=dialog.findViewById(R.id.edt_MaPin);
        btnMaPin=dialog.findViewById(R.id.btn_MaPin);

        btnMaPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtMaPin.getText().toString().equals("Nhom2")){
                    Intent intent=new Intent(ChucVu.this, DangNhap.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(ChucVu.this, "Nhập sai mã pin" +
                            "Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                    edtMaPin.setText("");
                }
            }
        });

        dialog.show();
    }
}