package com.mada.es.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by madali on 2019/3/12 10:37
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EsGroupByRequestEntity {

    /**
     * 索引名称：非空。若为空则返回一个空的EsGroupByResponseEntity对象
     */
    private String indexName;

    /**
     * 模糊查询：a:*123     即查询a值里面包含123
     */
    private String query;

    /**
     * 过滤条件：可以为空，支持如下格式，以逗号分开
     * 精准查询：a:123      即查询a=123
     * 精准查询：-a:123     即查询a!=123
     * 多条件查询：a:1|2|3  即查询a in (1,2,3)
     * 多条件查询：-a:1|2|3 即查询a not in (1,2,3)
     * 范围查询：
     * a:10_100 即查询 a大于等于10小于等于100
     * a:10_100|200_300 即查询 a大于等于10小于等于100 或者 a大于等于200小于等于300
     * <p>
     * 完整示例      -id:1,age:10_100,price:100|101|103|105,a:10_100|200_300
     * 表示查询-id为1并且age在10_100之间并且price在100,101,103,105之间
     * 并且a大于等于10小于等于100 或者 a大于等于200小于等于300的数据
     */
    private String filter;

    /**
     * 分组的字段名
     */
    private String fieldName;

    /**
     * 说明：
     * 1.按照query filter查询出的数据，进行group by后可能有十万条，但只返回前requiredSize个分组，且按照每个分组的大小降序排序
     * 2.该字段默认值为10，最大值为3500
     * 3.示例：如按id分组，100条数据 id为1的有30条，id为2的有40条，id3的有20条，id为4的有10条
     * 若requiredSize为2，则EsGroupByResponseEntity的map只会返回 2 40，1 30
     * 若requiredSize为3，则EsGroupByResponseEntity的map只会返回 2 40，1 30和3 20
     * 若requiredSize为4，则EsGroupByResponseEntity的map会返回 2 40，1 30，3 20和1,10
     */
    private int requiredSize;

    /**
     * 是否需要返回接口真正查询es时的json串。
     * 因接口返回值json串比较大，建议只在排查接口问题时传true，线上环境不传该值或传false。
     */
    private boolean needRealEsJson;

}
