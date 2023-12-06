package com.example.pro1121_md183110_nhom2.adapter;// GioHangAdapter.java

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pro1121_md183110_nhom2.model.SanPham;

import java.util.List;

public class giohangAdapter extends RecyclerView.Adapter<giohangAdapter.ViewHolder> {
     List<SanPham> sanPhamList;

    public giohangAdapter(List<SanPham> productList) {
        this.sanPhamList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sanPham = sanPhamList.get(position);
//        holder.txtProductName.setText(sanPham.get);
//        holder.txtProductPrice.setText(String.valueOf(product.getPrice()));
//        holder.txtProductQuantity.setText(String.valueOf(product.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        private TextView txtProductName;
//        private TextView txtProductPrice;
//        private TextView txtProductQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            txtProductName = itemView.findViewById(R.id.txtProductName);
//            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);
//            txtProductQuantity = itemView.findViewById(R.id.txtProductQuantity);
        }
    }
}
