package pl.mylittleworld.contraction;


import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

class Contraction {

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
        stop = LocalTime.now();
        duration = Duration.between(start, stop);
    }

    public Date getDate() {
        return date;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getStop() {
        return stop;
    }

    public Duration getDuration() {
        return duration;
    }


}
