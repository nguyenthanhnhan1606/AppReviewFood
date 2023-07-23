package com.example.baitaplon;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baitaplon.database.SQLiteHelper;
import com.example.baitaplon.database.UserDataSource;

public class Login extends AppCompatActivity {
    EditText userName, passWord;
    Button loginBtn, regisBtn;
    SQLiteHelper db;
    UserDataSource userDataSource;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (EditText) findViewById(R.id.textUser);
        passWord = (EditText) findViewById(R.id.textPass);
        loginBtn = (Button) findViewById(R.id.loginbtn);
        regisBtn = (Button) findViewById(R.id.regisBtn);

        userDataSource = new UserDataSource(this);
        userDataSource.open();
//
//        db = new SQLiteHelper(this);
//        db.close();
//        db.delAll(getApplicationContext());
//        recreate();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userName.getText().toString();
                String password = passWord.getText().toString();

                if (username.equals("") || password.equals("")){
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    boolean checkLogin = userDataSource.checkLogin(username, password);
                    if (checkLogin == true){
                        Toast.makeText(getApplicationContext(),"Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Index.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        regisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onResume() {
        userDataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        userDataSource.close();
        super.onPause();
    }
}