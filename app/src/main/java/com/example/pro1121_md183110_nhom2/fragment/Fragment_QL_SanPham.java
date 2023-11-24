package com.example.pro1121_md183110_nhom2.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.pro1121_md183110_nhom2.R;
import com.example.pro1121_md183110_nhom2.adapter.NhanVienAdapter;
import com.example.pro1121_md183110_nhom2.model.NhanVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class Fragment_QL_SanPham extends Fragment {

    FirebaseFirestore db;
    RecyclerView rcv;
    FloatingActionButton fab;
    Dialog dialog;
    EditText tennv, sdt, user, pass;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<NhanVien> nvList = new ArrayList<>();
    NhanVienAdapter adapter;
    Context context;

    Button btnthem,btnhuy;

    FirebaseFirestore database;

    public Fragment_QL_SanPham() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment__q_l__san_pham, container, false);

        fab=view.findViewById(R.id.floatAdd_SP);


        return view;
    }
}