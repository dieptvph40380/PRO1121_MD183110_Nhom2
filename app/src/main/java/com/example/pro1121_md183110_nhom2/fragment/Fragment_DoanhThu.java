package com.example.pro1121_md183110_nhom2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pro1121_md183110_nhom2.R;


public class Fragment_DoanhThu extends Fragment {



    public Fragment_DoanhThu() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__doanh_thu, container, false);
    }
}