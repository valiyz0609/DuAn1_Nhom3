package com.example.myapplication.FragmentManager;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter_Package.AdapterHome;
import com.example.myapplication.DAOModel.DAOLuuHD;
import com.example.myapplication.DAOModel.DAOSanPham;
import com.example.myapplication.DAOModel.DAOUser;
import com.example.myapplication.DangNhapAct;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.Model.TheLoai;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;

import java.util.ArrayList;

public class HomeFrgm extends Fragment {

    RecyclerView recycler_SPBanChay;
    private AdapterHome adapterHome;
    private ArrayList<SanPham> listSpTopOut = new ArrayList<>();
    DAOLuuHD daoLuuHD;
    DAOSanPham daoSanPham;
    LinearLayout layoutParent;
    DAOUser daoUser;
    private LinearLayout userFrgmTaiKhoan, userFrgmDoiMK, userFrgmTKDoanhThu,userFrgmTKDoanhThuNV, userFrgmTKNhanVien, userFrgmThemSP, userFrgmThemLSP, userFrgmThemNhanVien, userFrgmDangXuat;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_frgm, container, false);
        layoutParent = view.findViewById(R.id.layoutParent);
        recycler_SPBanChay = view.findViewById(R.id.recycler_SPBanChay);
        daoLuuHD = new DAOLuuHD(getContext());
        daoSanPham = new DAOSanPham(getContext());
        daoUser = new DAOUser(getContext());


        userFrgmTaiKhoan = view.findViewById(R.id.userFrgmTaiKhoan);
        userFrgmThemSP = view.findViewById(R.id.userFrgmThemSP);
        userFrgmThemLSP = view.findViewById(R.id.userFrgmThemLSP);
        userFrgmThemNhanVien = view.findViewById(R.id.userFrgmThemNhanVien);
        userFrgmDangXuat = view.findViewById(R.id.userFrgmDangXuat);


        daoUser = new DAOUser(getContext());



        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", getActivity().MODE_PRIVATE);
        int maUserNow = pref.getInt("MA", 0);
        User user = daoUser.getUser(maUserNow);
        int quyenUser = user.getMaChucVu();


        if (quyenUser == 2) {
            userFrgmThemNhanVien.setVisibility(View.GONE);
            userFrgmThemSP.setVisibility(View.GONE);
            userFrgmThemLSP.setVisibility(View.GONE);
        }


        userFrgmTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new UserInfoFrgm());
            }
        });


        userFrgmThemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ThemSPFrgm());
            }
        });

        userFrgmThemLSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ThemLSPFragm());
            }
        });

        userFrgmThemNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ThemNhanVienFrgm());
            }
        });

        userFrgmDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_confirm);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView dialog_confirm_content = dialog.findViewById(R.id.dialog_confirm_content);
                dialog_confirm_content.setText("Bạn muốn đăng xuất?");
                EditText btnDialogHuy = dialog.findViewById(R.id.btnDialogHuy);
                EditText btnDialogXN = dialog.findViewById(R.id.btnDialogXN);
                btnDialogXN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), DangNhapAct.class);
                        Toast.makeText(getContext(), "Đăng xuất!", Toast.LENGTH_SHORT).show();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                btnDialogHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        ArrayList<SanPham> listSanPham = daoSanPham.getAllProduct(0);
        ArrayList<Integer> listMaSPTop = daoLuuHD.getTopSP();
        for (int i = 0; i < listMaSPTop.size(); i++) {
            for (int j = 0; j < listSanPham.size(); j++) {
                if (listMaSPTop.get(i) == listSanPham.get(j).getId()){
                    listSpTopOut.add(listSanPham.get(j));
                }
            }
        }

        ArrayList<TheLoai> listLoaiSP = daoSanPham.getDSLSP();
        for (int i = 0; i < listLoaiSP.size(); i++) {
            ArrayList<SanPham> listSP = daoSanPham.getSPofTL(listLoaiSP.get(i).getMaLoai());
            if (listSP.size() != 0){
                View addLayout = inflater.inflate(R.layout.list_san_pham, null);
                TextView tittle = addLayout.findViewById(R.id.txtSPHomeTittle);
                tittle.setText(listLoaiSP.get(i).getTenLoai());
                RecyclerView recyclerViewAdd = addLayout.findViewById(R.id.recycler_SPTheoLoai);
                AdapterHome adapterHome1 = new AdapterHome(listSP, getContext());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerViewAdd.setLayoutManager(linearLayoutManager);
                recyclerViewAdd.setAdapter(adapterHome1);
                layoutParent.addView(addLayout);
            }
        }

        adapterHome = new AdapterHome(listSpTopOut ,getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_SPBanChay.setLayoutManager(linearLayoutManager);
        recycler_SPBanChay.setAdapter(adapterHome);


        return view;


    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = (getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}