package com.example.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class HorizontalBar extends ProgressBar {

    private TypedArray typedArray;
    private int visibility;
    private CountDownTimer countDownTimerProgress;

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
        try {
            dateEnd = typedArray.getString(R.styleable.HorizontalBar_end_date);
            dateStart = typedArray.getString(R.styleable.HorizontalBar_start_date);
            startDateFormat = typedArray.getString(R.styleable.HorizontalBar_start_date_format);
            endDateFormat = typedArray.getString(R.styleable.HorizontalBar_end_date_format);
            visibility = typedArray.getInt(R.styleable.HorizontalBar_finish_visibility, 1);
        } finally {
            typedArray.recycle();
        }
    }

    private void checkVariable() {
        if (getDateStart() == null) {
            @SuppressLint("SimpleDateFormat") String dateNow = new SimpleDateFormat(startDateFormat).format(Calendar.getInstance().getTime());
            setDateStart(dateNow);
        }
        if (getStartDateFormat() == null) {
            setStartDateFormat("dd MM yyyy HH:mm:ss");
        }
        if (getEndDateFormat() == null) {
            setEndDateFormat("dd MM yyyy HH:mm:ss");
        }
    }

    public void main() {
        checkVariable();
        if (getDateEnd() != null) {
            long start = convertDateToMillis(dateStart, startDateFormat);
            long end = convertDateToMillis(dateEnd, endDateFormat);
            long millis = end - start;

            setMax(Math.toIntExact(millis));
            setProgress(Math.toIntExact(progress));
            countDownTimer();
        } else {
            setMax(100);
            setProgress(0);
        }
    }

    private void countDownTimer() {
        long start = convertDateToMillis(dateStart, startDateFormat);
        long end = convertDateToMillis(dateEnd, endDateFormat);
        long result = end - start;
        countDownTimerProgress = new android.os.CountDownTimer(result, 1000) {
            @Override
            public void onTick(long l) {
                progress += 1000;
                setProgress(Math.toIntExact(progress));
            }

            @Override
            public void onFinish() {
                setMax(100);
                setProgress(100);

                switch (visibility) {
                    case 0:
                        setVisibility(INVISIBLE);
                        break;
                    case 1:
                        setVisibility(VISIBLE);
                        break;
                    case 2:
                        setVisibility(GONE);
                        break;
                }
            }
        };
    }

    public void startProgress() {
        main();
        countDownTimerProgress.start();
    }

    @SuppressLint("SimpleDateFormat")
    public long convertDateToMillis(String date, String format) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(Objects.requireNonNull(new SimpleDateFormat(format).parse(date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTimeInMillis();
    }


    /*
    getter and setter
     */
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
