package com.example.baitaplon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baitaplon.db.UserDataSource;

public class Register extends AppCompatActivity {

    EditText ho, ten, user, pass, email, sdt, confirm;
    Button summit;

    UserDataSource db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        ten = (EditText) findViewById(R.id.textTen);
        user = (EditText) findViewById(R.id.reUser);
        pass = (EditText) findViewById(R.id.textPass);
        confirm = (EditText) findViewById(R.id.rePass);
        email = (EditText) findViewById(R.id.reEmail);
        sdt = (EditText) findViewById(R.id.rePhone);

        summit = (Button) findViewById(R.id.summitbtn);

        db = new UserDataSource(this);
        db.open();


        summit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTen = ten.getText().toString();
                String userName = user.getText().toString();
                String passWord = pass.getText().toString();
                String conf = confirm.getText().toString();
                String mail = email.getText().toString();
                String phone = sdt.getText().toString();
                String avatar = " ";
                int user_role = 0;
                boolean active = true;

                if (userName.equals("") || passWord.equals("") || conf.equals("")){
                    Toast.makeText(Register.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    if (passWord.equals(conf)){
                        Boolean checkUser = db.checkUser(userName);
                        if (checkUser == false){
                            Boolean check = db.insertUser(hoTen, userName, passWord, mail, phone,avatar,user_role,active);
                            if (check == true) {
                                Toast.makeText(Register.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                            }else
                                Toast.makeText(Register.this, "Tài khoản không tạo được", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Register.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(Register.this, "Mật khẩu không trùng", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }

    @Override
    protected void onResume(){
        db.open();
        super.onResume();
    }

    @Override
    protected  void onPause(){
        db.close();
        super.onPause();
    }

}