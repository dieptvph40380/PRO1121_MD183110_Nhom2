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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pro1121_md183110_nhom2.R;
import com.example.pro1121_md183110_nhom2.model.NhanVien;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

import io.grpc.internal.ClientStream;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.ViewHolder> {

    List<NhanVien> list;
    Context context;

    FirebaseFirestore database;
    Dialog dialog;
    EditText tennv, sdt, user, pass;
    Button btnsua,btnhuy;

    public NhanVienAdapter(List<NhanVien> list, Context context , FirebaseFirestore database){
        this.list = list;
        this.context = context;
        this.database = database;
    }

    @NonNull
    @Override
    public NhanVienAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater .inflate(R.layout.item_recycler_nv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhanVienAdapter.ViewHolder holder, int position) {
        holder.tennv.setText(list.get(position).getTenNV());
        holder.sdt.setText(list.get(position).getSDT());
        holder.user.setText(list.get(position).getUser());
        holder.pass.setText(list.get(position).getPass());

        holder.imgsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_sua_nv);

                tennv = dialog.findViewById(R.id.edt_TenNV);
                sdt= dialog.findViewById(R.id.edt_SDT_NV);
                user = dialog.findViewById(R.id.edt_User_NV);
                pass = dialog.findViewById(R.id.edt_Pass_NV);
                btnhuy = dialog.findViewById(R.id.btn_Sua_NV);
                btnsua = dialog.findViewById(R.id.btn_Huy_NV);

                tennv.setText(list.get(position).getTenNV()+"");
                sdt.setText(list.get(position).getSDT()+"");
                user.setText(list.get(position).getUser()+"");
                pass.setText(list.get(position).getPass()+"");

                btnsua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String MaNV = list.get(position).getMaNV();
                        String TenNV = tennv.getText().toString();
                        String SDT = sdt.getText().toString();
                        String User = user.getText().toString();
                        String Pass = pass.getText().toString();
                        NhanVien nhanVien = new NhanVien(MaNV, TenNV,SDT , User, Pass);
                        HashMap<String, Object> mapnv = nhanVien.convertHashMap();

                    }
                });



            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView tennv,sdt,user,pass;
        ImageView imgxoa,imgsua;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tennv = itemView.findViewById(R.id.tv_Ten_NV);
            sdt = itemView.findViewById(R.id.tv_SDT_NV);
            user = itemView.findViewById(R.id.tv_User_NV);
            pass = itemView.findViewById(R.id.tv_Pass_NV);;
            imgxoa = itemView.findViewById(R.id.img_Remove_NV) ;
            imgsua = itemView.findViewById(R.id.img_Edit_NV);
        }
    }


}
