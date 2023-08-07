package com.example.baitaplon;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baitaplon.database.LoaiQuan;
import com.example.baitaplon.database.LoaiQuanDataSource;
import com.example.baitaplon.database.QuanAn;
import com.example.baitaplon.database.QuanAnDataSource;

import java.util.ArrayList;

public class QuanAdapter extends BaseAdapter {
    ArrayList<QuanAn> listQuan;
    QuanAnDataSource quanDAO;

    public QuanAdapter(ArrayList<QuanAn> listQuan, QuanAnDataSource quanDAO) {
        this.listQuan = listQuan;
        this.quanDAO = quanDAO;
    }

    @Override
    public int getCount() {
        return listQuan.size();
    }

    @Override
    public Object getItem(int i) {
        QuanAn objQuan = listQuan.get(i);

        return objQuan;
    }

    @Override
    public long getItemId(int i) {
        QuanAn objQuan = listQuan.get(i);
        return objQuan.getId_loai();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView;
        // khởi tạo cho itemView
        if(view == null){

            itemView = View.inflate(viewGroup.getContext(), R.layout.item_listview_quan, null);
        }else
            itemView = view;


        //--- lấy thông tin bản ghi dữ liệu
        final QuanAn objQuan = listQuan.get(i);
        final int _index = i;

        // ánh xạ các view vào biến
        TextView tv_id = itemView.findViewById(R.id.tv_id);
        TextView tv_tenquan = itemView.findViewById(R.id.tv_tenQuan);

        TextView tv_del = itemView.findViewById(R.id.tv_del);
        TextView tv_edit = itemView.findViewById(R.id.tv_edit);


        //----------- set text
        tv_id.setText( objQuan.getId() + "");
        tv_tenquan.setText( objQuan.getTenquan());

        //------ viết sự kiện bấm nút
        tv_tenquan.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(viewGroup.getContext());
                builder.setTitle("Thông tin quán");

                QuanAn new_obj_quan = quanDAO.selectOne(objQuan.getId());
                builder.setMessage(new_obj_quan.toString());
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
                builder.setTitle("Xóa quán?");
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setMessage("Có chắc chắn xóa : " + objQuan.getTenquan());

                builder.setPositiveButton("Đồng ý xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // xử lý sự kiện xóa

                        // gọi lệnh xóa dòng
                        int kq = quanDAO.deleteQuanAn(objQuan);
                        if(kq > 0)
                        {
                            // xóa thành công trong csdl
                            listQuan.remove(_index); // xóa khỏi danh sách
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

        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEdit(objQuan, _index ,viewGroup.getContext());
            }
        });
        return itemView;
    }

    public void showDialogEdit(QuanAn objQuan, int index, Context context){

        final Dialog dialog = new Dialog(context, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog_Alert);
        com.example.baitaplon.database.QuanAn new_obj_quan = quanDAO.selectOne(objQuan.getId());
        dialog.setContentView(R.layout.dialog_edit_quan);

        EditText ed_tenquan = dialog.findViewById(R.id.ed_tenQuan);
        ed_tenquan.setText(new_obj_quan.getTenquan());
        EditText ed_diadiem = dialog.findViewById(R.id.ed_diadiem);
        ed_diadiem.setText(new_obj_quan.getDiadiem());
        EditText ed_danhgia = dialog.findViewById(R.id.ed_danhgia);
        ed_danhgia.setText(String.valueOf(new_obj_quan.getDanhgia()));
        EditText ed_hinhanh = dialog.findViewById(R.id.ed_hinhanh);
        ed_hinhanh.setText(String.valueOf(new_obj_quan.getHinhanh()));
        Switch activeu = dialog.findViewById(R.id.sp_active);
        activeu.setChecked(new_obj_quan.isActive());

        // làm spinner chọn
        final Spinner spin_loai = dialog.findViewById(R.id.spin_loai);

        LoaiQuanDataSource loaiQuanDAO = new LoaiQuanDataSource(context);
        loaiQuanDAO.open();
        ArrayList<LoaiQuan> listLoaiQuan = loaiQuanDAO.getAllLoai();
        SpinLoaiQuanAnAdapter adapter = new SpinLoaiQuanAnAdapter(listLoaiQuan);
        spin_loai.setAdapter(adapter);

        for(int j = 0;j<listLoaiQuan.size(); j++){
            LoaiQuan tmp = listLoaiQuan.get(j);
            if(tmp.getId() == objQuan.getId_loai()){
                spin_loai.setSelection(j);
                spin_loai.setSelected(true);
            }
        }

        //======


        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // viết lệnh update dữ liệu

                objQuan.setTenquan(ed_tenquan.getText().toString());
                objQuan.setDiadiem(ed_diadiem.getText().toString());
                objQuan.setDanhgia(Double.parseDouble(ed_danhgia.getText().toString()));
                objQuan.setHinhanh(Integer.parseInt(ed_hinhanh.getText().toString()));
                objQuan.setActive(activeu.getShowText());
                // lấy id spin
                LoaiQuan objloaiQuan  =  (LoaiQuan) spin_loai.getSelectedItem();
                objQuan.setId_loai( objloaiQuan.getId() );
                int res = quanDAO.updateRow(objQuan);
                if(res > 0)
                {
                    listQuan.set(index,objQuan);

                    notifyDataSetChanged();
                    Toast.makeText(context, "Đã sửa ", Toast.LENGTH_SHORT).show();

                }else
                    Toast.makeText(context, "Không sửa được  " + res, Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
        dialog.show();
    }

//    public void showDialogAdd( Context context){
//
////        final Dialog dialog = new Dialog(context);
//        final Dialog dialog = new Dialog(context,androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog_Alert);
//
//
//        dialog.setContentView(R.layout.dialog_add_quan);
//        dialog.setTitle("Thêm quán mới");
//
//        EditText ed_tenquan = dialog.findViewById(R.id.ed_tenQuan);
//        EditText ed_diadiem = dialog.findViewById(R.id.ed_diadiem);
//        EditText ed_danhgia = dialog.findViewById(R.id.ed_danhgia);
//        ed_danhgia.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // Kiểm tra và hạn chế giá trị nhập vào
//                String input = s.toString();
//                if (!input.isEmpty()) {
//                    int value = Integer.parseInt(input);
//                    if (value > 5) {
//                        ed_danhgia.setText(String.valueOf(5));
//                    }
//                }
//            }
//        });
//        EditText ed_hinhanh = dialog.findViewById(R.id.ed_hinhanh);
//
//        // làm spinner chọn
//        final Spinner spin_loai = dialog.findViewById(R.id.spin_loai);
//
//        LoaiQuanDataSource loaiQuanDAO = new LoaiQuanDataSource(context);
//        loaiQuanDAO.open();
//
//        SpinLoaiQuanAnAdapter adapter = new SpinLoaiQuanAnAdapter( loaiQuanDAO.getAllLoai() );
//        spin_loai.setAdapter(adapter);
//        //======
//
//        Button btnSave = dialog.findViewById(R.id.btnSave);
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // viết lệnh update dữ liệu
//                QuanAn objQuan = new QuanAn();
//                String ten = ed_tenquan.getText().toString();
//                String diadiem = ed_diadiem.getText().toString();
//                String danhgiaText = ed_danhgia.getText().toString().trim();
//                String hinhanhText = ed_hinhanh.getText().toString().trim();
//                Double danhgia = Double.valueOf(5);
//                Integer hinhanh = 0;
//                if (!danhgiaText.isEmpty()) {
//                     danhgia = Double.parseDouble(danhgiaText);
//                }
//                if (!hinhanhText.isEmpty()) {
//                     hinhanh = Integer.parseInt(hinhanhText);
//                }
//                // lấy id spin
//                LoaiQuan objLoaiQuan =  (LoaiQuan)spin_loai.getSelectedItem();
//
//                int loaiQuanId = 1;
//                if (objLoaiQuan != null) {
//                    loaiQuanId = objLoaiQuan.getId();
//                }
//
//                if (quanDAO.checkNameQuan(ten) == false) {
//                    try {
//                        quanDAO.insertQuanAn(ten, diadiem, danhgia, hinhanh, true, loaiQuanId);
//
//                    } catch (NullPointerException ex) {
//                        quanDAO.insertQuanAn(ten, diadiem, 5, 0, true, loaiQuanId);
//                    }
//                    listQuan.clear();
//                    listQuan.addAll(quanDAO.getAllQuan());
//                    notifyDataSetChanged();
//                    Toast.makeText(context, "Đã thêm mới ", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(context.getApplicationContext(), com.example.baitaplon.QuanAn.class);
//                    context.startActivity(intent);
//                    dialog.dismiss();
//                }
//                else {
//                    Toast.makeText(context, "Tên bị trùng, đăt lại !!  ", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        dialog.show();
//
//    }
}
