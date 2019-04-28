package com.mada.es.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created by madali on 2019/3/12 10:37
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EsGroupByResponseEntity {

    /**
     * map示例：
     * 1.示例：如按id分组，100条数据 id为1的有30条，id为2的有40条，id3的有20条，id为4的有10条
     * 若EsGroupByRequestEntity中requiredSize为2，则EsGroupByResponseEntity的map只会返回 2 40，1 30
     * 若EsGroupByRequestEntity中requiredSize为3，则EsGroupByResponseEntity的map只会返回 2 40，1 30和3 20
     * 若EsGroupByRequestEntity中requiredSize为4，则EsGroupByResponseEntity的map会返回 2 40，1 30，3 20和1,10
     */
    private Map<String, Long> map;

    /**
     * 真正查询es时的请求json串
     */
    private String realQueryJson;

}
