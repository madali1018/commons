package annotationtest;

import java.lang.annotation.*;

/**
 * Created by madali on 2017/5/11.
 */

//自定义注解MethodAnnotation，要想使用反射读取注解，必须将Retention的值选为RUNTIME。

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)//注解作用于方法上
public @interface MethodAnnotation {

    String desc() default "method1";
}
