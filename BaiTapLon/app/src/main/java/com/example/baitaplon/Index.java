package com.example.baitaplon;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baitaplon.database.CommentDataSource;
import com.example.baitaplon.database.LoaiQuan;
import com.example.baitaplon.database.LoaiQuanDataSource;
import com.example.baitaplon.database.QuanAn;
import com.example.baitaplon.database.QuanAnDataSource;
import com.example.baitaplon.database.SQLiteHelper;
import com.example.baitaplon.databinding.ActivityIndexBinding;
import com.google.android.material.tabs.TabLayout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.List;




public class Index extends AppCompatActivity {

    ListView listView;
    TextView textView;
    Toolbar toolbar;
    QuanAnAdapter quanAnAdapter;

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPageAdapter viewPageAdapter;
    ActivityIndexBinding binding;
    private  LoaiQuanDataSource lq;
    private QuanAnDataSource qa;
    private LoaiQuan loaiQuan;
    private QuanAn quanAn;

    public static final String INTENT_ID_QUAN="id_quanan";
    public static final String INTENT_ID_USER="id_user";

    public static final String INTENT_DANHGIA="danhgia";
    private  int id_user_index;








    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        binding = ActivityIndexBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listView = (ListView) findViewById(R.id.list);
        textView = (TextView) findViewById(R.id.hightlight);
        toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        viewPager2 = (ViewPager2) findViewById(R.id.viewpager);
        viewPageAdapter = new ViewPageAdapter(this);
        viewPager2.setAdapter(viewPageAdapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
                listView.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
                listView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
                listView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });



        lq = new LoaiQuanDataSource(this);
        lq.open();
//        loaiQuan = this.lq.insertLoaiQuan("Quán ăn");
//        loaiQuan = this.lq.insertLoaiQuan("Quán nước");
        ArrayList<LoaiQuan> loaiQuans = new ArrayList<LoaiQuan>();
        loaiQuans.add(loaiQuan);
        loaiQuans = this.lq.getAllLoai();





        qa = new QuanAnDataSource(this);
        qa.open();

        //Lấy id User
        Intent intent = getIntent();
        id_user_index = intent.getIntExtra(INTENT_ID_USER,-1);

//        quanAn = this.qa.insertQuanAn("Phen's Coffee", "142 Nguyễn Văn Công, P.3, Quận Gò Vấp, TP.HCM", 7.0, R.d, true, loaiQuans.get(1).getId());
//        quanAn = this.qa.insertQuanAn("Hey Pelo", "60 Trần Khắc Chân, P.Tân Định, Quận 1, TP.HCM", 8.0, R.drawable.heypelo, true, loaiQuans.get(0).getId());
//        quanAn = this.qa.insertQuanAn("Cơm Tấm Sà Bì Chưởng", "179 Trần Bình Trọng, P.3, Quận 5, TP.HCM", 8.0, R.drawable.sabichuong, true, loaiQuans.get(0).getId());
//        quanAn = this.qa.insertQuanAn("Tiệm trà tháng tư", "1 Nhiêu Tứ, P.7, Quận Phú Nhuận, TPHCM", 6.0, R.drawable.tiemtrathangtu, true, loaiQuans.get(1).getId());
//        quanAn = this.qa.insertQuanAn("Bánh mì Pew Pew", "66 Út Tịch, P.4, Quận Tân Bình, TP.HCM", 7.0, R.drawable.banhmipewpew, true, loaiQuans.get(0).getId());
//        quanAn = this.qa.insertQuanAn("Cafe Mưa rào", "115/174B Lê Văn Sỹ, Phường 13, Phú Nhuận, TP. HCM", 9.0, R.drawable.cafemuarao, true, loaiQuans.get(1).getId());
        ArrayList<QuanAn> quanAns = new ArrayList<QuanAn>();
//        quanAns.add(quanAn);
        quanAns = this.qa.getAllQuan();

        quanAnAdapter = new QuanAnAdapter(this, R.layout.list_row, quanAns);
        binding.list.setAdapter(quanAnAdapter);
        binding.list.setClickable(true);
        ArrayList<QuanAn> finalQuanAns = quanAns;
        CommentDataSource commentDataSource = new CommentDataSource(this);
        binding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Index.this, Details.class);
                intent.putExtra("tenquan", finalQuanAns.get(position).getTenquan());
                intent.putExtra("diadiem", finalQuanAns.get(position).getDiadiem());
                intent.putExtra("hinhanh", finalQuanAns.get(position).getHinhanh());



                intent.putExtra(INTENT_ID_QUAN,finalQuanAns.get(position).getId());
                intent.putExtra(INTENT_ID_USER,id_user_index);
                startActivity(intent);

                commentDataSource.open();
                if(!commentDataSource.checkHis(id_user_index, finalQuanAns.get(position).getId())){
                    commentDataSource.insertComment("",0, String.valueOf(LocalDate.now()),id_user_index,finalQuanAns.get(position).getId());
                }
                commentDataSource.close();

            }
        });
////        ArrayAdapter<QuanAn> aa = new ArrayAdapter<QuanAn>(this, android.R.layout.simple_list_item_1, quanAns);
//        listView.setAdapter(quanAnAdapter);
//        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Nhập từ khóa ...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
            Toast.makeText(Index.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        lq.open();
        qa.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        lq.close();
        qa.close();
        super.onPause();
    }

    public int getId_user_index() {
        return id_user_index;
    }

}