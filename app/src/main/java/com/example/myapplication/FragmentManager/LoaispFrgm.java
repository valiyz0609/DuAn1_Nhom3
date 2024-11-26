package com.example.myapplication.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter_Package.AdapterLoaiSP;
import com.example.myapplication.DAOModel.DAOSanPham;
import com.example.myapplication.Model.TheLoai;
import com.example.myapplication.R;

import java.util.ArrayList;

public class LoaispFrgm extends Fragment {
    private RecyclerView recyclerloaisp;
    private DAOSanPham daoSanPham;
    ImageView btnbackLoaisp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loai_sp_fragment, container,false);
        recyclerloaisp = view.findViewById(R.id.recycler_Loaisp);
        btnbackLoaisp = view.findViewById(R.id.btnbackLoaisp);

        daoSanPham= new DAOSanPham(getContext());
        ArrayList<TheLoai> list = daoSanPham.getDSLSP();
        Log.d("TAG", "onCreateView: "+list.size());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerloaisp.setLayoutManager(linearLayoutManager);
        AdapterLoaiSP adapterLoaiSP= new AdapterLoaiSP(getContext(), list,daoSanPham);
        recyclerloaisp.setAdapter(adapterLoaiSP);
        adapterLoaiSP.notifyDataSetChanged();
        btnbackLoaisp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new UserInfoFrgm());
            }
        });
        return view;
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = (getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
