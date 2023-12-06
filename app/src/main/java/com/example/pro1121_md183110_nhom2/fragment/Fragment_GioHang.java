package com.example.pro1121_md183110_nhom2.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pro1121_md183110_nhom2.R;
import com.example.pro1121_md183110_nhom2.adapter.DSSPAdapter;
import com.example.pro1121_md183110_nhom2.model.GioHang;
import com.example.pro1121_md183110_nhom2.model.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Fragment_GioHang extends Fragment {

    FirebaseFirestore db;
    RecyclerView rcv;
    FloatingActionButton fab;
    EditText soluong, size,thanhtien;
    Dialog dialog;
    EditText tenlsp, nsx;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<SanPham> spList = new ArrayList<>();
    ArrayList<GioHang> ghList=new ArrayList<>();
    DSSPAdapter adapter;

    Context context;
    Button btnthem,btnthemct;


    public Fragment_GioHang() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //   return inflater.inflate(R.layout.fragment__xem_s_p, container, false);
        View view=inflater.inflate(R.layout.fragment__gh, container, false);


        rcv=view.findViewById(R.id.recycler_XemGH);
        db=FirebaseFirestore.getInstance();

        adapter = new DSSPAdapter(spList,getContext(),db);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(adapter);
        ListenFirebaseFirestoregh();

        rcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                openDialog(context ,0);

            }
        });
        return view;
    }
    //    private void ListenFirebaseFirestoregh(){
//        db.collection("GioHang")
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        if(error != null){
//                            Log.e("TAG", "fail", error);
//                            return;
//                        }
//                        if(value != null){
//                            for (DocumentChange dc: value.getDocumentChanges()){
//                                switch (dc.getType()){
//                                    case ADDED:{
//                                        GioHang newU = dc.getDocument().toObject(GioHang.class);
//                                        ghList.add(newU);
//                                        adapter.notifyItemInserted(ghList.size() - 1);
//                                        break;
//                                    }
//                                    case MODIFIED:{
//                                        GioHang update = dc.getDocument().toObject(GioHang.class);
//                                        if(dc.getOldIndex() == dc.getNewIndex()){
//                                            ghList.set(dc.getOldIndex(), update);
//                                            adapter.notifyItemChanged(dc.getOldIndex());
//
//                                        } else {
//                                            ghList.remove(dc.getOldIndex());
//                                            ghList.add(update);
//                                            adapter.notifyItemMoved(dc.getOldIndex(), dc.getNewIndex());
//
//                                        }
//                                        break;
//                                    }
//                                    case REMOVED:{
//                                        dc.getDocument().toObject(GioHang.class);
//                                        ghList.remove(dc.getOldIndex());
//                                        adapter.notifyItemRemoved(dc.getOldIndex());
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                });
//    }
    private void ListenFirebaseFirestoregh(){
        //        db.collection("GioHang")
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        if(error != null){
//                            Log.e("TAG", "fail", error);
//                            return;
//                        }
//        db.collection("GioHnag")
    }
    }
