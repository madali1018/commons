package com.mada.commons.demo.cglib.demo3;

import net.sf.cglib.proxy.Enhancer;

/**
 * Created by madali on 2017/6/1.
 */
public class LazyBean {

    private String name;
    private int age;

    private PropertyBean propertyBean;
    private PropertyBean propertyBeanDispatcher;

    public LazyBean(String name, int age) {
        System.out.println("lazy bean init");
        this.name = name;
        this.age = age;
        this.propertyBean = createPropertyBean();
        this.propertyBeanDispatcher = createPropertyBeanDispatcher();
    }

    //只第一次懒加载
    private PropertyBean createPropertyBean() {
        /**
         * 使用CGLIB进行懒加载，对需要延迟加载的对象添加代理，在获取该对象属性时先通过代理类回调方法进行对象初始化。
         * 在不需要加载该对象时，只要不去获取该对象内属性，该对象就不会被初始化了
         * （在CGLIB的实现中只要去访问该对象内属性的getter方法，就会自动触发代理类回调）
         */
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PropertyBean.class);
        return (PropertyBean) enhancer.create(PropertyBean.class, new ConcreteClassLazyLoader());
    }

    //每次都懒加载
    private PropertyBean createPropertyBeanDispatcher() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PropertyBean.class);
        return (PropertyBean) enhancer.create(PropertyBean.class, new ConcreteClassDispatcher());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public PropertyBean getPropertyBean() {
        return propertyBean;
    }

    public void setPropertyBean(PropertyBean propertyBean) {
        this.propertyBean = propertyBean;
    }

    public PropertyBean getPropertyBeanDispatcher() {
        return propertyBeanDispatcher;
    }

    public void setPropertyBeanDispatcher(PropertyBean propertyBeanDispatcher) {
        this.propertyBeanDispatcher = propertyBeanDispatcher;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LazyBean{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", propertyBean=").append(propertyBean);
        sb.append(", propertyBeanDispatcher=").append(propertyBeanDispatcher);
        sb.append('}');
        return sb.toString().replace("'null'", "null");
    }
}
