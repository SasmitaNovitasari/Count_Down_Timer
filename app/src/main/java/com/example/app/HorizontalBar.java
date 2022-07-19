package com.example.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.MissingResourceException;

public class HorizontalBar extends ProgressBar {

    private TypedArray typedArray;

    long progress = 0;
    String dateEnd, dateStart;
    String startDateFormat = "dd MM yyyy HH:mm:ss";
    String endDateFormat = "dd MM yyyy HH:mm:ss";

    public HorizontalBar(Context context) {
        super(context);
    }

    public HorizontalBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.HorizontalBar, 0, 0);
        init();
    }

    public HorizontalBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        if (typedArray.hasValue(R.styleable.HorizontalBar_end_date)) {
            dateEnd = typedArray.getString(R.styleable.HorizontalBar_end_date);

            if (typedArray.hasValue(R.styleable.HorizontalBar_start_date)) {
                dateStart = typedArray.getString(R.styleable.HorizontalBar_start_date);
            } else {
                dateStart = new SimpleDateFormat(startDateFormat).format(Calendar.getInstance().getTime());
            }

            long start = convertDateToMillis(dateStart, startDateFormat);
            long end = convertDateToMillis(dateEnd, endDateFormat);
            long millis = end - start;

            setMax(Math.toIntExact(millis));
            setProgress(Math.toIntExact(progress));
            countDownTimer();
        } else {
            throw new MissingResourceException("end_date attribute is required!", "", "");
        }

        if (typedArray.hasValue(R.styleable.HorizontalBar_start_date_format)) {
            startDateFormat = typedArray.getString(R.styleable.HorizontalBar_start_date_format);
        }
        if (typedArray.hasValue(R.styleable.HorizontalBar_end_date_format)) {
            endDateFormat = typedArray.getString(R.styleable.HorizontalBar_end_date_format);
        }
    }

    private void countDownTimer() {
        long start = convertDateToMillis(dateStart, startDateFormat);
        long end = convertDateToMillis(dateEnd, endDateFormat);
        long result = end - start;
        new android.os.CountDownTimer(result, 1000) {
            @Override
            public void onTick(long l) {
                progress += 1000;
                setProgress(Math.toIntExact(progress));
            }

            @Override
            public void onFinish() {
                onProgressFinish(progress);
            }
        }.start();
    }

    public long convertDateToMillis(String date, String format) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat(format).parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTimeInMillis();
    }


    // for user if want to override
    public void onProgressFinish(long progress) {
        setProgress(Math.toIntExact(progress));
    }

    public int getProgress() {
        return Math.toIntExact(progress);
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getStartDateFormat() {
        return startDateFormat;
    }

    public void setStartDateFormat(String startDateFormat) {
        this.startDateFormat = startDateFormat;
    }

    public String getEndDateFormat() {
        return endDateFormat;
    }

    public void setEndDateFormat(String endDateFormat) {
        this.endDateFormat = endDateFormat;
    }
}
