/**
 * Created by madali on 2018/5/2.
 */
package com.mada.common.designpattern.adapter.interface_adapter;

//接口适配器（采用继承实现）

//RealA类需要实现接口A中的a1，a2，a3方法，但却不能修改接口A的定义。
//加一个中间类（抽象类AbstractA实现接口A，但只实现a1方法，a2，a3方法都是空实现）
//RealA类继承抽象类AbstractA，重写a1方法即可。
