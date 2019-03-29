package com.mada.commons.util.mysql;

import org.junit.Test;

import java.util.List;

/**
 * Created by madali on 2019/3/29 11:06
 */
public class Demo {

    @Test
    public void t1() {
        WmbRecordEntity entity = WmbRecordEntity.builder().infoId(1).houseId(1).build();
        DbUtil.insertRecord(entity);
    }

    @Test
    public void t2() {
        List<WmbRecordEntity> list = DbUtil.getRecordList();
        list.stream().forEach(System.out::println);
    }

    @Test
    public void t3() {
        DbUtil.deleteRecord(3);
    }

}
