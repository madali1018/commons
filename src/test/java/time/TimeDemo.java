package time;

import java.text.SimpleDateFormat;

/**
 * Created by madali on 2017/12/31.
 */
public class TimeDemo {

    public static void main(String[] args) {

        Long currentTime = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String currentTimeStr = simpleDateFormat.format(currentTime);
        System.out.println(currentTimeStr);

        Long time = 1514993005000L;
        String timeStr = simpleDateFormat.format(time);
        System.out.println(timeStr);
    }

}