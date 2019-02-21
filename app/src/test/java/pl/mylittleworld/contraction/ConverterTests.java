package pl.mylittleworld.contraction;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import pl.mylittleworld.contraction.database.Converter;

import static org.junit.Assert.*;

public class ConverterTests {

    private final Converter converter = new Converter();

    @Test
    public void conversionOfLocalTime() {

        LocalTime localTime1 = LocalTime.now();

        int temp = converter.localTimeToSeconds(localTime1);

        LocalTime localTime2 = converter.secondsToLocalTime(temp);

        assertEquals(localTime1.getHour(), localTime2.getHour());
        assertEquals(localTime1.getMinute(), localTime2.getMinute());
        assertEquals(localTime1.getSecond(), localTime2.getSecond());
    }

    @Test
    public void conversionOfLocalDate() {

        LocalDate localDate1 = LocalDate.now();

        long temp = converter.dataToLong(localDate1);

        LocalDate localDate2 = converter.longToDate(temp);

        assertEquals(localDate1, localDate2);

    }
}