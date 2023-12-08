package com.example.pro1121_md183110_nhom2.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pro1121_md183110_nhom2.R;
import com.example.pro1121_md183110_nhom2.adapter.DSSPAdapter;
import com.example.pro1121_md183110_nhom2.adapter.giohangAdapter;
import com.example.pro1121_md183110_nhom2.model.GioHang;
import com.example.pro1121_md183110_nhom2.model.SanPham;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Fragment_GioHang extends Fragment {

    FirebaseFirestore db;
    RecyclerView rcv;
    FloatingActionButton fab;
    EditText soluong, size, thanhtien;
    Dialog dialog;
    EditText tenlsp, nsx;
    ArrayList<String> list = new ArrayList<>();

    ArrayList<GioHang> ghList = new ArrayList<>();
    giohangAdapter adapter;

    Context context;
    Button btndathang;


    public Fragment_GioHang() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //   return inflater.inflate(R.layout.fragment__xem_s_p, container, false);
        View view = inflater.inflate(R.layout.fragment__gh, container, false);



        rcv = view.findViewById(R.id.recyclerViewCart);
        db = FirebaseFirestore.getInstance();

        ListenFirebaseFirestore();

        adapter = new giohangAdapter(ghList, getContext(), db);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(adapter);


//        .setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                openDialog(context ,0);
//
//            }
//        });
        return view;
    }

    private void ListenFirebaseFirestore() {
        SharedPreferences pref=getContext().getSharedPreferences("TTKH",Context.MODE_PRIVATE);
        String usekh = pref.getString("USERNAME_KH","");
        db.collection("GioHang")
               .whereEqualTo("MaKH",usekh)
               .get()
               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       if(task.isSuccessful()){
                           for(QueryDocumentSnapshot x:task.getResult()){
                               ghList.add(x.toObject(GioHang.class));
                               adapter.notifyDataSetChanged();
                           }
                       }
                   }
               });
    }
}




