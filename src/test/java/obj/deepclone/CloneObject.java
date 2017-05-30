package obj.deepclone;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by madl on 2017/5/12.
 */
public class CloneObject implements Cloneable, Serializable {

    private int age;

    private Boolean flag;

    private String description;

    private int[] array;

    private List<String> list;

    private Map<Integer, String> map;

    private User user;

    public CloneObject() {
    }

    public CloneObject(int age, Boolean flag, String description, int[] array, List<String> list, Map<Integer, String> map, User user) {
        this.age = age;
        this.flag = flag;
        this.description = description;
        this.array = array;
        this.list = list;
        this.map = map;
        this.user = user;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        //浅拷贝：继承java.lang.Object类的clone方法
        CloneObject cloneObject = (CloneObject) super.clone();

        //list等实现深拷贝，需要遍历原list中的元素，将其添加至一个新的list，然后set给深拷贝出的对象。
        //才会实现原对象和拷贝出的对象相互独立。
        List<String> cloneList = new ArrayList<>(this.list.size());
        cloneList.addAll(this.list.stream().collect(Collectors.toList()));
        cloneObject.setList(cloneList);

        //map实现深拷贝也是遍历，加至新的map，然后set给深拷贝出的对象
        Map cloneMap = new HashMap<>(this.map.size());

        //不能使用putAll，因为HashMap的putAll方法是浅拷贝
//        cloneMap.putAll(this.map);

        for (Map.Entry<Integer, String> entry : this.map.entrySet()) {

            cloneMap.put(entry.getKey(), entry.getValue());
        }
        cloneObject.setMap(cloneMap);

        //深拷贝：给a1的引用类型属性赋值
        cloneObject.setUser((User) cloneObject.getUser().clone());

        return cloneObject;
    }

    @Override
    public String toString() {
        return "CloneObject{" +
                "age=" + age +
                ", flag=" + flag +
                ", description='" + description + '\'' +
                ", array=" + Arrays.toString(array) +
                ", list=" + list +
                ", map=" + map +
                ", user=" + user +
                '}';
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int[] getArray() {
        return array;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Map<Integer, String> getMap() {
        return map;
    }

    public void setMap(Map<Integer, String> map) {
        this.map = map;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}