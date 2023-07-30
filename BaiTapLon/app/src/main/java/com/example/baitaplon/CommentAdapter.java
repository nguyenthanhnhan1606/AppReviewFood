package com.example.baitaplon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.baitaplon.database.Comment;

import java.util.ArrayList;

public class CommentAdapter extends ArrayAdapter<Comment> {
    private Context context;
    private ArrayList<Comment> comments;

    public CommentAdapter(Context context, ArrayList<Comment> comments) {
        super(context, 0, comments);
        this.context = context;
        this.comments = comments;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_comment_items, parent, false);
        }

        // Lấy các View trong layout item comment_item.xml
        TextView textAuthor = convertView.findViewById(R.id.usernameTextView);
        TextView textContent = convertView.findViewById(R.id.commentTextView);
        TextView textNgayDang = convertView.findViewById(R.id.timestampTextView);

        // Lấy thông tin của comment từ danh sách comments
        Comment comment = comments.get(position);

        // Gán thông tin vào các TextView
        textAuthor.setText(comment.getUser().getHoten());
        textContent.setText(comment.getNoidung());
        textNgayDang.setText(comment.getNgaydang());

        return convertView;
    }
}
