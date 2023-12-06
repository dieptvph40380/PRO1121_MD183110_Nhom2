package com.example.pro1121_md183110_nhom2.adapter;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.pro1121_md183110_nhom2.model.GioHang;
import com.example.pro1121_md183110_nhom2.model.LoaiSanPham;
import com.example.pro1121_md183110_nhom2.model.SanPham;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.UUID;

public class DSSPAdapter extends RecyclerView.Adapter<DSSPAdapter.ViewHolder>{
    List<SanPham> list;
    FirebaseFirestore db;
    RecyclerView rcv;
    FloatingActionButton fab;
    Context context;

    FirebaseFirestore database;
    Dialog dialog;
    EditText soluong,size,thanhtien;
TextView nsx,khoiluong,luongcalo,thanhphan;
Button btnxemct,btnthemgh;

    public DSSPAdapter(List<SanPham> list, Context context , FirebaseFirestore database){
        this.list = list;
        this.context = context;
        this.database = database;
    }

    @NonNull
    @Override
    public DSSPAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater .inflate(R.layout.item_recycler_dssp, parent, false);
        return new DSSPAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       holder.tensp.setText("Tên SP: "+list.get(position).getTenSP());
       holder.gia.setText("Gia : "+list.get(position).getGia());
       holder.tenloai.setText(("Tên Loại : "+list.get(position).getTenLoai()));
       holder.btnthemGH.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               AlertDialog.Builder builder = new AlertDialog.Builder(context);//tạo đối tượng của alerdialog
               //gán layout, tạo view
               LayoutInflater inflater = ((Activity) context).getLayoutInflater();
               View view = inflater.inflate(R.layout.dialog_them_giohang, null);
               builder.setView(view);//gán view cho hộp thoại
               Dialog dialog = builder.create();//tạo hộp thoại
               dialog.show();//hiển thị hộp thoại lên màn hình
               //ánh xạ các thành phần widget

               soluong = dialog.findViewById(R.id.edt_SL_TGH);
               size = dialog.findViewById(R.id.edt_Size_TGH);
               thanhtien = dialog.findViewById(R.id.edt_ThanhTien_TGH);
               btnthemgh = dialog.findViewById(R.id.btn_Them_GH);

               soluong.setText(list.get(position).getSoLuong() + "");


               btnthemgh.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       GioHang gioHang = new GioHang();
                       String magh = UUID.randomUUID().toString();
                       gioHang.setMaGH(magh);
                       gioHang.setMaSP(list.get(position).getMaSP());
                       SharedPreferences pref=context.getSharedPreferences("TTKH",context.MODE_PRIVATE);
                       String usekh = pref.getString("USERNAME_KH","");
                       gioHang.setMaKH(usekh);
                       Toast.makeText(context, "loikh"+usekh, Toast.LENGTH_SHORT).show();
                       gioHang.setSoLuong(Integer.parseInt(soluong.getText().toString()));
                       gioHang.setSize(size.getText().toString());
                       gioHang.setThanhTien(Integer.parseInt(thanhtien.getText().toString()));


                       db = FirebaseFirestore.getInstance();
                       db.collection("GioHang").document(magh).set(gioHang).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               Toast.makeText(context, "add thanhcong", Toast.LENGTH_SHORT).show();
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(context, "add thatbai"+e, Toast.LENGTH_SHORT).show();
                           }
                       });
//                       Toast.makeText(context, "loigh", Toast.LENGTH_SHORT).show();
                   }
               });
           }
       });
        holder.btnxemct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);//tạo đối tượng của alerdialog
                //gán layout, tạo view
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_xem_chitiet, null);
                builder.setView(view);//gán view cho hộp thoại
                Dialog dialog = builder.create();//tạo hộp thoại
                dialog.show();//hiển thị hộp thoại lên màn hình
                //ánh xạ các thành phần widget
                nsx = dialog.findViewById(R.id.tv_NSX_DSSP);
                khoiluong= dialog.findViewById(R.id.tv_Khoiluong_DSSP);
                luongcalo = dialog.findViewById(R.id.tv_LuongCalo_DSSP);
                thanhphan = dialog.findViewById(R.id.tv_ThanhPhan_DSSP);

                nsx.setText(list.get(position).getNSX()+"");
                khoiluong.setText(list.get(position).getKhoiLuong()+"");
                luongcalo.setText(list.get(position).getLuongCalo()+"");
                thanhphan.setText(list.get(position).getThanhPhan()+"");

            }
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tensp,gia,tenloai;
        Button btnxemct,btnthemGH;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tensp = itemView.findViewById(R.id.tv_Ten_DSSP);
            gia = itemView.findViewById(R.id.tv_Gia_DSSP);;
            tenloai = itemView.findViewById(R.id.tv_Ten_Loai_DSSP);
          btnxemct = itemView.findViewById(R.id.btn_xemct_DSSP) ;
          btnthemGH=itemView.findViewById(R.id.btn_themgh_DSSP);

        }
    }
}
