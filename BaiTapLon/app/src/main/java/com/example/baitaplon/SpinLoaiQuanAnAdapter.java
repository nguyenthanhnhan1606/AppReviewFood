package com.example.baitaplon;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.baitaplon.database.LoaiQuan;

import java.util.ArrayList;

public class SpinLoaiQuanAnAdapter extends BaseAdapter {
    ArrayList<LoaiQuan> listLoaiQuan;

    public SpinLoaiQuanAnAdapter(ArrayList<LoaiQuan> listLoaiQuan) {
        this.listLoaiQuan = listLoaiQuan;
    }

    @Override
    public int getCount() {
        return listLoaiQuan.size();
    }

    @Override
    public Object getItem(int i) {
        LoaiQuan objLoaiQuan = listLoaiQuan.get(i);
        return objLoaiQuan;
    }

    @Override
    public long getItemId(int i) {
        LoaiQuan objLoaiQuan = listLoaiQuan.get(i);
        return objLoaiQuan.getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView;
        // khởi tạo cho itemView
        if (view == null) {

            itemView = View.inflate(viewGroup.getContext(), R.layout.item_spin_loaiquan_quanan, null);
        } else
            itemView = view;

        //--- lấy thông tin bản ghi dữ liệu
        final LoaiQuan objLoaiQuan = listLoaiQuan.get(i);
        final int _index = i;

        // ánh xạ các view vào biến
        TextView tv_id = itemView.findViewById(R.id.tv_id);
        TextView tv_name = itemView.findViewById(R.id.tv_name);

        //----------- set text
        tv_id.setText(objLoaiQuan.getId() + "");
        tv_name.setText(objLoaiQuan.getTenloai());

        return itemView;
    }
}
