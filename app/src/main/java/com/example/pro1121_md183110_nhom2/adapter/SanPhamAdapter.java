package com.example.pro1121_md183110_nhom2.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pro1121_md183110_nhom2.R;
import com.example.pro1121_md183110_nhom2.model.LoaiSanPham;
import com.example.pro1121_md183110_nhom2.model.NhanVien;
import com.example.pro1121_md183110_nhom2.model.SanPham;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.K;

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
List<String> loaiSanPhamList;
    public SanPhamAdapter(List<SanPham> list, Context context, FirebaseFirestore database){
        this.list=list;
        this.context = context;
        this.database = database;
        this.loaiSanPhamList= new ArrayList<>();
        fetchLoaiSanPhamData();
    }

    @NonNull
    @Override
    public SanPhamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater .inflate(R.layout.item_recycler_sp, parent, false);
        return new SanPhamAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SanPham sanPham= list.get(position);
        holder.tensp.setText(sanPham.getTenSP());
        holder.giasp.setText(String.valueOf(sanPham.getGia()));




//bật sk khi click vào nút sửa
        holder.imgsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(position);
            }
        });
        //Bắt sk khi click vô xóa
        holder.imgxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //xử lý xóa sản phẩm ở đây
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tensp,giasp,khoiluong,luongcalo,thanhphan;
        Spinner spnloai;
        ImageView imgxoa,imgsua;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tensp=itemView.findViewById(R.id.tv_Ten_SP);
            giasp=itemView.findViewById(R.id.tv_Gia_SP);
            imgxoa=itemView.findViewById(R.id.img_Edit_SP);
            imgsua=itemView.findViewById(R.id.img_Remove_SP);

        }
    }
    private void fetchLoaiSanPhamData(){
        database.collection("LoaiSanPham")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String loaiSP = document.getString("tenLoaiSP");
                                loaiSanPhamList.add(loaiSP);
                            }
                            notifyDataSetChanged();
                            }
                        else{
                            Log.d("SanPhamAdapter", "Error getting documnets:", task.getException());
                        }
                    }
                });
    }
    private void showDialog(int position){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_sua_sp);
        tensp=dialog.findViewById(R.id.edt_Ten_SP);
        giasp=dialog.findViewById(R.id.edt_Gia_SP);
        spnloai=dialog.findViewById(R.id.spn_TenLoai);
        khoiluong=dialog.findViewById(R.id.edt_Klg_SP);
        luongcalo=dialog.findViewById(R.id.edt_LgCalo_SP);
        thanhphan=dialog.findViewById(R.id.edt_TP_SP);
        btnhuy=dialog.findViewById(R.id.btn_HuyT_SP);
        btnsua=dialog.findViewById(R.id.btn_Sua_SP);
   //     List<String> dataList = new ArrayList<>();

// Thêm các mục dữ liệu khác nếu cần
        // Thay thế LoaiSachDAO và phương thức getListLoaiSach() bằng truy vấn dữ liệu từ Firestore
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference loaiSachCollection = database.collection("LoaiSanPham");

        loaiSachCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<String> loaisachs = new ArrayList<>();
                int spnIndex = 0;

                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    LoaiSanPham loaiSanPham = document.toObject(LoaiSanPham.class);
                    loaisachs.add(loaiSanPham.getTenLSP());

                    if (loaiSanPham.getMaLSP().equals(list.get(position).getMaLoai())) {
                        spnIndex = loaisachs.indexOf(loaiSanPham.getMaLSP());
                    }
                }

                spnloai.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, loaisachs));
                spnloai.setSelection(spnIndex);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "lỗi", Toast.LENGTH_SHORT).show();
            }
        });
 //     spnloai.setSelection(loaiSanPhamList.indexOf(sanPham.getMaLoai()));
     spnloai.setSelection(loaiSanPhamList.indexOf(list.get(position).getMaLoai()));
        khoiluong.setText(String.valueOf(list.get(position).getKhoiLuong()));
        luongcalo.setText(String.valueOf(list.get(position).getLuongCalo()));
        thanhphan.setText(String.valueOf(list.get(position).getThanhPhan()));
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //xử lý logic sửa sản phẩm ở đây
                // cập nhật dl trong danh sách list

                String MaSP = list.get(position).getMaSP();
                String TenSP = tensp.getText().toString();
                int Gia = Integer.parseInt((giasp.getText().toString()));
                String MaLoai = String.valueOf(spnloai.getSelectedItemPosition());
                String KhoiLuong = khoiluong.getText().toString();
                String LuongCalo= luongcalo.getText().toString();
                String ThanhPhan= thanhphan.getText().toString();

                SanPham sanPham1 = new SanPham(MaSP,TenSP,Gia,MaLoai, KhoiLuong,LuongCalo,ThanhPhan);
                HashMap<String, Object> mapsp = sanPham1.convertHashMap();

                database.collection("SanPham")
                        .document(MaSP)
                        .update(mapsp)
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
}
