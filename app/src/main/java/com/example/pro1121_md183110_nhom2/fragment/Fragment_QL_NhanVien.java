package com.example.pro1121_md183110_nhom2.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pro1121_md183110_nhom2.DangKy;
import com.example.pro1121_md183110_nhom2.DangNhap;
import com.example.pro1121_md183110_nhom2.R;
import com.example.pro1121_md183110_nhom2.adapter.NhanVienAdapter;
import com.example.pro1121_md183110_nhom2.database.Dbhelper;
import com.example.pro1121_md183110_nhom2.model.Admin;
import com.example.pro1121_md183110_nhom2.model.NhanVien;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class Fragment_QL_NhanVien extends Fragment {

    FirebaseFirestore db;
    RecyclerView rcv;
    FloatingActionButton fab;
    Dialog dialog;
    Context context;
    EditText tennv, sdt, user, pass;
    ArrayList<NhanVien> nvList = new ArrayList<>();
    NhanVienAdapter adapter;

    Button btnthem,btnhuy;


    public Fragment_QL_NhanVien() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment__q_l__nhan_vien, container, false);

        fab=view.findViewById(R.id.floatAdd_NV);
        rcv=view.findViewById(R.id.recycler_NV);
        db=FirebaseFirestore.getInstance();
        ListenFirebaseFirestore();

        adapter = new NhanVienAdapter(nvList,getContext(),db);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(context ,0);

            }
        });
        return view;
    }


    private void ListenFirebaseFirestore(){
        db.collection("NhanVien").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.e("TAG", "fail", error);
                    return;
                }
                if(value != null){
                    for (DocumentChange dc: value.getDocumentChanges()){
                        switch (dc.getType()){
                            case ADDED:{
                                NhanVien newU = dc.getDocument().toObject(NhanVien.class);
                                nvList.add(newU);
                                adapter.notifyItemInserted(nvList.size() - 1);
                                break;
                            }
                            case MODIFIED:{
                                NhanVien update = dc.getDocument().toObject(NhanVien.class);
                                if(dc.getOldIndex() == dc.getNewIndex()){
                                    nvList.set(dc.getOldIndex(), update);
                                    adapter.notifyItemChanged(dc.getOldIndex());

                                } else {
                                    nvList.remove(dc.getOldIndex());
                                    nvList.add(update);
                                    adapter.notifyItemMoved(dc.getOldIndex(), dc.getNewIndex());

                                }
                                break;
                            }
                            case REMOVED:{
                                dc.getDocument().toObject(NhanVien.class);
                                nvList.remove(dc.getOldIndex());
                                adapter.notifyItemRemoved(dc.getOldIndex());
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    public void openDialog(final Context context, final int type){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_them_nv);

        tennv = dialog.findViewById(R.id.edt_TenNV);
        sdt = dialog.findViewById(R.id.edt_SDT_NV);
        user = dialog.findViewById(R.id.edt_User_NV);
        pass = dialog.findViewById(R.id.edt_Pass_NV);
        btnthem = dialog.findViewById(R.id.btn_Them_NV);
        btnhuy = dialog.findViewById(R.id.btn_HuyT_NV);
        //adad

        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String User=user.getText().toString();
                db.collection("NhanVien")
                        .whereEqualTo("User", User)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    // Nếu không có bản ghi nào có tên đăng nhập giống như tên đăng nhập mới
                                    if (task.getResult().isEmpty()) {
                                        String TenNV=tennv.getText().toString();
                                        String SDT=sdt.getText().toString();
                                        String User=user.getText().toString();
                                        String Pass=pass.getText().toString();
                                        String MaNV=UUID.randomUUID().toString();

                                        NhanVien nv = new NhanVien(MaNV,TenNV,SDT,User,Pass);
                                        HashMap<String, Object> mapNV = nv.convertHashMap();
                                        db.collection("NhanVien")
                                                .document(MaNV)
                                                .set(mapNV)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();

                                                    }
                                                });
                                    } else {
                                        // Tên đăng nhập đã tồn tại, xử lý thông báo hoặc hành động phù hợp
                                        Toast.makeText(getContext(), "Tên đăng nhập đã tồn tại, vui lòng chọn tên đăng nhập khác.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // Xử lý khi truy vấn không thành công
                                    Toast.makeText(getContext(), "Lỗi khi kiểm tra tên đăng nhập.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}