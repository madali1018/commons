package com.mada.springdemo.entity.hibernate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by madali on 2017/4/27.
 */
//@Entity
//@Table(name = "add_time")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

}
