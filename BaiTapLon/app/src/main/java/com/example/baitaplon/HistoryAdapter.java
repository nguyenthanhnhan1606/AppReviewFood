package com.example.baitaplon;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.baitaplon.database.QuanAn;

public class HistoryAdapter extends ArrayAdapter<QuanAn> {
    public HistoryAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
