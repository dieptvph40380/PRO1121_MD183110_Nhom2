package com.example.pro1121_md183110_nhom2.fragment;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.example.pro1121_md183110_nhom2.adapter.KhachHangAdapter;
import com.example.pro1121_md183110_nhom2.adapter.NhanVienAdapter;
import com.example.pro1121_md183110_nhom2.model.KhachHang;
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

import org.checkerframework.checker.units.qual.K;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class Fragment_QL_KhachHang extends Fragment {
    FirebaseFirestore db_KH;
    RecyclerView rcv_KH;
    FloatingActionButton fab_KH;
    Dialog dialog_KH;
    EditText tenkh, sdtkh, userkh, passkh;
    ArrayList<String> List = new ArrayList<>();
    ArrayList<KhachHang> khList = new ArrayList<>();
    KhachHangAdapter Adapter;
    Context context;
    Button btnthem,btnhuy;

    public Fragment_QL_KhachHang() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment__q_l__khach_hang, container, false);
        fab_KH=view.findViewById(R.id.floatAdd_KH);
        rcv_KH=view.findViewById(R.id.recycler_KH);
        db_KH=FirebaseFirestore.getInstance();


        Adapter = new KhachHangAdapter(khList,getContext(),db_KH);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv_KH.setLayoutManager(linearLayoutManager);
        rcv_KH.setAdapter(Adapter);
        ListenFirebaseFirestore();
        fab_KH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(context ,0);
            }
        });
        return view;
    }
    private void ListenFirebaseFirestore() {
    db_KH.collection("KhachHang").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                            KhachHang newU = dc.getDocument().toObject(KhachHang.class);
                            khList.add(newU);
                            Adapter.notifyItemInserted(khList.size() - 1);
                            break;
                        }
                        case MODIFIED:{
                            KhachHang update = dc.getDocument().toObject(KhachHang.class);
                            if(dc.getOldIndex() == dc.getNewIndex()){
                                khList.set(dc.getOldIndex(), update);
                                Adapter.notifyItemChanged(dc.getOldIndex());

                            } else {
                                khList.remove(dc.getOldIndex());
                                khList.add(update);
                                Adapter.notifyItemMoved(dc.getOldIndex(), dc.getNewIndex());

                            }
                            break;
                        }
                        case REMOVED:{
                            dc.getDocument().toObject(KhachHang.class);
                            khList.remove(dc.getOldIndex());
                            Adapter.notifyItemRemoved(dc.getOldIndex());
                            break;
                        }
                    }
                }
            }
        }
    });
    }
    public void openDialog(final Context context, final int type){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.dialog_them_khachhang, null);
        builder.setView(view);
        Dialog dialog1 = builder.create();
        dialog1.show();
        dialog_KH = new Dialog(getContext());
        dialog_KH.setContentView(R.layout.dialog_them_khachhang);

        tenkh = dialog1.findViewById(R.id.edt_TenKH);
        sdtkh = dialog1.findViewById(R.id.edt_SDT_KH);
        userkh = dialog1.findViewById(R.id.edt_User_KH);
        passkh = dialog1.findViewById(R.id.edt_Pass_KH);
        btnthem = dialog1.findViewById(R.id.btn_Them_KH);
        btnhuy = dialog1.findViewById(R.id.btn_HuyT_KH);
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TenDN=userkh.getText().toString();
                db_KH.collection("KhachHang")
                        .whereEqualTo("TenDN", TenDN)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    // Nếu không có bản ghi nào có tên đăng nhập giống như tên đăng nhập mới
                                    if (task.getResult().isEmpty()) {
                                        if(validate()==1) {
                                            String TenKH = tenkh.getText().toString();
                                            String SDTKH = sdtkh.getText().toString();
                                            String TenDN = userkh.getText().toString();
                                            String MatKhau = passkh.getText().toString();
                                            String MaKH = UUID.randomUUID().toString();

                                            KhachHang kh = new KhachHang(MaKH, SDTKH, TenKH, TenDN, MatKhau);
                                            HashMap<String, Object> mapKH = kh.convertHashMap();
                                            db_KH.collection("KhachHang")
                                                    .document(MaKH)
                                                    .set(mapKH)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                                            dialog1.dismiss();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();

                                                        }
                                                    });
                                        }
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
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

    }

    public int validate(){
        int validate;
        if(tenkh.getText().length()==0||sdtkh.getText().length()==0||userkh.getText().length()==0||passkh.getText().length()==0) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin  ", Toast.LENGTH_SHORT).show();
            validate = 0;
        } else if (sdtkh.getText().length()>10||sdtkh.getText().length()<10) {
            Toast.makeText(getContext(), "Số điện thoại chỉ 10 kí tự  ", Toast.LENGTH_SHORT).show();
            validate = 2;
        } else  {
            validate =1;

        }
        return validate;
    }


}