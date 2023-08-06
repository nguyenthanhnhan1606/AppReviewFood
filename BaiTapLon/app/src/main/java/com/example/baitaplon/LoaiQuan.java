package com.example.baitaplon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.baitaplon.database.LoaiQuanDataSource;
import com.example.baitaplon.database.UserDataSource;
import com.example.baitaplon.databinding.ActivityIndexBinding;

public class LoaiQuan extends AppCompatActivity{
    LoaiQuanDataSource loaiQuanDAO;
    LoaiQuanAdapter loaiQuanAdapter;
    ActivityIndexBinding binding ;
    Button btnAdd, loginBtn, btnGroup;
    ListView lvLoaiQuan;
    Toolbar toolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaiquan);


        toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loaiQuanDAO = new LoaiQuanDataSource(this);
        loaiQuanDAO.open();

        loaiQuanAdapter = new LoaiQuanAdapter(loaiQuanDAO.getAllLoai(), loaiQuanDAO);


        // ánh xạ các view
        btnAdd = findViewById(R.id.btnAdd);
        lvLoaiQuan = findViewById(R.id.lvGroup);

        lvLoaiQuan.setAdapter(loaiQuanAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loaiQuanAdapter.showDialogAdd(LoaiQuan.this);
            }
        });
    }

}