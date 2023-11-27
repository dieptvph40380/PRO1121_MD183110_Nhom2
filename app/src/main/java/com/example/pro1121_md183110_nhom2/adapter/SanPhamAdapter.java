package com.example.pro1121_md183110_nhom2.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pro1121_md183110_nhom2.R;
import com.example.pro1121_md183110_nhom2.model.NhanVien;
import com.example.pro1121_md183110_nhom2.model.SanPham;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {

    List<SanPham> list;
    Context context;


    FirebaseFirestore database;
    Dialog dialog;
    EditText tensp, giasp , thanhphan,khoiluong,luongcalo;
    Spinner spnloai;
    Button btnsua,btnhuy;

    public SanPhamAdapter(List<SanPham> list, Context context, FirebaseFirestore database){
        this.list=list;
        this.context = context;
        this.database = database;
    }

    @NonNull
    @Override
    public SanPhamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater .inflate(R.layout.item_recycler_sp, parent, false);
        return new SanPhamAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamAdapter.ViewHolder holder, int position) {
//        holder.tensp.setText(list.get(position).getTenNV());
//        holder.sdt.setText(list.get(position).getSDT());
//        holder.user.setText(list.get(position).getUser());
//        holder.pass.setText(list.get(position).getPass());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tensp,giasp,spnloai,khoiluong,luongcalo,thanhphan;
        ImageView imgxoa,imgsua;
        public ViewHolder(@NonNull View itemView) {


            super(itemView);
        }
    }
}
