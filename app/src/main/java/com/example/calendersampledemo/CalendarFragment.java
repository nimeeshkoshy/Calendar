package com.example.calendersampledemo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CalendarFragment extends Fragment {

    private static final String ARG_MONTH = "month";
    private static final String ARG_YEAR = "year";

    private RecyclerView recyclerViewDates;
    private DateAdapter dateAdapter;
    private int month;
    private int year;

    public static CalendarFragment newInstance(int month, int year) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MONTH, month);
        args.putInt(ARG_YEAR, year);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        recyclerViewDates = view.findViewById(R.id.recyclerViewDates);


        recyclerViewDates.setLayoutManager(new GridLayoutManager(getContext(), 7)); // 7 columns for days of the week


        if (getArguments() != null) {
            month = getArguments().getInt(ARG_MONTH);
            year = getArguments().getInt(ARG_YEAR);


            int startDayOffset = calculateStartDayOffset(month, year);
            dateAdapter = new DateAdapter(startDayOffset, month, year, getContext(),this::onDateSelected);
            recyclerViewDates.setAdapter(dateAdapter);


            updateMonthHeader(month, year);










        }

        return view;
    }

    public void onDateSelected(int year, int month, int date) {

        Toast.makeText(requireContext(), "Selected Date: " + year + "-" + month + "-" + date, Toast.LENGTH_SHORT).show();

    }




    // Helper method to update the month header
    private void updateMonthHeader(int month, int year) {
        String monthName = getMonthName(month);
        // Update the month header TextView here
    }

    // Helper method to get the name of the month
    private String getMonthName(int month) {
        DateFormatSymbols dfs = new DateFormatSymbols();
        return dfs.getMonths()[month - 1];
    }

    // Method to calculate the offset of the first day of the month
    private int calculateStartDayOffset(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1); // Set the calendar to the first day of the month
        return calendar.get(Calendar.DAY_OF_WEEK) - 1; // Adjust for 0-based indexing of days
    }


}