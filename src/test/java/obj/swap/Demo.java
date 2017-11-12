package obj.swap;

import java.util.Map;

/**
 * Created by madali on 2017/5/17.
 */
public class Demo {

    @FieldDescriptionAnnotation(fieldDescription = "1")
    private String age;

    @FieldDescriptionAnnotation(fieldDescription = "2")
    private String bAge;

    @FieldDescriptionAnnotation(fieldDescription = "3")
    private String userName;

    @FieldDescriptionAnnotation(fieldDescription = "4")
    private String uSername;

    public String getuSername() {
        return uSername;
    }

    public void setuSername(String uSername) {
        this.uSername = uSername;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getbAge() {
        return bAge;
    }

    public void setbAge(String bAge) {
        this.bAge = bAge;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static void main(String[] args) throws Exception {

        Demo demo = new Demo();

        Map<String, Object[]> fieldAnnotationMap = ObjectSwapUtil.getFieldAnnotationMap(demo);

        System.out.println(fieldAnnotationMap.size());
    }
}
