package collection.list;

import org.junit.Test;

import java.util.*;

/**
 * Created by madali on 2017/11/9.
 */
public class ListDemo {

    private static User user;
    private static List<User> userList = new ArrayList<>();

    static {
        user = new User("u1", "p1", "2017-11-09 15:05:48");
        User user2 = new User("u2", "p2", "2017-11-09 15:05:48");
        User user3 = new User("u3", "p3", "2017-11-09 15:07:48");
        User user4 = new User("u4", "p4", "2017-11-09 15:06:48");
        User user5 = new User("u1", "p5", "2017-11-09 15:08:48");
        User user6 = new User("u2", "p6", "2017-11-09 15:03:48");

        userList.add(user);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);
        userList.add(user6);
    }

    //list根据字段分组，并统计每个字段的个数
    @Test
    public void group() {

        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < userList.size(); i++) {
            if (map.containsKey(userList.get(i).getUsername())) {
                int size = map.get(userList.get(i).getUsername());
                map.put(userList.get(i).getUsername(), size + 1);
            } else {
                map.put(userList.get(i).getUsername(), 1);
            }
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }

    }

    //list根据某个字段倒序排序
    @Test
    public void order() {
        //按照时间倒序排列
        Collections.sort(userList, (o1, o2) -> o2.getTime().compareTo(o1.getTime()));

        for (int i = 0; i < userList.size(); i++) {
            System.out.println(userList.get(i));
        }
    }

    //流处理
    @Test
    public void test() {
        boolean flag = userList.stream().noneMatch(user -> user.getTime().length() > 8);
        System.out.println(flag);

        Optional<User> op = userList.stream().findAny();
        System.out.println(op);
    }

}
