package com.example.pro1121_md183110_nhom2.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.example.pro1121_md183110_nhom2.model.LoaiSanPham;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class QLDonHangAdapter extends RecyclerView.Adapter<QLDonHangAdapter.ViewHolder> {
    Context context;
    List<DonHang> listdh;
    FirebaseFirestore database;
    Dialog dialog;
    EditText tenlsp, nsx;
    Button btnsua, btnhuy;
    TextView giasp,thanhtien,soluong,size;

    public QLDonHangAdapter(List<DonHang> listdh, Context context , FirebaseFirestore database){
        this.listdh=listdh;
        this.context = context;
        this.database = database;
    }
    @NonNull
    @Override
    public QLDonHangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater .inflate(R.layout.item_recycler_dh, parent, false);
        return new QLDonHangAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QLDonHangAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.TenSP.setText("Tên sản phẩm : "+listdh.get(position).getTenSP());
        holder.TenKH.setText("Tên ĐN khách hàng : "+listdh.get(position).getDNKH());
        holder.TenQL.setText("Tên ĐN Quản Lí : "+listdh.get(position).getDNQL());
        holder.TrangThaiGH.setText("Trạng thái giao hàng : "+listdh.get(position).getTrangThaiGH());
        holder.TrangThaiDH.setText("Trạng thái đơn hàng : "+listdh.get(position).getTrangThaiDH());
        holder.Ngay.setText("Ngày : "+listdh.get(position).getNgay());

        if(listdh.get(position).getTrangThaiDH()==0){
            holder.TrangThaiDH.setText("Chưa được xác nhận");
            holder.btn_XNDH.setEnabled(true);
            holder.btn_XNDH.setText("Xác nhận");
            holder.btn_XNDH.setBackgroundColor(Color.BLUE);
        }else {
            holder.TrangThaiDH.setText("Đã xác nhận");
            holder.btn_XNDH.setEnabled(false);
            holder.btn_XNDH.setText("Đã Xác nhận");
            holder.btn_XNDH.setBackgroundColor(Color.GRAY);
        }
        if (listdh.get(position).getTrangThaiGH()==0){
            holder.TrangThaiGH.setText("Chưa giao hàng");
            holder.TrangThaiGH.setEnabled(true);
            holder.TrangThaiGH.setTextColor(Color.BLUE);
        }else if(listdh.get(position).getTrangThaiGH()==1){
            holder.TrangThaiGH.setText("Đang giao hàng");
            holder.TrangThaiGH.setEnabled(true);
            holder.TrangThaiGH.setTextColor(Color.GREEN);
        }else if(listdh.get(position).getTrangThaiGH()==2){
            holder.TrangThaiGH.setText("Đã giao hàng");
            holder.TrangThaiGH.setEnabled(false);
            holder.TrangThaiGH.setTextColor(Color.GRAY);
        }

        holder.TrangThaiGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i=listdh.get(position).getTrangThaiGH()+1;
                listdh.get(position).setTrangThaiGH(i);
                database.collection("DonHang")
                        .document(listdh.get(position).getMaDH())
                        .set(listdh.get(position))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Chỉnh sửa trạng thái thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        holder.btn_XNDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listdh.get(position).setTrangThaiDH(1);

                SharedPreferences pref=context.getSharedPreferences("TTNV",Context.MODE_PRIVATE);
                String usernv = pref.getString("USERNAME_NV","");
                listdh.get(position).setDNQL(usernv);

                SharedPreferences prefad=context.getSharedPreferences("TTAD",Context.MODE_PRIVATE);
                String userad = prefad.getString("USERNAME_AD","");
                listdh.get(position).setDNQL(userad);

                database.collection("DonHang")
                        .document(listdh.get(position).getMaDH())
                        .set(listdh.get(position))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Xác đơn nhận đơn hàng thành công", Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });
        holder.btn_CTDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_quanli_dh, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();

                giasp=dialog.findViewById(R.id.QL_GiaDH);
                thanhtien=dialog.findViewById(R.id.QL_ThanhTienDH);
                size=dialog.findViewById(R.id.QL_Size_DH);
                soluong=dialog.findViewById(R.id.QL_SoLuongDH);

                giasp.setText("Giá SP : "+listdh.get(position).getGia()+"");
                thanhtien.setText("Thành Tiền : "+listdh.get(position).getThanhTien()+"");
                size.setText("Size SP :"+listdh.get(position).getSize());
                soluong.setText("Số Lượng : "+listdh.get(position).getSoLuong()+"");

            }
        });

    }

    @Override
    public int getItemCount() {
        return listdh.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView TenSP,TenKH,TrangThaiGH,TenQL,Ngay,TrangThaiDH;
        Button btn_CTDH,btn_XNDH;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TenSP=itemView.findViewById(R.id.TenSP_DH);
            TenKH=itemView.findViewById(R.id.TenKH_DH);
            TenQL=itemView.findViewById(R.id.TenQL_DH);
            TrangThaiDH=itemView.findViewById(R.id.TrangThai_DH);
            TrangThaiGH=itemView.findViewById(R.id.GiaoHang_DH);
            Ngay=itemView.findViewById(R.id.Ngay_DH);
            btn_CTDH=itemView.findViewById(R.id.btn_CTDH);
            btn_XNDH=itemView.findViewById(R.id.btn_XNDH);
        }
    }
}
