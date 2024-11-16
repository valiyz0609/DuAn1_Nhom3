package com.example.myapplication.Adapter_Package;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DAOModel.DAOSanPham;
import com.example.myapplication.Model.TheLoai;
import com.example.myapplication.R;

import java.util.ArrayList;

public class AdapterLoaiSP extends RecyclerView.Adapter<AdapterLoaiSP.ViewHolder> {

    private Context context;
    private ArrayList<TheLoai> list;
    private DAOSanPham daoSanPham;


    public AdapterLoaiSP(Context context, ArrayList<TheLoai> list, DAOSanPham daoSanPham) {
        this.context = context;
        this.list = list;
        this.daoSanPham = daoSanPham;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.card_view_loaisp, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTenLoaisp.setText("Tên loại: "+list.get(position).getTenLoai());

        holder.btnsuaLoaisp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialogEdit(list.get(holder.getAdapterPosition()));
            }
        });

        holder.btndeleteLoaisp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDelLSP(list.get(holder.getAdapterPosition()).getTenLoai(), list.get(holder.getAdapterPosition()).getMaLoai());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTenLoaisp;
        ImageView btnsuaLoaisp, btndeleteLoaisp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenLoaisp = itemView.findViewById(R.id.tenloaisp);
            btnsuaLoaisp = itemView.findViewById(R.id.btneditLoaisp);
            btndeleteLoaisp = itemView.findViewById(R.id.btndeleteLoaisp);

        }
    }

    private void showdialogEdit(TheLoai theLoai){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_loai_sp, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText edtSuaTenLoaisp = view.findViewById(R.id.edtsuaSPLoai);
        EditText btnSuaLoaisp = view.findViewById(R.id.btnsuaLSPXN);
        EditText btnHuyLoaisp = view.findViewById(R.id.btnsuaLSPHuy);

        edtSuaTenLoaisp.setText(theLoai.getTenLoai());

        btnHuyLoaisp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btnSuaLoaisp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int malsp = theLoai.getMaLoai();
                String tenlsp = edtSuaTenLoaisp.getText().toString();

                boolean checkAddLSP = true;

//                Kiểm tra Edt Rỗng
                if (tenlsp.isEmpty()){
                    edtSuaTenLoaisp.setError("Vui lòng nhập!");
                    edtSuaTenLoaisp.setHintTextColor(Color.RED);
                    checkAddLSP = false;
                }

//                Kiểm tra Tên Loại trùng
                ArrayList<TheLoai> listLoaiSP = daoSanPham.getDSLSP();
                for (int i = 0; i < listLoaiSP.size(); i++) {
                    TheLoai mTheLoai = listLoaiSP.get(i);
                    String mTenLoai = mTheLoai.getTenLoai();
                    if (mTenLoai.equals(tenlsp)){
                        checkAddLSP = false;
                        Toast.makeText(context, "Loại sản phẩm đã tồn tại!", Toast.LENGTH_SHORT).show();
                        edtSuaTenLoaisp.setText(null);
                        edtSuaTenLoaisp.setHintTextColor(Color.BLACK);
                    }
                }

//                Kiểm tra điểu kiện
                if (checkAddLSP) {
                    TheLoai theLoai = new TheLoai(malsp, tenlsp);
                    boolean checkAdd = daoSanPham.editLSP(theLoai);
                    if (checkAdd){
                        Toast.makeText(context, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                        edtSuaTenLoaisp.setText(null);
                        edtSuaTenLoaisp.setHintTextColor(Color.BLACK);
                        loadData();
                        alertDialog.dismiss();
                    }
                    else {
                        Toast.makeText(context, "Fail!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void showDelLSP(String tenLoaisp, int maLoai){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa loại \"" + tenLoaisp +"\" không?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean check = daoSanPham.deleteLSP(maLoai);
                if (check){
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                }
                else {

                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setNegativeButton("Huỷ",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadData(){
        list.clear();
        list = daoSanPham.getDSLSP();
        notifyDataSetChanged();
    }
}
