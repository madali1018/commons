package com.mada.utils.time;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Created by madali on 2019/5/5 11:26
 */
public class TimeDemo {

    // Date和LocalDate互转
    @Test
    public void t1() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println("dateToLocalDate:" + localDate);

        // LocalDate转Date
        LocalDate localDate2 = LocalDate.now();
        ZonedDateTime zonedDateTime = localDate2.atStartOfDay(ZoneId.systemDefault());
        Date date2 = Date.from(zonedDateTime.toInstant());
        System.out.println("localDateToDate:" + date2);
    }

    @Test
    public void t2() {
        String currentTimeStr = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
        System.out.println(currentTimeStr);
    }

}
