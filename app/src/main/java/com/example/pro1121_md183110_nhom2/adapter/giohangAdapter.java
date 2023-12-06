package com.example.pro1121_md183110_nhom2.adapter;// GioHangAdapter.java

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pro1121_md183110_nhom2.R;
import com.example.pro1121_md183110_nhom2.model.GioHang;
import com.example.pro1121_md183110_nhom2.model.KhachHang;
import com.example.pro1121_md183110_nhom2.model.LoaiSanPham;
import com.example.pro1121_md183110_nhom2.model.SanPham;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

public class giohangAdapter extends RecyclerView.Adapter<giohangAdapter.ViewHolder> {
     List<GioHang> list;
    Context context;


    FirebaseFirestore database;
    Dialog dialog;
    EditText soluong,size,thanhtien;
    Button btnxn,btnhuy,btnsuagh;

    public giohangAdapter(List<GioHang> list, Context context, FirebaseFirestore database){
        this.list = list;
        this.context = context;
        this.database = database;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_xem_gh, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tensp.setText("Tên SP : "+list.get(position).getMaSP());
        holder.soluong.setText("SDT : "+list.get(position).getSoLuong());
        holder.thanhtien.setText("User : "+list.get(position).getThanhTien());
        holder.size.setText("Pass : "+list.get(position).getSize());
        holder.btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_sua_loaisp);

                soluong = dialog.findViewById(R.id.edt_SL_SGH);
                size= dialog.findViewById(R.id.edt_Size_SGH);
                btnxn = dialog.findViewById(R.id.btn_XacNhan_GH);
                btnhuy = dialog.findViewById(R.id.btn_Huy_SGH);

                soluong.setText(list.get(position).getSoLuong()+"");
                size.setText(list.get(position).getSize()+"");



                btnsuagh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String MaKH = list.get(position).getMaKH();
                        String MaSP = list.get(position).getMaSP();
                        String MaGH = list.get(position).getMaKH();
                        int SL = Integer.parseInt(soluong.getText().toString());
                        String Size = size.getText().toString();
                        int ThanhTien = Integer.parseInt(thanhtien.getText().toString());
                        GioHang gioHang = new GioHang(MaGH,ThanhTien,SL,Size,MaKH,MaSP);

                        HashMap<String, Object> mapgh = gioHang.convertHashMap();

                        database.collection("GioHang")
                                .document(MaGH)
                                .update(mapgh)
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
            }
        });
        holder.btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder alertDelete = new android.app.AlertDialog.Builder(context);
                alertDelete.setTitle("canh bao");
                alertDelete.setMessage(" ban co chac muon xoa cong viec nay khong");
                alertDelete.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String MaKH = list.get(position).getMaKH();
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
        TextView tensp, soluong, thanhtien, size;
        Button btnsua,btndathang,btnxoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tensp = itemView.findViewById(R.id.tvHoTen_KH);
            soluong = itemView.findViewById(R.id.tvsdt_KH);
            thanhtien = itemView.findViewById(R.id.tvTenDN_KH);
            size = itemView.findViewById(R.id.tvTenDN_KH);
            btnsua = itemView.findViewById(R.id.btn_Sua_GH);
            btndathang = itemView.findViewById(R.id.btn_DatHang_GH);
            btnxoa = itemView.findViewById(R.id.btn_Xoa_GH);
        }
    }
}
