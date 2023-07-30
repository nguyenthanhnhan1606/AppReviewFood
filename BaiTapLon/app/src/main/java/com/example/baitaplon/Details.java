package com.example.baitaplon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toolbar;

import com.example.baitaplon.databinding.ActivityDetailsBinding;
import com.google.android.material.tabs.TabLayout;

public class Details extends AppCompatActivity {

    Button button;
    RatingBar ratingBar;
    androidx.appcompat.widget.Toolbar bar;
    ActivityDetailsBinding binding;

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPageDetailAdapter viewPageDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toobardetail);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        button = (Button) findViewById(R.id.submitRate);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        tabLayout = (TabLayout) findViewById(R.id.tab);
        viewPager2 = (ViewPager2) findViewById(R.id.viewpager2);
        viewPageDetailAdapter = new ViewPageDetailAdapter(this);
        viewPager2.setAdapter(viewPageDetailAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menudetail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //..
        return super.onOptionsItemSelected(item);
    }
}