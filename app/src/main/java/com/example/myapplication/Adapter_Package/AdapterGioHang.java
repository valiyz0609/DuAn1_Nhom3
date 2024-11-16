package com.example.myapplication.Adapter_Package;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DAOModel.DAOGioHang;
import com.example.myapplication.FragmentManager.StoreFrgm;
import com.example.myapplication.Model.GioHang;
import com.example.myapplication.R;

import java.util.ArrayList;

public class AdapterGioHang extends RecyclerView.Adapter<AdapterGioHang.ViewHolder> {

    private ArrayList<GioHang> list;
    private Context context;
    DAOGioHang daoGioHang;
    StoreFrgm storeFrgm;

    public AdapterGioHang(Context context, ArrayList<GioHang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_gio_hang, parent, false);
        daoGioHang = new DAOGioHang(view.getContext());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GioHang gioHang = list.get(position);

//        Set ảnh cho Items
        byte[] imgSP = gioHang.getImgSP();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgSP, 0, imgSP.length);
        holder.imgGHAnhSP.setImageBitmap(bitmap);
        holder.txtGHTenSP.setText(gioHang.getTenSP() + " - " + gioHang.getSize());
        double donGia = gioHang.getDonGia();
        String outDonGia = String.format("%,.0f", donGia);
        holder.txtGHGia.setText(outDonGia + "k");
        int soLuong = gioHang.getSoLuong();
        holder.edtGHSoLuong.setText(soLuong + "");

//        Sự kiện giảm số lượng sản phẩm
        holder.btnHGTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuong = gioHang.getSoLuong();
                if (soLuong > 1){
//                    Số lượng SP hiện tại > 0 => Giảm
                    soLuong--;
                    gioHang.setSoLuong(soLuong);
                    boolean kiemtra = daoGioHang.updateGioHang(gioHang);
                    if (kiemtra){
                        list.clear();
                        list = daoGioHang.getGioHang();
                        notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(context, "Update SL Fail!", Toast.LENGTH_SHORT).show();
                    }
                    holder.edtGHSoLuong.setText(soLuong + "");
                }
                else {
//                    Số lượng SP = 0 => Xóa SP khỏi giỏ hàng
                    boolean kiemtra = daoGioHang.deleteGiohang(gioHang);
                    if (kiemtra){
                        Toast.makeText(context, "Đã xóa Sản phẩm khỏi giỏ hàng!", Toast.LENGTH_SHORT).show();
                        list.clear();
                        list = daoGioHang.getGioHang();
                        notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(context, "Delete Fail!", Toast.LENGTH_SHORT).show();
                    }
                }
                notifyDataSetChanged();
                double tongTien = daoGioHang.tongTienGiohang();
                String outTongTien = String.format("%,.0f", tongTien);
                StoreFrgm.txtGHTongTien.setText(outTongTien + " VNĐ");
            }
        });

//        Sự kiện tăng số lượng sản phẩm
        holder.btnGHCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuong = gioHang.getSoLuong();
                soLuong++;
                gioHang.setSoLuong(soLuong);
                boolean kiemtra = daoGioHang.updateGioHang(gioHang);
                if (kiemtra){
                    list.clear();
                    list = daoGioHang.getGioHang();
                    notifyDataSetChanged();
                }
                else {
                    Toast.makeText(context, "Update SL Fail!", Toast.LENGTH_SHORT).show();
                }
                holder.edtGHSoLuong.setText(soLuong + "");
                notifyDataSetChanged();
                double tongTien = daoGioHang.tongTienGiohang();
                String outTongTien = String.format("%,.0f", tongTien);
                StoreFrgm.txtGHTongTien.setText(outTongTien + " VNĐ");
            }
        });
        holder.edtGHSoLuong.getText().toString();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGHAnhSP, btnHGTru, btnGHCong;
        TextView txtGHTenSP, txtGHGia, edtGHSoLuong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGHAnhSP = itemView.findViewById(R.id.imgGHAnhSP);
            txtGHTenSP = itemView.findViewById(R.id.txtGHTenSP);
            txtGHGia = itemView.findViewById(R.id.txtGHGia);
            edtGHSoLuong = itemView.findViewById(R.id.edtGHSoLuong);
            btnHGTru = itemView.findViewById(R.id.btnHGTru);
            btnGHCong = itemView.findViewById(R.id.btnGHCong);
        }
    }
}
