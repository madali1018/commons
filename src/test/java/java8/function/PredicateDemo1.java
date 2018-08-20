package java8.function;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Predicate函数式接口的主要作用就是提供一个test方法，接受一个参数返回一个boolean类型，Predicate在stream api中进行一些判断的时候非常常用。
 *
 * @Auther: madali
 * @Date: 2018/8/7 16:44
 */
public class PredicateDemo1 {

    static List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    @Test
    public void test1() {
        //输出大于5的数字
        List<Integer> result = conditionFilter(list, integer -> integer > 5);
        result.forEach(System.out::println);
        System.out.println("-------");
        //输出小于等于8的数字
        result = conditionFilter(list, integer -> integer <= 8);
        result.forEach(System.out::println);
        System.out.println("-------");
        //输出所有数字
        result = conditionFilter(list, integer -> true);
        result.forEach(System.out::println);
        System.out.println("-------");
    }

    @Test
    public void test2() {
        //大于5且是偶数
        List<Integer> result = conditionFilterAnd(list, integer -> integer > 5, integer2 -> integer2 % 2 == 0);
        result.forEach(System.out::println);
        System.out.println("-------");

        //大于等于5或者是偶数
        result = conditionFilterOr(list, integer -> integer >= 5, integer2 -> integer2 % 2 == 0);
        result.forEach(System.out::println);
        System.out.println("----------");

        //小于6
        result = conditionFilterNegate(list, integer -> integer >= 6);
        result.forEach(System.out::println);
    }

    @Test
    public void test3() {
        String s1 = "AX";
        String s2 = "AS";
        //isEqual方法返回类型也是Predicate，也就是说通过isEqual方法得到的也是一个用来进行条件判断的函数式接口实例。
        // 而返回的这个函数式接口实例是通过传入的targetRef的equals方法进行判断的。

        //这里会用s1的equals方法判断与s2是否相等，结果false。
        System.out.println(Predicate.isEqual(s1).test(s2));
    }

    //高度抽象的方法定义，复用性高
    private List<Integer> conditionFilter(List<Integer> list, Predicate<Integer> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }

    //与
    private List<Integer> conditionFilterAnd(List<Integer> list, Predicate<Integer> predicate, Predicate<Integer> predicate2) {
        return list.stream().filter(predicate.and(predicate2)).collect(Collectors.toList());
    }

    //或
    private List<Integer> conditionFilterOr(List<Integer> list, Predicate<Integer> predicate, Predicate<Integer> predicate2) {
        return list.stream().filter(predicate.or(predicate2)).collect(Collectors.toList());
    }

    //将当前条件取反
    private List<Integer> conditionFilterNegate(List<Integer> list, Predicate<Integer> predicate) {
        return list.stream().filter(predicate.negate()).collect(Collectors.toList());
    }

}
