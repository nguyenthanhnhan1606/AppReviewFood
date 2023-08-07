package com.example.baitaplon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.baitaplon.database.LoaiQuanDataSource;
import com.example.baitaplon.database.QuanAnDataSource;

public class QuanAn extends AppCompatActivity {
    QuanAnDataSource quanDAO;
    QuanAdapter quanAdapter;

    Button btnGroup;
    Button btnAdd;
    ListView lvQuan;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan);
        toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        quanDAO = new QuanAnDataSource(this);
        quanDAO.open();

        quanAdapter = new QuanAdapter(quanDAO.getAllQuan(), quanDAO);


        // ánh xạ các view
        btnGroup = findViewById(R.id.btnGroup);
        btnAdd = findViewById(R.id.btnAdd);
        lvQuan = findViewById(R.id.lvGroup);

        btnGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), LoaiQuan.class));
            }
        });

        lvQuan.setAdapter(quanAdapter);

//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                quanAdapter.showDialogAdd(QuanAn.this);
//            }
//        });
    }
}
