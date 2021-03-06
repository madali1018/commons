package com.mada.elasticsearch;

import com.mada.elasticsearch.entity.EsGroupByRangeRequestEntity;
import com.mada.elasticsearch.entity.EsGroupByRangeResponseEntity;
import com.mada.elasticsearch.impl.EsGroupByRangeSearchImpl;
import com.mada.elasticsearch.contarct.search.IEsGroupByRangeSearch;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by madali on 2019/3/12 14:43
 */
public class IEsGroupByRangeSearchTest extends BaseTest {

    @Test
    public void t1() {
        List<String> list = Arrays.asList("10-13", "13-15", "15-18", "18-20", "20-100");

        EsGroupByRangeRequestEntity request = EsGroupByRangeRequestEntity.builder()
                .indexName("groupby-test")
                .filter("id:1_4")
                .fieldName("age")
                .groupList(list)
                .needRealEsJson(true)
                .build();

        IEsGroupByRangeSearch iEsGroupByRangeSearch = new EsGroupByRangeSearchImpl();
        EsGroupByRangeResponseEntity response = iEsGroupByRangeSearch.groupByRangeFiledValue(request);
        System.out.println(response.getRealQueryJson());
        System.out.println(response.getMap());
    }
}

