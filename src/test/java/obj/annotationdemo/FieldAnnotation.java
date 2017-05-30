package obj.annotationdemo;

import java.lang.annotation.*;

/**
 * Created by madl on 2017/5/11.
 */

//自定义注解FieldTypeAnnotation，要想使用反射读取注解，必须将Retention的值选为RUNTIME。

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})//注解作用于类和字段上
public @interface FieldAnnotation {

    String name() default "name";

    int age() default 20;

    String[] hobby();//没有指定default的，需要在注解的时候显式指定
}
