package com.mada.utils.obj.demo;

import com.mada.utils.obj.utils.ObjDeepCloneUtil;
import com.mada.utils.obj.entity.Coordinates;
import com.mada.utils.obj.entity.House;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by madali on 2019/4/29 20:21
 */
public class ObjectDeepCloneDemo {

    private static List<String> urlList = new ArrayList<>();
    private static Map<String, String> extend = new HashMap<>();

    static {
        urlList.add("http://127.0.0.1:8080/hi");
        urlList.add("http://127.0.0.1:8080/hi2");

        extend.put("params1", "111");
        extend.put("params2", "222");
    }

    private static House house = House.builder()
            .houseId(10001L)
            .isVip(false)
            .content("望京")
            .cityIdArr(new int[]{1, 2})
            .urlList(urlList)
            .extend(extend)
            .coordinates(Coordinates.builder().lon(117.30599).lat(31.750798).build())
            .build();

    @Test
    public void t1() {
        House house2 = (House) ObjDeepCloneUtil.byFastJson(house);
        house2.setContent("朝阳");

        House house3 = ObjDeepCloneUtil.byCglib(house);
        house3.setIsVip(true);

        System.out.println(house);
        System.out.println(house2);
        System.out.println(house3);
    }

}
