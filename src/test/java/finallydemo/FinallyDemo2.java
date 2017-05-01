package finallydemo;

/**
 * Created by madl on 2017/5/1.
 * <p>
 * finally块中的return语句会覆盖try块中的return返回。
 */
public class FinallyDemo2 {

    public static void main(String[] args) {
        System.out.println(test2());
    }

    public static int test2() {
        int b = 20;

        try {
            System.out.println("try block");

            return b += 80;
        } catch (Exception e) {

            System.out.println("catch block");
        } finally {

            System.out.println("finally block");

            if (b > 25) {
                System.out.println("b>25, b = " + b);
            }

            return 200;
        }

        //finally里加上return之后，finally外面的return b就执行不到了，所以需要注释掉否则编译器报错。
//        return b;
    }
}
