package time;

import com.common.util.time.TimeUtil2;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by madali on 2017/12/31.
 */
public class TimeDemo {

    public static void main(String[] args) {

//        Long time = System.currentTimeMillis();
        Long time = 1514805300000L;
        System.out.println(time);

        System.out.println(TimeUtil2.isLegal(1514805297L));
        System.out.println(TimeUtil2.isLegal(1514805300000L));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        System.out.println(calendar.getTime());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.format(calendar.getTime()));
    }

}