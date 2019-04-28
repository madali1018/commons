package com.mada.commons.demo.staticdemo.pack2;

/**
 * Created by madali on 2018/4/6.
 */
public class Test extends Base {
    static{
        System.out.println("test static");
    }

    public Test(){
        System.out.println("test constructor");
    }

    public static void main(String[] args) {
        new Test();
    }
}

class Base{

    static{
        System.out.println("base static");
    }

    public Base(){
        System.out.println("base constructor");
    }
}
