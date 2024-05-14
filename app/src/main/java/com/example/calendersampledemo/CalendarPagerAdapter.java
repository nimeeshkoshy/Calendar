package com.example.calendersampledemo;

import android.content.Context;
import android.util.Pair;
import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarPagerAdapter extends FragmentStateAdapter {
    public static final int INITIAL_OFFSET = Integer.MAX_VALUE / 2;
    private SparseArray<Fragment> cachedFragments;
    private Context context;
    private List<Pair<Integer, Integer>> preloadedMonths;

    public CalendarPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        cachedFragments = new SparseArray<>();
        context = fragmentActivity.getApplicationContext();
        preloadedMonths = new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment cachedFragment = cachedFragments.get(position);
        if (cachedFragment != null) {
            return cachedFragment;
        }

        // Calculate the month and year based on the position
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.MONTH, position - INITIAL_OFFSET);
        int month = currentDate.get(Calendar.MONTH) + 1;
        int year = currentDate.get(Calendar.YEAR);




        Fragment newFragment = CalendarFragment.newInstance(month, year);
        cachedFragments.put(position, newFragment);
        return newFragment;
    }

    @Override
    public int getItemCount() {

        return Integer.MAX_VALUE;
    }



}