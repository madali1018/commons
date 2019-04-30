package com.mada.commons.utils.mysql;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Created by madali on 2019/3/20 16:59
 */
@Data
@Builder
public class WmbRecordEntity {

    private int id;

    private long infoId;

    private long houseId;

    private Date updateTime;

    // 扩展字段：写入json字符串，使用时再将json字符串转成json。避开MySQL的JSON类型只能使用5.7以上版本的限制。
    private String extend;

}