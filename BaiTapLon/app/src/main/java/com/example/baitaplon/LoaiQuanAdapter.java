package com.example.baitaplon;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baitaplon.database.LoaiQuanDataSource;
import com.example.baitaplon.database.UserDataSource;
import com.example.baitaplon.database.LoaiQuan;

import java.util.ArrayList;

public class LoaiQuanAdapter extends BaseAdapter {
    ArrayList<LoaiQuan> listLoaiQuan;
    LoaiQuanDataSource loaiQuanDAO;

    public LoaiQuanAdapter(ArrayList<LoaiQuan> listLoaiQuan,LoaiQuanDataSource loaiQuanDAO) {
        this.listLoaiQuan = listLoaiQuan;
        this.loaiQuanDAO = loaiQuanDAO;
    }

    @Override
    public int getCount() {
        return listLoaiQuan.size();
    }

    @Override
    public Object getItem(int position) {
        LoaiQuan objLoaiQuan = listLoaiQuan.get(position);

        return objLoaiQuan;
    }

    @Override
    public long getItemId(int position) {
        LoaiQuan objLoaiQuan = listLoaiQuan.get(position);
        return objLoaiQuan.getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView;
        // khởi tạo cho itemView
        if (view == null) {

            itemView = View.inflate(viewGroup.getContext(), R.layout.item_listview_loaiquan, null);
        } else
            itemView = view;

        //--- lấy thông tin bản ghi dữ liệu
        final LoaiQuan objLoaiQuan = listLoaiQuan.get(i);
        final int _index = i;


        // ánh xạ các view vào biến
        TextView tv_id = itemView.findViewById(R.id.tv_id);
        TextView tv_tenloaiquan = itemView.findViewById(R.id.tv_tenloaiquan);

        TextView tv_del = itemView.findViewById(R.id.tv_del);
        TextView tv_edit = itemView.findViewById(R.id.tv_edit);


        //----------- set text
        tv_id.setText(objLoaiQuan.getId() + "");
        tv_tenloaiquan.setText(objLoaiQuan.getTenloai());

        tv_tenloaiquan.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(viewGroup.getContext());
                builder.setTitle("Thông tin loai quán");

                LoaiQuan new_obj_loaiquan = loaiQuanDAO.selectOne(objLoaiQuan.getId());
                builder.setMessage(new_obj_loaiquan.toString());
                AlertDialog dialog = builder.create();
                dialog.show();

                return false;
            }
        });

        tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //------------ hiển thị dialog hỏi
                AlertDialog.Builder builder = new AlertDialog.Builder(viewGroup.getContext());
                builder.setTitle("Xóa Loai Quán?");
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setMessage("Có chắc chắn xóa : " + objLoaiQuan.getTenloai());

                builder.setPositiveButton("Đồng ý xóa", (dialogInterface, i1) -> {
                    // xử lý sự kiện xóa
                    int kq = loaiQuanDAO.deleteRow(objLoaiQuan);
                    if (kq > 0) {
                        // xóa thành công trong csdl
                        listLoaiQuan.remove(_index); // xóa khỏi danh sách
                        notifyDataSetChanged();
                        Toast.makeText(viewGroup.getContext(), "Đã xóa ", Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(viewGroup.getContext(), "Không xóa được  " + kq, Toast.LENGTH_SHORT).show();

                    dialogInterface.dismiss(); // đóng dialog
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEdit(objLoaiQuan, _index, viewGroup.getContext());
            }
        });
        return itemView;
    }

    public void showDialogEdit(LoaiQuan objLoaiQuan, int index, Context context) {

        final Dialog dialog = new Dialog(context, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog_Alert);
        LoaiQuan new_obj_user = loaiQuanDAO.selectOne(objLoaiQuan.getId());
        dialog.setContentView(R.layout.dialog_edit_loaiquan);

        EditText ed_tenLoaiQuan = dialog.findViewById(R.id.ed_tenLoaiQuan);
        ed_tenLoaiQuan.setText(new_obj_user.getTenloai());

        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // viết lệnh update dữ liệu
                objLoaiQuan.setTenloai(ed_tenLoaiQuan.getText().toString());
                int res = loaiQuanDAO.updateRow(objLoaiQuan);

                if (res > 0) {
                    listLoaiQuan.set(index, objLoaiQuan);

                    notifyDataSetChanged();
                    Toast.makeText(context, "Đã sửa ", Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(context, "Không sửa được  " + res, Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showDialogAdd(Context context) {

//        final Dialog dialog = new Dialog(context);
        final Dialog dialog = new Dialog(context, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog_Alert);


        dialog.setContentView(R.layout.dialog_add_loaiquan);
        dialog.setTitle("Thêm tên loại quán mới");

        EditText ed_username = dialog.findViewById(R.id.ed_tenLoaiQuan);

        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // viết lệnh update dữ liệu

                String tenLoaiQuan = ed_username.getText().toString();
                if (loaiQuanDAO.checkTenLoaiQuan(tenLoaiQuan) == false) {
                    LoaiQuan res = loaiQuanDAO.insertLoaiQuan(tenLoaiQuan);
                    if (res == null) {
                        Toast.makeText(context, "Không thêm được  ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context.getApplicationContext(), com.example.baitaplon.LoaiQuan.class);
                        context.startActivity(intent);
                    } else {
                        listLoaiQuan.clear();
                        listLoaiQuan.addAll(loaiQuanDAO.getAllLoai());

                        notifyDataSetChanged();
                        Toast.makeText(context, "Đã thêm mới ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context.getApplicationContext(), com.example.baitaplon.LoaiQuan.class);
                        context.startActivity(intent);

                        dialog.dismiss();
                    }
                }
                else {
                    Toast.makeText(context, "Tên loại bị trùng, đăt lại !!  ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();

    }
}
