package com.mada.springdemo.entity.hibernate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by madali on 2017/1/12.
 */
@Embeddable // @Embeddable表示此类可以被插入到某个entity中
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnapshotId extends BaseModel {

    //使用联合主键（执行时间 + buId）

    //执行时间
    @Column(name = "execute_gmt0_datetime")
    private String executeGmt0Datetime;

    @Column(name = "bu_id")
    private String buId;

}
