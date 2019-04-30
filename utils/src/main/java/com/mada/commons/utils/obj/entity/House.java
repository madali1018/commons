package com.mada.commons.utils.obj.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Created by madali on 2019/4/29 20:16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class House {

    private long houseId;

    private Boolean isVip;

    private String content;

    // 1-北京 2-上海 3-深圳
    private int[] cityIdArr;

    private List<String> urlList;

    // 扩展字段
    private Map<String, String> extend;

    private Coordinates coordinates;

}
