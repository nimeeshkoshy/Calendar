package com.example.calendersampledemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.calendersampledemo.databinding.ActivityMainBinding;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int currentMonth;
    private int currentYear;

    private float initialX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Calendar calendar = Calendar.getInstance();
        currentMonth = calendar.get(Calendar.MONTH) + 1;
        currentYear = calendar.get(Calendar.YEAR);
        updateMonthHeader();


        CalendarPagerAdapter pagerAdapter = new CalendarPagerAdapter(this);
        binding.viewPager.setAdapter(pagerAdapter);


        binding.viewPager.setCurrentItem(CalendarPagerAdapter.INITIAL_OFFSET, false);


        binding.leftArrow.setOnClickListener(v -> handleArrowClick(-1));
        binding.rightArrow.setOnClickListener(v -> handleArrowClick(1));


        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

                Calendar currentDate = Calendar.getInstance();
                currentDate.add(Calendar.MONTH, position - CalendarPagerAdapter.INITIAL_OFFSET);
                currentMonth = currentDate.get(Calendar.MONTH) + 1; // Adding 1 because Calendar.MONTH is zero-based
                currentYear = currentDate.get(Calendar.YEAR);
                updateMonthHeader();
            }
        });
    }

    // Handle arrow clicks (1 for right, -1 for left)
    private void handleArrowClick(int direction) {
        currentMonth += direction;
        if (currentMonth > 12) {
            currentMonth = 1;
            currentYear++;
        } else if (currentMonth < 1) {
            currentMonth = 12;
            currentYear--;
        }
        binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() + direction, true); // Swipe to next or previous month
    }


    private void updateMonthHeader() {
        String monthName = getMonthName(currentMonth);
        binding.monthTextView.setText(String.format(Locale.ENGLISH, "%s %d", monthName, currentYear));
    }


    private String getMonthName(int month) {
        DateFormatSymbols dfs = new DateFormatSymbols();
        return dfs.getMonths()[month - 1];
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();


        float leftSwipeThreshold = binding.leftArrow.getWidth();
        float rightSwipeThreshold = binding.viewPager.getWidth() - binding.rightArrow.getWidth();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                initialX = x;
                break;
            case MotionEvent.ACTION_MOVE:

                float deltaX = x - initialX;

                if (deltaX > 0 && x > leftSwipeThreshold) {

                    binding.viewPager.setUserInputEnabled(true);
                } else if (deltaX < 0 && x < rightSwipeThreshold) {

                    binding.viewPager.setUserInputEnabled(true);

                    binding.viewPager.setUserInputEnabled(false);
                }
                break;
        }

        return super.onTouchEvent(event);
    }


}