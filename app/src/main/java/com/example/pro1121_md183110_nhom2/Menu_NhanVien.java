package com.example.pro1121_md183110_nhom2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.pro1121_md183110_nhom2.fragment.Fragment_DoanhThu;
import com.example.pro1121_md183110_nhom2.fragment.Fragment_QL_DonHang;
import com.example.pro1121_md183110_nhom2.fragment.Fragment_QL_KhachHang;
import com.example.pro1121_md183110_nhom2.fragment.Fragment_QL_LoaiSanPham;
import com.example.pro1121_md183110_nhom2.fragment.Fragment_QL_NhanVien;
import com.example.pro1121_md183110_nhom2.fragment.Fragment_QL_SanPham;
import com.example.pro1121_md183110_nhom2.fragment.Fragment_Top_BanSanPham;
import com.google.android.material.navigation.NavigationView;

public class Menu_NhanVien extends AppCompatActivity {
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_nhan_vien);

        Toolbar toolbar = findViewById(R.id.toolbar_NV);
        FrameLayout frameLayout = findViewById(R.id.framelayout_NV);
        NavigationView navigationView = findViewById(R.id.navigationView_NV);
        drawerLayout=findViewById(R.id.drawerLayout_NV);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.menu_24);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment_nv = null;
                if (item.getItemId() == R.id.QLSanPham_NV){
                    fragment_nv = new Fragment_QL_SanPham();
                    Toast.makeText(getApplicationContext(),"Quản Lý Sản Phẩm",Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.QLLoaiSanPham_NV) {
                    fragment_nv = new Fragment_QL_LoaiSanPham();
                    Toast.makeText(getApplicationContext(),"Quản Lý Loại Sản Phẩm",Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.LogOut_NV) {
                    Intent intent = new Intent(Menu_NhanVien.this, DangNhap_NhanVien.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }  else if (item.getItemId() == R.id.QLKhachHang_NV) {
                    fragment_nv = new Fragment_QL_KhachHang();
                    Toast.makeText(getApplicationContext(),"Quản Lý Khách Hàng",Toast.LENGTH_SHORT).show();
                }else if (item.getItemId() == R.id.QLHoaDon_NV) {
                    fragment_nv = new Fragment_QL_DonHang();
                    Toast.makeText(getApplicationContext(),"Quản Lý Đơn Hàng",Toast.LENGTH_SHORT).show();
                }

                if (fragment_nv != null){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.framelayout_NV, fragment_nv)
                            .commit();
                    toolbar.setTitle(item.getTitle());
                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}