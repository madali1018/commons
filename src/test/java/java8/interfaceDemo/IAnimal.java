package java8.interfaceDemo;

/**
 * @Auther: madali
 * @Date: 2018/8/15 15:54
 */
public interface IAnimal {

    void m1();

    default String name() {
        return "default_name";
    }

}
