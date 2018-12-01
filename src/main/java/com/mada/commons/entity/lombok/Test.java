package com.mada.commons.entity.lombok;

import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: madali
 * @Date: 2018/11/5 11:21
 */
@Slf4j
public class Test {

    @org.junit.Test
    public void t1() {
        LombokEntity lombokEntity = new LombokEntity("name", false, true, 0);
        System.out.println(lombokEntity);
        log.info("slf4j");
    }

    @org.junit.Test
    public void t2() {
        LombokEntity lombokEntity = new LombokEntity("name", false, true, 0);
        System.out.println(lombokEntity);
        System.out.println("---------");

        int num = lombokEntity.getNum();
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                num += 1;
            }
        }
        lombokEntity.setNum(num);

        System.out.println(lombokEntity);
    }

}
