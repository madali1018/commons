package demo.cglib.demo2;

import net.sf.cglib.proxy.FixedValue;

/**
 * 表示锁定方法返回值，无论被代理的方法返回什么值，回调方法都返回固定值
 * <p>
 * Created by madali on 2017/6/1.
 */
public class TargetResultFixed implements FixedValue {

    /**
     * 该类实现FixedValue接口，并锁定回调值为1000
     * （整型，因CallbackFilter的实现类TargetMethodCallbackFilter中定义的方法返回值为int）
     *
     * @return
     * @throws Exception
     */
    @Override
    public Object loadObject() throws Exception {
        System.out.println("锁定结果");
        return 1000;
    }
}
