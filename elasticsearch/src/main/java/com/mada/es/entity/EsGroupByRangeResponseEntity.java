package com.mada.es.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created by madali on 2019/3/12 14:40
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EsGroupByRangeResponseEntity {

    /**
     * map的key为分组条件（对应EsGroupByRequestEntity中的groupList），value为该分组条件下的文档数目
     * 如：key 1-10,10-20,20-30   value  10 5 20
     */
    private Map<String, Long> map;

    /**
     * 真正查询es时的请求json串
     */
    private String realQueryJson;

}
