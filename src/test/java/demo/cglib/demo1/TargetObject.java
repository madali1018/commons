package demo.cglib.demo1;

/**
 * 没有实现接口，需要CGLIB动态代理的目标类
 * <p>
 * Created by madali on 2017/6/1.
 */
public class TargetObject {

    public String method1(String str) {
        System.out.println("-----------------------" + str);
        return str;
    }

    public int method2(int count) {
        System.out.println("-----------------------" + count);
        return count;
    }

    public int method3(int count) {
        System.out.println("-----------------------" + count);
        return count;
    }

    @Override
    public String toString() {
        return "TargetObject: " + getClass();
    }
}
