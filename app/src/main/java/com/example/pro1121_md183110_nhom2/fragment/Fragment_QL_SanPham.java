package com.example.pro1121_md183110_nhom2.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pro1121_md183110_nhom2.R;
import com.example.pro1121_md183110_nhom2.adapter.NhanVienAdapter;
import com.example.pro1121_md183110_nhom2.adapter.SanPhamAdapter;
import com.example.pro1121_md183110_nhom2.model.LoaiSanPham;
import com.example.pro1121_md183110_nhom2.model.NhanVien;
import com.example.pro1121_md183110_nhom2.model.SanPham;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class Fragment_QL_SanPham extends Fragment {

    FirebaseFirestore db;
    RecyclerView rcv;
    FloatingActionButton fab;
    Dialog dialog;
    EditText maloai,tenloai,soluong,tensp, giasp, thanhphan,luongcalo , khoiluong;
    Spinner spnloai;
    List<SanPham> list= new ArrayList<>();

    SanPhamAdapter adapter;
    Context context;
    List<String> loaiSanPhamList= new ArrayList<>();

    Button btnthem,btnhuy;
    SanPhamAdapter sanPhamAdapter;


    FirebaseFirestore database;

    public Fragment_QL_SanPham() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment__q_l__san_pham, container, false);

        fab=view.findViewById(R.id.floatAdd_SP);
        rcv=view.findViewById(R.id.recycler_SP);
        db=FirebaseFirestore.getInstance();
        ListenFirebaseFirestore();

       // nhanVienAdapter= new NhanVienAdapter(nvList,getContext(),db);


        adapter = new SanPhamAdapter(list,getContext(),db);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
openDialog(context,0);
            }
        });


        return view;
    }

    private void ListenFirebaseFirestore(){
        db.collection("SanPham").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.e("TAG", "fail", error);
                    return;
                }
                if(value != null){
                    for (DocumentChange dc: value.getDocumentChanges()){
                        switch (dc.getType()){
                            case ADDED:{
                                SanPham newU = dc.getDocument().toObject(SanPham.class);
                                list.add(newU);
                                adapter.notifyItemInserted(list.size() - 1);
                                break;
                            }
                            case MODIFIED:{
                                SanPham update = dc.getDocument().toObject(SanPham.class);
                                if(dc.getOldIndex() == dc.getNewIndex()){
                                    list.set(dc.getOldIndex(), update);
                                    adapter.notifyItemChanged(dc.getOldIndex());

                                } else {
                                    list.remove(dc.getOldIndex());
                                    list.add(update);
                                    adapter.notifyItemMoved(dc.getOldIndex(), dc.getNewIndex());

                                }
                                break;
                            }
                            case REMOVED:{
                                dc.getDocument().toObject(SanPham.class);
                                list.remove(dc.getOldIndex());
                                adapter.notifyItemRemoved(dc.getOldIndex());
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    public void openDialog(final Context context,  int position){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_them_sp);

        tensp = dialog.findViewById(R.id.edt_Ten_SP);
        giasp = dialog.findViewById(R.id.edt_Gia_SP);
        spnloai = dialog.findViewById(R.id.spn_TenLoai);

        khoiluong = dialog.findViewById(R.id.edt_Klg_SP);
        luongcalo = dialog.findViewById(R.id.edt_LgCalo_SP);
        thanhphan = dialog.findViewById(R.id.edt_TP_SP);

        btnthem = dialog.findViewById(R.id.btn_Them_SP);
        btnhuy = dialog.findViewById(R.id.btn_HuyT_SP);


       btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
         public void onClick(View v) {
            String TenSP=tensp.getText().toString();
               db.collection("SanPham")
                        .whereEqualTo("TenSP", TenSP)
                       .get()
                      .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                           @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                               if (task.isSuccessful()) {
                                   // Nếu không có bản ghi nào có tên đăng nhập giống như tên đăng nhập mới
                                   if (task.getResult().isEmpty()) {
                                       String TenSP=tensp.getText().toString();
                                       int Gia= Integer.parseInt(giasp.getText().toString());
                                       String KhoiLuong=khoiluong.getText().toString();
                                       String LuongCalo=luongcalo.getText().toString();
                                       String ThanhPhan=thanhphan.getText().toString();
                                       String MaLoai="maloai.getText().toString();";
                                       String TenLoai="tenloai.getText().toString();";
                                       int SoLuong=2;
                                    //   String maSP, String tenSP, int gia, String khoiLuong, String luongCalo, String thanhPhan, String maLoai, String tenLoai, int soLuong)
                                       String MaSP= UUID.randomUUID().toString();
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
                                        SanPham sp = new SanPham(MaSP,TenSP,Gia,KhoiLuong,LuongCalo,ThanhPhan,MaLoai);
                                       HashMap<String, Object> mapSP = sp.convertHashMap();
                                       db.collection("SanPham")
                                                .document(MaSP)
                                                .set(mapSP)
                                               .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                       Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                                    }
                                               })
                                                .addOnFailureListener(new OnFailureListener() {
                                                       @Override

                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();

                                                    }
                                                });
                                    } else {
                                        // Tên đăng nhập đã tồn tại, xử lý thông báo hoặc hành động phù hợp
                                        Toast.makeText(getContext(), "Tên đăng nhập đã tồn tại, vui lòng chọn tên đăng nhập khác.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // Xử lý khi truy vấn không thành công
                                    Toast.makeText(getContext(), "Lỗi khi kiểm tra tên đăng nhập.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                dialog.dismiss();
            }
        });
        dialog.show();
    }
//    private ArrayList<HashMap<String,Object>> getDSLoaiSach() {
//        ArrayList<LoaiSanPham> list1 = loaiSachDAO.getDSLoaiSach();
//        ArrayList<HashMap<String,Object>> listHM = new ArrayList<>();
//
//        for(LoaiSach loai: list1) {
//            HashMap<String,Object> hs = new HashMap<>();
//            hs.put("maloai",loai.getMaloai());
//            hs.put("tenloai",loai.getTenLoai());
//            listHM.add(hs);
//        }
//        return listHM;
//    }
}