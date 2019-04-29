package com.mada.elasticsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by madali on 2019/3/12 14:39
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EsGroupByRangeRequestEntity {

    /**
     * 索引名称：非空。若为空则返回一个空的EsGroupByRangeResponseEntity对象。
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
     * 需要group by的文档个数
     * 说明：
     * 1.按照query filter查询出的数据可能有十万条，但只返回前100条，在前100条里根据分组条件，计算每个分组下的数据个数。
     * 2.该字段默认值为100，最大值为3500
     */
    private int groupByNum;

    /**
     * 分组list
     * 说明：
     * 1.如字段a需要按1_10,10_20,20_30分组，则groupList中的三个元素应为 "1-10","10-20","20-30"
     * 2.要分组的字段的值必须是数值类型，其他类型es该版本暂不支持聚合。
     * 3.分组时会使用到RangeBuilder的addRange方法，该方法会将1和10转成double来执行。
     */
    private List<String> groupList;

    /**
     * 是否需要返回接口真正查询es时的json串。
     * 因接口返回值json串比较大，建议只在排查接口问题时传true，线上环境不传该值或传false。
     */
    private boolean needRealEsJson;

}
