package com.example.baitaplon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.baitaplon.database.CommentDataSource;

public class Comment extends AppCompatActivity {
    CommentDataSource commentDAO;
    CommentAdminAdapter commentAdapter;

    Button btnGroup;
    Button btnAdd;
    ListView lvComment;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        commentDAO = new CommentDataSource(this);
        commentDAO.open();

        commentAdapter = new CommentAdminAdapter(commentDAO.selectAll(), commentDAO);
        lvComment = findViewById(R.id.lvGroup);
        lvComment.setAdapter(commentAdapter);

    }
}
