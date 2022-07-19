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
import java.util.MissingResourceException;

@SuppressLint("AppCompatCustomView")
public class Tick extends TextView {

    private TypedArray typedArray;
    private long millisLeft;
    private boolean running = false;

    android.os.CountDownTimer countDownTimer;
    String dateStart, dateEnd;
    String startDateFormat = "dd MM yyyy HH:mm:ss";
    String endDateFormat = "dd MM yyyy HH:mm:ss";
    String finishText;
    boolean zeroNumber;

    String labelDay = " Days ";
    String labelHour = " Hours ";
    String labelMinute = " Minutes ";
    String labelSecond = " Seconds";

    public Tick(Context context) {
        super(context);
    }

    public Tick(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CountDownTimer, 0, 0);
        initialize();
    }

    public Tick(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initialize() {
        zeroNumber = typedArray.getBoolean(R.styleable.CountDownTimer_zero_number, true);
        if (typedArray.hasValue(R.styleable.CountDownTimer_end_date)) {
            dateEnd = typedArray.getString(R.styleable.CountDownTimer_end_date);

            if (typedArray.hasValue(R.styleable.CountDownTimer_start_date)) {
                dateStart = typedArray.getString(R.styleable.CountDownTimer_start_date);
            } else {
                dateStart = new SimpleDateFormat(startDateFormat).format(Calendar.getInstance().getTime());
            }

            long start = convertDateToMillis(dateStart, startDateFormat);
            long end = convertDateToMillis(dateEnd, endDateFormat);
            long l = end - start;
            setText(output(l));
        } else {
            throw new MissingResourceException("end_date attribute is required!", "", "");
        }

        if (typedArray.hasValue(R.styleable.CountDownTimer_start_date_format)) {
            startDateFormat = typedArray.getString(R.styleable.CountDownTimer_start_date_format);
        }
        if (typedArray.hasValue(R.styleable.CountDownTimer_end_date_format)) {
            endDateFormat = typedArray.getString(R.styleable.CountDownTimer_end_date_format);
        }

        if (typedArray.hasValue(R.styleable.CountDownTimer_label_day)) {
            labelDay = typedArray.getString(R.styleable.CountDownTimer_label_day);
        }
        if (typedArray.hasValue(R.styleable.CountDownTimer_label_hour)) {
            labelHour = typedArray.getString(R.styleable.CountDownTimer_label_hour);
        }
        if (typedArray.hasValue(R.styleable.CountDownTimer_label_day)) {
            labelMinute = typedArray.getString(R.styleable.CountDownTimer_label_minute);
        }
        if (typedArray.hasValue(R.styleable.CountDownTimer_label_second)) {
            labelSecond = typedArray.getString(R.styleable.CountDownTimer_label_second);
        }
    }

    private void countDownTimer(boolean afterPaused) {
        long result = 0;
        if (afterPaused) {
            result = millisLeft;
        } else {
            long start = convertDateToMillis(dateStart, startDateFormat);
            long end = convertDateToMillis(dateEnd, endDateFormat);
            result = end - start;
        }
        countDownTimer = new android.os.CountDownTimer(result, 1000) {

            @Override
            public void onTick(long l) {
                running = true;
                millisLeft = l;
                setText(output(l));
            }

            @Override
            public void onFinish() {
                if (typedArray.hasValue(R.styleable.CountDownTimer_finish_text)) {
                    finishText = typedArray.getString(R.styleable.CountDownTimer_finish_text);
                } else {
                    finishText = "Complete!";
                }
                setText(finishText);
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

        String output = "";
        if (zeroNumber) {
            output = String.valueOf(day) + labelDay +
                    String.valueOf(hour) + labelHour +
                    String.valueOf(minute) + labelMinute +
                    String.valueOf(second) + labelSecond;
        } else {
            if (day == 0) {
                output = String.valueOf(hour) + labelHour +
                        String.valueOf(minute) + labelMinute +
                        String.valueOf(second) + labelSecond;
                if (hour == 0) {
                    output = String.valueOf(minute) + labelMinute +
                            String.valueOf(second) + labelSecond;
                    if (minute == 0) {
                        output = String.valueOf(second) + labelSecond;
                    }
                }
            }
        }
        return output;
    }

    private long convertDateToMillis(String date, String format) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat(format).parse(date));
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
