package finallydemo;

/**
 * Created by madali on 2017/5/1.
 * <p>
 * try块里的return语句在异常的情况下不会被执行，这样具体返回哪个看情况。
 */
public class FinallyDemo4 {

    public static void main(String[] args) {
        System.out.println(test4());
    }

    public static int test4() {
        int b = 20;

        try {
            System.out.println("try block");

            b = b / 0;

            return b += 80;
        } catch (Exception e) {

            b += 15;
            System.out.println("catch block");
        } finally {

            System.out.println("finally block");

            if (b > 25) {
                System.out.println("b>25, b = " + b);
            }

            b += 50;
        }

//        return b;
        return 2000;
    }
}
