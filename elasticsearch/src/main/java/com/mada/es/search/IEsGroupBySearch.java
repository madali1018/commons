package com.mada.es.search;

import com.mada.es.entity.EsGroupByRequestEntity;
import com.mada.es.entity.EsGroupByResponseEntity;

/**
 * Created by madali on 2019/3/12 10:37
 */
public interface IEsGroupBySearch {

    /**
     * 1.group by 按字段的值进行分组，类似MySQL中的 SELECT sum(field) as sum_field from table group by field order by sum_field desc
     * 2.es中会使用：AggregationBuilders.sum("name").field("field"); 注意：字段类型必须是数值类型
     * 3.入参和返回值说明见EsGroupByRequestEntity和EsGroupByResponseEntity的说明
     */
    EsGroupByResponseEntity groupByFiledValue(EsGroupByRequestEntity request);

}
