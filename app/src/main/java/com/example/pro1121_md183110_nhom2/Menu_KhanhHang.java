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

import com.example.pro1121_md183110_nhom2.fragment.Fragment_DoiMK_AD;
import com.example.pro1121_md183110_nhom2.fragment.Fragment_DoiMK_KH;
import com.example.pro1121_md183110_nhom2.fragment.Fragment_GioHang;
import com.example.pro1121_md183110_nhom2.fragment.Fragment_QL_DonHang;
import com.example.pro1121_md183110_nhom2.fragment.Fragment_Top_MuaSanPham_;
import com.example.pro1121_md183110_nhom2.fragment.Fragment_XemDH;
import com.example.pro1121_md183110_nhom2.fragment.Fragment_XemGH;
import com.example.pro1121_md183110_nhom2.fragment.Fragment_XemSP;
import com.google.android.material.navigation.NavigationView;

public class Menu_KhanhHang extends AppCompatActivity {
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_khanh_hang);

        Toolbar toolbar = findViewById(R.id.toolbar_KH);
        FrameLayout frameLayout = findViewById(R.id.framelayout_KH);
        NavigationView navigationView = findViewById(R.id.navigationView_KH);
        drawerLayout=findViewById(R.id.drawerLayout_KH);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.menu_24);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId() == R.id.Xem_SanPham){
                    fragment = new Fragment_XemSP();
                    Toast.makeText(getApplicationContext(),"Xem Danh Sách Sản Phẩm",Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.Xem_GioHang) {
                    fragment = new Fragment_GioHang();
                    Toast.makeText(getApplicationContext(),"Xem Giỏ Hàng",Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.LogOut_KH) {
                    Intent intent = new Intent(Menu_KhanhHang.this, DangNhap_KhachHang.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }  else if (item.getItemId() == R.id.DoiMk_KH) {
                    fragment = new Fragment_DoiMK_KH();
                    Toast.makeText(getApplicationContext(),"Đổi Mật Khẩu",Toast.LENGTH_SHORT).show();
                }else if (item.getItemId() == R.id.Xem_DonHang) {
                    fragment = new Fragment_XemDH();
                    Toast.makeText(getApplicationContext(),"Xem Đơn Hàng",Toast.LENGTH_SHORT).show();
                }

                if (fragment != null){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.framelayout_KH, fragment)
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