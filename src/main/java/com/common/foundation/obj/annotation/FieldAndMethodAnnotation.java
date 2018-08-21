package com.common.foundation.obj.annotation;

import java.lang.annotation.*;

/**
 * Created by madali on 2017/5/11.
 */

//自定义注解FieldAndMethodAnnotation，要想使用反射读取注解，必须将Retention的值选为RUNTIME。

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})//注解作用于类和字段上
public @interface FieldAndMethodAnnotation {

    String name() default "name";

    int age() default 20;

    String[] hobby();//没有指定default的，需要在注解的时候显式指定
}
