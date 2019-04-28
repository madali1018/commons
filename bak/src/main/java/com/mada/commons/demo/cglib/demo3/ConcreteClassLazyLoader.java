package com.mada.commons.demo.cglib.demo3;

import net.sf.cglib.proxy.LazyLoader;

/**
* Created by madali on 2017/6/1.
*/
public class ConcreteClassLazyLoader implements LazyLoader {

    @Override
    public Object loadObject() throws Exception {
        System.out.println("Before lazyloader....");

        PropertyBean propertyBean = new PropertyBean();
        propertyBean.setKey("sdsfg");
        propertyBean.setValue(new User("username", "pwd"));

        System.out.println("After lazyloader....");

        return propertyBean;
    }
}
