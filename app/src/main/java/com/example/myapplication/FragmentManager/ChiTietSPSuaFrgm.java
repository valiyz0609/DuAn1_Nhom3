package com.example.myapplication.FragmentManager;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.DAOModel.DAOSanPham;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.Model.TheLoai;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ChiTietSPSuaFrgm extends Fragment implements View.OnClickListener {

    SanPham sanPham;
    DAOSanPham dao;
    double tongTien = 0;


    public ChiTietSPSuaFrgm(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chi_tiet_s_p_sua_frgm, container, false);
        // ánh xạ
        TextView txtCTSPSuaTenSp = view.findViewById(R.id.txtCTSPSuaTenSp);
        TextView txtCTSPSuaGiaSP = view.findViewById(R.id.txtCTSPSuaGiaSP);
        TextView txtCTSPSuaLoaiSP = view.findViewById(R.id.txtCTSPSuaLoaiSP);
        TextView txtCTSPSuaMoTaSP = view.findViewById(R.id.txtCTSPSuaMoTaSP);
        ImageView btnCTSPSuaSua = view.findViewById(R.id.btnCTSPSuaSua);
        ImageView btnCTSPSuaXoa = view.findViewById(R.id.btnCTSPSuaXoa);
        ImageView img1 = view.findViewById(R.id.img_SP);
        ImageView btnBackSuaSP = view.findViewById(R.id.btnBackSuaSP);
        dao = new DAOSanPham(getContext());
        // set text cho view
        txtCTSPSuaTenSp.setText("Tên sản phẩm: "+sanPham.getTenSanPham());
        txtCTSPSuaGiaSP.setText(sanPham.getPrice() + "");
        int maLSP = sanPham.getMaLoai();
        String tenLSP = "";
        ArrayList<TheLoai> listTheLoai = dao.getDSLSP();
        for (int i = 0; i < listTheLoai.size(); i++) {
            TheLoai theLoai = listTheLoai.get(i);
            if (theLoai.getMaLoai() == maLSP){
                tenLSP = theLoai.getTenLoai();
            }
        }
        txtCTSPSuaLoaiSP.setText("Loại sản phẩm: " + tenLSP);
        txtCTSPSuaMoTaSP.setText(sanPham.getMota());
        byte[] productsImage = sanPham.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(productsImage, 0, productsImage.length);
        img1.setImageBitmap(bitmap);
        //
        String outGia = String.format("%,.0f", sanPham.getPrice());
        txtCTSPSuaGiaSP.setText("Giá: "+outGia + " đ");
        // sự kiện onclick

        btnBackSuaSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new ProductFrgm());
            }
        });
        // sửa sản phẩm
        btnCTSPSuaSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new SuaSPFrgm(sanPham));
            }
        });
        // xoá sửa sản phẩm
        btnCTSPSuaXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_confirm);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView dialog_confirm_content = dialog.findViewById(R.id.dialog_confirm_content);
                EditText btnDialogHuy = dialog.findViewById(R.id.btnDialogHuy);
                EditText btnDialogXN = dialog.findViewById(R.id.btnDialogXN);

                dialog_confirm_content.setText("Bạn chắc chắn muốn xóa sản phẩm đã chọn!");

                btnDialogHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Hủy", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                btnDialogXN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dao.deleteData(sanPham.getId());
                        Toast.makeText(getContext(), "Đã xóa Sản phẩm", Toast.LENGTH_SHORT).show();
                        loadFragment(new ProductFrgm());
                        dialog.dismiss();
                    }
                });
                dialog.show();
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

    @Override
    public void onClick(View v) {

    }
}