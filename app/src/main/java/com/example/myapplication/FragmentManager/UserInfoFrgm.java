package com.example.myapplication.FragmentManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.DAOModel.DAOUser;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;


public class UserInfoFrgm extends Fragment {

    TextView txtInfoUserName, txtInfoFullName, txtInfoChucVu, txtInfoSDT, txtInfoNamSinh;
    DAOUser daoUser;
    ImageView btnInfoEdit;
    private LinearLayout userFrgmDoiMK, userFrgmTKDoanhThu,userFrgmLoaisp, userFrgmTKNhanVien;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info_frgm, container, false);

//        Ánh xạ
        ImageView btnBackUserInfo = view.findViewById(R.id.btnBackUserInfo);
        txtInfoUserName = view.findViewById(R.id.txtInfoUserName);
        txtInfoFullName = view.findViewById(R.id.txtInfoFullName);
        txtInfoChucVu = view.findViewById(R.id.txtInfoChucVu);
        txtInfoSDT = view.findViewById(R.id.txtInfoSDT);
        txtInfoNamSinh = view.findViewById(R.id.txtInfoNamSinh);
        btnInfoEdit = view.findViewById(R.id.btnInfoEdit);

        userFrgmDoiMK = view.findViewById(R.id.userFrgmDoiMK);
        userFrgmTKDoanhThu = view.findViewById(R.id.userFrgmTKDoanhThu);
        userFrgmLoaisp = view.findViewById(R.id.userFrgmLoaisp);
        userFrgmTKNhanVien = view.findViewById(R.id.userFrgmTKNhanVien);

//        Get Data
        daoUser = new DAOUser(getContext());
        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", getActivity().MODE_PRIVATE);
        int maUser = pref.getInt("MA", 0);
        User user = daoUser.getUser(maUser);
        int quyenUser = user.getMaChucVu();

        if (quyenUser == 2) {
            userFrgmTKNhanVien.setVisibility(View.GONE);
            userFrgmLoaisp.setVisibility(View.GONE);
        }
//        Settext
        txtInfoUserName.setText(user.getUsername());
        txtInfoFullName.setText(user.getFullName());
        txtInfoChucVu.setText(user.getTenChucVu());
        txtInfoSDT.setText(user.getSDT());
        txtInfoNamSinh.setText(String.valueOf(user.getNamSinh()));

//        Set Onclick Button Back


        userFrgmDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new DoiMKFrgm());
            }
        });
        userFrgmLoaisp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new LoaispFrgm());
            }
        });

        userFrgmTKDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new TKDoanhThuFrgm());
            }
        });

        userFrgmTKNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new TKNhanVienFrgm());
            }
        });

        btnBackUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new HomeFrgm());
            }
        });

//        Set Onclick Button Edit
        btnInfoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new SuaNVFrgm(user));
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