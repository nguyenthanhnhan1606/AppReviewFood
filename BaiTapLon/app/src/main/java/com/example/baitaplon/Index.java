package com.example.baitaplon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.baitaplon.database.LoaiQuan;
import com.example.baitaplon.database.LoaiQuanDataSource;
import com.example.baitaplon.database.QuanAn;
import com.example.baitaplon.database.QuanAnDataSource;
import com.example.baitaplon.database.SQLiteHelper;
import com.example.baitaplon.databinding.ActivityIndexBinding;
import com.example.baitaplon.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Index extends AppCompatActivity {
    Button btn;
    ListView listView;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPageAdapter viewPageAdapter;

    ActivityIndexBinding binding;

    private  LoaiQuanDataSource lq;
    private QuanAnDataSource qa;
    private LoaiQuan loaiQuan;
    private QuanAn quanAn;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        binding = ActivityIndexBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listView = (ListView) findViewById(R.id.list);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager2 = (ViewPager2) findViewById(R.id.viewpager);
        viewPageAdapter = new ViewPageAdapter(this);
        viewPager2.setAdapter(viewPageAdapter);
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager2.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//
//        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                tabLayout.getTabAt(position).select();
//            }
//        });


//        lq = new LoaiQuanDataSource(this);
//        lq.open();
//        loaiQuan = this.lq.insertLoaiQuan("Quán nước");
//        List<LoaiQuan> loaiQuans = new ArrayList<LoaiQuan>();
//        loaiQuans.add(loaiQuan);
//        loaiQuans = this.lq.getAllLoai();
//        ArrayAdapter<LoaiQuan> aa = new ArrayAdapter<LoaiQuan>(this, android.R.layout.simple_list_item_1, loaiQuans);
//        listView.setAdapter(aa);




        qa = new QuanAnDataSource(this);
        qa.open();

//        quanAn = this.qa.insertQuanAn("Tiệm trà tháng tư", "30 Nhất Chi Mai - Phường 13 - Quận Tân Bình - TPHCM", 7.0, R.drawable.tiemtrathangtu, true, 2);
        ArrayList<QuanAn> quanAns = new ArrayList<QuanAn>();
//        quanAns.add(quanAn);
        quanAns = this.qa.getAllQuan();

        QuanAnAdapter quanAnAdapter = new QuanAnAdapter(this, R.layout.list_row, quanAns);
        binding.list.setAdapter(quanAnAdapter);
        binding.list.setClickable(true);
        ArrayList<QuanAn> finalQuanAns = quanAns;
        binding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Index.this, Details.class);
                intent.putExtra("tenquan", finalQuanAns.get(position).getTenquan());
                intent.putExtra("diadiem", finalQuanAns.get(position).getDiadiem());
                intent.putExtra("hinhanh", finalQuanAns.get(position).getHinhanh());
                intent.putExtra("danhgia", finalQuanAns.get(position).getDanhgia());
                startActivity(intent);
            }
        });
//        ArrayAdapter<QuanAn> aa = new ArrayAdapter<QuanAn>(this, android.R.layout.simple_list_item_1, quanAns);
//        listView.setAdapter(quanAnAdapter);


    }

    @Override
    protected void onResume() {
        qa.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        qa.close();
        super.onPause();
    }
}