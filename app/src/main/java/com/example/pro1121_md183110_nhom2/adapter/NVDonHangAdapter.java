package com.example.pro1121_md183110_nhom2.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class NVDonHangAdapter extends RecyclerView.Adapter<NVDonHangAdapter.ViewHolder> {
    Context context;
    List<DonHang> listdh;
    FirebaseFirestore database;
    Dialog dialog;
    EditText tenlsp, nsx;
    Button btnsua, btnhuy;
    TextView giasp,thanhtien,soluong,size;
    public NVDonHangAdapter(List<DonHang> listdh, Context context , FirebaseFirestore database){
        this.listdh=listdh;
        this.context = context;
        this.database = database;
    }
    @NonNull
    @Override
    public NVDonHangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater .inflate(R.layout.item_recycler__nvdh, parent, false);
        return new NVDonHangAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NVDonHangAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.TenSP.setText("Tên sản phẩm : "+listdh.get(position).getTenSP());
        holder.TenKH.setText("Tên ĐN khách hàng : "+listdh.get(position).getDNKH());
        holder.TenQL.setText("Tên ĐN Quản Lí : "+listdh.get(position).getDNQL());
        holder.TrangThaiGH.setText("Trạng thái giao hàng : "+listdh.get(position).getTrangThaiGH());
        holder.TrangThaiDH.setText("Trạng thái đơn hàng : "+listdh.get(position).getTrangThaiDH());

        holder.Ngay.setText("Ngày : "+listdh.get(position).getNgay());
        if(listdh.get(position).getTrangThaiDH()==0){
            holder.TrangThaiDH.setText("Chưa được xác nhận");
        }else {
            holder.TrangThaiDH.setText("Đã xác nhận");
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
            TenSP=itemView.findViewById(R.id.TenSP_NVDH);
            TenKH=itemView.findViewById(R.id.TenKH_NVDH);
            TenQL=itemView.findViewById(R.id.TenQL_NVDH);
            TrangThaiDH=itemView.findViewById(R.id.TrangThai_NVDH);
            TrangThaiGH=itemView.findViewById(R.id.GiaoHang_NVDH);
            Ngay=itemView.findViewById(R.id.Ngay_NVDH);
            btn_CTDH=itemView.findViewById(R.id.btn_NVCTDH);

        }
    }
}
