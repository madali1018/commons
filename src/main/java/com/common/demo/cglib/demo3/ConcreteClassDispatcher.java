package com.common.demo.cglib.demo3;

import net.sf.cglib.proxy.Dispatcher;

/**
 * Created by madali on 2017/6/1.
 */
public class ConcreteClassDispatcher implements Dispatcher {

    @Override
    public Object loadObject() throws Exception {

        System.out.println("Before Dispatcher....");

        PropertyBean propertyBean = new PropertyBean();
        propertyBean.setKey("xxx");
        propertyBean.setValue(new User("usernameDispatcher", "pwdDispatcher"));

        System.out.println("After Dispatcher....");

        return propertyBean;
    }
}
