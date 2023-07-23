package com.example.baitaplon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baitaplon.database.QuanAn;

import java.util.ArrayList;

public class QuanAnAdapter extends ArrayAdapter<QuanAn> {
    private Context mContext;
    private int mResource;

    public QuanAnAdapter(@NonNull Context context, int resource, @NonNull ArrayList<QuanAn> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource, parent, false);

        ImageView imageView = convertView.findViewById(R.id.image);
        TextView textName = convertView.findViewById(R.id.txtName);
        TextView textSub = convertView.findViewById(R.id.txtSub);

        imageView.setImageResource(getItem(position).getHinhanh());

        textName.setText(getItem(position).getTenquan());

        textSub.setText(getItem(position).getDiadiem());

        return convertView;
    }
}
