package com.example.baitaplon.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.baitaplon.CommentAdapter;
import com.example.baitaplon.Details;
import com.example.baitaplon.Index;
import com.example.baitaplon.R;
import com.example.baitaplon.database.Comment;
import com.example.baitaplon.database.CommentDataSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class CommentFragment extends Fragment {
    ListView listComments;
    EditText editComment;
    Button btnSendComment;
    ArrayList<Comment> listcomment;
    private CommentAdapter commentAdapter;
    private Details activityDetail;
    private View mview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview = inflater.inflate(R.layout.fragment_comment, container, false);
        activityDetail = (Details) getActivity();
        editComment = mview.findViewById(R.id.editTextComment);
        btnSendComment = mview.findViewById(R.id.btnSendComment);

        btnSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id_quan= activityDetail.getId_quan_detail();
                CommentDataSource commentDataSource = new CommentDataSource(requireContext());
                commentDataSource.open();
                listcomment = commentDataSource.getAllCommentsByid(id_quan);
                if(!editComment.getText().toString().isEmpty()){
                    commentAdapter.clear();
                    commentDataSource.insertComment(editComment.getText().toString(),0, String.valueOf(LocalDate.now()),activityDetail.getId_user_detail(),activityDetail.getId_quan_detail());
                    listcomment = commentDataSource.getAllCommentsByid(id_quan);
                    commentAdapter = new CommentAdapter(requireContext(), listcomment);
                    listComments = mview.findViewById(R.id.listComments);
                    listComments.setAdapter(commentAdapter);

                    Toast.makeText(requireContext(), "Bình luận thành công", Toast.LENGTH_SHORT).show();
                    editComment.setText("");
                }else {
                    Toast.makeText(requireContext(), "Bình luận của bạn chưa nhập!", Toast.LENGTH_SHORT).show();
                }

                commentDataSource.close();
            }
        });
        initUI();
        return  mview;
    }

    private void initUI() {
        int id_quan= activityDetail.getId_quan_detail();
        CommentDataSource cmd= new CommentDataSource(requireActivity());
        cmd.open();
        cmd.getAllCommentsByid(id_quan);
        listcomment = cmd.getAllCommentsByid(id_quan);
        commentAdapter = new CommentAdapter(requireContext(), listcomment);
        listComments = mview.findViewById(R.id.listComments);
//        ArrayAdapter<Comment> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, listcomment);
        listComments.setAdapter(commentAdapter);

        cmd.close();
    }
}