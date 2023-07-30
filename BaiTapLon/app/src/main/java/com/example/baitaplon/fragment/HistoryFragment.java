package com.example.baitaplon.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.baitaplon.Details;
import com.example.baitaplon.Index;
import com.example.baitaplon.QuanAnAdapter;
import com.example.baitaplon.R;
import com.example.baitaplon.database.Comment;
import com.example.baitaplon.database.CommentDataSource;
import com.example.baitaplon.database.QuanAn;


import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {
    public static final String INTENT_ID_QUAN="id_quanan";
    public static final String INTENT_ID_USER="id_user";
    private int id_user_his;
    QuanAnAdapter quanAnAdapter;
    ListView listHis;
    Index activityIndex;
    CommentDataSource commentDataSource;
    View mview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview = inflater.inflate(R.layout.fragment_history, container, false);
        activityIndex = (Index) getActivity();
        id_user_his = activityIndex.getId_user_index();


        initUI();
        return mview;
    }

    private void initUI() {
        commentDataSource = new CommentDataSource(requireContext());
        commentDataSource.open();


        ArrayList<QuanAn> listQuanAn = commentDataSource.getAllHisQuanAn(id_user_his);
        listHis = mview.findViewById(R.id.listHis);

        // Tạo Adapter và set cho ListView
        quanAnAdapter= new QuanAnAdapter(requireContext(),R.layout.list_row,listQuanAn);
        listHis.setAdapter(quanAnAdapter);
        listHis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy thông tin quán ăn tại vị trí nhấp vào
                // Chuyển sang trang Detail và truyền thông tin của quán ăn
                Intent intent = new Intent(requireContext(), Details.class);
                intent.putExtra("tenquan", listQuanAn.get(position).getTenquan());
                intent.putExtra("diadiem", listQuanAn.get(position).getDiadiem());
                intent.putExtra("hinhanh", listQuanAn.get(position).getHinhanh());

                intent.putExtra(INTENT_ID_QUAN,listQuanAn.get(position).getId());
                intent.putExtra(INTENT_ID_USER,id_user_his);
                startActivity(intent);
            }
        });
        commentDataSource.close();
    }
}