package com.example.myapplication.Adapter_Package;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.LuuHoaDon;
import com.example.myapplication.R;

import java.util.ArrayList;

public class AdapterThongTinHD extends RecyclerView.Adapter<AdapterThongTinHD.ViewHolder>{

    private Context context;
    private ArrayList<LuuHoaDon> listHoaDon;

    public AdapterThongTinHD(Context context, ArrayList<LuuHoaDon> listHoaDon){
        this.context = context;
        this.listHoaDon = listHoaDon;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_hoadon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LuuHoaDon luuHoaDon = listHoaDon.get(position);
        holder.txtHDTenSP.setText(luuHoaDon.getTenSP() + " (" + luuHoaDon.getSize() + ")");
        holder.txtHDSL.setText(luuHoaDon.getSoLuong() + "");
        double donGia = luuHoaDon.getDonGia();
        String outDonGia = String.format("%,.0f", donGia);
        holder.txtHDDonGia.setText(outDonGia);
    }

    @Override
    public int getItemCount() {
        return listHoaDon.size();
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
