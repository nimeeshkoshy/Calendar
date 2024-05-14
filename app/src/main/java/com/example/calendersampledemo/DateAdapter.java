package com.example.calendersampledemo;



import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {

    private List<Integer> dates;

    private int startDayOffset;
    private int month;
    private int year;
    private int currentDay;
    private int currentMonth;
    private int currentYear;
    private OnDateSelectedListener dateSelectedListener;
    private int selectedDate = -1;


    Context context ;

    public interface OnDateSelectedListener {
        void onDateSelected(int year, int month, int date);
    }

    public DateAdapter(int startDayOffset, int month, int year,Context context,OnDateSelectedListener listener) {
        this.context= context;
        this.startDayOffset = startDayOffset;
        this.month = month;
        this.year = year;
        this.dateSelectedListener = listener;
        this.dates = generateDates(startDayOffset, month, year);


        Calendar calendar = Calendar.getInstance();
        this.currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        this.currentMonth = calendar.get(Calendar.MONTH) + 1;
        this.currentYear = calendar.get(Calendar.YEAR);
    }


    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        Integer date = dates.get(position);



        if (date != null) {
            holder.bind(date);
            holder.dateTextView.setBackgroundResource(0);
            holder.dateTextView.setTextColor(Color.BLACK);


            if (date == currentDay && month == currentMonth && year == currentYear &&
                    position >= startDayOffset && position - startDayOffset < dates.size()) {
                holder.view_line.setVisibility(View.VISIBLE);
            } else {
                holder.view_line.setVisibility(View.INVISIBLE);
            }


            if (date == selectedDate) {
                holder.dateTextView.setBackgroundResource(R.drawable.selected_date_background);
            } else {
                holder.dateTextView.setBackgroundResource(0);
            }


            holder.dateTextView.setOnClickListener(v -> {

                int previousSelectedDate = selectedDate;
                selectedDate = date;



                notifyItemChanged(dates.indexOf(previousSelectedDate));
                notifyItemChanged(dates.indexOf(selectedDate));



                if (dateSelectedListener != null) {
                    dateSelectedListener.onDateSelected(year, month, date);
                }
            });
        } else {
            holder.bind(null);
            holder.dateTextView.setBackgroundResource(0);
            holder.dateTextView.setTextColor(Color.BLACK);
            holder.dateTextView.setOnClickListener(null);
            holder.view_line.setVisibility(View.INVISIBLE);
        }

        if (date != null) {
            holder.bind(date);


        } else {
            holder.bind(null);
            holder.dateTextView.setBackgroundResource(0);
            holder.dateTextView.setTextColor(Color.BLACK);
            holder.dateTextView.setOnClickListener(null);

        }
    }




    @Override
    public int getItemCount() {
        return dates.size();
    }

    static class DateViewHolder extends RecyclerView.ViewHolder {

        private TextView dateTextView;
        View view_line;




        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            view_line=itemView.findViewById(R.id.view_date_line);

        }

        public void bind(Integer date) {
            if (date != null) {
                dateTextView.setText(String.valueOf(date));

            } else {

                dateTextView.setText("");

            }
        }
    }

    // Method to generate the list of dates for the given month and year
    private List<Integer> generateDates(int startDayOffset, int month, int year) {
        List<Integer> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);


        for (int i = 0; i < startDayOffset; i++) {
            dates.add(null);
        }


        int numDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= numDays; i++) {
            dates.add(i);
        }

        return dates;
    }
}