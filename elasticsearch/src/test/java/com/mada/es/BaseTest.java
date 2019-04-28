package com.mada.es;

import com.mada.es.client.EsClient;
import org.junit.Before;

/**
 * Created by madali on 2019/1/16 16:44
 */
public class BaseTest {

    @Before
    public void before() {
        EsClient.getInstance().initEs(Constants.ES_HOSTS);
    }

}
