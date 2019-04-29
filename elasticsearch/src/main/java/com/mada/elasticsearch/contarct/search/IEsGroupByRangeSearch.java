package com.mada.elasticsearch.contarct.search;

import com.mada.elasticsearch.entity.EsGroupByRangeRequestEntity;
import com.mada.elasticsearch.entity.EsGroupByRangeResponseEntity;

/**
 * Created by madali on 2019/3/12 14:38
 */
public interface IEsGroupByRangeSearch {

    /**
     * 1.group by 按字段的值进行范围分组     参考：https://blog.csdn.net/molong1208/article/details/50589469
     * 2.与IEsGroupBySearch接口中groupByFiledValue区别在于：aggregation 构造方式不同；searchResponse获取返回结果方式不同
     * 3.入参和返回值说明见EsGroupRangeByRequestEntity和EsGroupByRangeResponseEntity的说明
     *
     * @param request
     * @return
     */
    EsGroupByRangeResponseEntity groupByRangeFiledValue(EsGroupByRangeRequestEntity request);

}
