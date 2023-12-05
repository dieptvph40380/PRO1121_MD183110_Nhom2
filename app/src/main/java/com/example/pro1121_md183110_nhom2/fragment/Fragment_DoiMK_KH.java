package com.example.pro1121_md183110_nhom2.fragment;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pro1121_md183110_nhom2.R;
import com.example.pro1121_md183110_nhom2.model.KhachHang;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Locale;

public class Fragment_DoiMK_KH extends Fragment {

    EditText edtMKC,edtMKM,edtReMKM;
    Button btnXacNhan;
    FirebaseFirestore db;
    public Fragment_DoiMK_KH() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment__doi_m_k__k_h, container, false);
        edtMKC=v.findViewById(R.id.edt_MKC_KH);
        edtMKM=v.findViewById(R.id.edt_MKM_KH);
        edtReMKM=v.findViewById(R.id.edt_REMKM_KH);
        btnXacNhan=v.findViewById(R.id.btn_DoiMK_KH);


        db=FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferencesa= getActivity().getSharedPreferences("USER_FILE_KH",Context.MODE_PRIVATE);
        String MaKH= sharedPreferencesa.getString("USERNAME_KH","");

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference collectionReference= db.collection("KhachHang");

                collectionReference.whereEqualTo("TenDN",MaKH).get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                            KhachHang khachHang= documentSnapshot.toObject(KhachHang.class);
                            if(khachHang.getMatKhau().equals(edtMKC.getText().toString())){

                                collectionReference.document(documentSnapshot.getId()).update("MatKhau",edtMKM.getText().toString())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), "Đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }else {
                                Toast.makeText(getContext(), "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else {
                        Toast.makeText(getContext(), "lỗi truy vấn", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        return v;
    }
}