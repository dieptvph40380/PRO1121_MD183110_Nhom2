package com.example.pro1121_md183110_nhom2.adapter;// GioHangAdapter.java

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
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
import com.example.pro1121_md183110_nhom2.model.DonHang;
import com.example.pro1121_md183110_nhom2.model.GioHang;
import com.example.pro1121_md183110_nhom2.model.KhachHang;
import com.example.pro1121_md183110_nhom2.model.LoaiSanPham;
import com.example.pro1121_md183110_nhom2.model.SanPham;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class giohangAdapter extends RecyclerView.Adapter<giohangAdapter.ViewHolder> {
    List<GioHang> list;
    Context context;


    FirebaseFirestore database;
    Dialog dialog;
    EditText soluong, size, thanhtien;
    Button btnxn, btnhuy, btnsuagh;

    int sosize;

    public giohangAdapter(List<GioHang> list, Context context, FirebaseFirestore database) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(list.get(position).getSize().equals("Nhỏ")){
            sosize=0;
        } else if (list.get(position).getSize().equals("Vừa")) {
            sosize=30;
        } else if (list.get(position).getSize().equals("Lớn")) {
            sosize=50;
        }
        holder.tensp.setText("Tên SP : " + list.get(position).getTenSP());
        holder.giasp.setText("Giá SP : " + TinhSize(list.get(position).getGiaSP(),sosize));
        holder.soluong.setText("Số lượng : " + list.get(position).getSoLuong());
        holder.size.setText("Size : " + list.get(position).getSize());
        holder.thanhtien.setText("Thành Tiền : " +TinhTien(list.get(position).getGiaSP(),list.get(position).getSoLuong(),sosize));




        holder.btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_sua_giohang, null);
                builder.setView(view);
                Dialog dialog1 = builder.create();
                dialog1.show();
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_sua_giohang);

                soluong = dialog1.findViewById(R.id.edt_SL_SGH);
                size = dialog1.findViewById(R.id.edt_Size_SGH);
                btnhuy = dialog1.findViewById(R.id.btn_Huy_SGH);
                btnsuagh = dialog1.findViewById(R.id.btn_XacNhan_GH);

                soluong.setText(list.get(position).getSoLuong()+"");
                size.setText(list.get(position).getSize() + "");

                btnhuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                    }
                });
                size.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(context);
                        builder.setTitle("Chọn Size");
                        String[] Size={"Nhỏ","Vừa","Lớn"};
                        builder.setItems(Size, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                size.setText(Size[which]);

                            }
                        });

                        android.app.AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                });
                btnsuagh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String MaKH = list.get(position).getMaKH();
                        String MaSP = list.get(position).getTenSP();
                        String MaGH = list.get(position).getMaGH();
                        int SL = Integer.parseInt(soluong.getText().toString());
                        String Size = size.getText().toString();
                        int ThanhTien = list.get(position).getThanhTien();
                        int Gia = list.get(position).getGiaSP();



                        GioHang gioHang = new GioHang(MaGH, ThanhTien, SL, Size, MaKH, MaSP, Gia);

                        HashMap<String, Object> mapgh = gioHang.convertHashMap();
                        database = FirebaseFirestore.getInstance();
                        database.collection("GioHang")
                                .document(MaGH)
                                .update(mapgh)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                                        Cleardulieu();
                                        dialog1.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                                        Log.d("/////////", "uploadImage: " + e);
                                    }
                                });


                    }
                });


            }
        });
        holder.btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder alertDelete = new android.app.AlertDialog.Builder(context);
                alertDelete.setTitle("Cảnh báo");
                alertDelete.setMessage(" Bạn có chăc chắn");
                alertDelete.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String MaGH = list.get(position).getMaGH();
                        database.collection("GioHang")
                                .document(MaGH)
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
                        Cleardulieu();
                    }
                });
                alertDelete.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog a1 = alertDelete.create();
                a1.show();
                ;
            }

        });

        holder.btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonHang donHang=new DonHang();
                String MaDH= UUID.randomUUID().toString();
                donHang.setMaDH(MaDH);
                donHang.setGia(TinhSize(list.get(position).getGiaSP(),sosize));
                donHang.setTenSP(list.get(position).getTenSP());
                donHang.setSoLuong(list.get(position).getSoLuong());
                donHang.setThanhTien(TinhTien(list.get(position).getGiaSP(),list.get(position).getSoLuong(),sosize));
                donHang.setDNKH(list.get(position).getMaKH());
                SharedPreferences pref=context.getSharedPreferences("TTKH",Context.MODE_PRIVATE);
                String DNQL = pref.getString("USERNAME_KH","");
                donHang.setNgay(Ngay());
                donHang.setTrangThaiDH(0);
                donHang.setTrangThaiGH(0);
                donHang.setSize(list.get(position).getSize());
                database.collection("DonHang")
                        .document(MaDH)
                        .set(donHang)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(context, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tensp, soluong, thanhtien, size, giasp;
        Button btnsua, btndathang, btnxoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            giasp = itemView.findViewById(R.id.Gia_SP_GH);
            tensp = itemView.findViewById(R.id.Ten_SP_GH);
            soluong = itemView.findViewById(R.id.tv_SL_GH);
            thanhtien = itemView.findViewById(R.id.tv_TT_GH);
            size = itemView.findViewById(R.id.tv_Size_GH);
            btnsua = itemView.findViewById(R.id.btn_Sua_GH);
            btndathang = itemView.findViewById(R.id.btn_DatHang_GH);
            btnxoa = itemView.findViewById(R.id.btn_Xoa_GH);
        }
    }

    public int TinhTien(int a, int b,int c) {

        return (a+c) *b;
    }
    public int TinhSize(int a, int b){
        return a+b;
    }
    public int SizeVua(int gia){
        return gia + 150;
    }
    public int SizeLon(int gia){
        return gia + 250;
    }

    public  void Cleardulieu(){
        list.clear();
        SharedPreferences pref=context.getSharedPreferences("TTKH",Context.MODE_PRIVATE);
        String usekh = pref.getString("USERNAME_KH","");
        database.collection("GioHang")
                .whereEqualTo("MaKH",usekh)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot x:task.getResult()){
                                list.add(x.toObject(GioHang.class));
                                notifyDataSetChanged();
                            }
                        }
                    }
                });

    }

    public String Ngay(){
        int ngay, thang, nam;
        ngay= Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        thang=Calendar.getInstance().get(Calendar.MONTH)+1;
        nam=Calendar.getInstance().get(Calendar.YEAR);
        return String.format("%d/%d/%d",ngay,thang,nam);
    }
}
