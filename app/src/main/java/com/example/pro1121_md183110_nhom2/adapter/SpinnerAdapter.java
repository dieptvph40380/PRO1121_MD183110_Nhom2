package com.example.pro1121_md183110_nhom2.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pro1121_md183110_nhom2.R;
import com.example.pro1121_md183110_nhom2.model.LoaiSanPham;

import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter {
Context context;
ArrayList<LoaiSanPham> list;

    public SpinnerAdapter(Context context, ArrayList<LoaiSanPham> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        convertView=inflater.inflate(R.layout.spinnerlayout,parent,false);
        TextView spn=convertView.findViewById(R.id.spinnerl);
        spn.setText(list.get(position).getTenLSP());
        Log.e("TAG", "getView: "+ list.get(position).getNSXLSP() );
        return convertView;


    }
}
