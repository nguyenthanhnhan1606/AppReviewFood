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

import com.example.baitaplon.database.UserDataSource;
import com.example.baitaplon.databinding.ActivityIndexBinding;

public class User extends AppCompatActivity{
    UserDataSource userDAO;
    UserAdapter userAdapter;
    ActivityIndexBinding binding ;
    Button btnAdd, loginBtn, btnGroup;
    ListView lvUser;
    Toolbar toolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uesr);

        toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userDAO = new UserDataSource(this);
        userDAO.open();

        userAdapter = new UserAdapter(userDAO.selectAll(), userDAO);


        // ánh xạ các view
        btnAdd = findViewById(R.id.btnAdd);
        lvUser = findViewById(R.id.lvGroup);

        lvUser.setAdapter(userAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userAdapter.showDialogAdd(User.this);
            }
        });
    }

}