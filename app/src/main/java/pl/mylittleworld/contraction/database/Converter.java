package pl.mylittleworld.contraction.database;

import android.arch.persistence.room.TypeConverter;

import java.time.LocalDate;
import java.time.LocalTime;

public class Converter {

    @TypeConverter
    public int localTimeToSeconds(LocalTime localTime) {
        return localTime.toSecondOfDay();
    }

    @TypeConverter
    public LocalTime secondsToLocalTime(int seconds) {
        return LocalTime.ofSecondOfDay(seconds);
    }

    @TypeConverter
    public long dataToLong(LocalDate date) {
        return date.toEpochDay();
    }

    @TypeConverter
    public LocalDate longToDate(long millis) {
        return LocalDate.ofEpochDay(millis);
    }
}