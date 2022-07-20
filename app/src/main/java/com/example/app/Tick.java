package com.example.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

@SuppressLint("AppCompatCustomView")
public class Tick extends TextView {

    private TypedArray typedArray;
    private boolean running = false;

    android.os.CountDownTimer countDownTimer;
    long millisLeft;
    String dateStart, dateEnd;
    String startDateFormat;
    String endDateFormat;
    String finishText;
    boolean zeroNumber;

    String labelDay;
    String labelHour;
    String labelMinute;
    String labelSecond;

    public Tick(Context context) {
        super(context);
    }

    public Tick(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CountDownTimer, 0, 0);
        init();
        main();
    }

    public Tick(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        try {
            zeroNumber = typedArray.getBoolean(R.styleable.CountDownTimer_zero_number, true);
            dateEnd = typedArray.getString(R.styleable.CountDownTimer_end_date);
            dateStart = typedArray.getString(R.styleable.CountDownTimer_start_date);
            startDateFormat = typedArray.getString(R.styleable.CountDownTimer_start_date_format);
            endDateFormat = typedArray.getString(R.styleable.CountDownTimer_end_date_format);
            labelDay = typedArray.getString(R.styleable.CountDownTimer_label_day);
            labelHour = typedArray.getString(R.styleable.CountDownTimer_label_hour);
            labelMinute = typedArray.getString(R.styleable.CountDownTimer_label_minute);
            labelSecond = typedArray.getString(R.styleable.CountDownTimer_label_second);
            finishText = typedArray.getString(R.styleable.CountDownTimer_finish_text);
        } finally {
            typedArray.recycle();
        }

        if (getDateStart() == null) {
            @SuppressLint("SimpleDateFormat") String dateNow = new SimpleDateFormat("dd MM yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
            setDateStart(dateNow);
        }
        if (getStartDateFormat() == null) {
            setStartDateFormat("dd MM yyyy HH:mm:ss");
        }
        if (getEndDateFormat() == null) {
            setEndDateFormat("dd MM yyyy HH:mm:ss");
        }
        if (getFinishText() == null) {
            setText("Complete!");
        }
        if (getLabelDay() == null) {
            setLabelDay(" Days ");
        }
        if (getLabelHour() == null) {
            setLabelHour(" Hours ");
        }
        if (getLabelMinute() == null) {
            setLabelMinute(" Minutes ");
        }
        if (getLabelSecond() == null) {
            setLabelSecond(" Seconds ");
        }
    }

    private long main() {
        if (getDateEnd() != null) {
            long start = convertDateToMillis(getDateStart(), getEndDateFormat());
            long end = convertDateToMillis(getDateEnd(), getStartDateFormat());
            setText(output(end - start));
            return end - start;
        } else {
            return 1000;
        }
    }

    static long result;
    private void countDownTimer(boolean afterPaused) {
        if (afterPaused) {
            setMillisLeft(result);
        } else {
            result = main();
        }
        countDownTimer = new android.os.CountDownTimer(result, 1000) {

            @Override
            public void onTick(long l) {
                running = true;
                setMillisLeft(l);
                setText(output(l));
            }

            @Override
            public void onFinish() {
                setText(getFinishText());
            }
        };
    }

    private String output(long millis) {

        int day = Math.toIntExact(millis / 86400000);
        int sisa = Math.toIntExact(millis % 86400000);

        int hour = Math.toIntExact(sisa / 3600000);
        int sisa1 = Math.toIntExact(sisa % 3600000);

        int minute = Math.toIntExact(sisa1 / 60000);
        int sisa2 = Math.toIntExact(sisa1 % 60000);

        int second = Math.toIntExact(sisa2 / 1000);

        String output;

        output = day + labelDay +
                hour + labelHour +
                minute + labelMinute +
                second + labelSecond;

        if (!isZeroNumber()) {
            if (day == 0) {
                output = hour + labelHour +
                        minute + labelMinute +
                        second + labelSecond;
                if (hour == 0) {
                    output = minute + labelMinute +
                            second + labelSecond;
                    if (minute == 0) {
                        output = second + labelSecond;
                    }
                }
            }
        }
        return output;
    }

    @SuppressLint("SimpleDateFormat")
    private long convertDateToMillis(String date, String format) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(Objects.requireNonNull(new SimpleDateFormat(format).parse(date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTimeInMillis();
    }



    public void start() {
        if (running) {
            countDownTimer.cancel();
        }
        countDownTimer(false);
        countDownTimer.start();
    }

    public void stop() {
        if (running) {
            countDownTimer.cancel();
        }
    }

    public void resume() {
        if (running) {
            countDownTimer.cancel();
        } else {
            countDownTimer(true);
            countDownTimer.start();
        }
    }

    public void reset() {
        if (running) {
            countDownTimer.cancel();
        }
        countDownTimer(false);

        long start = convertDateToMillis(dateStart, startDateFormat);
        long end = convertDateToMillis(dateEnd, endDateFormat);
        long l = end - start;

        setText(output(l));
    }


    /*
    Getter and Setter
     */

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
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

    public String getFinishText() {
        return finishText;
    }

    public void setFinishText(String finishText) {
        this.finishText = finishText;
    }

    public boolean isZeroNumber() {
        return zeroNumber;
    }

    public void setZeroNumber(boolean zeroNumber) {
        this.zeroNumber = zeroNumber;
    }

    public String getLabelDay() {
        return labelDay;
    }

    public void setLabelDay(String labelDay) {
        this.labelDay = labelDay;
    }

    public String getLabelHour() {
        return labelHour;
    }

    public void setLabelHour(String labelHour) {
        this.labelHour = labelHour;
    }

    public String getLabelMinute() {
        return labelMinute;
    }

    public void setLabelMinute(String labelMinute) {
        this.labelMinute = labelMinute;
    }

    public String getLabelSecond() {
        return labelSecond;
    }

    public void setLabelSecond(String labelSecond) {
        this.labelSecond = labelSecond;
    }

    public long getMillisLeft() {
        return millisLeft;
    }

    public void setMillisLeft(long millisLeft) {
        this.millisLeft = millisLeft;
    }

}
