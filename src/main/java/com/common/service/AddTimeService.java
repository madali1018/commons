package com.common.service;

import com.common.dao.AddTimeDao;
import com.common.model.AddTime;
import com.common.util.time.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by madl on 2017/4/27.
 */
@Service
public class AddTimeService {

    @Autowired
    private AddTimeDao addTimeDao;

    /**
     * 获取当前gmt0时间（加上了addTime表中添加的时间）
     *
     * @param currentGmt0Datetime 当前gmt0时间（未加上addTime表中添加的时间）
     * @return
     */
    public String getCurrentGmt0Datetime(String currentGmt0Datetime) {

        //当前gmt0时间  String类型 -- > OffsetDateTime类型
        OffsetDateTime currentGmt0OffsetDatetime = TimeUtil.gmtOffsetDateTime(0);

        //addTime表中配置的时间
        List<AddTime> addTimeList = addTimeDao.list();

        int addDays = 0;
        int addHours = 0;
        int addMinutes = 0;

        for (AddTime addTime : addTimeList) {

            if (addTime.getAddDay() != null) {

                addDays = addDays + addTime.getAddDay();
            }

            if (addTime.getAddHour() != null) {

                addHours = addHours + addTime.getAddHour();
            }

            if (addTime.getAddMinute() != null) {

                addMinutes = addMinutes + addTime.getAddMinute();
            }
        }

        //返回当前gmt0时间（包含了addTime表中配置的时间）
        return currentGmt0OffsetDatetime.plusDays(addDays).plusHours(addHours).plusMinutes(addMinutes).format(DateTimeFormatter.ofPattern(TimeUtil.DEFAULT_DATETIME_FORMAT));
    }
}
