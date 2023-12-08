package com.example.pro1121_md183110_nhom2.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.pro1121_md183110_nhom2.R;
import com.example.pro1121_md183110_nhom2.adapter.QLDonHangAdapter;
import com.example.pro1121_md183110_nhom2.adapter.XDonHangAdapter;
import com.example.pro1121_md183110_nhom2.model.DonHang;
import com.example.pro1121_md183110_nhom2.model.GioHang;
import com.example.pro1121_md183110_nhom2.model.NhanVien;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class Fragment_XemDH extends Fragment {

    FirebaseFirestore db;
    RecyclerView rcv;
    FloatingActionButton fab;
    Dialog dialog;

    ArrayList<DonHang> dhList = new ArrayList<>();
    XDonHangAdapter adapter;
    Context context;
    Button btnthem,btnhuy;
    public Fragment_XemDH() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment__xem_d_h, container, false);
        rcv=view.findViewById(R.id.recycler_XemDH);

        onResume();




        ListenFirebaseFirestore();

        return view;
    }
    private void ListenFirebaseFirestore(){
        SharedPreferences pref=getContext().getSharedPreferences("TTKH",Context.MODE_PRIVATE);
        String usekh = pref.getString("USERNAME_KH","");

        db.collection("DonHang")
                .whereEqualTo("dnkh",usekh)
                .whereEqualTo("trangThaiDH",1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot x:task.getResult()){
                                dhList.add(x.toObject(DonHang.class));
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    public void reloadData(){
        db=FirebaseFirestore.getInstance();
        adapter = new XDonHangAdapter(dhList,getContext(),db);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(adapter);

    }
    @Override
    public void onResume() {
        reloadData();
        super.onResume();

    }
}