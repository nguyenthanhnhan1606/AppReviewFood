package com.example.baitaplon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.baitaplon.database.QuanAn;
import com.example.baitaplon.database.User;
import com.example.baitaplon.database.UserDataSource;
import com.example.baitaplon.databinding.ActivityIndexBinding;

import java.util.ArrayList;

public class AdminIndex extends AppCompatActivity {
    UserDataSource userDAO;
    UserAdapter userAdapter;
    ActivityIndexBinding binding ;
    Button btnAdd, loginBtn, btnGroup;
    ListView lvUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_index);
        userDAO = new UserDataSource(this);
        userDAO.open();

        userAdapter = new UserAdapter(userDAO.selectAll(), userDAO);


        // ánh xạ các view
        btnGroup = findViewById(R.id.btnGroup);
        btnAdd = findViewById(R.id.btnAdd);
        lvUser = findViewById(R.id.lvGroup);

        lvUser.setAdapter(userAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userAdapter.showDialogAdd(AdminIndex.this);
            }
        });
    }

}