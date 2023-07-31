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

import com.example.baitaplon.database.CommentDataSource;
import com.example.baitaplon.database.LoaiQuanDataSource;
import com.example.baitaplon.database.QuanAnDataSource;
import com.example.baitaplon.database.SQLiteHelper;
import com.example.baitaplon.database.UserDataSource;

public class Login extends AppCompatActivity {
    public static final String INTENT_ID_USER="id_user";
    EditText userName, passWord;
    Button loginBtn, regisBtn;
    SQLiteHelper db;
    UserDataSource userDataSource;

    LoaiQuanDataSource loaiQuanDataSource;
    QuanAnDataSource quanAnDataSource;
    CommentDataSource commentDataSource;



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


//        loaiQuanDataSource = new LoaiQuanDataSource(this);
//        loaiQuanDataSource.open();
//        loaiQuanDataSource.insertLoaiQuan("Quán ăn");
//        loaiQuanDataSource.insertLoaiQuan("Quán nước");
//        quanAnDataSource = new QuanAnDataSource(this);
//        quanAnDataSource.open();
//        quanAnDataSource.insertQuanAn("Phen's Coffee", "142 Nguyễn Văn Công, P.3, Quận Gò Vấp, TP.HCM", 7.0, 2131165347, true, 2);
//        quanAnDataSource.insertQuanAn("Hey Pelo ", "60 Trần Khắc Chân, P.Tân Định, Quận 1, TP.HCM", 8.0, 2131165292, true, 1);
//        quanAnDataSource.insertQuanAn("Cơm Tấm Sà Bì Chưởng", "179 Trần Bình Trọng, P.3, Quận 5, TP.HCM", 6.0, 2131165349, true, 1);
//        quanAnDataSource.insertQuanAn("Tiệm trà tháng tư", "1 Nhiêu Tứ, P.7, Quận Phú Nhuận, TPHCM", 6.0, 2131165352, true, 2);
//        quanAnDataSource.insertQuanAn("Bánh mì Pew Pew", "66 Út Tịch, P.4, Quận Tân Bình, TP.HCM", 7.0, 2131165272, true, 1);
////        quanAnDataSource.insertQuanAn("Nhà phô mai", "440/40 Thống Nhất, P.16, Quận Gò Vấp, TP.HCM", 7.0, 2131165334, true, 1);
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
                        int id=userDataSource.getIdUser(username,password);
                        Intent intent = new Intent(getApplicationContext(), Index.class);
                        intent.putExtra(INTENT_ID_USER, id);
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