package com.example.baitaplon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.baitaplon.databinding.ActivityDetailsBinding;

public class Details extends AppCompatActivity {

    ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();
        if (intent != null){
            String name = intent.getStringExtra("tenquan");
            String diadiem = intent.getStringExtra("diadiem");
            int hinhanh = intent.getIntExtra("hinhanh", R.drawable.phencoffee);
            binding.txtTenQuan.setText(name);
            binding.txtDes.setText(diadiem);
            binding.img.setImageResource(hinhanh);

        }
    }
}