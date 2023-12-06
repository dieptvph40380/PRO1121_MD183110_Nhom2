package com.example.pro1121_md183110_nhom2.fragment;

import android.app.Dialog;
import android.content.Context;
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

import com.example.pro1121_md183110_nhom2.R;
import com.example.pro1121_md183110_nhom2.adapter.LoaiSanPhamAdapter;
import com.example.pro1121_md183110_nhom2.adapter.NhanVienAdapter;
import com.example.pro1121_md183110_nhom2.model.LoaiSanPham;
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


public class Fragment_QL_LoaiSanPham extends Fragment {

    FirebaseFirestore db;
    RecyclerView rcv;
    FloatingActionButton fab;
    Dialog dialog;
    EditText tenlsp, nsx;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<LoaiSanPham> spList = new ArrayList<>();
    LoaiSanPhamAdapter adapter;
    Context context;
    Button btnthem,btnhuy;

    public Fragment_QL_LoaiSanPham() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment__q_l__loai_san_pham, container, false);
        View view=inflater.inflate(R.layout.fragment__q_l__loai_san_pham, container, false);

        fab=view.findViewById(R.id.floatAdd_AD);
        rcv=view.findViewById(R.id.recycler_LoaiSP);
        db=FirebaseFirestore.getInstance();

        adapter = new LoaiSanPhamAdapter(spList,getContext(),db);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(adapter);
        ListenFirebaseFirestore();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(context ,0);

            }
        });
        return view;
    }


    private void ListenFirebaseFirestore(){
        db.collection("LoaiSanPham")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                                LoaiSanPham newU = dc.getDocument().toObject(LoaiSanPham.class);
                                spList.add(newU);
                                adapter.notifyItemInserted(spList.size() - 1);
                                break;
                            }
                            case MODIFIED:{
                                LoaiSanPham update = dc.getDocument().toObject(LoaiSanPham.class);
                                if(dc.getOldIndex() == dc.getNewIndex()){
                                    spList.set(dc.getOldIndex(), update);
                                    adapter.notifyItemChanged(dc.getOldIndex());

                                } else {
                                    spList.remove(dc.getOldIndex());
                                    spList.add(update);
                                    adapter.notifyItemMoved(dc.getOldIndex(), dc.getNewIndex());

                                }
                                break;
                            }
                            case REMOVED:{
                                dc.getDocument().toObject(LoaiSanPham.class);
                                spList.remove(dc.getOldIndex());
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
        dialog.setContentView(R.layout.dialog_them_loaisp);

        tenlsp = dialog.findViewById(R.id.edt_TenLSP);
        nsx = dialog.findViewById(R.id.edt_NSX);
        btnthem = dialog.findViewById(R.id.btn_Them_LSP);
        btnhuy = dialog.findViewById(R.id.btn_HuyT_LSP);

        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TenLSP=tenlsp.getText().toString();
                db.collection("LoaiSanPham")
                        .whereEqualTo("TenLSP", TenLSP)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    // Nếu không có bản ghi nào có tên đăng nhập giống như tên đăng nhập mới
                                    if (task.getResult().isEmpty()) {
                                        String Tenlsp=tenlsp.getText().toString();
                                        String NSX=nsx.getText().toString();

                                        String MaLSP= UUID.randomUUID().toString();

                                        LoaiSanPham lsp = new LoaiSanPham(MaLSP,Tenlsp,NSX);
                                        HashMap<String, Object> MapLSP = lsp.convertHashMap();
                                        db.collection("LoaiSanPham")
                                                .document(MaLSP)
                                                .set(MapLSP)
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
                                                        dialog.dismiss();
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

            }
        });
        dialog.show();
    }
}