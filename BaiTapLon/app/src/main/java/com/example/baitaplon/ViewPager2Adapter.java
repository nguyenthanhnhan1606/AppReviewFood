package com.example.baitaplon;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.baitaplon.fragment.CommentFragment;
import com.example.baitaplon.fragment.DanhGiaFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPager2Adapter extends FragmentStateAdapter {
    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new CommentFragment();
            case 1:
                return new DanhGiaFragment();
            default:
                return new DanhGiaFragment();
        }
    }


    @Override
    public int getItemCount() {
        return 2;
    }
}
