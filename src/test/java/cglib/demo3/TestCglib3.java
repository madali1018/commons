package cglib.demo3;

/**
 * Created by madali on 2017/6/1.
 */
public class TestCglib3 {

    public static void main(String[] args) {

        PropertyBean propertyBean = new PropertyBean("key1", "value1");
        PropertyBean propertyBeanDispatcher = new PropertyBean("keyDispatcher", "valueDispatcher");

        LazyBean lazyBean = new LazyBean("name1", 10, propertyBean, propertyBeanDispatcher);

        System.out.println(lazyBean);
        System.out.println("-----------------------------------------------------------");

        System.out.println(lazyBean.getName());
        System.out.println(lazyBean.getPropertyBean().getKey());
        System.out.println(lazyBean.getPropertyBean().getValue());
        System.out.println("-----------------------------------------------------------");

        System.out.println(lazyBean.getPropertyBeanDispatcher().getKey());
        System.out.println(lazyBean.getPropertyBeanDispatcher().getValue());
    }
}
