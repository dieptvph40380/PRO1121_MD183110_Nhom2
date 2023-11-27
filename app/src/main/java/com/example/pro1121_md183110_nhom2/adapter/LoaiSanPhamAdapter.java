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
import com.example.pro1121_md183110_nhom2.model.LoaiSanPham;
import com.example.pro1121_md183110_nhom2.model.NhanVien;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

public class LoaiSanPhamAdapter extends RecyclerView.Adapter<LoaiSanPhamAdapter.ViewHolder> {

    List<LoaiSanPham> list;
    Context context;

    FirebaseFirestore database;
    Dialog dialog;
    EditText tenlsp, nsx;
    Button btnsua, btnhuy;

    public LoaiSanPhamAdapter(List<LoaiSanPham> list, Context context , FirebaseFirestore database){
        this.list = list;
        this.context = context;
        this.database = database;
    }
    @NonNull
    @Override
    public LoaiSanPhamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater .inflate(R.layout.item_recycler_loaisp, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSanPhamAdapter.ViewHolder holder,  int position) {
        String [] malsp = {list.get(holder.getAdapterPosition()).getMaLSP()};
        holder.tenlsp.setText(list.get(position).getTenLSP());
        holder.nsx.setText(list.get(position).getNSXLSP());


        holder.imgsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_sua_loaisp);

                tenlsp = dialog.findViewById(R.id.edt_TenLSP);
                nsx= dialog.findViewById(R.id.edt_NSX);
                btnhuy = dialog.findViewById(R.id.btn_Huy_LSP);
                btnsua = dialog.findViewById(R.id.btn_Sua_LSP);

                tenlsp.setText(list.get(position).getTenLSP()+"");
                nsx.setText(list.get(position).getNSXLSP()+"");


                btnsua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String MaLSP = list.get(position).getMaLSP();
                        String TenLSP = tenlsp.getText().toString();
                        String NSX = nsx.getText().toString();

                        LoaiSanPham loaiSanPham = new LoaiSanPham(MaLSP,TenLSP,NSX);
                        HashMap<String, Object> maplsp = loaiSanPham.convertHashMap();

                        database.collection("LoaiSanPham")
                                .document(MaLSP)
                                .update(maplsp)
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
        holder.imgxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder alertDelete = new android.app.AlertDialog.Builder(context);
                alertDelete.setTitle("canh bao");
                alertDelete.setMessage(" ban co chac muon xoa cong viec nay khong");
                alertDelete.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String MaLSP = list.get(position).getMaLSP();
                        database.collection("LoaiSanPham")
                                .document(MaLSP)
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
        TextView tenlsp,nsx;
        ImageView imgxoa,imgsua;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tenlsp = itemView.findViewById(R.id.tv_Ten_LSP);
             nsx = itemView.findViewById(R.id.tv_NSX_LSP);;
            imgxoa = itemView.findViewById(R.id.img_Remove_LSP) ;
            imgsua = itemView.findViewById(R.id.img_Edit_LSP);
        }
    }
}
