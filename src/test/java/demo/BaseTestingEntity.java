package demo;

import java.io.Serializable;
import java.util.List;

/**
 * 测试实体
 *
 * @Auther: madali
 * @Date: 2018/8/21 15:05
 */
public class BaseTestingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private int age;
    private String name;
    private String url;
    private List<String> list;

    public BaseTestingEntity() {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseTestingEntity{");
        sb.append("age=").append(age);
        sb.append(", name='").append(name).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", list=").append(list);
        sb.append('}');
        return sb.toString().replace("'null'", "null");
    }
}
