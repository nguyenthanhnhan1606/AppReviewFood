package com.example.baitaplon;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baitaplon.db.LoaiQuan;
import com.example.baitaplon.db.LoaiQuanDataSource;
import com.example.baitaplon.db.QuanAn;
import com.example.baitaplon.db.QuanAnDataSource;

import java.util.ArrayList;
import java.util.List;

public class Index extends AppCompatActivity {


    ListView list;

    private QuanAnDataSource db;
    private LoaiQuanDataSource lq;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);


        list = (ListView)findViewById(R.id.list);

//        lq = new LoaiQuanDataSource(this);
//        lq.open();
//        LoaiQuan loaiQuan = lq.insertLoaiQuan("Quán ăn");
//        lq.close();


        db = new QuanAnDataSource(this);
        db.open();
        QuanAn quanAn = db.insertQuanAn("Bánh mì PewPew", "66 Út Tịch, phường 4, Tân Bình, TP.HCM"," ", 8.0, 1);
        List<QuanAn> quanAns = new ArrayList<QuanAn>();
        quanAns.add(quanAn);
        quanAns = this.db.getAll();
        ArrayAdapter<QuanAn> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, quanAns);
        list.setAdapter(adapter);
        db.close();

    }


//    @Override
//    protected void onResume() {
//        db.open();
//        super.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        db.close();
//        super.onPause();
//    }
}
