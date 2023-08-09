package com.example.baitaplon;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baitaplon.database.Comment;
import com.example.baitaplon.database.CommentDataSource;
import com.example.baitaplon.database.QuanAn;
import com.example.baitaplon.database.QuanAnDataSource;

import java.util.ArrayList;

public class CommentAdminAdapter extends BaseAdapter{
    ArrayList<Comment> listComment;
    CommentDataSource commentDAO;
    public CommentAdminAdapter(ArrayList<Comment> listComment, CommentDataSource commentDAO){
        this.listComment = listComment;
        this.commentDAO = commentDAO;
    }
    @Override
    public int getCount() {
        return listComment.size();
    }

    @Override
    public Object getItem(int i) {
        Comment objComment = listComment.get(i);
        return objComment;
    }

    @Override
    public long getItemId(int i) {
        Comment objComment = listComment.get(i);
        return objComment.getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView;
        // khởi tạo cho itemView
        if(view == null){

            itemView = View.inflate(viewGroup.getContext(), R.layout.item_listview_comment, null);
        }else
            itemView = view;


        //--- lấy thông tin bản ghi dữ liệu
        final Comment objComment = listComment.get(i);
        User objUser = new User();
        final int _index = i;

        // ánh xạ các view vào biến
        TextView tv_id = itemView.findViewById(R.id.tv_id);
        TextView tv_commennt = itemView.findViewById(R.id.tv_comment);
        TextView tv_user = itemView.findViewById(R.id.tv_user);

        TextView tv_del = itemView.findViewById(R.id.tv_del);
        TextView tv_detail = itemView.findViewById(R.id.tv_detail);

        tv_id.setText( objComment.getId() + "");
        tv_commennt.setText( objComment.getNoidung());
        tv_user.setText(String.valueOf(objComment.getDanhgia()));

        tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //------------ hiển thị dialog hỏi
                AlertDialog.Builder builder = new AlertDialog.Builder(viewGroup.getContext());
                builder.setTitle("Xóa Comment?");
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setMessage("Có chắc chắn xóa : " + objComment.getNoidung());

                builder.setPositiveButton("Đồng ý xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // xử lý sự kiện xóa

                        // gọi lệnh xóa dòng
                        int kq = commentDAO.deleteRow(objComment);
                        if(kq > 0)
                        {
                            // xóa thành công trong csdl
                            listComment.remove(_index); // xóa khỏi danh sách
                            notifyDataSetChanged();
                            Toast.makeText(viewGroup.getContext(), "Đã xóa ", Toast.LENGTH_SHORT).show();

                        }else
                            Toast.makeText(viewGroup.getContext(), "Không xóa được  " + kq, Toast.LENGTH_SHORT).show();

                        dialogInterface.dismiss(); // đóng dialog

                    }
                });

                builder.setNegativeButton("Không xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss(); // đóng dialog
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialogDetail(objComment, _index ,viewGroup.getContext());
            }
        });
        return itemView;
    }
    public void showDialogDetail(Comment objComment, int index, Context context) {

        final Dialog dialog = new Dialog(context, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog_Alert);
        com.example.baitaplon.database.Comment new_obj_comment = commentDAO.selectOne(objComment.getId());
        dialog.setContentView(R.layout.dialog_detail_comment);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println(new_obj_comment.getDanhgia());
        System.out.println(new_obj_comment.getId());
        System.out.println(new_obj_comment.getNgaydang());
        System.out.println(new_obj_comment.getNoidung());
        System.out.println(new_obj_comment.getId_qan());
        System.out.println(new_obj_comment.getId_user());
        TextView ed_noidung = dialog.findViewById(R.id.tv_noidung);
        ed_noidung.setText(new_obj_comment.getNoidung());
        TextView ed_danhgia = dialog.findViewById(R.id.tv_danhgia);
        ed_danhgia.setText(String.valueOf(new_obj_comment.getDanhgia()));
        TextView ed_ngaydaang = dialog.findViewById(R.id.tv_ngaydang);
        ed_ngaydaang.setText(new_obj_comment.getNgaydang());
        TextView ed_iduser = dialog.findViewById(R.id.tv_user);
        ed_iduser.setText(String.valueOf(new_obj_comment.getId_user()));
        TextView ed_idquan = dialog.findViewById(R.id.tv_quan);
        ed_idquan.setText(String.valueOf(new_obj_comment.getId_qan()));
        dialog.show();
    }

}