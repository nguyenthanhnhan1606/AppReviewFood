package com.example.baitaplon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.example.baitaplon.database.CommentDataSource;
import com.google.android.material.tabs.TabLayout;
import com.example.baitaplon.databinding.ActivityDetailsBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Details extends AppCompatActivity {
    private int id_quan_detail;
    private int id_user_detail;
    public static final String INTENT_ID_QUAN="id_quanan";
    public static final String INTENT_ID_USER="id_user";
    public static final String INTENT_DANHGIA="danhgia";


    private ViewPager2 viewPager2;
    TabLayout tblay;
    ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tblay = findViewById(R.id.tabLayout);

        viewPager2 = findViewById(R.id.viewpager2);

        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this);
        viewPager2.setAdapter(viewPager2Adapter);

        new TabLayoutMediator(tblay, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Bình luận");
                        break;
                    case 1:
                        tab.setText("Đánh giá");
                        break;
                }
            }
        }).attach();


        Intent intent = this.getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("tenquan");
            String diadiem = intent.getStringExtra("diadiem");
            int hinhanh = intent.getIntExtra("hinhanh", R.drawable.phencoffee);


            id_quan_detail = intent.getIntExtra(INTENT_ID_QUAN, -1);
            id_user_detail = intent.getIntExtra(INTENT_ID_USER, -1);

            CommentDataSource commentDataSource = new CommentDataSource(this);
            commentDataSource.open();
            int quantityUser = commentDataSource.getCountUserCommentForQuan(id_quan_detail);
            double totalRating = commentDataSource.getTotalMaxDanhGia(id_quan_detail);
            float avg = (float) (totalRating/quantityUser);



            binding.txtTenQuan.setText(name);
            binding.txtDes.setText(diadiem);
            binding.img.setImageResource(hinhanh);
            binding.ratingBar3.setRating(avg);
        }

    }

    public int getId_user_detail() {
        return id_user_detail;
    }
    public int getId_quan_detail() {
        return id_quan_detail;
    }



}