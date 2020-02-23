package com.techneapps.notestaking;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class UtilsTest {
    @Test
    public void date_correctDate_ReturnsTrue() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM YYYY, hh:mm a",
                Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 23);
        cal.set(Calendar.MONTH, Calendar.FEBRUARY);
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.HOUR_OF_DAY, 22);
        cal.set(Calendar.MINUTE, 30);

        String correctDate = "23 February 2020, 10:30 PM";
        assertEquals(simpleDateFormat.format(cal.getTime()), correctDate);
    }


}
