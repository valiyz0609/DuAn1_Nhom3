package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.FragmentManager.HomeFrgm;
import com.example.myapplication.FragmentManager.ProductFrgm;
import com.example.myapplication.FragmentManager.StoreFrgm;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.pageTrangChu);
        loadFragment(new HomeFrgm());

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment;
        if (item.getItemId() == R.id.pageTrangChu) {
            fragment = new HomeFrgm();
            loadFragment(fragment);
            return true;
        } else if (item.getItemId() == R.id.pageSanPham) {
            fragment = new ProductFrgm();
            loadFragment(fragment);
            return true;
        } else if (item.getItemId() == R.id.pageBanHang) {
            fragment = new StoreFrgm();
            loadFragment(fragment);
            return true;
        } else {
            return false;
        }

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    
}