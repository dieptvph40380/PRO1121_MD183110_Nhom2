package com.example.pro1121_md183110_nhom2.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pro1121_md183110_nhom2.R;
import com.example.pro1121_md183110_nhom2.adapter.NhanVienAdapter;
import com.example.pro1121_md183110_nhom2.adapter.SanPhamAdapter;
import com.example.pro1121_md183110_nhom2.adapter.SpinnerAdapter;
import com.example.pro1121_md183110_nhom2.model.LoaiSanPham;
import com.example.pro1121_md183110_nhom2.model.NhanVien;
import com.example.pro1121_md183110_nhom2.model.SanPham;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class Fragment_QL_SanPham extends Fragment {

    FirebaseFirestore db;
    RecyclerView rcv;
    FloatingActionButton fab;
    Dialog dialog;
    EditText maloai, tenloai, soluong, tensp, giasp, thanhphan, luongcalo, khoiluong;
    Spinner spnloai;
    ArrayList<SanPham> list;

    SanPhamAdapter adapter;
    Context context;
    ArrayList<LoaiSanPham> loaiSanPhamList;
    SpinnerAdapter spinnerAdapter;
    Button btnthem, btnhuy;
    SanPhamAdapter sanPhamAdapter;


    FirebaseFirestore database;

    public Fragment_QL_SanPham() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__q_l__san_pham, container, false);
        list = new ArrayList<>();
        fab = view.findViewById(R.id.floatAdd_SP);
        rcv = view.findViewById(R.id.recycler_SP);
        db = FirebaseFirestore.getInstance();
        ListenFirebaseFirestore();

        // nhanVienAdapter= new NhanVienAdapter(nvList,getContext(),db);


        adapter = new SanPhamAdapter(list, getContext(), db);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(context, 0);
            }
        });


        return view;
    }

    private void ListenFirebaseFirestore() {
        db.collection("SanPham").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("TAG", "fail", error);
                    return;
                }
                if (value != null) {
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        switch (dc.getType()) {
                            case ADDED: {
                                SanPham newU = dc.getDocument().toObject(SanPham.class);
                                list.add(newU);
                                adapter.notifyItemInserted(list.size() - 1);
                                break;
                            }
                            case MODIFIED: {
                                SanPham update = dc.getDocument().toObject(SanPham.class);
                                if (dc.getOldIndex() == dc.getNewIndex()) {
                                    list.set(dc.getOldIndex(), update);
                                    adapter.notifyItemChanged(dc.getOldIndex());

                                } else {
                                    list.remove(dc.getOldIndex());
                                    list.add(update);
                                    adapter.notifyItemMoved(dc.getOldIndex(), dc.getNewIndex());

                                }
                                break;
                            }
                            case REMOVED: {
                                dc.getDocument().toObject(SanPham.class);
                                list.remove(dc.getOldIndex());
                                adapter.notifyItemRemoved(dc.getOldIndex());
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    public void openDialog(final Context context, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.dialog_them_sp, null);
        builder.setView(view);
        Dialog dialog1 = builder.create();
        dialog1.show();
        tensp = view.findViewById(R.id.edt_Ten_SP);
        giasp = view.findViewById(R.id.edt_Gia_SP);
        spnloai = view.findViewById(R.id.spn_TenLoai);

        khoiluong = view.findViewById(R.id.edt_Klg_SP);
        luongcalo = view.findViewById(R.id.edt_LgCalo_SP);
        thanhphan = view.findViewById(R.id.edt_TP_SP);

        btnthem = view.findViewById(R.id.btn_Them_SP);
        btnhuy = view.findViewById(R.id.btn_HuyT_SP);

        loaiSanPhamList = new ArrayList<>();
        spinnerAdapter = new SpinnerAdapter(getContext(), loaiSanPhamList);
        spnloai.setAdapter(spinnerAdapter);
        getLoaiSanPham();
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SanPham sanPham = new SanPham();
                String masanpham = UUID.randomUUID().toString();
                sanPham.setMaSP(masanpham);
//                Log.e("TAG", "onClick: "+123);
                sanPham.setThanhPhan(thanhphan.getText().toString());
                sanPham.setLuongCalo(luongcalo.getText().toString());
                sanPham.setGia(Integer.parseInt(giasp.getText().toString()));
                sanPham.setKhoiLuong(khoiluong.getText().toString());
                sanPham.setTenSP(tensp.getText().toString());
                sanPham.setMaLoai(loaiSanPhamList.get(spnloai.getSelectedItemPosition()).getMaLSP());
                db.collection("SanPham").document(masanpham).set(sanPham.convertHashMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
dialog1.dismiss();
                        }
                        else {
                            Toast.makeText(getContext(), "thêm thất bại", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

    }

    private void getLoaiSanPham() {
        db.collection("LoaiSanPham").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isComplete()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.e("TAG", "onComplete: "+document.toObject(LoaiSanPham.class).getTenLSP());
                        loaiSanPhamList.add(document.toObject(LoaiSanPham.class));
                        spinnerAdapter.notifyDataSetChanged();
                    }
                }

            }
        });

    }
}


