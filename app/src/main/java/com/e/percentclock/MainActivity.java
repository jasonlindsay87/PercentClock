package com.e.percentclock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler();
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                TextView tvTheTimeIs = (TextView)findViewById(R.id.theTimeIs);
                TextView tvTime = (TextView) findViewById(R.id.tvTime);
                TextView tvMonth = (TextView) findViewById(R.id.tvMonth);
                TextView tvMonthName = (TextView) findViewById(R.id.tvMonthName);
                TextView tvYear = (TextView) findViewById(R.id.tvYear);
                TextView tvYearName = (TextView) findViewById(R.id.tvYearName);

                tvTheTimeIs.setText(String.format(sdf.format(new Date())));
                tvTime.setText(String.format("%.3f", getDayPercent()) + "%");
                tvMonthName.setText(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
                tvMonth.setText(String.format("%.2f", getMonthPercent()) + "%");
                tvYearName.setText(Integer.toString(calendar.get(Calendar.YEAR)));
                tvYear.setText(String.format("%.2f", getYearPercent()) + "%");

                handler.postDelayed(runnable, 1000);
            }
        }, 1000);

        super.onResume();
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    protected double getDayPercent() {
        Calendar calendar = Calendar.getInstance();
        int hrs = calendar.get(Calendar.HOUR_OF_DAY);
        int mins = calendar.get(Calendar.MINUTE);
        int secs = calendar.get(Calendar.SECOND);
        double elapsedSecsToday = ((hrs * 3600) + (mins * 60) + secs);
        double percent = (elapsedSecsToday / 86400) * 100;

        return percent;
    }


    protected double getMonthPercent() {
        Calendar calendar = Calendar.getInstance();
        double secondsInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) * 86400;
        double elapsedSecsToday = calendar.get(Calendar.HOUR_OF_DAY) * 3600 +  calendar.get(Calendar.MINUTE) * 60 + calendar.get(Calendar.SECOND);
        double elapsedSecondsThisMonth = calendar.get(Calendar.DAY_OF_MONTH) * 86400;
        double percent = ((elapsedSecondsThisMonth + elapsedSecsToday) / secondsInMonth) * 100;

        return percent;
    }

    protected double getYearPercent() {
        Calendar calendar = Calendar.getInstance();
        double secondsInYear = calendar.getActualMaximum(Calendar.DAY_OF_YEAR) * 86400;
        double elapsedSecondsInYear = calendar.get(Calendar.DAY_OF_YEAR) * 86400;
        double percent = (elapsedSecondsInYear / secondsInYear) * 100;

        return percent;
    }
}



