package jvm;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * 查看永久保留区域PermGen使用情况：一个类型装载之后会创建一个对应的java.lang.Class实例，这个实例本身和普通对象实例一样存储于堆中，我
 * 觉得之所以说是这是一种特殊的实例，某种程度上是因为其充当了访问PermGen区域中类型信息的代理者。
 *
 * @Auther: madali
 * @Date: 2018/8/20 17:49
 */
public class PermGenDemo {

    private static List<Object> insList = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        permLeak();
    }

    private static void permLeak() throws Exception {
        for (int i = 0; i < 1000; i++) {
            URL[] urls = getURLS();
            URLClassLoader urlClassloader = new URLClassLoader(urls, null);
            Class<?> logfClass = Class.forName("org.apache.commons.logging.LogFactory", true, urlClassloader);
            Method getLog = logfClass.getMethod("getLog", String.class);
            Object result = getLog.invoke(logfClass, "TestPermGen");
            insList.add(result);
            System.out.println(i + ": " + result);
        }
    }

    private static URL[] getURLS() throws MalformedURLException {
        File libDir = new File("D:\\mavenRespository\\commons-logging\\commons-logging\\1.1.1");
        File[] subFiles = libDir.listFiles();
        int count = subFiles.length;
        URL[] urls = new URL[count];
        for (int i = 0; i < count; i++) {
            urls[i] = subFiles[i].toURI().toURL();
        }
        return urls;
    }

}
