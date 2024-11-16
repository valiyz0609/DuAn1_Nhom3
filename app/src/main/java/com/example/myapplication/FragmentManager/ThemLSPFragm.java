package com.example.myapplication.FragmentManager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.DAOModel.DAOSanPham;
import com.example.myapplication.Model.TheLoai;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ThemLSPFragm extends Fragment {
    
    DAOSanPham daoSanPham;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them_l_s_p, container, false);
        
//        Khai báo DAO
        daoSanPham = new DAOSanPham(getContext());

//        Back về màn hình trước
        ImageView btnBackLSP = view.findViewById(R.id.btnBackLSP);
        btnBackLSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new HomeFrgm());
            }
        });
//        Ánh xạ cho Editext
        EditText edtAddSPLoai = view.findViewById(R.id.edtAddSPLoai);
//        Ánh xạ Button
        EditText btnLSPHuy = view.findViewById(R.id.btnLSPHuy);
        EditText btnLSPXN = view.findViewById(R.id.btnLSPXN);
//        Sự kiện nút Hủy
        btnLSPHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtAddSPLoai.setText(null);
                edtAddSPLoai.setHintTextColor(Color.BLACK);
            }
        });
//        Sự kiện nút thêm
        btnLSPXN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strLoai = edtAddSPLoai.getText().toString();

                boolean checkAddLSP = true;

//                Kiểm tra Edt Rỗng
                if (strLoai.isEmpty()){
                    edtAddSPLoai.setError("Vui lòng nhập!");
                    edtAddSPLoai.setHintTextColor(Color.RED);
                    checkAddLSP = false;
                }

//                Kiểm tra Tên Loại trùng
                ArrayList<TheLoai> listLoaiSP = daoSanPham.getDSLSP();
                for (int i = 0; i < listLoaiSP.size(); i++) {
                    TheLoai mTheLoai = listLoaiSP.get(i);
                    String mTenLoai = mTheLoai.getTenLoai();
                    if (mTenLoai.equals(strLoai)){
                        checkAddLSP = false;
                        Toast.makeText(getContext(), "Loại sản phẩm đã tồn tại!", Toast.LENGTH_SHORT).show();
                        edtAddSPLoai.setText(null);
                        edtAddSPLoai.setHintTextColor(Color.BLACK);
                    }
                }

//                Kiểm tra điểu kiện
                if (checkAddLSP) {
                    TheLoai theLoai = new TheLoai(strLoai);
                    boolean checkAdd = daoSanPham.addLSP(theLoai);
                    if (checkAdd){
                        loadFragment(new HomeFrgm());
                        Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                        edtAddSPLoai.setText(null);
                        edtAddSPLoai.setHintTextColor(Color.BLACK);
                    }
                    else {
                        Toast.makeText(getContext(), "Fail!", Toast.LENGTH_SHORT).show();
                    }
                }

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