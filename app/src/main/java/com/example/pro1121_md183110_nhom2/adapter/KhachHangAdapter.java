package com.example.pro1121_md183110_nhom2.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pro1121_md183110_nhom2.R;
import com.example.pro1121_md183110_nhom2.model.KhachHang;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.List;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.ViewHolder>{
    List<KhachHang> list;
    Context context;


    FirebaseFirestore database;
    Dialog dialog_KH;
    EditText tenkh, sdtkh, userkh, passkh;


    public KhachHangAdapter(List<KhachHang> list, Context context, FirebaseFirestore database) {
        this.list = list;
        this.context = context;
        this.database = database;
    }

    @NonNull
    @Override
    public KhachHangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_kh, parent, false);
        return new KhachHangAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhachHangAdapter.ViewHolder holder, int i) {
        String[] makh = {list.get(holder.getAdapterPosition()).getMaKH()};
        holder.tenkh.setText(list.get(i).getHoVaTen());
        holder.sdtkh.setText(list.get(i).getSDT());
        holder.userkh.setText(list.get(i).getTenDN());
        holder.passkh.setText(list.get(i).getMatKhau());

        holder.imgxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder alertDelete = new android.app.AlertDialog.Builder(context);
                alertDelete.setTitle("canh bao");
                alertDelete.setMessage(" ban co chac muon xoa cong viec nay khong");
                alertDelete.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String MaKH = list.get(i).getMaKH();
                        database.collection("KhachHang")
                                .document(MaKH)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "delete thanh cong", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "delete that bai", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        notifyItemRemoved(holder.getAdapterPosition());
                    }
                });
                alertDelete.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog a1 = alertDelete.create();
                a1.show();
                ;
            }

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tenkh, sdtkh, userkh, passkh;
        ImageView imgxoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenkh = itemView.findViewById(R.id.tvMa_KH);
            sdtkh = itemView.findViewById(R.id.tvsdt_KH);
            userkh = itemView.findViewById(R.id.tvHoTen_KH);
            passkh = itemView.findViewById(R.id.tvMK_KH);

            imgxoa = itemView.findViewById(R.id.btnRemove);

        }

    }
}

