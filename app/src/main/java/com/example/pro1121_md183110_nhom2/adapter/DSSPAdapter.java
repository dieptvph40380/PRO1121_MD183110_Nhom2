package com.example.pro1121_md183110_nhom2.adapter;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.bumptech.glide.Glide;
import com.example.pro1121_md183110_nhom2.R;
import com.example.pro1121_md183110_nhom2.model.GioHang;
import com.example.pro1121_md183110_nhom2.model.LoaiSanPham;
import com.example.pro1121_md183110_nhom2.model.NhanVien;
import com.example.pro1121_md183110_nhom2.model.SanPham;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
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
    TextView nsx,khoiluong,luongcalo,thanhphan , TenSP_GH,Gia_GH ;
    Button btnhuy,btnthemgh;
    int sosize;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        
        Glide.with(context).load(list.get(position).getIMG()).into(holder.imgdssp);
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

               TenSP_GH=dialog.findViewById(R.id.tv_TenSP_GH);
               Gia_GH=dialog.findViewById(R.id.tv_Gia_SP);
               soluong = dialog.findViewById(R.id.edt_SL_TGH);
               size = dialog.findViewById(R.id.edt_Size_TGH);
               btnhuy=dialog.findViewById(R.id.btn_Huy_GH);
               btnthemgh = dialog.findViewById(R.id.btn_Them_GH);

               TenSP_GH.setText("Tên SP : "+list.get(position).getTenSP());
               Gia_GH.setText("Giá SP : "+list.get(position).getGia()+"");

               btnhuy.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       dialog.dismiss();
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
                               if (size.getText().toString().equals("Nhỏ")){

                               } else if (size.getText().toString().equals("Vừa")) {
                                   Gia_GH.setText("Giá SP : "+list.get(position).getGia()+" + 30");
                                   Toast.makeText(context, "adadad", Toast.LENGTH_SHORT).show();
                               } else if (size.getText().toString().equals("Lớn")) {
                                   Gia_GH.setText("Giá SP : "+list.get(position).getGia()+" + 50");
                               }
                           }
                       });

                       android.app.AlertDialog dialog = builder.create();
                       dialog.show();

                   }
               });

               btnthemgh.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       db=FirebaseFirestore.getInstance();
                       String TenSP=TenSP_GH.getText().toString();
                       SharedPreferences pref=context.getSharedPreferences("TTKH",Context.MODE_PRIVATE);
                       String usekh = pref.getString("USERNAME_KH","");
                       db.collection("GioHang")
                               .whereEqualTo("TenSP", TenSP)
                               .whereEqualTo("MaKH",usekh)
                               .get()
                               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                   @Override
                                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                       if (task.isSuccessful()) {
                                           // Nếu không có bản ghi nào có tên đăng nhập giống như tên đăng nhập mới
                                           if (task.getResult().isEmpty()) {
                                               if (validate()==1) {
                                                   int GiaGH = list.get(position).getGia();
                                                   int SoLuong = Integer.parseInt(soluong.getText().toString());
                                                   String Size = size.getText().toString();
                                                   String MaGH = UUID.randomUUID().toString();
                                                   String TenSP = list.get(position).getTenSP();

                                                   SharedPreferences pref = context.getSharedPreferences("TTKH", Context.MODE_PRIVATE);
                                                   String usekh = pref.getString("USERNAME_KH", "");

                                                   GioHang gh = new GioHang(MaGH, SoLuong, Size, usekh, TenSP, GiaGH);
                                                   HashMap<String, Object> mapGH = gh.convertHashMap();
                                                   db.collection("GioHang")
                                                           .document(MaGH)
                                                           .set(mapGH)
                                                           .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                               @Override
                                                               public void onSuccess(Void aVoid) {
                                                                   Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                                                   dialog.dismiss();
                                                               }
                                                           })
                                                           .addOnFailureListener(new OnFailureListener() {
                                                               @Override
                                                               public void onFailure(@NonNull Exception e) {
                                                                   Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();

                                                               }
                                                           });
                                               }

                                           } else {

                                               // Tên đăng nhập đã tồn tại, xử lý thông báo hoặc hành động phù hợp
                                               Toast.makeText(context, "Đã có sản phẩm này trong giỏ hàng . Vui lòng chọn sản phẩm khác", Toast.LENGTH_SHORT).show();

                                           }
                                       } else {
                                           // Xử lý khi truy vấn không thành công
                                           Toast.makeText(context, "Lỗi khi kiểm tra tên đăng nhập.", Toast.LENGTH_SHORT).show();
                                       }
                                   }
                               });
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

                nsx.setText("Nhà Sản Xuất : "+list.get(position).getNSX()+"");
                khoiluong.setText("Khối Lượng : " +list.get(position).getKhoiLuong()+"");
                luongcalo.setText("Lượng Calo : "+list.get(position).getLuongCalo()+"");
                thanhphan.setText("Thành Phần : "+list.get(position).getThanhPhan()+"");

            }
        });


    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tensp,gia,tenloai;
        Button btnxemct,btnthemGH,btnDatHang;
        ImageView imgdssp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgdssp=itemView.findViewById(R.id.img_DSSP);
            tensp = itemView.findViewById(R.id.tv_Ten_DSSP);
            gia = itemView.findViewById(R.id.tv_Gia_DSSP);;
            tenloai = itemView.findViewById(R.id.tv_Ten_Loai_DSSP);
            btnxemct = itemView.findViewById(R.id.btn_xemct_DSSP) ;
            btnthemGH=itemView.findViewById(R.id.btn_themgh_DSSP);
            btnDatHang=itemView.findViewById(R.id.btn_DatHang_GH);

        }
    }
    public int validate(){
        int validate;
        if(size.getText().length()==0||soluong.getText().length()==0){
            Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin  ", Toast.LENGTH_SHORT).show();
            validate=0;
        }else {
            validate =1;

        }
        return validate;
    }
}
