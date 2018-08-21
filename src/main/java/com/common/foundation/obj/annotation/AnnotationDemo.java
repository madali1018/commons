package com.common.foundation.obj.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by madali on 2017/5/11.
 */
@FieldAndMethodAnnotation(name = "class", hobby = {"smoke"})
public class AnnotationDemo {

    @FieldAndMethodAnnotation(hobby = {"sleep", "play"})
    private String s1;

    @FieldAndMethodAnnotation(hobby = {"sleep", "play", "buy"}, age = 30, name = "s2")
    private String s2;

    @FieldAndMethodAnnotation(hobby = {"a1", "a2", "a3"})
    private String s3;

    @MethodAnnotation
    private void m1() {
    }

    @MethodAnnotation(desc = "这是method2")
    private void m2() {
    }

    public static void main(String[] args) {

        Class<AnnotationDemo> cls = AnnotationDemo.class;

        //类上是否有注解
        boolean clsHasAnnotation = cls.isAnnotationPresent(FieldAndMethodAnnotation.class);
        if (clsHasAnnotation) {
            //获取类上的注解
            FieldAndMethodAnnotation clsAnnotation = cls.getAnnotation(FieldAndMethodAnnotation.class);
            //输出注解上的属性
            int age = clsAnnotation.age();
            String[] hobby = clsAnnotation.hobby();
            String name = clsAnnotation.name();
            System.out.println("ClassName: " + cls.getName() + ", age: " + age + ", hobby: " + Arrays.asList(hobby).toString() + ", name: " + name);
        }

        //字段上是否有注解
        // getDeclaredFields返回类所声明的所有字段，但不包含父类的。getFields则会返回包含父类的所有public字段
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {

            field.setAccessible(true);

            boolean filedHasAnnotation = field.isAnnotationPresent(FieldAndMethodAnnotation.class);
            if (filedHasAnnotation) {
                //获取字段上的注解
                FieldAndMethodAnnotation fieldAnnotation = field.getAnnotation(FieldAndMethodAnnotation.class);
                //输出注解上的属性
                int age = fieldAnnotation.age();
                String[] hobby = fieldAnnotation.hobby();
                String name = fieldAnnotation.name();
                System.out.println("FieldName: " + field.getName() + ", age: " + age + ", hobby: " + Arrays.asList(hobby).toString() + ", name: " + name);
            }
        }

        //方法上是否有注解
        // getDeclaredMethods返回类所声明的所有方法，但不包含父类的。getMethods则会返回包含父类的所有public方法
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            boolean methodHasAnnotation = method.isAnnotationPresent(MethodAnnotation.class);
            if (methodHasAnnotation) {
                //获取方法上的注解
                MethodAnnotation methodAnnotation = method.getAnnotation(MethodAnnotation.class);
                //输出注解上的属性
                String desc = methodAnnotation.desc();
                System.out.println("MethodName: " + method.getName() + ", desc: " + desc);
            }
        }
    }
}
