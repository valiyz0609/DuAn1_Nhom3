package com.example.myapplication.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.DAOModel.DAOGioHang;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.GioHang;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ChiTietSPFrgm extends Fragment {

    SanPham sanPham;
    String sizeCheck;
    TextView txtChiTietTenSp, txtChiTietGiaSP, txtChiTietMoTaSP, txtChiTietTongTien, txtChiTietSL;
    ImageView img_sp, btnSoLuongTang, btnSoLuongGiam;
    double donGia = 0;
    int soLuong;
    double tongTien;
    DAOGioHang daoGioHang;

    public ChiTietSPFrgm(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chi_tiet_s_p_frgm, container, false);
        txtChiTietTenSp = view.findViewById(R.id.txtChiTietTenSp);
        txtChiTietGiaSP = view.findViewById(R.id.txtChiTietGiaSP);
        txtChiTietSL = view.findViewById(R.id.txtChiTietSL);
        img_sp = view.findViewById(R.id.imgCTSanPham);
        txtChiTietTongTien = view.findViewById(R.id.txtChiTietTongTien);
        btnSoLuongTang = view.findViewById(R.id.btnSoLuongTang);
        btnSoLuongGiam = view.findViewById(R.id.btnSoLuongGiam);
        ImageView btnbackCT = view.findViewById(R.id.btnbackCT);

        btnbackCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new HomeFrgm());
            }
        });

        daoGioHang = new DAOGioHang(getContext());

        RadioButton rdoSizeLon = view.findViewById(R.id.rdoSizeLon);
        RadioButton rdoSizeVua = view.findViewById(R.id.rdoSizeVua);
        RadioButton rdoSizeNho = view.findViewById(R.id.rdoSizeNho);

//        Set kích thước Size

        double donGiaGoc = sanPham.getPrice();
        rdoSizeNho.setChecked(true);
        sizeCheck = "S";
        donGia = 0;

        rdoSizeLon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rdoSizeNho.setChecked(false);
                    rdoSizeVua.setChecked(false);
                    sizeCheck = "L";
                    donGia = 10000;
                    tongTien = tinhTien(soLuong, donGia, donGiaGoc);
                    String mTinhTien = String.format("%,.0f", tongTien);
                    txtChiTietTongTien.setText(mTinhTien + " k");
                }
            }
        });

        rdoSizeVua.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rdoSizeLon.setChecked(false);
                    rdoSizeNho.setChecked(false);
                    sizeCheck = "M";
                    donGia = 5000;
                    tongTien = tinhTien(soLuong, donGia, donGiaGoc);
                    String mTinhTien = String.format("%,.0f", tongTien);
                    txtChiTietTongTien.setText(mTinhTien + " k");
                }
            }
        });

        rdoSizeNho.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rdoSizeLon.setChecked(false);
                    rdoSizeVua.setChecked(false);
                    sizeCheck = "N";
                    donGia = 0;
                    tongTien = tinhTien(soLuong, donGia, donGiaGoc);
                    String mTinhTien = String.format("%,.0f", tongTien);
                    txtChiTietTongTien.setText(mTinhTien + " k");
                }
            }
        });

//        Set số lượng
        soLuong = 1;
        txtChiTietSL.setText("0" + soLuong);
        btnSoLuongGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soLuong > 1){
                    soLuong --;
                    if (soLuong < 10){
                        txtChiTietSL.setText("0" + soLuong);
                    }
                    else {
                        txtChiTietSL.setText(soLuong + "");
                    }
                    tongTien = tinhTien(soLuong, donGia, donGiaGoc);
                    String mTinhTien = String.format("%,.0f", tongTien);
                    txtChiTietTongTien.setText(mTinhTien + " k");
                }
            }
        });

        btnSoLuongTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soLuong++;
                if (soLuong < 10){
                    txtChiTietSL.setText("0" + soLuong);
                }
                else {
                    txtChiTietSL.setText(soLuong + "");
                }
                tongTien = tinhTien(soLuong, donGia, donGiaGoc);
                String mTinhTien = String.format("%,.0f", tongTien);
                txtChiTietTongTien.setText(mTinhTien + " k");
            }
        });

//        Set Data cho các View
        txtChiTietTenSp.setText("Tên sản phẩm: "+sanPham.getTenSanPham());
        double giaSP = sanPham.getPrice();
        String mGiaSP = String.format("%,.0f", giaSP);
        txtChiTietGiaSP.setText("Giá: "+mGiaSP + " k");
        byte[] productsImage = sanPham.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(productsImage, 0, productsImage.length);
        img_sp.setImageBitmap(bitmap);

        tongTien = tinhTien(soLuong, donGia, donGiaGoc);
        String mTinhTien = String.format("%,.0f", tongTien);
        txtChiTietTongTien.setText(mTinhTien + " k");

        EditText btnChiTietAddToCart = view.findViewById(R.id.btnChiTietAddToCart);

//        Thêm sự kiện Button Add
        btnChiTietAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GioHang gioHang = new GioHang(1, sanPham.getId(), soLuong, sizeCheck, donGia + donGiaGoc);
                ArrayList<GioHang> outList = daoGioHang.checkValidGioHang(gioHang);
                if (outList.size() != 0){
//                - Có: Update số lượng
                    GioHang gioHang1 = outList.get(0);
                    int newSL = gioHang1.getSoLuong() + gioHang.getSoLuong();
                    gioHang.setSoLuong(newSL);
                    boolean kiemtra = daoGioHang.updateGioHang(gioHang);
                    if (kiemtra){
                        Toast.makeText(getContext(), "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "Update SL Fail!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
//                - Không: Thêm sản phẩm
                    boolean check = daoGioHang.addGiohang(gioHang);
                    if (check){
                        Toast.makeText(getContext(), "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "Fail!", Toast.LENGTH_SHORT).show();
                    }
                }
                loadFragment(new StoreFrgm());
                MainActivity.bottomNavigationView.setSelectedItemId(R.id.pageBanHang);
            }
        });

        return view;
    }

//    Tính tổng tiền
    public double tinhTien(int mSoLuong, double mDonGia, double mDonGiaGoc){
        double tongTien = 0;
        tongTien = mSoLuong * (mDonGia + mDonGiaGoc);
        return tongTien;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = (getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}