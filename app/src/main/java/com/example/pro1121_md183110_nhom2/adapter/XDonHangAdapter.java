package com.example.pro1121_md183110_nhom2.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pro1121_md183110_nhom2.R;
import com.example.pro1121_md183110_nhom2.model.DonHang;
import com.example.pro1121_md183110_nhom2.model.GioHang;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class XDonHangAdapter extends RecyclerView.Adapter<XDonHangAdapter.ViewHolder>{
    Context context;
    List<DonHang> listdh;
    FirebaseFirestore database;
    Dialog dialog;
    EditText tenlsp, nsx;
    Button btnsua, btnhuy;
    TextView GiaSP,SoLuong,ThanhTien,Size;


    public XDonHangAdapter(List<DonHang> listdh, Context context , FirebaseFirestore database){
        this.listdh=listdh;
        this.context = context;
        this.database = database;
    }
    @NonNull
    @Override
    public XDonHangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater .inflate(R.layout.item_recycler_xdh, parent, false);
        return new XDonHangAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull XDonHangAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.TenSP.setText("Tên sản phẩm : "+listdh.get(position).getTenSP());
        holder.TenKH.setText("Tên ĐN khách hàng : "+listdh.get(position).getDNKH());
        holder.TenQL.setText("Tên ĐN Quản Lí : "+listdh.get(position).getDNQL());
        holder.TrangThaiGH.setText("Trạng thái giao hàng : "+listdh.get(position).getTrangThaiGH());
        holder.Ngay.setText("Ngày : "+listdh.get(position).getNgay());

        if (listdh.get(position).getTrangThaiGH()==0){
            holder.TrangThaiGH.setText("Chưa giao hàng");
            holder.TrangThaiGH.setEnabled(false);
            holder.TrangThaiGH.setTextColor(Color.BLUE);
        }else if(listdh.get(position).getTrangThaiGH()==1){
            holder.TrangThaiGH.setText("Đang giao hàng");
            holder.TrangThaiGH.setEnabled(false);
            holder.TrangThaiGH.setTextColor(Color.GREEN);
        }else if(listdh.get(position).getTrangThaiGH()==2){
            holder.TrangThaiGH.setText("Đã giao hàng");
            holder.TrangThaiGH.setEnabled(false);
            holder.TrangThaiGH.setTextColor(Color.GRAY);
        }
        if(listdh.get(position).getTrangThaiGH()==2){
            holder.btn_HuyDH.setEnabled(false);
            holder.btn_HuyDH.setBackgroundColor(Color.GRAY);
        }


        holder.btn_HuyDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listdh.get(position).getTrangThaiGH()==0) {
                    database.collection("DonHang")
                            .document(listdh.get(position).getMaDH())
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(context, "Hủy đơn hàng thành công", Toast.LENGTH_SHORT).show();

                                }
                            });

                    Cleardulieu();

                }else {
                    Toast.makeText(context, "Đang giao hàng không thể hủy đơn", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.btn_Xem_CTDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_xem_dh, null);
                builder.setView(view);
                Dialog dialog1 = builder.create();
                dialog1.show();

                GiaSP=dialog1.findViewById(R.id.Xem_GiaDH);
                SoLuong=dialog1.findViewById(R.id.Xem_SoLuongDH);
                ThanhTien=dialog1.findViewById(R.id.Xem_ThanhTienDH);
                Size=dialog1.findViewById(R.id.Xem_Size_DH);


            }
        });
    }

    @Override
    public int getItemCount() {
        return listdh.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView TenSP,TenKH,TrangThaiGH,TenQL,Ngay,TrangThaiDH;
        Button btn_HuyDH,btn_Xem_CTDH;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TenSP=itemView.findViewById(R.id.TenSP_XDH);
            TenKH=itemView.findViewById(R.id.TenKH_XDH);
            TenQL=itemView.findViewById(R.id.TenQL_XDH);
            TrangThaiGH=itemView.findViewById(R.id.GiaoHang_XDH);
            Ngay=itemView.findViewById(R.id.Ngay_XDH);
            btn_HuyDH=itemView.findViewById(R.id.btn_HuyDH);
            btn_Xem_CTDH=itemView.findViewById(R.id.btn_XCTDH);
        }
    }

    public  void Cleardulieu(){
        listdh.clear();
        SharedPreferences pref=context.getSharedPreferences("TTKH",Context.MODE_PRIVATE);
        String usekh = pref.getString("USERNAME_KH","");
        database.collection("DonHang")

                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot x:task.getResult()){
                                listdh.add(x.toObject(DonHang.class));
                                notifyDataSetChanged();
                            }
                        }
                    }
                });

    }
}
