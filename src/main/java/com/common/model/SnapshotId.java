package com.common.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by madali on 2017/1/12.
 */
@Embeddable
//@Embeddable表示此类可以被插入到某个entity中
public class SnapshotId extends BaseModel {

    //使用联合主键（执行时间 + buId）

    //执行时间
    @Column(name = "execute_gmt0_datetime")
    private String executeGmt0Datetime;

    @Column(name = "bu_id")
    private String buId;

    public String getExecuteGmt0Datetime() {
        return executeGmt0Datetime;
    }

    public void setExecuteGmt0Datetime(String executeGmt0Datetime) {
        this.executeGmt0Datetime = executeGmt0Datetime;
    }

    public String getBuId() {
        return buId;
    }

    public void setBuId(String buId) {
        this.buId = buId;
    }
}
