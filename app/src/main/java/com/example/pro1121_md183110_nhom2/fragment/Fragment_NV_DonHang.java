package com.example.pro1121_md183110_nhom2.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

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
import com.example.pro1121_md183110_nhom2.adapter.NVDonHangAdapter;
import com.example.pro1121_md183110_nhom2.adapter.QLDonHangAdapter;
import com.example.pro1121_md183110_nhom2.model.DonHang;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class Fragment_NV_DonHang extends Fragment {
    FirebaseFirestore db;
    RecyclerView rcv;
    FloatingActionButton fab;
    Dialog dialog;

    ArrayList<DonHang> dhList = new ArrayList<>();
    NVDonHangAdapter adapter;
    Context context;
    Button btnthem,btnhuy;

    public Fragment_NV_DonHang() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment__n_v__don_hang, container, false);
        rcv=view.findViewById(R.id.recycler_NVQLDH);
        db=FirebaseFirestore.getInstance();

        adapter = new NVDonHangAdapter(dhList,getContext(),db);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(adapter);
        ListenFirebaseFirestore();

        return view;
    }
    private void ListenFirebaseFirestore(){
        db.collection("DonHang")
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
                                        DonHang newU = dc.getDocument().toObject(DonHang.class);
                                        dhList.add(newU);
                                        adapter.notifyItemInserted(dhList.size() - 1);
                                        break;
                                    }
                                    case MODIFIED:{
                                        DonHang update = dc.getDocument().toObject(DonHang.class);
                                        if(dc.getOldIndex() == dc.getNewIndex()){
                                            dhList.set(dc.getOldIndex(), update);
                                            adapter.notifyItemChanged(dc.getOldIndex());

                                        } else {
                                            dhList.remove(dc.getOldIndex());
                                            dhList.add(update);
                                            adapter.notifyItemMoved(dc.getOldIndex(), dc.getNewIndex());

                                        }
                                        break;
                                    }
                                    case REMOVED:{
                                        dc.getDocument().toObject(DonHang.class);
                                        dhList.remove(dc.getOldIndex());
                                        adapter.notifyItemRemoved(dc.getOldIndex());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                });
    }
}