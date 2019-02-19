package pl.mylittleworld.contraction.database;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Contraction {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private LocalDate date;
    private LocalTime start;
    private LocalTime stop;


    public Contraction() {
        date = LocalDate.now();
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

    @NonNull
    public LocalDate getDate() {
        return date;
    }


    public LocalTime getStart() {
        if (start == null) {
            throw new RuntimeException("Try to access not initialized variable");
        } else {
            return start;
        }
    }

    public LocalTime getStop() {
        if (stop == null) {
            throw new RuntimeException("Try to access not initialized variable");
        } else {
            return stop;
        }
    }

    public Duration getDuration() {
        if (start != null && stop != null) {
            return Duration.between(start, stop);

        } else {
            throw new RuntimeException("Try to access when start or stop variable is not initialized");
        }
    }

    public void setDate(@NonNull LocalDate date) {
        this.date = date;
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
