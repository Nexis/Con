package pl.mylittleworld.contraction;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

class Contraction {

    @NonNull
    private final Date date;
    private LocalTime start;
    private LocalTime stop;
    private Duration duration;

    private static DateTimeFormatter contractionDurationTimeFormat = DateTimeFormatter.ofPattern("mm : ss");

    public Contraction() {
        date = Calendar.getInstance().getTime();
    }

    public void startContraction() {
        start = LocalTime.now();
    }

    public void stopContraction() {
        if (start == null) {
            throw new IllegalStateException("Trying to stop contraction which is already not started");
        }
        stop = LocalTime.now();
        duration = Duration.between(start, stop);
    }

    public Date getDate() {
        return date;
    }

    @Nullable
    public LocalTime getStart() {
        return start;
    }

    @Nullable
    public LocalTime getStop() {
        return stop;
    }

    @Nullable
    public Duration getDuration() {
        return duration;
    }


}
