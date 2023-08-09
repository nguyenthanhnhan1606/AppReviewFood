package com.example.baitaplon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.baitaplon.database.UserDataSource;
import com.example.baitaplon.databinding.ActivityIndexBinding;

public class AdminIndex extends AppCompatActivity {
    UserDataSource userDAO;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_index);

        toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);

        userDAO = new UserDataSource(this);
        userDAO.open();

        Button btnUser = findViewById(R.id.btnUser);
        Button btnLoaiQuan = findViewById(R.id.btnLoaiQuan);
        Button btnQuan = findViewById(R.id.btnQuan);
        Button btnComment = findViewById(R.id.btnComment);
        Button btnThongKe = findViewById(R.id.btnThongKe);

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), User.class);
                startActivity(intent);
            }
        });

        btnLoaiQuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoaiQuan.class);
                startActivity(intent);
            }
        });

        btnQuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuanAn.class);
                startActivity(intent);
            }
        });
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Comment.class);
                startActivity(intent);
            }
        });

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ThongKe.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
            Toast.makeText(AdminIndex.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}