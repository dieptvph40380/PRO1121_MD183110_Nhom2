package com.example.pro1121_md183110_nhom2.adapter;

import android.annotation.SuppressLint;
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
import com.example.pro1121_md183110_nhom2.model.NhanVien;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.ViewHolder> {

    List<NhanVien> list;
    Context context;

    FirebaseFirestore database;
    Dialog dialog;
    EditText tennv, sdt, user, pass;
    Button btnsua,btnhuy;




    public NhanVienAdapter(List<NhanVien> list, Context context , FirebaseFirestore database ){
        this.list = list;
        this.context = context;
        this.database = database;
//        this.itemKhoClick = itemKhoClick;
    }

    @NonNull
    @Override
    public NhanVienAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater .inflate(R.layout.item_recycler_nv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhanVienAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String [] manv = {list.get(holder.getAdapterPosition()).getMaNV()};
        holder.tennv.setText("Tên NV : "+list.get(position).getTenNV());
        holder.sdt.setText("SDT : "+list.get(position).getSDT());
        holder.user.setText("User : "+list.get(position).getUser());
        holder.pass.setText("Pass : "+list.get(position).getPass());

        holder.imgsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_sua_nv);



//                itemKhoClick.onItemClick(list.get(holder.getAdapterPosition()));

                tennv = dialog.findViewById(R.id.edt_TenNV);
                sdt= dialog.findViewById(R.id.edt_SDT_NV);
                user = dialog.findViewById(R.id.edt_User_NV);
                pass = dialog.findViewById(R.id.edt_Pass_NV);
                btnsua=dialog.findViewById(R.id.btn_Sua_NV);
                btnhuy=dialog.findViewById(R.id.btn_Huy_NV);

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

                        database.collection("NhanVien")
                                .document(MaNV)
                                .update(mapnv)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                        dialog.dismiss();
                    }
                });

                dialog.show();
                btnhuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        holder.imgxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder alertDelete = new android.app.AlertDialog.Builder(context);
                alertDelete.setTitle("canh bao");
                alertDelete.setMessage(" ban co chac muon xoa cong viec nay khong");
                alertDelete.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String MaNV = list.get(position).getMaNV();
                        database.collection("NhanVien")
                                .document(MaNV)
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
                a1.show();;
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
