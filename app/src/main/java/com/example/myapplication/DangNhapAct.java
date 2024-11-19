package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.myapplication.DAOModel.DAOUser;
import com.example.myapplication.Model.User;

import java.util.ArrayList;

public class DangNhapAct extends AppCompatActivity {
    private DAOUser daoUser;
    EditText edtUser, edtPassword;
    ImageView  img_hidePassword;
    CheckBox checkBox;
    AppCompatButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        edtUser = findViewById(R.id.edtTenDangNhap);
        edtPassword = findViewById(R.id.edtMatKhau);

        checkBox = findViewById(R.id.chkNhoMK);
        btnLogin = findViewById(R.id.btnDangNhap);
        img_hidePassword = findViewById(R.id.img_hidePassword);
        daoUser = new DAOUser(this);
        edtPassword.getInputType();
        //sự kiện hide pass
        img_hidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    img_hidePassword.setImageResource(R.drawable.ic_hide_on);
                } else {
                    edtPassword.setInputType(129);
                    img_hidePassword.setImageResource(R.drawable.ic_visibility_off);
                }
            }
        });

//        Get Data từ SharedPreferences
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = pref.getString("USERNAME", "");
        String pass = pref.getString("PASSWORD", "");
        boolean rem = pref.getBoolean("REMEMBER", false);

        edtUser.setText(user);
        edtPassword.setText(pass);
        checkBox.setChecked(rem);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUser = edtUser.getText().toString();
                String strPass = edtPassword.getText().toString();
                boolean checkLogin = true;

//                Kiểm tra tên đăng nhập
                if (strUser.isEmpty()) {
                    edtUser.setHintTextColor(Color.RED);
                    Toast.makeText(DangNhapAct.this, "Nhập tên đăng nhập!", Toast.LENGTH_SHORT).show();
                    checkLogin = false;
                }
//                Kiểm tra mật khẩu
                if (strPass.isEmpty()) {
                    edtPassword.setHintTextColor(Color.RED);
                    Toast.makeText(DangNhapAct.this, "Nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                    checkLogin = false;
                }

//                Kiểm tra User tồn tại
                if (checkLogin) {
                    ArrayList<User> list = daoUser.checkLogin(strUser, strPass);
                    if (list.size() > 0) {
                        Toast.makeText(DangNhapAct.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("user", strUser);
                        startActivity(intent);
                        User user = list.get(0);
                        int maUser = user.getID_User();
                        remmemberUser(maUser, strUser, strPass, checkBox.isChecked());
                        closeKeyboard();
                    } else {
                        Toast.makeText(DangNhapAct.this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void remmemberUser(int maUser, String u, String p, boolean status) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (!status) {
            editor.clear();
        } else {
            editor.putInt("MA", maUser);
            editor.putString("USERNAME", u);
            editor.putString("PASSWORD", p);
            editor.putBoolean("REMEMBER", status);
        }
        editor.commit();

    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}