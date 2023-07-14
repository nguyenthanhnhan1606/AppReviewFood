package com.example.baitaplon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.baitaplon.db.SQLiteHelper;
import com.example.baitaplon.db.User;
import com.example.baitaplon.db.UserDataSource;

public class Login extends AppCompatActivity {

    private Button btnLogin, regisBtn;
    private EditText textUser;
    private EditText textPass;

    private UserDataSource db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        helper = new SQLiteHelper(this);
//        helper.close();
//        helper.delAll(getApplicationContext());
//        recreate();
        db = new UserDataSource(this);
        db.open();


        btnLogin = (Button) findViewById(R.id.loginbtn);
        textUser = (EditText) findViewById(R.id.textUser);
        textPass = (EditText) findViewById(R.id.textPass);
        regisBtn = (Button) findViewById(R.id.regisBtn);

        View.OnClickListener login = view -> {
            String username = textUser.getText().toString();
            String password = textPass.getText().toString();

            if (username.equals("") || password.equals("")) {
                Toast.makeText(Login.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }else{
                Boolean checkLogin = db.checkLogin(username, password);
                if (checkLogin == true){
                    Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Index.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Login.this, "Người dùng không khả dụng", Toast.LENGTH_SHORT).show();
                }


            }
        };
        btnLogin.setOnClickListener(login);

        View.OnClickListener register = view -> {
            if (view.getId() == regisBtn.getId()){
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        };
        regisBtn.setOnClickListener(register);
    }
}