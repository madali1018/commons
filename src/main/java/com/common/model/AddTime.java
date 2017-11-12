package com.common.model;

import javax.persistence.*;

/**
 * Created by madali on 2017/4/27.
 */
@Entity
@Table(name = "add_time")
public class AddTime extends BaseModel {

    @Id
    //主键自动生成，无需赋值
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "add_day")
    private Integer addDay;

    @Column(name = "add_hour")
    private Integer addHour;

    @Column(name = "add_minute")
    private Integer addMinute;

    public Integer getAddDay() {
        return addDay;
    }

    public Integer getAddHour() {
        return addHour;
    }

    public Integer getAddMinute() {
        return addMinute;
    }

    @Override
    public String toString() {
        return "AddTime{" +
                "id=" + id +
                ", addDay=" + addDay +
                ", addHour=" + addHour +
                ", addMinute=" + addMinute +
                '}';
    }
}
