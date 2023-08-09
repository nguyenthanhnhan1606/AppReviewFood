package com.example.baitaplon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.baitaplon.database.Comment;
import com.example.baitaplon.database.CommentDataSource;
import com.example.baitaplon.database.QuanAn;
import com.example.baitaplon.database.QuanAnDataSource;

import java.util.ArrayList;

public class ThongKeAdapter extends BaseAdapter {
    private ArrayList<com.example.baitaplon.database.QuanAn> restaurants;
    CommentDataSource commentDAO;
    public ThongKeAdapter(ArrayList<QuanAn> restaurants, CommentDataSource commentDAO) {
        this.restaurants = restaurants;
        this.commentDAO = commentDAO;
    }
    @Override
    public int getCount() {
        return restaurants.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return restaurants.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            itemView = inflater.inflate(R.layout.item_listview_thongke, parent, false);
        }

        com.example.baitaplon.database.QuanAn quanAn = restaurants.get(position);

        TextView nameTextView = itemView.findViewById(R.id.tv_idCount);
//        TextView addressTextView = itemView.findViewById(R.id.tv_quanCount);
        TextView commentCountTextView = itemView.findViewById(R.id.tv_binhluanCount);
        TextView ratingCountTextView = itemView.findViewById(R.id.tv_danhgiaCount);

        nameTextView.setText(quanAn.getTenquan());
//        addressTextView.setText(quanAn.getDiadiem());

        // Lấy số lượng bình luận và đánh giá từ cơ sở dữ liệu
        int commentCount = commentDAO.getCommentCountForRestaurant(quanAn.getId());
        int ratingCount = commentDAO.getRatingCountForRestaurant(quanAn.getId());

        commentCountTextView.setText("Bình luận: " + commentCount);
        ratingCountTextView.setText("Đánh giá: " + ratingCount);

        return itemView;
    }

}
