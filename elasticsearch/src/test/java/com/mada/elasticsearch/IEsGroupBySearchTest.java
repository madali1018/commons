package com.mada.elasticsearch;

import com.mada.elasticsearch.entity.EsGroupByRequestEntity;
import com.mada.elasticsearch.entity.EsGroupByResponseEntity;
import com.mada.elasticsearch.impl.EsGroupBySearchImpl;
import com.mada.elasticsearch.contarct.search.IEsGroupBySearch;
import org.junit.Test;

/**
 * Created by madali on 2019/3/12 14:36
 */
public class IEsGroupBySearchTest extends BaseTest {

    @Test
    public void t1() {
        EsGroupByRequestEntity request = EsGroupByRequestEntity.builder()
                .indexName("groupby-test")
//                .filter("id:1_4")
                .fieldName("age")
                .requiredSize(20)
                .needRealEsJson(true)
                .build();

        IEsGroupBySearch iEsGroupBySearch = new EsGroupBySearchImpl();
        EsGroupByResponseEntity response = iEsGroupBySearch.groupByFiledValue(request);
        System.out.println(response.getRealQueryJson());
        System.out.println(response.getMap());

    }
}
