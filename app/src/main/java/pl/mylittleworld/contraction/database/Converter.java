package pl.mylittleworld.contraction.database;

import android.arch.persistence.room.TypeConverter;

import java.time.LocalTime;
import java.util.Date;

public class Converter {

    @TypeConverter
    public int localTimeToSeconds(LocalTime localTime) {
        return localTime.toSecondOfDay();
    }

    @TypeConverter
    public LocalTime secondsToLocalTime(int seconds){
        return LocalTime.ofSecondOfDay(seconds);
    }

    @TypeConverter
    public long dataToLong(Date date){
        return date.getTime();
    }

    @TypeConverter
    public Date longToDate(long millis){
        return new Date(millis);
    }
}