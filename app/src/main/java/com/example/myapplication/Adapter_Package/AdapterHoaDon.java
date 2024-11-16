package com.example.myapplication.Adapter_Package;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DAOModel.DAOGioHang;
import com.example.myapplication.Model.GioHang;
import com.example.myapplication.R;

import java.util.ArrayList;

public class AdapterHoaDon extends RecyclerView.Adapter<AdapterHoaDon.ViewHolder>{

    private Context context;
    private ArrayList<GioHang> list;
    DAOGioHang daoGioHang;

    public AdapterHoaDon(Context context, ArrayList<GioHang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_hoadon, parent, false);
        daoGioHang = new DAOGioHang(view.getContext());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GioHang gioHang = list.get(position);
        holder.txtHDTenSP.setText(gioHang.getTenSP() + "(" + gioHang.getSize() + ")");
        double donGia = gioHang.getDonGia();
        String outDonGia = String.format("%,.0f", donGia);
        holder.txtHDDonGia.setText(outDonGia);
        holder.txtHDSL.setText(gioHang.getSoLuong() + "");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtHDTenSP, txtHDDonGia, txtHDSL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHDTenSP = itemView.findViewById(R.id.txtHDTenSP);
            txtHDDonGia = itemView.findViewById(R.id.txtHDDonGia);
            txtHDSL = itemView.findViewById(R.id.txtHDSL);
        }
    }
}
