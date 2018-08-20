package java8.interfaceDemo;

/**
 * @Auther: madali
 * @Date: 2018/8/15 15:58
 */
public class CatImpl implements IAnimal {

    @Override
    public void m1() {
        System.out.println("实现类中重写了接口方法.");
    }

    /**
     * 实现类中没有重写接口方法，则该方法调用时调用的是接口中的default name()方法。
     *
     * @param args
     */
//    @Override
//    public String name() {
//        return "cat";
//    }

    public static void main(String[] args) {
        CatImpl cat = new CatImpl();
        cat.m1();
        System.out.println(cat.name());
    }

}
