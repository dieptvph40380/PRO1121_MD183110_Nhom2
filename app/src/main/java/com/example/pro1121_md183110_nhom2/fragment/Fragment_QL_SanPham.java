package com.example.pro1121_md183110_nhom2.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pro1121_md183110_nhom2.R;
import com.example.pro1121_md183110_nhom2.adapter.NhanVienAdapter;
import com.example.pro1121_md183110_nhom2.adapter.SanPhamAdapter;
import com.example.pro1121_md183110_nhom2.adapter.SpinnerAdapter;
import com.example.pro1121_md183110_nhom2.model.LoaiSanPham;
import com.example.pro1121_md183110_nhom2.model.NhanVien;
import com.example.pro1121_md183110_nhom2.model.SanPham;
import com.google.android.gms.tasks.Continuation;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firestore.v1.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class Fragment_QL_SanPham extends Fragment {

    FirebaseFirestore db;
    RecyclerView rcv;
    FloatingActionButton fab;
    Dialog dialog;
    EditText maloai, tenloai, soluong, tensp, giasp, thanhphan, luongcalo, khoiluong;
    Spinner spnloai;
    ArrayList<SanPham> list;

    SanPhamAdapter adapter;
    Context context;
    ArrayList<LoaiSanPham> loaiSanPhamList;
    SpinnerAdapter spinnerAdapter;
    Button btnthem, btnhuy,btnimg,btnsuaanh,btnsua;

    ImageView imgthemanh, imgsuaanh ;
    private Uri filePath; // đường dẫn file
    // khai báo request code để chọn ảnh
    private final int PICK_IMAGE_REQUEST = 22;
    FirebaseStorage storage;
    StorageReference storageReference;
    Uri downloadUri;
    String TAG = "zzzzzzzzzz";

     public int check;


    public Fragment_QL_SanPham() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__q_l__san_pham, container, false);
        list = new ArrayList<>();
        fab = view.findViewById(R.id.floatAdd_SP);
        rcv = view.findViewById(R.id.recycler_SP);
        db = FirebaseFirestore.getInstance();


        storage = FirebaseStorage.getInstance("gs://duan1-82699.appspot.com");
        storageReference = storage.getReference();
        loaiSanPhamList =new ArrayList<>();

        adapter = new SanPhamAdapter(list, getContext(), db,loaiSanPhamList,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(adapter);

        ListenFirebaseFirestore();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialog(context, 0);
                check=1;
            }
        });


        return view;
    }

    private void ListenFirebaseFirestore() {
        db.collection("SanPham").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("TAG", "fail", error);
                    return;
                }
                if (value != null) {
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        switch (dc.getType()) {
                            case ADDED: {
                                SanPham newU = dc.getDocument().toObject(SanPham.class);
                                list.add(newU);
                                adapter.notifyItemInserted(list.size() - 1);
                                break;
                            }
                            case MODIFIED: {
                                SanPham update = dc.getDocument().toObject(SanPham.class);
                                if (dc.getOldIndex() == dc.getNewIndex()) {
                                    list.set(dc.getOldIndex(), update);
                                    adapter.notifyItemChanged(dc.getOldIndex());

                                } else {
                                    list.remove(dc.getOldIndex());
                                    list.add(update);
                                    adapter.notifyItemMoved(dc.getOldIndex(), dc.getNewIndex());

                                }
                                break;
                            }
                            case REMOVED: {
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

    public void openDialog(final Context context, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.dialog_them_sp, null);
        builder.setView(view);
        Dialog dialog1 = builder.create();
        dialog1.show();

        tensp = view.findViewById(R.id.edt_Ten_SP);
        giasp = view.findViewById(R.id.edt_Gia_SP);
        spnloai = view.findViewById(R.id.spn_TenLoai);

        khoiluong = view.findViewById(R.id.edt_Klg_SP);
        luongcalo = view.findViewById(R.id.edt_LgCalo_SP);
        thanhphan = view.findViewById(R.id.edt_TP_SP);

        btnthem = view.findViewById(R.id.btn_Them_SP);
        btnhuy = view.findViewById(R.id.btn_Huy_SP);
        btnimg = view.findViewById(R.id.btn_tanh_sp);
        imgthemanh=view.findViewById(R.id.img_them_sp);

        loaiSanPhamList = new ArrayList<>();
        spinnerAdapter = new SpinnerAdapter(getContext(), loaiSanPhamList);
        spnloai.setAdapter(spinnerAdapter);
        getLoaiSanPham();

        btnimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SanPham sanPham = new SanPham();
//                sanPham.setThanhPhan(thanhphan.getText().toString());
//                sanPham.setLuongCalo(luongcalo.getText().toString());
//                sanPham.setGia(Integer.parseInt(giasp.getText().toString()));
//                sanPham.setKhoiLuong(khoiluong.getText().toString());
//                sanPham.setTenSP(tensp.getText().toString());
//                sanPham.setMaLoai(loaiSanPhamList.get(spnloai.getSelectedItemPosition()).getMaLSP());
//                sanPham.setTenLoai(loaiSanPhamList.get(spnloai.getSelectedItemPosition()).getTenLSP());
//                sanPham.setNSX(loaiSanPhamList.get(spnloai.getSelectedItemPosition()).getNSXLSP());
//                db.collection("SanPham").document(masanpham).set(sanPham).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()){
//                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            Toast.makeText(getContext(), "thêm thất bại", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                });
                uploadImage(dialog1);


            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

    }

    private void getLoaiSanPham() {
        db.collection("LoaiSanPham").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override

            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isComplete()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.e("TAG", "onComplete: "+document.toObject(LoaiSanPham.class).getTenLSP());
                        loaiSanPhamList.add(document.toObject(LoaiSanPham.class));
                        spinnerAdapter.notifyDataSetChanged();
                    }
                }



            }
        });

    }

    public void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }


    @Override
    public void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                ((SanPhamAdapter) rcv.getAdapter()).setFilePath(data.getData());
            }
            // Lấy dữ liệu từ màn hình chọn ảnh truyền về
            filePath = data.getData();


//            Toast.makeText(getContext(), ""+img_themnv, Toast.LENGTH_SHORT).show();
            Log.d("zzzzz", "onActivityResult: " + filePath.toString());


            try {
                // xem thử ảnh , nếu không muốn xem thử thì bỏ đoạn code này

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(((Activity) getContext()).getContentResolver(), filePath);
                if(check ==1){
                    imgthemanh.setImageBitmap(bitmap);
                }else {
                    imgsuaanh.setImageBitmap(bitmap);
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

    }



    private void uploadImage(Dialog dialog1 ) {

    if(validate()==1) {
        if (filePath != null) {

            // Hiển thị dialog
            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Tạo đường dẫn lưu trữ file, images/ là 1 thư mục trên firebase, chuỗi uuid... là tên file, tạm thời có thể phải lên web firebase tạo sẵn thư mục images
            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());


            Log.d(TAG, "uploadImage: " + ref.getPath());

            // Tiến hành upload file
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // upload thành công, tắt dialog
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Image Uploaded!!", Toast.LENGTH_SHORT).show();

                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace(); // có lỗi upload
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    // cập nhật tiến trình upload
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                                }
                            })
                    .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            // gọi task để lấy URL sau khi upload thành công
                            return ref.getDownloadUrl();
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                downloadUri = task.getResult();
                                // upload thành công, lấy được url ảnh, ghi ra log. Bạn có thể ghi vào CSdl....
//                                img_themnv.setImageURI(downloadUri);
//                                Toast.makeText(getContext(), ""+img_themnv, Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onComplete: url download = " + downloadUri.toString());

                                String TenSP = tensp.getText().toString();
                                db.collection("SanPham")
                                        .whereEqualTo("TenSP", TenSP)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    // Nếu không có bản ghi nào có tên đăng nhập giống như tên đăng nhập mới
                                                    if (task.getResult().isEmpty()) {

                                                        String MaSP = UUID.randomUUID().toString();
                                                        String TenSP = tensp.getText().toString();
                                                        int Gia = Integer.parseInt(giasp.getText().toString());
                                                        String KhoiLuong = khoiluong.getText().toString();
                                                        String LuongCalo = luongcalo.getText().toString();
                                                        String ThanPhan = thanhphan.getText().toString();
                                                        String MaLoai = loaiSanPhamList.get(spnloai.getSelectedItemPosition()).getMaLSP();
                                                        String TenLoai = loaiSanPhamList.get(spnloai.getSelectedItemPosition()).getTenLSP();
                                                        String NSX = loaiSanPhamList.get(spnloai.getSelectedItemPosition()).getNSXLSP();

                                                        SanPham sp = new SanPham(MaSP, TenSP, Gia, KhoiLuong, LuongCalo, ThanPhan, MaLoai, TenLoai, NSX, downloadUri.toString());
                                                        HashMap<String, Object> mapSP = sp.convertHashMap();

                                                        db.collection("SanPham")
                                                                .document(MaSP)
                                                                .set(mapSP)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                                                        dialog1.dismiss();
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
                                                        Toast.makeText(getContext(), "Tên sản phẩm đã tồn tại, vui lòng chọn tên sản phẩm khác.", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    // Xử lý khi truy vấn không thành công
                                                    Toast.makeText(getContext(), "Lỗi khi kiểm tra tên đăng nhập.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });


                            } else {
                                // lỗi lấy url download
                            }

                        }
                    });

        }else {
            Toast.makeText(getContext(), "Vui lòng chọn ảnh", Toast.LENGTH_SHORT).show();
        }
        dialog1.show();
    }

    }
    public int validate(){
        int validate;
        if(tensp.getText().length()==0||giasp.getText().length()==0||khoiluong.getText().length()==0||luongcalo.getText().length()==0||thanhphan.getText().length()==0) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin  ", Toast.LENGTH_SHORT).show();
            validate = 0;
        } else if (giasp.getText().length()<0) {
            Toast.makeText(getContext(), "giá phải là số dương ", Toast.LENGTH_SHORT).show();
            validate = 2;
        } else  {
            validate =1;

        }
        return validate;
    }


    public void showDialog(int position,Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.dialog_sua_sp, null);
        builder.setView(view);
        Dialog dialog2 = builder.create();
        dialog2.show();

//        dialog = new Dialog(context);
//        dialog.setContentView(R.layout.dialog_sua_sp);
        imgsuaanh=view.findViewById(R.id.img_sua_sp);
        tensp=view.findViewById(R.id.edt_Ten_SP);
        giasp=view.findViewById(R.id.edt_Gia_SP);
        spnloai=view.findViewById(R.id.spn_TenLoai);
        khoiluong=view.findViewById(R.id.edt_Klg_SP);
        luongcalo=view.findViewById(R.id.edt_LgCalo_SP);
        thanhphan=view.findViewById(R.id.edt_TP_SP);
        btnhuy=view.findViewById(R.id.btn_Huy_SP);
        btnsua=view.findViewById(R.id.btn_Sua_SP);
        btnsuaanh=view.findViewById(R.id.btn_sanh_sp);
        //     List<String> dataList = new ArrayList<>();

        // Thêm các mục dữ liệu khác nếu cần
        // Thay thế LoaiSachDAO và phương thức getListLoaiSach() bằng truy vấn dữ liệu từ Firestore
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference loaiSachCollection = database.collection("LoaiSanPham");

        Glide.with(getContext()).load(list.get(position).getIMG()).into(imgsuaanh);
        tensp.setText(list.get(position).getTenSP());
        giasp.setText(list.get(position).getGia()+"");
        khoiluong.setText(list.get(position).getKhoiLuong());
        luongcalo.setText(list.get(position).getLuongCalo());
        thanhphan.setText(list.get(position).getThanhPhan());

        int tenloai=adapter.getIndex(list.get(position).getMaLoai());
        btnsuaanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
                Toast.makeText(getContext(), ""+tenloai, Toast.LENGTH_SHORT).show();
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });



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

                spnloai.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, loaisachs));
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
                //xử lý logic sửa sản phẩm ở đây
                // cập nhật dl trong danh sách list

                uploadImagesua(position,context);
                dialog2.dismiss();
            }
        });


    }

    private void uploadImagesua(int position,Context context) {

        if (filePath != null) {

            // Hiển thị dialog
            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Tạo đường dẫn lưu trữ file, images/ là 1 thư mục trên firebase, chuỗi uuid... là tên file, tạm thời có thể phải lên web firebase tạo sẵn thư mục images
            StorageReference ref = storageReference .child(  "images/" + UUID.randomUUID().toString());

            Log.d(TAG, "uploadImage: " + ref.getPath());

            // Tiến hành upload file
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess( UploadTask.TaskSnapshot taskSnapshot) {
                                    // upload thành công, tắt dialog
                                    progressDialog.dismiss();
                                    Toast.makeText(context, "Image Uploaded!!", Toast.LENGTH_SHORT) .show();

                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace(); // có lỗi upload
                            progressDialog.dismiss();
                            Toast.makeText(context, "Failed " + e.getMessage(),  Toast.LENGTH_SHORT) .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    // cập nhật tiến trình upload
                                    double progress  = (100.0   * taskSnapshot.getBytesTransferred()  / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage( "Uploaded " + (int) progress + "%");
                                }
                            })
                    .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            // gọi task để lấy URL sau khi upload thành công
                            return ref.getDownloadUrl();
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                downloadUri = task.getResult();
                                // upload thành công, lấy được url ảnh, ghi ra log. Bạn có thể ghi vào CSdl....
//                                img_themnv.setImageURI(downloadUri);
//                                Toast.makeText(getContext(), ""+img_themnv, Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onComplete: url download = " + downloadUri.toString() );

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


                                SanPham sanPham = new SanPham(MaSP,TenSP,Gia, KhoiLuong,LuongCalo,ThanhPhan,MaLoai,TenLoai,NSX,downloadUri.toString());
                                HashMap<String, Object> mapsp = sanPham.convertHashMap();

                                db.collection("SanPham")
                                        .document(MaSP)
                                        .update(mapsp)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(getContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
                                            }
                                        });


                            } else {
                                Toast.makeText(getContext(), "adadadad", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

        }else {
            Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
        }


    }


}


