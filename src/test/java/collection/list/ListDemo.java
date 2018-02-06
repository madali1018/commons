package collection.list;

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

    public static void main(String[] args) {
//        group();
//        order();

        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 15; i++) {
            list.add(i);
        }
//        System.out.println(getLimitList(list, 0, 5));
//        System.out.println(getLimitList(list, 1, 5));
//        System.out.println(getLimitList(list, 2, 5));
        System.out.println(getLimitList(list, 3, 5));
    }

    //list根据字段分组，并统计每个字段的个数
    private static void group() {

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
    private static void order() {
        //按照时间倒序排列
        Collections.sort(userList, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o2.getTime().compareTo(o1.getTime());
            }
        });


        //按照时间倒序排列
        Collections.sort(userList, (o1, o2) -> o2.getTime().compareTo(o1.getTime()));

        Collections.sort(userList, (o1, o2) -> o1.getTime().compareTo(o2.getTime()));


        for (int i = 0; i < userList.size(); i++) {
            System.out.println(userList.get(i));
        }
    }

    //list分页
    private static List getLimitList(List list, int pageIndex, int pageSize) {

        int pageStartRow;
        int pageEndRow;
        int totalPage = list.size() % pageSize == 0 ? list.size() / pageSize : list.size() / pageSize + 1;

        // 判断是否为最后一页
        if ((pageIndex + 1) * pageSize < list.size()) {
            pageEndRow = (pageIndex + 1) * pageSize;
            pageStartRow = pageEndRow - pageSize;
        } else {
            pageEndRow = list.size();
            pageStartRow = pageSize * (totalPage - 1);
        }

        List resultList = null;
        if (!list.isEmpty()) {
            resultList = list.subList(pageStartRow, pageEndRow);
        }

        return resultList;
    }

}
