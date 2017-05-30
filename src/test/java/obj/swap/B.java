package obj.swap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by madl on 2017/5/17.
 */
public class B {

//    @FieldDescriptionAnnotation(fieldDescription = "10")
//    private int age;

    @FieldDescriptionAnnotation(fieldDescription = "10")
    private int bge;

    private Double price;

    @FieldDescriptionAnnotation(fieldDescription = "false")
    private Boolean f;

    @FieldDescriptionAnnotation(fieldDescription = "name")
    private String name;

    @FieldDescriptionAnnotation(fieldDescription = "array")
    private int[] array;

    @FieldDescriptionAnnotation(fieldDescription = "list")
    private List<String> list;

    @FieldDescriptionAnnotation(fieldDescription = "map")
    private Map<Integer, String> map;

    @FieldDescriptionAnnotation(fieldDescription = "user")
    private User user;

    public B() {
    }

    public B(int bge, Double price, Boolean f, String name, int[] array, List<String> list, Map<Integer, String> map, User user) {
        this.bge = bge;
        this.price = price;
        this.f = f;
        this.name = name;
        this.array = array;
        this.list = list;
        this.map = map;
        this.user = user;
    }

    @Override
    public String toString() {
        return "B{" +
                "bge=" + bge +
                ", price=" + price +
                ", f=" + f +
                ", name='" + name + '\'' +
                ", array=" + Arrays.toString(array) +
                ", list=" + list +
                ", map=" + map +
                ", user=" + user +
                '}';
    }

    public int getBge() {
        return bge;
    }

    public void setBge(int bge) {
        this.bge = bge;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getF() {
        return f;
    }

    public void setF(Boolean f) {
        this.f = f;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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