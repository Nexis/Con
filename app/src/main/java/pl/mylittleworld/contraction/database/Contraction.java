package pl.mylittleworld.contraction.database;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Contraction {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private Date date;
    private LocalTime start;
    private LocalTime stop;


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

    public Duration getDuration() {
        if (start != null && stop != null) {
            return Duration.between(start, stop);

        } else {
            return null;
        }
    }

    public void setDate(Date date){
        this.date=date;
    }
    public void setStart(LocalTime start) {
        this.start = start;
    }

    public void setStop(LocalTime stop) {
        this.stop = stop;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
