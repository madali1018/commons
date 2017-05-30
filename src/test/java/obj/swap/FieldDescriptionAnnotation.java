package obj.swap;

import java.lang.annotation.*;

/**
 * Created by madl on 2017/5/17.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)//注解作用于字段上
public @interface FieldDescriptionAnnotation {

    //注解的fieldDescription值在使用时显式指定。PS：一个属性只能有0或1个FieldDescriptionAnnotation注解
    String fieldDescription();
}
