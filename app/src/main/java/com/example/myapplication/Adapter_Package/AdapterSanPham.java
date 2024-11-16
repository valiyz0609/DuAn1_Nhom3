package com.example.myapplication.Adapter_Package;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DAOModel.DAOGioHang;
import com.example.myapplication.DAOModel.DAOUser;
import com.example.myapplication.FragmentManager.ChiTietSPFrgm;
import com.example.myapplication.FragmentManager.ChiTietSPSuaFrgm;
import com.example.myapplication.Model.GioHang;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class AdapterSanPham extends RecyclerView.Adapter<AdapterSanPham.UserViewHolder> {

    private Context context;
    private ArrayList<SanPham> list;
    DAOGioHang daoGioHang;
    DAOUser daoUser;
    BottomNavigationView bottomNavigationView;

    public AdapterSanPham(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_sanpham, parent, false);
        daoGioHang = new DAOGioHang(view.getContext());
        daoUser = new DAOUser(view.getContext());
        bottomNavigationView = view.findViewById(R.id.navigation);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
//        Set Data cho List Item
        SanPham sanPham = list.get(position);
        holder.TenSanPham.setText(sanPham.getTenSanPham());
        holder.GiaTien.setText(String.valueOf(sanPham.getPrice()));
        byte[] productsImage = sanPham.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(productsImage, 0, productsImage.length);
        holder.img_SanPham.setImageBitmap(bitmap);
        //
        String outTongTien = String.format("%,.0f", sanPham.getPrice());
        holder.GiaTien.setText(outTongTien + "k");

//        Set sự kiện Onclick cho các Button
//        Buton xem sản phẩm
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                bottomNavigationView.setSelectedItemId(R.id.pageBanHang);
                loadFragment(new ChiTietSPFrgm(sanPham));
            }
        });
//        Button thêm sản phẩm vào giỏ hàng (Icon ADD)
        holder.add_sanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Khởi tạo Model
                GioHang gioHang = new GioHang(1, sanPham.getId(), 1, "N", sanPham.getPrice());
//                Check Valid SP (SanPham.ID, Size)
                ArrayList<GioHang> outList = daoGioHang.checkValidGioHang(gioHang);
//                Toast.makeText(context, outList.size() + "", Toast.LENGTH_SHORT).show();
                if (outList.size() != 0) {
//                - Có: Update số lượng
                    GioHang gioHang1 = outList.get(0);
//                    Toast.makeText(context, "OldSL: " + gioHang1.getSoLuong(), Toast.LENGTH_SHORT).show();
                    int newSL = gioHang1.getSoLuong() + 1;
//                    Toast.makeText(context, "NewSL" + newSL, Toast.LENGTH_SHORT).show();
                    gioHang.setSoLuong(newSL);
                    boolean kiemtra = daoGioHang.updateGioHang(gioHang);
                    if (kiemtra) {
                        notifyDataSetChanged();
//                        Toast.makeText(context, "Done!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Update SL Fail!", Toast.LENGTH_SHORT).show();
                    }
                } else {
//                - Không: Thêm sản phẩm
                    boolean check = daoGioHang.addGiohang(gioHang);
                    if (check) {
                        Toast.makeText(context, "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Fail!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        SharedPreferences pref = context.getSharedPreferences("USER_FILE", context.MODE_PRIVATE);
        int maUser = pref.getInt("MA", 0);
        User user = daoUser.getUser(maUser);
        int quyenUser = user.getMaChucVu();
        if (quyenUser == 2) {
            holder.info_sanpham.setVisibility(View.GONE);
        }

        holder.info_sanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ChiTietSPSuaFrgm(sanPham));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView add_sanpham,  img_SanPham;
        private TextView TenSanPham ,info_sanpham;
        private TextView GiaTien;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview_sanPham);
            info_sanpham = itemView.findViewById(R.id.info_sanpham);
            add_sanpham = itemView.findViewById(R.id.add_sanpham);
            img_SanPham = itemView.findViewById(R.id.img_sanpham);
            TenSanPham = itemView.findViewById(R.id.ten_sanpham);
            GiaTien = itemView.findViewById(R.id.gia_sanpham);
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
