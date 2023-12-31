package com.example.pro1121_md183110_nhom2.adapter;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.example.pro1121_md183110_nhom2.R;
import com.example.pro1121_md183110_nhom2.fragment.Fragment_QL_SanPham;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
    ImageView imgsuanh;
    Spinner spnloai;
    Button btnsua,btnhuy,btnsuanh;
    List<LoaiSanPham> loaiSanPhamList;
    public Uri filePath; // đường dẫn file
    // khai báo request code để chọn ảnh
    private final int PICK_IMAGE_REQUEST = 22;
    FirebaseStorage storage;

    StorageReference storageReference;
    Uri downloadUri;
    String TAG = "zzzzzzzzzz";
    Fragment_QL_SanPham fragment_ql_sanPham;
    public void setFilePath(Uri filePath) {
        this.filePath = filePath;
    }

    public SanPhamAdapter(List<SanPham> list, Context context, FirebaseFirestore database,List<LoaiSanPham> loaiSanPhamList, Fragment_QL_SanPham fragment_ql_sanPham){
        this.list=list;
        this.context = context;
        this.database = database;
        this.loaiSanPhamList= loaiSanPhamList;
        this.fragment_ql_sanPham = fragment_ql_sanPham;
        fetchLoaiSanPhamData();

        // Khởi tạo FirebaseStorage
        storage = FirebaseStorage.getInstance();

        // Khởi tạo StorageReference
        storageReference = storage.getReference();
    }

    @NonNull
    @Override
    public SanPhamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater .inflate(R.layout.item_recycler_sp, parent, false);
        return new SanPhamAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        SanPham sanPham= list.get(position);
        holder.tensp.setText("Tên SP : "+sanPham.getTenSP());
        holder.giasp.setText("Giá : "+String.valueOf(sanPham.getGia()));
        holder.tenloai.setText("Tên Loại : "+sanPham.getTenLoai());
        Glide.with(context).load(list.get(position).getIMG()).into(holder.imganhsp);

//bật sk khi click vào nút sửa
        holder.imgsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment_ql_sanPham.check=2;
                fragment_ql_sanPham.showDialog(position,context);
            }
        });
        //Bắt sk khi click vô xóa
        holder.imgxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //xử lý xóa sản phẩm ở đây
                android.app.AlertDialog.Builder alertDelete = new android.app.AlertDialog.Builder(context);
                alertDelete.setTitle("canh bao");
                alertDelete.setMessage(" ban co chac muon xoa cong viec nay khong");
                alertDelete.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String MaSP = list.get(position).getMaSP();
                        database.collection("SanPham")
                                .document(MaSP)
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tensp,giasp,tenloai,khoiluong,luongcalo,thanhphan;
        Spinner spnloai;
        ImageView imganhsp;
        ImageView imgxoa,imgsua;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tensp=itemView.findViewById(R.id.tv_Ten_SP);
            giasp=itemView.findViewById(R.id.tv_Gia_SP);
            tenloai=itemView.findViewById(R.id.tv_Ten_Loai);
            imgsua=itemView.findViewById(R.id.img_Edit_SP);
            imgxoa=itemView.findViewById(R.id.img_Remove_SP);
            imganhsp=itemView.findViewById(R.id.img_SP);

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

                                loaiSanPhamList.add(document.toObject(LoaiSanPham.class));
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
        btnhuy=dialog.findViewById(R.id.btn_Huy_SP);
        btnsua=dialog.findViewById(R.id.btn_Sua_SP);
        //     List<String> dataList = new ArrayList<>();

        // Thêm các mục dữ liệu khác nếu cần
        // Thay thế LoaiSachDAO và phương thức getListLoaiSach() bằng truy vấn dữ liệu từ Firestore
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference loaiSachCollection = database.collection("LoaiSanPham");


        tensp.setText(list.get(position).getTenSP());
        giasp.setText(list.get(position).getGia()+"");
        khoiluong.setText(list.get(position).getKhoiLuong());
        luongcalo.setText(list.get(position).getLuongCalo());
        thanhphan.setText(list.get(position).getThanhPhan());

        int tenloai=getIndex(list.get(position).getMaLoai());


        Log.e("TAG",tenloai+"" );
        loaiSachCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                ArrayList<String> loaisachs = new ArrayList<>();
                int spnIndex = 0;

                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    LoaiSanPham loaiSanPham = document.toObject(LoaiSanPham.class);
                    loaisachs.add(loaiSanPham.getTenLSP());

                }

                spnloai.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, loaisachs));
                spnloai.setSelection(tenloai);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "lỗi", Toast.LENGTH_SHORT).show();
            }
        });
 //     spnloai.setSelection(loaiSanPhamList.indexOf(sanPham.getMaLoai()));


        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment_ql_sanPham.showDialog(position,context);
                //xử lý logic sửa sản phẩm ở đây
                // cập nhật dl trong danh sách list

                String MaSP = list.get(position).getMaSP();
                String TenSP = tensp.getText().toString();
              // int Gia = Integer.parseInt(String.valueOf(giasp.getText().toString()));
                int Gia=Integer.parseInt(giasp.getText().toString());

                String MaLoai = loaiSanPhamList.get(spnloai.getSelectedItemPosition()).getMaLSP();
                String KhoiLuong = khoiluong.getText().toString();
                String LuongCalo= luongcalo.getText().toString();
                String ThanhPhan= thanhphan.getText().toString();
                String TenLoai = loaiSanPhamList.get(spnloai.getSelectedItemPosition()).getTenLSP();
                String NSX = loaiSanPhamList.get(spnloai.getSelectedItemPosition()).getNSXLSP();
                String IMG=imgsuanh.toString();

                SanPham sanPham1 = new SanPham(MaSP,TenSP,Gia, KhoiLuong,LuongCalo,ThanhPhan,MaLoai,TenLoai,NSX,IMG);
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

    public int getIndex(String maloai) {
        int i=0;
        for(LoaiSanPham loaiSanPham : loaiSanPhamList){
            if(loaiSanPham.getMaLSP().equals(maloai)){
                break;

            }
            i++;
        }
        if(i==loaiSanPhamList.size()){
            return 0;
        }
        return i;
    }
}
