package demo;

import demo.BaseTestingEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: madali
 * @Date: 2018/8/21 15:08
 */
public class ObjectReferenceDemo1 {

    public static void main(String[] args) {

        BaseTestingEntity entity = new BaseTestingEntity();
        entity.setAge(1);
        System.out.println("entity1:" + entity);

        buildEntity(entity);
        System.out.println("entity2:" + entity);
    }

    private static void buildEntity(BaseTestingEntity entity) {

        List<String> strList = new ArrayList<>();
        strList.add("str1");
        // entity对象中的list变量的引用指向的是strList
        entity.setList(strList);
        System.out.println("entity.getList:" + entity.getList());

        strList.add("str2");
        // strList中添加新元素后，entity对象中的list变量的引用不变，但list变量的值更新了，故而此时entity对象中的list变量的值也更新了。
        System.out.println("strList添加新元素后, entity.getList:" + entity.getList());
    }

}
