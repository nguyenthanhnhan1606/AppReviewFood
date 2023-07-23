package com.example.baitaplon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baitaplon.database.SQLiteHelper;
import com.example.baitaplon.database.UserDataSource;

public class Register extends AppCompatActivity {
    EditText hoTen, userName, passWord, rePass, eMail, SDT;
    Button reBtn;


    UserDataSource userDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        hoTen = (EditText) findViewById(R.id.textTen);
        userName = (EditText) findViewById(R.id.reUser);
        passWord = (EditText) findViewById(R.id.textPass);
        rePass = (EditText) findViewById(R.id.rePass);
        eMail = (EditText) findViewById(R.id.reEmail);
        SDT = (EditText) findViewById(R.id.rePhone);
        reBtn = (Button) findViewById(R.id.summitbtn);


        userDataSource = new UserDataSource(this);
        userDataSource.open();

        reBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten = hoTen.getText().toString();
                String username = userName.getText().toString();
                String password = passWord.getText().toString();
                String repass = rePass.getText().toString();
                String email = eMail.getText().toString();
                String sdt = SDT.getText().toString();
                String avatar = " ";
                String user_role = "user";

                if (username.equals(" ") || password.equals(" ") || repass.equals(" ")){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    if (password.equals(repass)){
                        boolean checkUser = userDataSource.checkUserName(username);
                        if (checkUser == false){
                            boolean insert = userDataSource.insertUser(hoten,username,password,email,sdt,avatar,true,user_role);
                            if (insert == true){
                                Toast.makeText(getApplicationContext(),"Tạo tài khoàn thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(), "Tạo tài khoản thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Tên người dùng đã được sử dụng", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Mật khẩu không trùng", Toast.LENGTH_SHORT).show();
                    }
                }
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