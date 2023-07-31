package com.example.baitaplon;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.baitaplon.fragment.CategoryFragment;
import com.example.baitaplon.fragment.CommentFragment;
import com.example.baitaplon.fragment.DanhGiaFragment;
import com.example.baitaplon.fragment.HistoryFragment;
import com.example.baitaplon.fragment.QuanAnFragment;

public class ViewPageDetailAdapter extends FragmentStateAdapter {
    public ViewPageDetailAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new CommentFragment();
            case 1:
                return new DanhGiaFragment();
            default:
                return new CommentFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
