package com.example.pro1121_md183110_nhom2.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pro1121_md183110_nhom2.DangNhap;
import com.example.pro1121_md183110_nhom2.Menu_Admin;
import com.example.pro1121_md183110_nhom2.R;
import com.example.pro1121_md183110_nhom2.model.Admin;
import com.example.pro1121_md183110_nhom2.model.KhachHang;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;


public class Fragment_DoiMK_AD extends Fragment {

    FirebaseFirestore database;

    EditText edtMKC,edtMKM,edtReMKM;
    Button btnXacNhanad;


    public Fragment_DoiMK_AD() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v=inflater.inflate(R.layout.fragment__doi_m_k_ad, container, false);

       edtMKC=v.findViewById(R.id.edt_MKC_AD);
       edtMKM=v.findViewById(R.id.edt_MKM_AD);
       edtReMKM=v.findViewById(R.id.edt_REMKM_AD);
       btnXacNhanad=v.findViewById(R.id.btn_DoiMK_AD);
       database=FirebaseFirestore.getInstance();

       SharedPreferences sharedPreferencesa= getActivity().getSharedPreferences("TTAD",Context.MODE_PRIVATE);
       String MaAC= sharedPreferencesa.getString("USERNAME_AD","");

       btnXacNhanad.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               CollectionReference collectionReference= database.collection("Admin");
               collectionReference.whereEqualTo("MaAC",MaAC).get().addOnCompleteListener(task -> {
                   if(task.isSuccessful()){
                       for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                           Admin khachHang= documentSnapshot.toObject(Admin.class);
                           if(checkMK()==1) {
                               if (khachHang.getMatKhau().equals(edtMKC.getText().toString())) {
                                   collectionReference.document(documentSnapshot.getId()).update("MatKhau", edtMKM.getText().toString())
                                           .addOnSuccessListener(new OnSuccessListener<Void>() {
                                               @Override
                                               public void onSuccess(Void unused) {
                                                   Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                                   Intent i = new Intent(getContext(), DangNhap.class);
                                                   startActivity(i);
                                               }
                                           }).addOnFailureListener(new OnFailureListener() {
                                               @Override
                                               public void onFailure(@NonNull Exception e) {
                                                   Toast.makeText(getContext(), "Đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
                                               }
                                           });
                               } else {
                                   Toast.makeText(getContext(), "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                               }
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
    public int checkMK(){
        int check;
        if(edtReMKM.getText().toString().equals(edtMKM.getText().toString())){
            check=1;
        } else {
            check =0;
            Toast.makeText(getContext(), "Mật khẩu không trùng khớp  ", Toast.LENGTH_SHORT).show();
        }
        return check;
    }



}