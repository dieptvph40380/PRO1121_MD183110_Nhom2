package com.example.pro1121_md183110_nhom2.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pro1121_md183110_nhom2.R;
import com.example.pro1121_md183110_nhom2.model.Admin;
import com.example.pro1121_md183110_nhom2.model.NhanVien;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Fragment_DoiMK_AD extends Fragment {
    ArrayList<Admin> list = new ArrayList<>();
    Context context;
    int position;
    FirebaseFirestore database;

    EditText edtMKC,edtMKM,edtReMKM;
    Button btnXacNhan;


    public Fragment_DoiMK_AD() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v=inflater.inflate(R.layout.fragment__doi_m_k_ad, container, false);

       database=FirebaseFirestore.getInstance();
       list=new ArrayList<Admin>();


       edtMKC=v.findViewById(R.id.edt_MKC_AD);
       edtMKM=v.findViewById(R.id.edt_MKM_AD);
       edtReMKM=v.findViewById(R.id.edt_REMKM_AD);
       btnXacNhan=v.findViewById(R.id.btn_DoiMK_AD);

       btnXacNhan.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DoiMK(position);
           }
       });

       return v;
    }



//    private void ListenFirebaseFirestore(){
//        database.collection("NhanVien").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if(error != null){
//                    Log.e("TAG", "fail", error);
//                    return;
//                }
//                if(value != null){
//                    for (DocumentChange dc: value.getDocumentChanges()){
//                        switch (dc.getType()){
//                            case ADDED:{
//                                Admin newU = dc.getDocument().toObject(Admin.class);
//                                list.add(newU);
//                                adapter.notifyItemInserted(list.size() - 1);
//                                break;
//                            }
//                            case MODIFIED:{
//                                Admin update = dc.getDocument().toObject(Admin.class);
//                                if(dc.getOldIndex() == dc.getNewIndex()){
//                                    list.set(dc.getOldIndex(), update);
//                                    adapter.notifyItemChanged(dc.getOldIndex());
//
//                                } else {
//                                    list.remove(dc.getOldIndex());
//                                    list.add(update);
//                                    adapter.notifyItemMoved(dc.getOldIndex(), dc.getNewIndex());
//
//                                }
//                                break;
//                            }
//                            case REMOVED:{
//                                dc.getDocument().toObject(Admin.class);
//                                list.remove(dc.getOldIndex());
//                                adapter.notifyItemRemoved(dc.getOldIndex());
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
//        });
//    }


    public void DoiMK(int position){
        String MaAC = list.get(position).getMaAC();
        Toast.makeText(getContext(), ""+MaAC, Toast.LENGTH_SHORT).show();
        String TenAC = list.get(position).getTenAC();
        String MatKhau= edtMKC.getText().toString();
        String MKM = edtMKM.getText().toString();
        String REMKM= edtReMKM.getText().toString();
        if (MKM.equals(REMKM)){
            Admin admin= new Admin(MaAC,TenAC,MatKhau);
            HashMap<String, Object> mapad = admin.convertHashMap();
            database.collection("NhanVien")
                    .document(MaAC)
                    .update(mapad)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });

        }else {
            Toast.makeText(getContext(), "NHập mật khẩu không trùng vs nhau", Toast.LENGTH_SHORT).show();
        }

    }
}