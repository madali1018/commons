package cglib.demo2;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

/**
 * 回调方法过滤
 * <p>
 * Created by madali on 2017/6/1.
 */
public class TargetMethodCallbackFilter implements CallbackFilter {

    /**
     * 过滤方法
     * 返回的值为数字，代表了要用到的Callback在Callback数组中索引的位置
     *
     * @param method
     * @return
     */
    @Override
    public int accept(Method method) {

        if ("method1".equals(method.getName())) {
            System.out.println("filter method1 == 0");
            return 0;
        } else if ("method2".equals(method.getName())) {
            System.out.println("filter method2 == 1");
            return 1;
        } else if ("method3".equals(method.getName())) {
            System.out.println("filter method3 == 2");
            return 2;
        }

        return 0;
    }
}
