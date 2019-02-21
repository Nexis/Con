package pl.mylittleworld.contraction;

import android.content.Context;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import pl.mylittleworld.contraction.database.Contraction;

public class ContractionListAdapterTest {

    @Test
    public void isNextDateTest(){
        LocalDate localDate1=LocalDate.of(1996,2,1);
        LocalDate localDate2=LocalDate.of(1996,2,2);

        LocalDate localDate3=LocalDate.of(1999,12,31);
        LocalDate localDate4=LocalDate.of(2000,1,1);

        LocalDate localDate5=LocalDate.of(2001,12,31);
        LocalDate localDate6=LocalDate.of(2000,1,2);

        Assert.assertTrue(ContractionListAdapter.isNextDate(localDate1,localDate2));
        Assert.assertFalse(ContractionListAdapter.isNextDate(localDate2,localDate1));

        Assert.assertTrue(ContractionListAdapter.isNextDate(localDate3,localDate4));
        Assert.assertFalse(ContractionListAdapter.isNextDate(localDate4,localDate3));

        Assert.assertFalse(ContractionListAdapter.isNextDate(localDate5,localDate6));
        Assert.assertFalse(ContractionListAdapter.isNextDate(localDate6,localDate5));

    }
}
