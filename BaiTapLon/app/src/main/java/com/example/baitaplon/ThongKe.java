package com.example.baitaplon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.baitaplon.database.CommentDataSource;
import com.example.baitaplon.database.QuanAn;
import com.example.baitaplon.database.QuanAnDataSource;

import java.util.ArrayList;

public class ThongKe extends AppCompatActivity {
    CommentDataSource commentDAO;
    QuanAnDataSource quanAnDAO;
    ThongKeAdapter thongKeAdapter;

    Button btnGroup;
    Button btnAdd;
    ListView lvComment;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvComment = findViewById(R.id.lvGroup);

        quanAnDAO = new QuanAnDataSource(this);
        quanAnDAO.open();

        commentDAO = new CommentDataSource(this);
        commentDAO.open();

        // Lấy danh sách quán ăn từ cơ sở dữ liệu
        ArrayList<QuanAn> restaurantList = quanAnDAO.getAllQuan();

        // Khởi tạo adapter và thiết lập cho listView
        thongKeAdapter = new ThongKeAdapter(restaurantList, commentDAO);
        lvComment.setAdapter(thongKeAdapter);
    }
}